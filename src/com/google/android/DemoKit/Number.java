package com.google.android.DemoKit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

class Number{
	//���_�o�b�t�@
	private FloatBuffer vertexBuffer;
	//�e�N�X�`�����W�o�b�t�@
	private FloatBuffer textureBuffer;

	//�e�N�X�`�����
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
		//���_��x���_�\���v�f��x4�o�C�g�̃T�C�Y�̃o�b�t�@�쐬
		ByteBuffer vb = ByteBuffer.allocateDirect(4 * 2 * 4);
		vb.order(ByteOrder.nativeOrder());	//�r�b�O�G���f�B�A�������g���G���f�B�A���ɂ��킹�Ă����
		textureBuffer = vb.asFloatBuffer();
		
		//���_�f�[�^�Œ��_�o�b�t�@�쐬
		float[] vertices = {
				// �摜�f�[�^�̍�������_�ɁA0.0~1.0�͈̔͂̍��W
				1.0f * num/10*100/128	 , 1.0f*20/32,		//����
				1.0f * (num+1)/10*100/128, 1.0f*20/32,		//�E��
				1.0f * num/10*100/128	 , 0.0f		 ,		//����
				1.0f * (num+1)/10*100/128, 0.0f		 ,		//�E��
		};
		textureBuffer.put(vertices);
		textureBuffer.position(0);
	}
	
	private void setVertBuf(){
		//���_��x���_�\���v�f��x4�o�C�g�̃T�C�Y�̃o�b�t�@�쐬
		ByteBuffer vb2D = ByteBuffer.allocateDirect(4 * 3 * 4);
		vb2D.order(ByteOrder.nativeOrder());	//�r�b�O�G���f�B�A�������g���G���f�B�A���ɂ��킹�Ă����
		vertexBuffer = vb2D.asFloatBuffer();
		
		//���_�f�[�^�Œ��_�o�b�t�@�쐬
		float[] vertices = new float[4 * 3]; 
		//���_�f�[�^�̒���
		float[][] temp2D = {
				{-texW, -texH, 0},  // -/-
				{ texW, -texH, 0},  // +/-
				{-texW,  texH, 0},  // -/+
				{ texW,  texH, 0},  // +/+
		};
		//���_�f�[�^�Œ��_�o�b�t�@�쐬
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
	 * @param gl	GL����n���h��
	 **/
	public void draw( GL10 gl){
		//�e�N�X�`���Ǘ��ԍ��o�C���h
		gl.glBindTexture(GL10.GL_TEXTURE_2D, BindTextures.getNumIndex());
		//���_�o�b�t�@�ݒ�
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		//�`��
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}