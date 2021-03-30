package com.example.smsfilter ;
import android.database.Cursor;
import android.test.mock.MockContentResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class UnitTest_CancellazioneNumero {

    @Mock
    public MockContentResolver mcr ;

    private CancellazioneNumero cn ;

    @Mock
    Cursor c ;

    /*@Rule
    public ActivityScenarioRule<CancellazioneNumero> activityRule
            = new ActivityScenarioRule<>(CancellazioneNumero.class);b*/

    @Before
    public void insertContactList() {

        cn = spy(CancellazioneNumero.getInstance()) ;

    }

    @Test
    public void test() {

        ArrayList<String> list1 = new ArrayList<String>() ;
        ArrayList<String> list2 = new ArrayList<String>() ;
        list1.add("Alessio") ;
        list1.add("Pino") ;
        list2.add("03") ;
        list2.add("04") ;

        NNClass nn = new NNClass( list2, list1) ;
        doReturn(nn).when(cn).loadContacts() ;

        cn.setLists(cn.loadContacts()) ;

        assertEquals(list1,cn.getNomi()) ;
        assertEquals(list2,cn.getNumeri()) ;

    }
}
