package com.metropolitan.cs330_dz05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ContextMenu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ImageView;

public class SecondaryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        ImageView img = (ImageView) findViewById(R.id.image1);
        img.setOnCreateContextMenuListener(this);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, view, menuInfo);
        CreateMenu(menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){

        return MenuChoice(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        return MenuChoice(item);
    }

    private void CreateMenu(Menu menu) {
        menu.setQwertyMode(true);
        MenuItem mnu1 = menu.add(0, 0, 0, "MA101");
        {

            mnu1.setAlphabeticShortcut('a');

        }
        MenuItem mnu2 = menu.add(0, 1, 1, "CS101");
        {
            mnu2.setAlphabeticShortcut('b');

        }
        MenuItem mnu3 = menu.add(0, 2, 2, "CS102");
        {
            mnu3.setAlphabeticShortcut('c');

        }
        MenuItem mnu4 = menu.add(0, 3, 3, "CS330");
        {
            mnu4.setAlphabeticShortcut('d');
        }
        MenuItem mnu5 = menu.add(0, 4, 4, "CS200");
        {
            mnu5.setAlphabeticShortcut('c');

        }
    }
    private boolean MenuChoice(MenuItem item){

        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this,"Izabrali ste predmet MA101",Toast.LENGTH_LONG).show();

                return true;
            case 1:
                Toast.makeText(this,"Izabrali ste predmet CS101",Toast.LENGTH_LONG).show();

                return true;
            case 2:
                Toast.makeText(this,"Izabrali ste predmet CS102",Toast.LENGTH_LONG).show();
                return true;
            case 3:
                Toast.makeText(this,"Izabrali ste predmet CS330",Toast.LENGTH_LONG).show();
                return true;
            case 4:
                Toast.makeText(this,"Izabrali ste predmet CS200",Toast.LENGTH_LONG).show();
                return true;

        }
        return false;
    }

}
