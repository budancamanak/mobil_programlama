package tr.yildiz.edu.l1108080.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import tr.yildiz.edu.l1108080.R;
import tr.yildiz.edu.l1108080.activities.question.AddQuestionActivity;
import tr.yildiz.edu.l1108080.activities.question.ListQuestionsActivity;

public class DashboardActivity extends AppCompatActivity {

    Button btnAddQuestion, btnListQuestions, btnAddExam, btnListExams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnListQuestions = findViewById(R.id.btnListQuestions);
        btnAddExam = findViewById(R.id.btnAddExam);
        btnListExams = findViewById(R.id.btnListExams);

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
                startActivity(intent);
            }
        });
        btnListQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListQuestionsActivity.class);
                startActivity(intent);
            }
        });
    }
}