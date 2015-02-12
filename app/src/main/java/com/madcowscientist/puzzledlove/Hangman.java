package com.madcowscientist.puzzledlove;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Hangman extends ActionBarActivity {

    //Setup info to be used at beginnning
    SharedPreferences SETUP_INFO;
    //Set global variables
    String question;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        //Get intent from previous activity
        Intent intent = getIntent();

        //Set the shared preferences for Setup
        SETUP_INFO = getSharedPreferences("SETUP_INFO", Context.MODE_PRIVATE);

        //Set the question & answer from the user preferences
        question = SETUP_INFO.getString("Hangman_Question","");
        answer = SETUP_INFO.getString("Hangman_Answer","");
        TextView questionTV = (TextView) findViewById(R.id.questionTextView);
        TextView guessTV = (TextView) findViewById(R.id.guessProgressTextView);
        //Get initial guess progress string (replaces letters with blank)
        String guess = answer;
        guess = initGuess(guess);
        questionTV.setText(question);
        guessTV.setText(Html.fromHtml(guess));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hangman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Initiate guess string with replacing letters in string
    public String initGuess(String tempStr) {
        //Blank space -> White Large Square
        String blankSpace = "\u2B1C";
        //Replace any letters with blank space
        return tempStr.replaceAll("[a-zA-Z]", blankSpace);
    }



}
