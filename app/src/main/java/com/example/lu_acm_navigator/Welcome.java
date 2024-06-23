package com.example.lu_acm_navigator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        Button btngetstarted=findViewById(R.id.btn_get_started);
        btngetstarted.setOnClickListener(v ->{
            Intent intent = new Intent(Welcome.this,MainActivity.class);
            startActivity(intent);
        });
    }
}