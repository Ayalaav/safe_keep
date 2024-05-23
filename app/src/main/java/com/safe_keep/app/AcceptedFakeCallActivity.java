package com.safe_keep.app;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class AcceptedFakeCallActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private String securityQuestion;
    private static final String TAG = "AcceptedFakeCallActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_fake_call);

        // Retrieve the security question from intent
        Intent intent = getIntent();
        if (intent != null) {
            securityQuestion = intent.getStringExtra("securityQuestion");
        }

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "Language not supported");
                        Toast.makeText(AcceptedFakeCallActivity.this, "TTS language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        // Speak the security question
                        if (securityQuestion != null && !securityQuestion.isEmpty()) {
                            textToSpeech.speak(securityQuestion, TextToSpeech.QUEUE_FLUSH, null, null);
                        }
                    }
                } else {
                    Log.e(TAG, "TTS Initialization failed");
                    Toast.makeText(AcceptedFakeCallActivity.this, "TTS initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Handle the end call button click
    public void onEndCall(View view) {
        // End the fake call activity
        finish();
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
