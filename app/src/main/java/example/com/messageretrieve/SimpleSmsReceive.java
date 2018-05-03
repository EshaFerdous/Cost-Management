package com.example.sadia.sms;

/**
 * Created by sadia on 3/26/18.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

// Todo : SMS Reciever Class

public class SimpleSmsReceive extends BroadcastReceiver {

    private static final String TAG = "Message recieved";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        SmsMessage messages =SmsMessage.createFromPdu((byte[]) pdus[0]);
        // Log.i(TAG,  messages.getMessageBody());

        // Todo : Start Application's  MainActivty activity

        // Todo : Send Message And Number In Activity

        Intent smsIntent=new Intent(context,SMS_receive.class);
        smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        smsIntent.putExtra("MessageNumber", messages.getOriginatingAddress());
        smsIntent.putExtra("Message", messages.getMessageBody());
        context.startActivity(smsIntent);

        // Todo : Show Message In Toast

        Toast.makeText(context, "SMS Received From :"+messages.getOriginatingAddress()+"\n"+ messages.getMessageBody(), Toast.LENGTH_LONG).show();
    }

}
