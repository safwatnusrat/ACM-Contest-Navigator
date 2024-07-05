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

public class AdminViewParticipantsActivity extends AppCompatActivity {
    private static final String TAG="AdminViewWParticipation";
    private RecyclerView participantsRecyclerView;
    private DataBaseHelper databaseHelper;
    private ParticipationAdapter participantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_participants);
        participantsRecyclerView = findViewById(R.id.participants_recycler_view);
        participantsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DataBaseHelper(this);
        participantAdapter = new ParticipationAdapter(this, null);
        participantsRecyclerView.setAdapter(participantAdapter);
        //displayParticipants();
    }

//    private void displayParticipants() {
//        Cursor cursor = databaseHelper.getAllParticipants();
//        if (cursor != null && cursor.getCount() > 0) {
//            Log.d(TAG, "Number Of Participants: " + cursor.getCount());
//            participantAdapter.swapCursor(cursor);
//        } else {
//            Log.d(TAG, "No Participants Available");
//            Toast.makeText(this, "No Participants Available!", Toast.LENGTH_SHORT).show();
//        }
//    }
}