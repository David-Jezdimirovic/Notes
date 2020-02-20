package com.metropolitan.beleske;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
       // notificationHelper = new NotificationHelper(this);
//        try {
//            // copydatabase();
//            db.createdatabase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.notes_n4_32x32);
//        actionBar.setLogo(R.drawable.);
//        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Sets default values only once, first time this is called.
        // The third argument is a boolean that indicates whether the default values
        // should be set more than once. When false, the system sets the default values
        // only if this method has never been called in the past.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Read settings
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean
                (Podesavanja.KEY_PREF_EXAMPLE_SWITCH, false);
       // Toast.makeText(this, switchPref.toString(), Toast.LENGTH_SHORT).show();

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        prefs.edit().putBoolean("prekopirano",false).commit();
//        boolean kopiranje=prefs.getBoolean("prekopirano",false);
//
//        if(!kopiranje){
//           // kopiranje=true;
//            prefs.edit().putBoolean("prekopirano",true).apply();
//            //finish();
//        }else {

        List<Sifra> sifre2 = db.getAllSifre();
        if(switchPref && !sifre2.isEmpty()){
//        if(switchPref){
            Intent myIntent = new Intent(getBaseContext(), Login.class);
            startActivityForResult(myIntent, 0);
            finish();  // sprecava back dugme da ponovo otvori aktivnost

        }else{
            Intent myIntent10 = new Intent(getBaseContext(), ListaBeleski.class);
            startActivityForResult(myIntent10, 0);
            finish();  // sprecava back dugme da ponovo otvori aktivnost
        }

//        }

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
////        menu.add(0,4,4,"Lista beleški");
//        //  menu.add(0,5,5,"Login");
////        menu.add(0,6,6,"Mapa");
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
//                Intent myIntent2 = new Intent(getBaseContext(), PromenaSifre.class);
////                myIntent2.putExtra("datum","");
//                startActivityForResult(myIntent2, 0);
                return true;
            case R.id.three: //case 3
                Toast.makeText(this, "Podešavanja",
                        Toast.LENGTH_LONG).show();
                Intent myIntent3 = new Intent(getBaseContext(), Podesavanja.class);
                startActivityForResult(myIntent3, 0);
                return true;
//            case 4:
//                Toast.makeText(this, "Lista beleški",
//                        Toast.LENGTH_LONG).show();
//                Intent myIntent4 = new Intent(getBaseContext(), ListaBeleski.class);
//                startActivityForResult(myIntent4, 0);
//                return true;
//            case 5:
//                Toast.makeText(this, "Login",
//                        Toast.LENGTH_LONG).show();
//                Intent myIntent5 = new Intent(getBaseContext(), Login.class);
//                startActivityForResult(myIntent5, 0);
//                return true;
//            case 6:
//                Toast.makeText(this, "Mapa",
//                        Toast.LENGTH_LONG).show();
//                Intent myIntent6 = new Intent(getBaseContext(), MapsActivity.class);
//                startActivityForResult(myIntent6, 0);
//                return true;
        }
        return false;
    }

}
