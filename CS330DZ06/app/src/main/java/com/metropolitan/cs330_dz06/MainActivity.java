package com.metropolitan.cs330_dz06;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAdapter(this);

        try {
            String destPath = "/data/data/" + getPackageName() + "/databases";
            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
                CopyDB(getBaseContext().getAssets().open("ispit"),
                        new FileOutputStream(destPath + "/MyDB"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DisplayStudent(Cursor c){
        Toast.makeText(this,
                "Broj Indeksa: " + c.getString(0) + "\n" +
                        "Ime: " + c.getString(1) + "\n" +
                        "Broj Poena: " + c.getInt(2),
                Toast.LENGTH_LONG).show();
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

    public void onClickCreate(View view){
        Button btn = (Button) findViewById(R.id.btnCreate);
        final EditText indeks = (EditText) findViewById(R.id.indeks);
        final EditText ime = (EditText) findViewById(R.id.ime);
        final EditText bodovi = (EditText) findViewById(R.id.bodovi);

        db.open();
        long id = db.insertStudent(
                Integer.parseInt(indeks.getText().toString()),
                ime.getText().toString(),
                Integer.parseInt(bodovi.getText().toString()));
        if(id != -1){
            Toast.makeText(getApplicationContext(),
                    "Broj Indeksa: " + indeks.getText().toString() + "\n" +
                            "Ime: " + ime.getText().toString() + "\n" +
                            "Broj Poena: " + bodovi.getText().toString(), Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(), "Greska pirlikom dodavanja!", Toast.LENGTH_LONG).show();
        }
        db.close();

    }

    public void onClickRead(View view){
        db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllStudents();
        if(c.moveToFirst()){
            do{
                DisplayStudent(c);
            } while (c.moveToNext());
        }
        db.close();
    }

    public void onClickUpdate(View view){
        db = new DBAdapter(this);
        db.open();
        Button negativeButton = (Button) findViewById(R.id.btnCreate);
        final EditText indeks = (EditText) findViewById(R.id.indeks);
        final EditText ime = (EditText) findViewById(R.id.ime);
        final EditText bodovi = (EditText) findViewById(R.id.bodovi);

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Broj Indeksa: " + indeks.getText().toString() + "\n" +
                                "Ime: " + ime.getText().toString() + "\n" +
                                "Broj Poena: " + bodovi.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
        if (db.updateStudent(Integer.parseInt(indeks.getText().toString()), ime.getText().toString(), Integer.parseInt(bodovi.getText().toString())))
            Toast.makeText(this, "Izmena uspesna!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Neuspesna izmena!", Toast.LENGTH_LONG).show();
        db.close();
    }

    public void onClickDelete(View view){
        db = new DBAdapter(this);
        db.open();
        if (db.deleteStudent(2345))
            Toast.makeText(this, "Uspesno brisanje!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Neuspesno brisanje!", Toast.LENGTH_LONG).show();
        db.close();
    }
}
