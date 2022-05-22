package com.example.runandcycle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

public class MainActivity3 extends AppCompatActivity {

    Button stop;
    Button finish;
    TextView distanceText;
    TextView timepkm;
    TextView timeinminkm;
    int distintime = 0;
    double lat;
    double lon;
    double lat1;
    double lon1;
    private LocationCallback locationCallback;
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    String minutes;
    String second;
    Location mCurrentLocation;
    LocationRequest locationRequest;
    boolean requestingLocationUpdates = false;

    private FusedLocationProviderClient fusedLocationClient;
    public int intpart;
    public double sec;
    int minint;
    int secint;
    String toslow = "Your going to slow. Run faster";
    String goodpace = "you have found the right pace. Keep it up";
    String tofast = "Your going to fast. Slow down";


    TextToSpeech textToSpeech;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_runpage);

        distanceText = findViewById(R.id.distanceinkm);
        timepkm = findViewById(R.id.timepkm);
        finish = findViewById(R.id.finish);
        timeinminkm = findViewById(R.id.timeinminkm);

        Bundle extras = getIntent().getExtras();
        minutes = extras.getString("minutes");
        second = extras.getString("second");
        Log.d("mainactivity3", String.valueOf(minutes));
        Log.d("mainactivity3", String.valueOf(second));

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        if(minutes != null && second != null) {

            minint = Integer.parseInt(minutes);
            secint = Integer.parseInt(second);
        }
        if(minutes != null && second == null){
            minint = Integer.parseInt(minutes);
            secint = 0;
        }
        if(second != null && minutes == null){
            secint = Integer.parseInt(second);
            minint = 0;
        }
            Log.d("seconds", String.valueOf(secint));
            if(secint <= 60) {
                CharSequence text = "seconds above 60";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                finish();
            }
            minint = minint *60;
            secint = minint +secint;



        Log.d("seconds", String.valueOf(secint));



        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        stop = findViewById(R.id.stop);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            mCurrentLocation = location;
                            lon = mCurrentLocation.getLongitude();
                            lat = mCurrentLocation.getLatitude();
                            double x = 0.00;

                        }else{
                            Log.d("mainactivity3", "location is null " );
                        }
                    }
                });

        locationCallback = new LocationCallback() {
            private double x;
            public double kmph, avgkmph, minpkm;


            @SuppressLint("DefaultLocale")
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d("mainactivity3", "no Location result in updates");
                    return;
                }
                for (Location location : locationResult.getLocations()) {

                        lon1 = location.getLongitude();
                        lat1 = location.getLatitude();
                        Log.d("mainactivity3", "old lat " + lat + " new lat "+ lat1);
                    double R = 6371; // Radius of the earth in km
                    double dLat = deg2rad(lat1-lat);  // deg2rad below
                    double dLon = deg2rad(lon1-lon);
                    double a =
                            Math.sin(dLat/2) * Math.sin(dLat/2) +
                                    Math.cos(deg2rad(lat)) * Math.cos(deg2rad(lat1)) *
                                            Math.sin(dLon/2) * Math.sin(dLon/2)
                            ;
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    double d = R * c; // Distance in km
                    x =  d + x;
                    avgkmph = (x * 1000) / seconds;
                    kmph = (d * 1000) / 5;
                    kmph = kmph * 3.6;
                    minpkm = 60/avgkmph;
                    Log.d("seconds", String.valueOf(seconds));
                    String z = String.format("%.1f",kmph);
                    timepkm.setText(z);
                    Log.d("minperkm" , String.valueOf(minpkm));
                    intpart  = (int) minpkm;
                    sec = minpkm - intpart;
                    sec = sec * 100;
                    String r = String.format("%2d" + ":" + "%2f",intpart, sec );
                    timeinminkm.setText(r);

                    String s = String.format("%.2f",x);
                    distanceText.setText(s);
                        lon = lon1;
                        lat = lat1;



                }
            }
        };


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
                running = false;
                seconds = 0;
                minutes = null;
                second = null;
                Intent data = new Intent(MainActivity3.this, MainActivity2.class);
                data.putExtra("time", R.id.timer);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }

    private double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }


    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(createLocationRequest(),
                locationCallback,
                Looper.getMainLooper());
    }

    private LocationRequest createLocationRequest() {
        locationRequest = LocationRequest.create()
                .setInterval(1000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(1000);
        return locationRequest;

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

    public void onClickStop() {
        if (running == true) {
            running = false;
            onPause();
            stop.setText(R.string.start);
        } else {
            requestingLocationUpdates = true;
            running = true;
            startLocationUpdates();
            stop.setText(R.string.stop);

        }
    }

    private void runTimer() {
        final TextView timeView
                = findViewById(
                R.id.timer);

        final Handler handler
                = new Handler();

        handler.post(new Runnable() {
            @Override


            public void run() {
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
                    distintime = (int) ((intpart * 60) + sec);
                    if((seconds % 30) == 0){
                        if(secint > distintime){
                            //to slow
                            int speech = textToSpeech.speak(toslow,TextToSpeech.QUEUE_FLUSH,null);

                        }else if(secint == distintime){
                            //right pace
                            int speech = textToSpeech.speak(goodpace,TextToSpeech.QUEUE_FLUSH,null);
                        }
                        else{
                            //to fast
                            int speech = textToSpeech.speak(tofast,TextToSpeech.QUEUE_FLUSH,null);
                        }
                    }

                }

                handler.postDelayed(this, 1000);
            }
        });
    }

}