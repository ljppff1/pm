package com.superdata.pm.activity.project.member;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;

/**
 * 项目-->项目列表-->项目成员列表-->成员详细
 * 
 * @author kw
 * 
 */
public class ProjectMemberDetailActivity extends BaseActivity {

	private ImageView back;
	private TextView top_title;
	private TextView tv_memberdetail_projectname;
	private TextView tv_tv_memberdetail_membername;
	private TextView tv_memberdetail_memberposition;
	private TextView tv_memberdetail_state;
	private TextView tv_memberdetail_remark;
	private TextView tv_memberdetail_creator;
	
	String empId;//用户ID
	String projectId;//项目ID
	String projectEmpId;//成员ID

	String url = InterfaceConfig.PROJECT_MEMBERDETAIL_URL;
	SDHttpClient sdClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectmemberdetail);

		empId = getIntent().getExtras().getString("empId");
		projectId = getIntent().getExtras().getString("projectId");
		projectEmpId = getIntent().getExtras().getString("projectEmpId");
		
		initView();// 初始化视图
		initData();// 初始化数据

	}

	
	public void initData() {
		top_title.setText("成员详细");
		initData(empId,projectId,projectEmpId);
	}

	public void initData(String empId, String projectId, String projectEmpId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("projectEmpId", projectEmpId));
		
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
					
					String projectName = (String) jRoot.get("projectName");//项目名称
					String empName = (String) jRoot.get("empName");//成员姓名
					String positionName = (String) jRoot.get("positionName");//成员职位
					String isCreatorName = (String) jRoot.get("isCreatorName");//是否为立项人
					String closedName = (String) jRoot.get("closedName");//是否参与
					String remark = (String) (jRoot.get("remark").equals(null)?
							"":jRoot.get("remark"));//备注
					
					
					tv_memberdetail_projectname.setText(projectName);
					tv_tv_memberdetail_membername.setText(empName);
					tv_memberdetail_memberposition.setText(positionName);
					tv_memberdetail_creator.setText(isCreatorName);
					tv_memberdetail_state.setText(closedName);
					tv_memberdetail_remark.setText(remark);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}
	
	//初始化视图
	public void initView() {
		top_title = (TextView) findViewById(R.id.tv_top_title);

		back = (ImageView) findViewById(R.id.ll_top_title);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 回退
				onBackPressed();
			}
		});

		tv_memberdetail_projectname = (TextView) findViewById(R.id.tv_memberdetail_projectname);
		tv_tv_memberdetail_membername = (TextView) findViewById(R.id.tv_tv_memberdetail_membername);
		tv_memberdetail_memberposition = (TextView) findViewById(R.id.tv_memberdetail_memberposition);
		tv_memberdetail_creator = (TextView) findViewById(R.id.tv_memberdetail_creator);
		tv_memberdetail_state = (TextView) findViewById(R.id.tv_memberdetail_state);
		tv_memberdetail_remark = (TextView) findViewById(R.id.tv_memberdetail_remark);
		
	}
	


}
