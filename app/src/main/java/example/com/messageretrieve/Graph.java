package com.example.sadia.sms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Graph extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeries;

    private Button btnAddPt;

    private EditText mX,mY;

    GraphView mScatterPlot;

    //make xyValueArray global
    private ArrayList<XYValue> xyValueArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        //declare variables in oncreate
        btnAddPt = (Button) findViewById(R.id.btnAddPt);

        mScatterPlot = (GraphView) findViewById(R.id.scatterPlot);
        xyValueArray = new ArrayList<>();
        mScatterPlot.removeAllSeries();
        init();
    }

    private void init(){
        //declare the xySeries Object
        xySeries = new PointsGraphSeries<>();

        btnAddPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        //mHour = date.getHours();
                        //mMinute = date.getMinutes();
                    }
                }
                int indexBody = smsInboxCursor.getColumnIndex("body");
                int indexAddress = smsInboxCursor.getColumnIndex("address");
                if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
                String cnt="1" +
                        "";

                do {
                    String ss;
                    ss=  smsInboxCursor.getString(indexAddress);
                    String msg;
                    msg=smsInboxCursor.getString(indexBody);


                    if(ss.equals("+8801705742294")) {
                        //Date date = new Date(smsInboxCursor.getLong(0));
                        //String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
                        Calendar calendar = Calendar.getInstance();
                        // calendar.setTimeInMillis(smsInboxCursor.getTimestampMillis());

                        //int date = calendar.get(CALENDAR.DATE);
                        //int hour = calendar.get(CALENDAR.HOUR_OF_DAY);

                        String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                                "\n" + smsInboxCursor.getString(indexBody) + "\n";
                        //arrayAdapter.add(str);
                        String taka="";
                        if(msg.contains("Deposited"))
                        {
                            for(int i=14;i<msg.length();i++)
                            {
                                if(msg.charAt(i)=='t')
                                {
                                    break;
                                }
                                taka+=msg.charAt(i);
                            }
                            double x = Double.parseDouble(cnt);
                            double y = Double.parseDouble(taka);
                            y=y/1000;
                            int counter=Integer.parseInt(cnt);
                            counter+=50;
                            cnt="";
                            cnt+=counter;
                            Log.d(TAG, "onClick: Adding a new point. (x,y): (" + x + "," + y + ")" );
                            xyValueArray.add(new XYValue(x,y));
                            init();

                            taka+= " " +"Tk."+" ";
                            taka+=date;





                            taka="";



                        }
                    }
                } while (smsInboxCursor.moveToNext());














            }
        });

        //little bit of exception handling for if there is no data.
        if(xyValueArray.size() != 0){
            createScatterPlot();
        }else{
            Log.d(TAG, "onCreate: No data to plot.");
        }
    }

    private void createScatterPlot() {
        Log.d(TAG, "createScatterPlot: Creating scatter plot.");

        //sort the array of xy values
       // xyValueArray = sortArray(xyValueArray);


        //add the data to the series
        for(int i = xyValueArray.size()-1;i >=0; i--){
            try{
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
                xySeries.appendData(new DataPoint(x,y),true, 1000);
            }catch (IllegalArgumentException e){
                Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage() );
            }
        }

        //set some properties
        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.BLUE);
        xySeries.setSize(20f);

        //set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(150);
        mScatterPlot.getViewport().setMinY(-150);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(150);
        mScatterPlot.getViewport().setMinX(-150);

        mScatterPlot.addSeries(xySeries);
    }

    /*
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    /*private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){

        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>

        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size() - 1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");


        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try {
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }*/

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}