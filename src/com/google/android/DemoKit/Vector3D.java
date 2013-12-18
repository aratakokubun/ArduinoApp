package com.google.android.DemoKit;

/**
 * @author art
 * vector計算を行うクラスがなかったので自作
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
	 * 指定したベクトルとの外積を計算
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
	 * 指定されたベクトルとの内積を計算
	 */
	public float dot(Vector3D v)
	{
		return this.x * v.x + this.y * v.y + this.z * v.z;
	}
	
	/* ------------------------------------------------------------------------- */
	/**
	 * 指定された2つのベクトルの外積を計算
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
	 * 指定された2つのベクトルの内積を計算
	 */
	public static float dot(Vector3D u, Vector3D v)
	{
		return u.x * v.x + u.y * v.y + u.z * v.z;
	}
	
	/* ------------------------------------------------------------------------- */
	/**
	 * このベクトルを正規化(単位ベクトル化)
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
	 * このベクトルからfloat配列を生成
	 */
	public float[] makeFloatArr(){
		float[] arr = {x, y ,z};
		return arr;
	}
}