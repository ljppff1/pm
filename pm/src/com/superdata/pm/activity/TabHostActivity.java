package com.superdata.pm.activity;

import com.suda.pm.ui.R;
import com.superdata.pm.common.SDAppManager;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * 选项卡类
 * @author kw
 *
 */
public abstract class TabHostActivity extends TabActivity {
	
	private TabHost mTabHost;
	private TabWidget mTabWidget; //底部菜单
	private LayoutInflater mLayoutInflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setTheme(R.style.Theme_Tabhost);// 设置样式
		
		// 设置全屏模式
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// 去除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.api_tap_host);
		
		
		mLayoutInflater = getLayoutInflater();
		
		mTabHost = getTabHost();
		mTabWidget = getTabWidget();
		
		//设置tab页切换监听
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				TextView titleName = (TextView) findViewById(R.id.example_center);
				titleName.setText(tabId);
			}
		});
		
		prepare();
		initTop();
		initTabSpec();
		SDAppManager.getAppManager().addActivity(this); // 添加Activity到堆栈
		
	}
	
	
	//初始化标题栏
		private void initTop() {
			View child = getTop();
			LinearLayout layout = (LinearLayout) findViewById(R.id.tab_top);
			layout.addView(child);
			
		}

	//初始化tab
	private void initTabSpec() {
		
		int count = getTabItemCount();
		
		for(int i=0; i<count;i++){
			View tabItem = mLayoutInflater.inflate(R.layout.api_tab_item, null);
			TextView tvTabItem = (TextView) tabItem.findViewById(R.id.tab_item_tv);
			setTabItemTextView(tvTabItem, i);
			//set id
			String tabItemId = getTabItemId(i);
			//set tabSpec
			TabSpec tabSpec = mTabHost.newTabSpec(tabItemId);
			tabSpec.setIndicator(tabItem);
			tabSpec.setContent(getTabItemIntent(i));
			
			mTabHost.addTab(tabSpec);
		}
		
	}


	//自定义头部布局
	protected View getTop() {
		// do nothing or you override it
		return null;
	}

	//在初始化界面之前调用
	protected void prepare() {
		// do nothing or you override it
		
	}
	
	protected int getTabCount(){
		return mTabHost.getTabWidget().getTabCount();
	}
	
	
	//设置TabItem的图标和标题等
	abstract protected void setTabItemTextView(TextView textView, int position);
	
	abstract protected String getTabItemId(int position);
	
	abstract protected Intent getTabItemIntent(int position);
	
	abstract protected int getTabItemCount();
	
	protected void setCurrentTab(int index){
		mTabHost.setCurrentTab(index);
	}
	
	protected void focusCurrentTab(int index){
		mTabWidget.focusCurrentTab(index);
	}
}
