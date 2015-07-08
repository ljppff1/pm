package com.superdata.pm.activity.homepage;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.adapter.AppAdapter;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.entity.AppInfo;
import com.superdata.pm.util.QueryAppUtil;
import com.superdata.pm.util.SaveAppUtil;
import com.superdata.pm.view.SDProgressDialog;

public class MyPhoneAppActivity extends BaseActivity implements OnClickListener {
	
	public static final int SEARCH_APP = 1;//搜索类型
	GridView gridView;
	SDProgressDialog dialog;
	SaveAppUtil appSave;
	QueryAppUtil appQuery;
	private List<AppInfo> apps;
	private List<AppInfo> temp;
	AppAdapter adapter;
	private static final String SCHEME = "package";
	private ImageView btnBack;
	private TextView tv_top_title;
	
	/*
	 * (非 Javadoc)
	 * 
	 * @see com.superdata.soho.common.BaseActivity#onCreate(android.os.Bundle)
	 */
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_app);
		init();
	}
	 
	 public void init() {
		 	btnBack = (ImageView) findViewById(R.id.ll_top_title);
		 	btnBack.setOnClickListener(this);
		 	tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		 	tv_top_title.setText("我的手机");
			gridView = (GridView) findViewById(R.id.gridView);
			dialog = new SDProgressDialog(MyPhoneAppActivity.this);
			dialog.show();
			appSave = new SaveAppUtil(MyPhoneAppActivity.this);
			appQuery = new QueryAppUtil(MyPhoneAppActivity.this);
			new DataTask(0).start();
		}
	 
	 private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case SEARCH_APP:
					adapter = new AppAdapter(MyPhoneAppActivity.this, apps);
					gridView.setAdapter(adapter);
					gridView.setOnItemClickListener(new ItemClickListener());
				default:
					break;
				}
				if (dialog.isShow()) {
					dialog.cancel();
				}
			}

		};
		
		
		/**
		 * 点击事件
		 * @ClassName ItemClickListener
		 * @author Administrator
		 * @date 2014年7月26日 上午9:06:15
		 *
		 */
		private class ItemClickListener implements OnItemClickListener {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				AppInfo app = apps.get(position);
				try {
					appSave.saveData(QueryAppUtil.USEDAPP, app.getPkgName() + ";");
				} catch (Exception e) {
					e.printStackTrace();
				}

				Intent intent = app.getIntent();

				if (intent != null) {
					startActivity(intent);
				} else {
					openSystemApp(app);
				}
			}
		}
	
		
		/**
		 * 打开系统应用
		 * 
		 * @param app
		 */
		private void openSystemApp(AppInfo app) {
			Intent intent = new Intent();
			int apiLevel = Build.VERSION.SDK_INT;
			if (apiLevel >= 9) {
				intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				Uri uri = Uri.fromParts(SCHEME, app.getPkgName(), null);
				intent.setData(uri);
				startActivity(intent);
			}
		}
		
		
		
		/**
		 * 加载数据线程
		 */
		private class DataTask extends Thread {

			private int category;

			public DataTask(int category) {
				this.category = category;
			}

			@Override
			public void run() {
				switch (category) {
				case QueryAppUtil.FILTER_ALL_APP://所有应用程序
					apps = appQuery.queryFilterAppInfo(QueryAppUtil.FILTER_ALL_APP);
					break;
				case QueryAppUtil.FILTER_USED_APP:// 常用工具
					try {
						String[] names = appSave.readApps(QueryAppUtil.USEDAPP);
						temp = appQuery
								.queryFilterAppInfo(QueryAppUtil.FILTER_ALL_APP);
						apps = appQuery.queryAppInfoByPkname(temp, names);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
				handler.sendEmptyMessage(SEARCH_APP);
			}
		}
		
		
		/* (非 Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
	@Override
	public void onClick(View v) {
		
		if(v == btnBack){
			onBackPressed();
		}
		
	}

}
