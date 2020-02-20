package com.metropolitan.beleske;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BeleskaInfo extends AppCompatActivity {


    DatabaseHelper db;


    TextView datum, datumkreirano;
    EditText naslov, beleska;
    private RadioGroup radioGroup;
    private RadioButton radioButton,radioButton2;
    ImageButton save,delete;
    Button exportOne;

    String beleska_id, naslo, belesk, datu, datumkr, statu, datumst;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beleska_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.notes_n4_32x32);
//        actionBar.setLogo(R.drawable.);
//        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db = new DatabaseHelper(this);

        naslov = (EditText) findViewById(R.id.naslov3);
        beleska = (EditText) findViewById(R.id.beleska3);
        datum = (TextView) findViewById(R.id.datum7);
        datumkreirano = (TextView) findViewById(R.id.datum8);

        radioGroup = (RadioGroup)findViewById(R.id.radioCheck4);
        radioButton = (RadioButton)findViewById(R.id.radioUradjeno2);
        radioButton2 = (RadioButton)findViewById(R.id.radioNijeUradjeno2);

        exportOne = (Button) findViewById(R.id.export_one);
//        ImageButton save = (ImageButton) findViewById(R.id.save4);
//        ImageButton del = (ImageButton) findViewById(R.id.delete7);

        Intent intent = getIntent();
        beleska_id = intent.getStringExtra("beleska_id");
        naslo = intent.getStringExtra("naslov");
        belesk = intent.getStringExtra("beleska");
        datu = intent.getStringExtra("datum");
        datumkr = intent.getStringExtra("datumkreirano");
        statu = intent.getStringExtra("status");
        //Log.e("status",statu);

        naslov.setText(naslo);
        beleska.setText(belesk);
        datum.setText(datu);


        if(statu.equals(radioButton.getText().toString())) {
            radioButton.setChecked(true);
        }else {
            radioButton2.setChecked(true);

        }
         datumkreirano.setText(datumkr);




        Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);
        datumst = day + "." + (month + 1) + "." + year + ".";


        int selectedButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedButtonId);
        statu = radioButton.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                radioButton = (RadioButton) findViewById(checkedId);
                statu = radioButton.getText().toString();
//                Log.e("radio", statu);
            }
        });



        exportOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String putanja = "/beleske/";
                File wallpaperDirectory = new File(
                        Environment.getExternalStorageDirectory() ,putanja + "/");
                // have the object build the directory structure, if needed.
                if (!wallpaperDirectory.exists()) {
                    wallpaperDirectory.mkdirs();
                }
                String fileName = naslov.getText() + ".txt" ;
                try {
                    File f = new File(wallpaperDirectory, fileName);
//                    f.createNewFile();
                    //       Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

                    FileWriter writer = new FileWriter(f);


                        writer.append("Naslov: " + naslov.getText() + "\n" + "Tekst: " + beleska.getText() + "\n" +
                                "Izmenjeno: " + datum.getText() + "\n" + "Kreirano: " + datumkreirano.getText() + "\n" +
                                "Status: " + statu + "\n" );



                    writer.flush();
                    writer.close();

                    String m = "File generated with name " + fileName;
                    Toast.makeText(getBaseContext(),m,Toast.LENGTH_SHORT ).show();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });



//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isUpdate = db.updateBeleska(
//                        Integer.parseInt(beleska_id),
//                        naslov.getText().toString(),
//                        beleska.getText().toString(),
//                        datumst,
//                        statu);
//                if(isUpdate) {
//                    Toast.makeText(getBaseContext(), "Beleška uspešno ažurirana", Toast.LENGTH_LONG).show();
//                    Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
//
//                    startActivity(myIntent);
//                    // startActivity(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"));
//                } else {
//                    Toast.makeText(BeleskaInfo.this, "Došlo je do greške", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//
//
//        del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showDialog7(beleska_id);
//
//
//            }
//        });


    }


    private void showDialog1(final String beleska_id){
        new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK) //android.R.style.Theme_Material_Light_Dialog_Alert)
                .setTitle("Upozorenje!")
                .setMessage(
                        "Da li ste sigurni da želite da izbrišete belešku?")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

//                               // int id = k.getId();
//
                                // Integer deleteK = db.deleteKlijentAndReports(kl.getId());
                                Integer deleteI = db.deleteBeleska(Integer.parseInt(beleska_id));
                                if(deleteI > 0){
                                    Toast.makeText(getBaseContext(),"Beleška uspešno obrisana", Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(getBaseContext(), ListaBeleski.class);

                                    startActivity(myIntent);
                                    // startActivityForResult(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"),0);
                                }else{
                                    Toast.makeText(getBaseContext(), "Beleška nije obrisana", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .setNegativeButton("ne",  new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        dialog.dismiss();
                    }
                }).show();
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

        getMenuInflater().inflate(R.menu.update_menu,menu);

//        menu.add(0,0,0,"Lista beleški");
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
//            //   mnu3.setIcon(R.mipmap.ic_launcher);
//        }
//        MenuItem mnu4 = menu.add(0, 4, 4, "Sačuvaj belešku");
//        {
//
//            //   mnu1.setAlphabeticShortcut('a');
//            mnu4.setIcon(R.drawable.save_white_512x512);
//            //ovo sluzi za prikaz iz menija u action baru ako ima mesta
//            mnu4.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        }
//        MenuItem mnu5 = menu.add(0, 5, 5, "Obriši belešku");
//        {
//
//            //   mnu1.setAlphabeticShortcut('a');
//            mnu5.setIcon(R.drawable.trash_white_512x512);
//            //ovo sluzi za prikaz iz menija u action baru ako ima mesta
//            mnu5.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
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
                Intent myIntent0 = new Intent(getBaseContext(), ListaBeleski.class);
                startActivityForResult(myIntent0, 0);

                return true;
            case R.id.one:
                Toast.makeText(this, "Dodavanje nove beleške",
                        Toast.LENGTH_LONG).show();
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
                boolean isUpdate = db.updateBeleska(
                        Integer.parseInt(beleska_id),
                        naslov.getText().toString(),
                        beleska.getText().toString(),
                        datumst,
                        statu);
                if(isUpdate) {
                    Toast.makeText(getBaseContext(), "Beleška uspešno ažurirana", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(getBaseContext(), ListaBeleski.class);

                    startActivity(myIntent);
                    // startActivity(new Intent("com.metropolitan.cs330_pz.ListaKlijenata"));
                } else {
                    Toast.makeText(BeleskaInfo.this, "Došlo je do greške", Toast.LENGTH_LONG).show();
                }
//                Intent myIntent4 = new Intent(getBaseContext(), MainActivity.class);
//                startActivityForResult(myIntent4, 0);
                return true;

            case R.id.five:
//                Toast.makeText(this, "Kontakti",
//                        Toast.LENGTH_LONG).show();
//                Intent myIntent5 = new Intent(getBaseContext(), ListaKontakata.class);
//                startActivityForResult(myIntent5, 0);
                showDialog1(beleska_id);
                return true;
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
