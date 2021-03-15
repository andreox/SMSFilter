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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class InserimentoNumero extends AppCompatActivity {

    private Spinner contact_list ;
    private ArrayList<String> nomi ;
    private ArrayList<String> numeri ;
    private ArrayAdapter<String> dataAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_numero);
        DBHelper db = new DBHelper(this) ;
        contact_list = (Spinner) findViewById(R.id.spinner) ;
        nomi = new ArrayList<String>() ;
        numeri = new ArrayList<String>() ;
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nomi);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contact_list.setAdapter(dataAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);

        }

        else {

            loadContacts();
        }

        EditText numero = (EditText) findViewById(R.id.editTextPhone) ;
        EditText nome = (EditText) findViewById(R.id.editTextTextPersonName) ;

        Button btn = (Button) findViewById(R.id.button) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String ph_number = numero.getText().toString() ;
                //String cont_name = nome.getText().toString();
                String cont_name = (String) contact_list.getSelectedItem();
                int index = nomi.indexOf(cont_name) ;
                String ph_number = numeri.get(index) ;

                try {

                    nome.setText(cont_name);
                    numero.setText(ph_number);

                    try {
                        if (db.insertContact(ph_number, cont_name)) {

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        loadContacts();
    }

    private void loadContacts() {

        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        if (cursor.moveToFirst()){
            do {

                String id = cursor.getString( cursor.getColumnIndex(ContactsContract.Contacts._ID)) ;
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) ;
                System.out.println(name) ;
                nomi.add(name);
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) ;

                if ( hasPhoneNumber > 0 ) {

                    Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ",
                            new String[]{id}, null) ;

                    while ( cursor2.moveToNext()) {
                        String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ;
                        numeri.add(phoneNumber) ;
                    }
                    cursor2.close() ;
                }

            } while( cursor.moveToNext()) ;
        }
        cursor.close() ;
        dataAdapter.notifyDataSetChanged();

    }

}