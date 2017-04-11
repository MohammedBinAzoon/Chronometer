package com.example.android.chronometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    static final String TIME_START = "start time";
    static final String TIME_STOP = "stop time";
    static final String BOOLEAN_CHRONO = "is running";
    boolean bool_start = false;
    CharSequence timeStop = "00:00";

    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(savedInstanceState != null && savedInstanceState.getBoolean(BOOLEAN_CHRONO)){
            //if chronometer is already running, update the time base
            //to the last stored value TIME_START so that it does not
            //start again from scratch

            setContentView(R.layout.activity_main);
            chronometer = (Chronometer) findViewById(R.id.chronometer);
            chronometer.setBase(savedInstanceState.getLong(TIME_START));
            chronometer.start();
            super.onCreate(savedInstanceState);
        }


        else if(savedInstanceState != null) {
            //if chronometer is not running, show the last time stamp (stored in TIME_STOP)

            setContentView(R.layout.activity_main);
            chronometer = (Chronometer) findViewById(R.id.chronometer);
            chronometer.setText(savedInstanceState.getCharSequence(TIME_STOP));
            super.onCreate(savedInstanceState);
        }


        else{ //else do nothing, savedInstanceState is 'null'

            setContentView(R.layout.activity_main);
            chronometer = (Chronometer) findViewById(R.id.chronometer);
            super.onCreate(null);
        }

        ((Button) findViewById(R.id.start_button)).setOnClickListener(this);
        ((Button) findViewById(R.id.stop_button)).setOnClickListener(this);

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {

        //save time base
        savedInstanceState.putLong(TIME_START, chronometer.getBase());

        //save chronometer status
        savedInstanceState.putBoolean(BOOLEAN_CHRONO,bool_start);

        //save time stamp of chronometer stop
        savedInstanceState.putCharSequence(TIME_STOP, timeStop);

        super.onSaveInstanceState(savedInstanceState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState){

        if(savedInstanceState.getBoolean(BOOLEAN_CHRONO)){

            //if chronometer is running, store
            //chronometer status in bool_start
            bool_start=savedInstanceState.getBoolean(BOOLEAN_CHRONO);
        }

        else{

            //if chronometer is not running,
            //show the last time stamp (stored in TIME_STOP)
            //and "pass it on" to the next saved state
            chronometer = (Chronometer) findViewById(R.id.chronometer);
            timeStop = savedInstanceState.getCharSequence(TIME_STOP);
            chronometer.setText(timeStop);
            savedInstanceState.putCharSequence(TIME_STOP, timeStop);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                bool_start=true;
                break;

            case R.id.stop_button:
                chronometer.stop();
                bool_start=false;
                timeStop=chronometer.getText();
                break;

            case R.id.reset_button:
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                bool_start=false;
                timeStop="00:00";
                break;
        }
    }

}
