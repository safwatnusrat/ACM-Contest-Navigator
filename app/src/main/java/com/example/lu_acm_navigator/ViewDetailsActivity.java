package com.example.lu_acm_navigator;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ViewDetailsActivity";
    private RecyclerView upcomingContestsRecyclerView;
    private DataBaseHelper databaseHelper;
    private CnAdapter cnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        upcomingContestsRecyclerView = findViewById(R.id.upcoming_contests_recycler_view);
        upcomingContestsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DataBaseHelper(this);
        cnAdapter = new CnAdapter(this, null);
        //adapter binding the data for recycleviewer
        upcomingContestsRecyclerView.setAdapter(cnAdapter);

        displayContests();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayContests();
    }

    private void displayContests() {
        Cursor cursor = databaseHelper.getallcontest();
        if (cursor != null && cursor.getCount() > 0) {
            Log.d(TAG, "Number of contests: " + cursor.getCount());
            cnAdapter.swapCursor(cursor);
        } else {
            Log.d(TAG, "No contests found or cursor is null");
            Toast.makeText(this, "No contests available", Toast.LENGTH_SHORT).show();
        }
    }
}
