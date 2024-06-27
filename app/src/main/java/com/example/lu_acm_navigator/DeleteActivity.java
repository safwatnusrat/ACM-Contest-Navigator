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

public class DeleteActivity extends AppCompatActivity {
    private EditText dlnameEditText;
    private EditText dllinkEditText;
    private EditText dldateEditText;
    private EditText dltimeEditText;
    private EditText dlcontestidEditText;
    private Button deleteButton;
    private Button searchButton;
    private DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        dlnameEditText= findViewById(R.id.delete_contest_name);
        dllinkEditText= findViewById(R.id.delete_contest_link);
        dldateEditText= findViewById(R.id.delete_contest_date);
        dltimeEditText= findViewById(R.id.delete_contest_time);
        dlcontestidEditText= findViewById(R.id.delete_contest_id);
        deleteButton= findViewById(R.id.btn_delete);
        searchButton= findViewById(R.id.btn_search);
        dataBaseHelper = new DataBaseHelper(this);
        searchButton.setOnClickListener(view -> searchcontest());
        deleteButton.setOnClickListener(view -> deletecontest());

    }
    private void searchcontest () {
        String name = dlnameEditText.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Please Enter Correct Contest Name", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = dataBaseHelper.getnameofdeletecontest(name);
        if (cursor != null && cursor.moveToFirst()) {
            String contestid = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_ID1));
            String link = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_CONTEST_LINK));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_DATE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_TIME));
            dlcontestidEditText.setText(String.valueOf(contestid));
            dllinkEditText.setText(String.valueOf(link));
            dldateEditText.setText(String.valueOf(date));
            dltimeEditText.setText(String.valueOf(time));
            cursor.close();
            Toast.makeText(this, "Insert successfully", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "Insert the contest name properly", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletecontest() {
        String name=dlnameEditText.getText().toString();
        dataBaseHelper.deletecontest(name);
    }
}