package com.example.lu_acm_navigator;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddContestctivity extends AppCompatActivity {

    private EditText ContestLinkEditText;
    private EditText ContestnameEditText;
    private Button AddContestButton;
    private DataBaseHelper dataBaseHelper;
    private Button pickDateButton;
    private Button pickTimeButton;
    private Calendar contestCalendar;
    private TextView DateTextView;
    private TextView TimeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_contestctivity);
        ContestLinkEditText= findViewById(R.id.contestlink);
        ContestnameEditText= findViewById(R.id.contestName);
        TimeTextView=findViewById(R.id.time);
        DateTextView=findViewById(R.id.date);
        pickDateButton = findViewById(R.id.btn_pick_date);
        pickTimeButton = findViewById(R.id.btn_pick_time);
        contestCalendar = Calendar.getInstance();

        pickDateButton.setOnClickListener(v->{
            int year=contestCalendar.get(Calendar.YEAR);
            int month=contestCalendar.get(Calendar.MONTH);
            int day=contestCalendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(AddContestctivity.this,(view,yr,monthh,dayy)->{
                contestCalendar.set(Calendar.YEAR,yr);
                contestCalendar.set(Calendar.MONTH,monthh);
                contestCalendar.set(Calendar.DAY_OF_MONTH,dayy);
                DateTextView.setText(new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(contestCalendar.getTime()));

            },year,month,day);
            dialog.show();
        });
        pickTimeButton.setOnClickListener(v->{
            int hour=contestCalendar.get(Calendar.HOUR_OF_DAY);
            int min=contestCalendar.get(Calendar.MINUTE);
            TimePickerDialog dialog=new TimePickerDialog(AddContestctivity.this,(view,hr,minn)->{
                contestCalendar.set(Calendar.HOUR_OF_DAY,hr);
                contestCalendar.set(Calendar.MINUTE,minn);
                TimeTextView.setText(new SimpleDateFormat("hh:mm a",Locale.getDefault()).format(contestCalendar.getTime()));

            },hour,min,false);
            dialog.show();
        });

        AddContestButton=findViewById(R.id.btn_add_contest);
        dataBaseHelper = new DataBaseHelper(this);

        AddContestButton.setOnClickListener(view -> addcontest());

    }


    private void addcontest() {
        String ContestLink=ContestLinkEditText.getText().toString();
        String Name=ContestnameEditText.getText().toString();
        String Date=DateTextView.getText().toString();
        String Time=TimeTextView.getText().toString();
        if(ContestLink.isEmpty() || Name.isEmpty()|| Date.isEmpty() || Time.isEmpty()){
            Toast.makeText(this, "Please Enter Correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        dataBaseHelper.addContest(ContestLink,Name,Date,Time);
        Toast.makeText(this,"Contest Added Successfully", Toast.LENGTH_SHORT).show();

    }
}
