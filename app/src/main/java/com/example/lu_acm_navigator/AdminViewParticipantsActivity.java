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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminViewParticipantsActivity extends AppCompatActivity {
    private static final String TAG = "AdminViewParticipation";
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

        displayParticipants();
    }

    private void displayParticipants() {
        Cursor cursor = databaseHelper.getParticipantsGroupedByContest();
        if (cursor != null && cursor.getCount() > 0) {
            Map<String, List<Participant>> contestParticipantsMap = new HashMap<>();
            while (cursor.moveToNext()) {
                String contestName = cursor.getString(cursor.getColumnIndexOrThrow("contest_name"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                Participant participant = new Participant(username, email);
                if (!contestParticipantsMap.containsKey(contestName)) {
                    contestParticipantsMap.put(contestName, new ArrayList<>());
                }
                contestParticipantsMap.get(contestName).add(participant);
            }
            cursor.close();
            participantAdapter = new ParticipationAdapter(this, contestParticipantsMap);
            participantsRecyclerView.setAdapter(participantAdapter);
        } else {
            Log.d(TAG, "No Participants Available");
            Toast.makeText(this, "No Participants Available!", Toast.LENGTH_SHORT).show();
        }
    }
}