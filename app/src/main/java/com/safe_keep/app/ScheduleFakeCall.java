package com.safe_keep.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;

public class ScheduleFakeCall extends AppCompatActivity {

    private TimePicker timePicker;
    private Button scheduleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_fake_call);

        timePicker = findViewById(R.id.time_picker);
        scheduleButton = findViewById(R.id.btn_schedule_call);

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleFakeCall();
            }
        });
    }

    private void scheduleFakeCall() {
        // Retrieve the selected time from the TimePicker
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        // Store the scheduled time for the fake call if needed

        // Navigate to SecretCodeQuestionsActivity
        Intent intent = new Intent(ScheduleFakeCall.this, SelectSecurityQuestionActivity.class);
        startActivity(intent);
    }
}
