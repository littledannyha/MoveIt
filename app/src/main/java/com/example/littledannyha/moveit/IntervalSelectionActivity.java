package com.example.littledannyha.moveit;

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
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Button intervalStartButton = (Button) findViewById(R.id.startIntervalButton);
        intervalStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText durationPicker = ((EditText) findViewById(R.id.durationPicker));
                Integer duration = Integer.parseInt(durationPicker.getText().toString());

                Log.d("Interval Button", "Duration selected: " + duration.toString());
                startCountdownService(duration);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_interval_selection, menu);
        return true;
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
}
