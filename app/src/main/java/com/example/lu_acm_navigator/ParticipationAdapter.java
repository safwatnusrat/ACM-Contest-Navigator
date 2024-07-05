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

public class ParticipationAdapter extends RecyclerView.Adapter<ParticipationAdapter.ParticipantViewHolder> {
    private Context context;
    private Cursor cursor;

    public ParticipationAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.participants_list, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_FULLNAME));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_USERNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_EMAIL));

            holder.fullnameTextView.setText(fullname);
            holder.usernameTextView.setText(username);
            holder.emailTextView.setText(email);

            int backgroundColor = (position % 2 == 0) ?
                    ContextCompat.getColor(context, R.color.Thistle) :
                    ContextCompat.getColor(context, R.color.soap);
            holder.itemView.setBackgroundColor(backgroundColor);
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    static class ParticipantViewHolder extends RecyclerView.ViewHolder {
        TextView fullnameTextView;
        TextView usernameTextView;
        TextView emailTextView;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            fullnameTextView = itemView.findViewById(R.id.fullname_insert);
            usernameTextView = itemView.findViewById(R.id.username_insert);
            emailTextView = itemView.findViewById(R.id.email_insert);
        }
    }
}