package com.superdata.pm.activity.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.MainActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.NetCheckUtil;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.CustomDialog;
import com.superdata.pm.view.SDProgressDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * 登录页面
 * 
 * @ClassName LoginActivity
 * @author Administrator
 * @date 2014年6月27日 上午9:46:30
 * 
 */
public class LoginActivity extends Activity {
	private EditText et_name, et_pwd;// 用户名 和密码editText
	private Button bt_login, bt_reset;// 登录和重置按钮
	SDProgressDialog sdDialog;
	loginThread loginThread;
	Message message;
	TelephonyManager telephonyManager;
	private String password,username;
	private final String SuccessResultCode="200";//登录成功返回200

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
	}

	public void init() {
		et_name = (EditText) findViewById(R.id.et_name);// 初始化控件
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(LoginActivity.this);
		String LOGIN_USER_NAME=mapconfig.get(InterfaceConfig.LOGIN_USER_NAME);
		if(LOGIN_USER_NAME!="null"||!LOGIN_USER_NAME.equals("")){
			et_name.setText(mapconfig.get(InterfaceConfig.LOGIN_USER_NAME));
			et_pwd.setText(mapconfig.get(InterfaceConfig.PASSWORD));
		}
		bt_login = (Button) findViewById(R.id.bt_login);
		bt_reset = (Button) findViewById(R.id.bt_reset);
		sdDialog = new SDProgressDialog(LoginActivity.this);
		
		loginThread=new loginThread();
		
		bt_login.setOnClickListener(new OnClickListener() {// 添加bt点击监听事件

			@Override
			public void onClick(View v) {
				message = new Message();
				if (!NetCheckUtil.isNetworkAvailable(LoginActivity.this)) {//监测网络是否连接
					message.what = R.string.INTERNETCLOSE;
					handler.sendMessage(message);
					return;
				}
				if (isnull(et_name.getText().toString().trim(), et_pwd.getText().toString().trim())) {
					sdDialog.show();
					new Thread(loginThread).start();
				}else if(!isnull(et_name.getText().toString().trim(), et_pwd.getText().toString().trim())){
					message.what = 405;
					handler.sendMessage(message);
				}
				
			}
		});
		bt_reset.setOnClickListener(new OnClickListener() {// 添加重置按钮监听事件

			@Override
			public void onClick(View v) {// 重置文本框
				et_name.setText("");
				et_pwd.setText("");
			}
		});
	}

	/**
	 * 登录线程
	 * @ClassName loginThread
	 * @author Administrator
	 * @date 2014年7月29日 上午9:20:40
	 *
	 */
	class loginThread implements Runnable {
		/*
		 * (非 Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			
			/*SharedPreferencesConfig.saveConfig(LoginActivity.this,
					InterfaceConfig.LAST_USER_NAME, "123");// 轻量级数据存储
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sdDialog.cancel();
			Intent intent = new Intent();// 跳转到主界面
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
			
			String url = InterfaceConfig.LOGIN_URL;
			SDHttpClient sdClient = new SDHttpClient();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			password=et_pwd.getText().toString().trim();
			username=et_name.getText().toString().trim();
			params.add(new BasicNameValuePair("userCode", username));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("mobilePhone", getMobileInfo()));
			try {
				String json = sdClient.post_session(url, params);
				sdDialog.cancel();
				Log.d("json++", json);
				String resuletCode = analysisJson(json);
				if (SuccessResultCode.equals(resuletCode)) {
					/*if(checkMobile(json)){
						message.what = 10011;
						return;
					}*/
					message.what = InterfaceConfig.handlerSuccess;
					message.obj = json;
				} else {
					message.what = R.string.USERNAMEORPASSWORDERROR;
				}
				handler.sendMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
				message.what = 404;
				handler.sendMessage(message);
			}
		}
	}

	public String analysisJson(String json) {
		String resuletCode = null;
		try {
			JSONObject jRoot = new JSONObject(json);
			resuletCode = jRoot.getString("resultCode");
			Log.d("---resuletCode---", resuletCode);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return resuletCode;
	}

	public String getMobileInfo(){
		telephonyManager = (TelephonyManager) LoginActivity.this.getSystemService(Context.TELEPHONY_SERVICE);  
		String NativePhoneNumber=null;  
	    NativePhoneNumber=telephonyManager.getLine1Number();  

		return NativePhoneNumber;
	}
	
	public Boolean checkMobile(String json){
		JSONObject jRoot;
		try {
			jRoot = new JSONObject(json);
			JSONObject jUser = jRoot.getJSONObject("user");
			String mobilePhone=jUser.getString("mobilePhone");
			String num=getMobileInfo();
			if(num.equals(mobilePhone)){
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void saveSharedPreferences(String json) {
		try {
			JSONObject jRoot = new JSONObject(json);
			JSONObject jUser = jRoot.getJSONObject("user");
			String name = jUser.getString("name");
			String id = jUser.getString("id");
			String companyId = jUser.getString("companyId");
			String positionName=jUser.getString("positionName");
			//Log.d(id + "----", id);
			SharedPreferencesConfig.saveConfig(LoginActivity.this,
					InterfaceConfig.LAST_USER_NAME, name);
			SharedPreferencesConfig.saveConfig(LoginActivity.this,
					InterfaceConfig.LOGIN_USER_NAME,username);
			SharedPreferencesConfig.saveConfig(LoginActivity.this,
					InterfaceConfig.PASSWORD, password);
			SharedPreferencesConfig.saveConfig(LoginActivity.this,
					InterfaceConfig.ID, id);
			SharedPreferencesConfig.saveConfig(LoginActivity.this,
					InterfaceConfig.COMPANYID, companyId);
			SharedPreferencesConfig.saveConfig(LoginActivity.this,
					InterfaceConfig.POSITIONNAME, positionName);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {// 刷新界面handler

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case R.string.USERNAMEORPASSWORDERROR:
				Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
				break;
			case 10011:
				Toast.makeText(LoginActivity.this, "与绑定手机号不一致，请登录平台修改", Toast.LENGTH_LONG).show();
				break;
			case 10086:
				saveSharedPreferences(msg.obj.toString());
				Intent intent = new Intent();// 跳转到主界面
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				break;
				
			case 404:
				Toast.makeText(LoginActivity.this, "网络异常!",Toast.LENGTH_LONG).show();
				break;
			case 405:
				CustomDialog.myToastShow(LoginActivity.this, "温馨提示", "用户名或密码不能为空!",
						0);
				break;
			case R.string.INTERNETCLOSE:
				Toast.makeText(LoginActivity.this, "网络有点不给力,请稍后重试!",Toast.LENGTH_LONG).show();
			}
			if (sdDialog.isShow()) {
				sdDialog.cancel();
			}
		}
	};

	public boolean isnull(String name, String password) {
		if (name.equals("") || password.equals("")) {
			return false;
		} else {
			return true;
		}
	}

}
