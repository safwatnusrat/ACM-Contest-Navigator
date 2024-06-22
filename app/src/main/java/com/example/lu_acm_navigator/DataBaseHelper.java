package com.example.lu_acm_navigator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static  final String DATABASE_NAME="Test_BD";
    private static  final int DATABASE_VERSION= 1;
    private static final String TABLE_REGISTER ="register";
    private static final String COL_ID ="id";
    private static final String COL_USERNAME="username";
    private static final String COL_EMAIL ="email";
    private static final String COL_PASSWORD ="password";
    private static final String COL_PHONE ="phone";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME,null,  DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_REGISTER +"("+
                COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_USERNAME +" TEXT, " +
                COL_EMAIL +" TEXT, " +
                COL_PASSWORD+" TEXT, " +
                COL_PHONE+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_REGISTER);
        onCreate(db);
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

    public boolean CheckUserAuthentication(String username, String password) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT * FROM " +TABLE_REGISTER + " WHERE "+ COL_USERNAME + " = ? AND " + COL_PASSWORD+ " = ? ",new String[]{username,password});
        boolean exist=cursor.getCount()>0;
        cursor.close();
        return exist;
    }
}