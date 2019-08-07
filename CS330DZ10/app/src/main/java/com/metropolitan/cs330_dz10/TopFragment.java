package com.metropolitan.cs330_dz10;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;


import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLConnection;
public class TopFragment extends Fragment {

    ImageView img;

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

    private Bitmap DownloadImage(String URL) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }
        return bitmap;
    }


    private class DownloadImageTask extends AsyncTask <String, Bitmap, Long> {
        protected Long doInBackground(String... urls) {
            long imagesCount = 0;
            for (int i = 0; i < urls.length; i++) {
                Bitmap imageDownloaded = DownloadImage(urls[i]);
                if (imageDownloaded != null)  {
                    imagesCount++;
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(imageDownloaded);
                }
            }
            return imagesCount;
        }

        protected void onProgressUpdate(Bitmap... bitmap) {
            img.setImageBitmap(bitmap[0]);
        }

        protected void onPostExecute(Long imagesDownloaded) {
            Toast.makeText(getActivity().getBaseContext(),
                    "Ukupno je " + imagesDownloaded +
                            " slika primljeno" ,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.top_fragment, container, false);

        img = (ImageView)view. findViewById(R.id.img);
        new DownloadImageTask().execute(
                "http://www.metropolitan.edu.rs/files/fotografije/galerija/"
                        + "okruzenje-univerziteta/Okru%C5%BEenje-Univerziteta-01.jpg",
                "http://www.metropolitan.edu.rs/files/fotografije/galerija/"
                        + "okruzenje-univerziteta/Okru%C5%BEenje-Univerziteta-03.jpg",
                "http://www.metropolitan.edu.rs/files/fotografije/galerija/"
                        + "okruzenje-univerziteta/Okru%C5%BEenje-Univerziteta-10.jpg",
                "http://www.metropolitan.edu.rs/files/fotografije/galerija/"
                        + "okruzenje-univerziteta/Okru%C5%BEenje-Univerziteta-28.jpg",
                "http://www.metropolitan.edu.rs/files/fotografije/galerija/"
                        + "okruzenje-univerziteta/Okru%C5%BEenje-Univerziteta-28.jpg"
        );





        return view;



    }




}
