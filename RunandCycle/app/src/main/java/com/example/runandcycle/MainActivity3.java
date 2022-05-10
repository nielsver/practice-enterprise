package com.example.runandcycle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {
   /* TextView timertext;
    Button finish;
    Button stop;
    Timer timer;
    TimerTask timertask;
    Double time = 0.0;
    private boolean timerRunning = true;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_runpage);
        getSupportActionBar().setTitle("recording");

        /*
        timertext = findViewById(R.id.timer);
        finish = findViewById(R.id.finish);
        stop = findViewById(R.id.stop);
        timer = new Timer();
        startTimer();
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopTapped(view);
            }
        });*/
    }

/*
    public void startStopTapped(View view) {
        if (!timerRunning) {
            timerRunning = true;
            stop.setText("@string/Stop");
            startTimer();
        } else {
            timerRunning = false;
            stop.setText("@string/Start");
            timertask.cancel();
        }
    }

    private void startTimer() {
        timertask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timertext.setText(getTimerText());
                    }
                });

            }
        };
        timer.scheduleAtFixedRate(timertask, 0, 1000);

    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours + " : " + "%02d", minutes + " : " + "%02d", seconds);
    }*/
}