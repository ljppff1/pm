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
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.view.SDProgressDialog;


/**
 * 发件箱消息详细
 * @author kw
 *
 */
public class CompanyNewsOutBoxDetailActivity extends BaseActivity implements OnClickListener {

	private ImageView btnBack;
	private Button btnResend;
	private Button btnDelete;
	
	//标题
	private TextView top_title;
	//收件人
//	private TextView tv_outbox_detail_receiver;
	//发送内容
	private TextView tv_outbox_detail_content;
	//发送时间
	private TextView tv_outbox_detail_sendtime;
	//主题
	private TextView tv_oubox_detail_title;
	
	//消息id
	private int id;
	//用户id
	private String empId;
	//删除返回结果
	private String resultCode;
	/*//收件人id
	String receiveId;*/
	
	private String msgName;
	private String content;
	
	
	String url = InterfaceConfig.NEWSOUTBOX_DETAIL_URL;
	String deleteUrl = InterfaceConfig.NEWSOUTBOX_LIST_DELETE_URL;
	SDHttpClient sdClient;
	SDProgressDialog sdDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companynews_outbox_detail);
		
		//初始化视图
		initView();
		//初始化数据
		initData();
		
	}
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 1:
				Intent intent = new Intent();
				intent.setClass(CompanyNewsOutBoxDetailActivity.this,
						CompanyNewsOutboxListActivity.class);
				startActivity(intent);
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
		
		top_title = (TextView) findViewById(R.id.tv_top_title);
//		tv_outbox_detail_receiver = (TextView) findViewById(R.id.tv_outbox_detail_receiver);
		tv_outbox_detail_content = (TextView) findViewById(R.id.tv_outbox_detail_content);
		tv_outbox_detail_sendtime = (TextView) findViewById(R.id.tv_outbox_detail_sendtime);
		tv_oubox_detail_title = (TextView) findViewById(R.id.tv_oubox_detail_title);
		
		tv_outbox_detail_content.setMovementMethod(new ScrollingMovementMethod());
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnResend = (Button) findViewById(R.id.btn_outbox_detail_resend);
		btnDelete = (Button) findViewById(R.id.btn_outbox_detail_delete);
		
		btnBack.setOnClickListener(this);
		btnResend.setOnClickListener(this);
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
					msgName = (String) (jRoot.get("name").equals(null)?
							"":jRoot.get("name"));
					//接收人姓名
					String receiveName = (String) jRoot.get("receiveName");
					//消息内容
					content = (String) jRoot.get("content");
					//创建时间
					String createDate = (String) jRoot.get("createDate");
					//接收人id
//					receiveId = (String) jRoot.get("receiveId");
					
					
					top_title.setText("To: "+receiveName);
//					tv_outbox_detail_receiver.setText(receiveName);
					tv_outbox_detail_content.setText(content);
					tv_outbox_detail_sendtime.setText(createDate);
					tv_oubox_detail_title.setText(msgName);
					
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
					resultCode = jRoot.getString("resultCode");
					if("200".equals(resultCode)){
						Toast.makeText(CompanyNewsOutBoxDetailActivity.this,
								"删除成功！", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(CompanyNewsOutBoxDetailActivity.this,
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
	
	
	
	public void askYesOrNo(){
		new AlertDialog.Builder(CompanyNewsOutBoxDetailActivity.this)
		.setTitle("删除消息")
		.setMessage("删除消息后，对应发送给收件人的消息也会相应删除！确定删除消息？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
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
		
		// 点击转发时
		if(v == btnResend){
			Intent intent = new Intent(this, CompanyNewMessageActivity.class);
			intent.putExtra("msgName", msgName);
			intent.putExtra("content", content);
			startActivity(intent);
			finish();
		}
		
		// 点击删除时
		if(v == btnDelete){
			askYesOrNo();
			
		}
		
	}

}
