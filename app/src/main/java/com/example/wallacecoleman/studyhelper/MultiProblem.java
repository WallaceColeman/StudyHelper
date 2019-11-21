package com.example.wallacecoleman.studyhelper;

import java.util.ArrayList;
import java.util.List;

public class MultiProblem {
    private String Question;
    private String Answer;
    private String Wrong1;
    private String Wrong2;
    private String Wrong3;
    public MultiProblem(String question, String answer, String a, String b, String c){
        Question = question;
        Answer = answer;
        Wrong1 = a;
        Wrong2 = b;
        Wrong3 = c;
    }

    public String getQuestion(){
        return Question;
    }

    public String getAnswer(){
        return Answer;
    }

    public List<String> getAllWrong(){
        List<String> temp = new ArrayList<>();
        temp.add(Wrong1);
        temp.add(Wrong2);
        temp.add(Wrong3);
        return temp;
    }

    public String getWrong1(){
        return Wrong1;
    }

    public String getWrong2(){
        return Wrong2;
    }

    public String getWrong3(){
        return Wrong3;
    }
}
