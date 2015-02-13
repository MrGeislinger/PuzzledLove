package com.madcowscientist.puzzledlove;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;

import static com.madcowscientist.puzzledlove.R.drawable.unlock;


public class LevelsActivity extends ActionBarActivity {

    //Unlocks Preferences
    public SharedPreferences UNLOCKED_LEVELS;

    //Array of level strings
    String[] LevelStrings = {
        "TicTacToe",
        "Hangman"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        //Get intent from previous activity
        Intent intent = getIntent();
        System.out.println("Create=====================");
        setButtonImages();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_levels, menu);
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

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Start=====================");
        setButtonImages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resume=====================");
        setButtonImages();
    }

    /** Generic call to the next game activity */
    public void goToGame(View view, String className) {
        Class<?> myClass = null;
        if(className != null) {
            className = "com.madcowscientist.puzzledlove." + className;
            try {
                myClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Intent intent;
            intent = new Intent(this, myClass);
            startActivity(intent);
        }
    }

    /** Generic call to the next game activity */
    public void goToMedia(View view, String className) {
        Class<?> myClass = null;
        if(className != null) {
            className = "com.madcowscientist.puzzledlove." + className;
            try {
                myClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Intent intent;
            intent = new Intent(this, myClass);
            startActivity(intent);
        }
    }

    //Set button images (and onClick methods) based on unlocked status
    public void setButtonImages() {
        //Set the shared preferences
        UNLOCKED_LEVELS = getSharedPreferences("UNLOCKED_LEVELS", Context.MODE_PRIVATE);

        //Check the unlocked preferences to update the (game & media) buttons
        int resGameID, resMediaID;
        ImageButton tempGameButton, tempMediaButton;
        for(String level : LevelStrings) {
            final String tempStr = level;
            //Set game & media button
            resGameID = getResources().getIdentifier(
                    "button_goTo" + level, "id", getPackageName());
            resMediaID = getResources().getIdentifier(
                    "button_goTo" + level + "Media", "id", getPackageName());
            tempGameButton = (ImageButton) findViewById(resGameID);
            tempMediaButton = (ImageButton) findViewById(resMediaID);

            //Check game buttons
            //Set to unlocked icon
            if(UNLOCKED_LEVELS.getBoolean(level + "UNLOCKED", false)) {
                tempGameButton.setImageResource(R.drawable.unlock);
                //Set intent to go to the game
                tempGameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToGame(view,tempStr);
                    }
                });

            }
            //Set to locked icon
            else {
                tempGameButton.setImageResource(R.drawable.lock);
                //Set intent to do nothing on button click
                tempGameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Set to no action on click
                    }
                });

            }

            //Check media buttons
            //Set to locked icon
            if(UNLOCKED_LEVELS.getBoolean(level + "MediaUNLOCKED", false)) {
                tempMediaButton.setImageResource(R.drawable.play);
                //Set intent to go to the media content
                tempMediaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //View the media content
                        goToMedia(view,tempStr+"Media");
                    }
                });
            }
            //Set to unlocked media icon
            else {
                tempMediaButton.setImageResource(R.drawable.lock);
                //Set intent to do nothing on button click
                tempMediaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Set to no action on click
                    }
                });
            }
        }
    }

}
