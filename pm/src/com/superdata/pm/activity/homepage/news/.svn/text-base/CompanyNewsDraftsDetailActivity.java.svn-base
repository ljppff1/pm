package com.superdata.pm.activity.homepage.news;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.homepage.news.CompanyNewMessageActivity.SendThread;
import com.superdata.pm.activity.homepage.news.CompanyNewsOutBoxDetailActivity.DeleteTask;
import com.superdata.pm.activity.homepage.news.CompanyNewsOutBoxDetailActivity.MyTask;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.NetCheckUtil;
import com.superdata.pm.view.SDProgressDialog;

public class CompanyNewsDraftsDetailActivity extends BaseActivity implements OnClickListener{

	private ImageView btnBack;
	private Button btnSend;
	private Button btnDelete;
	
	//标题
	private TextView top_title;
	//收件人
	private TextView tv_drafts_detail_receiver;
	//发送内容
	private EditText et_drafts_detail_content;
	//发送时间
//	private TextView tv_drafts_detail_sendtime;
	private TextView tv_drafts_title;
	
	//消息id
	private int id;
	//用户id
	private String empId;
	//删除返回结果
	private String resultCode;
	//标题
	private String name;
	//内容
	private String content;
	//接收人id
	private String receiveId;
	//发送状态
	private Integer status = 1;
	
	
	
	String url = InterfaceConfig.NEWSOUTBOX_DETAIL_URL;
	String deleteUrl = InterfaceConfig.NEWSOUTBOX_LIST_DELETE_URL;
	String sendUrl = InterfaceConfig.SENDMESSAGE_URL;
	SDHttpClient sdClient;
	SDProgressDialog sdDialog;
	Message msg;
	List<NameValuePair> params;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companynews_draftsdetail);
		
		//初始化视图
		initView();
		//初始化数据
		initData();
	}
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 1:	//删除后返回
				Intent intent = new Intent();
				intent.setClass(CompanyNewsDraftsDetailActivity.this,
						CompanyNewsDraftsListActivity.class);
				startActivity(intent);
				finish();
				break;
			
			case 2:
				Toast.makeText(CompanyNewsDraftsDetailActivity.this,
						"内容不能为空！", Toast.LENGTH_SHORT).show();
				break;
				
			case 3:
				Toast.makeText(CompanyNewsDraftsDetailActivity.this,
						"网络异常！", Toast.LENGTH_SHORT).show();
				break;
			
			case 4:	//发送消息确认
				sendMsg(msg.obj.toString());
				
				onBackPressed();
				finish();
				break;

			default:
				break;
			}
			
			super.handleMessage(msg);
		}
		
	};
	
	
	
	public void initView(){
		id = getIntent().getExtras().getInt("id");
		empId = getIntent().getExtras().getString("empId");
		receiveId = getIntent().getExtras().getString("receiveId");
		
		top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_drafts_title = (TextView) findViewById(R.id.tv_drafts_title);
		
//		tv_drafts_detail_receiver = (TextView) findViewById(R.id.tv_drafts_detail_receiver);
		et_drafts_detail_content = (EditText) findViewById(R.id.et_drafts_detail_content);
//		tv_outbox_detail_sendtime = (TextView) findViewById(R.id.tv_outbox_detail_sendtime);
		
		et_drafts_detail_content.setMovementMethod(new ScrollingMovementMethod());
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSend = (Button) findViewById(R.id.btn_drafts_detail_send);
		btnDelete = (Button) findViewById(R.id.btn_drafts_detail_delete);
		
		btnBack.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		
		sdDialog = new SDProgressDialog(this);
		
	}
	
	
	public void initData(){
		initData(id+"");
	}
	
	
	public void initData(String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		
		new MyTask().execute(params);
	}
	
	
	
	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String>{

		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			sdClient = new SDHttpClient();
			String json = null;
			try {
				json = sdClient.post_session(url, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return json;
		}
		
		
		
		@Override
		protected void onPostExecute(String result) {
			if(null != result){
				try {
					JSONObject jRoot = new JSONObject(result);
					
					//消息标题
					String msgName = (String) (jRoot.get("name").equals(null)?
							"":jRoot.get("name"));
					//接收人姓名
					String receiveName = (String) jRoot.get("receiveName");
					//消息内容
					String content = (String) jRoot.get("content");
					//创建时间
//					String createDate = (String) jRoot.get("createDate");
					
					
					top_title.setText("To: "+receiveName);
//					tv_drafts_detail_receiver.setText(receiveName);
					et_drafts_detail_content.setText(content);
//					tv_outbox_detail_sendtime.setText(createDate);
					tv_drafts_title.setText(msgName);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			super.onPostExecute(result);
		}
		
	}
	
	
	
	public void initDataDelete(){
		initDataDelete(empId, id+"");
	}
	
	
	public void initDataDelete(String empId, String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("id", id));
		
		new DeleteTask().execute(params);
	}
	
	
	class DeleteTask extends AsyncTask<List<NameValuePair>, Integer, String>{

		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			sdClient = new SDHttpClient();
			String json = null;
			try {
				json = sdClient.post_session(deleteUrl, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return json;
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			if(null != result){
				try {
					JSONObject jRoot = new JSONObject(result);
					resultCode = jRoot.getString("resultCode");
					if("200".equals(resultCode)){
						Toast.makeText(CompanyNewsDraftsDetailActivity.this,
								"删除成功！", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(CompanyNewsDraftsDetailActivity.this,
								"删除失败！", Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			handler.sendEmptyMessage(1);
			super.onPostExecute(result);
		}
		
		
	}
	
	
	
	public List<NameValuePair> getParamList(){
		params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("receiveId", receiveId));
		params.add(new BasicNameValuePair("status", status+""));
		
		return params;
	}
	
	
	
	public void sendMsg(String json){
		JSONObject jRoot;
		try {
			jRoot = new JSONObject(json);
			String resultCode = jRoot.getString("resultCode");
			if("200".equals(resultCode)){
				Toast.makeText(CompanyNewsDraftsDetailActivity.this,
						"消息发送成功！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(CompanyNewsDraftsDetailActivity.this,
						"消息发送失败！", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void askYesOrNo(){
		new AlertDialog.Builder(CompanyNewsDraftsDetailActivity.this)
		.setTitle("删除消息")
		.setMessage("确定删除消息？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				initDataDelete();
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			handler.sendEmptyMessage(1);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onClick(View v) {
		// 点击返回时
		if(v == btnBack){
			handler.sendEmptyMessage(1);
		}
				
		// 点击发送时
		if(v == btnSend){
			name = top_title.getText().toString();
			content = et_drafts_detail_content.getText().toString();
			
			if(chechIsNull(et_drafts_detail_content)){
				msg = new Message();
				handler.sendEmptyMessage(2);
				return;
			}else{
//				sdDialog.show();
				
				SendThread sendThread = new SendThread();
				new Thread(sendThread).start();
			}
			
		}
				
		// 点击删除时
		if(v == btnDelete){
			askYesOrNo();
					
		}
		
	}
	
	
	
	
	class SendThread implements Runnable{

		@Override
		public void run() {
			if(!NetCheckUtil.isNetworkAvailable(CompanyNewsDraftsDetailActivity.this)){
				Toast.makeText(CompanyNewsDraftsDetailActivity.this,
						"网络不给力，请稍后重试！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			sdClient = new SDHttpClient();
			getParamList();
			msg = new Message();
			try {
				String json = sdClient.post_session(sendUrl, params);
				
				msg.what = 4;
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
