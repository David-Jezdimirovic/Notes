package com.metropolitan.beleske;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ListaBeleski extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    public static final int PICKFILE_RESULT_CODE = 1;
    DatabaseHelper db;
    List<Beleska> beleske = new ArrayList<>();
    BeleskaListAdapter adapter;
    ListView listView;
    ImageButton add;
    ImageButton x;
    Button izbrisiSve, exportAll, importOne;
    AutoCompleteTextView pretraga;
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_beleski);

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
//        actionBar.setLogo(R.drawable.notes_n11_32x32);
//        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Read settings
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean
                (Podesavanja.KEY_PREF_EXAMPLE_SWITCH, false);
        List<Sifra> sifre3 = db.getAllSifre();
        if(switchPref && sifre3.isEmpty()){
            showDialogSifre();
        }

        beleske=db.getAllBeleske();
        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, beleske);
        adapter = new BeleskaListAdapter(this, R.layout.beleska_list_adapter, beleske);
        pretraga = (AutoCompleteTextView) findViewById(R.id.bel);
        //Log.e("li",beleske.toString());
        listView = (ListView) findViewById(R.id.listabeleski);
        listView.setAdapter(adapter);

        ArrayAdapter<Beleska> adapter2 = new ArrayAdapter<Beleska>(ListaBeleski.this,
                android.R.layout.simple_list_item_1, beleske);

        pretraga.setAdapter(adapter);

        // disable dropdown list from auto complete text view
        pretraga.setDropDownHeight(0);
        // enable dropdown again
        //pretraga.setDropDownHeight(LayoutParams.WRAP_CONTENT);

       // pretraga.dismissDropDown();


        add = (ImageButton) findViewById(R.id.addBeleska) ;
        izbrisiSve = (Button) findViewById(R.id.izbrisiSve);
        x = (ImageButton) findViewById(R.id.x) ;


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("dozvola", "Dozvola je već odobrena.");
            } else {
                requestPermission();
            }
        }

        exportAll = (Button) findViewById(R.id.export_all) ;
        importOne = (Button) findViewById(R.id.import_note) ;

//        final ListView listView = (ListView) findViewById(R.id.listabeleski);
//        listView.setAdapter(adapter);

        final TextView poruka = (TextView)findViewById(R.id.poruka3) ;
        //  poruka.setVisibility(View.GONE);

        if(beleske.size()!= 0) {
            poruka.setVisibility(View.GONE);


        } else{
            poruka.setVisibility(View.VISIBLE);
            showDialog3();
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ListaBeleski.this, AddBeleska.class);
                startActivity(myIntent);
            }
        });



        izbrisiSve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog4();


            }
        });

        pretraga.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> parent,
                                     View view, int index, long id)
            {
                Beleska beleska=(Beleska) parent.getAdapter().getItem(index);

                Toast.makeText(getBaseContext(),
                        "Izabrali ste stavku : " + beleska,
                        Toast.LENGTH_SHORT).show();
                    pretraga.setText("");

                Intent myIntent = new Intent(getBaseContext(), BeleskaInfo.class);
                myIntent.putExtra("beleska_id",String.valueOf(beleska.getBeleska_id()));
                //  Log.e("id",String.valueOf(beleska.getBeleska_id()));
                myIntent.putExtra("naslov",beleska.getNaslov());
                myIntent.putExtra("beleska",beleska.getTekst());
                myIntent.putExtra("datum",beleska.getDatum());
                myIntent.putExtra("status",beleska.getStatus());
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);

            }

        });

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pretraga.setText("");
            }
        });

        x.setVisibility(View.GONE);

        //close button visibility for manual typing
        pretraga.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    x.setVisibility(View.VISIBLE);
                } else {
                    x.setVisibility(View.GONE);
                }
            }
        });


        //close button visibility for autocomplete text view selection
        pretraga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                x.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                x.setVisibility(View.GONE);
            }

        });



//        // Sets default values only once, first time this is called.
//        // The third argument is a boolean that indicates whether the default values
//        // should be set more than once. When false, the system sets the default values
//        // only if this method has never been called in the past.
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//
//        // Read settings
//        SharedPreferences sharedPref =
//                PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean switchPref = sharedPref.getBoolean
//                (Podesavanja.KEY_PREF_EXAMPLE_SWITCH, false);
//        // Toast.makeText(this, switchPref.toString(), Toast.LENGTH_SHORT).show();
//        if(switchPref){
//            Intent myIntent = new Intent(getBaseContext(), Login.class);
//            startActivityForResult(myIntent, 0);
//        }



        exportAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogExport();

            }
        });

        importOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);

