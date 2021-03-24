package com.example.smsfilter;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

        private String stringToBetyped;
        private String contact ;

        @Rule
        public ActivityScenarioRule<InserimentoNumero> activityRule
                = new ActivityScenarioRule<>(InserimentoNumero.class);

        @Before
        public void initValidString() {
            // Specify a valid string.
            stringToBetyped = "Espresso";
        }

        public void setString() { contact = "Alessio" ; }
        @Test
        public void changeText_sameActivity() {
            // Type text and then press the button.

            setString() ;

            onView(withId(R.id.spinner)).perform(click()) ;

            onData(allOf(is(instanceOf(String.class)), is(contact))).perform(click());

            onView(withId(R.id.button)).perform(click()) ;

            onView(withId(R.id.editTextTextPersonName)).check(matches(withText(contact))) ;
        }

}


