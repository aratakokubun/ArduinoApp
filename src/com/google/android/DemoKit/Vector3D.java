package com.google.android.DemoKit;

/**
 * @author art
 * vector�v�Z���s���N���X���Ȃ������̂Ŏ���
 */
public class Vector3D
{
	protected float x;
	protected float y;
	protected float z;
	
	/* ------------------------------------------------------------------------- */
	public Vector3D(){
		this.x = 0f;
		this.y = 0f;
		this.z = 0f;
	}
	public Vector3D(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/* ------------------------------------------------------------------------- */
	/**
	 * �w�肵���x�N�g���Ƃ̊O�ς��v�Z
	 */
	public Vector3D cross(Vector3D v)
	{
		return new Vector3D(
				this.y * v.z - this.z * v.y,
				this.z * v.x - this.x - v.z,
				this.x * v.y - this.y - v.x
		);
	}
	
	/**
	 * �w�肳�ꂽ�x�N�g���Ƃ̓��ς��v�Z
	 */
	public float dot(Vector3D v)
	{
		return this.x * v.x + this.y * v.y + this.z * v.z;
	}
	
	/* ------------------------------------------------------------------------- */
	/**
	 * �w�肳�ꂽ2�̃x�N�g���̊O�ς��v�Z
	 */
	public static Vector3D cross(Vector3D u, Vector3D v)
	{
		return new Vector3D(
				u.y * v.z - u.z * v.y,
				u.z * v.x - u.x - v.z,
				u.x * v.y - u.y - v.x
		);
	}
	
	/**
	 * �w�肳�ꂽ2�̃x�N�g���̓��ς��v�Z
	 */
	public static float dot(Vector3D u, Vector3D v)
	{
		return u.x * v.x + u.y * v.y + u.z * v.z;
	}
	
	/* ------------------------------------------------------------------------- */
	/**
	 * ���̃x�N�g���𐳋K��(�P�ʃx�N�g����)
	 */
	public void normalize(){
		float norm = dot(this);
		if(norm != 0){
			x = x/norm;
			y = y/norm;
			z = z/norm;
		}
	}

	/* ------------------------------------------------------------------------- */
	/**
	 * ���̃x�N�g������float�z��𐶐�
	 */
	public float[] makeFloatArr(){
		float[] arr = {x, y ,z};
		return arr;
	}
}