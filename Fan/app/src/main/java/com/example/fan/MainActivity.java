package com.example.fan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.StrictMath.abs;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Switch s1;
    ImageView img;
    SeekBar seekBar;
    TextView temp;
    Button opengl;
    private SensorManager sensorManager;
    private float temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imgfan);
        s1 = findViewById(R.id.switch1);
        seekBar = findViewById(R.id.seekBar);
        temp = findViewById(R.id.textView2);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        opengl = findViewById(R.id.openanimation);

        opengl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OpenGLDraw.class));
            }
        });

        final RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());


        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                if (s1.isChecked()) {
                    img.startAnimation(rotate);
                    s1.setText("ON");
                    seekBar.setProgress(50);
                } else {
                    img.clearAnimation();
                    s1.setText("OFF");
                }


            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                rotate.setDuration(abs(2020 - (progress * 20)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadAmbientTemperature();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterAll();
    }

    private void simulateAmbientTemperature() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                temperature = 20;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        temp.setText("Temperature: " + temperature);
                        getSupportActionBar().setTitle(getString(R.string.app_name) + " : " + temperature);
                    }
                });
            }
        }, 0, 3500);
    }

    private void loadAmbientTemperature() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(this, "No Ambient Temperature Sensor !", Toast.LENGTH_LONG).show();
        }
    }

    private void unregisterAll() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values.length > 0) {
            temperature = sensorEvent.values[0];
            temp.setText("Temperature: " + temperature);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " : " + temperature);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

