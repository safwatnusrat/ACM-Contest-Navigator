package com.example.lu_acm_navigator;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContestDisplay extends AppCompatActivity {
    private static final String TAG="ContestDisplay";
    private RecyclerView userupcomingcontestRecyclerview;
    private DataBaseHelper databaseHelper;
    private ContestAdapter contestAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_display);

        userupcomingcontestRecyclerview =findViewById(R.id.user_contest_view);
        userupcomingcontestRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper=new DataBaseHelper(this);
        contestAdapter = new ContestAdapter(this, null);
        userupcomingcontestRecyclerview.setAdapter(contestAdapter);
        displayycontest();

    }

    private void displayycontest() {
        Cursor cursor=databaseHelper.fetchallcontest();
        if (cursor !=null && cursor.getCount()>0)
        {
            Log.d(TAG,"Number Of Contests: "+cursor.getCount());
            contestAdapter.swapCursor(cursor);

        }
        else{
            Log.d(TAG,"NO Contest Available");
            Toast.makeText(this, "No Contest Available!", Toast.LENGTH_SHORT).show();
        }
    }
}