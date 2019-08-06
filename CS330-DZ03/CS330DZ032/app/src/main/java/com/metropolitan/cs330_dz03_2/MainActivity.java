package com.metropolitan.cs330_dz03_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    public String s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    public void click1(View v) {
        Toast.makeText(this, "DA! Student: " + s, Toast.LENGTH_LONG).show();
    }
    public void click2(View v) {
        Toast.makeText(this, "NE! Student: " + s, Toast.LENGTH_LONG).show();
    }


    public void CreateMenu(Menu menu) {
        final MenuItem mi1 = menu.add(0, 0, 0, "FIT");
        {
            mi1.setTitle("FIT");
            mi1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
        MenuItem mi2 = menu.add(0, 1, 1, "FAM");
        {
            mi2.setTitle("FAM");
            mi2.setShowAsAction( MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
        MenuItem mi3 = menu.add(0, 2, 2, "FDU");
        {
            mi3.setTitle("FDU");
            mi3.setShowAsAction( MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
    }
    private boolean MenuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Odabrali ste ikonu aplikacije!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            case 0:
                Toast.makeText(this, "Odabran: " + item.toString(), Toast.LENGTH_LONG).show();
                s = item.toString();
                return true;
            case 1:
                Toast.makeText(this, "Odabran: " + item.toString(), Toast.LENGTH_LONG).show();
                s = item.toString();
                return true;
            case 2:
                Toast.makeText(this, "Odabran: " + item.toString(), Toast.LENGTH_LONG).show();
                s = item.toString();
                return true;
        }
        return false;
    }

}