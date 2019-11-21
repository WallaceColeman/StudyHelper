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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;


public class multi_choice extends AppCompatActivity {

    private ArrayList<MultiProblem> Problems = new ArrayList<>();
    private int num;
    private int numRight;
    private int rightLoc;
    private TextView question;
    private boolean earnPoints;
    private boolean answered;
    Random loc;
    RadioGroup answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_choice);
        num = 0;
        numRight = 0;
        loc = new Random();
        earnPoints = true;
        answered = false;
        addProblems();
        Collections.shuffle(Problems);
        updateTexts();
        setProblem();
        answers = findViewById(R.id.radioGroup);

    }

    //Gets the problem and updates all the textviews
    //and radiobuttons that need to be updated
    private void setProblem(){
        answers = findViewById(R.id.radioGroup);
        question = findViewById(R.id.questionText);

        //If the questions have been exhausted
        //show the final score
        if(num >= Problems.size()){
            endOfQuestions();
            return;
        }

        //Get answers and shuffle locations so that the
        //answer is not always in the same place
        MultiProblem temp = Problems.get(num);
        question.setText(temp.getQuestion());
        String right = temp.getAnswer();
        List<String> Wrong = Problems.get(num).getAllWrong();
        Collections.shuffle(Wrong);
        rightLoc = loc.nextInt(4);

        int j = 0;
        for(int i = 0; i < 4; i++,j++){
            if(i == rightLoc){
                ((RadioButton)answers.getChildAt(i)).setText(right);
                j--;
                continue;
            }

            ((RadioButton)answers.getChildAt(i)).setText(Wrong.get(j));
        }


    }

    //Grades score on a percent,
    //each letter grade has different feedback
    private void endOfQuestions() {

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
        TextView x = findViewById(R.id.questionNumber);
        x.setText(String.format("Question Number: %d", num));
        next.setEnabled(false);
        check.setEnabled(false);
        removeButtons();
    }

    private void addProblems() {

        boolean testing = false;
        //Hard coded data before use of SQLite
        if(testing){
            MultiProblem prob = new MultiProblem("What is type of methods are used when a button is pressed?", "onClick", "onPress", "Clicker", "onButton");
            Problems.add(prob);
            prob = new MultiProblem("How do you set a background color to white in xml?", "android:background=\"#ffffffff\"", "layout:background=\"#ffffffff\"", "background.setColor(\"WHITE\")", "background.set(\"WHITE\")");
            Problems.add(prob);
            prob = new MultiProblem("In terms of mobile apps, what is an event?", "An action in an app such as a button getting pressed or a line being drawn", "A gathering of people", "Creating a variable", "Any line in the xml file that starts with android:event=");
            Problems.add(prob);
            prob = new MultiProblem("Where should you put images for your app?", "The drawable folder", "Just drag from anywhere onto the layout", "The layout folder", "Anywhere in the app folder");
            Problems.add(prob);
            prob = new MultiProblem("In the xml file, which of these lines would produce an error?", "android:range=\"all\"", "android:id=\"@+id/imageView\"", "android:layout_height=\"0dp\"", "android:id=\"@+id/linearLayout\"");
            Problems.add(prob);
            prob = new MultiProblem("What is the name for code that supports a screen in android?", "activity", "screen", "window", "view");
            Problems.add(prob);
            prob = new MultiProblem("What is the first step in the app development cycle?", "Identify need or want", "Survey target users", "Design the app", "Develop it, test it, deploy it");
            Problems.add(prob);
            prob = new MultiProblem("What IDE is commonly used for Android app development?", "Android Studio", "Visual Studios", "Eclipse: Neon", "Eclipse: Oxygen");
            Problems.add(prob);
            prob = new MultiProblem("What is a variable", "A container for a value", "A list of values", "A method that returns a value", "A constant");
            Problems.add(prob);
            prob = new MultiProblem("What does IDE stand for?", "Integrated Design Environment", "Inside Developer Explorer", "It doesn't stand for anything", "It is the initials of the founder of Java");
            Problems.add(prob);

            return;
        }

        try{
            SQLiteOpenHelper DBHelper = new DatabaseHelper(this);
            SQLiteDatabase db = DBHelper.getReadableDatabase();
            Cursor cursor = db.query("PROBLEMS", new String[] {"QUESTION","ANSWER","WRONGA","WRONGB","WRONGC"},
                    null,null,null,null,null);
            cursor.moveToFirst();
            String question;
            String answer;
            String wrongA;
            String wrongB;
            String wrongC;
            MultiProblem prob;
            do{
                question = cursor.getString(0);
                answer = cursor.getString(1);
                wrongA = cursor.getString(2);
                wrongB = cursor.getString(3);
                wrongC = cursor.getString(4);
                prob = new MultiProblem(question,answer,wrongA,wrongB,wrongC);
                Problems.add(prob);
            }while(cursor.moveToNext());
        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void checkAnswer(View view){

        if (((RadioButton)answers.getChildAt(rightLoc)).isChecked()){
            Toast.makeText(getApplicationContext(),"Correct", Toast.LENGTH_LONG).show();
            num++;
            updateScore();
            answered = true;
            for(int i = 0; i < 4; i++){
                ((RadioButton)answers.getChildAt(i)).setEnabled(false);
            }
            findViewById(R.id.check).setEnabled(false);
        }
        else{

            for(int i = 0; i < 4; i++){
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

    public void getNext(View view){
        if(!answered){
            Toast.makeText(getApplicationContext(),"You must answer the question correctly", Toast.LENGTH_LONG).show();
            return;
        }
        findViewById(R.id.check).setEnabled(true);
        updateTexts();
        for(int i = 0; i < 4; i++){
            if (((RadioButton)answers.getChildAt(i)).isChecked()){

            }
        }
        earnPoints = true;
        answered = false;
        resetButtons();
        setProblem();

    }

    public void goHome(View view){
        Intent intent = new Intent(multi_choice.this,Home.class);
        startActivity(intent);
    }

    public void resetButtons(){
        answers.setVisibility(View.VISIBLE);
        answers.clearCheck();
        for(int i = 0; i < 4;i++){
//            if(((RadioButton)answers.getChildAt(i)).isChecked()){
//                ((RadioButton)answers.getChildAt(i)).setChecked(false);
//            }
            ((RadioButton)answers.getChildAt(i)).setEnabled(true);
        }
    }

    public void removeButtons(){
        for(int i = 0; i < 4;i++){
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
}
