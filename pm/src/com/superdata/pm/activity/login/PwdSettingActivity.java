package com.superdata.pm.activity.login;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.SettingsActivity;
import com.superdata.pm.util.CheckGetUtils;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.view.CheckView;
import com.superdata.pm.view.ConmentConfig;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 密码修改的类
 * @author kw
 *
 */
public class PwdSettingActivity extends Activity implements Button.OnClickListener {
	
	private TextView title;
	private LinearLayout layout;
	private CheckView mCheckView;
	private EditText mEditTest;
	private Button mChangePicture;
	private Button mSubmit;
	
	//验证码
	int[] checkNum = {0,0,0,0};
	
	private EditText mOldPwd;
	private EditText mNewPwd;
	private EditText mConfimPwd;
	
	protected static final int UPDATE_CHECKNUM =0x101; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pwd_setting);
		
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("密码修改");
		
		layout = (LinearLayout) findViewById(R.id.title_back);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IntentUtils.gotoActivity(PwdSettingActivity.this, SettingsActivity.class);
				PwdSettingActivity.this.finish();
			}
		});
		
		initView();
		
	}
	
	public void initView(){
		mCheckView = (CheckView) findViewById(R.id.checkview);
		mEditTest = (EditText) findViewById(R.id.et_pwd_setting_checktest);
		mChangePicture = (Button) findViewById(R.id.btn_pwd_setting_change);
		mSubmit = (Button) findViewById(R.id.btn_pwd_setting_submit);
		
		mOldPwd = (EditText) findViewById(R.id.et_pwd_setting_oldpwd);
		mNewPwd = (EditText) findViewById(R.id.et_pwd_setting_newpwd);
		mConfimPwd = (EditText) findViewById(R.id.et_pwd_setting_confimpwd);
		
		mChangePicture.setOnClickListener(this);
		mSubmit.setOnClickListener(this);
	}
	
	//初始化验证码并刷新界面
	public void initCheckNum(){
		checkNum = CheckGetUtils.getCheckNum();
		mCheckView.setCheckNum(checkNum);
		mCheckView.updateCheckNum();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pwd_setting_change:
			initCheckNum();
			break;
			
		case R.id.btn_pwd_setting_submit:
			String userInput = mEditTest.getText().toString();
			String oldPwd = mOldPwd.getText().toString();
			String newPwd = mNewPwd.getText().toString();
			String confimPwd = mConfimPwd.getText().toString();
			if(CheckGetUtils.checkNum(userInput, checkNum)
					&& oldPwd.equals("")
					&& newPwd.equals("")
					&& newPwd.equals(confimPwd)){
				Toast.makeText(this, "提交成功", 1).show();
			}else{
				Toast.makeText(this, "提交不成功", 1).show();
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onResume() {
		new Thread().start();
		super.onResume();
	}
	
	
	class myThread implements Runnable{

		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				//创建消息
				Message message = new Message();
				message.what = PwdSettingActivity.UPDATE_CHECKNUM;
				
				PwdSettingActivity.this.handler.sendMessage(message);
				
				try {
					//线程休眠
					Thread.sleep(ConmentConfig.UPDATE_TIME);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			
		}
		
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PwdSettingActivity.UPDATE_CHECKNUM:
				mCheckView.updateCheckNum();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		};
	};

}
