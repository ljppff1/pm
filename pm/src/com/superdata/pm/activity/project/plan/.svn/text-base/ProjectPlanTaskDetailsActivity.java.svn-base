package com.superdata.pm.activity.project.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.project.contract.ProjectContractActivity;
import com.superdata.pm.activity.project.manager.detail.comment.ProjectCommentActivity;
import com.superdata.pm.activity.project.manager.detail.log.ProjectLogActivity;
import com.superdata.pm.activity.project.member.ProjectMemberActivity;
import com.superdata.pm.activity.project.report.CheckProgressActivity;
import com.superdata.pm.activity.project.report.CheckReportActivity;
import com.superdata.pm.activity.project.report.FillProgressActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;

/**
 * 项目--》项目管理--》项目详细--》项目计划（工作包）--》项目计划（任务）
 * @author kw
 *
 */
public class ProjectPlanTaskDetailsActivity extends BaseActivity  {

	private ImageView back;
	private TextView tv_top_title;
	private String exprate;
	private String writerate;
	private String auditrate;
	private TextView tv_exprate;
	private TextView tv_inputrate;
	private TextView tv_auditrate;
	private TextView tv_inputremark;
	private String remark;
	private TextView tv_taskremark;
	private String taskremark;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.projectplan_task_detail);
	     initDate();
		initView();
		
	

	}
	
	
	private void initDate() {
    Bundle bundle =getIntent().getExtras();
    exprate =  bundle.getString("EXPRATE");
    writerate = bundle.getString("WRITERATE");
    auditrate =  bundle.getString("AUDITRATE");
    remark =  bundle.getString("REMARK");
    taskremark =  bundle.getString("TASKREMARK");
	}


	private void initView() {
	//点击返回
	back = (ImageView) findViewById(R.id.ll_top_title);
	back.setOnClickListener(new OnClickListener() {
				
		@Override
		public void onClick(View v) {
			// 回退
			onBackPressed();
		}
	});
	
	tv_top_title = (TextView) findViewById(R.id.tv_top_title);
	tv_top_title.setText("任务查看");
	
    tv_exprate =(TextView)this.findViewById(R.id.proplan_task_detail_tv_progress);
    tv_inputrate =(TextView)this.findViewById(R.id.proplan_task_detail_tv_inputprogress);
    tv_auditrate =(TextView)this.findViewById(R.id.proplan_task_detail_tv_auditprogress);
    tv_inputremark =(TextView)this.findViewById(R.id.proplan_task_detail_tvinputdetail);
    tv_taskremark =(TextView)this.findViewById(R.id.proplan_task_detail_tvtaskdetail);
 
    if(!TextUtils.isEmpty(exprate)){
    	tv_exprate.setText(exprate+"%");
    }else{
    	tv_exprate.setText("");

    }

    
      if(!TextUtils.isEmpty(auditrate)){
    	  tv_auditrate.setText(auditrate+"%");
      }else{
    	  tv_auditrate.setText("");

      }
      if(!TextUtils.isEmpty(writerate)){
    	  tv_inputrate.setText(writerate+"%");
      }else{
    	  tv_inputrate.setText("");
      }
      if(!TextUtils.isEmpty(taskremark)){
    	  tv_taskremark.setText(taskremark);
      }else{
    	  tv_taskremark.setText("");

      }
      if(!TextUtils.isEmpty(remark)){
    	  tv_inputremark.setText(remark);
      }else{
    	  tv_inputremark.setText("");

      }  
   

     
	
	}
	

	
	
}