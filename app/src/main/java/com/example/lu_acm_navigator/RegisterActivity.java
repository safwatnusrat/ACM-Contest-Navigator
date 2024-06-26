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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etUsername= findViewById(R.id.et_register_username);
        EditText etEmail= findViewById(R.id.et_register_email);
        EditText etPhone =findViewById(R.id.et_register_phone);
        EditText etPassword= findViewById(R.id.et_register_password);
        EditText etConfirmPassword= findViewById(R.id.et_register_confirm_password);
        Button btnLogin=findViewById(R.id.btn_sign_up_login);
        Button btnRegister=findViewById(R.id.btn_sign_up_register);

        btnRegister.setOnClickListener(v ->{
           String username =etUsername.getText().toString();
           String email =etEmail.getText().toString();
           String phone =etPhone.getText().toString();
           String password =etPassword.getText().toString();
           String confirmpassword =etConfirmPassword.getText().toString();

           if(password.equals(confirmpassword) && !username.isEmpty() && !password.isEmpty()){
               //Toast.makeText(RegisterActivity.this, "db connect", Toast.LENGTH_SHORT).show();
               DataBaseHelper dbHelper =new DataBaseHelper(RegisterActivity.this);
               boolean isinserted=dbHelper.insertUser(username, email, phone, password);
               if(isinserted)
               {
                   Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                   startActivity(intent);
               }
               else {
                   Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
               }
           }
           else {
               Toast.makeText(RegisterActivity.this, "Fill Up the Username and Password Carefully!", Toast.LENGTH_SHORT).show();
           }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterActivity.this, "Login Button Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}