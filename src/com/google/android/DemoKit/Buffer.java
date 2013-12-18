package com.google.android.DemoKit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Buffer {
	
	public static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer vb = ByteBuffer.allocateDirect(arr.length*4);
		vb.order(ByteOrder.nativeOrder());	//�r�b�O�G���f�B�A�������g���G���f�B�A���ɂ��킹�Ă����
		FloatBuffer fb = vb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
}
