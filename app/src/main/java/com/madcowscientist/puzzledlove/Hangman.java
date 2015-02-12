package com.madcowscientist.puzzledlove;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Hangman extends ActionBarActivity {

    //Setup info to be used at beginnning
    SharedPreferences SETUP_INFO;
    //Set global variables
    String question;
    String answer;
    String guess;
    String blankSpace = "\u2B1C"; //Blank space -> White Large Square
    int wrongGuesses = 0; //number of wrong guess

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        //Get intent from previous activity
        Intent intent = getIntent();

        //Set the shared preferences for Setup
        SETUP_INFO = getSharedPreferences("SETUP_INFO", Context.MODE_PRIVATE);

        //Set the question & answer from the user preferences (all capitals)
        question = SETUP_INFO.getString("Hangman_Question","").toUpperCase();
        answer = SETUP_INFO.getString("Hangman_Answer","").toUpperCase();
        TextView questionTV = (TextView) findViewById(R.id.questionTextView);
        TextView guessTV = (TextView) findViewById(R.id.guessProgressTextView);
        //Get initial guess progress string (replaces letters with blank)
        guess = answer;
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
        //Replace any letters with blank space
        return tempStr.replaceAll("[a-zA-Z]", blankSpace);
    }

    //Run game logic on button press
    public void guessMade(View v) {
        //Get letter from user guess (make capital
        TextView guessView = (TextView) findViewById(R.id.letterGuess);
        String myGuess = guessView.getText().toString().toUpperCase();
        //Check if no letter was given
        //Check that letter was actually given
        //Guess was correct
        if(guessWasRight(myGuess)) {
            //Check that guess was a letter
            if(myGuess.matches("[A-Z]+")) {
                //Update guess progress
                updateGuessProgress(myGuess);
            }
            //Guess was not a letter
            else {
                return;
            }

        }
        //Guess was incorrect
            //Increase number of wrong guesses
            //Update screen (check if total lost)
        //Reset guess box
    }

    //Test if guess was correct
    public boolean guessWasRight(String guessStr) {
        //Default that the guess was wrong
        boolean correct = false;
        //Guess is in answer
        if( answer.contains(guessStr) ) {
            correct = true;
        }
        return correct;
    }

    //
    public void updateGuessProgress(String guessString) {
        int foundIndex = 0 ,lastIndex = 0;
        //Find all places where guess matches letter in answer
        while(foundIndex != -1) {
            System.out.println(guess);
            foundIndex = answer.indexOf(guessString,lastIndex);
            lastIndex = foundIndex+1;
            if( foundIndex != -1){
                System.out.println("Gues:" + guess+"\n");
                //Replace at index
                guess = guess.substring(0,foundIndex) +
                        guessString +
                        guess.substring(lastIndex);
                //Update guess progress
                TextView guessTV = (TextView) findViewById(R.id.guessProgressTextView);
                guessTV.setText(Html.fromHtml(guess));
            }

        }
    }

}
