package com.metropolitan.cs330_dz07;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.database.Cursor;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> konsultacije = new ArrayList<>();
    List<Konsultacije> konsult = new ArrayList<>();
    private ListView listView;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.listView);
        this.btnAdd = (Button) findViewById(R.id.addButton);

        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKonsultacije();
            }
        });

        konsult = getKonstultacije();
        for (int i = 0; i < konsult.size(); i ++){
            konsultacije.add(konsult.get(i).getSifra_predmeta() + " " + konsult.get(i).getNaziv_predmeta() + " - " + konsult.get(i).getRad_sa_studentima());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, konsultacije);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateKonsultacije(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
    }

    private void updateListView() {
        konsult = getKonstultacije();
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < konsult.size(); i ++){
            lista.add(konsult.get(i).getSifra_predmeta() + " " + konsult.get(i).getNaziv_predmeta() + " - " + konsult.get(i).getRad_sa_studentima());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        this.listView.setAdapter(adapter);
    }

    private void addKonsultacije() {
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }

    public List<Konsultacije> getKonstultacije() {
        List<Konsultacije> lista = new ArrayList<>();
        String URL = "content://com.metropolitan.cs330_dz07.KonsultacijeProvider";
        Uri students = Uri.parse(URL);
        Cursor c = managedQuery(students, null, null, null, null);
        if (c.moveToFirst()) {
            do{
                Konsultacije k = new Konsultacije(Integer.parseInt(c.getString(c.getColumnIndex(KonsultacijeProvider._ID))), c.getString(c.getColumnIndex(KonsultacijeProvider.NAME)), c.getString(c.getColumnIndex(KonsultacijeProvider.RAD)));
                lista.add(k);
            } while (c.moveToNext());
        }
        return lista;
    }

    private void updateKonsultacije(int index) {
        Konsultacije konsultacije = konsult.get(index);
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("KONSULTACIJE", konsultacije);
        startActivity(intent);
    }
}
