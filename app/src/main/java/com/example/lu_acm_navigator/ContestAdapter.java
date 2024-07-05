package com.example.lu_acm_navigator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ContestViewHolder> {
    private Context context;
    private Cursor cursor;
    private DataBaseHelper dataBaseHelper;
    private Calendar calendar;

    public ContestAdapter(Context context,Cursor cursor)
    {
        this.context=context;
        this.cursor=cursor;
        this.dataBaseHelper=new DataBaseHelper(context);
    }



    @NonNull
    @Override
    public ContestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_contest_view, parent, false);
        return new ContestAdapter.ContestViewHolder(view);
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
            holder.participationCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    long contestId= cursor.getLong(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_ID1));
                    boolean isAdded = dataBaseHelper.addParticipant(contestId);
                    if (isAdded) {
                        Toast.makeText(context, "Registered for the contest", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.reminderButton.setOnClickListener(v -> {
                try {
                    setReminder(date, time, name);
                } catch (ParseException | java.text.ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed to set reminder", Toast.LENGTH_SHORT).show();
                }
            });
            int backgroundColor = (position % 2 == 0) ?
                    ContextCompat.getColor(context, R.color.AntiqueWhite) :
                    ContextCompat.getColor(context, R.color.Isabelline);
                    holder.itemView.setBackgroundColor(backgroundColor);
        }

    }

    private void setReminder(String date, String time, String name) throws ParseException, java.text.ParseException {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault());
        Date contestDateTime= sdf.parse(date+" "+ time);
        if(contestDateTime!=null)
        {
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(contestDateTime);
            Intent intent= new Intent(context, ReminderReceiver.class);
            intent.putExtra("Contest Name",name);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager=(AlarmManager)context.getSystemService(context.ALARM_SERVICE);
            if(alarmManager!=null)
            {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                Toast.makeText(context, "Reminder set for"+ name, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public int getItemCount() {
        return (cursor==null?0:cursor.getCount());
    }

    public void swapCursor(Cursor newCursor) {
           if(cursor !=null)
           {
               cursor.close();
           }
           cursor = newCursor;
           if (newCursor != null) {
            notifyDataSetChanged();
          }
    }
    static class ContestViewHolder extends RecyclerView.ViewHolder{
        public CheckBox participationCheckBox;
        public Button reminderButton;
        TextView nameTextView,linkTextView,dateTextView,timeTextView;
        public ContestViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.user_contest_name);
            linkTextView=itemView.findViewById(R.id.user_contest_link);
            dateTextView=itemView.findViewById(R.id.user_contest_date);
            timeTextView=itemView.findViewById(R.id.user_contest_time);
            participationCheckBox= itemView.findViewById(R.id.user_contest_participation_checkbox);
            reminderButton= itemView.findViewById(R.id.btn_reminder);


        }
    }
}
