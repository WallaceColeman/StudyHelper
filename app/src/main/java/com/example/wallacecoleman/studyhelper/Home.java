package com.example.wallacecoleman.studyhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

    }

    public void MultipleChoice(View view){
        Intent intent = new Intent(Home.this, multi_choice.class);
        startActivity(intent);
    }

    public void TrueFalse(View view){
        Intent intent = new Intent(Home.this, true_false.class);
        startActivity(intent);
    }

    public void FlashCards(View view){
        Intent intent = new Intent(Home.this, flash_cards.class);
        startActivity(intent);
    }
}
