package com.metropolitan.beleske;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Login extends AppCompatActivity {

    DatabaseHelper db;
    EditText passw;
    Button login;
    TextView message;
    TextView nagovestaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.notes_n4_32x32);
//        actionBar.setLogo(R.drawable.);
//        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db = new DatabaseHelper(this);

//        List<Sifra> sifre3 = db.getAllSifre();
//        if(sifre3.isEmpty()){
//            Intent myIntent = new Intent(getBaseContext(), ListaBeleski.class);
//            startActivity(myIntent);
//            finish();
//        }


        passw = (EditText)findViewById(R.id.passw) ;
        message = (TextView)findViewById(R.id.message) ;
//        nagovestaj = (TextView)findViewById(R.id.link) ;
        login = (Button) findViewById(R.id.login) ;


//        nagovestaj.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getBaseContext(), "Gladni zeka skace", Toast.LENGTH_LONG).show();
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sifra s = db.login(passw.getText().toString());
//                Log.e("s",s.toString());
                if(s.getSifra()==null){
                    message.setText("Šifra nije validna");
                    passw.setText("");
                }else{
                    message.setText("");
                    Intent myIntent = new Intent(getBaseContext(), ListaBeleski.class);
                    startActivity(myIntent);
                    finish();  // sprecava back dugme da ponovo otvori aktivnost
                }
            }
        });


    }

//    @Override
//    public void onBackPressed() {
//        moveTaskToBack(true);
////        return;
//    }

}
