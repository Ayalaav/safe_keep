package com.safe_keep.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SelectSecurityQuestionActivity extends AppCompatActivity {

    private RadioGroup rgSecurityQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_code_questions);

        rgSecurityQuestions = findViewById(R.id.rg_security_questions);
        Button btnSelectQuestion = findViewById(R.id.btn_select_question);

        btnSelectQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgSecurityQuestions.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedQuestion = selectedRadioButton.getText().toString();
                    saveSelectedQuestion(selectedQuestion);
                    Toast.makeText(SelectSecurityQuestionActivity.this, "Question Selected: " + selectedQuestion, Toast.LENGTH_SHORT).show();

                    // Navigate to LearnAnswersActivity
                    Intent intent = new Intent(SelectSecurityQuestionActivity.this, LearnAnswersActivity.class);
                    intent.putExtra("selectedQuestion", selectedQuestion);
                    startActivity(intent);

                } else {
                    Toast.makeText(SelectSecurityQuestionActivity.this, "Please select a question", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveSelectedQuestion(String question) {
        // Save the selected question to shared preferences or database
        // For this example, we'll just use a Toast message
        Toast.makeText(this, "Selected question saved: " + question, Toast.LENGTH_SHORT).show();
    }
}
