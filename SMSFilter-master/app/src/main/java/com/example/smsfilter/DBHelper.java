package com.example.smsfilter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String SMS_DB = "SMSDB.db";


    public DBHelper(Context context) {

        super(context, SMS_DB, null,1 ) ;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL( "CREATE TABLE Persone ( Telefono VARCHAR(15) NOT NULL, Nome VARCHAR(20) NOT NULL, PRIMARY KEY(Telefono));");
        db.execSQL( "CREATE TABLE Messaggi ( Mess_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " Telefono VARCHAR(15) NOT NULL," +
                " Body VARCHAR(200) , FOREIGN KEY(Telefono) REFERENCES Persone(Telefono));");


    }
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}

    public boolean insertMessage(String phone, String body) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues() ;
        contentValues.put("Telefono", phone);
        contentValues.put("Body", body);
        db.insert("Messaggi", null, contentValues);
        return true;

    }

    public boolean insertContact(String phone, String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues() ;
        contentValues.put("Telefono", phone);
        contentValues.put("Nome", name);
        if ( (db.insert("Persone", null, contentValues)) == -1 ) throw new SQLiteConstraintException();

        return true;

    }

    public int deleteContact (String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Persone",
                "Telefono = ? ",
                new String[]{phone});

    }

    public int deleteMessages ( String phone ) {
        SQLiteDatabase db = this.getWritableDatabase() ;
        return db.delete( "Messaggi" ,
                "Telefono = ? ",
                new String[]{phone}) ;

    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select M.Telefono AS Telefono, M.Body AS Body, P.Nome AS Nome from Messaggi M, Persone P where M.Mess_ID="+id+" AND P.Telefono = M.Telefono", null );
        return res;
    }
    public ArrayList<String> getAllContacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Persone", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("Telefono")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllMessages() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Messaggi", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("Body")));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean checkIfEmpty() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM Persone", null);
        Cursor mCursor2 = db.rawQuery("SELECT * FROM Messaggi",null);

        if (!mCursor.moveToFirst() && !mCursor2.moveToFirst()) {
            System.out.println("DB VUOTO!") ;
            return true;
        }

        return false ;
    }

    DBHelper makeDBHelper( Context context ) {
        return new DBHelper(context) ;
    }
}
