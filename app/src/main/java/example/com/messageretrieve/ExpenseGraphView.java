package com.example.fatema.expensecalculator;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExpenseGraphView extends AppCompatActivity {



    databaseHelper database;
  //  GraphView graphview;
 //   LineGraphSeries<com.jjoe64.graphview.series.DataPoint> series;
    Button clear,list;
    double x,y,bmivalue;
    int numberOfPoints,index=0;
    double array[];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_graph_view);

        /*get id for each variable*/

     /*   database=new databaseHelper(this);
        graphview=(GraphView) findViewById(R.id.bmigraph);
        series = new LineGraphSeries<com.jjoe64.graphview.series.DataPoint>();
        clear=(Button) findViewById(R.id.button);
        list=(Button) findViewById(R.id.list);


        getData();
        DrawGraph();



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteData();
            }
        });
    }


    /*To draw the graph where date is in the x axis and bmicount is in the y axis*/

   /* public void DrawGraph()
    {
        x=0;

        for(int i=0;i<numberOfPoints;i++)
        {
            x=x+2;
            y=array[i];
            series.appendData(new com.jjoe64.graphview.series.DataPoint(x,y),true, numberOfPoints);
        }

        graphview.addSeries(series);
        series.setDrawDataPoints(true);

    }

    /*This function will get values(x,y) from database and set it to an array*/

   /* void getData()
    {
        Cursor res= database.getAllData();
        numberOfPoints= res.getCount();


        if(numberOfPoints==0)
        {
            Toast.makeText(this,"No values",Toast.LENGTH_SHORT).show();
            return;
        }

        else
        {
            Toast.makeText(this,"Values",Toast.LENGTH_SHORT).show();
            array = new double[numberOfPoints];
        }

        index =0;

        while(res.moveToNext()){

            array[index++]=Double.parseDouble(res.getString(2).trim());
        }*/
    }
}
