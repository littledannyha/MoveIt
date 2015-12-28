package com.example.littledannyha.moveit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ExerciseReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int duration = getIntent().getIntExtra(IntervalCountdownService.EXTRA_COUNTDOWN_DURATION, 5);

        Button restartButton = (Button) findViewById(R.id.restartCountdownButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startedIntent = getIntent();
                int duration = startedIntent.getIntExtra(IntervalCountdownService.EXTRA_COUNTDOWN_DURATION, 5);
                startCountdownService(duration);
                onBackPressed();
                returnToMainMenu();
            }
        });
        restartButton.setText("Another Reminder? (" + duration + "min)");

        Button doneButton = (Button) findViewById(R.id.finishCountdownButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                returnToMainMenu();
            }
        });
    }

    public void startCountdownService(int duration){
        Intent intent = new Intent(getApplicationContext(), IntervalCountdownService.class);
        intent.setAction(IntervalCountdownService.ACTION_START_COUNTDOWN);
        intent.putExtra(IntervalCountdownService.EXTRA_COUNTDOWN_DURATION, duration);
        startService(intent);
    }

    public void returnToMainMenu(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}