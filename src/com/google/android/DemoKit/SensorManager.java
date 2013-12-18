package com.google.android.DemoKit;

public class SensorManager
{
	//圧力値の上限値を設定
	public static final float MAX_PRESS = 0.443f;

	//押している判定圧力の境界値
	public static final float THRESHOLD_PRESSURE = 0.0f;
	
	//センサから直にとった値
	private static float pressure;
	private static float x, y;			//現在の押下座標
	private static float baseX, baseY;	//初期押下時の座標
	private static boolean isPushed;
	
	//GL描画用に変換した値
	private static float GLpressure;
	private static float GLrange;
	private static float GLx, GLy;
	private static float GLbaseX, GLbaseY;
	private static float dfmAmnt;//変形量の係数
	
	//圧力のログを取得し，フィードバックをかける
	private static final int LOG = 5;	//何回分のログを取得するか
	private static float[][] log = new float[LOG][4];
	private static float delay;	//遅延の係数

	public SensorManager()
	{
		initSensorValue();
	}
	
	public static void initSensorValue()
	{
		pressure = 0.0f;
		x = GLRenderer.BASE_WIDTH /2;
		y = GLRenderer.BASE_HEIGHT /2;
		baseX = x;
		baseY = y;
		isPushed = false;
		
		GLpressure = 0.0f;
		GLrange = 0.0f;
		GLx = 0.0f;
		GLy = 0.0f;
		GLbaseX = 0.0f;
		GLbaseY = 0.0f;
		// read texure forn bind texture and se dfnAmnt.
		// the structure of class is not effective because texture and dfmAmnt are in the separeted classes.
		// if time forgive, please fix them in the same class. (Maybe it is desirable in the class BindTexture)
		// dfmAmnt = 4.0f;
		dfmAmnt = BindTextures.getDfmAmnt();
		
				
		for(int i = 0; i < LOG; i++){
			for(int j = 0; j < 2; j++){
				log[i][j] = 0.0f;
			}
		}
		delay = 0.9f;
	}
	
	/* ------------------------------------------------------------------------------ */
	public static float getPressure()
	{
		return pressure;
	}
	
	public static float getX()
	{
		return x;
	}
	
	public static float getY()
	{
		return y;
	}
	
	public static float getBaseX()
	{
		return baseX;
	}
	
	public static float getBaseY()
	{
		return baseY;
	}
	
	public static boolean getIsPushed()
	{
		return isPushed;
	}
	
	public static float getGLpressure()
	{
		return GLpressure;
	}
	
	public static float getGLrange()
	{
		return GLrange;
	}
	
	public static float getGLx()
	{
		return GLx;
	}
	
	public static float getGLy()
	{
		return GLy;
	}
	
	public static float getGLbaseX()
	{
		return GLbaseX;
	}
	
	public static float getGLbaseY()
	{
		return GLbaseY;
	}
	
	/* ------------------------------------------------------------------------------ */
	public static boolean updateSensorValue(float pressValue, float xValue, float yValue)
	{
		pressure = pressValue;
		x = xValue;
		y = yValue;
		if(pressure > THRESHOLD_PRESSURE){
			if(!isPushed){
				baseX = xValue;
				baseY = yValue;
			}
			return isPushed = true;
		} else {
			return isPushed = false;
		}
	}
	
	public static boolean updateFSRValue(float pressValue)
	{
		pressure = pressValue;
		if(pressure > THRESHOLD_PRESSURE){
			return isPushed = true;
		} else {
			return isPushed = false;
		}
	}

	public static void updateTouchValue(float xValue, float yValue)
	{
		x = xValue;
		y = yValue;
		if(!isPushed){
			baseX = xValue;
			baseY = yValue;
		}
	}
	
	public static void setDfmAmnt(float dfmValue)
	{
		dfmAmnt = dfmValue;
	}
	
	public static void setDelay(float delayValue)
	{
		delay = delayValue;
	}
	
	/*-------------------------------------------------------------------------*/
	/**
	 * GL用のセンサ値変換
	 */
	public static void convertSensorValueGL(float Ax, float Ay)
	{
		//画面サイズに合わせて変換(x:0~800 → -Ax/2~Ax/2 , y:0~480 → -Ay/2~Ay/2)
		//GLx = - (x - GLRenderer.BASE_WIDTH /2) / GLRenderer.BASE_WIDTH  * Ax;
		//GLy =   (y - GLRenderer.BASE_HEIGHT/2) / GLRenderer.BASE_HEIGHT * Ay;
		GLx = 0;
		GLy = 0;
		
		/*
		 * 電圧から圧力(g)への変換について(式についてはデータシート参照)
		 * x軸に圧力[log(g)],縦軸に抵抗[kΩ]をとったとき、(30, 20)と(0.25, 10000)を通る直線のグラフになる。つまり,
		 * (R - 30) / (logX - log20) = - 29.75 / log500
		 * また,電圧と抵抗の関係は
		 * V[v] = - (1.5[kΩ] * 5[v]) / R[kΩ]
		 * これより、
		 * (-7.5/V - 30)/log(X/20) = - 29.75 / log500
		 *  maxValue = 0.17f, minValue = 0.00f(電圧に変換するには5倍にする必要がある)
		 *  全範囲の電圧値を0.050f(R=30)から0.167f(検出限界値)までの範囲で変換し、最大値がMAX_PRESSになるように等倍する
		 */
		float fpshi		= 20f * (float)Math.pow(500, (30-7.5/pressure)/29.75);
		float maxFpshi	= 20f * (float)Math.pow(500, (30-7.5/0.835f  )/29.75);//0.835は検出限界の最大
		float minFpshi	= 20f * (float)Math.pow(500, (30-7.5/0.250f  )/29.75);//0.250は検出限界の最小値
		float value 	= fpshi    / maxFpshi * MAX_PRESS;
		float minValue 	= minFpshi / maxFpshi * MAX_PRESS;
		GLrange = (float)Math.sqrt(value * 2) * 3.0f;
		
		//圧力値を一定範囲になるように変換
		GLpressure = value;
		if(GLpressure > MAX_PRESS){
			GLpressure = MAX_PRESS;
		} else if(GLpressure >= minValue){
			GLpressure = (GLpressure-minValue) / (MAX_PRESS-minValue) * MAX_PRESS;
		} else {
			GLpressure = 0f;
		}
		GLpressure *= dfmAmnt;
		
		// TODO
		updateLog();
	}
	
	/*-------------------------------------------------------------------------*/
	/**
	 * タッチ圧ログの取得
	 */
	public static void updateLog(){
		for(int i = LOG-1; i > 0; i--){
			log[i] = log[i-1];
		}
		
		// ログに記録
		log[0][0] = GLx;
		log[0][1] = GLy;
		log[0][2] = GLpressure;
		log[0][3] = GLrange;
		
		// 遅延を適用
		delaySensorManager();
		GLpressure = log[0][2];
		GLrange = log[0][3];
	}
	/**
	 * タッチ圧が下がった時に遅延させる
	 */
	private static void delaySensorManager(){
		if(log[0][2] < log[1][2]){
//			log[0][0] = log[0][0]*(1.0f-delay) + log[1][0]*delay;
//			log[0][1] = log[0][1]*(1.0f-delay) + log[1][1]*delay;
			log[0][2] = log[0][2]*(1.0f-delay) + log[1][2]*delay;
			log[0][3] = log[0][3]*(1.0f-delay) + log[1][3]*delay;
		}
	}
}