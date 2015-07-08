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
 * 进度填报
 * 
 * @author lj
 * 
 *         2014年8月4日
 */
public class WriteRateActivity extends BaseActivity {
	// 预期进度
	private TextView exprate;
	// 用于填报进度的SeekBar
	private SeekBar seekBar;
	// 进度显示
	private TextView seekBarText;
	// 进度输入框
	private EditText inputDetail;
	// 填报确定
	private Button btn_input;
	private ImageView back;
	private SDProgressDialog sdDialog;
	// 填报进度的任务ID，用于提交 参数审核
	private String id;
	// 填报进度
	private String value = "10";
	// 预期进度
	private String ExpRate;
	private Button btn_cancel;
	private String Position;
	private String userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectplan_task_writerate);
		Map<String, String> mapconfig = com.superdata.pm.util.SharedPreferencesConfig
				.config(WriteRateActivity.this);
		userId = mapconfig.get(InterfaceConfig.ID);

		initData();
		initView();

	}

	private void initData() {
		id = String.valueOf(getIntent().getExtras().get("ID"));
		ExpRate = getIntent().getExtras().getString("EXTRATE");
		Position =getIntent().getExtras().getString("POSITION");
	}

	private void initView() {
		exprate = (TextView) this.findViewById(R.id.proplan_task_writerate_tv_money);
		seekBar = (SeekBar) this.findViewById(R.id.proplan_task_writerate_tv_progress);
		seekBarText = (TextView) this.findViewById(R.id.proplan_task_writerate_tv_progresstext);
		inputDetail = (EditText) this.findViewById(R.id.proplan_task_writerate_etdetail);
		//初始化这个填报进度,默认和预期的相等
		seekBar.setProgress(Integer.valueOf(ExpRate));
		seekBarText.setText(ExpRate+"%");
		value=ExpRate;
		if(!TextUtils.isEmpty(ExpRate)){
		exprate.setText(ExpRate+"%");
		}else{
			exprate.setText("");
		}
		btn_input = (Button) this.findViewById(R.id.proplan_task_writerate_btnput);
		btn_cancel =(Button)this.findViewById(R.id.proplan_task_writerate_btncancel);
		btn_cancel.setOnClickListener(listener);
		btn_input.setOnClickListener(listener);
		seekBar.setOnSeekBarChangeListener(listener1);
		// 点击返回
		back = (ImageView) findViewById(R.id.proplan_task_writerate_iv_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.proplan_task_writerate_btncancel:
				onBackPressed();
				break;
			case R.id.proplan_task_writerate_btnput:
				initdata();
				break;

			default:
				break;
			}
			
		}
	};

	private void initdata() {
		/**
		 * --填报进度 taskInterface/writeRate.do 参数: taskId --任务ID rate --进度 remark
		 * --填报意见 userId --填报用户ID
		 */
		initdata(id, value, inputDetail.getText().toString(), userId);
	}

	private void initdata(String taskId, String rate, String remark,
			String userId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("taskId", taskId));
		params.add(new BasicNameValuePair("rate", rate));
		params.add(new BasicNameValuePair("remark", remark));
		params.add(new BasicNameValuePair("userId", userId));
		new MyTask().execute(params);
	}

	// String url ="http://192.168.0.117:8080/pm/taskInterface/writeRate.do";
	String url = InterfaceConfig.WRITE_RATE_URL;

	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String> {
		private String code;

		@Override
		protected void onPreExecute() {
			sdDialog = new com.superdata.pm.view.SDProgressDialog(
					WriteRateActivity.this);
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
				setResult(2, intent);
				finish();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "已存在新的填报记录,不能进行进度填报",
						Toast.LENGTH_SHORT).show();
				onBackPressed();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "fail",
						Toast.LENGTH_SHORT).show();
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
