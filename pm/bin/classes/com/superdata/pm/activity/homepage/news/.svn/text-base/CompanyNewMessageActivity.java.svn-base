package com.superdata.pm.activity.homepage.news;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.login.LoginActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.NetCheckUtil;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;

/**
 * 首页-->站内消息-->发送新消息
 * @author kw 
 *
 */
public class CompanyNewMessageActivity extends BaseActivity implements OnClickListener {

	private ImageView btnBack;
	private Button btnChoose;
	private Button btnSend;
	private Button btnSave;
	
	private TextView top_title;
	private TextView tv_newmessage_receiver;
	
	private EditText et_newmessage_title;
	private EditText et_newmessage_content;
	
	//标题
	private String name;
	//内容
	private String content;
	//用户id
	private String empId;
	//接收人id
	private String receiveId;
	//发送状态
	private Integer status = 1;
	//接收人
	private String receiveName;
	
	
	Intent intent;
	Message msg;
	List<NameValuePair> params;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	String sendUrl = InterfaceConfig.SENDMESSAGE_URL;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compaynews_newmessage);
		
		
		//初始化视图
		initView();
		//初始化控件
		initData();
		
		
	}
	
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				sendMsg(msg.obj.toString());
				
				onBackPressed();
				finish();
				break;
				
			case 2:
				Toast.makeText(CompanyNewMessageActivity.this,
						"标题或内容不能为空！", Toast.LENGTH_SHORT).show();
				break;
				
			case 3:
				Toast.makeText(CompanyNewMessageActivity.this,
						"网络异常！", Toast.LENGTH_SHORT).show();
				break;
				
			case 4:
				saveMsg(msg.obj.toString());
				
				onBackPressed();
				finish();
				break;

			default:
				break;
			}
			if (sdDialog.isShow()) {
				sdDialog.cancel();
			}
			
			
			super.handleMessage(msg);
		}
		
	};
	
	
	
	public void initData(){
		top_title.setText("新消息");
//		tv_newmessage_receiver.setText(receiveName);
		et_newmessage_title.setText(name);
		et_newmessage_content.setText(content);
	}
	
	
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(CompanyNewMessageActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);
		
		/*Bundle bundle1 = getIntent().getExtras();
		if(bundle1 == null){
			receiveName = "";
			receiveId = "";
		}else{
			receiveName = getIntent().getExtras().getString("name");
			receiveId = getIntent().getExtras().getString("id");
		}*/
		
		Bundle bundle2 = getIntent().getExtras();
		if(bundle2 == null){
			name = "";
			content = "";
		}else{
			name = getIntent().getExtras().getString("msgName");
			content = getIntent().getExtras().getString("content");
		}
		
		
		tv_newmessage_receiver = (TextView) findViewById(R.id.tv_newmessage_receiver);
		tv_newmessage_receiver.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		et_newmessage_title = (EditText) findViewById(R.id.et_newmessage_title);
		et_newmessage_content = (EditText) findViewById(R.id.et_newmessage_content);
		
		et_newmessage_title.setMovementMethod(new ScrollingMovementMethod());
		et_newmessage_content.setMovementMethod(new ScrollingMovementMethod());