////           ovo je pozvano u activitz result linija 343
//                File sdcard = Environment.getExternalStorageDirectory();
//
//                  //Get the text file
//                File file = new File(sdcard + "/beleske/","Filmovi.txt");
//
//                  //Read text from file
//                StringBuilder text = new StringBuilder();
//
//                try {
//                    BufferedReader br = new BufferedReader(new FileReader(file));
//                    String line;
//
//                    while ((line = br.readLine()) != null) {
//                        text.append(line);
//                        text.append('\n');
//
//                    }
//                    br.close();
//
//                    String[] splits = text.toString().split("Naslov:|Tekst:|Izmenjeno:|Kreirano:|Status:");
//
//                    Log.i("nasl",splits[1]);
//                    Log.i("teks",splits[2]);
//                    Log.i("izmenj",splits[3]);
//                    Log.i("kreir",splits[4]);
//                    Log.i("stat",splits[5]);
////                    for(String s : splits) {
////                        Log.i("2", s);
////
////                    }
////
//                    boolean isDodato = db.addBeleska(splits[1],splits[2], splits[3], splits[4], splits[5]) ;
//                    if(isDodato){
//                        Toast.makeText(getBaseContext(),"Beleška je importovana",Toast.LENGTH_SHORT ).show();
////                        Intent newIntent = new Intent(getApplicationContext(),ListaBeleski.class);
////                        startActivity(newIntent);
//                    }else{
//                        Toast.makeText(getBaseContext(),"Došlo je do greške",Toast.LENGTH_SHORT ).show();
//                    }
////
//                }
//                catch (IOException e) {
//                    //You'll need to add proper error handling here
//                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                  Uri fileUri = data.getData();
                    filePath = fileUri.getPath();

                    List<String> lis = fileUri.getPathSegments();
                    String fileName = lis.get(lis.size()-1);
                    //Get the text file
                    File sdcard = Environment.getExternalStorageDirectory();
//                    Log.i("h",sdcard+"");
//                    Log.i("ghf",filePath);
//                    Log.i("a",);
                File file = new File(sdcard + "/beleske/" + fileName);

                  //Read text from file
                StringBuilder text = new StringBuilder();
                    StringBuilder text2 = new StringBuilder();

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    int brojac = 0;

                    while ((line = br.readLine()) != null) {

                        if(line.equals("----------------------- END OF NOTE ---------------------")){
                           brojac++;
                           break;
                        }
                    }
                    br.close();

                    BufferedReader br2 = new BufferedReader(new FileReader(file));
                    String line2;
                    if(brojac != 0) {
                        while ((line2 = br2.readLine()) != null) {


                            text.append(line2);
                            // text.append("\r\n");
                            text2.append(line2);
                            text2.append('\n');
                            if (line2.equals("----------------------- END OF NOTE ---------------------")) {
                                String[] splits = text.toString().split("Naslov: |Tekst: |Izmenjeno: |Kreirano: |Status: |----------------------- END OF NOTE ---------------------");
                                String[] splits2 = text2.toString().split("Naslov: |Tekst: |Izmenjeno: |Kreirano: |Status: |----------------------- END OF NOTE ---------------------");
                                db.addBeleska(splits[1], splits2[2], splits[3], splits[4], splits[5]);

                                text.setLength(0);
                                text2.setLength(0);
                            }
                        }
                        beleske = db.getAllBeleske();
                        adapter.clear();
                        adapter = new BeleskaListAdapter(this, R.layout.beleska_list_adapter, beleske);
                        listView.setAdapter(adapter);

                    }else{
                        while ((line2 = br2.readLine()) != null) {


                            text.append(line2);
                            // text.append("\r\n");
                            text2.append(line2);
                            text2.append('\n');
                         }
                                String[] splits = text.toString().split("Naslov: |Tekst: |Izmenjeno: |Kreirano: |Status: ");
                                String[] splits2 = text2.toString().split("Naslov: |Tekst: |Izmenjeno: |Kreirano: |Status: ");

        //                    Log.i("nasl",splits[1]);
        //                    Log.i("teks",splits2[2]);
        //                    Log.i("izmenj",splits[3]);
        //                    Log.i("kreir",splits[4]);
        //                    Log.i("stat",splits[5]);
        //                    for(String s : splits) {
        //                        Log.i("2", s);
        //
        //                    }
        //
                            boolean isDodato = db.addBeleska(splits[1],splits2[2], splits[3], splits[4], splits[5]) ;
                            if(isDodato){
                                Toast.makeText(getBaseContext(),"Beleška je importovana",Toast.LENGTH_SHORT ).show();
                                beleske = db.getAllBeleske();
                                adapter.clear();
                                adapter = new BeleskaListAdapter(this, R.layout.beleska_list_adapter, beleske);
                                listView.setAdapter(adapter);

        //                        Intent newIntent = new Intent(getApplicationContext(),ListaBeleski.class);
        //                        startActivity(newIntent);
                            }else{
                                Toast.makeText(getBaseContext(),"Došlo je do greške",Toast.LENGTH_SHORT ).show();
                            }


                    }


                    br2.close();


