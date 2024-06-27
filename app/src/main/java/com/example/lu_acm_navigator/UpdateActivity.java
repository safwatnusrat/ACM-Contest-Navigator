package com.example.lu_acm_navigator;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {
   private EditText NameEditText;
   private EditText linkEditText;
   private EditText dateEditText;
   private EditText timeEditText;
   private EditText contestidEditText;
   private Button  searchButton;
   private Button updateButton;
   private DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        NameEditText =findViewById(R.id.search_contest_name);
        linkEditText =findViewById(R.id.search_contest_link);
        dateEditText =findViewById(R.id.search_contest_date);
        timeEditText =findViewById(R.id.search_contest_time);
        contestidEditText =findViewById(R.id.search_contest_id);
        searchButton =findViewById(R.id.btn_search);
        updateButton =findViewById(R.id.btn_update);
        dataBaseHelper = new DataBaseHelper(this);
        searchButton.setOnClickListener(view -> searchcontest());
        updateButton.setOnClickListener(view -> updatecontest());

    }

    private void searchcontest () {
        String Contestname=NameEditText.getText().toString();
        if(Contestname.isEmpty()){
            Toast.makeText(this,"Please Enter Correct Contest Name", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cursor= dataBaseHelper.getContestbyname(Contestname);
        if(cursor !=null && cursor.moveToFirst())
        {
            String contestid=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_ID1));
            String link=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_CONTEST_LINK));
            String date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_DATE));
            String time=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_TIME));
            contestidEditText.setText(String.valueOf(contestid));
            linkEditText.setText(String.valueOf(link));
            dateEditText.setText(String.valueOf(date));
            timeEditText.setText(String.valueOf(time));
            cursor.close();
            Toast.makeText(this,"Insert successfully",Toast.LENGTH_SHORT).show();


        }
        else {
            Toast.makeText(this,"Insert the contest name properly",Toast.LENGTH_SHORT).show();
        }

    }

    private void updatecontest() {
        String ContestLink=linkEditText.getText().toString();
        String Name=NameEditText.getText().toString();
        String Date=dateEditText.getText().toString();
        String Time=timeEditText.getText().toString();
        String Id=contestidEditText.getText().toString();
        if(ContestLink.isEmpty()|| Name.isEmpty()|| Date.isEmpty()|| Time.isEmpty()){
            Toast.makeText(this,"Fill all fileds",Toast.LENGTH_SHORT).show();
        }

        dataBaseHelper.updatecontest(Id,Name,ContestLink,Date,Time);

    }
}