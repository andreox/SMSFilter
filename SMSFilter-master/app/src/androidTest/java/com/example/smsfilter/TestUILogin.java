package com.example.smsfilter;

import android.os.Looper;

import androidx.test.espresso.Espresso;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class TestUILogin {

    private String email = "utente1@prova.com" ;
    private String pwd = "utente1" ;

    @Rule
    public ActivityTestRule<LoginActivity> act = new ActivityTestRule<>(LoginActivity.class) ;

    @Mock
    DBHelper db ;

    @Before
    public void setup() {

        Looper.prepare() ;
    }

    @Test
    public void verifyEmail() {

        LoginActivity la = LoginActivity.getInstance() ;/*
        doReturn(db).when(la).makeDBHelper(any(Context.class)) ;
        when(db.checkIfEmpty()).thenReturn(true) ;*/

        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(email)) ;
        Espresso.closeSoftKeyboard() ;
        onView(withId(R.id.editTextTextPassword)).perform(typeText(pwd)) ;
        Espresso.closeSoftKeyboard() ;
        onView(withId(R.id.button3)).perform(click()) ;

        assertEquals(email, la.getStr_email());

    }

    @Test
    public void verifyPassword() {

        LoginActivity la = LoginActivity.getInstance() ;

        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(email)) ;
        Espresso.closeSoftKeyboard() ;
        onView(withId(R.id.editTextTextPassword)).perform(typeText(pwd)) ;
        Espresso.closeSoftKeyboard() ;
        onView(withId(R.id.button3)).perform(click()) ;

        assertEquals(pwd, la.getStr_pswd());


    }

}
