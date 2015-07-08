package com.superdata.pm.activity;

import java.util.ArrayList;
import java.util.List;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.cost.CostsActivity;
import com.superdata.pm.activity.document.DocumentsActivity;
import com.superdata.pm.activity.homepage.HomepageActivity;
import com.superdata.pm.activity.project.ProjectsActivity;
import com.superdata.pm.common.SDAppManager;
import com.superdata.pm.entity.TabItem;
import com.superdata.pm.util.IntentUtils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.TabActivity;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主界面类
 * @author kw
 *
 */
public class MainActivity extends TabHostActivity{
	List<TabItem> mItems;
	private LayoutInflater mLayoutInflater;
//	private TabWidget tabWidget;
	private long exitTime = 0;
	
	/**在初始化TabWidget前调用
	 * 和TabWidget有关的必须在这里初始化*/
	@Override
	protected void prepare() {
		TabItem homepage = new TabItem(
				"首页", 	//标题
				R.drawable.ic_home, //图标
				R.drawable.example_tab_item_bg,  //背景
				new Intent(this,HomepageActivity.class)); //跳转
		
		TabItem projects = new TabItem(
				"项目", 
				R.drawable.ic_xiangmu, 
				R.drawable.example_tab_item_bg, 
				new Intent(this,ProjectsActivity.class));
		
		TabItem costs = new TabItem(
				"成本", 
				R.drawable.ic_chengben, 
				R.drawable.example_tab_item_bg, 
				new Intent(this,CostsActivity.class));
		
		TabItem documents = new TabItem(
				"文档", 
				R.drawable.ic_wendang, 
				R.drawable.example_tab_item_bg1, 
				new Intent(this,DocumentsActivity.class));
		
		mItems = new ArrayList<TabItem>();
		mItems.add(homepage);
		mItems.add(projects);
		mItems.add(costs);
		mItems.add(documents);
//		mItems.add(more);
		
		
		//设置分割线
		TabWidget tabWidget = getTabWidget();
		tabWidget.setDividerDrawable(R.drawable.tab_divider);
		
		mLayoutInflater = getLayoutInflater();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置默认选中
		setCurrentTab(0);
		/*//设置窗口的宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int count = mItems.size();
		if(count < 4){
			for(int i=0; i<count; i++){
				//设置每个选项卡的宽度
				tabWidget.getChildTabViewAt(i).setMinimumWidth(screenWidth / 4);
			}
		}*/
	}
	
	
	/**tab的title，icon，边距设定等等*/
	@Override
	protected void setTabItemTextView(TextView textView, int position) {
		textView.setPadding(3, 3, 3, 3);
		textView.setText(mItems.get(position).getTitle());
		textView.setBackgroundResource(mItems.get(position).getBg());
		textView.setCompoundDrawablesWithIntrinsicBounds(0,mItems.get(position).getIcon(), 0, 0);
	}

	/**tab唯一的id*/
	@Override
	protected String getTabItemId(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position).getTitle();//使用title作为id
	}

	/**点击tab时触发的事件*/
	@Override
	protected Intent getTabItemIntent(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position).getIntent();
	}

	@Override
	protected int getTabItemCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}
	
	/**自定义头部文件*/
	@Override
	protected View getTop() {
		// TODO Auto-generated method stub
		return mLayoutInflater.inflate(R.layout.example_top, null);
	}
	
	
	public boolean isServiceRun(Context context){
	     ActivityManager am = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
	     List<RunningServiceInfo> list = am.getRunningServices(30);
	     for(RunningServiceInfo info : list){
	         if(info.service.getClassName().trim().equals("com.superdata.soho.service.UploadPostionService")){
	                  return true;
	         }
	    }
	    return false;
	}
	
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		Boolean flag = isServiceRun(MainActivity.this);
		if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if((System.currentTimeMillis() - exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", 1).show();
				exitTime = System.currentTimeMillis();
				}else{
					SDAppManager.getAppManager().AppExit(getApplicationContext());
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			return true;
			}
		return super.dispatchKeyEvent(event);
	}
	
	
}
