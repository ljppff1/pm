package com.superdata.pm.activity.project.contract;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.view.SDProgressDialog;

/**
 * 项目-->项目合同-->合同详细
 * @author kw
 *
 */
public class ProjectContractDetailActivity extends BaseActivity {

	private ImageView back;
	private TextView top_title;
	private TextView tv_contractdetail_project;
	private TextView tv_contractdetail_contractnumber;
	private TextView tv_contractdetail_contractname;
	private TextView tv_contractdetail_contractsigndata;
	private TextView tv_contractdetail_contractsum;
	private TextView tv_contractdetail_startdata;
	private TextView tv_contractdetail_enddata;
	private TextView tv_contractdetail_type;
	private TextView tv_contractdetail_myperson;
	private TextView tv_contractdetail_othercompany;
	private TextView tv_contractdetail_otherperson;
	private TextView tv_contractdetail_clause;
	
	private String empId;
	private String contractId;
	private boolean flag = true;
	
	String url = InterfaceConfig.PROJECT_CONTRACTDETAIL_URL;
	SDHttpClient sdClient;
	SDProgressDialog sdDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectcontract_detail);
		
		empId = getIntent().getExtras().getString("empId");
		contractId = getIntent().getExtras().getString("contractId");
		
		initView();//初始化视图
		initData();//初始化数据
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	
	public void initData(){
		top_title.setText("合同详细");
		
		initData(empId, contractId);
	}
	
	public void initData(String empId, String contractId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("contractId", contractId));
		
		new MyTask().execute(params);
	}
	
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		
		back = (ImageView) findViewById(R.id.ll_top_title);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		sdDialog = new SDProgressDialog(ProjectContractDetailActivity.this);
		
		tv_contractdetail_project = (TextView) findViewById(R.id.tv_contractdetail_project);
		tv_contractdetail_contractnumber = (TextView) findViewById(R.id.tv_contractdetail_contractnumber);
		tv_contractdetail_contractname = (TextView) findViewById(R.id.tv_contractdetail_contractname);
		tv_contractdetail_contractsigndata = (TextView) findViewById(R.id.tv_contractdetail_contractsigndata);
		tv_contractdetail_contractsum = (TextView) findViewById(R.id.tv_contractdetail_contractsum);
		tv_contractdetail_startdata = (TextView) findViewById(R.id.tv_contractdetail_startdata);
		tv_contractdetail_enddata = (TextView) findViewById(R.id.tv_contractdetail_enddata);
		tv_contractdetail_type = (TextView) findViewById(R.id.tv_contractdetail_type);
		tv_contractdetail_myperson = (TextView) findViewById(R.id.tv_contractdetail_myperson);
		tv_contractdetail_othercompany = (TextView) findViewById(R.id.tv_contractdetail_othercompany);
		tv_contractdetail_otherperson = (TextView) findViewById(R.id.tv_contractdetail_otherperson);
		tv_contractdetail_clause = (TextView) findViewById(R.id.tv_contractdetail_clause);
		
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
		
		
		DecimalFormat df = new DecimalFormat("#.##");
		@Override
		protected void onPostExecute(String result) {
			if(null != result){
				try {
					JSONObject jRoot = new JSONObject(result);
					
					String projectName = (String) jRoot.get("projectName");//项目名称
					String code = (String) jRoot.get("code");//合同编码
					String name = (String) jRoot.get("name");//合同名称
					String signedDate = (String) jRoot.get("signedDate");//签订日期
					String amount = df.format(jRoot.get("amount"));//合同金额
					String startDate = (String) jRoot.get("startDate");//开始日期
					String endDate = (String) jRoot.get("endDate");//终止日期
					String typeName = (String) jRoot.get("typeName");//合同类型名称
					String empName = (String) jRoot.get("empName");//我方代表员工姓名
					String otherCompany = (String) jRoot.get("otherCompany");//对方公司
					String otherDeputy = (String) jRoot.get("otherDeputy");//对方代表
					String content = (String) (jRoot.get("content").equals(null)?
							"":jRoot.get("content"));//简要条款
					
					String signedDate1 = signedDate.substring(0, 10);
					String startDate1 = startDate.substring(0, 10);
					String endDate1 = endDate.substring(0, 10);
					
					tv_contractdetail_project.setText(projectName);
					tv_contractdetail_contractnumber.setText(code);
					tv_contractdetail_contractname.setText(name);
					tv_contractdetail_contractsigndata.setText(signedDate1);
					tv_contractdetail_contractsum.setText(amount);
					tv_contractdetail_startdata.setText(startDate1);
					tv_contractdetail_enddata.setText(endDate1);
					tv_contractdetail_type.setText(typeName);
					tv_contractdetail_myperson.setText(empName);
					tv_contractdetail_othercompany.setText(otherCompany);
					tv_contractdetail_otherperson.setText(otherDeputy);
					tv_contractdetail_clause.setText(content);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(flag){
				handler.sendEmptyMessage(1);
			}
			
			super.onPostExecute(result);
		}
		
	}
	
}
