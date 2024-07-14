package com.example.lu_acm_navigator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button btnaddcontest=findViewById(R.id.btn_add_contest);
        Button btnviewdetails=findViewById(R.id.btn_view_details);
        Button btnupdatecontest=findViewById(R.id.btn_update_contest);
        Button btndeletecontest=findViewById(R.id.btn_delete_contest);
        Button btnparticipationlist=findViewById(R.id.btn_participation_list);


        btnaddcontest.setOnClickListener(v-> {
            Intent intent =new Intent(AdminActivity.this,AddContestctivity.class);
            startActivity(intent);
        });
        btnviewdetails.setOnClickListener(v-> {
            Intent intent =new Intent(AdminActivity.this,ViewDetailsActivity.class);
            startActivity(intent);
        });
        btnupdatecontest.setOnClickListener(v-> {
            Intent intent =new Intent(AdminActivity.this,UpdateActivity.class);
            startActivity(intent);
        });
        btndeletecontest.setOnClickListener(v-> {
            Intent intent =new Intent(AdminActivity.this,DeleteActivity.class);
            startActivity(intent);
        });
       btnparticipationlist.setOnClickListener(v->{
           Intent intent=new Intent(AdminActivity.this,AdminViewParticipantsActivity.class);
           startActivity(intent);
       });




    }
}