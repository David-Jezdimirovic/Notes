package com.metropolitan.beleske;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class DodajSifru extends AppCompatActivity {

    DatabaseHelper db;

    EditText pass;
    Button addSifra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sifra);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.notes_n4_32x32);
//        actionBar.setLogo(R.drawable.);
//        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db = new DatabaseHelper(this);


        pass = (EditText)findViewById(R.id.pass) ;
        addSifra = (Button) findViewById(R.id.addSifra) ;


        addSifra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDodato = db.addSifra(
                        pass.getText().toString()
                        );
                if(isDodato) {
                    Toast.makeText(getBaseContext(), "Šifra uspešno dodata", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(getBaseContext(), ListaBeleski.class);

                     startActivity(myIntent);
                    // startActivity(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"));
                } else {
                    Toast.makeText(DodajSifru.this, "Došlo je do greške", Toast.LENGTH_LONG).show();
                }
            }
        });

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

