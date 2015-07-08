package com.superdata.pm.activity.project.report;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.project.contract.ProjectContractActivity;
import com.superdata.pm.activity.project.manager.detail.comment.ProjectCommentActivity;
import com.superdata.pm.activity.project.manager.detail.log.ProjectLogActivity;
import com.superdata.pm.activity.project.member.ProjectMemberActivity;
import com.superdata.pm.activity.project.plan.ProjectPlanPackageActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.PReport;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.BaseUtil;
import com.superdata.pm.util.IntentUtils;

/**
 * 项目-->项目管理-->项目详细-->验收报告
 * @author kw
 *
 */
public class CheckReportActivity extends BaseActivity {

	private ImageView back;//返回
	private TextView top_title;//标题
	
	private Button btnCheck;//审核按钮
	private Button btnCheckReport;//查看验收项按钮
	
	private TextView tv_tab_checkreport_reportnumber;//验收报告编号
	private TextView tv_tab_checkreport_project;//验收项目
	private TextView tv_tab_checkreport_reportname;//验收报告名称
	private TextView tv_tab_checkreport_checktime;//验收时间
	private TextView tv_tab_checkreport_header;//验收负责人
	private TextView tv_tab_checkreport_projectstartdata;//项目开始时间
	private TextView tv_tab_checkreport_projectenddata;//项目结束时间
	private TextView tv_tab_checkreport_checkresult;//验收结果
	private TextView tv_tab_checkreport_checkexplain;//验收说明
	private TextView tv_tab_checkreport_statu;//审核状态
	private TextView tv_tab_checkreport_fillman;//填报人
	private TextView tv_tab_checkreport_filldata;//填报日期
	private TextView tv_tab_projectmember_checkman;//审核人
	private TextView tv_tab_checkreport_checkdata;//审核日期
	
	private Integer id;//报告id
	private Integer projectId;//项目id
	private Integer result1;//验收状态
	private String auditId;//审核人id
	private String empId;//当前登录用户id
	
	private String result2;//验收状态(显示)
	private boolean success;//审核是否成功
	private String resultcode;
	
