package com.safe_keep.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DoYouWantFakeCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_you_want_fake_call);

        TextView tvWhatIsFakeCall = findViewById(R.id.tv_what_is_fake_call);
        tvWhatIsFakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoYouWantFakeCall.this, FakeCallDescriptionActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method called when the "Yes" button is clicked
    public void onYesClick(View view) {
        Intent intent = new Intent(this, ScheduleFakeCall.class);
        startActivity(intent);
    }

    // Method called when the "No" button is clicked
    public void onNoClick(View view) {
        // Optionally, handle the "No" button click here
        finish(); // Close the activity if "No" is clicked
    }
}