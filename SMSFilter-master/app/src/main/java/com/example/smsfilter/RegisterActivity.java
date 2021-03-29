package com.example.smsfilter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static RegisterActivity instance ;
    private static final String TAG = "";
    private FirebaseAuth mAuth ;
    private FirebaseFirestore cloud_db ;
    private String str_name ;
    private String str_email ;
    private String str_pwd ;

    public static RegisterActivity getInstance() { return instance ;}
    @Override
    public void onStart() {
        super.onStart() ;
        instance = this ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cloud_db = FirebaseFirestore.getInstance() ;
        mAuth = FirebaseAuth.getInstance() ;
        EditText name = (EditText) findViewById(R.id.editTextTextPersonName3) ;
        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress2) ;
        EditText pwd = (EditText) findViewById(R.id.editTextTextPassword2) ;
        Button register = (Button) findViewById(R.id.button5) ;

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_name = name.getText().toString().trim() ;
                str_email = email.getText().toString().trim() ;
                str_pwd = pwd.getText().toString().trim() ;
                mAuth.createUserWithEmailAndPassword(str_email,str_pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful() && checkName(str_name) && checkPassword(str_pwd)) {

                                User user = new User(str_name, str_email, str_pwd);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                                addUserToCloudDB(str_email, str_name);
                                Toast.makeText(RegisterActivity.this, "Registrazione effettuata", Toast.LENGTH_SHORT);
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);


                            } else {
                                Toast.makeText(getApplicationContext(), "IL NOME NON DEVE CONTENERE NUMERI E LA PASSWORD DEVE AVERE ALMENO 6 CARATTERI", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                );
            }
        });
    }

    void addUserToCloudDB( String email, String name) {

        Map<String, Object> user = new HashMap<>();
        user.put("name", name);

        cloud_db.collection("Utenti").document(email)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    boolean checkName( String nome ) {

        if ( nome.matches(".*\\d.*") ) {
            return false ;
        }

        return true ;
    }

    boolean checkPassword( String pwd ) {

        if ( pwd.length() < 6 ) {

            return false ;

        }

        return true ;
    }

    public String getStr_name() { return str_name ; }
    public String getStr_email() { return str_email ; }
    public String getStr_pwd() { return str_pwd ; }

}