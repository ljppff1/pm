package com.superdata.pm.activity;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.login.PwdSettingActivity;
import com.superdata.pm.util.IntentUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	
	private TextView title;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("设置");
		
		layout = (LinearLayout) findViewById(R.id.title_back);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IntentUtils.gotoActivity(SettingsActivity.this, MainActivity.class);
				SettingsActivity.this.finish();
			}
		});
	}
	
	/**
	 * 修改账号密码的方法
	 * @param v
	 */
	public void changeAccount(View v){
		IntentUtils.gotoActivity(SettingsActivity.this, PwdSettingActivity.class);
		SettingsActivity.this.finish();
	}
	
	/**
	 * 修改个人资料的方法
	 * @param v
	 */
	public void changeUserInfo(View v){
		IntentUtils.gotoActivity(SettingsActivity.this, UserinfoSettingActivity.class);
		SettingsActivity.this.finish();
	}
	
	/**
	 * 分享的方法
	 * @param v
	 */
	public void shareApp(View v){
		Intent share_intent = new Intent();
		share_intent.setAction(Intent.ACTION_SEND);
		share_intent.setType("text/plain");
		share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
		share_intent.putExtra(Intent.EXTRA_TEXT, "向您推荐应用");
		share_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		share_intent = Intent.createChooser(share_intent, "分享");
		startActivity(share_intent);
	}
	
	/**
	 * 意见反馈的方法
	 * @param v
	 */
	public void suggestion(View v){
		
		
	}

}
