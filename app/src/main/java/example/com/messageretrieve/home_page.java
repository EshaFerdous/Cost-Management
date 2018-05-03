package com.example.sadia.sms;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class home_page extends AppCompatActivity {
    private static home_page inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    Button btn,btn3;
    ImageButton total,graph,deposit,withdraw;
    ArrayAdapter arrayAdapter;
    DatabaseHelper mDatabaseHelper;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //

    public static home_page instance() {
        return inst;
    }
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        total=(ImageButton) findViewById(R.id.total);
        graph=(ImageButton) findViewById(R.id.graph);
        deposit=(ImageButton) findViewById(R.id.deposit);
        withdraw=(ImageButton) findViewById(R.id.withdraw);




       // mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mDatabaseHelper = new DatabaseHelper(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.bluefont);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshSmsInbox();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        //smsListView.setOnClickListener(this);
        // Add SMS Read Permision At Runtime

        // Todo : If Permission Is Not GRANTED
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            // Todo : If Permission Granted Then Show SMS
            refreshSmsInbox();

        } else {
            // Todo : Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(home_page.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(home_page.this, Graph.class);
                startActivity(Intent);

            }
        });
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(home_page.this, Deposit_list.class);
                startActivity(Intent);

            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(home_page.this, Withdraw_list.class);
                startActivity(Intent);

            }
        });

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(home_page.this, total_show.class);
                //startActivity(Intent);

            }
        });


        // tv_sms.setText("Phone Number: " + phoneNumber1 + " " + "SMS: " + SMSBody1);

    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), new String[]{"address","person","date","body"}, null, null, null);
        Date date=new Date();
        smsInboxCursor.moveToFirst();
        if(smsInboxCursor.getCount() > 0){
            while(!smsInboxCursor.isLast()){
                System.out.println(smsInboxCursor.getString(smsInboxCursor.getColumnIndex("date")));
                smsInboxCursor.moveToNext();
                final Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                date = cal.getTime();

            }
        }
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        //mDatabaseHelper.onUpgrade(db,0,1);
        arrayAdapter.clear();
        do {
            String ss;
            ss=  smsInboxCursor.getString(indexAddress);
            String msg;
            msg=smsInboxCursor.getString(indexBody);


            if(ss.equals("+8801705742294")) {
                //Date date = new Date(smsInboxCursor.getLong(0));
                //String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
                //int hour = calendar.get(CALENDAR.HOUR_OF_DAY);
                //int date = calendar.get(CALENDAR.DATE);
                Calendar calendar = Calendar.getInstance();
                // calendar.setTimeInMillis(smsInboxCursor.getTimestampMillis());


                String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                        "\n" + smsInboxCursor.getString(indexBody) + "\n";
                //arrayAdapter.add(str);
                String taka="";
                if(msg.contains("Deposited"))
                { taka+="Deposited:";
                    for(int i=14;i<msg.length();i++)
                    {
                        if(msg.charAt(i)=='t')
                        {
                            break;
                        }
                        taka+=msg.charAt(i);
                    }

                    taka+= " " +"Tk."+" ";
                    taka+=date;


                    arrayAdapter.add(str);
                    boolean insertData = mDatabaseHelper.addData(taka);

                    if (insertData)
                    {
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show();
                    }

                    taka="";



                }
                if(msg.contains("Withdrawn"))
                { taka+="Withdrwan:";
                    for(int i=14;i<msg.length();i++)
                    {
                        if(msg.charAt(i)=='t')
                        {
                            break;
                        }
                        taka+=msg.charAt(i);
                    }

                    taka+= " " +"Tk."+" ";
                    taka+=date;


                    arrayAdapter.add(str);
                    boolean insertData = mDatabaseHelper.addData(taka);

                    if (insertData)
                    {
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show();
                    }

                    taka="";



                }
            }
        } while (smsInboxCursor.moveToNext());
    }
    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
