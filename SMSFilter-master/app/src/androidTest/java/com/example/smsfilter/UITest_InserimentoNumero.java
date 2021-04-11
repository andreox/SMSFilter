package com.example.smsfilter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest_InserimentoNumero {

    //private static final String[] PERMISSIONS_REQUEST_READ_CONTACTS = "500" ;
    private String contact ;

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_CONTACTS);
    @Rule public GrantPermissionRule permissionRule2 = GrantPermissionRule.grant(android.Manifest.permission.WRITE_CONTACTS);

    @Rule
        public IntentsTestRule<InserimentoNumero> activityRule
                = new IntentsTestRule<>(InserimentoNumero.class);


        public void setString() { contact = "Sara" ; }

        @Before //da provare con BeforeClass
        public void inserisci_contatto() {

            Uri add_contact_uri = ContactsContract.Data.CONTENT_URI;

            long contact_id = getRawContactId() ;
            String display_name = "Sara" ;
            insertContactDisplayName(add_contact_uri,contact_id,display_name);
            String phone_number = "06" ;
            insertContactPhoneNumber(add_contact_uri,contact_id,phone_number,"mobile");


        }

        private  long getRawContactId()
        {

            ContentValues contentValues = new ContentValues();
            Uri rawContactUri = getApplicationContext().getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
            // Get the newly created contact raw id.
            long ret = ContentUris.parseId(rawContactUri);
            return ret;
        }

        private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName)
        {
            ContentValues contentValues = new ContentValues();

            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

            // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
            contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

            // Put contact display name value.
            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);

            getApplicationContext().getContentResolver().insert(addContactsUri, contentValues);

        }

        private void insertContactPhoneNumber(Uri addContactsUri, long rawContactId, String phoneNumber, String phoneTypeStr)
        {
            // Create a ContentValues object.
            ContentValues contentValues = new ContentValues();

            // Each contact must has an id to avoid java.lang.IllegalArgumentException: raw_contact_id is required error.
            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

            // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
            contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

            // Put phone number value.
            contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);

            // Calculate phone type by user selection.
            int phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;

            if("home".equalsIgnoreCase(phoneTypeStr))
            {
                phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
            }else if("mobile".equalsIgnoreCase(phoneTypeStr))
            {
                phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
            }else if("work".equalsIgnoreCase(phoneTypeStr))
            {
                phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
            }
            // Put phone type value.
            contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType);

            // Insert new contact data into phone contact list.
            getApplicationContext().getContentResolver().insert(addContactsUri, contentValues);

        }

        @Test
        public void test() { //test inserito per ritardare il secondo test, affinchè si possa consentire al secondo di ottenere il lista il contatto fittizio inserito
            onView(withId(R.id.spinner)).perform(click()) ;

            assertEquals(true,true) ;
        }
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


