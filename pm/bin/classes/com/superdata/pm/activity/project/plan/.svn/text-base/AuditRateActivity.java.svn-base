package com.superdata.pm.activity.project.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.view.SDProgressDialog;

/**
 * 审核进度
 * 
 * @author lj
 * 
 * 2014年8月4日
 */
public class AuditRateActivity extends BaseActivity {
	// 审核进度的填报
	private SeekBar seekBar;
	// 审核描述
	private TextView seekBarText;
	// 审核按钮
	private Button btn_input;
	private ImageView back;
	private SDProgressDialog sdDialog;
	// 审核描述
	private EditText auditEdit;
	// 项目ID
	private String projectId;
	// 任务ID
	private String taskId;
	// 填报记录
	private String scheduleId;

	private String value = "10";
	// 预期进度
	private TextView exprate;
	// 填报进度
	private TextView inputrate;
	// 填报描述
	private TextView inputDetail;
	// 审核描述暂存
	private String remark;
    //返回码
	private String code;
	private String d_extrate;
	private String d_inputrate;
	private String d_inputremark;
	private Button btn_cancel;
	private String Position;
	private String userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectplan_task_auditrate);
	
		Map<String, String> mapconfig = com.superdata.pm.util.SharedPreferencesConfig
				.config(AuditRateActivity.this);
		userId = mapconfig.get(InterfaceConfig.ID);

		// 初始化数据
		initData();
		// 初始化View
		initView();
	}

	private void initData() {
		projectId = getIntent().getExtras().get("PROJECTID") + "";
		taskId = getIntent().getExtras().get("TASKID") + "";
		scheduleId = getIntent().getExtras().get("SCHEDULEID") + "";
		d_extrate =getIntent().getExtras().get("EXTRATE")+"";
		d_inputrate =getIntent().getExtras().getString("INPUTRATE");
		value=d_inputrate;
		d_inputremark =getIntent().getExtras().getString("REMARK");
		Position =getIntent().getExtras().getString("POSITION");


		
	}

	private void initView() {
		exprate = (TextView) this
				.findViewById(R.id.proplan_task_auditrate_tv_progress);
		inputrate = (TextView) this
				.findViewById(R.id.proplan_task_auditrate_tv_inputprogress);
		seekBar = (SeekBar) this
				.findViewById(R.id.proplan_task_auditrate_tv_seekbar1);
		seekBarText = (TextView) this
				.findViewById(R.id.proplan_task_auditrate_tv_progresstext);
		inputDetail = (TextView) this
				.findViewById(R.id.proplan_task_auditrate_tvdetail);
		auditEdit = (EditText) this
				.findViewById(R.id.proplan_task_auditrate_etdetail);
		btn_input = (Button) this
				.findViewById(R.id.proplan_task_auditrate_btnput);
		btn_cancel =(Button) this
				.findViewById(R.id.proplan_task_auditrate_btncancel);
	
		//初始化审核进度,默认为填报进度
		seekBar.setProgress(Integer.valueOf(d_inputrate));
		

		seekBarText.setText(d_inputrate+"%");
		 
		
		if(!TextUtils.isEmpty(d_extrate)){

		exprate.setText(d_extrate+"%");
		 }
		if(!TextUtils.isEmpty(d_inputrate)){
		inputrate.setText(d_inputrate+"%");
		 }
	
		inputDetail.setText(d_inputremark);
		btn_input.setOnClickListener(listener);
		btn_cancel.setOnClickListener(listener);
		seekBar.setOnSeekBarChangeListener(listener1);
		// 点击返回
		back = (ImageView) findViewById(R.id.proplan_task_auditrate_iv_back);
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
			case R.id.proplan_task_auditrate_btnput:
				initdata();
				break;
			case R.id.proplan_task_auditrate_btncancel:
				onBackPressed();
				break;

			default:
				break;
			}
			
		}
		
		
	};

	private void initdata() {
		/**
		 * --审核进度 taskInterface/auditRate.do 参数: projectId --项目ID taskId --任务ID
		 * auditRate --审核进度 remark --审核意见 auditId --审核人ID scheduleId --填报记录ID
		 */
		remark = auditEdit.getText().toString();
		initdata(projectId, taskId, value, remark,userId, scheduleId);

	}

	private void initdata(String projectId, String taskId, String auditRate,
			String remark, String auditId, String scheduleId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("taskId", taskId));
		params.add(new BasicNameValuePair("auditRate", auditRate));
		params.add(new BasicNameValuePair("remark", remark));
		params.add(new BasicNameValuePair("auditId", auditId));
		params.add(new BasicNameValuePair("scheduleId", scheduleId));

		new MyTask().execute(params);
	}

	// String url ="http://192.168.0.117:8080/pm/taskInterface/auditRate.do";
	String url = InterfaceConfig.AUDIT_RATE_URL;


	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String> {

		@Override
		protected void onPreExecute() {

			sdDialog = new com.superdata.pm.view.SDProgressDialog(
					AuditRateActivity.this);
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
					JSONObject jo = new JSONObject(result);
					code = jo.getInt("resultCode") + "";
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Message msg = new Message();
			if (code.equals("200")) {
				msg.what = 1;
			} else if (code.equals("1001")) {
				msg.what = 2;
			} else {
				msg.what = 3;
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
				setResult(1, intent);
				finish();

				break;
			case 2:
				Toast.makeText(AuditRateActivity.this, "该进度已经被审核",Toast.LENGTH_SHORT).show();
				onBackPressed();
				
				break;
			case 3:
				Toast.makeText(AuditRateActivity.this, "fail",Toast.LENGTH_SHORT).show();
				onBackPressed();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	OnSeekBarChangeListener listener1 = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			value = String.valueOf(progress);
			seekBarText.setText(value + "%");
		}
	};
}