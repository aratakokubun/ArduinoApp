package com.google.android.DemoKit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

class Hand{
	//���_�o�b�t�@
	private static FloatBuffer vertexBuffer;
	//�e�N�X�`�����W�o�b�t�@
	private static FloatBuffer textureBuffer;
	
	//�e�N�X�`�����
	private static float texW = 1.0f, texH = 2.0f;
	//private static float FingerX = 0.7f, FingerY = -0.1f;//�w������ۂ̕\��
	//private static float FingerX = 0.813f, FingerY = 0.213f;//�l�����w�̂ݐL�΂����\��
	private static float FingerX = 0.813f, FingerY = -0.213f;//�l�����w�̂ݐL�΂����\��
	private static float[] texPos		= new float[3];
	private static float[][] Vertex2D 	= new float[4][3];
	
	public Hand(float w, float h){
		texW *= w; texH *= h;
		FingerX *= texW; FingerY *= texH;
		
		setCoordBuf();
		setVertBuf();
	}
	
	private void setCoordBuf(){
		//���_��x���_�\���v�f��x4�o�C�g�̃T�C�Y�̃o�b�t�@�쐬
		ByteBuffer vb = ByteBuffer.allocateDirect(4 * 2 * 4);
		vb.order(ByteOrder.nativeOrder());	//�r�b�O�G���f�B�A�������g���G���f�B�A���ɂ��킹�Ă����
		textureBuffer = vb.asFloatBuffer();
		
		//���_�f�[�^�Œ��_�o�b�t�@�쐬
		float[] vertices = {
				// �摜�f�[�^�̍�������_�ɁA0.0~1.0�͈̔͂̍��W
				0.0f, 1.0f,		//����
				1.0f, 1.0f,		//�E��
				0.0f, 0.0f,		//����
				1.0f, 0.0f,		//�E��
		};
		textureBuffer.put(vertices);
		textureBuffer.position(0);
	}
	
	private static void setVertBuf(){
		//���_��x���_�\���v�f��x4�o�C�g�̃T�C�Y�̃o�b�t�@�쐬
		ByteBuffer vb2D = ByteBuffer.allocateDirect(4 * 3 * 4);
		vb2D.order(ByteOrder.nativeOrder());	//�r�b�O�G���f�B�A�������g���G���f�B�A���ɂ��킹�Ă����
		vertexBuffer = vb2D.asFloatBuffer();
		
		//���_�f�[�^�Œ��_�o�b�t�@�쐬
		float[] vertices = new float[4 * 3]; 
		//���_�f�[�^�̒���
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
		//���_�f�[�^�Œ��_�o�b�t�@�쐬
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
	
	//Renderer����^�b�`�����󂯎��A���ɍ��킹�Ĉʒu�𒲐�
	public static void setPos(float X, float Y, float Press){
		texPos[0] = X + FingerX;
		texPos[1] = Y + FingerY;
		setVertBuf();
	}
	
	//�����p�e�N�X�`���ύX�֐�
	public static void changeModeExperiment(int count){
		/*
		int newMode = count/(BaseActivity.MAX_COUNT/6) % 3;
		texMode = MODEARRAY[newMode];//3��ނ̎�̃��f��
		*/
	}

	/*-------------------------------------------------------------------------*/
	/**
	 * @param gl	GL����n���h��
	 **/
	public void draw( GL10 gl){
		//�e�N�X�`���Ǘ��ԍ��o�C���h
		gl.glBindTexture(GL10.GL_TEXTURE_2D, BindTextures.getHandIndex());
		//���_�o�b�t�@�ݒ�
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		//�`��
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}