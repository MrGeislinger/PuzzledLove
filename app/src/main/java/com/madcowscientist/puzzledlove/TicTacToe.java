package com.madcowscientist.puzzledlove;

import android.content.Intent;
import android.os.Handler;
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


public class TicTacToe extends ActionBarActivity {

    //Global definitions
    int PLAYER_X = 0; //X goes first
    int PLAYER_O = 1;
    int[] PLAYERS = {PLAYER_X,PLAYER_O};
    String PLAYER_X_STRING = "X";
    String PLAYER_O_STRING = "O";
    String[] PLAYER_STRINGS = {PLAYER_X_STRING,PLAYER_O_STRING};
    int PLAYING = PLAYER_X; //X goes first
    boolean isSinglePlayer = true; //playing against computer (note PLAYER_X is human user)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        //Get intent from previous activity
        Intent intent = getIntent();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tic_tac_toe, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);
            return rootView;
        }
    }

    /** Make a computer move **/
    public void computerPlay() {

    }

    /** Changes PLAYING **/
    public void changePlayingPlayer() {
        //Change to next player
        PLAYING = PLAYERS[(PLAYING + 1) % 2];
        //If a single player, check if computer's move
        if(isSinglePlayer && (PLAYING == PLAYER_O) ) {
            //Wait some time (?)
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Wait 500ms before playing
                    //Computer plays
                    computerPlay();
                    //Change player
                    changePlayingPlayer();
                }
            }, 500);

        }
    }


    /** Called when the user clicks the button_ticTacToe# */
    public void playTicTacToeSpace(View view) {
        //Get text of the button clicked
        final Button buttonPressed = (Button) view;
        String buttonValue = buttonPressed.getText().toString();

        //Check that space isn't already taken
        if(buttonValue.equals(PLAYER_X_STRING) || buttonValue.equals(PLAYER_O_STRING)) {
            //End execution since this space has been played
            return;
        }
        //Single Player (not their turn yet)
        if(isSinglePlayer && (PLAYING != PLAYER_X) ) {
            //Computer's turn so stop execution
            return;

        }
        //Human's turn or Both Human Players
        //Change button value depending who's turn it is (space not already played)
        buttonPressed.setText(PLAYER_STRINGS[PLAYING]);
        //Check for winner
        //Change to next player
        changePlayingPlayer();


    }

}
