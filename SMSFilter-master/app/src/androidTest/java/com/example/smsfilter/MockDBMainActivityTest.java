package com.example.smsfilter ;

import android.content.Context;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockDBMainActivityTest {

    @Mock
    private DBHelper db ;

   @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void mainActivityShowMsgsList() {

        MainActivity ma = spy(MainActivity.getInstance()) ;
        doReturn(db).when(ma).makeDBHelper(any(Context.class)) ;
        ArrayList<String> messaggi = new ArrayList<>() ;
        messaggi.add("messaggio1") ;
        messaggi.add("messaggio2") ;
        messaggi.add("messaggio3") ;

        when(db.getAllMessages()).thenReturn(messaggi) ;

        assertEquals(ma.getMsgs_list(), messaggi);

    }

}
