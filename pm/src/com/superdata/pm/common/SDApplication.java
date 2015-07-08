/**
* <p>Title: SDApplication<／p>
* <p>Description: <／p>
* <p>Company: SD<／p> 
* @author luolei
* @date 2014年6月6日
*/

package com.superdata.pm.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.superdata.pm.common.exception.YBAppException;

import android.app.Application;

/**
 * Title: SDApplication
 * Description:存放共有的数据
 * Company: SD 
 * @author luolei
 * @date 2014年6月6日
 */
public class SDApplication extends Application{

	public void onCreate(){
		super.onCreate();
		//拦截异常，对异常进行友好处理（开发时可以去掉，发布正式版本加上去）
		/*YBAppException exception = YBAppException.getInstance();
		exception.init(this);*/
	}
	
	/**
	 * 判断换成数据是否可读
	 * @param cachefile
	 * @return
	 */
	public boolean isReadDataCache(String cachefile){
		return readObject(cachefile) != null;
	}
	
	/**
	 * 判断缓存是否存在
	 * @param cachefile
	 * @return
	 */
	public boolean isExistDataCache(String cachefile){
		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if(data.exists())
			exist = true;
		return exist;
	}
	
	/**
	 * 保存对象
	 * @param ser
	 * @param file
	 * @return
	 */
	public boolean saveObject(Serializable ser, String file){
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				oos.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 读取对象
	 * @param file
	 * @return
	 */
	public Serializable readObject(String file){
		if(!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable)ois.readObject();
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof InvalidClassException){
				File data = getFileStreamPath(file);
				data.delete();
			}
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
			}
			try {
				fis.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
}
