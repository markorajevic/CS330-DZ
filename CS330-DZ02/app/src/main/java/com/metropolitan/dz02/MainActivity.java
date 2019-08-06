package com.metropolitan.dz02;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CharSequence[] items = { "FIT", "FAM", "FDU" };
    int checkedItem = 1;
    String[] urlToOpen = { "http://www.metropolitan.ac.rs/osnovne-studije/fakultet-informacionih-tehnologija/", "http://www.metropolitan.ac.rs/osnovne-studije/fakultet-za-menadzment/", "http://www.metropolitan.ac.rs/fakultet-digitalnih-umetnosti-2/" };
    boolean[] itemsChecked = new boolean [items.length];

    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openOptions(View v) {
        showDialog(0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Izaberite fakultet")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,	int whichButton)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToOpen[checkedItem]));
                                startActivity(intent);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                Toast.makeText(getBaseContext(),
                                        "Cancel je kliknut!", Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                checkedItem = arg1;
                            }
                        } ).create();
    }
}
