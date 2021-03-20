package com.example.smsfilter;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class InserimentoNumero extends AppCompatActivity {

    private Spinner contact_list ;
    private String email = "" ;
    private FirebaseFirestore cloud_db ;
    private ArrayList<String> nomi ;
    private ArrayList<String> numeri ;
    private ArrayAdapter<String> dataAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_numero);


        cloud_db = FirebaseFirestore.getInstance() ;
        Bundle email_bundle = getIntent().getExtras() ;

        if ( email_bundle != null ) {

            email = email_bundle.getString("user_email") ;
        }
        DBHelper db = new DBHelper(this) ;
        System.out.println(db.getAllContacts()) ;
        System.out.println(db.getAllMessages()) ;

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

                            HashMap<String,Object> contatto = new HashMap<>() ;
                            contatto.put("Numero",ph_number) ;

                            cloud_db.collection("Utenti").document(email).collection("Contatti").document(cont_name).set(contatto) ;

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