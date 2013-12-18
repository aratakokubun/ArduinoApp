package com.google.android.DemoKit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

public class GLView extends GLSurfaceView
{
  GLRenderer renderer;
  public float x, y, size, press;
	
  Resources res;
  
  public GLView(Context context) {
    super(context);
    setEGLConfigChooser(8, 8, 8, 8, 16, 0);
    renderer = new GLRenderer();
    setRenderer(renderer);
    getHolder().setFormat(PixelFormat.TRANSLUCENT);
    
    BindTextures.setContext(this.getContext());
  }
}