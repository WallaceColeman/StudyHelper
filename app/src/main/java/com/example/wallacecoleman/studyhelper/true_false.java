package com.example.wallacecoleman.studyhelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class true_false extends AppCompatActivity {

    private ArrayList<TrueFalseProblem> Problems = new ArrayList<>();
    private int num;
    private int numRight;
    private int rightLoc;
    private TextView question;
    private boolean earnPoints;
    private boolean answered;
    Random loc;
    RadioGroup answers;

    public true_false() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false);

        num = 0;
        numRight = 0;
        loc = new Random();
        earnPoints = true;
        answered = false;

        try {
            addProblems();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        Collections.shuffle(Problems);
        updateTexts();
        setProblem();
        //updateTexts();
        answers = findViewById(R.id.radioGroup);
    }



    public void checkAnswer(View view){

        if (((RadioButton)answers.getChildAt(rightLoc)).isChecked()){
            Toast.makeText(getApplicationContext(),"Correct", Toast.LENGTH_LONG).show();
            num++;
            updateScore();
            answered = true;
            for(int i = 0; i < 2; i++){
                ((RadioButton)answers.getChildAt(i)).setEnabled(false);
            }
            findViewById(R.id.check).setEnabled(false);
        }
        else{

            for(int i = 0; i < 2; i++){
                if(((RadioButton)answers.getChildAt(i)).isChecked()){
                    num++;
                    updateScore();
                    num--;
                    Toast.makeText(getApplicationContext(),"Incorrect", Toast.LENGTH_LONG).show();
                    earnPoints = false;
                    ((RadioButton)answers.getChildAt(i)).setEnabled(false);
                }
            }
            //if nothing is selected nothing should happen
            if(earnPoints){
                return;
            }
        }
        if (earnPoints){
            numRight++;
            updateScore();
        }

    }

    private void addProblems() {
        boolean testing = false;
        //Hard coded data before use of SQLite
        if(testing) {
            TrueFalseProblem temp = new TrueFalseProblem("EditText is element of the user interface that can be used to enter text into the app.", true);
            Problems.add(temp);
            temp = new TrueFalseProblem("Text views automatically scroll if there isn't enough room for the text in one line.", false);
            Problems.add(temp);

            temp = new TrueFalseProblem("A SeekBar can only have up to 3 options.", false);
            Problems.add(temp);
            temp = new TrueFalseProblem("There are three types of linear layout, vertical, horizontal, and diagonal.", false);
            Problems.add(temp);

            temp = new TrueFalseProblem("Image buttons can be used the same way as regular buttons.", true);
            Problems.add(temp);
            temp = new TrueFalseProblem("You must create an intent to go to a new activity.", true);
            Problems.add(temp);

            temp = new TrueFalseProblem("Android Studio is commonly used to make Android apps.", true);
            Problems.add(temp);
            temp = new TrueFalseProblem("Android Studio is an IDE.", true);
            Problems.add(temp);

            temp = new TrueFalseProblem("You can distinguish between a tablet and a phone programmatically.", true);
            Problems.add(temp);
            temp = new TrueFalseProblem("A toast is a type of popup.", true);
            Problems.add(temp);

            return;
        }

        try{
            SQLiteOpenHelper DBHelper = new DatabaseHelper(this);
            SQLiteDatabase db = DBHelper.getReadableDatabase();
            Cursor cursor = db.query("TRUE_FALSE", new String[] {"QUESTION","ANSWER"},null,null,null,null,null);
            cursor.moveToFirst();
            String question;
            String answer;
            TrueFalseProblem prob;
            boolean answerTF;
            do{
                question = cursor.getString(0);
                answer = cursor.getString(1);

                if(answer.contains("true")){
                    answerTF = true;
                }
                else{
                    answerTF = false;
                }

                prob = new TrueFalseProblem(question,answerTF);
                Problems.add(prob);
            }while(cursor.moveToNext());
        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void getNext(View view){
        if(!answered){
            Toast.makeText(getApplicationContext(),"You must answer the question correctly", Toast.LENGTH_LONG).show();
            return;
        }
        findViewById(R.id.check).setEnabled(true);
        updateTexts();
        for(int i = 0; i < 2; i++){
            if (((RadioButton)answers.getChildAt(i)).isChecked()){

            }
        }
        earnPoints = true;
        resetButtons();
        setProblem();
        answered = false;
    }

    public void goHome(View view){
        Intent intent = new Intent(true_false.this,Home.class);
        startActivity(intent);
    }

    public void resetButtons(){
        answers.setVisibility(View.VISIBLE);
        answers.clearCheck();
        for(int i = 0; i < 2;i++){
//            if(((RadioButton)answers.getChildAt(i)).isChecked()){
//                ((RadioButton)answers.getChildAt(i)).setChecked(false);
//            }
            ((RadioButton)answers.getChildAt(i)).setEnabled(true);
        }
    }

    public void removeButtons(){
        for(int i = 0; i < 2;i++){
            answers.setVisibility(View.INVISIBLE);
        }
    }

    private void updateTexts(){
        TextView QuestionNumber = findViewById(R.id.questionNumber);
        QuestionNumber.setText((String.format("Question Number: %d", (num+1))));
    }

    private void updateScore(){
        TextView Score = findViewById(R.id.score);
        Score.setText(String.format("Score: %d/%d", numRight, num));
    }

    private void setProblem(){
        answers = findViewById(R.id.radioGroup);
        question = findViewById(R.id.questionText);
        if(num >= Problems.size()){
            endOfQuestions();
            return;
        }

        TrueFalseProblem temp = Problems.get(num);
        question.setText(temp.getQuestion());

        Boolean right = temp.getAnswer();

        if(right){
            rightLoc = 0;
        }
        else{
            rightLoc = 1;
        }


    }

    private void endOfQuestions() {

        //Grades score on a percent, each letter grade has different feedback

        String grade;
        if(numRight == num){
            grade = "Perfect";
        }
        else if(((double)numRight)/num >= 0.9){
            grade = "Excellent";
        }
        else if(((double)numRight)/num >= 0.8){
            grade = "Good";
        }
        else if(((double)numRight)/num >= 0.7){
            grade = "Adequate";
        }
        else if(((double)numRight)/num >= 0.6){
            grade = "Needs Improvement";
        }
        else{
            grade = "Poor";
        }

        question.setText(String.format("Final Score: %d/%d\n\n%s", numRight, num, grade));
        Button next = findViewById(R.id.next);
        Button check = findViewById(R.id.check);
        next.setEnabled(false);
        check.setEnabled(false);
        removeButtons();

        TextView x = findViewById(R.id.questionNumber);
        x.setText(String.format("Question Number: %d", num));
    }
}
