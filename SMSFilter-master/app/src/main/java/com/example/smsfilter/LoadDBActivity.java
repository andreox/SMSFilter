package com.example.smsfilter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoadDBActivity extends AppCompatActivity {

    private FirebaseFirestore cloud_db ;
    private DBHelper db ;
    private String email ;
    private boolean tr = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_d_b);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar) ;
        pb.setVisibility(View.VISIBLE);
        db = new DBHelper(this) ;
        Bundle bundle_email = getIntent().getExtras() ;
        if ( bundle_email != null )
            email = bundle_email.getString("user_email") ;

        cloud_db = FirebaseFirestore.getInstance() ;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    fillDB() ;
                    Thread.sleep(5000);
                    Bundle bundle = new Bundle() ;
                    bundle.putString("user_email",email);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();




    }

    public void fillDB() {

        cloud_db.collection("Utenti").document(email).collection("Contatti")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.insertContact(document.getData().get("Numero").toString(), document.getId()) ;
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            System.out.println("Task not successful1") ;
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

        cloud_db.collection("Utenti").document(email).collection("Messaggi")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.insertMessage(document.getData().get("Telefono").toString(), document.getData().get("Corpo").toString()) ;
                                //msgs_list.add(document.getData().get("Corpo").toString()) ;
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            System.out.println("Task not successful2") ;
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

    }

}