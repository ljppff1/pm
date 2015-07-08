package com.superdata.pm.entity;

import android.content.Intent;

/**
 * TabItem类
 * @author kw
 *
 */
public class TabItem {
	
	private String title;	//标题
	private int icon;		//图标
	private int bg;			//背景
	private Intent intent;	//意图
	
	public TabItem(String title, int icon, int bg, Intent intent) {
		super();
		this.title = title;
		this.icon = icon;
		this.bg = bg;
		this.intent = intent;
	}

	public TabItem() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getBg() {
		return bg;
	}

	public void setBg(int bg) {
		this.bg = bg;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	

}
