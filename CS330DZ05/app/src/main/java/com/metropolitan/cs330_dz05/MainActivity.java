package com.metropolitan.cs330_dz05;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return MenuChoice(item);
    }

    private void CreateMenu(Menu menu){
        menu.setQwertyMode(true);
        MenuItem mnu1 = menu.add(0, 0, 0, "Univerzitet");{

            mnu1.setAlphabeticShortcut('a');
            mnu1.setIcon(R.mipmap.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "FIT");{
            mnu2.setAlphabeticShortcut('b');
            mnu2.setIcon(R.mipmap.ic_launcher);
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "FAM");{
            mnu3.setAlphabeticShortcut('c');
            mnu3.setIcon(R.mipmap.ic_launcher);
        }
        MenuItem mnu4 = menu.add(0, 3, 3, "FDU");{
            mnu4.setAlphabeticShortcut('d');
        }
        menu.add(0, 4, 4, "Next");

    }
    private boolean MenuChoice(MenuItem item){


        Intent intent = new Intent(this,SecondaryActivity.class);
        WebView wv = (WebView) findViewById(R.id.webview1);
        switch (item.getItemId()) {
            case 0:

                WebSettings webSettings = wv.getSettings();
                webSettings.setBuiltInZoomControls(true);
                wv.setWebViewClient(new Callback());
                wv.loadUrl("http://www.metropolitan.ac.rs/");
                return true;
            case 1:

                wv.loadUrl("http://www.metropolitan.ac.rs/osnovne-studije/fakultet-informacionih-tehnologija/");
                return true;
            case 2:
                wv.loadUrl("http://www.metropolitan.ac.rs/osnovne-studije/fakultet-za-menadzment/");
                return true;
            case 3:
                wv.loadUrl("http://www.metropolitan.ac.rs/fakultet-digitalnih-umetnosti-2/");
                return true;
            case 4:
                startActivity(intent);
                return true;

        }
        return false;
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return(false);
        }
    }


}
