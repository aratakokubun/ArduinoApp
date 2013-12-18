package com.google.android.DemoKit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

class Grid{
	//頂点バッファ
	private FloatBuffer vertexBuffer;
	private FloatBuffer colorBuffer;
	private int pointNum = 0;
	private static final float LINE_WIDTH = 1.0f;
	private float spaceX = 0f, spaceY = 0f;
	private float Width, Height;
	
	public Grid(int Num, float width, float height){
		pointNum = Num;
		Width = width;
		Height = height;
		spaceX = Width / (pointNum+1);
		spaceY = Height / (pointNum+1);
		makeGrid();
	}
	
	private void makeGrid(){
		float vertices[] = new float[pointNum * 3 * 2 * 2];
		float color[] = new float[pointNum * 4 * 2 * 2];
		float x0 = 0f, x1 = 0f;
		float y0 = 0f, y1 = 0f;
		float z0 = 0f, z1 = 0f;
		
		//頂点数x頂点構成要素数x4バイトのサイズのバッファ作成
		ByteBuffer vb3D = ByteBuffer.allocateDirect(pointNum * 3 * 2 * 2 * 4);
		vb3D.order(ByteOrder.nativeOrder());	//ビッグエンディアンかリトルエンディアンにあわせてくれる
		vertexBuffer = vb3D.asFloatBuffer();
		ByteBuffer cb3D = ByteBuffer.allocateDirect(pointNum * 4 * 2 * 2 * 4);
		cb3D.order(ByteOrder.nativeOrder());	//ビッグエンディアンかリトルエンディアンにあわせてくれる
		colorBuffer = cb3D.asFloatBuffer();
		
		for(int i = 0; i < pointNum; i++){
			x0 = (i+1) * spaceX - Width/2; y0 = 0f;		  z0 = 0f;
			x1 = (i+1) * spaceX - Width/2; y1 = Height/2; z1 = 0f;
			vertices[i*6 + 0] = x0;
			vertices[i*6 + 1] = y0;
			vertices[i*6 + 2] = z0;
			vertices[i*6 + 3] = x1;
			vertices[i*6 + 4] = y1;
			vertices[i*6 + 5] = z1;
			for( int j=0 ; j<4*2 ; j++ ){
    			color[i*4*2 + j] = 1.0f;
    		}
		}
		for(int i = 0; i < pointNum; i++){
			x0 = 0f;      y0 = (i+1) * spaceY - Height/2; z0 = 0f;
			x1 = Width/2; y1 = (i+1) * spaceY - Height/2; z1 = 0f;
			vertices[(pointNum+i)*6 + 0] = x0;
			vertices[(pointNum+i)*6 + 1] = y0;
			vertices[(pointNum+i)*6 + 2] = z0;
			vertices[(pointNum+i)*6 + 3] = x1;
			vertices[(pointNum+i)*6 + 4] = y1;
			vertices[(pointNum+i)*6 + 5] = z1;
			for( int j=0 ; j<4*2 ; j++ ){
    			color[(pointNum+i)*4*2 + j] = 1.0f;
    		}
		}
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		colorBuffer.put(color);
		colorBuffer.position(0);
	}
	
	/**
	 * @param gl	GL操作ハンドル
	 **/
	public void draw( GL10 gl){
		//gl.glDisable(GL10.GL_BLEND);
		gl.glLineWidth(LINE_WIDTH);
		//頂点バッファ設定
		gl.glVertexPointer( 3, GL10.GL_FIXED, 0, vertexBuffer );
		gl.glColorPointer( 4, GL10.GL_FIXED, 0, colorBuffer );
		//描画
		gl.glDrawArrays(GL10.GL_LINES, 0, pointNum*3*2);
	}
}