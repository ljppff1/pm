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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.homepage.bbs.BBSAddTitleActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.view.SDProgressDialog;

/**
 * 首页-->站内消息-->收件箱-->收件箱消息
 * @author kw
 *
 */
public class CompanyNewsInboxDetailActivity extends BaseActivity implements OnClickListener {
	
	private ImageView btnBack;
	private Button btnReply;
//	private Button btnSave;
	private Button btnDelete;
	
	//标题
	private TextView top_title;
	//发送人
	private TextView tv_inbox_detail_sender;
	//消息内容
	private TextView tv_inbox_detail_content;
	//接收时间
	private TextView tv_inbox_detail_sendtime;
	//标题
	private TextView tv_inbox_detail_title;
	
	//消息id
	private int id;
	//用户id
	private String empId;
	//删除返回结果
	private String resultCode;
	//发送人id
	private int senderId;
	//发送人姓名
	private String senderName;
	//标题
	private String msgName;
	
	String url = InterfaceConfig.NEWSINBOX_DETAIL_URL;
	String deleteUrl = InterfaceConfig.NEWSINBOX_LIST_DELETE_URL;
	SDHttpClient sdClient;
	SDProgressDialog sdDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companynews_inbox_detail);
		
		initView();
		initData();
		
	}
	
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 1:
				Intent intent = new Intent();
				intent.setClass(CompanyNewsInboxDetailActivity.this,
						CompanyNewsInboxListActivity.class);
				startActivity(intent);
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
	
	
	
	//初始化方法	
	public void initView(){
		id = getIntent().getExtras().getInt("id");
		empId = getIntent().getExtras().getString("empId");
		
		top_title = (TextView) findViewById(R.id.tv_top_title);
//		tv_inbox_detail_sender = (TextView) findViewById(R.id.tv_inbox_detail_sender);
		tv_inbox_detail_content = (TextView) findViewById(R.id.tv_inbox_detail_content);
		tv_inbox_detail_sendtime = (TextView) findViewById(R.id.tv_inbox_detail_sendtime);
		tv_inbox_detail_title = (TextView) findViewById(R.id.tv_inbox_detail_title);
		
		tv_inbox_detail_content.setMovementMethod(new ScrollingMovementMethod());
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnReply = (Button) findViewById(R.id.btn_inbox_detail_reply);
//		btnSave = (Button) findViewById(R.id.btn_inbox_detail_save);
		btnDelete = (Button) findViewById(R.id.btn_inbox_detail_delete);
		
		btnBack.setOnClickListener(this);
		btnReply.setOnClickListener(this);
//		btnSave.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		
		sdDialog = new SDProgressDialog(this);
		
	}
	
	public void initData(){
		initData(empId,id+"");
		
	}
	
	
	public void initData(String empId, String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("id", id));
		
		new MyTask().execute(params);
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
			} catch (HttpHostConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return json;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result != null){
				try {
					JSONObject jRoot = new JSONObject(result);
					resultCode = jRoot.getString("resultCode");
					if("200".equals(resultCode)){
						Toast.makeText(CompanyNewsInboxDetailActivity.this,
								"删除成功！", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(CompanyNewsInboxDetailActivity.this,
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
	
	
	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String>{

		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			
			sdClient = new SDHttpClient();
			String json = null;
			try {
				json = sdClient.post_session(url, params[0]);
			} catch (HttpHostConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServerException e) {
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
					
					//标题
					msgName = (String) (jRoot.get("msgName").equals(null)?
							"":jRoot.get("msgName"));
					//发送人
					senderName = (String) jRoot.get("senderName");
					//消息内容
					String msgContent = (String) jRoot.get("msgContent");
					//接收时间
					String receiveDate = (String) jRoot.get("receiveDate");
					//发送人id
					senderId = (Integer) jRoot.get("senderId");
					
					
					top_title.setText("From: "+senderName);
//					tv_inbox_detail_sender.setText(senderName);
					tv_inbox_detail_content.setText(msgContent);
					tv_inbox_detail_sendtime.setText(receiveDate);
					tv_inbox_detail_title.setText(msgName);
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			super.onPostExecute(result);
		}
		
		
	}
	
	
	
	//询问是否删除
	public void askYesOrNo(){
		new AlertDialog.Builder(CompanyNewsInboxDetailActivity.this)
		.setTitle("删除消息")
		.setMessage("确定删除消息？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				initDataDelete();
					
			}
		}).setNegativeButton("取消", null).show();
			
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
		
		// 点击回复时
		if(v == btnReply){
			Intent intent = new Intent(CompanyNewsInboxDetailActivity.this,
					CompanyNewsInboxReplyActivity.class);
			intent.putExtra("senderId", senderId);
			intent.putExtra("senderName", senderName);
			intent.putExtra("empId", empId);
			intent.putExtra("msgName", msgName);
			startActivity(intent);
			finish();
		}
		
		/*// 点击保存时
		if(v == btnSave){
			
		}*/
		
		// 点击删除时
		if(v == btnDelete){
			
			askYesOrNo();
			
		}
		
	}

}
