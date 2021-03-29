package com.example.smsfilter;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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
public class UITest_CancellazioneNumero {

    @Rule
    public ActivityScenarioRule<CancellazioneNumero> activityRule = new ActivityScenarioRule<>(CancellazioneNumero.class) ;

    @Test
    public void testEditTextDeleteContact() {

        String contact = "Vincenzo" ;
        String number = "02" ;

        onView(withId(R.id.spinner2)).perform(click()) ;

        onData(allOf(is(instanceOf(String.class)), is(contact))).perform(click());

        onView(withId(R.id.button2)).perform(click()) ;

        onView(withId(R.id.editTextTextPersonName2)).check(matches(withText(number))) ;

    }


}
