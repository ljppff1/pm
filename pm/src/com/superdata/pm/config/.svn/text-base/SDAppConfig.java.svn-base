package com.superdata.pm.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.superdata.pm.util.StringUtils;

/**
 * 
* Title: YBAppConfig
* Description:应用程序配置类：用于保存用户相关信息及设置
* Company: SD 
* @author luolei
* @date 2014年6月6日
 */

public class SDAppConfig {

	private final static String APP_CONFIG = "config";

	/**
	 * app唯一性标示
	 */
	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";

	/**
	 * cookie
	 */
	public final static String CONF_COOKIE = "cookie";

	/**
	 * 访问令牌，用于oauth授权
	 */
	public final static String CONF_ACCESSTOKEN = "accessToken";

	/**
	 * 访问机密，用于oauth授权
	 */
	public final static String CONF_ACCESSSECRET = "accessSecret";

	/**
	 * 过期时间，用于oauth授权
	 */
	public final static String CONF_EXPIRESIN = "expiresIn";

	/**
	 * 加载图片
	 */
	public final static String CONF_LOAD_IMAGE = "perf_loadimage";

	/**
	 * 保存图片路径
	 */
	public final static String SAVE_IMAGE_PATH = "save_image_path";

	/**
	 * 保存文件路径
	 */
	public final static String SAVE_FILE_PATH = "sava_file_path";

	/**
	 * 保存xml路径
	 */
	public final static String SAVE_XML_PATH = "sava_xml_path";

	/**
	 * 默认保存图片路径
	 */
	public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory() + File.separator + "image" + File.separator;

	public final static String DEFAULT_SAVE_FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + "files" + File.separator;

	public final static String DEFAULT_SAVE_XML_PATH = DEFAULT_SAVE_FILE_PATH + "xml" + File.separator;

	private Context mContext;

	private static SDAppConfig appConfig;

	public static SDAppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new SDAppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	/**
	 * 获取Preference设置
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * 是否加载显示文章图片
	 * 
	 * @param context
	 *            上下文
	 * @return
	 */
	public static boolean isLoadImage(Context context) {
		return getSharedPreferences(context).getBoolean(CONF_LOAD_IMAGE, true);
	}

	/**
	 * 获得cookie
	 * 
	 * @return 字符串
	 */
	public String getCookie() {
		return get(CONF_COOKIE);
	}

	/**
	 * 设置访问令牌，用于oauth授权
	 * 
	 * @param accessToken
	 */
	public void setAccessToken(String accessToken) {
		set(CONF_ACCESSTOKEN, accessToken);
	}

	/**
	 * 得到访问令牌，用于oauth授权
	 * 
	 * @return
	 */
	public String getAccessToken() {
		return get(CONF_ACCESSTOKEN);
	}

	/**
	 * 设置访问机密，用于oauth授权
	 * 
	 * @param accessSecret
	 */
	public void setAccessSecret(String accessSecret) {
		set(CONF_ACCESSSECRET, accessSecret);
	}

	/**
	 * 得到访问机密，用于oauth授权
	 * 
	 * @return
	 */
	public String getAccessSecret() {
		return get(CONF_ACCESSSECRET);
	}

	/**
	 * 设置过期时间，用于oauth授权
	 * 
	 * @param expiresIn
	 */
	public void setExpiresIn(long expiresIn) {
		set(CONF_EXPIRESIN, String.valueOf(expiresIn));
	}

	/**
	 * 得到过期时间，用于oauth授权
	 * 
	 * @return
	 */
	public long getExpiresIn() {
		return StringUtils.toLong(get(CONF_EXPIRESIN));
	}

	/**
	 * 根据关键字获得属性值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}

	/**
	 * 获得属性
	 * 
	 * @return
	 */
	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			// 读取files目录下的config
			// fis = activity.openFileInput(APP_CONFIG);
			// 读取app_config目录下的config
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator + APP_CONFIG);

			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	/**
	 * 设置文件配置参数
	 * 
	 * @param p
	 */
	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {
			// 把config建在files目录下
			// fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

			// 把config建在(自定义)app_config的目录下
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);

			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 设置属性
	 * 
	 * @param ps
	 */
	public void set(Properties ps) {
		Properties props = get();
		props.putAll(ps);
		setProps(props);
	}

	/**
	 * 设置属性关键字和属性值
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}

	/**
	 * 根据关键字删除属性
	 * 
	 * @param key
	 */
	public void remove(String... key) {
		Properties props = get();
		for (String k : key)
			props.remove(k);
		setProps(props);
	}

	public static boolean isSDExist() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		return sdCardExist;
	}

}
