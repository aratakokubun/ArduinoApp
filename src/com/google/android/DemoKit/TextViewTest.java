package com.google.android.DemoKit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
  
/** 
������`��A�N�e�B�r�e�B�N���X 
**/  
public class TextViewTest extends Activity {  
	/** 
	 * �������C�x���g 
	 **/  
	 @Override  
	 public void onCreate(Bundle bundle) {
		 super.onCreate(bundle);  
		 setContentView( new TextView(this) );
	 }  
	  
	 /** 
	  * ������\��View�N���X 
	 **/  
	 class TextView extends View {
		 public TextView(Context context) {  
			 super(context);  
			 setBackgroundColor( Color.rgb(0, 128, 255) );  
		 }  
	  
		 /** 
		  * �`��C�x���g 
		  **/  
		 @Override  
		 public void onDraw(Canvas canvas) {  
			 Paint paint = new Paint();  
			 
			 // �p�����[�^�ݒ�  
			 paint.setAntiAlias(true);  
			 paint.setColor(Color.BLACK);  
			 paint.setTextSize(20);  
			 
			 // Bitmap��Canvas�쐬  
			 Bitmap image = Bitmap.createBitmap(128, 128, Config.ARGB_8888);  
			 Canvas image_canvas = new Canvas(image);  
			 
			 // Bitmap�ɕ`��  
			 image_canvas.drawText("test", 20, 20, paint);  
	  
			 // ��ʂ֕`��  
			 canvas.drawBitmap(image, 0, 0, new Paint());  
		 }
	 }
}