	//验收报告url
	String url = InterfaceConfig.CHECKREPORT_URL;
	//验收报告审核url
	String url1 = InterfaceConfig.AUDITREPORT_URL;
	SDHttpClient sdClient;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_checkreport);
		
		empId = getIntent().getExtras().getString("empId");
		id =  getIntent().getExtras().getInt("Id");
		
		initView();//初始化视图
		initData();//初始化数据
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(CheckReportActivity.this, "审核成功！", Toast.LENGTH_SHORT).show();
				initView();
				tv_tab_checkreport_statu.setText("已审核");
				btnCheck.setVisibility(View.INVISIBLE);
				
				break;
			case 2:
				Toast.makeText(CheckReportActivity.this, "审核失败！", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	
	public void initData(){
		top_title.setText("报告详细");
		initData1(id+"");
	}
	
	public void initData1(String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		
		new MyTask().execute(params);
	}
	
	public void initData2(String projectId,String id,String result,String auditId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("result", result));
		params.add(new BasicNameValuePair("auditId", auditId));
		
		new MyTask2().execute(params);
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
					JSONObject jsonObject = jRoot.getJSONObject("resultData");
					
					String resultCode = (String) jRoot.get("resultCode");
					
					//判断resultCode是否为200，为200则查询成功，否则失败
					if("200".equals(resultCode.trim())){
						String code = (String) jsonObject.get("code");//报告编号
						String projectName = (String) jsonObject.get("projectName");//验收项目
						String name = (String) jsonObject.get("name");//报告名称
						String billDate = (String) jsonObject.get("billDate");//验收时间
						String empName = (String) jsonObject.get("empName"); //验收负责人
						String projectStartDate = (String) jsonObject.get("projectStartDate");//项目开始时间
						String projectEndDate = (String) jsonObject.get("projectEndDate");//项目结束时间
						String resultName = (String) jsonObject.get("resultName");//验收结果
						String remark = (String) (jsonObject.get("remark").equals(null)?
								"":jsonObject.get("remark"));//验收说明
						Integer status = (Integer) jsonObject.get("status");//审核状态
						String createName = (String) jsonObject.get("createName");//填报人
						String createDate = (String) jsonObject.get("createDate");//填报时间
						String auditName = (String) (jsonObject.get("auditName").equals(null)?
								"":jsonObject.get("auditName"));//审核人
						String auditDate = (String) (jsonObject.get("auditDate").equals(null)?
								"":jsonObject.get("auditDate"));//审核日期
						
						projectId = (Integer) jsonObject.get("projectId");//项目id
						id = (Integer) jsonObject.get("id");//报告id
						result1 = (Integer) jsonObject.get("result");//验收结果
						auditId = empId;//审核人id即用户id
						
						String billDate1 = billDate.substring(0, 10);
						String projectStartDate1 = projectStartDate.substring(0, 10);
						String projectEndDate1 = projectEndDate.substring(0, 10);
						String createDate1 = createDate.substring(0, 10);
						String auditDate1 = "";
						if(auditDate!= ""){
							auditDate1 = auditDate.substring(0, 10);
						}
						
						tv_tab_checkreport_reportnumber.setText(code);
						tv_tab_checkreport_project.setText(projectName);
						tv_tab_checkreport_reportname.setText(name);
						tv_tab_checkreport_checktime.setText(billDate1);
						tv_tab_checkreport_header.setText(empName);
						tv_tab_checkreport_projectstartdata.setText(projectStartDate1);
						tv_tab_checkreport_projectenddata.setText(projectEndDate1);
						tv_tab_checkreport_checkresult.setText(resultName);
						tv_tab_checkreport_checkexplain.setText(remark);
						String statusName;//审核状态名称
						if(status == 0){
							statusName = "未审核";
							tv_tab_checkreport_statu.setText(statusName);
						}else if(status == 1){
							statusName = "已审核";
							tv_tab_checkreport_statu.setText(statusName);
						}
						tv_tab_checkreport_fillman.setText(createName);
						tv_tab_checkreport_filldata.setText(createDate1);
						tv_tab_projectmember_checkman.setText(auditName);
						tv_tab_checkreport_checkdata.setText(auditDate1);
						
						//验收状态
						result2 = tv_tab_checkreport_statu.getText().toString();
						if(("未审核").equals(result2.trim()) || ("").equals(result2.trim())){
							btnCheck.setVisibility(View.VISIBLE);
							btnCheck.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									//点击审核状态变为已审核
									initData2(projectId+"",id+"",result1+"",auditId);
								}
							});
						}else{
							//若是已审核，则按钮隐藏
							btnCheck.setVisibility(View.INVISIBLE);
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			super.onPostExecute(result);
		}
		
	}
	
	class MyTask2 extends AsyncTask<List<NameValuePair>, Integer, String>{

		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			sdClient = new SDHttpClient();
			String json = null;
			try {
				json = sdClient.post_session(url1, params[0]);
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
					resultcode = (String) jRoot.get("resultCode");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Message msg = new Message();
			if("200".equals(resultcode.trim())){
				msg.what = 1;
			}else{
				msg.what = 2;
			}
			handler.sendMessage(msg);
			super.onPostExecute(result);
		}
		
	}
	
	//初始化方法
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		
		back = (ImageView) findViewById(R.id.ll_top_title);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 回退
				onBackPressed();
			}
		});
		
		tv_tab_checkreport_reportnumber = (TextView) findViewById(R.id.tv_tab_checkreport_reportnumber);
		tv_tab_checkreport_project = (TextView) findViewById(R.id.tv_tab_checkreport_project);
		tv_tab_checkreport_reportname = (TextView) findViewById(R.id.tv_tab_checkreport_reportname);
		tv_tab_checkreport_checktime = (TextView) findViewById(R.id.tv_tab_checkreport_checktime);
		tv_tab_checkreport_header = (TextView) findViewById(R.id.tv_tab_checkreport_header);
		tv_tab_checkreport_projectstartdata = (TextView) findViewById(R.id.tv_tab_checkreport_projectstartdata);
		tv_tab_checkreport_projectenddata = (TextView) findViewById(R.id.tv_tab_checkreport_projectenddata);
		tv_tab_checkreport_checkresult = (TextView) findViewById(R.id.tv_tab_checkreport_checkresult);
		tv_tab_checkreport_checkexplain = (TextView) findViewById(R.id.tv_tab_checkreport_checkexplain);
		tv_tab_checkreport_statu = (TextView) findViewById(R.id.tv_tab_checkreport_statu);
		tv_tab_checkreport_fillman = (TextView) findViewById(R.id.tv_tab_checkreport_fillman);
		tv_tab_checkreport_filldata = (TextView) findViewById(R.id.tv_tab_checkreport_filldata);
		tv_tab_projectmember_checkman = (TextView) findViewById(R.id.tv_tab_projectmember_checkman);
		tv_tab_checkreport_checkdata = (TextView) findViewById(R.id.tv_tab_checkreport_checkdata);
		
		//审核按钮
		btnCheck = (Button) findViewById(R.id.btn_tab_checkreport_check);
		
		//查看验收项按钮
		btnCheckReport = (Button) findViewById(R.id.btn_tab_checkreport);
		btnCheckReport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 点击查看验收项
				Intent intent = new Intent(CheckReportActivity.this, CheckItemActivity.class);
				intent.putExtra("reportId", id);
				startActivity(intent);
			}
		});
		
	}
	
}
