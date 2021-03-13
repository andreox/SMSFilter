package com.example.smsfilter;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log ;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance ;
    private static final int PERMISSIONS_REQUEST_RECEIVE_SMS = 0;
    private ListView lw_obj;
    private ArrayAdapter adapt ;
    private ArrayList msgs_list ;
    DBHelper mydb;


    public static MainActivity getInstance() {

        return instance ;

    }

    @Override
    public void onStart() {
        super.onStart();
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(Manifest.permission.RECEIVE_SMS, PERMISSIONS_REQUEST_RECEIVE_SMS);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mydb = new DBHelper(this) ;

        System.out.println(mydb.getAllMessages()) ;
        System.out.println(mydb.getAllContacts()) ;
        msgs_list = mydb.getAllMessages() ;
        adapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, msgs_list) ;
        lw_obj = (ListView) findViewById(R.id.listView1) ;
        lw_obj.setAdapter(adapt);

        lw_obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = position + 1 ;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("Mess_ID", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),DisplayMessage.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ( item.getItemId() ) {

                    case R.id.navigation_dashboard :
                        Intent a = new Intent(MainActivity.this,InserimentoNumero.class);
                        startActivity(a);
                        break;

                    case R.id.navigation_notifications :

                        Intent b = new Intent( MainActivity.this, CancellazioneNumero.class) ;
                        startActivity(b);
                        break ;

                }

                return false ;
            }
        });
    }

    public void updateList(final String smsMessage, final String smsSource ) {

        mydb.insertMessage(smsSource,smsMessage);
        msgs_list.add(smsMessage) ;
        adapt.notifyDataSetChanged();

    }

    private void requestPermissions(String permission, int requestCode) {

        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {

                Toast.makeText(this, "Granting permission is necessary!", Toast.LENGTH_LONG).show();

            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{permission},
                        requestCode);

            }
        }
    }

}