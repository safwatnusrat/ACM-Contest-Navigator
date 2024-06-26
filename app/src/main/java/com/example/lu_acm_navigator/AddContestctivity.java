package com.example.lu_acm_navigator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddContestctivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK=1;
    private EditText ContestLinkEditText;
    private EditText ContestnameEditText;
    private EditText DateEditText;
    private EditText TimeEditText;
    private Button AddContestButton;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_contestctivity);
        ContestLinkEditText= findViewById(R.id.contestlink);
        ContestnameEditText= findViewById(R.id.contestName);
        DateEditText= findViewById(R.id.date);
        TimeEditText= findViewById(R.id.time);
        AddContestButton=findViewById(R.id.btn_add_contest);
        dataBaseHelper = new DataBaseHelper(this);

        AddContestButton.setOnClickListener(view -> addcontest());

    }

    private void addcontest() {
        String ContestLink=ContestLinkEditText.getText().toString();
        String Name=ContestnameEditText.getText().toString();
        String Date=DateEditText.getText().toString();
        String Time=TimeEditText.getText().toString();
        if(ContestLink.isEmpty() || Name.isEmpty()|| Date.isEmpty() || Time.isEmpty()){
            Toast.makeText(this, "Please Enter Correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        dataBaseHelper.addContest(ContestLink,Name,Date,Time);

        Toast.makeText(this,"Contest Added Successfully", Toast.LENGTH_SHORT).show();

    }
}
