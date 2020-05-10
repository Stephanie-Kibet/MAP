package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    //start the stopwatch running when the start button is clicked
    public void onClickStart(View view){
        running = true;
    }

    //stops the stopwatch running when the stop button is clicked
    public void onClickStop(View view){
        running = false;
    }

    //resets the stopwatch running when the reset button is clicked
    public void onClickReset(View view){
        running = false;//stops the watch from running
        seconds = 0;//sets the seconds back to 0

    }

    //gets a reference to the text view in the layout, format the contents to hrs min and sec and displays them in the text view
    //sets the number of seconds on the timer
    private void runTimer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d" , hours, minutes, secs);
                timeView.setText(time);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }
    //Allows the time to continue running even when the configurations of the phone changes. Saving the current state method
   @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
       super.onSaveInstanceState(savedInstanceState);
       savedInstanceState.putInt("seconds", seconds);
       savedInstanceState.putBoolean("running", running);
       savedInstanceState.putBoolean("wasRunning", wasRunning);
   }

   //timer stops running when invisible
   @Override
    protected void onStop(){
        super.onStop();
        wasRunning = running;
        running = false;
   }

   //timer starts running when visible
   @Override
    protected void onStart(){
        super.onStart();
        if (wasRunning){
            running = true;
        }
   }

   //pauses the timer when another activity is visible in the foreground
    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //resumes the timer when the stopwatch activity is in the foreground
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
}
