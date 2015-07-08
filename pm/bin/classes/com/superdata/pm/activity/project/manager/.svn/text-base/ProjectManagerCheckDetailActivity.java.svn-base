package com.superdata.pm.activity.project.manager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.homepage.bbs.BBSActivity;
import com.superdata.pm.activity.project.contract.ProjectContractActivity;
import com.superdata.pm.activity.project.manager.detail.comment.ProjectCommentActivity;
import com.superdata.pm.activity.project.manager.detail.log.ProjectLogActivity;
import com.superdata.pm.activity.project.member.ProjectMemberActivity;
import com.superdata.pm.activity.project.plan.ProjectPlanPackageActivity;
import com.superdata.pm.activity.project.report.CheckReportActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
/**
 * 项目-->项目管理-->项目列表-->项目详细
 * @author kw
 *
 */
public class ProjectManagerCheckDetailActivity extends BaseActivity implements OnClickListener {

	private ImageView btnBack;
	private TextView top_title;
	private PopupWindow popupwindow;
	private ImageView iv_top;
	private TextView tv_projectmanager_check_detail_number;
	private TextView tv_projectmanager_check_detail_name;
	private TextView tv_projectmanager_check_detail_startdata;
	private TextView tv_projectmanager_check_detail_enddata;
	private TextView tv_projectmanager_check_detail_type;
	private TextView tv_projectmanager_check_detail_manager;
	private TextView tv_projectmanager_check_detail_expense;
	private TextView tv_projectmanager_check_detail_material;
	private TextView tv_projectmanager_check_detail_job;
	private TextView tv_projectmanager_check_detail_state;
	private TextView tv_projectmanager_check_detail_creater;
	private TextView tv_projectmanager_check_detail_createtime;
	private TextView tv_projectmanager_check_detail_description;
	private TextView tv_projectmanager_check_detail_budget;
	
	private String id;//项目ID
	private String empId;//用户ID
	private String name;
	
	
	
