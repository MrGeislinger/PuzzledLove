package com.madcowscientist.puzzledlove;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.*;


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
    ArrayList<Button> availableMoves = new ArrayList<Button>();  //list of available moves
    Random rand = new Random();

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
    protected void onStart() {
        super.onStart();
        //Add  to availableMoves list
        String buttonIdBase = "button_ticTacToe";
        String buttonId = "";
        int resID = 0;
        for(int i=0;i<9;i++){
            //Make the button id
            buttonId = buttonIdBase+i;
            resID = getResources().getIdentifier(buttonId, "id", getPackageName());
            //Add into list
            availableMoves.add((Button) findViewById(resID));
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

    /** General function to check if there is a winner for a given line
     * Input: starting position and increment
     * Output: null if no winner, else the button 'owned' by winner
     */
    public Button checkWin(int startPos, int incBy){
        //Default winner
        Button winner = availableMoves.get(startPos);
        //Check that it's not blank
        if(winner.getText().toString().equals("")){
            return null;
        }
        //Check with the starting position
        for(int i=startPos;i<(startPos+3*incBy);i+=incBy) {
            //Assume a win until the next one doesn't match
            if(!winner.getText().equals( availableMoves.get(i).getText() )){
                //Next spot doesn't match; must be a loser
                return null;
            }
        }
        //No  player has won, set to null
        return winner;
    }

    /* Returns the button that won */
    public Button winBy(String winType){
        Button winner = null;
        boolean hasWon = false;

        //Win on Diagonal
        if(winType.equals("diagonal")){
            //Start at left-top
            winner = checkWin(0,4);
            //Break out if won, else check next
            if(winner != null){
                return winner;
            }
            //Start at right-top
            winner = checkWin(2,2);
            //Return winner if won
            if(winner != null){
                return winner;
            }
        }
        //Win on row
        else if(winType.equals("row")){
            //test for each row
            for(int rowStart=0;rowStart<9; rowStart+=3){
                //Test the whole row
                winner = checkWin(rowStart,1);
                //Return winner if won, else check next
                if(winner != null){
                    return winner;
                }
            }
        }
        //Win on column
        else if(winType.equals("column")){
            //test for each column
            for(int colStart=0;colStart<4; colStart++){
                //Test the whole column
                winner = checkWin(colStart,3);
                //Return winner if won, else check next
                if(winner != null){
                    return winner;
                }
            }
        }
        //Mistake in input
        else{
            winner = null;
        }
        //No winner found (returning null)
        return winner;
    }

    /** Game logic for when game has been won **/
    public boolean gameWon(View v){
        //Check if won by anyone (by any means)
        String winner;
        Button winnerDiag,winnerRow,winnerCol;
        winnerDiag = winBy("diagonal");
        winnerRow  = winBy("row");
        winnerCol  = winBy("column");

        if(winnerDiag != null){
            winner = winnerDiag.getText().toString();
        } else if(winnerRow != null){
            winner = winnerRow.getText().toString();
        } else if(winnerCol != null){
            winner = winnerCol.getText().toString();
        } else { //no winner
            return false;
        }
        System.out.println("============Won==============");
        //Display winning message
        AlertDialog alertDialog = new AlertDialog.Builder(TicTacToe.this).create();
        //(Human) X won in single player mode
        if(winner.equals("X") && isSinglePlayer) {
            alertDialog.setTitle("You Won!");
            alertDialog.setMessage("You won the game! You can got play another game now!");
        }
        //X won in 2 player game
        else if(winner.equals("X") && !isSinglePlayer){
            alertDialog.setTitle("Winner!");
            alertDialog.setMessage("Player 1 has won the game!!!");
        }
        //Computer won
        else if(isSinglePlayer) {
            alertDialog.setTitle("Better luck next time...");
            alertDialog.setMessage("Sorry, but the computer won the game...");
        }
        //(Human) O won in 2 player game
        else {
            alertDialog.setTitle("Winner!");
            alertDialog.setMessage("Player 2 has won the game!!!");
        }
        //Game was won (show dialog)
        alertDialog.show();
        return true;
    }
    /** Make a computer move **/
    public Button computerPlay() {
        Button spacePlayed = null;
        boolean taken = true;
        //Takes the first available move
        for(Button move : availableMoves) {
            taken = move.getText().toString().equals(PLAYER_O_STRING) ||
                    move.getText().toString().equals(PLAYER_X_STRING);
            if (!taken) {
                spacePlayed = move;
                break;
            }
        }
        //Check if there are no spaces left (halt execution)
        if(spacePlayed == null){
            return null;
        }
        //Change text
        spacePlayed.setText(PLAYER_STRINGS[PLAYING]);
        //Return what move was played
        return spacePlayed;
    }

    /** Changes PLAYING **/
    public void changePlayingPlayer(final View view, Button spacePlayed) {
        //Check for won game
        if( gameWon(view) ) {
            //Stop game execution
            return;
        };
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
                    Button chosenSpace = computerPlay();
                    //Change player
                    changePlayingPlayer(view,chosenSpace);
                }
            }, 1000);

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
        //Change to next player
        changePlayingPlayer(view,buttonPressed);


    }

}
