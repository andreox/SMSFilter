package com.example.smsfilter;

import android.content.Context;

import androidx.test.rule.ActivityTestRule;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;


public class MockDBTest extends EasyMockSupport {

    @Rule
    public EasyMockRule rule2 = new EasyMockRule(this);


    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule(MainActivity.class);

    DBHelper db ;
    @Test
    public void testGetMessage() {


        MainActivity ma = rule.getActivity() ;

        db = partialMockBuilder(DBHelper.class).withConstructor(Context.class).withArgs(ma.getApplicationContext()).createMock() ;

        ArrayList<String> messaggi = new ArrayList<String>() ;
        messaggi.add("Messaggio1") ;
        messaggi.add("Messaggio2") ;
        messaggi.add("Messaggio3") ;
        System.out.println(messaggi) ;

        EasyMock.expect(db.getAllMessages()).andReturn(messaggi) ;
        EasyMock.replay(db) ;

        System.out.println(ma.getMsgs_list()) ;
        Assert.assertEquals(ma.getMsgs_list(), messaggi) ;

        EasyMock.verify(db) ;
    }
}
