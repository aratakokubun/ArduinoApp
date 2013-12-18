package com.google.android.DemoKit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

class Texture {
	//������
	public static final int DIV_X = 40, DIV_Y = 24;
	
	//�e�N�X�`���T�C�Y
	private static float Ax = 0, Ay = 0;
	//�e�N�X�`�����
	private float[][][] tex_coord_v = new float [DIV_Y+1][DIV_X+1][2]; /* �e�N�X�`�����W�s�� */
	private float[][][] vertex_v 	= new float [DIV_Y+1][DIV_X+1][3]; /* �|���S�����_�s�� */
	
	//�����p�e�N�X�`���z��
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
		//���̓���ւ�
		for(int i = 0; i < BaseActivity.MAX_COUNT/4; i++){
			if(Math.random() < 0.5){//��5���̊m���œ���ւ�
				int no1 = i;
				int no2 = i + BaseActivity.MAX_COUNT/4;
				int[] temp1 = modeArray[no1];
				int[] temp2 = modeArray[no2];
				modeArray[no1] = temp2;
				modeArray[no2] = temp1;
			}
		}
		
		//���s���Ԃ̕ύX
		for(int i = 0; i < 6; i++){
			int hand = i*(BaseActivity.MAX_COUNT/12);
			for(int j = 0; j < 500; j++){//500�����ւ�
				int no1 = hand+(int)((BaseActivity.MAX_COUNT/12)*Math.random());
				int no2 = hand+(int)((BaseActivity.MAX_COUNT/12)*Math.random());
				int[] temp1 = modeArray[no1];
				int[] temp2 = modeArray[no2];
				modeArray[no1] = temp2;
				modeArray[no2] = temp1;
			}
		}
	}
	
	//�o�b�t�@�̍쐬�ƕ`��
	private void setBuffer(GL10 gl){
		for(int j=0; j<DIV_Y; j++ ){
			for(int i=0; i<DIV_X; i++ ){
				/*
				float[] tex_buf = {
						// �摜�f�[�^�̍�������_�ɁA0.0~1.0�͈̔͂̍��W
						tex_coord_v[j+1][i][0]	, tex_coord_v[j+1][i][1],			//����
						tex_coord_v[j+1][i+1][0], tex_coord_v[j+1][i+1][1],			//�E��
						tex_coord_v[j][i][0]	, tex_coord_v[j][i][1],				//����
						tex_coord_v[j][i+1][0]	, tex_coord_v[j][i+1][1],			//�E��
				};
				*/
				float[] tex_buf = {
						// �摜�f�[�^�̍�������_�ɁA0.0~1.0�͈̔͂̍��W
						tex_coord_v[j][i][0]	, tex_coord_v[j][i][1],				//����
						tex_coord_v[j][i+1][0]	, tex_coord_v[j][i+1][1],			//�E��
						tex_coord_v[j+1][i][0]	, tex_coord_v[j+1][i][1],			//����
						tex_coord_v[j+1][i+1][0], tex_coord_v[j+1][i+1][1],			//�E��
				};

				float[] vertex_buf = {
						vertex_v[j][i][0]    ,vertex_v[j][i][1]    ,vertex_v[j][i][2],
						vertex_v[j][i+1][0]  ,vertex_v[j][i+1][1]  ,vertex_v[j][i+1][2],
						vertex_v[j+1][i][0]  ,vertex_v[j+1][i][1]  ,vertex_v[j+1][i][2],
						vertex_v[j+1][i+1][0],vertex_v[j+1][i+1][1],vertex_v[j+1][i+1][2]
				};
				/*
				 * 4�_��tex_buf�̏��Ԃ�A,B,C,D�Ƃ��ē�̕��ʏ�̃x�N�g��AB={x1,y1,z1},AC={x2,y2,z2}�Ɩ@���x�N�g��N={x, y, z}�̓���
				 * AB*N=0,AC*N=0����@���x�N�g���̊e���������߂�
				 * ���̂Ƃ��AAB�x�N�g����y������AC�x�N�g����x������0(y1=0,x2=0)
				 * ������ x:y:z = -y2z1:-x1z2:x1y2 �Ŗ@���x�N�g�������܂�
				 */
				//�@���x�N�g���̌v�Z
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
	//�e�N�X�`�����W�z��̍쐬
	@Deprecated
	private void genCosWave(){
		float Radius;
		
		for( int j=0; j<=DIV_Y; j++ ){
			for( int i=0; i<=DIV_X; i++ ){
				//���_���W�z��̍쐬
				vertex_v[j][i][0] = (float)((float)i/(float)DIV_X - 0.5f) * Ax;
				vertex_v[j][i][1] = (float)((float)j/(float)DIV_Y - 0.5f) * Ay;
				//�e�N�X�`�����ʂ̐ݒ�
				Radius = (float)Math.sqrt(Math.pow(vertex_v[j][i][0]-SensorManager.getGLx(), 2)
										+ Math.pow(vertex_v[j][i][1]-SensorManager.getGLy(), 2));
				if(Radius <= SensorManager.getGLrange() && SensorManager.getIsPushed()){
					//cos�֐��ɂ�����-PI ~ PI�܂ł͈̔͂�����ĕό`���Ă���
					vertex_v[j][i][2] = (float)(1.0f + Math.cos(Math.PI * Radius/SensorManager.getGLrange()))/2.0f 
							* SensorManager.getGLpressure();
				} else {
					vertex_v[j][i][2] = 0;
				}
				//�e�N�X�`���z��̍쐬
				tex_coord_v[j][i][0] = (float)i/(float)DIV_X;
				tex_coord_v[j][i][1] = (float)j/(float)DIV_Y;
			}
		}
	}

	//e�̎w���֐��ɏ]���ό`�̍쐬
	private void genExponentialWave(){
		float Radius;
		
		for(int j = 0; j <= DIV_Y; j++){
			for(int i = 0; i <= DIV_X; i++){
				//���_���W�z��̍쐬
				vertex_v[j][i][0] = (float)((float)i/(float)DIV_X - 0.5f) * Ax;
				vertex_v[j][i][1] = (float)((float)j/(float)DIV_Y - 0.5f) * Ay;
				//���S����̋���
				Radius = (float)Math.sqrt(Math.pow(vertex_v[j][i][0]-SensorManager.getGLx(), 2)
						+ Math.pow(vertex_v[j][i][1]-SensorManager.getGLy(), 2));
				//�e�N�X�`�����ʂ̐ݒ�
				vertex_v[j][i][2] = (float)(Math.exp(-Math.pow(Radius, 2))) * 2 * SensorManager.getGLpressure();
				//�e�N�X�`���z��̍쐬
				tex_coord_v[j][i][0] = (float)i/(float)DIV_X;
				tex_coord_v[j][i][1] = (float)j/(float)DIV_Y;
			}
		}
	}
	
	/*-----------------------------------------------------------------------------*/
	//Float�o�b�t�@�̐���
	public static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer vb = ByteBuffer.allocateDirect(arr.length*4);
		vb.order(ByteOrder.nativeOrder());	//�r�b�O�G���f�B�A�������g���G���f�B�A���ɂ��킹�Ă����
		FloatBuffer fb = vb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

	/*-----------------------------------------------------------------------------*/
	//�����p�e�N�X�`���ύX�֐�
	public static void changeSurfaceExperiment(int count){
		/*
		sfMode = modeArray[count/2][count%2];//2��ނ�1�Z�b�g
		if(count/(BaseActivity.MAX_COUNT/6) == 1
				|| count/(BaseActivity.MAX_COUNT/6) == 4) alphaMode = 1;//3��ނ̎�̃��f��
		else alphaMode = 0;
		texMode = sfMode*ALPHA + alphaMode;
		*/
	}
	
	/*-----------------------------------------------------------------------------*/
	/**
	 * @param gl	GL����n���h��
	 **/
	public boolean draw( GL10 gl){
		//�e�N�X�`���Ǘ��ԍ��o�C���h
		gl.glBindTexture(GL10.GL_TEXTURE_2D, BindTextures.getTexIndex());
		//�\�ʌ`��̃Z�b�g
		genExponentialWave();
		//�ގ��̐ݒ�
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, GLMaterials.getAmbient(), 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, GLMaterials.getDiffuse(), 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, GLMaterials.getSpecular(), 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, GLMaterials.getShininess(), 0);
		
		setBuffer(gl);
		return true;
	}
}