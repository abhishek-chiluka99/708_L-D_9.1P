package com.example.findandlostapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    private static final String DATABASE_NAME = "Object_data.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_Items";

    private static final String COLUMN_ID = "_id";

    private static final String COLUMN_TITLE = "item_tile";
    private static final String COLUMN_NAME = "item_name";
    private static final String COLUMN_PHONE = "item_phone";
    private static final String COLUMN_DESC = "item_desc";
    private static  final  String COLUMN_DATE = "item_date";
    private static final String COLUMN_LOC = "item_loc";

    private static final String COLUMN_LAT = "item_lat";
    private static final String COLUMN_LONG = "item_long";

    public MyDatabaseHelper(@Nullable Context context){
        super(context,DATABASE_NAME ,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOC + " TEXT, " +
                COLUMN_LAT + " REAL , " +
                COLUMN_LONG + " REAL );" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
    }

    void add_item(String title, String name, String phone, String desc, String date, String loc, Double lat, Double lng){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE,phone);
        cv.put(COLUMN_DESC,desc);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_LOC,loc);
        cv.put(COLUMN_LAT, lat );
        cv.put(COLUMN_LONG, lng);
        long res = db.insert(TABLE_NAME, null,cv);

        if(res == -1){
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Item Added Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readAllData(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(" select * from my_Items " ,null);
        }

        return cursor;
    }

    void deledata(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();

        long res = db.delete(TABLE_NAME, "_id=?" , new String[]{row_id});

        if(res == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Suceesfully Removed the Item.", Toast.LENGTH_SHORT).show();
        }
    }
}
