package com.example.lu_acm_navigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText etUsername= findViewById(R.id.et_username);
        EditText etPassword= findViewById(R.id.et_password);
        Button btnLogin=findViewById(R.id.btn_login);
        Button btnRegister=findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v ->{
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
        });
        btnLogin.setOnClickListener(v -> {
           String username=etUsername.getText().toString();
           String password=etPassword.getText().toString();
           if(username.isEmpty() || password.isEmpty())
           {
               Toast.makeText(MainActivity.this, "Please Enter Username & Password Correctly", Toast.LENGTH_SHORT).show();
           }
           else
           {
               if(username.equals("admin") && password.equals("admin"))
               {
                   Intent intent = new Intent(MainActivity.this,AdminActivity.class);
                   startActivity(intent);
               }
               else {
                   DataBaseHelper dbHelper= new DataBaseHelper(MainActivity.this);
                   boolean result= dbHelper.CheckUserAuthentication(username,password);
                   if(result)
                   {
                       Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(MainActivity.this,ContestDisplay.class);
                       intent.putExtra("Username", username);
                       startActivity(intent);
                   }
                   else
                   {
                       Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                   }
               }
           }
        });


    }
}
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        EditText etUsername = findViewById(R.id.et_username);
//        EditText etPassword = findViewById(R.id.et_password);
//        Button btnLogin = findViewById(R.id.btn_login);
//        Button btnRegister = findViewById(R.id.btn_register);
//
//        btnRegister.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//            startActivity(intent);
//        });
//
//        btnLogin.setOnClickListener(v -> {
//            String username = etUsername.getText().toString();
//            String password = etPassword.getText().toString();
//            if (username.isEmpty() || password.isEmpty()) {
//                Toast.makeText(MainActivity.this, "Please Enter Username & Password Correctly", Toast.LENGTH_SHORT).show();
//            } else {
//                if (username.equals("admin") && password.equals("admin")) {
//                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
//                    startActivity(intent);
//                } else {
//                    DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
//                    boolean result = dbHelper.CheckUserAuthentication(username, password);
//                    if (result) {
//                        Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, ContestDisplay.class);
//                        intent.putExtra("Username", username);  // Correct extra name
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//    }
//}
