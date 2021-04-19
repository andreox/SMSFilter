package com.example.smsfilter;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class InserimentoNumero extends AppCompatActivity {

    private static InserimentoNumero instance ;
    private DBHelper db ;
    private Spinner contact_list ;
    private String email = "" ;
    private FirebaseFirestore cloud_db ;
    public ArrayList<String> nomi ;
    public ArrayList<String> numeri ;
    public ArrayAdapter<String> dataAdapter ;


    public static InserimentoNumero getInstance() { return instance ; }

    @Override
    public void onStart() {
        super.onStart();
        instance = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_numero);


        cloud_db = FirebaseFirestore.getInstance() ;
        Bundle email_bundle = getIntent().getExtras() ;

        if ( email_bundle != null ) {

            email = email_bundle.getString("user_email") ;
        }
        db = makeDBHelper(this) ;
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

                String cont_name =  contact_list.getSelectedItem().toString();
                System.out.println(cont_name) ;
                int index = nomi.indexOf(cont_name) ;
                System.out.println(index) ;
                String ph_number = numeri.get(index) ;


                try {

                    nome.setText(cont_name);
                    numero.setText(ph_number);

                    try {
                        insertContactInCloud(ph_number, cont_name) ;

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
                String name_to_add = "" ;
                String number_to_add = "" ;

                name_to_add = name ;
                //nomi.add(name) ;
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) ;

                if ( hasPhoneNumber > 0 ) {

                    Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ",
                            new String[]{id}, null) ;

                    while ( cursor2.moveToNext()) {
                        String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ;
                        //numeri.add(phoneNumber) ;
                        number_to_add = phoneNumber ;
                    }
                    insertContactToList(name_to_add,number_to_add);
                    cursor2.close() ;
                }

            } while( cursor.moveToNext()) ;
        }
        cursor.close() ;
        dataAdapter.notifyDataSetChanged();

    }

    public DBHelper makeDBHelper(Context context) {
        return new DBHelper(context) ;
    }

    public boolean insertContactInCloud(String ph_number, String cont_name) {

        FirebaseFirestore cloud_db = getinst() ;
        DBHelper db = makeDBHelper(this) ;
            if (db.insertContact(ph_number, cont_name)) {


                HashMap<String, Object> contatto = new HashMap<>();
                contatto.put("Numero", ph_number);

                try {
                    cloud_db.collection("Utenti").document(email).collection("Contatti").document(cont_name).set(contatto); //commentare in fase di Testing
                } catch(IllegalArgumentException e ) {                     Toast.makeText(getApplicationContext(), "CANNOT INSERT CONTACT IN CLOUD", Toast.LENGTH_SHORT).show();
                }

                try {
                    Toast.makeText(getApplicationContext(), "CONTATTO INSERITO", Toast.LENGTH_SHORT).show();
                } catch( NullPointerException e ) {}

                return true;

            }

        return false ;
    }

    public void insertContactToList(String contact, String numero ) {
        nomi.add(contact) ;
        numeri.add(numero) ;

    }

    public void removeContactFromList(String contact, String numero ) {
        nomi.remove(contact) ;
        numeri.remove(contact) ;
    }
    public FirebaseFirestore getinst() { return FirebaseFirestore.getInstance() ; }
}