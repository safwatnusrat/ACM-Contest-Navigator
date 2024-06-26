package com.example.lu_acm_navigator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME="Test_BD";
    public static  final int DATABASE_VERSION= 1;
    public static final String TABLE_REGISTER ="register";
    public static final String TABLE_CONTEST ="contestadd" ;
    public static final String COL_ID ="id";
    public static final String COL_USERNAME="username";
    public static final String COL_EMAIL ="email";
    public static final String COL_PASSWORD ="password";
    public static final String COL_PHONE ="phone";
    public static final String COL_CONTEST_LINK ="contestlink";
    public static final String COL_NAME = "contestname";
    public static final String COL_DATE ="date";
    public static final String COL_TIME ="time" ;
    public static final String COL_ID1 ="ID" ;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME,null,  DATABASE_VERSION);
    }

    public void addContest(String contestLink, String name, String date, String time) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(COL_CONTEST_LINK, contestLink);
        Values.put(COL_NAME, name);
        Values.put(COL_DATE, date);
        Values.put(COL_TIME, time);
        db.insert(TABLE_CONTEST ,null,Values);
        db.close();
    }

    public boolean insertUser(String username, String email, String phone, String password) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PHONE, phone);
        contentValues.put(COL_PASSWORD, password);
        long result=db.insert(TABLE_REGISTER,null, contentValues);

        return result !=-1;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_REGISTER +"("+
                COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_USERNAME +" TEXT, " +
                COL_EMAIL +" TEXT, " +
                COL_PASSWORD+" TEXT, " +
                COL_PHONE+" TEXT)");


        db.execSQL("CREATE TABLE " + TABLE_CONTEST + "("+
                COL_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CONTEST_LINK + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_TIME + " TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        onCreate(db);
    }



    public boolean CheckUserAuthentication(String username, String password) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT * FROM " +TABLE_REGISTER + " WHERE "+ COL_USERNAME + " = ? AND " + COL_PASSWORD+ " = ? ",new String[]{username,password});
        boolean exist=cursor.getCount()>0;
        cursor.close();
        return exist;
    }

    public Cursor getContestbyname(String contestname) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CONTEST + " WHERE " + COL_NAME + " = ?", new String[]{contestname});
    }
}