package com.metropolitan.beleske;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "baza.db";
    public static final String TABLE_BELESKE = "beleske";
    public static final String TABLE_SIFRE = "sifre";

    //tabela beleske
    public static final String COL_BELESKA_ID = "beleska_id";
    public static final String COL_NASLOV = "naslov";
    public static final String COL_TEKST = "tekst";
    public static final String COL_DATUM = "datum";
    public static final String COL_DATUM_KREIRANO = "datumkreirano";
    public static final String COL_STATUS = "status";

    //tabela sifre
    public static final String COL_SIFRA_ID = "sifra_id";
    public static final String COL_SIFRA = "sifra";

    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // copydatabase();
            createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        db.execSQL("create table " + TABLE_NAME1  + " (id INTEGER PRIMARY KEY AUTOINCREMENT,ime TEXT, prezime TEXT, datum TEXT, vreme TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SIFRE  + " (sifra_id INTEGER PRIMARY KEY AUTOINCREMENT, sifra TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BELESKE + " (beleska_id INTEGER PRIMARY KEY AUTOINCREMENT, naslov TEXT, tekst TEXT, datum TEXT, datumkreirano TEXT, status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SIFRE);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BELESKE);

//        onCreate(db);
    }



    public SQLiteDatabase myDataBase;




    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(!dbexist) {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String DB_PATH ="/data/data/"+mContext.getPackageName()+"/databases/";
            String myPath = DB_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        String DB_PATH ="/data/data/"+mContext.getPackageName()+"/databases/";
        InputStream myinput = mContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public List<Beleska> getAllBeleske() {
        // SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT beleska_id, naslov, tekst, datum, datumkreirano, status FROM beleske";
        Cursor cursor = db.rawQuery(query, null);
        List<Beleska> list = new ArrayList<Beleska>();
        while(cursor.moveToNext()) {
            Beleska beleska = new Beleska();
            beleska.setBeleska_id(cursor.getInt(0));
            beleska.setNaslov(cursor.getString(1));
            beleska.setTekst(cursor.getString(2));
            beleska.setDatum(cursor.getString(3));
            beleska.setDatumkreirano(cursor.getString(4));
            beleska.setStatus(cursor.getString(5));
            list.add(beleska);
        }
        db.close();
        return list;
    }

    public boolean addBeleska(String naslov, String tekst, String datum, String datumkreirano, String status) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(COL_NASLOV, naslov);
        value.put(COL_TEKST, tekst);
        value.put(COL_DATUM, datum);
        value.put(COL_DATUM_KREIRANO, datumkreirano);
        value.put(COL_STATUS, status);

        long result = db.insert(TABLE_BELESKE, null, value);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateBeleska(int id,String naslov,String tekst, String datum, String status ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //  contentValues.put(COL_INDEKS,broj_indeksa);
        contentValues.put(COL_NASLOV,naslov);
        contentValues.put(COL_TEKST,tekst);
        contentValues.put(COL_DATUM,datum);
        contentValues.put(COL_STATUS,status);

        long result1 = db.update(TABLE_BELESKE, contentValues, COL_BELESKA_ID+"="+id,null);
        db.close();
        if(result1 == -1)
            return false;
        else
            return true;


    }


    public Integer deleteBeleska (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //  return db.delete(TABLE_NAME, "broj_indeksa = ?",new String[] {indeks}); //radi ali mora se promeniti tip podatka da bude string

        int a = db.delete(TABLE_BELESKE, COL_BELESKA_ID+"="+id,null);

        return a ;
    }

    public Integer deleteAllBeleske () {
        SQLiteDatabase db = this.getWritableDatabase();
        //  return db.delete(TABLE_NAME, "broj_indeksa = ?",new String[] {indeks}); //radi ali mora se promeniti tip podatka da bude string

        int a = db.delete(TABLE_BELESKE, null,null);

        return a ;
    }





    public List<Sifra> getAllSifre() {
        // SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT sifra_id, sifra FROM sifre";
        Cursor cursor = db.rawQuery(query, null);
        List<Sifra> list = new ArrayList<Sifra>();
        while(cursor.moveToNext()) {
            Sifra sifra = new Sifra();
            sifra.setSifra_id(cursor.getInt(0));
            sifra.setSifra(cursor.getString(1));
            list.add(sifra);
        }
        db.close();
        return list;
    }





    public boolean addSifra(String pass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(COL_SIFRA, pass);


        long result = db.insert(TABLE_SIFRE, null, value);
        if(result == 0) {
            return false;
        } else {
            return true;
        }
    }




    //  return db.delete(TABLE_NAME, "broj_indeksa = ?",new String[] {indeks}); //radi ali mora se promeniti tip podatka da bude string
//        String query = "SELECT * FROM izvestaji WHERE klijent_id = \"" + id + "\"";
//        Cursor cursor = db.rawQuery(query, null);


    public boolean updateSifra(String oldpass,String newpass ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //  contentValues.put(COL_INDEKS,broj_indeksa);
        contentValues.put(COL_SIFRA,newpass);


        //long result1 = db.update(TABLE_SIFRE, contentValues, COL_SIFRA_ID+"="+1,null);
        long result1 = db.update(TABLE_SIFRE, contentValues, COL_SIFRA + "=?",new String[] {oldpass});
        //long result1 = db.update(TABLE_SIFRE, contentValues, COL_SIFRA + "=?",null);
       // String query = "UPDATE SIFRE SET sifra=? WHERE sifra = \"" + oldpass + "\"";
        //db.execSQL(query);
        db.close();
        if(result1 == 0)
            return false;
        else
            return true;


    }


    public boolean deleteSifra(String pass ) {

        SQLiteDatabase db = this.getWritableDatabase();

       // long result1 = db.delete(TABLE_SIFRE, COL_SIFRA_ID+"="+1,null);
         long result1 = db.delete(TABLE_SIFRE, COL_SIFRA + "= ?",new String[] {pass});
        db.close();
        if(result1 == 0)
            return false;
        else
            return true;


    }


    public Sifra login(String passw) {
        // SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT sifra_id, sifra FROM sifre WHERE sifra = \"" + passw + "\"";
        Cursor cursor = db.rawQuery(query, null);
        Sifra sifra = new Sifra();
        while(cursor.moveToNext()) {

            sifra.setSifra_id(cursor.getInt(0));
            sifra.setSifra(cursor.getString(1));

        }
        db.close();
        return sifra;
    }


}
