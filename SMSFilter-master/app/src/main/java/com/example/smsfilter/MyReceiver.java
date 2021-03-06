package com.example.smsfilter;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private DBHelper db ;

    @Override
    public void onReceive(Context context, Intent intent) {

        db = new DBHelper(context) ;

        if (intent.getAction().equals(SMS_RECEIVED)) {

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }

                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String sender = messages[0].getOriginatingAddress();
                String message = sb.toString();
                MainActivity instance = MainActivity.getInstance() ;

                if ( checkNumber(sender) ) {
                    instance.updateList(message,sender);
                }

            }
        }
    }

    public boolean checkNumber( String number ) {

        return db.getAllContacts().contains(number) ;

    }
}