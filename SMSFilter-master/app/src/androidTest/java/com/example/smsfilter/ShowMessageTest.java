package com.example.smsfilter;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

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
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);


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

        int position = 1 ;
        MainActivity ma = MainActivity.getInstance() ;
        onData(anything()).inAdapterView(withId(R.id.listView1)).atPosition(position).perform(click());

        intended(allOf(
                hasComponent(hasShortClassName(".DisplayMessage")),
                toPackage("com.example.smsfilter"),
                hasExtra(ID, position+1)));

    }


}
