package com.superdata.pm.activity.project.plan;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.view.SDProgressDialog;

/**
 * 反审核
 * 
 * @author lj
 * 
 *         2014年8月4日
 */
public class AuditBackRateActivity extends BaseActivity {
	private TextView exprate;
	private Button btn_input;
	private ImageView back;
	private SDProgressDialog sdDialog;
	private TextView inputrate;
	private TextView auditrate;
	private TextView auditremark;
	// 项目ID
	private String projectId;
	// 任务ID
	private String taskId;
	// 填报记录
	private String scheduleId;
	// 预期进度
	private String Exrate;
	// 审核进度
	private String Auditrate;
	// 审核描述
	private String Auditremark;
	private Button btn_cancel;
	private String Position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectplan_task_auditbackrate);
		// 初始化数据
		initData();
		// 初始化View
		initView();
	}

	private void initData() {
		projectId = getIntent().getExtras().get("PROJECTID") + "";
		taskId = getIntent().getExtras().get("TASKID") + "";
		scheduleId = getIntent().getExtras().get("SCHEDULEID") + "";
		Exrate = getIntent().getExtras().get("EXTRATE") + "";
		Auditrate = getIntent().getExtras().getString("AUDITRATE") ;
		Auditremark = getIntent().getExtras().getString("REMARK");
		Position =getIntent().getExtras().getString("POSITION");

	}

	private void initView() {
		exprate = (TextView) this.findViewById(R.id.proplan_task_auditbackrate_tv_progress);
		auditrate = (TextView) this.findViewById(R.id.proplan_task_auditbackrate_tv_auditprogress);
		auditremark = (TextView) this.findViewById(R.id.proplan_task_auditbackrate_etdetail);
		if(!TextUtils.isEmpty(Exrate)){

		exprate.setText(Exrate+"%");
		 }
	
		 if(!TextUtils.isEmpty(Auditrate)){

		auditrate.setText(Auditrate+"%");
		 }
	
		auditremark.setText(Auditremark);
		
		btn_input = (Button) this.findViewById(R.id.proplan_task_auditbackrate_btnput);
		btn_input.setOnClickListener(listener);
        btn_cancel =(Button)this.findViewById(R.id.proplan_task_auditbackrate_btncancel);
        btn_cancel.setOnClickListener(listener);
		// 点击返回
		back = (ImageView) findViewById(R.id.proplan_task_auditbackrate_iv_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 回退
				onBackPressed();
			}
		});
	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.proplan_task_auditbackrate_btnput:
				initdata();
				break;
			case R.id.proplan_task_auditbackrate_btncancel:
				onBackPressed();
				break;
			default:
				break;
			}
			
		}
	};

	private void initdata() {
		/**
		 * -taskInterface/reverseAuditRate.do projectId --项目ID taskId --任务ID
		 * scheduleId --填报记录ID
		 */
		initdata(projectId, taskId, scheduleId);
	}

	private void initdata(String projectId, String taskId, String scheduleId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("taskId", taskId));
		params.add(new BasicNameValuePair("scheduleId", scheduleId));

		new MyTask().execute(params);
	}

	
	//"http://192.168.0.117:8080/pm/taskInterface/reverseAuditRate.do";
	String url = InterfaceConfig.AUDIT_BACK_RATE_URL;
	private String code;
	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String> {
		@Override
		protected void onPreExecute() {
			sdDialog = new com.superdata.pm.view.SDProgressDialog(
					AuditBackRateActivity.this);
//			sdDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(List<NameValuePair>... params) {

			String aa = null;
			try {
				aa = new SDHttpClient().post_session(url, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return aa;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					// {"code":1000,"msg":"进度填报成功"}
					JSONObject jo = new JSONObject(result);
					code = jo.getInt("resultCode") + "";
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			Message msg = new Message();
			if (code.equals("200")) {
				msg.what = 1;
			} else {
				msg.what =2;
			}
			handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
						Intent intent =new Intent();
						intent.putExtra("POSITION", Position);
						setResult(3, intent);
						finish();

				break;
			case 2:
				Toast.makeText(getApplicationContext(), "已存在新的填报记录,无法进行反审核",
						Toast.LENGTH_SHORT).show();
				onBackPressed();
				break;
		
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};
}