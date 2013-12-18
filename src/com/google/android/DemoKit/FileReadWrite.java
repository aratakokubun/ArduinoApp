package com.google.android.DemoKit;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import android.util.Log;

public class FileReadWrite {
	
	private static String fName = "";
	private static String bkFileName = "";
	private static String ENCODE = "UTF-8";
	
	public static final int START = 0;
	public static final int HEADER = 1;
	public static final int DATA = 2;
	public static final int RETURN = 3;
	public static final int END = 4;
	public static final int ARRAYDATA = 5;
	
	public static void setFileTitle(String name){
		//ファイル名(年月日時分、被験者名)
		final Calendar calendar = Calendar.getInstance();
		String date = "" 
					+ calendar.get(Calendar.YEAR)
					+ (int)(calendar.get(Calendar.MONTH)+1)
					+ calendar.get(Calendar.DAY_OF_MONTH)
					+ calendar.get(Calendar.HOUR_OF_DAY)
					+ calendar.get(Calendar.MINUTE);
		
		fName	   = "/mnt/sdcard/experiment/" 			  + date + "_" + name + ".csv";
		bkFileName = "/mnt/sdcard/experiment/" + "BackUp" + date + "_" + name + ".csv";
	}
	
	public static void WriteFile(String sData, int sMode){
		String writeData;
		
		switch(sMode){
		case START:		writeData = "LOG START ," + "\n NAME : ," + sData + ",";
						break;
		case HEADER:	writeData = "\n,\n MODEL : ," + sData + ",";
						break;
		case DATA:		writeData = sData + ",";
						break;
		case RETURN:	writeData = "\n" + sData + ",";
						break;
		case END:		writeData = "\n,\n LOG END ,";
						break;
		case ARRAYDATA:	writeData = "\n MODE ARRAY," + sData + ",\n";
						break;
		default:		writeData = ",";
						break;
		}
		
		BufferedWriter bufferedWriterObj = null;
		try {
			//ファイル出力ストリームの作成
			bufferedWriterObj = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fName, true), ENCODE));
	
			bufferedWriterObj.write(writeData);
			bufferedWriterObj.flush();

		} catch (Exception e) {
			Log.d("CommonFile.writeFile", e.getMessage());
		} finally {
			try {
				if( bufferedWriterObj != null) bufferedWriterObj.close();
			} catch (IOException e2) {
				Log.d("CommonFile.writeFile", e2.getMessage());
			}
		}
	}
	
	public static void WriteBackUp(String sData){
		String writeData;
		writeData = "BACKUP ,\n" + "\n MODE ARRAY," + sData + ",\n"; 
		BufferedWriter bufferedWriterObj = null;
		try {
			//ファイル出力ストリームの作成
			bufferedWriterObj = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(bkFileName, true), ENCODE));
	
			bufferedWriterObj.write(writeData);
			bufferedWriterObj.flush();

		} catch (Exception e) {
			Log.d("CommonFile.writeBackUpFile", e.getMessage());
		} finally {
			try {
				if( bufferedWriterObj != null) bufferedWriterObj.close();
			} catch (IOException e2) {
				Log.d("CommonFile.writeBackUpFile", e2.getMessage());
			}
		}
	}
}