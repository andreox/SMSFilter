package com.example.smsfilter;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowMessageTest {

    private final String ID = "Mess_ID" ;
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp() {
        Intents.init() ;
    }

    @After
    public void tearDown() {
        Intents.release() ;
    }

    @Test
    public void verifyIdDisplayMessage() {
        //onView(withId(R.id.listView1)).perform(click()) ;

        MainActivity ma = MainActivity.getInstance() ;
        ma.msgs_list.add("Messaggio") ;
        ma.adapt.notifyDataSetChanged();
        onData(anything()).inAdapterView(withId(R.id.listView1)).atPosition(0).perform(click());

        intended(allOf(
                hasComponent(hasShortClassName(".DisplayMessage")),
                toPackage("com.example.smsfilter"),
                hasExtra(ID, 1)));

    }


}