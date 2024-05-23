package com.safe_keep.app;
import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class NewDateActivity extends Activity {

    public static final int REQUEST_CALENDAR_PERMISSION = 100;
    private EditText editTextDate;
    private EditText editTextTime;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_date);

        editTextDate = findViewById(R.id.location);
        editTextTime = findViewById(R.id.time);
        buttonSave = findViewById(R.id.btnSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateString = editTextDate.getText().toString();
                String timeString = editTextTime.getText().toString();
                if (ContextCompat.checkSelfPermission(NewDateActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewDateActivity.this, new String[]{Manifest.permission.WRITE_CALENDAR}, REQUEST_CALENDAR_PERMISSION);
                } else {
                    addEventToCalendar(dateString, timeString);
                }
            }
        });

    }

    private void addEventToCalendar(String dateString, String timeString) {
        // Combine date and time strings into a single datetime string
        String combinedDateTimeString = dateString + " " + timeString;

        // Parse the combined datetime string
        SimpleDateFormat combinedDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateTime;
        try {
            dateTime = combinedDateTimeFormat.parse(combinedDateTimeString);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date or time format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set start and end time
        long startMillis = dateTime.getTime();
        long endMillis = startMillis + (60 * 60 * 1000); // 1 hour event

        // Insert event into calendar
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "New Event");
        values.put(CalendarContract.Events.DESCRIPTION, "Event Description");
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        Uri uri = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
        if (uri != null) {
            long eventID = Long.parseLong(uri.getLastPathSegment());
            Toast.makeText(this, "Event added with ID: " + eventID, Toast.LENGTH_SHORT).show();
        }
    }



}
