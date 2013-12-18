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
文字列描画アクティビティクラス 
**/  
public class TextViewTest extends Activity {  
	/** 
	 * 初期化イベント 
	 **/  
	 @Override  
	 public void onCreate(Bundle bundle) {
		 super.onCreate(bundle);  
		 setContentView( new TextView(this) );
	 }  
	  
	 /** 
	  * 文字列表示Viewクラス 
	 **/  
	 class TextView extends View {
		 public TextView(Context context) {  
			 super(context);  
			 setBackgroundColor( Color.rgb(0, 128, 255) );  
		 }  
	  
		 /** 
		  * 描画イベント 
		  **/  
		 @Override  
		 public void onDraw(Canvas canvas) {  
			 Paint paint = new Paint();  
			 
			 // パラメータ設定  
			 paint.setAntiAlias(true);  
			 paint.setColor(Color.BLACK);  
			 paint.setTextSize(20);  
			 
			 // BitmapとCanvas作成  
			 Bitmap image = Bitmap.createBitmap(128, 128, Config.ARGB_8888);  
			 Canvas image_canvas = new Canvas(image);  
			 
			 // Bitmapに描画  
			 image_canvas.drawText("test", 20, 20, paint);  
	  
			 // 画面へ描画  
			 canvas.drawBitmap(image, 0, 0, new Paint());  
		 }
	 }
}