package com.safe_keep.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class FakeCallActivity extends AppCompatActivity {

    private String securityQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call);

        // Retrieve the security question from intent
        Intent intent = getIntent();
        if (intent != null) {
            securityQuestion = intent.getStringExtra("securityQuestion");
        }
    }

    // Handle the accept call button click
    public void onAcceptCall(View view) {
        // Navigate to AcceptedFakeCallActivity
        Intent intent = new Intent(FakeCallActivity.this, AcceptedFakeCallActivity.class);
        intent.putExtra("securityQuestion", securityQuestion);
        startActivity(intent);
        finish();
    }

    // Handle the decline call button click
    public void onDeclineCall(View view) {
        // End the fake call activity
        finish();
    }
}
