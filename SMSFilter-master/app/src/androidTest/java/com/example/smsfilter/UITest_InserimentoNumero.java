package com.example.smsfilter;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest_InserimentoNumero {

    private String contact ;
    private InserimentoNumero in ;

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_CONTACTS);
    @Rule public GrantPermissionRule permissionRule2 = GrantPermissionRule.grant(android.Manifest.permission.WRITE_CONTACTS);

    @Rule
        public IntentsTestRule<InserimentoNumero> activityRule
                = new IntentsTestRule<>(InserimentoNumero.class);


        public void setString() { contact = "Pierluigi" ; }

        @Before
        public void inserisci_contatto() {


            in = InserimentoNumero.getInstance() ;
            in.nomi.add("Pierluigi") ;
            in.numeri.add("10") ;

        }

        @After
        public void after_test() {

            in.nomi.remove("Pierluigi") ;
            in.numeri.remove("10") ;
        }

    @Test
    public void testInserimentoNumero() {

        setString() ;

        onView(withId(R.id.spinner)).perform(click()) ;

        onData(allOf(is(instanceOf(String.class)), is(contact))).perform(click());

        onView(withId(R.id.button)).perform(click()) ;

        onView(withId(R.id.editTextTextPersonName)).check(matches(withText(contact))) ;


    }

    @Test
    public void checkToast() {

        setString() ;

        onView(withId(R.id.spinner)).perform(click()) ;

        onData(allOf(is(instanceOf(String.class)), is(contact))).perform(click());

        onView(withId(R.id.button)).perform(click()) ;

        onView(withText(startsWith("CANNOT INSERT CONTACT IN CLOUD"))).
                inRoot(withDecorView(
                        not(is(activityRule.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));

    }


}


