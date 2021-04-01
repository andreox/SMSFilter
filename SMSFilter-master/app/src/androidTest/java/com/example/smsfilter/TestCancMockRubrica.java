package com.example.smsfilter;

import android.test.mock.MockContentResolver;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class TestCancMockRubrica  {

    @Mock
    public MockContentResolver mcr ;

    private CancellazioneNumero cn ;


    @Rule
    public ActivityTestRule<CancellazioneNumero> activityRule
            = new ActivityTestRule<>(CancellazioneNumero.class);

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
        doReturn(nn).when(cn).loadContacts() ; //Mock Rubrica

        cn.setLists(cn.loadContacts()) ;

        assertEquals(list1,cn.getNomi()) ;
        assertEquals(list2,cn.getNumeri()) ;


    }
}
