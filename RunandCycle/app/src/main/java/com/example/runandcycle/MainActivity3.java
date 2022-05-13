package com.example.runandcycle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity3 extends AppCompatActivity {

    Button stop;
    Button finish;

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_runpage);

        finish = findViewById(R.id.finish);

        Bundle extras = getIntent().getExtras();
        String minutes = extras.getString("minutes");
        String second = extras.getString("second");

        stop = findViewById(R.id.stop);


        getSupportActionBar().setTitle("recording");
        if (savedInstanceState != null) {
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }
        runTimer();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("time", R.id.timer);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }

    public void onClickStop()
    {
        if(running == true) {
            running = false;
            stop.setText(R.string.start);
        }
        else{
            running = true;
            stop.setText(R.string.stop);
        }
    }
    private void runTimer()
    {
        final TextView timeView
                = findViewById(
                R.id.timer);

        final Handler handler
                = new Handler();

        handler.post(new Runnable() {
            @Override


            public void run()
            {
                stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickStop();
                    }
                });
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);
                timeView.setText(time);

                if (running == true) {
                    seconds++;
                    Log.d("runpage","running");
                }
                Log.d("runpage", String.valueOf(seconds));

                handler.postDelayed(this, 1000);
            }
        });
    }

}