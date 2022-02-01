package com.example.smsfilter;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                String telefono = "" ;
                String corpo = "";
                String nome = "" ;
                if ( rs != null && rs.getColumnCount() != 0 ) {
                    rs.moveToFirst();
                    try {
                        nome = rs.getString(rs.getColumnIndex("Nome")) ;
                        System.out.println("Nome : "+nome) ;
                        telefono = rs.getString(rs.getColumnIndex("Telefono"));
                        System.out.println("Telefono : "+telefono) ;
                        corpo = rs.getString(rs.getColumnIndex("Body"));
                        System.out.println("Body : "+corpo) ;
                    } catch( CursorIndexOutOfBoundsException e ) {
                        e.printStackTrace();
                        System.out.println("Il contatto che ha inviato questo messaggio è stato eliminato dalla lista contatti, dunque non è più possibile visualizzarne il contenuto. Riaggiungerlo per visualizzare il messaggio.");
                        Toast.makeText(getApplicationContext(), "Il contatto che ha inviato questo messaggio è stato eliminato dalla lista contatti, dunque non è più possibile visualizzarne il contenuto. Riaggiungerlo per visualizzare il messaggio." , Toast.LENGTH_LONG).show();

                    }
                }

                if (!rs.isClosed())  {
                    rs.close();
                }


                number.setText((CharSequence) telefono+" "+nome) ;
                body.setText((CharSequence) corpo) ;

            }
        }
    }
}