//		name = et_newmessage_title.getText().toString();
//		content = et_newmessage_content.getText().toString();
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnChoose = (Button) findViewById(R.id.btn_newmessage_receiver);
		btnSend = (Button) findViewById(R.id.btn_newmessage_send);
		btnSave = (Button) findViewById(R.id.btn_newmessage_save);
		
		btnBack.setOnClickListener(this);
		btnChoose.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		
		sdDialog = new SDProgressDialog(this);
		
	}
	
	
	/**
	 * 发送消息得到返回json
	 * @param json
	 */
	public void sendMsg(String json){
		JSONObject jRoot;
		try {
			jRoot = new JSONObject(json);
			String resultCode = jRoot.getString("resultCode");
			if("200".equals(resultCode)){
				Toast.makeText(CompanyNewMessageActivity.this,
						"消息发送成功！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(CompanyNewMessageActivity.this,
						"消息发送失败！", Toast.LENGTH_SHORT).show();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 保存消息得到返回json
	 * @param json
	 */
	public void saveMsg(String json){
		JSONObject jRoot;
		try {
			jRoot = new JSONObject(json);
			String resultCode = jRoot.getString("resultCode");
			if("200".equals(resultCode)){
				Toast.makeText(CompanyNewMessageActivity.this,
						"消息保存成功！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(CompanyNewMessageActivity.this,
						"消息保存失败！", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 返回询问
	 */
	public void askYesOrNo(){
		new AlertDialog.Builder(CompanyNewMessageActivity.this)
		.setTitle("取消").setMessage("取消，内容不保存")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		}).setNegativeButton("取消", null).show();
	}
	
	
	
	//判断输入框是否为空
	public boolean chechIsNull(EditText editText){
		boolean flag=false;
		String str=editText.getText().toString().trim();
		if(str==null||str.equals("")){
			flag=true;
		}
		return flag;
	}
	
	
	
	/**
	 * 请求发送数据
	 * @return
	 */
	public List<NameValuePair> getParamList(){
		params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("receiveId", "0:"+receiveId));
		params.add(new BasicNameValuePair("status", status+""));
		
		return params;
	}
	
	
	
	/**
	 * 请求保存数据（不存联系人）
	 */
	/*public List<NameValuePair> getSaveParamsWithoutReceiver(){
		params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("content", content));
//		params.add(new BasicNameValuePair("receiveId", "0:"+receiveId));
		
		return params;
	}*/
	
	
	/**
	 * 请求保存数据（存联系人）
	 * @return
	 */
	public List<NameValuePair> getSaveParamsWithReceiver(){
		params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("receiveId", "0:"+receiveId));
		
		return params;
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 200){
			switch (requestCode) {
			case 1:
//				receiveName = getIntent().getExtras().getString("name");
//				receiveId = getIntent().getExtras().getString("id");
				receiveName = data.getStringExtra("name");
				receiveId = data.getStringExtra("id");
				
				tv_newmessage_receiver.setText(receiveName);
				break;

			default:
				break;
			}
		}
		
	}
	
	
	@Override
	public void onClick(View v) {
		// 点击返回时
		if(v == btnBack){
			askYesOrNo();
		}
		
		// 点击选择时
		if(v == btnChoose){
			/*IntentUtils.gotoActivity(CompanyNewMessageActivity.this,
					ChooseReceiverActivity.class);*/
			Intent intent = new Intent(CompanyNewMessageActivity.this, ChooseReceiverActivity.class);
//			intent.setClass(CompanyNewMessageActivity.this, ChooseReceiverActivity.class);
			startActivityForResult(intent, 1);
//			finish();
			
		}
		
		
		// 点击发送时
		if(v == btnSend){
			name = et_newmessage_title.getText().toString();
			content = et_newmessage_content.getText().toString();
			//"" == receiveId || "".equals(receiveName)
			if("".equals(tv_newmessage_receiver.getText())){
				Toast.makeText(CompanyNewMessageActivity.this,
						"请选择收件人！", Toast.LENGTH_SHORT).show();
				
			}else if(chechIsNull(et_newmessage_title) || chechIsNull(et_newmessage_content)){
				msg = new Message();
				handler.sendEmptyMessage(2);
				return;
			}else{
				sdDialog.show();
				
				SendThread sendThread = new SendThread();
				new Thread(sendThread).start();
				
			}
			
		}
		
		
		// 点击保存时
		if(v == btnSave){
			name = et_newmessage_title.getText().toString();
			content = et_newmessage_content.getText().toString();
//			SharedPreferencesConfig.saveConfig(CompanyNewMessageActivity.this,
//					InterfaceConfig.RECEIVE_ID, receiveId);
			if(chechIsNull(et_newmessage_title) || chechIsNull(et_newmessage_content)){
				msg = new Message();
				handler.sendEmptyMessage(2);
				return;
			}else{
				/*sdDialog.show();
				
				SaveThread saveThread = new SaveThread();
				new Thread(saveThread).start();*/
				if("".equals(tv_newmessage_receiver.getText())){
					Toast.makeText(CompanyNewMessageActivity.this,
							"请选择收件人！", Toast.LENGTH_SHORT).show();
				}else{
					sdDialog.show();
					
					SaveThread saveThread = new SaveThread();
					new Thread(saveThread).start();
				}
				
				/*if( "" == receiveId || ("").equals(receiveId) ){
					SaveWithOutReceiverThread thread = new SaveWithOutReceiverThread();
					new Thread(thread).start();
				}else{
					
				}*/
				
			}
		}
		
	}
	
	
	/**
	 * 保存消息线程(不带联系人)
	 * @author Administrator
	 *
	 *//*
	class SaveWithOutReceiverThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			sdClient = new SDHttpClient();
			
			getSaveParamsWithoutReceiver();
			
			msg = new Message();
			try {
				String json = sdClient.post_session(sendUrl, params);
				
				msg.what = 4;
				msg.obj = json;
				handler.sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}*/
	
	
	
	/**
	 * 保存消息线程(带联系人)
	 * @author Administrator
	 *
	 */
	class SaveThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			sdClient = new SDHttpClient();
			
			getSaveParamsWithReceiver();
			
			msg = new Message();
			try {
				String json = sdClient.post_session(sendUrl, params);
				
				msg.what = 4;
				msg.obj = json;
				handler.sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	/**
	 * 发送消息线程
	 * @author Administrator
	 *
	 */
	class SendThread  implements Runnable{

		@Override
		public void run() {
			if(!NetCheckUtil.isNetworkAvailable(CompanyNewMessageActivity.this)){
				Toast.makeText(CompanyNewMessageActivity.this,
						"网络不给力，请稍后重试！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			sdClient = new SDHttpClient();
			getParamList();
			msg = new Message();
			try {
				String json = sdClient.post_session(sendUrl, params);
				
				msg.what = 1;
				msg.obj = json;
				handler.sendMessage(msg);
			} catch (Exception e) {
				msg.what = 3;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	
}
