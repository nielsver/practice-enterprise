package com.example.runandcycle;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.runandcycle.databinding.ActivityMain2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText minutes, seconds;
    private Button Run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        getSupportActionBar().setTitle("home");
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    public void startpop(View view) {
        CreateDialog();
    }
    public void CreateDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactpopupView = getLayoutInflater().inflate(R.layout.popup, null);
        minutes = contactpopupView.findViewById(R.id.minutes);
        seconds = contactpopupView.findViewById(R.id.seconds);

        Run = contactpopupView.findViewById(R.id.Run);
        dialogBuilder.setView(contactpopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        Run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(i);
            }
        });
    }


}