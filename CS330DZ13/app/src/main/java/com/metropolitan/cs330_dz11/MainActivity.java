package com.metropolitan.cs330_dz11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.app.NotificationManager;
import android.content.Context;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPlayClick(View view) {
        startService(new Intent(getBaseContext(), Mp3Service.class));
        displayNotification("Start pesme", "Pesma je startovana");
    }

    public void onStopClick(View view) {
        stopService(new Intent(getBaseContext(), Mp3Service.class));
        displayNotification("Stop pesme", "Pesma je pauzirana");
    }


    private void displayNotification(String title, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content);

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

}
