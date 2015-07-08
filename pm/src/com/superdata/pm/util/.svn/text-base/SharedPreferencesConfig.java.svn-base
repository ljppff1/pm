/**
 * @Title SharedPreferences.java
 * @Package com.superdata.soho.util
 * @author Administrator
 * @date 2014年7月5日 上午9:11:25
 * @version V1.0
 */
package com.superdata.pm.util;

import java.util.HashMap;
import java.util.Map;


import com.superdata.pm.config.InterfaceConfig;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 轻量级数据存储
 * @ClassName SharedPreferences
 * @author Administrator
 * @date 2014年7月5日 上午9:11:25
 *
 */
public class SharedPreferencesConfig {
	public final static String CONFIG_NAME = "KC_MOBILE_CONFIG";
	public static SharedPreferences sharedPreferences;

	public synchronized static Map<String, String> config(Context context) {
		Map<String, String> configMap = new HashMap<String, String>();
		sharedPreferences = context.getSharedPreferences(CONFIG_NAME,Context.MODE_PRIVATE);

		configMap.put(InterfaceConfig.LAST_USER_NAME, sharedPreferences
				.getString(InterfaceConfig.LAST_USER_NAME,
						InterfaceConfig.EMPTY_STRING));
		configMap.put(InterfaceConfig.ID, sharedPreferences
				.getString(InterfaceConfig.ID,
						InterfaceConfig.EMPTY_STRING));
		
		configMap.put(InterfaceConfig.LOGIN_USER_NAME, sharedPreferences
				.getString(InterfaceConfig.LOGIN_USER_NAME,
						InterfaceConfig.EMPTY_STRING));
		configMap.put(InterfaceConfig.PASSWORD, sharedPreferences
				.getString(InterfaceConfig.PASSWORD,
						InterfaceConfig.EMPTY_STRING));
		configMap.put(InterfaceConfig.COMPANYID, sharedPreferences
				.getString(InterfaceConfig.COMPANYID,
						InterfaceConfig.EMPTY_STRING));
		configMap.put(InterfaceConfig.POSITIONNAME, sharedPreferences
				.getString(InterfaceConfig.POSITIONNAME,
						InterfaceConfig.EMPTY_STRING));

		return configMap;
	}
	
	
	public synchronized static Map<String, Boolean> config1(Context context){
		Map<String, Boolean> configMap = new HashMap<String, Boolean>();
		sharedPreferences = context.getSharedPreferences(CONFIG_NAME,Context.MODE_PRIVATE);
		
		configMap.put(InterfaceConfig.ISSTOP, sharedPreferences
				.getBoolean(InterfaceConfig.ISSTOP,
						InterfaceConfig.IS_STOP));
		
		return configMap;
	}
	
	
/**
 * 存储数据
 * @Title saveConfig
 * @param context
 * @param keyName 名称
 * @param keyValue 值
 */
	public synchronized static void saveConfig(Context context, String keyName,
			String keyValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				CONFIG_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(keyName, keyValue);
		editor.commit();
	}
	
	
	public synchronized static void saveConfig1(Context context, String keyName,
			Boolean keyValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				CONFIG_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(keyName, keyValue);
		editor.commit();
	}
	
/**
 * 
 * @Title clearData 清楚所有保存数据
 */
	public static void clearData() {
		if (sharedPreferences != null)
			sharedPreferences.edit().clear().commit();
	}
}
