package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;
    private Button btnStart, btnStop, btnHold;
    private Handler handler;
    private long startTime, timeInMillis, timeSwapBuff, updateTime;
    private Runnable updateTimerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tv_timer);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnHold = findViewById(R.id.btn_hold);

        handler = new Handler();
        timeSwapBuff = 0L;

        updateTimerThread = new Runnable() {
            public void run() {
                timeInMillis = System.currentTimeMillis() - startTime;
                updateTime = timeSwapBuff + timeInMillis;
                int secs = (int) (updateTime / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (updateTime % 1000);
                tvTimer.setText(String.format("%02d:%02d:%03d", mins, secs, milliseconds));
                handler.postDelayed(this, 0);
            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                handler.postDelayed(updateTimerThread, 0);
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                btnHold.setEnabled(true);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuff += timeInMillis;
                handler.removeCallbacks(updateTimerThread);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                btnHold.setEnabled(true);
            }
        });

        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimerThread);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                btnHold.setEnabled(true);
            }
        });
    }
}
