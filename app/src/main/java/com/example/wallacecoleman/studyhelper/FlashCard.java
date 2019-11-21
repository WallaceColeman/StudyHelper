package com.example.wallacecoleman.studyhelper;

public class FlashCard {


    private String definition;
    private String word;

    public FlashCard(String word, String definition){
        this.word = word;
        this.definition = definition;
    }
    public String getWord() {
        return word;
    }
    public String getDefinition() {
        return definition;
    }

}
