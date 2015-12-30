package com.example.littledannyha.moveit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExerciseReminderActivity extends AppCompatActivity {
    public static final String EXTRA_COUNTDOWN_DURATION = "com.littledannyha.moveit.extra_countdown_duration";
    private static final String KEY_HAS_SHIA_LEBOUF_SHOUTED_STRING = "com.littledannyha.moveit.has_shia_lebouf_shouted_yet";

    private boolean hasShiaLeboufShoutedYet;

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
        if (savedInstanceState != null) {
            hasShiaLeboufShoutedYet = savedInstanceState.getBoolean(KEY_HAS_SHIA_LEBOUF_SHOUTED_STRING, false);
        } else {
            hasShiaLeboufShoutedYet = false;
        }
        if (!hasShiaLeboufShoutedYet) {
            Log.d("call to shout", "call to shout");
            shiaLabeoufSaysDoIt();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putBoolean(KEY_HAS_SHIA_LEBOUF_SHOUTED_STRING, hasShiaLeboufShoutedYet);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void shiaLabeoufSaysDoIt() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.justdoit);
        mediaPlayer.setVolume(2, 2);
        final AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final AudioManager.OnAudioFocusChangeListener focusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                TextView t = (TextView) findViewById(R.id.exerciseReminderText);
                t.setText(Integer.toString(focusChange));
//                Log.d("focusChange", Integer.toString(focusChange));
//                switch(focusChange){
//                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
//                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE:
//                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
//                    case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
//                        Log.d("playing", "playing");
//                        hasShiaLeboufShoutedYet = true;
//                        mediaPlayer.start();
//                        break;
//                    case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
//                        am.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
//                        break;
//                }
            }
        };
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                am.abandonAudioFocus(focusChangeListener);
                mp.release();
            }
        });

        int result = am.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE);
//        int result = am.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        mediaPlayer.start();

    }

    public void returnToMainMenu() {
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