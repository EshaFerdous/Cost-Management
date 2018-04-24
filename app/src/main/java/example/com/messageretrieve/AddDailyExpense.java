package com.example.fatema.expensecalculator;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddDailyExpense extends AppCompatActivity {


    /*this will declare variables for widges*/

    private EditText h;
    private  EditText w;
    private EditText institution;
    private EditText other;

    private TextView result;
    private  TextView date;
    Button save;
    Button date1;
    Button graph;
    Button list;
    String dateString;

    Calendar cal= Calendar.getInstance();
    databaseHelper bmidb;
    DateFormat formatDate = DateFormat.getDateInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_expense);

        /*Get id for each variables*/

        h=(EditText)findViewById(R.id.height);
        w=(EditText)findViewById(R.id.weight);
        institution=(EditText)findViewById(R.id.editText);
        other=(EditText)findViewById(R.id.editText2);

        result=(TextView) findViewById(R.id.result);
        date=(TextView) findViewById(R.id.datetextview);
        date1=(Button) findViewById(R.id.datebutton);
        save=(Button)findViewById(R.id.calc) ;
        graph=(Button) findViewById(R.id.stat);
        list=(Button) findViewById(R.id.list);
        bmidb=new databaseHelper(this);


        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();




            }
        });

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(AddDailyExpense.this, ExpenseGraphView.class);
                startActivity(p);


            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(AddDailyExpense.this, ExpenseListView.class);
                startActivity(p);


            }
        });

    }


    /*This will calculate BMI according to height and weight*/

    public void calculate()
    {
        Double bmi=0.0;
        String bmiString;
        String heightstr=h.getText().toString();
        String weightstr=w.getText().toString();
        String instr=institution.getText().toString();
        String others=other.getText().toString();


        if(heightstr!=null && !"".equals(heightstr))
        {
            Double heightvalue= Double.parseDouble(heightstr);
            Double weightvalue= Double.parseDouble(weightstr);
            Double insvalue= Double.parseDouble(instr);
            Double othervalue= Double.parseDouble(others);

            bmi=heightvalue+weightvalue+insvalue+othervalue;
            display(bmi);
        }
        bmiString = Double.toString(bmi);

        /*if we do not insert value in height,weight and date..it wil not be inserted in database*/

        if(!TextUtils.isEmpty(heightstr)&& !TextUtils.isEmpty(weightstr)&&  !TextUtils.isEmpty(instr)&& !TextUtils.isEmpty(others)&&!TextUtils.isEmpty(dateString))
        {
            boolean flag =bmidb.insertData(dateString,bmiString);

            if(flag==true){
                Toast.makeText(AddDailyExpense.this,"inserted",Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(AddDailyExpense.this," not inserted",Toast.LENGTH_SHORT).show();

        }

        return ;

    }


    /*this will display the BMI result and your condition*/

    public void display(Double bmi)
    {
        String bmilevel="";

        bmilevel="Total amount for today "+bmi+" Taka" +bmilevel;
        result.setText(bmilevel);
    }

    /*this will get data according to a calander*/

    public void getDate(){

        new DatePickerDialog(this,d, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayofMonth){

            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,monthofYear);
            cal.set(Calendar.DAY_OF_MONTH,dayofMonth);
            updateDate();

        }
    };


    public void updateDate(){


        date.setText(formatDate.format(cal.getTime()));
        dateString = date.getText().toString();

    }

}
