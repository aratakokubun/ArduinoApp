package com.google.android.DemoKit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

class Hand{
	//頂点バッファ
	private static FloatBuffer vertexBuffer;
	//テクスチャ座標バッファ
	private static FloatBuffer textureBuffer;
	
	//テクスチャ情報
	private static float texW = 1.0f, texH = 2.0f;
	//private static float FingerX = 0.7f, FingerY = -0.1f;//指を閉じた際の表示
	//private static float FingerX = 0.813f, FingerY = 0.213f;//人差し指のみ伸ばした表示
	private static float FingerX = 0.813f, FingerY = -0.213f;//人差し指のみ伸ばした表示
	private static float[] texPos		= new float[3];
	private static float[][] Vertex2D 	= new float[4][3];
	
	public Hand(float w, float h){
		texW *= w; texH *= h;
		FingerX *= texW; FingerY *= texH;
		
		setCoordBuf();
		setVertBuf();
	}
	
	private void setCoordBuf(){
		//頂点数x頂点構成要素数x4バイトのサイズのバッファ作成
		ByteBuffer vb = ByteBuffer.allocateDirect(4 * 2 * 4);
		vb.order(ByteOrder.nativeOrder());	//ビッグエンディアンかリトルエンディアンにあわせてくれる
		textureBuffer = vb.asFloatBuffer();
		
		//頂点データで頂点バッファ作成
		float[] vertices = {
				// 画像データの左上を原点に、0.0~1.0の範囲の座標
				0.0f, 1.0f,		//左下
				1.0f, 1.0f,		//右下
				0.0f, 0.0f,		//左上
				1.0f, 0.0f,		//右上
		};
		textureBuffer.put(vertices);
		textureBuffer.position(0);
	}
	
	private static void setVertBuf(){
		//頂点数x頂点構成要素数x4バイトのサイズのバッファ作成
		ByteBuffer vb2D = ByteBuffer.allocateDirect(4 * 3 * 4);
		vb2D.order(ByteOrder.nativeOrder());	//ビッグエンディアンかリトルエンディアンにあわせてくれる
		vertexBuffer = vb2D.asFloatBuffer();
		
		//頂点データで頂点バッファ作成
		float[] vertices = new float[4 * 3]; 
		//頂点データの調整
		float[][] temp2D = {
				/*
				{-texW,  texH, 0},  // -/+
				{-texW, -texH, 0},  // -/-
				{ texW,  texH, 0},  // +/+
				{ texW, -texH, 0},	// +/-
				*/
				{ texW, -texH, 0},  // -/+
				{ texW,  texH, 0},  // -/-
				{-texW, -texH, 0},  // +/+
				{-texW,  texH, 0},	// +/-
		};
		//頂点データで頂点バッファ作成
		for(int i = 0; i < 4; i++){
			if(i == 0){
				Vertex2D[i][0] 	= texPos[0] + temp2D[i][0];
				vertices[i*3] 	= Vertex2D[i][0];
				Vertex2D[i][1]	= temp2D[i][1];
				vertices[i*3+1] = Vertex2D[i][1];
				Vertex2D[i][2] 	= texPos[2] + temp2D[i][2];
				vertices[i*3+2] = Vertex2D[i][2];
			} else if(i == 1){
				Vertex2D[i][0] 	= texPos[0] + temp2D[i][0];
				vertices[i*3] 	= Vertex2D[i][0];
				Vertex2D[i][1] 	= temp2D[i][1];
				vertices[i*3+1] = Vertex2D[i][1];
				Vertex2D[i][2] 	= texPos[2] + temp2D[i][2];
				vertices[i*3+2] = Vertex2D[i][2];
			} else {
				Vertex2D[i][0] 	= texPos[0] + temp2D[i][0];
				vertices[i*3] 	= Vertex2D[i][0];
				Vertex2D[i][1] 	= texPos[1] + temp2D[i][1];
				vertices[i*3+1] = Vertex2D[i][1];
				Vertex2D[i][2] 	= texPos[2] + temp2D[i][2];
				vertices[i*3+2] = Vertex2D[i][2];				
			}
		}
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
	}
	
	//Rendererからタッチ情報を受け取り、手先に合わせて位置を調整
	public static void setPos(float X, float Y, float Press){
		texPos[0] = X + FingerX;
		texPos[1] = Y + FingerY;
		setVertBuf();
	}
	
	//実験用テクスチャ変更関数
	public static void changeModeExperiment(int count){
		/*
		int newMode = count/(BaseActivity.MAX_COUNT/6) % 3;
		texMode = MODEARRAY[newMode];//3種類の手のモデル
		*/
	}

	/*-------------------------------------------------------------------------*/
	/**
	 * @param gl	GL操作ハンドル
	 **/
	public void draw( GL10 gl){
		//テクスチャ管理番号バインド
		gl.glBindTexture(GL10.GL_TEXTURE_2D, BindTextures.getHandIndex());
		//頂点バッファ設定
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		//描画
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}