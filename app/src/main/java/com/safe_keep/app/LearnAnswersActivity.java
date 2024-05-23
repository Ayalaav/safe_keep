package com.safe_keep.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LearnAnswersActivity extends AppCompatActivity {

    private TextView tvSelectedQuestion;
    private String securityQuestion;
    private String answerFine;
    private String answerDanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_answers);

        tvSelectedQuestion = findViewById(R.id.tv_selected_question);
        TextView answer1View = findViewById(R.id.answer1);
        TextView answer2View = findViewById(R.id.answer2);
        Button btnProceed = findViewById(R.id.btn_proceed);

        // Retrieve the security question from the previous activity
        Intent intent = getIntent();
        if (intent != null) {
            securityQuestion = intent.getStringExtra("securityQuestion");
            if (securityQuestion != null) {
                tvSelectedQuestion.setText(securityQuestion);
                setAnswers(securityQuestion);
                answer1View.setText("Answer 1: " + answerFine);
                answer2View.setText("Answer 2: " + answerDanger);
            }
        }

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            // update here so it goes back to the menu
            public void onClick(View v) {
                // Navigate to FakeCallActivity
                Intent intent = new Intent(LearnAnswersActivity.this, FakeCallActivity.class);
                intent.putExtra("securityQuestion", securityQuestion);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setAnswers(String question) {
        switch (question) {
            case "Hi! Where is the charger for the iPad?":
                answerFine = "At the kitchen";
                answerDanger = "It's in my pink bag";
                break;
            case "Do you know where I left my car keys?":
                answerFine = "They are on the table";
                answerDanger = "I think they are in the garage";
                break;
            case "Where is the dog's leash?":
                answerFine = "It's in the hallway";
                answerDanger = "It's in the closet";
                break;
            case "Did you see my headphones?":
                answerFine = "They are on the desk";
                answerDanger = "They are in my purse";
                break;
        }
    }
}
