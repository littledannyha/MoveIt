package com.example.littledannyha.moveit;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class IntervalCountdownService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    static final String ACTION_START_COUNTDOWN = "com.example.littledannyha.moveit.action.START_COUNTDOWN";

    // TODO: Rename parameters
    static final String EXTRA_COUNTDOWN_DURATION = "com.example.littledannyha.moveit.extra.COUNTDOWN_DURATION";

    public IntervalCountdownService() {
        super("IntervalCountdownService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    private static int secToMilliSeconds(int numSeconds){
        return numSeconds * 1000;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START_COUNTDOWN.equals(action)) {
                Log.d("Interval Service", "received correct action in intent");
                final Integer countdownDurationSeconds = intent.getIntExtra(EXTRA_COUNTDOWN_DURATION, 5);
                final Integer countdownDurationMilliseconds = countdownDurationSeconds * 1000;
                try {
                    Thread.sleep(countdownDurationMilliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext(), ExerciseReminderActivity.class);
                i.putExtra(EXTRA_COUNTDOWN_DURATION,countdownDurationSeconds);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
    }
}