//
                }
                catch (IOException e) {
                    //You'll need to add proper error handling here
                }
                }

                break;
        }
    }


    // ********************** PERMISIONS    *********************************************
    //provera dozvole
    private boolean checkPermission() {

        int readPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeontactPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if ( readPermissionResult == PackageManager.PERMISSION_GRANTED &&
                writeontactPermissionResult == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    //zahteva proveru dozvole
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    //vraća rezultate provere dozvole SEND_SMS
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(grantResults.length > 0) {
                    boolean read = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean write = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if( read && write) {
                        Toast.makeText(ListaBeleski.this,
                                "Dozvola je prihvaćena", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(ListaBeleski.this,
                            "Dozvola nije prihvaćena", Toast.LENGTH_LONG).show();



                }
                break;
        }
    }


    private void showDialogExport(){
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK) //android.R.style.Theme_Material_Light_Dialog_Alert)
                .setTitle("Obaveštenje")
                .setMessage(
                        "Kako želite da exportujete beleške?")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "Kao jedan fajl",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                String putanja = "/beleske/";
                                File wallpaperDirectory = new File(
                                        Environment.getExternalStorageDirectory() ,putanja + "/");
                                // have the object build the directory structure, if needed.
                                if (!wallpaperDirectory.exists()) {
                                    wallpaperDirectory.mkdirs();
                                }
                                String fileName = "beleske" + ".txt" ;
                                try {
                                    File f = new File(wallpaperDirectory, fileName);
//                    f.createNewFile();
                                    //       Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

                                    FileWriter writer = new FileWriter(f);

                                    for(Beleska b : beleske) {
                                        writer.append("Naslov: " + b.getNaslov() + "\n" + "Tekst: " + b.getTekst() + "\n" +
                                                 "Izmenjeno: " + b.getDatum() + "\n" + "Kreirano: " + b.getDatumkreirano() + "\n" +
                                                "Status: " + b.getStatus() + "\n" + "----------------------- END OF NOTE ---------------------" + "\n");

                                    }

                                    writer.flush();
                                    writer.close();

                                    String m = "File generated with name " + fileName;
                                    Toast.makeText(getBaseContext(),m,Toast.LENGTH_SHORT ).show();

                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        })
                .setNegativeButton("Kao posebne fajlove",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                String putanja = "/beleske/";
                                File wallpaperDirectory = new File(
                                        Environment.getExternalStorageDirectory() ,putanja + "/");
                                // have the object build the directory structure, if needed.
                                if (!wallpaperDirectory.exists()) {
                                    wallpaperDirectory.mkdirs();
                                }

                                try {

        //                    f.createNewFile();
                                    //       Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());



                                    for(Beleska b : beleske) {
                                        String fileName = b.getNaslov() + ".txt" ;
                                        File f = new File(wallpaperDirectory, fileName);
                                        FileWriter writer = new FileWriter(f);
                                        writer.append("Naslov: "  + b.getNaslov() + "\n" + "Tekst: " + b.getTekst() + "\n" +
                                                 "Izmenjeno: " + b.getDatum() + "\n" +  "Kreirano: " + b.getDatumkreirano() + "\n" +
                                                "Status: " + b.getStatus() + "\n" );
                                        writer.flush();
                                        writer.close();
                                    }



                                    String m = "Files are generated ";
                                    Toast.makeText(getBaseContext(),m,Toast.LENGTH_SHORT ).show();

                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }

                                dialog.dismiss();
                    }
                }).show();

    }



    private void showDialog3(){
        new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK) //android.R.style.Theme_Material_Light_Dialog_Alert)
                .setTitle("Obaveštenje")
                .setMessage(
                        "Trenutno nemate nijednu belešku.")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_info))
                .setPositiveButton(
                        "U redu",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                dialog.dismiss();
                            }
                        }).show();

    }

    private void showDialogSifre(){
        new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK) //android.R.style.Theme_Material_Light_Dialog_Alert)
                .setTitle("Obaveštenje")
                .setMessage(
                        "Nemate kreiranu šifru. Da bi opcija bezbednosti postala zaista aktivna, morate kreirati šifru.")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_info))
                .setPositiveButton(
                        "U redu",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                dialog.dismiss();
                                Intent noviIntent = new Intent(getBaseContext(), DodajSifru.class);
                                startActivity(noviIntent);
                            }
                        }).show();

    }


    private void showDialog4(){
        new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK) //android.R.style.Theme_Material_Light_Dialog_Alert)
                .setTitle("Upozorenje!")
                .setMessage(
                        "Da li ste sigurni da želite da obrišete sve beleške?")
                .setIcon(
                        this.getResources().getDrawable(
                                android.R.drawable.ic_dialog_alert))
                .setPositiveButton(
                        "Da",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                int a = db.deleteAllBeleske();
                                if(a!=0) {
                                    Toast.makeText(getApplicationContext(),"Sve beleške su obrisane",Toast.LENGTH_SHORT).show();
//                    Intent myIntent = new Intent(getBaseContext(), KlijentInfo.class);
//                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(myIntent);

                                    Intent intt = getIntent();
                                    intt.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // sakriva animaciju
                                    overridePendingTransition(0,0); // sakriva animaciju
                                    finish();
                                    startActivity(intt);
                                    overridePendingTransition(0,0);
                                    //   startActivity(getIntent()); //
                                }else{
                                    Toast.makeText(getApplicationContext(),"Došlo je do greške",Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View view,
//                                    ContextMenu.ContextMenuInfo menuInfo)
//    {
//        super.onCreateContextMenu(menu, view, menuInfo);
//        //CreateMenu(menu);
//        menu.setHeaderTitle("Context Menu");
//        menu.add(0, view.getId(), 0, "Delete");
//    }
//
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        // Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
//        switch (item.getItemId()) {
//            case 0:
////                Toast.makeText(this, "Lista beleški",
////                        Toast.LENGTH_LONG).show();
////                Intent myIntent0 = new Intent(getBaseContext(), MainActivity.class);
////                startActivityForResult(myIntent0, 0);
//
////                showDialog9(br(AdapterView<Beleska> adapter,
////                          item.getItemId()));
//                showDialog9(String.valueOf(item.getItemId()));
//
//                return true;
//        }
//        return false;
//    }

//    public int br(AdapterView<?> parent,
//             int index){
//        Beleska bele=(Beleska) parent.getAdapter().getItem(index);
//        return bele.getBeleska_id();
//    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

   // @RequiresApi(api = Build.VERSION_CODES.P)
    private void CreateMenu(Menu menu) {
        // menu.setQwertyMode(true);

    getMenuInflater().inflate(R.menu.main_menu,menu);
    //menu.setGroupDividerEnabled(true);

//        menu.add(0,0,0,"Lista beleški");
//
//        MenuItem mnu1 = menu.add(0, 1, 1, "Dodaj belešku");
//        {
//
//            //   mnu1.setAlphabeticShortcut('a');
////               mnu1.setIcon(R.mipmap.ic_launcher);
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
//
//
////        menu.add(0,4,4,"Lista beleški");
////        menu.add(0,5,5,"Login");
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
                        Toast.LENGTH_SHORT).show();
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


    private void showDialog9(final String beleska_id){
        new AlertDialog.Builder(this)
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
                                    Intent myIntent = new Intent(getBaseContext(), MainActivity.class);

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


}


