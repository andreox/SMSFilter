package com.example.smsfilter;

import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InserimentoNumero extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_numero);
        DBHelper db = new DBHelper(this) ;

        EditText numero = (EditText) findViewById(R.id.editTextPhone) ;
        EditText nome = (EditText) findViewById(R.id.editTextTextPersonName) ;

        Button btn = (Button) findViewById(R.id.button) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ph_number = numero.getText().toString() ;
                String cont_name = nome.getText().toString();

                if ( ph_number != null ) { //Questo IF è da rivedere per il Testing ( Approfondibile )

                    try {
                        db.insertContact(ph_number, cont_name);
                    }

                    catch( SQLiteConstraintException e ) {

                        Toast.makeText(getApplicationContext(), "CONTATTO GIA' INSERITO", Toast.LENGTH_SHORT).show();

                    }
                }

                System.out.println( db.getAllContacts() ) ;
            }
        });
    }
}