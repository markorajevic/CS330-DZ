package com.metropolitan.cs330_dz07;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ViewActivity extends AppCompatActivity {
    
    private EditText etIme;
    private EditText etRad;
    private Button btnSave;
    private Button btnDelete;
    private Konsultacije konsultacije;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_form);

        findViews();
        checkIntentForStudent();
        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (konsultacije == null) {
                    addKonsul();
                } else {
                    updateKonsul();
                }
            }
        });
        this.btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteKonsul();
            }
        });
    }

    public void addKonsul(){
        ContentValues values = new ContentValues();
        values.put(KonsultacijeProvider.NAME,
                ((EditText)findViewById(R.id.editIme)).getText().toString());

        values.put(KonsultacijeProvider.RAD,
                ((EditText)findViewById(R.id.editRadSaStudentima)).getText().toString());

        Uri uri = getContentResolver().insert(
                KonsultacijeProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
        this.finish();
    }

    public void updateKonsul(){
        ContentValues values = new ContentValues();
        values.put(KonsultacijeProvider.NAME,
                ((EditText)findViewById(R.id.editIme)).getText().toString());

        values.put(KonsultacijeProvider.RAD,
                ((EditText)findViewById(R.id.editRadSaStudentima)).getText().toString());

        int uri = getContentResolver().update(
                KonsultacijeProvider.CONTENT_URI, values, KonsultacijeProvider._ID +"="+konsultacije.getSifra_predmeta(), null);
        this.finish();
    }

    public void deleteKonsul(){
        int uri = getContentResolver().delete(KonsultacijeProvider.CONTENT_URI, KonsultacijeProvider._ID + "=" + konsultacije.getSifra_predmeta(),null);
        this.finish();
    }

    private void findViews() {
        this.etIme = (EditText) findViewById(R.id.editIme);
        this.etRad = (EditText) findViewById(R.id.editRadSaStudentima);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.btnDelete = (Button) findViewById(R.id.btnDelete);
    }

    private void checkIntentForStudent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        btnDelete.setEnabled(false);
        if (bundle != null) {
            konsultacije = (Konsultacije) bundle.get("KONSULTACIJE");
            if (konsultacije != null) {
                this.etIme.setText(konsultacije.getNaziv_predmeta());
                this.etRad.setText(konsultacije.getRad_sa_studentima());
                btnDelete.setEnabled(true);
            }
        }

    }

}
