package com.example.fan;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import java.util.jar.Attributes;

public class OpenGLClass extends GLSurfaceView {
    public OpenGLClass(Context context) {
        super(context);
        init();
    }

    public OpenGLClass(Context context, AttributeSet attrs) {
        super(context);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        setRenderer(new OpenGLRender());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
