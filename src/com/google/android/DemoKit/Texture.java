package com.google.android.DemoKit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

class Texture {
	//分割数
	public static final int DIV_X = 40, DIV_Y = 24;
	
	//テクスチャサイズ
	private static float Ax = 0, Ay = 0;
	//テクスチャ情報
	private float[][][] tex_coord_v = new float [DIV_Y+1][DIV_X+1][2]; /* テクスチャ座標行列 */
	private float[][][] vertex_v 	= new float [DIV_Y+1][DIV_X+1][3]; /* ポリゴン頂点行列 */
	
	//実験用テクスチャ配列
	public int[][] modeArray = {
			{0, 1}, {0, 2}, {0, 3}, {0, 4}, {1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4},
			{0, 1}, {0, 2}, {0, 3}, {0, 4}, {1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4},
			{0, 1}, {0, 2}, {0, 3}, {0, 4}, {1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4},
			{1, 0}, {2, 0}, {3, 0}, {4, 0}, {2, 1}, {3, 1}, {4, 1}, {3, 2}, {4, 2}, {4, 3},
			{1, 0}, {2, 0}, {3, 0}, {4, 0}, {2, 1}, {3, 1}, {4, 1}, {3, 2}, {4, 2}, {4, 3},
			{1, 0}, {2, 0}, {3, 0}, {4, 0}, {2, 1}, {3, 1}, {4, 1}, {3, 2}, {4, 2}, {4, 3},
	};
	
	public Texture(float w, float h){
		Ax = w;
		Ay = h;
		genExponentialWave();
		//if in experiment, make random array to 
		if(BaseActivity.EXPERIMENT)randArrayDouble();
	}
	
	private void randArrayDouble(){
		//先後の入れ替え
		for(int i = 0; i < BaseActivity.MAX_COUNT/4; i++){
			if(Math.random() < 0.5){//約5割の確率で入れ替え
				int no1 = i;
				int no2 = i + BaseActivity.MAX_COUNT/4;
				int[] temp1 = modeArray[no1];
				int[] temp2 = modeArray[no2];
				modeArray[no1] = temp2;
				modeArray[no2] = temp1;
			}
		}
		
		//試行順番の変更
		for(int i = 0; i < 6; i++){
			int hand = i*(BaseActivity.MAX_COUNT/12);
			for(int j = 0; j < 500; j++){//500回入れ替え
				int no1 = hand+(int)((BaseActivity.MAX_COUNT/12)*Math.random());
				int no2 = hand+(int)((BaseActivity.MAX_COUNT/12)*Math.random());
				int[] temp1 = modeArray[no1];
				int[] temp2 = modeArray[no2];
				modeArray[no1] = temp2;
				modeArray[no2] = temp1;
			}
		}
	}
	
