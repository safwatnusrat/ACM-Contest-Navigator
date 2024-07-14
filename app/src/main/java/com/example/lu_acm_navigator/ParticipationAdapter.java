package com.example.lu_acm_navigator;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParticipationAdapter extends RecyclerView.Adapter<ParticipationAdapter.ParticipantViewHolder> {

    private Context context;
    private Map<String, List<Participant>> contestParticipantsMap;
    private List<String> contestNames;

    public ParticipationAdapter(Context context, Map<String, List<Participant>> contestParticipantsMap) {
        this.context = context;
        this.contestParticipantsMap = contestParticipantsMap;
        this.contestNames = new ArrayList<>(contestParticipantsMap.keySet());
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.participants_list, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        String contestName = contestNames.get(position);
        List<Participant> participants = contestParticipantsMap.get(contestName);

        holder.contestNameTextView.setText("Contest Name: " +contestName);
        StringBuilder participantsInfo = new StringBuilder();
        for (Participant participant : participants) {
            participantsInfo.append("Username: ").append(participant.username)
                    .append("\nEmail: ").append(participant.email).append("\n\n");
        }
        holder.participantsTextView.setText(participantsInfo.toString().trim());
        int backgroundColor = (position % 2 == 0) ?
                ContextCompat.getColor(context, R.color.Thistle) :
                ContextCompat.getColor(context, R.color.soap);
        holder.itemView.setBackgroundColor(backgroundColor);
    }

    @Override
    public int getItemCount() {
        return contestNames.size();
    }

    static class ParticipantViewHolder extends RecyclerView.ViewHolder {
        TextView contestNameTextView;
        TextView participantsTextView;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            contestNameTextView = itemView.findViewById(R.id.contest_name);
            participantsTextView = itemView.findViewById(R.id.participants);
        }
    }
}