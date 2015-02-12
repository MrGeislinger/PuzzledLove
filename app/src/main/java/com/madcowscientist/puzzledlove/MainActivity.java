package com.madcowscientist.puzzledlove;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    //Unlocks Preferences
    SharedPreferences UNLOCKED_LEVELS;
    //Setup info to be used at beginnning
    SharedPreferences SETUP_INFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //Set the shared preferences
        UNLOCKED_LEVELS = getSharedPreferences("UNLOCKED_LEVELS", Context.MODE_PRIVATE);
        Editor UnlockedEditor = UNLOCKED_LEVELS.edit();

        //Check if the preferences have been set
        if (!UNLOCKED_LEVELS.contains("TicTacToeUNLOCKED")) {
            UnlockedEditor.putBoolean("TicTacToeUNLOCKED", true);
            UnlockedEditor.putBoolean("TicTacToeMediaUNLOCKED", false);
            UnlockedEditor.putBoolean("HangmanUNLOCKED", false);
            UnlockedEditor.putBoolean("HangmanMediaUNLOCKED", false);
            UnlockedEditor.commit();
        }

        //Set the shared preferences for Setup
        SETUP_INFO = getSharedPreferences("SETUP_INFO", Context.MODE_PRIVATE);
        Editor SetupEditor = SETUP_INFO.edit();

        //Checks setup
        if (!SETUP_INFO.contains("User")) {
            SetupEditor.putString("User", null);
            SetupEditor.putString("Lover", null);
            SetupEditor.putString("Hangman_Question", "Question not set");
            SetupEditor.putString("Hangman_Answer", null);
        }

        //Ask user to input setup info (for the first time)
        setInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    /** Called when the user clicks the levelsButton button */
    public void goToLevels(View view) {
        Intent intent = new Intent(this, LevelsActivity.class);
        /*
        //From guide at: developer.android.com/training/basics/firstapp/starting-activity.html
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        */
        startActivity(intent);
    }


    public void setInfo() {
        //Set the shared preferences for Setup
        SETUP_INFO = getSharedPreferences("SETUP_INFO", Context.MODE_PRIVATE);
        final Editor SetupEditor = SETUP_INFO.edit();


        //Checks if setup already submitted
        if (SETUP_INFO.getString("User", null) != null) {
            //break out if already filled out
            return;
        }
        else {
            //Create a dialog for user input
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            alert.setView(inflater.inflate(R.layout.dialog_setup, null))
                    // Add action buttons
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                        }
                    });

            alert.setTitle("Fill out for your lover");
//            alert.setMessage("Message");

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Dialog alert = (Dialog) dialog;
                    EditText userText = (EditText) alert.findViewById(R.id.inputUser);
                    EditText loverText = (EditText) alert.findViewById(R.id.inputLover);
                    EditText questionText = (EditText) alert.findViewById(R.id.inputHangmanQuestion);
                    EditText answerText = (EditText) alert.findViewById(R.id.inputHangmanQuestion);

//                    SetupEditor.putString("User", userText.getText().toString());
                    SetupEditor.putString("Lover", loverText.getText().toString());
                    SetupEditor.putString("Hangman_Question", questionText.getText().toString());
                    SetupEditor.putString("Hangman_Question", answerText.getText().toString());
                    SetupEditor.commit();
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Cancel
                    SetupEditor.putString("User", null);
                    SetupEditor.commit();
                }
            });

            alert.create();
            alert.show();


        }


    }
}
