package com.superdata.pm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.superdata.pm.entity.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


/**
 * 应用程序搜索工具
 * @ClassName QueryAppUtil
 * @author Administrator
 * @date 2014年7月25日 上午9:57:06
 *
 */
public class QueryAppUtil {

	public static final String USEDAPP = "useapp.txt";

	public static final int FILTER_ALL_APP = 0; // 所有应用程序
	public static final int FILTER_SYSTEM_APP = 1; // 系统程序
	public static final int FILTER_THIRD_APP = 2; // 第三方应用程序
	public static final int FILTER_SDCARD_APP = 3; // 安装在SDCard的应用程序
	public static final int FILTER_USED_APP = 4; // 常用工具

	private PackageManager pm;
	private Context context;

	public QueryAppUtil(Context context) {
		this.context = context;
		pm = context.getPackageManager();
	}

	// 根据查询条件，查询特定的ApplicationInfo
	public List<AppInfo> queryFilterAppInfo(int filter) {
		// 查询所有已经安装的应用程序
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));// 排序
		List<AppInfo> appInfos = new ArrayList<AppInfo>(); // 保存过滤查到的AppInfo
		// 根据条件来过滤
		switch (filter) {
		case FILTER_ALL_APP: // 所有应用程序
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				appInfos.add(getAppInfo(app));
			}
			removeSelf(appInfos);
			return appInfos;
		case FILTER_SYSTEM_APP: // 系统程序
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					appInfos.add(getAppInfo(app));
				}
			}
			removeSelf(appInfos);
			return appInfos;
		case FILTER_THIRD_APP: // 第三方应用程序
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
					appInfos.add(getAppInfo(app));
				}
			}
			removeSelf(appInfos);
			return appInfos;
		case FILTER_SDCARD_APP: // 安装在SDCard的应用程序
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
					appInfos.add(getAppInfo(app));
				}
			}
			removeSelf(appInfos);
			return appInfos;
		default:
			return null;
		}
	}

	private AppInfo getAppInfo(ApplicationInfo app) {
		AppInfo appInfo = new AppInfo();
		appInfo.setAppLabel((String) app.loadLabel(pm));
		appInfo.setAppIcon(app.loadIcon(pm));
		appInfo.setPkgName(app.packageName);
		appInfo.setIntent(pm.getLaunchIntentForPackage(app.packageName));
		return appInfo;
	}

	/**
	 * 去除本应用信息
	 * @param list
	 * @return
	 */
	private List<AppInfo> removeSelf(List<AppInfo> list) {
		PackageInfo info = null;
		String name = "";
		try {
			info = pm.getPackageInfo(context.getPackageName(), 0);
			name = info.packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Iterator<AppInfo> it = list.iterator();
		while (it.hasNext()) {
			if (name.equals(it.next().getPkgName())) {
				it.remove();
			}
		}
		return list;
	}

	/**
	 * 去除重复应用信息
	 * @param list
	 * @param names
	 * @return
	 */
	public List<AppInfo> queryAppInfoByPkname(List<AppInfo> list, String[] names) {
		List<AppInfo> apps = new ArrayList<AppInfo>();
		for (AppInfo appInfo : list) {
			for (String name : names) {
				if (appInfo.getPkgName().equals(name)) {
					apps.add(appInfo);
				}
			}
		}
		return removeDuplicate(apps);
	}

	// 删除ArrayList中重复元素
	private static List<AppInfo> removeDuplicate(List<AppInfo> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).equals(list.get(i))) {
					list.remove(j);
				}
			}
		}
		return list;
	}

	
}

