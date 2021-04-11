package com.example.smsfilter ;

import android.content.Context;

import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
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

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECEIVE_SMS);

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

   MainActivity ma ;
   ArrayList<String> messaggi ;

   @Before
   public void before_test() {

       ma = spy(MainActivity.getInstance()) ;
       messaggi = new ArrayList<>() ;
       messaggi.add("messaggio1") ;
       messaggi.add("messaggio2") ;
       messaggi.add("messaggio3") ;

   }

    @Test
    public void mainActivityShowMsgsList() {

        doReturn(db).when(ma).makeDBHelper(any(Context.class)) ;

        when(db.getAllMessages()).thenReturn(messaggi) ;

        assertEquals(ma.getMsgs_list(), messaggi);

    }

}
