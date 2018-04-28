package example.com.messageretrieve;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Expense.db";
    public static final String TABLE_NAME = "Expense_table";
    public static final String COL_1 = "Date";
    public static final String COL_2 = "ExpenseAmount";


    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,BMINUMBER INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //onCreate(db);
    }

    public boolean insertData(String date,String amount ) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,date);
        contentValues.put(COL_2,amount);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public void deleteData(){


        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }



    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        //ContentValues contentValues =new ContentValues();
        //String data ="";
        //data=contentValues.getAsString(COL_1);
        // data+="\n";
        //data+=contentValues.getAsString(COL_2);
        //data+="\n";


        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM " +TABLE_NAME;
        Cursor res = db.rawQuery(query,null);
        return res;    }


}
