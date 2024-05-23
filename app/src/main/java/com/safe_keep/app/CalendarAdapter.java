package com.safe_keep.app;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        LocalDate date = days.get(position);

        if (date != null) {
            // Setting the day of month
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

            // Setting background color based on selected date
            if (date.equals(CalendarUtils.selectedDate)) {
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            } else {
                holder.parentView.setBackgroundColor(Color.TRANSPARENT);
            }

            // Setting text color based on whether it's the same month as the selected date
            if (CalendarUtils.selectedDate != null && date.getMonthValue() == CalendarUtils.selectedDate.getMonthValue()) {
                holder.dayOfMonth.setTextColor(Color.BLACK);
            } else {
                holder.dayOfMonth.setTextColor(Color.LTGRAY);
            }
        } else {
            // If date is null, setting empty text and transparent background
            holder.dayOfMonth.setText("");
            holder.parentView.setBackgroundColor(Color.TRANSPARENT);
            holder.dayOfMonth.setTextColor(Color.LTGRAY);
        }
    }



    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
