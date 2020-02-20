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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class AddBeleska extends AppCompatActivity {

    DatabaseHelper db;


    TextView datum;
    EditText naslov, beleska;
    private RadioGroup radioGroup;
    private RadioButton radioButton,radioButton2;
    ImageButton save;

    String status, datumstr;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_beleska);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.notes_n4_32x32);
//        actionBar.setLogo(R.drawable.);
//        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db = new DatabaseHelper(this);

        naslov = (EditText) findViewById(R.id.naslov2);
        beleska = (EditText) findViewById(R.id.beleska2);
        datum = (TextView) findViewById(R.id.datum5);

        radioGroup = (RadioGroup) findViewById(R.id.radioCheck3);


        int selectedButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedButtonId);
        status = radioButton.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                radioButton = (RadioButton) findViewById(checkedId);
                status = radioButton.getText().toString();
               // Log.e("radio", status);
            }
        });

//        ImageButton save = (ImageButton) findViewById(R.id.save3);

        Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);
        datumstr = day + "." + (month + 1) + "." + year + ".";

        datum.setText(datumstr);

//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isDodato = db.addBeleska(naslov.getText().toString(),beleska.getText().toString(), datumstr, status) ;
//                if(isDodato){
//                    Toast.makeText(getBaseContext(),"Beleška je sačuvana",Toast.LENGTH_SHORT ).show();
//                    Intent newIntent = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(newIntent);
//                }else{
//                    Toast.makeText(getBaseContext(),"Došlo je do greške",Toast.LENGTH_SHORT ).show();
//                }
//            }
//        });
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

        getMenuInflater().inflate(R.menu.add_menu,menu);

//        menu.add(0,0,0,"Lista beleški");
//
//        MenuItem mnu1 = menu.add(0, 1, 1, "Dodaj belešku");
//        {
//
//            //   mnu1.setAlphabeticShortcut('a');
//            //   mnu1.setIcon(R.mipmap.ic_launcher);
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
//               mnu3.setIcon(R.drawable.settings_icon_24x24);
//            mnu3.setShowAsAction( MenuItem.SHOW_AS_ACTION_NEVER);
//        }
//        MenuItem mnu4 = menu.add(0, 4, 4, "Sačuvaj belešku");
//        {
//
//            //   mnu1.setAlphabeticShortcut('a');
//            mnu4.setIcon(R.drawable.save_white_512x512);
//            //ovo sluzi za prikaz iz menija u action baru ako ima mesta
//            mnu4.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        }
//        menu.add(0,4,4,"Lista beleški");
//        menu.add(0,5,5,"Kontakti");
//        menu.add(0,6,6,"Mapa");

    }

    private boolean MenuChoice(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.zero:

                Toast.makeText(this, "Lista beleški",
                        Toast.LENGTH_LONG).show();
                Intent myIntent0 = new Intent(getBaseContext(), MainActivity.class);
                startActivityForResult(myIntent0, 0);

                return true;
            case R.id.one:

//                Toast.makeText(this, "Dodavanje nove beleške",
//                        Toast.LENGTH_LONG).show();
                Intent myIntent1 = new Intent(getBaseContext(), AddBeleska.class);
                startActivityForResult(myIntent1, 0);


                return true;
            case R.id.two:

//                Toast.makeText(this, "Promena šifre",
//                        Toast.LENGTH_LONG).show();
                List<Sifra> sifre = db.getAllSifre();
                //Log.e("sf",sifre.toString());
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
            case R.id.three:

                Toast.makeText(this, "Podešavanja",
                        Toast.LENGTH_LONG).show();
                Intent myIntent3 = new Intent(getBaseContext(), Podesavanja.class);
                startActivityForResult(myIntent3, 0);



                return true;
            case R.id.four:

//                Toast.makeText(this, "Beleška sačuvana",
//                        Toast.LENGTH_LONG).show();
                //                Intent myIntent4 = new Intent(getBaseContext(), MainActivity.class);
//                startActivityForResult(myIntent4, 0);
                boolean isDodato = db.addBeleska(naslov.getText().toString(),beleska.getText().toString(), datumstr, datumstr, status) ;
                if(isDodato){
                    Toast.makeText(getBaseContext(),"Beleška je sačuvana",Toast.LENGTH_SHORT ).show();
                    Intent newIntent = new Intent(getApplicationContext(),ListaBeleski.class);
                    startActivity(newIntent);
                }else{
                    Toast.makeText(getBaseContext(),"Došlo je do greške",Toast.LENGTH_SHORT ).show();
                }

                return true;
//            case 5:
//                Toast.makeText(this, "Kontakti",
//                        Toast.LENGTH_LONG).show();
//                Intent myIntent5 = new Intent(getBaseContext(), ListaKontakata.class);
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

