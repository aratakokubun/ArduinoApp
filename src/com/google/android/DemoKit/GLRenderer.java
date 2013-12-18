package com.google.android.DemoKit;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class GLRenderer implements Renderer {
	//��ʃT�C�Y
	public static final float BASE_WIDTH = 800, BASE_HEIGHT = 480;
	public static int width = 0, height = 0;
	
	// �e�N�X�`���T�C�Y�ݒ�
	private static final float MAG_RATE = 1.0f/0.9f;//�T�C�Y�̔�����
	private static float Ax = 0, Ay = 0;
	
	//���_�̕ύX
	private static float perX = 0, perY = 0;
	private static boolean perMode = false;
		
	//�`��I�u�W�F�N�g
	Texture texture;
	Hand hand;
	Number place100, place10, place1;
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT 
				| GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		//�����E�ʒu�p�x�̐ݒ�
		LightPositionController.setLightfv(gl, GL10.GL_LIGHT0);
		gl.glRotatef(perX, 1, 0, 0);
		gl.glRotatef(perY, 0, 1, 0);
		gl.glTranslatef(0, 0, -6f);
		
		//���O�̎擾�E�x������
		SensorManager.updateLog();

		//��̕`������̎擾
		int mode = BindTextures.getHandId();
		boolean isDraw = BindTextures.getIsTransParent();
		
		//�֊s�\���̍ۂɌ����ɂ����̂ŗ֊s�\���̏ꍇ�̂ݔw�i��,DEPTH��؂�
		if(mode == BindTextures.EDGE || mode == BindTextures.SHADOW){
			gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			gl.glDisable(GL10.GL_DEPTH_TEST);
		} else {
			gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			gl.glEnable(GL10.GL_DEPTH_TEST);
		}
		
		//��̕`��
		gl.glPushMatrix();
		{
			if(isDraw && mode == BindTextures.ALPHA) hand.draw(gl);
		}
		gl.glPopMatrix();
		//�e�N�X�`���I�u�W�F�N�g�̕`��
		gl.glPushMatrix();
		{
			texture.draw(gl);
		}
		gl.glPopMatrix();
		//��̗֊s�݂̂̕\��
		gl.glPushMatrix();
		{
			if(isDraw && mode == BindTextures.EDGE || mode == BindTextures.SHADOW) hand.draw(gl);
		}
		gl.glPopMatrix();
		//�����i�s�p�̔ԍ��\��
		gl.glPushMatrix();
		{
			if(BaseActivity.EXPERIMENT){
				place100.draw(gl);
				place10	.draw(gl);
				place1	.draw(gl);
			}
		}
		gl.glPopMatrix();
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45f,(float) width / height, 1f, 50f);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		initGL(gl);		
		BindTextures.setTexture(gl);
		
		Ax = BASE_WIDTH  / width * MAG_RATE;
		Ay = BASE_HEIGHT / height * MAG_RATE;
		
		//�`��N���X�̐錾
		texture = new Texture(Ax, Ay);
		hand = new Hand(Ax, Ay);
		place100 = new Number(Ax, Ay, 100);
		place10 = new Number(Ax, Ay, 10);
		place1 = new Number(Ax, Ay, 1);
	}
	
	private void initGL(GL10 gl)
	{
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);

		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		LightPositionController.setLightPosition(-1.0f, 1.0f, 2.0f);
		LightPositionController.setLightfv(gl, GL10.GL_LIGHT0);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glShadeModel(GL10.GL_SMOOTH);
	}
	
	/*-------------------------------------------------------------------------*/
	public static float getAx()
	{
		return Ax;
	}
	public static float getAy()
	{
		return Ay;
	}
	
	/*-------------------------------------------------------------------------*/
	/**
	 * change mode on/off perspective mode 
	 */
	public static boolean perspectiveMode()
	{
		return perMode = !perMode;
	}
	/**
	 * change perspective to look at texture
	 */
	public static void setPerspective(float x, float y){
		perY = (x - BASE_WIDTH/2) / BASE_WIDTH * 90;
		perX = (y - BASE_HEIGHT/2) / BASE_HEIGHT * 90;
	}
}