package com.example.smsfilter;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
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
public class UITest_CancellazioneNumero {

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_CONTACTS);
    private CancellazioneNumero cn ;

    @Rule
    public ActivityTestRule<CancellazioneNumero> activityRule = new ActivityTestRule<>(CancellazioneNumero.class) ;

    @Before
    public void before_test() {
        cn = CancellazioneNumero.getInstance() ;
        cn.nomi.add("Antonio") ;
        cn.numeri.add("02") ;
    }

    @After
    public void after_test() {

        cn.nomi.remove("Antonio") ;
        cn.numeri.remove("02") ;
    }

    @Test
    public void testEditTextDeleteContact() {

        String contact = "Antonio" ;
        String number = "02" ;

        onView(withId(R.id.spinner2)).perform(click()) ;

        onData(allOf(is(instanceOf(String.class)), is(contact))).perform(click());

        onView(withId(R.id.button2)).perform(click()) ;

        onView(withId(R.id.editTextTextPersonName2)).check(matches(withText(number))) ;

    }

    @Test
    public void checkToast() {

        String contact = "Antonio" ;
        String number = "02" ;

        onView(withId(R.id.spinner2)).perform(click()) ;

        onData(allOf(is(instanceOf(String.class)), is(contact))).perform(click());

        onView(withId(R.id.button2)).perform(click()) ;

        onView(withText(startsWith("CONTATTO INESISTENTE"))).
                inRoot(withDecorView(
                        not(is(activityRule.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));

    }


}
