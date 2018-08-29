package com.mentors.android.bsor3a;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.mentors.android.bsor3a.LoginAndRegister.ConfirmationActivity;

public class SimpleSMSReciver extends BroadcastReceiver {
    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        SmsMessage messages =SmsMessage.createFromPdu((byte[]) pdus[0]);
        // Log.i(TAG,  messages.getMessageBody());

        // Todo : Start Application's  MainActivty activity

        // Todo : Send Message And Number In Activity

        Intent smsIntent=new Intent(context,ConfirmationActivity.class);
        smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        smsIntent.putExtra("MessageNumber", messages.getOriginatingAddress());
        smsIntent.putExtra("Message", messages.getMessageBody());
        if(messages.getMessageBody().startsWith("Your Activation code")){
        MainActivity.message = 1;
        context.startActivity(smsIntent);
//        ((Activity)context).finish();
        }else{

        }

        // Todo : Show Message In Toast

        Toast.makeText(context, "SMS Received From :"+messages.getOriginatingAddress()+"\n"+ messages.getMessageBody(), Toast.LENGTH_LONG).show();
    }
}

