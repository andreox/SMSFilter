package com.example.smsfilter;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.test.mock.MockContentResolver;
import android.util.Log;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TestCancMockRubrica {

    @Rule
    public ActivityScenarioRule<CancellazioneNumero> activityRule
            = new ActivityScenarioRule<>(CancellazioneNumero.class);

    @Before
    public void insertContactList() {

        CancellazioneNumero cn = CancellazioneNumero.getInstance() ;
        MockContentResolver mcr = new MockContentResolver(cn.getApplicationContext()) ;
        ContentValues content = new ContentValues(4) ;
        ContentValues content2 = new ContentValues(4) ;
        content.put(ContactsContract.Contacts._ID, "1") ;
        content.put(ContactsContract.Contacts.DISPLAY_NAME,"Pino") ;
        content2.put(ContactsContract.CommonDataKinds.Phone.CONTACT_ID, "1" ) ;
        content2.put( ContactsContract.CommonDataKinds.Phone.NUMBER, "333" ) ;
        Uri uri = mcr.insert( ContactsContract.Contacts.CONTENT_URI,content) ;
        Uri uri2 = mcr.insert( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, content2) ;

        Cursor cursor=mcr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        if ( cursor.moveToFirst() ) {
            String id = cursor.getString( cursor.getColumnIndex(ContactsContract.Contacts._ID)) ;
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) ;
            Log.d("",id) ;
            Log.d("",name) ;
            Log.d("","SONO QUIIIIIIIIIIIIIII") ;
        }
    }

    @Test
    public void test() {

        assertEquals(true,true) ;
    }
}
