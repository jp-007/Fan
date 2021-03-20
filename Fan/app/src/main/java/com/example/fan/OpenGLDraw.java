package com.example.fan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class OpenGLDraw extends AppCompatActivity {

    private OpenGLClass openGLClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_g_l_draw);
        openGLClass = (OpenGLClass) findViewById(R.id.openGLView1);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        openGLClass.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        openGLClass.onPause();
//    }
}