	String url = InterfaceConfig.PROJECT_DETAIL_URL;
	SDHttpClient sdClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectmanager_check_detail);
		
		id = (String) getIntent().getExtras().getString("ID");
		empId = getIntent().getExtras().getString("empId");
		init();//初始化视图
		initData();//初始化数据
	}
	
	
	public void initData(){
		top_title.setText("项目详细");
		initData(empId, id);
	}
	
	public void initData(String empId,String projectId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("projectId", projectId));

		new MyTask().execute(params);
	}
	
	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String>{
		
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			sdClient = new SDHttpClient();
			String json = null;
			//empId=xx&projectId=xx
			try {
				json = sdClient.post_session(url, params[0]);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return json;
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		@Override
		protected void onPostExecute(String result) {
			if(result != null){
				try {
					JSONObject jRoot = new JSONObject(result);
					
					String code = (String) jRoot.get("code");//项目编号
					name = (String) jRoot.get("name");//项目名称
					String startDate = (String) jRoot.get("startDate");//开始日期
					String endDate = (String) jRoot.get("endDate");//结束日期
					String empName = (String) jRoot.get("empName");//项目经理
					String amount =  df.format(jRoot.get("amount"));//预算总额
//					Integer rate = (Integer) jRoot.get("rate");//完成度
					String statusName =  (String) (jRoot.get("statusName").equals(null)?
							"已终止":jRoot.get("statusName"));//项目状态
					String createDate = (String) jRoot.get("createDate");//立项日期
					String employeeAmt = df.format(jRoot.get("employeeAmt"));//人力预算
					String productAmt = df.format(jRoot.get("productAmt"));//材料预算
					String feeAmt = df.format(jRoot.get("feeAmt"));//费用预算
					String typeName = (String) jRoot.get("typeName");//项目类型
					String createName = (String) jRoot.get("createName");//立项人
					String remark = (String) (jRoot.get("remark").equals(null)?
							"":jRoot.get("remark"));//项目说明
					
					String startDate1 = startDate.substring(0, 10);
					String endDate1 = endDate.substring(0, 10);
					String createDate1 = createDate.substring(0, 10);
					
					tv_projectmanager_check_detail_number.setText(code);
					tv_projectmanager_check_detail_name.setText(name);
					tv_projectmanager_check_detail_startdata.setText(startDate1);
					tv_projectmanager_check_detail_enddata.setText(endDate1);
					tv_projectmanager_check_detail_type.setText(typeName);
					tv_projectmanager_check_detail_manager.setText(empName);
					tv_projectmanager_check_detail_budget.setText(amount.toString());
					tv_projectmanager_check_detail_expense.setText(feeAmt.toString());
					tv_projectmanager_check_detail_material.setText(productAmt.toString());
					tv_projectmanager_check_detail_job.setText(employeeAmt.toString());
					tv_projectmanager_check_detail_state.setText(statusName);
					tv_projectmanager_check_detail_creater.setText(createName);
					tv_projectmanager_check_detail_createtime.setText(createDate1);
					tv_projectmanager_check_detail_description.setText(remark);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			super.onPostExecute(result);
		}
		
	}
	
	
	@Override
	public void onClick(View v) {
		// 点击返回时
		if(v == btnBack){
			onBackPressed();
		}
		
	}


	public void init(){
		top_title = (TextView) findViewById(R.id.tv_top_title_window);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title_window);
		btnBack.setOnClickListener(this);
		
		
		iv_top = (ImageView) findViewById(R.id.iv_top_window);
		iv_top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(popupwindow != null && popupwindow.isShowing()){
					popupwindow.dismiss();
					return;
				}else{
					initPopupWindowView();//初始化popupwindow
				}
			}
		});
		
		tv_projectmanager_check_detail_number = (TextView) findViewById(R.id.tv_projectmanager_check_detail_number);
		tv_projectmanager_check_detail_name = (TextView) findViewById(R.id.tv_projectmanager_check_detail_name);
		tv_projectmanager_check_detail_startdata = (TextView) findViewById(R.id.tv_projectmanager_check_detail_startdata);
		tv_projectmanager_check_detail_enddata = (TextView) findViewById(R.id.tv_projectmanager_check_detail_enddata);
		tv_projectmanager_check_detail_type = (TextView) findViewById(R.id.tv_projectmanager_check_detail_type);
		tv_projectmanager_check_detail_manager = (TextView) findViewById(R.id.tv_projectmanager_check_detail_manager);
		tv_projectmanager_check_detail_budget = (TextView) findViewById(R.id.tv_projectmanager_check_detail_budget);
		tv_projectmanager_check_detail_expense = (TextView) findViewById(R.id.tv_projectmanager_check_detail_expense);
		tv_projectmanager_check_detail_material = (TextView) findViewById(R.id.tv_projectmanager_check_detail_material);
		tv_projectmanager_check_detail_job = (TextView) findViewById(R.id.tv_projectmanager_check_detail_job);
		tv_projectmanager_check_detail_state = (TextView) findViewById(R.id.tv_projectmanager_check_detail_state);
		tv_projectmanager_check_detail_creater = (TextView) findViewById(R.id.tv_projectmanager_check_detail_creater);
		tv_projectmanager_check_detail_createtime = (TextView) findViewById(R.id.tv_projectmanager_check_detail_createtime);
		tv_projectmanager_check_detail_description = (TextView) findViewById(R.id.tv_projectmanager_check_detail_description);
		
	}
	
	
	public void initPopupWindowView(){
		// 获取自定义布局文件pop.xml的视图  
		View customView = getLayoutInflater().inflate(R.layout.popupwindow, null, false);
		// 创建PopupWindow实例
		popupwindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, 
				ViewGroup.LayoutParams.WRAP_CONTENT);
		//设置popupwindow背景
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
		//popupwindow获得焦点
		popupwindow.setFocusable(true);
		//popupwindow显示的位置
		popupwindow.showAsDropDown(iv_top);
		
		// 自定义view添加触摸事件  
		customView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 if (popupwindow != null && popupwindow.isShowing()){
					 popupwindow.dismiss();  
					 popupwindow = null;  
				 }
				return false;
			}
		});
		
	}
	
	
	//点击查看项目评论
	public void projectComment(View v){
		popupwindow.dismiss();
//		IntentUtils.gotoActivity(this, ProjectCommentActivity.class);
		Intent intent = new Intent(this,BBSActivity.class);
		intent.putExtra("empId", empId);
		intent.putExtra("projectName", name);
		intent.putExtra("projectId", id);
		startActivity(intent);
	}
	
	//点击查看项目日志
	public void projectlog(View v){
		popupwindow.dismiss();
//		IntentUtils.gotoActivity(this, ProjectLogActivity.class);
		Intent intent = new Intent(this, ProjectLogActivity.class);
		intent.putExtra("empId", empId);
		intent.putExtra("projectId", id);
		startActivity(intent);
	}
	
	
}
