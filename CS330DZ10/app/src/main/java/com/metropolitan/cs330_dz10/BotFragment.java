package com.metropolitan.cs330_dz10;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BotFragment extends Fragment {

    static final String NICKNAME = "Marko";
    InetAddress serverAddress;
    Socket socket;
    static TextView txtMessagesReceived;
    EditText txtMessage;
    CommsThread commsThread;
    Button btn;


    private InputStream OpenHttpConnection(String urlString)
            throws IOException {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Nema HTTP Konekcije");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Greska u povezivanju");
        }
        return in;
    }

    private String WordDefinition(String word) {
        InputStream in = null;
        String strDefinition = "";
        try {
            in = OpenHttpConnection(
                    "http://services.aonaware.com/DictService/"
                            + "DictService.asmx/Define?word=" + word);
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            try {
                db = dbf.newDocumentBuilder();
                doc = db.parse(in);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            doc.getDocumentElement().normalize();

            NodeList definitionElements = doc.getElementsByTagName("Definition");

            for (int i = 0; i < definitionElements.getLength(); i++) {
                Node itemNode = definitionElements.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element definitionElement = (Element) itemNode;

                    NodeList wordDefinitionElements =
                            (definitionElement).getElementsByTagName(
                                    "WordDefinition");
                    strDefinition = "";

                    for (int j = 0; j < wordDefinitionElements.getLength(); j++)
                    {
                        Element wordDefinitionElement =
                                (Element) wordDefinitionElements.item(j);
                        NodeList textNodes =
                                ((Node) wordDefinitionElement).getChildNodes();
                        strDefinition +=
                                ((Node) textNodes.item(0)).getNodeValue() + ". \n";
                    }
                }
            }
        } catch (IOException e1) {
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }
        return strDefinition;
    }

    private class AccessWebServiceTask extends AsyncTask<String,
            Void, String> {
        protected String doInBackground(String... urls) {
            return WordDefinition(urls[0]);
        }

        protected void onPostExecute(String result) {
            Toast.makeText(getActivity().getBaseContext(), result,
                    Toast.LENGTH_LONG).show();
        }
    }

    private class CreateCommThreadTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                serverAddress = InetAddress.getByName("192.168.1.101");
                socket = new Socket(serverAddress, 500);
                commsThread = new CommsThread(socket);
                commsThread.start();
                sendToServer(NICKNAME);
            } catch (UnknownHostException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            } catch (IOException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            }
            return null;
        }
    }

    private class WriteToServerTask extends AsyncTask<byte[], Void, Void> {
        protected Void doInBackground(byte[]...data) {
            commsThread.write(data[0]);
            return null;
        }
    }

    private class CloseSocketTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket.close();
            } catch (IOException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            }
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bot_fragment, container, false);

        //Chat
        txtMessage = (EditText)view. findViewById(R.id.txtMessage);
        txtMessagesReceived = (TextView)view.findViewById(R.id.txtMessagesReceived);

        final Button loginButton = (Button) view.findViewById(R.id.send);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickSend(v);
            }
        });



        return view;
    }

    public void onClickSend(View view) {
        sendToServer(txtMessage.getText().toString());
    }


    private void sendToServer(String message) {
        byte[] theByteArray = message.getBytes();
        new WriteToServerTask().execute(theByteArray);
    }

    @Override
    public void onResume() {
        super.onResume();
        new CreateCommThreadTask().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        new CloseSocketTask().execute();
    }
}
