package com.superdata.pm.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;

/**
 * 保存添加网站信息
 * @ClassName SaveAppUtil
 * @author Administrator
 * @date 2014年7月25日 上午9:56:26
 *
 */
public class SaveAppUtil {

	private Context context;
	
	public SaveAppUtil(Context context) {
		this.context = context;
	}
	
	/**
	 * 保存数据（内容以追加模式）
	 * @param filename
	 * @param content
	 * @throws Exception
	 */
	public void saveData(String filename, String content) throws Exception{
		FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
		outputStream.write(content.getBytes());
		outputStream.close();
	}
	
	/**
	 * 保存数据（内容以覆盖模式）
	 * @param filename
	 * @param content
	 * @throws Exception
	 */
	public void save(String filename, String content) throws Exception{
		FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
		outputStream.write(content.getBytes());
		outputStream.close();
	}
	
	/**
	 * 读取文件内容
	 * @param filename
	 * @param content
	 * @throws Exception
	 */
	public String read(String filename) throws Exception {
		FileInputStream insStream = context.openFileInput(filename);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = insStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		String str = new String(data);
		return str;
	}
	
	/**
	 * 读取添加网站数据
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String[] readApps(String filename) throws Exception{
		FileInputStream insStream = context.openFileInput(filename);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = insStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		String str = new String(data);
		String[] result = new String[str.length()];
		for(int i=0; i<str.length(); i++){
			result = str.split(";");
		}
		insStream.close();
		return result;
	}
	
	
}
