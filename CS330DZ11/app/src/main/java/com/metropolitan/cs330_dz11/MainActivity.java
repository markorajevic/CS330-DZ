package com.metropolitan.cs330_dz11;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPlayClick(View view) {
        startService(new Intent(getBaseContext(), Mp3Service.class));
    }

    public void onStopClick(View view) {
        stopService(new Intent(getBaseContext(), Mp3Service.class));
    }
}
