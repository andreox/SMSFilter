package com.example.smsfilter;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest_InserimentoNumero {

        private String contact ;

        @Rule
        public ActivityTestRule<InserimentoNumero> activityRule
                = new ActivityTestRule<>(InserimentoNumero.class);


        public void setString() { contact = "Alessio" ; }

        @Test
        public void testInserimentoNumero() {
            // Type text and then press the button.

            setString() ;

            onView(withId(R.id.spinner)).perform(click()) ;

            onData(allOf(is(instanceOf(String.class)), is(contact))).perform(click());

            onView(withId(R.id.button)).perform(click()) ;

            onView(withId(R.id.editTextTextPersonName)).check(matches(withText(contact))) ;
        }

}


