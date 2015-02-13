package com.madcowscientist.puzzledlove;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.VideoView;


public class HangmanMedia extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe_media);

        //Get intent from previous activity
        Intent intent = getIntent();
        VideoView videoView = (VideoView)findViewById(R.id.VideoView);
        //MediaController mediaController = new MediaController(this);
        // mediaController.setAnchorView(videoView);
        //videoView.setMediaController(mediaController);

        //
        //Set the shared preferences
        SharedPreferences SETUP_INFO = getSharedPreferences("SETUP_INFO", Context.MODE_PRIVATE);
        String temp = SETUP_INFO.getString("Hangman_Media", null);
        try {
            //Get the path to video saved
            videoView.setVideoPath(temp);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        videoView.start();
    }
}
