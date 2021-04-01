package com.example.smsfilter;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestUIRegister {

    @Rule
    public ActivityTestRule<RegisterActivity> rule = new ActivityTestRule<>(RegisterActivity.class) ;

    @Test
    public void testFieldsRegister() {

        RegisterActivity ra = RegisterActivity.getInstance() ;
        String nome = "Alessio" ;
        String email = "alessio@prova.com" ;
        String pwd = "prova123" ;

        User u = new User(nome,email,pwd) ;

        onView(withId(R.id.editTextTextPersonName3)).perform(typeText(nome)) ;
        onView(withId(R.id.editTextTextEmailAddress2)).perform(typeText(email)) ;
        onView(withId(R.id.editTextTextPassword2)).perform(typeText(pwd));
        Espresso.closeSoftKeyboard() ;
        onView(withId(R.id.button5)).perform(click()) ;
        assertEquals(email,ra.getStr_email()) ;
        assertEquals(nome,ra.getStr_name()) ;
        assertEquals(pwd,ra.getStr_pwd());

    }
}
