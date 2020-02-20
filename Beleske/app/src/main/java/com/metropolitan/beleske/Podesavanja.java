package com.metropolitan.beleske;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class Podesavanja extends AppCompatActivity {


    public static final String KEY_PREF_EXAMPLE_SWITCH = "example_switch";
    DatabaseHelper db;

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        db = new DatabaseHelper(this);

//        setContentView(R.layout.podesavanja);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PodesavanjaFragment())
                .commit();


//        savedInstanceState..setBackgroundColor(Color.TRANSPARENT);
////        getListView().setCacheColorHint(Color.TRANSPARENT);
//        getView().setBackgroundColor(Color.rgb(4, 26, 55));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    private void CreateMenu(Menu menu) {
        // menu.setQwertyMode(true);

        getMenuInflater().inflate(R.menu.main_menu,menu);

//        menu.add(0,0,0,"Lista beleški");

//        MenuItem mnu1 = menu.add(0, 1, 1, "Dodaj belešku");
//        {
//
//            //   mnu1.setAlphabeticShortcut('a');
//            //   mnu1.setIcon(R.mipmap.ic_launcher);
//            // mnu1.setIcon(R.drawable.save_512x512);
//            //ovo sluzi za prikaz iz menija u action baru ako ima mesta
////            mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        }
//        MenuItem mnu2 = menu.add(0, 2, 2, "Promena šifre");
//        {
//            // mnu2.setAlphabeticShortcut('b');
//            //   mnu2.setIcon(R.mipmap.ic_launcher);
//        }
//        MenuItem mnu3 = menu.add(0, 3, 3, "Podešavanja");
//        {
//            // mnu3.setAlphabeticShortcut('c');
//            //   mnu3.setIcon(R.mipmap.ic_launcher);
//        }


//        menu.add(0,4,4,"Lista beleški");
        //  menu.add(0,5,5,"Login");
//        menu.add(0,6,6,"Mapa");
    }

    private boolean MenuChoice(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.zero: //case 0
                Toast.makeText(this, "Lista beleški",
                        Toast.LENGTH_LONG).show();
                Intent myIntent0 = new Intent(getBaseContext(), ListaBeleski.class);
                startActivityForResult(myIntent0, 0);

                return true;
            case R.id.one: //case 1
                Toast.makeText(this, "Dodavanje nove beleške",
                        Toast.LENGTH_LONG).show();
                Intent myIntent1 = new Intent(getBaseContext(), AddBeleska.class);
                startActivityForResult(myIntent1, 0);

                return true;
            case R.id.two: //case 2

                List<Sifra> sifre = db.getAllSifre();
                // Log.e("sf",sifre.toString());
                if(sifre.isEmpty()){
                    Toast.makeText(this, "Dodaj šifru",
                            Toast.LENGTH_LONG).show();
                    Intent myIntent8 = new Intent(getBaseContext(), DodajSifru.class);
                    startActivityForResult(myIntent8, 0);

                }else {
                    Toast.makeText(this, "Promena šifre",
                            Toast.LENGTH_LONG).show();
                    Intent myIntent7 = new Intent(getBaseContext(), PromenaSifre.class);
                    startActivityForResult(myIntent7, 0);

                }

                return true;

            case R.id.three: //case 3
                Toast.makeText(this, "Podešavanja",
                        Toast.LENGTH_LONG).show();
                Intent myIntent3 = new Intent(getBaseContext(), Podesavanja.class);
                startActivityForResult(myIntent3, 0);
                return true;

        }
        return false;
    }


}
