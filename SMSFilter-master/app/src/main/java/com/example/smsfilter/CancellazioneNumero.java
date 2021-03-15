package com.example.smsfilter;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class CancellazioneNumero extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellazione_numero);


        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        DBHelper db = new DBHelper(this ) ;

        EditText numero = (EditText) findViewById(R.id.editTextTextPersonName2) ;
        Button btn = (Button) findViewById(R.id.button2) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ph_number = numero.getText().toString() ;

                if ( ph_number != null ) {

                    int value = db.deleteContact(ph_number);
                    if ( value != 0 ) {

                            Toast.makeText(getApplicationContext(), "CONTATTO ELIMINATO", Toast.LENGTH_SHORT).show();
                    }

                    else {

                        Toast.makeText(getApplicationContext(), "CONTATTO INESISTENTE", Toast.LENGTH_SHORT).show();
                    }

                }

                System.out.println( db.getAllContacts() ) ;
            }
        });

        /*navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {

                    case R.id.navigation_home :
                        Intent a = new Intent(CancellazioneNumero.this,MainActivity.class);
                        startActivity(a);
                        break;

                    case R.id.navigation_dashboard :

                        Intent b = new Intent( CancellazioneNumero.this, InserimentoNumero.class) ;
                        startActivity(b);
                        break ;

                }

                return false ;
            }
        });*/
    }
}