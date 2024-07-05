package com.example.lu_acm_navigator;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CnAdapter extends RecyclerView.Adapter<CnAdapter.ContestViewHolder> {

    private Context context;
    private Cursor cursor;

    public CnAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ContestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contest_view, parent, false);
        return new ContestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContestViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_NAME));
            String link = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_CONTEST_LINK));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_DATE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_TIME));

            holder.nameTextView.setText(name);
            holder.linkTextView.setText(link);
            holder.dateTextView.setText(date);
            holder.timeTextView.setText(time);
            int backgroundColor = (position % 2 == 0) ?
                    ContextCompat.getColor(context, R.color.Thistle) :
                    ContextCompat.getColor(context, R.color.soap);
            holder.itemView.setBackgroundColor(backgroundColor);
        }
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
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

    static class ContestViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, linkTextView, dateTextView, timeTextView;

        ContestViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_contest_name);
            linkTextView = itemView.findViewById(R.id.tv_contest_link);
            dateTextView = itemView.findViewById(R.id.tv_contest_date);
            timeTextView = itemView.findViewById(R.id.tv_contest_time);
        }
    }
}
