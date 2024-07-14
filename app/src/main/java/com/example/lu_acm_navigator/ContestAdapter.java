package com.example.lu_acm_navigator;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.os.Build;
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
    private String username;

    public ContestAdapter(Context context,Cursor cursor,String username)
    {
        this.context=context;
        this.cursor=cursor;
        this.dataBaseHelper=new DataBaseHelper(context);
        this.username=username;
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
//            holder.participationCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                if (isChecked) {
//                    long contestId= cursor.getLong(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_ID1));
//                    boolean isAdded = dataBaseHelper.addParticipant(contestId);
//                    if (isAdded) {
//                        Toast.makeText(context, "Registered for the contest", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            holder.participationCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                if (isChecked) {
//                    long contestId = cursor.getLong(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_ID1));
//                    long userId = dataBaseHelper.getUserIdByUsername(username);
//                    if (userId != -1) {
//                        boolean isAdded = dataBaseHelper.addParticipant(contestId,userId);
//                        if (isAdded) {
//                            Toast.makeText(context, "Registered for the contest", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
            holder.participationCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    try {
                        long contestId = cursor.getLong(cursor.getColumnIndexOrThrow(DataBaseHelper.COL_ID1));
                        long userId = dataBaseHelper.getUserIdByUsername(username);

                        Log.d("ContestAdapter", "Contest ID: " + contestId + ", User ID: " + userId); // Log contest ID and user ID

                        if (userId != -1) {
                            boolean isAdded = dataBaseHelper.addParticipant(contestId, userId);
                            if (isAdded) {
                                Toast.makeText(context, "Registered for the contest", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("ContestAdapter", "Error registering for contest", e);
                        Toast.makeText(context, "Error registering for contest", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            holder.reminderButton.setOnClickListener(v -> {
                showDatePicker(name);
            });
            int backgroundColor = (position % 2 == 0) ?
                    ContextCompat.getColor(context, R.color.AntiqueWhite) :
                    ContextCompat.getColor(context, R.color.Isabelline);
                    holder.itemView.setBackgroundColor(backgroundColor);
        }

    }

    private void showDatePicker(String name) {
        Calendar calendar1=Calendar.getInstance();
        int year=calendar1.get(Calendar.YEAR);
        int month=calendar1.get(Calendar.MONTH);
        int date=calendar1.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog= new DatePickerDialog(context,(view, year1, month1, dayOfMonth) -> {
            calendar1.set(Calendar.YEAR,year1);
            calendar1.set(Calendar.MONTH,month1);
            calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            showTimePicker(name,calendar1);
        },year,month,date);
        dialog.show();
    }

    private void showTimePicker(String name,Calendar calendar1) {
        //Calendar calender = Calendar.getInstance();
        int hour =  calendar1.get(Calendar.HOUR_OF_DAY);
        int minute = calendar1.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(context, (view, hr, minn) -> {
            calendar1.set(Calendar.HOUR_OF_DAY, hr);
            calendar1.set(Calendar.MINUTE, minn);
            calendar1.set(Calendar.SECOND,0);
            setReminder(name, calendar1);
        }, hour, minute, false);
        dialog.show();
    }

    private void setReminder(String name, Calendar calendar1) {
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("Contest Name", name);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                PermissinUtils.requestScheduleExactAlarmPermission(context);
                return;
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);
            Toast.makeText(context, "Reminder set for " + name, Toast.LENGTH_SHORT).show();
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