	//バッファの作成と描画
	private void setBuffer(GL10 gl){
		for(int j=0; j<DIV_Y; j++ ){
			for(int i=0; i<DIV_X; i++ ){
				/*
				float[] tex_buf = {
						// 画像データの左上を原点に、0.0~1.0の範囲の座標
						tex_coord_v[j+1][i][0]	, tex_coord_v[j+1][i][1],			//左下
						tex_coord_v[j+1][i+1][0], tex_coord_v[j+1][i+1][1],			//右下
						tex_coord_v[j][i][0]	, tex_coord_v[j][i][1],				//左上
						tex_coord_v[j][i+1][0]	, tex_coord_v[j][i+1][1],			//右上
				};
				*/
				float[] tex_buf = {
						// 画像データの左上を原点に、0.0~1.0の範囲の座標
						tex_coord_v[j][i][0]	, tex_coord_v[j][i][1],				//左上
						tex_coord_v[j][i+1][0]	, tex_coord_v[j][i+1][1],			//右上
						tex_coord_v[j+1][i][0]	, tex_coord_v[j+1][i][1],			//左下
						tex_coord_v[j+1][i+1][0], tex_coord_v[j+1][i+1][1],			//右下
				};

				float[] vertex_buf = {
						vertex_v[j][i][0]    ,vertex_v[j][i][1]    ,vertex_v[j][i][2],
						vertex_v[j][i+1][0]  ,vertex_v[j][i+1][1]  ,vertex_v[j][i+1][2],
						vertex_v[j+1][i][0]  ,vertex_v[j+1][i][1]  ,vertex_v[j+1][i][2],
						vertex_v[j+1][i+1][0],vertex_v[j+1][i+1][1],vertex_v[j+1][i+1][2]
				};
				/*
				 * 4点をtex_bufの順番にA,B,C,Dとして二つの平面上のベクトルAB={x1,y1,z1},AC={x2,y2,z2}と法線ベクトルN={x, y, z}の内積
				 * AB*N=0,AC*N=0から法線ベクトルの各成分を求める
				 * このとき、ABベクトルのy成分とACベクトルのx成分は0(y1=0,x2=0)
				 * これより x:y:z = -y2z1:-x1z2:x1y2 で法線ベクトルが求まる
				 */
				//法線ベクトルの計算
				Vector3D u = new Vector3D(
						vertex_v[j][i+1][0]-vertex_v[j][i][0],
						vertex_v[j][i+1][1]-vertex_v[j][i][1],
						vertex_v[j][i+1][2]-vertex_v[j][i][2]
						);
				Vector3D v = new Vector3D(
						vertex_v[j][i+1][0]-vertex_v[j][i][0],
						vertex_v[j][i+1][1]-vertex_v[j][i][1],
						vertex_v[j][i+1][2]-vertex_v[j][i][2]
						);
				Vector3D normVector = Vector3D.cross(u, v);
				normVector.normalize();
				
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, Buffer.makeFloatBuffer(vertex_buf));
				gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, Buffer.makeFloatBuffer(tex_buf));
				gl.glNormalPointer(GL10.GL_FLOAT, 0, Buffer.makeFloatBuffer(normVector.makeFloatArr()));
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
				/*
				float[][] vector = {
						{
							vertex_v[j+1][i+1][0]-vertex_v[j+1][i][0],
							vertex_v[j+1][i+1][1]-vertex_v[j+1][i][1],
							vertex_v[j+1][i+1][2]-vertex_v[j+1][i][2],
						},
						{
							vertex_v[j][i][0]-vertex_v[j+1][i][0],
							vertex_v[j][i][1]-vertex_v[j+1][i][1],
							vertex_v[j][i][2]-vertex_v[j+1][i][2],
						}
				};
				float[] normal_buf = {
						- vector[1][1] * vector[0][2],
						- vector[0][0] * vector[1][2],
						  vector[0][0] * vector[1][1],
				};
				
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertex_buf));
				gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, makeFloatBuffer(tex_buf));
				gl.glNormalPointer(GL10.GL_FLOAT, 0, makeFloatBuffer(normal_buf));
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
				*/
			}
		}
	}
	
	/*-----------------------------------------------------------------------------*/
	//テクスチャ座標配列の作成
	@Deprecated
	private void genCosWave(){
		float Radius;
		
		for( int j=0; j<=DIV_Y; j++ ){
			for( int i=0; i<=DIV_X; i++ ){
				//頂点座標配列の作成
				vertex_v[j][i][0] = (float)((float)i/(float)DIV_X - 0.5f) * Ax;
				vertex_v[j][i][1] = (float)((float)j/(float)DIV_Y - 0.5f) * Ay;
				//テクスチャ凹凸の設定
				Radius = (float)Math.sqrt(Math.pow(vertex_v[j][i][0]-SensorManager.getGLx(), 2)
										+ Math.pow(vertex_v[j][i][1]-SensorManager.getGLy(), 2));
				if(Radius <= SensorManager.getGLrange() && SensorManager.getIsPushed()){
					//cos関数において-PI ~ PIまでの範囲を取って変形している
					vertex_v[j][i][2] = (float)(1.0f + Math.cos(Math.PI * Radius/SensorManager.getGLrange()))/2.0f 
							* SensorManager.getGLpressure();
				} else {
					vertex_v[j][i][2] = 0;
				}
				//テクスチャ配列の作成
				tex_coord_v[j][i][0] = (float)i/(float)DIV_X;
				tex_coord_v[j][i][1] = (float)j/(float)DIV_Y;
			}
		}
	}

	//eの指数関数に従う変形の作成
	private void genExponentialWave(){
		float Radius;
		
		for(int j = 0; j <= DIV_Y; j++){
			for(int i = 0; i <= DIV_X; i++){
				//頂点座標配列の作成
				vertex_v[j][i][0] = (float)((float)i/(float)DIV_X - 0.5f) * Ax;
				vertex_v[j][i][1] = (float)((float)j/(float)DIV_Y - 0.5f) * Ay;
				//中心からの距離
				Radius = (float)Math.sqrt(Math.pow(vertex_v[j][i][0]-SensorManager.getGLx(), 2)
						+ Math.pow(vertex_v[j][i][1]-SensorManager.getGLy(), 2));
				//テクスチャ凹凸の設定
				vertex_v[j][i][2] = (float)(Math.exp(-Math.pow(Radius, 2))) * 2 * SensorManager.getGLpressure();
				//テクスチャ配列の作成
				tex_coord_v[j][i][0] = (float)i/(float)DIV_X;
				tex_coord_v[j][i][1] = (float)j/(float)DIV_Y;
			}
		}
	}
	
	/*-----------------------------------------------------------------------------*/
	//Floatバッファの生成
	public static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer vb = ByteBuffer.allocateDirect(arr.length*4);
		vb.order(ByteOrder.nativeOrder());	//ビッグエンディアンかリトルエンディアンにあわせてくれる
		FloatBuffer fb = vb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

	/*-----------------------------------------------------------------------------*/
	//実験用テクスチャ変更関数
	public static void changeSurfaceExperiment(int count){
		/*
		sfMode = modeArray[count/2][count%2];//2種類で1セット
		if(count/(BaseActivity.MAX_COUNT/6) == 1
				|| count/(BaseActivity.MAX_COUNT/6) == 4) alphaMode = 1;//3種類の手のモデル
		else alphaMode = 0;
		texMode = sfMode*ALPHA + alphaMode;
		*/
	}
	
	/*-----------------------------------------------------------------------------*/
	/**
	 * @param gl	GL操作ハンドル
	 **/
	public boolean draw( GL10 gl){
		//テクスチャ管理番号バインド
		gl.glBindTexture(GL10.GL_TEXTURE_2D, BindTextures.getTexIndex());
		//表面形状のセット
		genExponentialWave();
		//材質の設定
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, GLMaterials.getAmbient(), 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, GLMaterials.getDiffuse(), 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, GLMaterials.getSpecular(), 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, GLMaterials.getShininess(), 0);
		
		setBuffer(gl);
		return true;
	}
}