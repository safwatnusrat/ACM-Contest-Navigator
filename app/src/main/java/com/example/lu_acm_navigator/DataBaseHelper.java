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
    public static final String COL_FULLNAME ="fullname";
    public static final String COL_USERNAME="username";
    public static final String COL_EMAIL ="email";
    public static final String COL_PASSWORD ="password";
    public static final String COL_PHONE ="phone";
    public static final String COL_CONTEST_LINK ="contestlink";
    public static final String COL_NAME = "contestname";
    public static final String COL_DATE ="date";
    public static final String COL_TIME ="time" ;
    public static final String COL_ID1 ="ID" ;
    private static final String TABLE_PARTICIPATION ="ParticipationTable" ;
    private static final String COL_ID2 = "participationID";
  private static final String COL_CONTEST_ID = "ContestID";
    private static final String COL_USER_ID ="UserID" ;


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

    public boolean insertUser(String fullname,String username, String email, String phone, String password) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FULLNAME,fullname);
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
                COL_FULLNAME +" TEXT, "+
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

        db.execSQL("CREATE TABLE " + TABLE_PARTICIPATION + " (" +
                COL_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CONTEST_ID + " INTEGER, " +
                COL_USER_ID + " INTEGER, " +
                "FOREIGN KEY (" + COL_CONTEST_ID + ") REFERENCES " + TABLE_CONTEST + "(" + COL_ID1 + "), " +
                "FOREIGN KEY (" + COL_USER_ID + ") REFERENCES " + TABLE_REGISTER + "(" + COL_ID + "), " +
                "UNIQUE(" + COL_CONTEST_ID + ", " + COL_USER_ID + "))"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_PARTICIPATION);
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

    public void updatecontest(String id,String name, String contestLink, String date, String time) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(COL_CONTEST_LINK, contestLink);
        Values.put(COL_NAME, name);
        Values.put(COL_DATE, date);
        Values.put(COL_TIME, time);
        db.update(TABLE_CONTEST, Values, COL_ID1+ " = ?", new String[]{String.valueOf(id)});

        db.close();

    }

    public Cursor getnameofdeletecontest(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CONTEST + " WHERE " + COL_NAME + " = ?", new String[]{name});
    }
    public void deletecontest(String name) {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_CONTEST, COL_NAME+ " = ?", new String[]{name});

        db.close();
    }

    public Cursor getallcontest() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CONTEST ,null);
    }

    public Cursor fetchallcontest() {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ TABLE_CONTEST,null);
    }


     public boolean addParticipant(long contestId, long userId) {
          SQLiteDatabase db = this.getWritableDatabase();
          ContentValues values = new ContentValues();
          values.put(COL_CONTEST_ID, contestId);
          values.put(COL_USER_ID, userId);

          long result = db.insert(TABLE_PARTICIPATION, null, values);
          return result != -1;
}
    

    public long getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REGISTER, new String[]{COL_ID},
                "username = ?", new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            long userId = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
            cursor.close();
            return userId;
        } else {
            return -1;
        }
    }
    public Cursor getParticipantsGroupedByContest() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT c.contestname AS contest_name, u.username, u.email " +
                "FROM register u " +
                "JOIN ParticipationTable cp ON u.id = cp.UserID " +
                "JOIN contestadd c ON cp.ContestID = c.ID";
        return db.rawQuery(query, null);
    }

}
