package com.example.wallacecoleman.studyhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Questions";
    private static final int DB_VERSION = 1;

    DatabaseHelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PROBLEMS ("
                    +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"QUESTION TEXT, "
                    +"ANSWER TEXT, "
                    +"WRONGA TEXT, "
                    +"WRONGB TEXT, "
                    +"WRONGC TEXT);");

        insertProblem(db,"What is type of methods are used when a button is pressed?", "onClick", "onPress", "Clicker", "onButton");
        insertProblem(db,"How do you set a background color to white in xml?", "android:background=\"#ffffffff\"", "layout:background=\"#ffffffff\"", "background.setColor(\"WHITE\")", "background.set(\"WHITE\")");
        insertProblem(db,"In terms of mobile apps, what is an event?", "An action in an app such as a button getting pressed or a line being drawn", "A gathering of people", "Creating a variable", "Any line in the xml file that starts with android:event=");
        insertProblem(db,"Where should you put images for your app?", "The drawable folder", "Just drag from anywhere onto the layout", "The layout folder", "Anywhere in the app folder");
        insertProblem(db,"In the xml file, which of these lines would produce an error?", "android:range=\"all\"", "android:id=\"@+id/imageView\"", "android:layout_height=\"0dp\"", "android:id=\"@+id/linearLayout\"");
        insertProblem(db,"What is the name for code that supports a screen in android?", "activity", "screen", "window", "view");
        insertProblem(db,"What is the first step in the app development cycle?", "Identify need or want", "Survey target users", "Design the app", "Develop it, test it, deploy it");
        insertProblem(db,"What IDE is commonly used for Android app development?", "Android Studio", "Visual Studios", "Eclipse: Neon", "Eclipse: Oxygen");
        insertProblem(db,"What is a variable", "A container for a value", "A list of values", "A method that returns a value", "A constant");
        insertProblem(db,"What does IDE stand for?", "Integrated Design Environment", "Inside Developer Explorer", "It doesn't stand for anything", "It is the initials of the founder of Java");


        db.execSQL("CREATE TABLE TRUE_FALSE ("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"QUESTION TEXT, "
                +"ANSWER TEXT);");

        insertTrueFalse(db,"EditText is element of the user interface that can be used to enter text into the app.", "true");
        insertTrueFalse(db,"Text views automatically scroll if there isn't enough room for the text in one line.", "false");
        insertTrueFalse(db,"A SeekBar can only have up to 3 options.", "false");
        insertTrueFalse(db,"There are three types of linear layout, vertical, horizontal, and diagonal.", "false");
        insertTrueFalse(db,"You must create an intent to go to a new activity.", "true");
        insertTrueFalse(db,"Image buttons can be used the same way as regular buttons.", "true");
        insertTrueFalse(db,"Android Studio is commonly used to make Android apps.", "true");
        insertTrueFalse(db,"Android Studio is an IDE.", "true");
        insertTrueFalse(db,"You can distinguish between a tablet and a phone.", "true");
        insertTrueFalse(db,"A toast is a type of popup.", "true");

        db.execSQL("CREATE TABLE FLASH_CARDS ("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"WORD TEXT, "
                +"DEFINITION TEXT);");

        insertFlashCard(db,"Variable","A container for a value");
        insertFlashCard(db,"IDE","Integrated Design Environment");
        insertFlashCard(db,"Demo","A Display of an apps capabilities");
        insertFlashCard(db,"Button","A clickable that may have an associated method or listener");
        insertFlashCard(db,"Algorithm","precise sequence of instructions for processes executed by a computer");
        insertFlashCard(db,"Syntax Error","an error in the sequence of words or rules in a program that prevents the program from running");
        insertFlashCard(db,"Wireframe","a schematic blueprint that provides a skeletal framework for a mobile app");
        insertFlashCard(db,"Layout","the section of the palette used to determine whether the pieces of the app appear horizontally, vertically, or in a table");
        insertFlashCard(db,"Image","the element of the user interface that is used to display a picture on the screen");
        insertFlashCard(db,"Image","the element of the user interface that is used to display a picture on the screen");
        insertFlashCard(db,"TextBox","the element of the user interface that we can use to enter text into the app");
        insertFlashCard(db,"Emulator","simulates a phone or tablet that the application can run on");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void insertFlashCard(SQLiteDatabase db, String word, String definition){
        ContentValues problemValues = new ContentValues();
        problemValues.put("WORD",word);
        problemValues.put("DEFINITION",definition);
        db.insert("FLASH_CARDS", null, problemValues);
    }

    public static void insertTrueFalse(SQLiteDatabase db, String question, String answer){
        ContentValues problemValues = new ContentValues();
        problemValues.put("QUESTION",question);
        problemValues.put("ANSWER",answer);
        db.insert("TRUE_FALSE", null, problemValues);
    }

    public static void insertProblem(SQLiteDatabase db, String question, String answer, String wrongA, String wrongB, String wrongC){
        ContentValues problemValues = new ContentValues();
        problemValues.put("QUESTION",question);
        problemValues.put("ANSWER",answer);
        problemValues.put("WRONGA",wrongA);
        problemValues.put("WRONGB",wrongB);
        problemValues.put("WRONGC",wrongC);
        db.insert("PROBLEMS", null, problemValues);
    }
}
