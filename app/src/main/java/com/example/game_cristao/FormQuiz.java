package com.example.game_cristao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FormQuiz extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionsTextView;
    Button bt_A, bt_B, bt_C;
    Button submitBtn;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_quiz);

        IniciarComponetes();

        bt_A.setOnClickListener(this);
        bt_B.setOnClickListener(this);
        bt_C.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total de Questôes: " + totalQuestion);

        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {

        bt_A.setBackgroundColor(Color.WHITE);
        bt_B.setBackgroundColor(Color.WHITE);
        bt_C.setBackgroundColor(Color.WHITE);

        Button clickButton = (Button) view;

        if(clickButton.getId() == R.id.bt_Escolher){

            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
            }

            currentQuestionIndex++;
            loadNewQuestion();

        }else{
            selectedAnswer = clickButton.getText().toString();
            clickButton.setBackgroundColor(Color.BLUE);
        }
    }

    void loadNewQuestion(){

        if(currentQuestionIndex == totalQuestion){
            finishQuiz();
            return;
        }

        questionsTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        bt_A.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        bt_B.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        bt_C.setText(QuestionAnswer.choices[currentQuestionIndex][2]);

    }

    void finishQuiz(){
        String passStatus = "";
        if(score > totalQuestion * 0.60){
            passStatus = "Você passou";
        }else{
            passStatus = "Você reprovou";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Acertou: " + score + " de " + totalQuestion + " questões!")
                .setPositiveButton("Reiniciar ", ((dialogInterface, i) -> restartQuiz()))
                .setCancelable(false)
                .show();
    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    private  void IniciarComponetes(){
        totalQuestionsTextView = findViewById(R.id.total_quest);
        questionsTextView = findViewById(R.id.question);
        bt_A = findViewById(R.id.bt_A);
        bt_B = findViewById(R.id.bt_B);
        bt_C = findViewById(R.id.bt_C);
        submitBtn = findViewById(R.id.bt_Escolher);
    }
}