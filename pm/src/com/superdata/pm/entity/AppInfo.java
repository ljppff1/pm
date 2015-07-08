/**
 * @Title AppInfo.java
 * @Package com.superdata.soho.entity
 * @author Administrator
 * @date 2014年7月24日 下午3:44:38
 * @version V1.0
 */
package com.superdata.pm.entity;

import java.io.Serializable;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * 应用实体
 * @ClassName AppInfo
 * @author luolei
 * @date 2014年7月24日 下午3:44:38
 * 
 */
public class AppInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String appLabel; // 应用程序标签
	private Drawable appIcon; // 应用程序图像
	private Intent intent; // 启动应用程序的Intent
							// ，一般是Action为Main和Category为Lancher的Activity
	private String pkgName; // 应用程序所对应的包名

	public AppInfo() {
	}

	public String getAppLabel() {
		return appLabel;
	}

	public void setAppLabel(String appName) {
		this.appLabel = appName;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
}
