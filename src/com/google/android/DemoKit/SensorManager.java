package com.google.android.DemoKit;

public class SensorManager
{
	//���͒l�̏���l��ݒ�
	public static final float MAX_PRESS = 0.443f;

	//�����Ă��锻�舳�͂̋��E�l
	public static final float THRESHOLD_PRESSURE = 0.0f;
	
	//�Z���T���璼�ɂƂ����l
	private static float pressure;
	private static float x, y;			//���݂̉������W
	private static float baseX, baseY;	//�����������̍��W
	private static boolean isPushed;
	
	//GL�`��p�ɕϊ������l
	private static float GLpressure;
	private static float GLrange;
	private static float GLx, GLy;
	private static float GLbaseX, GLbaseY;
	private static float dfmAmnt;//�ό`�ʂ̌W��
	
	//���͂̃��O���擾���C�t�B�[�h�o�b�N��������
	private static final int LOG = 5;	//���񕪂̃��O���擾���邩
	private static float[][] log = new float[LOG][4];
	private static float delay;	//�x���̌W��

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
	 * GL�p�̃Z���T�l�ϊ�
	 */
	public static void convertSensorValueGL(float Ax, float Ay)
	{
		//��ʃT�C�Y�ɍ��킹�ĕϊ�(x:0~800 �� -Ax/2~Ax/2 , y:0~480 �� -Ay/2~Ay/2)
		//GLx = - (x - GLRenderer.BASE_WIDTH /2) / GLRenderer.BASE_WIDTH  * Ax;
		//GLy =   (y - GLRenderer.BASE_HEIGHT/2) / GLRenderer.BASE_HEIGHT * Ay;
		GLx = 0;
		GLy = 0;
		
		/*
		 * �d�����爳��(g)�ւ̕ϊ��ɂ���(���ɂ��Ă̓f�[�^�V�[�g�Q��)
		 * x���Ɉ���[log(g)],�c���ɒ�R[k��]���Ƃ����Ƃ��A(30, 20)��(0.25, 10000)��ʂ钼���̃O���t�ɂȂ�B�܂�,
		 * (R - 30) / (logX - log20) = - 29.75 / log500
		 * �܂�,�d���ƒ�R�̊֌W��
		 * V[v] = - (1.5[k��] * 5[v]) / R[k��]
		 * ������A
		 * (-7.5/V - 30)/log(X/20) = - 29.75 / log500
		 *  maxValue = 0.17f, minValue = 0.00f(�d���ɕϊ�����ɂ�5�{�ɂ���K�v������)
		 *  �S�͈͂̓d���l��0.050f(R=30)����0.167f(���o���E�l)�܂ł͈̔͂ŕϊ����A�ő�l��MAX_PRESS�ɂȂ�悤�ɓ��{����
		 */
		float fpshi		= 20f * (float)Math.pow(500, (30-7.5/pressure)/29.75);
		float maxFpshi	= 20f * (float)Math.pow(500, (30-7.5/0.835f  )/29.75);//0.835�͌��o���E�̍ő�
		float minFpshi	= 20f * (float)Math.pow(500, (30-7.5/0.250f  )/29.75);//0.250�͌��o���E�̍ŏ��l
		float value 	= fpshi    / maxFpshi * MAX_PRESS;
		float minValue 	= minFpshi / maxFpshi * MAX_PRESS;
		GLrange = (float)Math.sqrt(value * 2) * 3.0f;
		
		//���͒l�����͈͂ɂȂ�悤�ɕϊ�
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
	 * �^�b�`�����O�̎擾
	 */
	public static void updateLog(){
		for(int i = LOG-1; i > 0; i--){
			log[i] = log[i-1];
		}
		
		// ���O�ɋL�^
		log[0][0] = GLx;
		log[0][1] = GLy;
		log[0][2] = GLpressure;
		log[0][3] = GLrange;
		
		// �x����K�p
		delaySensorManager();
		GLpressure = log[0][2];
		GLrange = log[0][3];
	}
	/**
	 * �^�b�`���������������ɒx��������
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