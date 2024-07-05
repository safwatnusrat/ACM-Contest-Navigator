package com.example.lu_acm_navigator;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {
   private EditText NameEditText;
   private EditText linkEditText;
   private TextView timeTextView;
   private TextView dateTextView;
   private EditText contestidEditText;
   private Button dateButton;
   private Button timeButton;
   private Button  searchButton;
   private Button updateButton;
   private Calendar contestCalender;
   private DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        NameEditText =findViewById(R.id.search_contest_name);
        linkEditText =findViewById(R.id.search_contest_link);
        timeTextView =findViewById(R.id.search_contest_time);
        dateTextView =findViewById(R.id.search_contest_date);
        contestidEditText =findViewById(R.id.search_contest_id);
        searchButton =findViewById(R.id.btn_search);
        updateButton =findViewById(R.id.btn_update);
        dateButton =findViewById(R.id.btn_update_date);
        timeButton =findViewById(R.id.btn_update_time);
        dataBaseHelper = new DataBaseHelper(this);
        contestCalender=Calendar.getInstance();
        searchButton.setOnClickListener(view -> searchcontest());
        updateButton.setOnClickListener(view -> updatecontest());
        dateButton.setOnClickListener(view -> datepicker());
        timeButton.setOnClickListener(view -> timepicker());

    }

    private void timepicker() {
        int hour=contestCalender.get(Calendar.HOUR_OF_DAY);
        int min=contestCalender.get(Calendar.MINUTE);
        TimePickerDialog dialog=new TimePickerDialog(UpdateActivity.this,(view, hr, minn)->{
            contestCalender.set(Calendar.HOUR_OF_DAY,hr);
            contestCalender.set(Calendar.MINUTE,minn);
            timeTextView.setText(new SimpleDateFormat("hh:mm a",Locale.getDefault()).format(contestCalender.getTime()));

        },hour,min,false);
        dialog.show();
    }

    private void datepicker() {
        int year=contestCalender.get(Calendar.YEAR);
        int month=contestCalender.get(Calendar.MONTH);
        int day=contestCalender.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog=new DatePickerDialog(UpdateActivity.this,(view, yr, monthh, dayy)->{
            contestCalender.set(Calendar.YEAR,yr);
            contestCalender.set(Calendar.MONTH,monthh);
            contestCalender.set(Calendar.DAY_OF_MONTH,dayy);
            dateTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(contestCalender.getTime()));

        },year,month,day);
        dialog.show();
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
            dateTextView.setText(String.valueOf(date));
            timeTextView.setText(String.valueOf(time));
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
        String Date=dateTextView.getText().toString();
        String Time=timeTextView.getText().toString();
        String Id=contestidEditText.getText().toString();
        if(ContestLink.isEmpty()|| Name.isEmpty()|| Date.isEmpty()|| Time.isEmpty()){
            Toast.makeText(this,"Fill all fileds",Toast.LENGTH_SHORT).show();
        }

        dataBaseHelper.updatecontest(Id,Name,ContestLink,Date,Time);

    }
}