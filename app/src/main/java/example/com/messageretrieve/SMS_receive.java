package com.example.sadia.sms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SMS_receive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_receive);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String address = extras.getString("MessageNumber");
            String message = extras.getString("Message");
            TextView addressField = (TextView) findViewById(R.id.address);
            TextView messageField = (TextView) findViewById(R.id.message);

            // Todo : Set Number And Message In TextView


                addressField.setText("Message From : " + address);
                messageField.setText("Messsage : " + message);

        }
    }
}
