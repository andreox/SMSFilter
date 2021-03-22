package com.example.smsfilter;

import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessage extends AppCompatActivity {

    private DBHelper mydb ;
    TextView number ;
    TextView body ;
    int id_To_Update = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        number = (TextView) findViewById(R.id.textView3) ;
        body = (TextView) findViewById(R.id.textView2) ;

        mydb = new DBHelper(this) ;
        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {

            int Value = extras.getInt("Mess_ID");
            if ( Value > 0 ) {

                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String telefono = rs.getString(rs.getColumnIndex("Telefono")) ;
                String corpo = rs.getString(rs.getColumnIndex("Body")) ;

                if (!rs.isClosed())  {
                    rs.close();
                }

                number.setText((CharSequence) telefono) ;
                body.setText((CharSequence) corpo) ;

            }
        }
    }
}