package com.google.android.DemoKit;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class BindTextures 
{
	public static final int BASE_TEXTURE_WIDTH = 128;
	public static final int BASE_TEXTURE_HEIGHT = 128;
	
	public static final int CHESS = 0;
	public static final int SPONGE = 1;
	public static final int STONE = 2;
	public static final int MEAT_SOFT = 3;
	public static final int MEAT_NORMAL = 4;
	public static final int MEAT_HARD = 5;
	
	public static final float[] DFMAMNT = {2.0f, 4.0f, 0.2f, 4.0f, 2.0f, 0.5f};
	
	public static final int ALPHA 	= 0;
	public static final int EDGE 	= 1;
	public static final int SHADOW 	= 2;

	private static Context context;
	private static Resources res;
	
	//テクスチャのID割り当て
	private static int[] tex_index = new int[12];//(6種類  * 2アルファ値)
	private static int[] hand_index = new int[3];//通常・輪郭・シャドウ
	private static int[] num_index = new int[1];//表示する数字
	//テクスチャ元画像
	private static Bitmap[][] bmp = new Bitmap[6][2];
	private static Bitmap[] hand = new Bitmap[3];
	private static Bitmap number;
	
	// 選択しているテクスチャのID
	private static boolean isTransParent = false;
	// null pointer exception 回避用に値をセット
	private static int tex_id = CHESS;
	private static int hand_id = ALPHA;
	
	/* ------------------------------------------------------------------------------------------------------ */
	public static void setContext(Context cntxt){
		context = cntxt;
		res = context.getResources();
		getTexturesResource();
		textureInitWithStart();
	}
	
	private static void textureInitWithStart(){
		tex_id = CHESS;
		hand_id = ALPHA;
	}

	/* ------------------------------------------------------------------------------------------------------ */
	private static void getTexturesResource() {
		//texture object
		bmp[0][0] = BitmapFactory.decodeResource(res, R.drawable.chess);
		bmp[0][1] = BitmapFactory.decodeResource(res, R.drawable.alpha875_chess);
		bmp[1][0] = BitmapFactory.decodeResource(res, R.drawable.sponge);
		bmp[1][1] = BitmapFactory.decodeResource(res, R.drawable.alpha875_sponge);
		bmp[2][0] = BitmapFactory.decodeResource(res, R.drawable.stone);
		bmp[2][1] = BitmapFactory.decodeResource(res, R.drawable.alpha875_stone);
		bmp[3][0] = BitmapFactory.decodeResource(res, R.drawable.meat_s);
		bmp[3][1] = BitmapFactory.decodeResource(res, R.drawable.meat_s);
		bmp[4][0] = BitmapFactory.decodeResource(res, R.drawable.meat_n);
		bmp[4][1] = BitmapFactory.decodeResource(res, R.drawable.meat_n);
		bmp[5][0] = BitmapFactory.decodeResource(res, R.drawable.meat_h);
		bmp[5][1] = BitmapFactory.decodeResource(res, R.drawable.meat_h);
		// hand
		hand[0] = BitmapFactory.decodeResource(res, R.drawable.right);
		hand[1] = BitmapFactory.decodeResource(res, R.drawable.edge_right);
		hand[2] = BitmapFactory.decodeResource(res, R.drawable.shadow_right);
		// number
		number = BitmapFactory.decodeResource(res, R.drawable.number_yellow);
		
		// view size
		GLRenderer.width = BASE_TEXTURE_WIDTH;
		GLRenderer.height = BASE_TEXTURE_HEIGHT;
	}

	/* ------------------------------------------------------------------------------------------------------ */
	public static void setTexture(GL10 gl) {
		int index = 0;
		
		//object
		gl.glGenTextures(12, tex_index, 0);
		for (int i = 0; i < 6; i++) {
			for(int j = 0; j < 2; j++){
				gl.glBindTexture(GL10.GL_TEXTURE_2D, tex_index[index]);
				if (bmp[i][j] != null) {
					GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp[i][j], 0);
					gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);// 縮小時のフィルタ
					gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);// 拡大時のフィルタ
					bmp[i][j].recycle();
				} else {
					Log.e("bitmap read error", "Can't read bitmap.");
				}
				index++;
			}
		}
		//hand
		gl.glGenTextures(1, hand_index, 0);
		for(int i = 0; i < 3; i++){
			gl.glBindTexture(GL10.GL_TEXTURE_2D, hand_index[i]);
			if (hand[i] != null) {
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, hand[i], 0);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);// 縮小時のフィルタ
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);// 拡大時のフィルタ
				hand[i].recycle();
			} else {
				Log.e("bitmap read error", "Can't read bitmap.");
			}
			index++;
		}
		//number
		gl.glGenTextures(1, num_index, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, num_index[0]);
		if (number != null) {
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, number, 0);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);// 縮小時のフィルタ
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);// 拡大時のフィルタ
			number.recycle();
		} else {
			Log.e("bitmap read error", "Can't read bitmap.");
		}
		index++;
	}
	
	/* ------------------------------------------------------------------------------------------------------ */
	public static void setTexId(int id){
		tex_id = id;
	}
	
	public static void setHandId(boolean isDraw, int id){
		isTransParent = isDraw;
		hand_id = id;
		
		if(isDraw && tex_id%2 == 0){
			tex_id++;
		} else if(tex_id%2 == 1){
			tex_id--;
		}
	}

	/* ------------------------------------------------------------------------------------------------------ */
	public static int getTexIndex(){
		return tex_index[tex_id];
	}
	
	public static int getTexId(){
		return tex_id;
	}
	
	public static int getHandIndex(){
		return hand_index[hand_id];
	}
	
	public static int getHandId(){
		return hand_id;
	}
	
	public static int getNumIndex(){
		return num_index[0];
	}

	public static boolean getIsTransParent(){
		return isTransParent;
	}
	
	public static float getDfmAmnt(){
		return DFMAMNT[tex_id/2];
	}
}