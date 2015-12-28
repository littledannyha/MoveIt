package com.example.littledannyha.moveit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class IntervalSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_selection);
        Button intervalStartButton = (Button) findViewById(R.id.startIntervalButton);
        intervalStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText durationPicker = ((EditText) findViewById(R.id.durationPicker));
                Integer duration = Integer.parseInt(durationPicker.getText().toString());
                setAlarmManager(duration);
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


    public void setAlarmManager(int durationInMinutes) {
        Log.d("Selection Duration", Integer.toString(durationInMinutes));
        Intent intent = new Intent(this, ExerciseReminderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ExerciseReminderActivity.EXTRA_COUNTDOWN_DURATION, durationInMinutes);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getActivity(this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + durationInMinutes * 1000, alarmIntent);
    }

}
