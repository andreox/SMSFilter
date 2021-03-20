package com.example.smsfilter;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

                String str_email = email.getText().toString().trim();
                String str_pswd = pswd.getText().toString().trim() ;
                mAuth.signInWithEmailAndPassword(str_email, str_pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if ( task.isSuccessful()) {

                            Bundle bundle = new Bundle() ;
                            bundle.putString("user_email",str_email);
                            Intent intent = new Intent( getApplicationContext(), MainActivity.class) ;

                            intent.putExtras(bundle) ;
                            startActivity(intent);

                        }
                    }
                });
            }
        });
    }
}