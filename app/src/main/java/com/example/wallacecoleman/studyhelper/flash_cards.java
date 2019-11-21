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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class flash_cards extends AppCompatActivity {

    String def;
    String word;
    boolean wordSide;
    int num;
    Button card;
    boolean end;

    List<FlashCard> cards = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_cards);
        end = false;
        num = 0;
        wordSide = true;
        getCards();

        Collections.shuffle(cards);

        word = cards.get(num).getWord();
        def = cards.get(num).getDefinition();

        card = findViewById(R.id.card);

        card.setText(word);



    }

    public void getNext(View view){

        num++;

        if(num >= cards.size()){
            endOfCards();
            return;
        }

        wordSide = true;


        word = cards.get(num).getWord();
        def = cards.get(num).getDefinition();
        card.setText(word);

    }

    private void endOfCards() {
        card.setText("Start Over?");
        end = true;
    }

    public void flip(View view){

        if(end){
            num = 0;
            end = false;
            wordSide = false;
        }

        if(wordSide){
            card.setText(def);
            wordSide = false;
        }
        else{
            card.setText(word);
            wordSide = true;
        }
    }

    public void getCards(){
        Boolean testing = false;
        //Hard coded data before use of SQLite
        if(testing){
            FlashCard temp = new FlashCard("Variable","A container for a value");
            cards.add(temp);
            temp = new FlashCard("IDE","Integrated Design Environment");
            cards.add(temp);

            temp = new FlashCard("Demo","A Display of an apps capabilities");
            cards.add(temp);
            temp = new FlashCard("Button","A clickable that may have an associated method or listener");
            cards.add(temp);

            temp = new FlashCard("Algorithm","precise sequence of instructions for processes executed by a computer");
            cards.add(temp);
            temp = new FlashCard("Syntax Error","an error in the sequence of words or rules in a program that prevents the program from running");
            cards.add(temp);

            temp = new FlashCard("Wireframe","a schematic blueprint that provides a skeletal framework for a mobile app");
            cards.add(temp);
            temp = new FlashCard("Layout","the section of the palette used to determine whether the pieces of the app appear horizontally, vertically, or in a table");
            cards.add(temp);

            temp = new FlashCard("Image","the element of the user interface that is used to display a picture on the screen");
            cards.add(temp);
            temp = new FlashCard("TextBox","the element of the user interface that we can use to enter text into the app");
            cards.add(temp);

            temp = new FlashCard("Emulator","simulates a phone or tablet that the application can run on");
            cards.add(temp);
            return;
        }
        try{
            SQLiteOpenHelper DBHelper = new DatabaseHelper(this);
            SQLiteDatabase db = DBHelper.getReadableDatabase();
            Cursor cursor = db.query("FLASH_CARDS", new String[] {"WORD","DEFINITION"},
                    null,null,null,null,null);
            cursor.moveToFirst();
            String word;
            String def;
            FlashCard temp;
            do{
                word = cursor.getString(0);
                def = cursor.getString(1);
                temp = new FlashCard(word,def);
                cards.add(temp);
            }while(cursor.moveToNext());
        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void goHome(View view){
        Intent intent = new Intent(flash_cards.this,Home.class);
        startActivity(intent);
    }
}
