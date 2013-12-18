package com.google.android.DemoKit;

import javax.microedition.khronos.opengles.GL10;

public class LightPositionController {
	private static float x;
	private static float y;
	private static float z;
	private static float w;
	private static boolean resetFlag = false;
	
	public static void setLightPosition(float xValue, float yValue, float zValue)
	{
		x = xValue;
		y = yValue;
		z = zValue;
		w = 1.0f;
		resetFlag = true;
	}
	
	public static void setLightfv(GL10 gl, int light){
		if(resetFlag){
			float[] params = {x, y ,z, w};
			gl.glLightfv(light, GL10.GL_POSITION, params, 0);
			resetFlag = false;
		}
	}
	
	public static float getX()
	{
		return x;
	}
	
	public static float getY()
	{
		return y;
	}
	
	public static float getZ()
	{
		return z;
	}
}
