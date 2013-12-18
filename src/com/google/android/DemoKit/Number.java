package com.google.android.DemoKit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

class Number{
	//頂点バッファ
	private FloatBuffer vertexBuffer;
	//テクスチャ座標バッファ
	private FloatBuffer textureBuffer;

	//テクスチャ情報
	private float texW = 0.025f, texH = 0.05f;
	private float texPos[] = new float[3];
	private float Vertex2D[][] = new float[4][3];
	public boolean isDraw = false;
	
	public Number(float w, float h, int place){
		switch(place){
		case 1:		texPos[0] = -w/2+w/15;
					texPos[1] = h/2;
					texPos[2] = 0;
					break;
		case 10:	texPos[0] = -w/2;
					texPos[1] = h/2;
					texPos[2] = 0;
					break;
		case 100:	texPos[0] = -w/2-w/15;
					texPos[1] = h/2;
					texPos[2] = 0;
					break;
		default:	break;
		}
		texW *= w;
		texH *= h;
		
		setVertBuf();
		setCoordBuf(0);
	}
	
	private void setCoordBuf(int num){
		//頂点数x頂点構成要素数x4バイトのサイズのバッファ作成
		ByteBuffer vb = ByteBuffer.allocateDirect(4 * 2 * 4);
		vb.order(ByteOrder.nativeOrder());	//ビッグエンディアンかリトルエンディアンにあわせてくれる
		textureBuffer = vb.asFloatBuffer();
		
		//頂点データで頂点バッファ作成
		float[] vertices = {
				// 画像データの左上を原点に、0.0~1.0の範囲の座標
				1.0f * num/10*100/128	 , 1.0f*20/32,		//左下
				1.0f * (num+1)/10*100/128, 1.0f*20/32,		//右下
				1.0f * num/10*100/128	 , 0.0f		 ,		//左上
				1.0f * (num+1)/10*100/128, 0.0f		 ,		//右上
		};
		textureBuffer.put(vertices);
		textureBuffer.position(0);
	}
	
	private void setVertBuf(){
		//頂点数x頂点構成要素数x4バイトのサイズのバッファ作成
		ByteBuffer vb2D = ByteBuffer.allocateDirect(4 * 3 * 4);
		vb2D.order(ByteOrder.nativeOrder());	//ビッグエンディアンかリトルエンディアンにあわせてくれる
		vertexBuffer = vb2D.asFloatBuffer();
		
		//頂点データで頂点バッファ作成
		float[] vertices = new float[4 * 3]; 
		//頂点データの調整
		float[][] temp2D = {
				{-texW, -texH, 0},  // -/-
				{ texW, -texH, 0},  // +/-
				{-texW,  texH, 0},  // -/+
				{ texW,  texH, 0},  // +/+
		};
		//頂点データで頂点バッファ作成
		for(int i = 0; i < 4; i++){
			Vertex2D[i][0] = texPos[0] + temp2D[i][0];
			vertices[i*3] = Vertex2D[i][0];
			Vertex2D[i][1] = texPos[1] + temp2D[i][1];
			vertices[i*3+1] = Vertex2D[i][1];
			Vertex2D[i][2] = texPos[2] + temp2D[i][2];
			vertices[i*3+2] = Vertex2D[i][2];
		}
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
	}
	
	public void changeNumber(int num){
		setCoordBuf(num);
	}
	
	/**
	 * @param gl	GL操作ハンドル
	 **/
	public void draw( GL10 gl){
		//テクスチャ管理番号バインド
		gl.glBindTexture(GL10.GL_TEXTURE_2D, BindTextures.getNumIndex());
		//頂点バッファ設定
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		//描画
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}