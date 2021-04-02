package com.example.smsfilter;

import android.content.Context;
import android.os.Looper;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InserimentoContattoDBTest {

    @Mock
    private DBHelper db ;

    @Before
    public void setup() {

        Looper.prepare() ;
    }
    @Rule
    public ActivityTestRule<InserimentoNumero> activityRule
            = new ActivityTestRule<>(InserimentoNumero.class);

    @Test
    public void testInsertContact() {

        InserimentoNumero in = spy(InserimentoNumero.getInstance()) ;
        doReturn(db).when(in).makeDBHelper(any(Context.class)) ;

        when(db.insertContact(any(String.class),any(String.class))).thenReturn(true) ;

        String numero = "333" ;
        String nome = "cont_test_inserimento" ;

        assertEquals(in.insertContactInCloud(numero,nome),true) ;

    }
}
