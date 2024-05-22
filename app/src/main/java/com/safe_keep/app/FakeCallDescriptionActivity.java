package com.safe_keep.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FakeCallDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call_description);

        Button btnScheduleFakeCall = findViewById(R.id.btn_schedule_fake_call);
        Button btnMaybeLater = findViewById(R.id.btn_maybe_later);

        btnScheduleFakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FakeCallDescriptionActivity.this, ScheduleFakeCall.class);
                startActivity(intent);
            }
        });

        btnMaybeLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
