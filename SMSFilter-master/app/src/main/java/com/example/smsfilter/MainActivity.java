package com.example.smsfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lw_obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mydb = new DBHelper(this) ;

        mydb.insertContact("3922858891", "Alessio") ;
        mydb.insertContact("3356541039", "Eugenia") ;
        mydb.insertContact("3453586382", "Vincenzo") ;
        mydb.insertMessage("3453586382", "Belllaaaaaaaaa") ;
        mydb.insertMessage("3922858891", "AOOOOOOOOOOOOOOOOO") ;

        ArrayList msgs_list = mydb.getAllMessages() ;
        ArrayAdapter adapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, msgs_list) ;
        lw_obj = (ListView) findViewById(R.id.listView1) ;
        lw_obj.setAdapter(adapt);

        lw_obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("Mess_ID", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),DisplayMessage.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });


    }

}