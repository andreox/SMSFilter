package com.example.smsfilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static LoginActivity instance ;
    private FirebaseAuth mAuth ;
    private DBHelper db ;
    private String str_email = "";
    private String str_pswd = "";

    public static LoginActivity getInstance() { return instance ;}

    @Override
    public void onStart() {
        super.onStart(); ;
        instance = this ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = makeDBHelper(this) ;
        mAuth = FirebaseAuth.getInstance() ;
        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress) ;
        EditText pswd = (EditText) findViewById(R.id.editTextTextPassword) ;
        Button login = (Button) findViewById(R.id.button3) ;
        Button register = (Button) findViewById(R.id.button4) ;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getApplicationContext(), RegisterActivity.class) ;
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_email = email.getText().toString().trim();
                str_pswd = pswd.getText().toString().trim() ;
                Bundle bundle = new Bundle() ;
                bundle.putString("user_email",str_email);

                mAuth.signInWithEmailAndPassword(str_email, str_pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if ( task.isSuccessful()) {

                            Bundle bundle = new Bundle() ;
                            bundle.putString("user_email",str_email);
                            if ( db.checkIfEmpty()) {
                                Intent intent_empty = new Intent( getApplicationContext(), LoadDBActivity.class) ;
                                intent_empty.putExtras(bundle) ;
                                startActivity(intent_empty) ;
                            }
                            else {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                        }

                        else {
                            Toast.makeText(getApplicationContext(), "Email o Password scorretti. Riprova", Toast.LENGTH_SHORT).show() ;
                        }
                    }
                });
            }
        });
    }

    public String getStr_email() { return str_email ;}
    public String getStr_pswd() { return  str_pswd ;}
    public DBHelper makeDBHelper(Context context) { return new DBHelper(context) ;}
}