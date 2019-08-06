package com.metropolitan.cs330_dz03_1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.Display;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();
        if (d.getWidth() > d.getHeight()) {
            Toast.makeText(getBaseContext(), "Landscape", Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(getBaseContext(), "Portrait", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayName(View v) {
        Toast.makeText(getBaseContext(), "Univerzitet Metropolitan", Toast.LENGTH_SHORT).show();
    }

    public void displayLocation(View v) {
        Intent i = new Intent("android.intent.action.VIEW");
        i.setData(Uri.parse("https://www.google.com/maps/place/Metropolitan+University/@44.8303365,20.4525867,17z/data=!3m1!4b1!4m5!3m4!1s0x475a6530da697d59:0xd2bdfbf9d4b80259!8m2!3d44.8303365!4d20.4547754"));
        startActivity(i);
    }
}
