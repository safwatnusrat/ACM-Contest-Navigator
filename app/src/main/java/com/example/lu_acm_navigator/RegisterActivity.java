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

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etFullname= findViewById(R.id.et_register_fullname);
        EditText etUsername= findViewById(R.id.et_register_username);
        EditText etEmail= findViewById(R.id.et_register_email);
        EditText etPhone =findViewById(R.id.et_register_phone);
        EditText etPassword= findViewById(R.id.et_register_password);
        EditText etConfirmPassword= findViewById(R.id.et_register_confirm_password);
        Button btnLogin=findViewById(R.id.btn_sign_up_login);
        Button btnRegister=findViewById(R.id.btn_sign_up_register);

        btnRegister.setOnClickListener(v ->{

            String fullname= etFullname.getText().toString();
            String username =etUsername.getText().toString();
            String email =etEmail.getText().toString();
            String phone =etPhone.getText().toString();
            String password =etPassword.getText().toString();
            String confirmpassword =etConfirmPassword.getText().toString();


           if( password.equals(confirmpassword) && !username.isEmpty() && validation(fullname,phone,email,password)){
               DataBaseHelper dbHelper =new DataBaseHelper(RegisterActivity.this);
               boolean isinserted=dbHelper.insertUser(fullname,username, email, phone, password);
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

    private boolean validation(String fullname,String phone,String email,String password) {
            Pattern phonePattern= Pattern.compile("^(0088|\\+88)?01[2-9]\\d{8}$");
            Pattern emailPattern= Pattern.compile("^[a-z0-9]+@(gmail|yahoo|outlook).com$");
            Pattern fullnamePattern= Pattern.compile("^[a-zA-Z ]+$");
            Pattern passwordPattern= Pattern.compile("^(?=.+[0-9])(?=.+[a-z])(?=.*[A-Z])(?=.+[@#$%^&+=!]).{8,}$");
            if(!fullnamePattern.matcher(fullname).matches()){
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return false;
            }

            if(!emailPattern.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return false;
            }

            if(!phonePattern.matcher(phone).matches()){
                Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!passwordPattern.matcher(password).matches()){
            Toast.makeText(this, "Atleast 8 character containing atleast one upercase,one lowercase,one digit and one special character ", Toast.LENGTH_SHORT).show();
            return false;
            }
            else {
                return true;
            }


    }


}