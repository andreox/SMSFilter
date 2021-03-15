package com.example.smsfilter;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class InserimentoNumero extends AppCompatActivity {

    private Spinner contact_list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_numero);
        DBHelper db = new DBHelper(this) ;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, PackageManager.PERMISSION_GRANTED);
       /* BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        EditText numero = (EditText) findViewById(R.id.editTextPhone) ;
        EditText nome = (EditText) findViewById(R.id.editTextTextPersonName) ;

        Button btn = (Button) findViewById(R.id.button) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ph_number = numero.getText().toString() ;
                String cont_name = nome.getText().toString();

                try {

                    Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE},
                            "DISPLAY_NAME = '" + cont_name + "'", null, null);

                    cursor.moveToFirst();
                    numero.setText(cursor.getString(0));

                    try {
                        if (db.insertContact(cursor.getString(0), cont_name)) {

                            numero.setText((CharSequence) cursor.getString(0));
                            Toast.makeText(getApplicationContext(), "CONTATTO INSERITO", Toast.LENGTH_SHORT).show();

                        }
                    } catch (SQLiteConstraintException e) {

                        Toast.makeText(getApplicationContext(), "CONTATTO GIA' INSERITO", Toast.LENGTH_SHORT).show();

                    }
                }

                catch ( Exception e ) {

                    Toast.makeText(getApplicationContext(), "CONTATTO NON PRESENTE", Toast.LENGTH_SHORT).show();

                }

                System.out.println( db.getAllContacts() ) ;
            }
        });
    }

}