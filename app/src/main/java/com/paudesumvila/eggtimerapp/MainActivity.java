package com.paudesumvila.eggtimerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timeView;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Boolean counterActive = false;
    Button button;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timeView.setText("00:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        button.setText("GO!");
        counterActive = false;
    }

    public void buttonClicked(View view){

        if (counterActive){
            resetTimer();
        } else {
            counterActive = true;
            seekBar.setEnabled(false);
            button.setText("STOP");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        if(minutes < 10 && seconds < 10){
            timeView.setText("0" + Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds));
        } else if(minutes < 10){
            timeView.setText("0" + Integer.toString(minutes) + ":" + Integer.toString(seconds));
        } else if(seconds < 10){
            timeView.setText(Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds));
        } else {
            timeView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        timeView = findViewById(R.id.timeView);
        button = findViewById(R.id.button);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);

        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
