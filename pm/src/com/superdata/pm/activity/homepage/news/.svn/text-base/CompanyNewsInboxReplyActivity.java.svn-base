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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.NetCheckUtil;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;

/**
 * 首页-->站内消息-->收件箱-->收件箱消息-->消息回复
 * @author kw
 *
 */
public class CompanyNewsInboxReplyActivity extends BaseActivity implements OnClickListener {
	
	private ImageView btnBack;
	private Button btnSend;
	
	private TextView top_title;
	private TextView tv_inbox_reply_sender;
	private EditText et_inbox_reply_content;
	
	//发送人姓名
	private String senderName;
	//发送人id
	private int senderId;
	//用户id
	private String empId;
	//回复的内容
	private String content;
	//回复的标题
	private String msgName;
	//发送状态
	private Integer status = 1;
	
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	String sendUrl = InterfaceConfig.SENDMESSAGE_URL;
	Message msg;
	List<NameValuePair> params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companynews_inbox_reply);
		
		initView();
		initData();
	}
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(CompanyNewsInboxReplyActivity.this, "内容不能为空！",
						Toast.LENGTH_SHORT).show();
				break;
				
			case 2:
				Toast.makeText(CompanyNewsInboxReplyActivity.this, "网络异常！",
						Toast.LENGTH_SHORT).show();
				break;
				
			case 3:
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
	
	
	public void initData(){
		top_title.setText("回复："+msgName);
		tv_inbox_reply_sender.setText("To:"+senderName);
		
	}
	
	
	//初始化方法
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_inbox_reply_sender = (TextView) findViewById(R.id.tv_inbox_reply_sender);
		et_inbox_reply_content = (EditText) findViewById(R.id.et_inbox_reply_content);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSend = (Button) findViewById(R.id.btn_inbox_reply_send);
		
		btnBack.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		
		senderName = getIntent().getExtras().getString("senderName");
		senderId = getIntent().getExtras().getInt("senderId");
		empId = getIntent().getExtras().getString("empId");
		msgName = getIntent().getExtras().getString("msgName");
		
		sdDialog = new SDProgressDialog(this);
	}
	
	
	/**
	 * 请求发送数据
	 * @return
	 */
	public List<NameValuePair> getParamsList(){
		params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("msgName", msgName));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("receiveId", "0:"+senderId));
		params.add(new BasicNameValuePair("status", status+""));
		
		return params;
	}
	
	

	/**
	 * 发送消息线程
	 * @author Administrator
	 *
	 */
	class SendThread implements Runnable{

		@Override
		public void run() {
			if(!NetCheckUtil.isNetworkAvailable(CompanyNewsInboxReplyActivity.this)){
				Toast.makeText(CompanyNewsInboxReplyActivity.this,
						"网络不给力，请稍后重试！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			sdClient = new SDHttpClient();
			getParamsList();
			msg = new Message();
			try {
				String json = sdClient.post_session(sendUrl, params);
				
				msg.what = 3;
				msg.obj = json;
				handler.sendMessage(msg);
			} catch (Exception e) {
				msg.what = 2;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
			
		}
		
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
				Toast.makeText(CompanyNewsInboxReplyActivity.this,
						"消息回复成功！", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(CompanyNewsInboxReplyActivity.this,
						"消息回复失败！", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	public void onClick(View v) {
		// 点击返回时
		if(v == btnBack){
			new AlertDialog.Builder(CompanyNewsInboxReplyActivity.this)
			.setTitle("取消").setMessage("取消，内容不保存")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					onBackPressed();
				}
			}).setNegativeButton("取消", null).show();
		}
		
		
		// 点击发送时
		if(v == btnSend){
			content = et_inbox_reply_content.getText().toString();
			
			if(chechIsNull(et_inbox_reply_content)){
				msg = new Message();
				handler.sendEmptyMessage(1);
				return;
			}else{
				sdDialog.show();
				
				SendThread sendThread = new SendThread();
				new Thread(sendThread).start();
			}
		}
		
	}

}
