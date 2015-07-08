package com.superdata.pm.activity.homepage.bbs;

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

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.NetCheckUtil;
import com.superdata.pm.view.SDProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
/**
 * 首页-->企业论坛-->发表主题
 * @author kw
 *
 */
public class BBSAddTitleActivity extends BaseActivity implements OnClickListener {

	private TextView top_title;
	private ImageView btnBack;
	private Button btnPublish;
	private Button btnCancel;
	private EditText bbs_title;
	private EditText bbs_content;
	
	//用户id
	private String empId;
	//主题
	private String title = "";
	//内容
	private String content = "";
	//页码
	private int pageNum = 1;
	//每页大小
	private int pageSize = 10;

	Intent intent;
	Message msg;
	List<NameValuePair> params;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	String url = InterfaceConfig.BBS_ADD_URL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bbs_addtitle);
		
		empId = getIntent().getExtras().getString("empId");
		
		initView();//初始视图
		initData();//初始控件
		
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				//发帖跳转到论坛列表
				intent = new Intent();
				intent.setClass(BBSAddTitleActivity.this, BBSActivity.class);
				startActivity(intent);
				finish();
				break;

			case 2:
				Toast.makeText(BBSAddTitleActivity.this, "回复的内容不能为空！",
						Toast.LENGTH_SHORT).show();
				break;
			
			case 3:
				Toast.makeText(BBSAddTitleActivity.this, "网络异常！",
						Toast.LENGTH_SHORT).show();
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
		top_title.setText("发表主题");
	}
	
	
	
	@Override
	public void onClick(View v) {
		
		// 点击返回时
		if(v == btnBack){
			askYesOrNo();
		}
		
		//点击发表时
		if(v == btnPublish){
			title = bbs_title.getText().toString().trim();
			content = bbs_content.getText().toString().trim();
			
			if(chechIsNull(bbs_title) ||
					chechIsNull(bbs_content)){
				msg = new Message();
				msg.what = 2;
				handler.sendMessage(msg);
				return;
			}
			
			sdDialog.show();
			PublishThread publishThread = new PublishThread();
			new Thread(publishThread).start();
		}
		
		
		if(v == btnCancel){
			askYesOrNo();
		}
		
	}



	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		bbs_title = (EditText) findViewById(R.id.et_bbs_addtitle_title);
		bbs_content = (EditText) findViewById(R.id.et_bbs_addtitle_content);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnPublish = (Button) findViewById(R.id.btn_bbs_addtitle_publish);
		btnCancel = (Button) findViewById(R.id.btn_bbs_addtitle_cancel);
		
		btnBack.setOnClickListener(this);
		btnPublish.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		
		sdDialog = new SDProgressDialog(this);
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
	
	//获取数据
	public List<NameValuePair> getParamList(){
		params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("name", title));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum+""));
		params.add(new BasicNameValuePair("pageSize", pageSize+""));
		
		return params;
	} 
	
	//询问是否返回
	public void askYesOrNo(){
		new AlertDialog.Builder(BBSAddTitleActivity.this)
		.setTitle("取消发帖")
		.setMessage("确定取消发帖？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				onBackPressed();
				
			}
		}).setNegativeButton("取消", null).show();
		
	}
	
	/**
	 * 发表帖子的线程
	 * @author Administrator
	 *
	 */
	class PublishThread implements Runnable{

		@Override
		public void run() {
			if(!NetCheckUtil.isNetworkAvailable(BBSAddTitleActivity.this)){
				Toast.makeText(BBSAddTitleActivity.this, "网络不给力，请稍后重试！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			sdClient = new SDHttpClient();
			getParamList();
			msg = new Message();
			try {
				String json = sdClient.post_session(url, params);
				
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
