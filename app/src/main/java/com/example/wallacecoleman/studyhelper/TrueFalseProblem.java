package com.example.wallacecoleman.studyhelper;

public class TrueFalseProblem {


    private String question;
    private Boolean answer;

    TrueFalseProblem(String question, Boolean answer){
        this.question = question;
        this.answer = answer;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
}
