package com.example.littledannyha.moveit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ExerciseReminderActivity extends AppCompatActivity {
    public static final String EXTRA_COUNTDOWN_DURATION = "com.littledannyha.moveit.extra_countdown_duration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder);
        int duration = getIntent().getIntExtra(EXTRA_COUNTDOWN_DURATION, 5);
        clearPendingIntentCache();
        Button restartButton = (Button) findViewById(R.id.restartCountdownButton);
        restartButton.setText("Another Reminder? (" + duration + "min)");

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int durationInSeconds = getIntent().getIntExtra(EXTRA_COUNTDOWN_DURATION, 5);
                Log.d("Duration selected: ", Integer.toString(durationInSeconds));
                setAlarmManager(durationInSeconds);
                onBackPressed();
                returnToMainMenu();
            }
        });

        Button doneButton = (Button) findViewById(R.id.finishCountdownButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                returnToMainMenu();
            }
        });
    }

    public void returnToMainMenu(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public void clearPendingIntentCache() {
        Intent intent = new Intent(this, ExerciseReminderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_COUNTDOWN_DURATION, 3);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getActivity(this, 0, intent, 0);
        alarmIntent.cancel();
    }

    public void setAlarmManager(int durationInSeconds) {
        Log.d("Reminder Duration", Integer.toString(durationInSeconds));
        Intent intent = new Intent(this, ExerciseReminderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_COUNTDOWN_DURATION, durationInSeconds);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getActivity(this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + durationInSeconds * 1000, alarmIntent);
    }

}