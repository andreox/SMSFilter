package com.example.smsfilter;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = MyReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras() ;
        SmsMessage[] messages ;
        String strMessage = "" ;
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get(pdu_type);

        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM =
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            // Fill the msgs array.
            messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                /* if ( messages[i].getOriginatingAddress().equals( some_of_numbers_in_Persona_TABLE ) {


                        Insert into Messaggio TABLE => Key Incrementale, Stringa NUM TEL, Stringa Body ( messages[i].getMessageBody() )
                        Aggiungi questa istanza alla lista a scorrimento nell'Activity di SHOW SMS

                 */
            }
        }

    }
}