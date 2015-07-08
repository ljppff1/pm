package com.superdata.pm.activity.cost.job;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
/**
 * 人力费用详情类
 * @author lj
 *
 * 2014年8月5日
 */
public class CostJobDetailMoreActivity extends BaseActivity{
	private ImageView iv_back;
	private TextView job_name;
	private TextView tv_top_title;
	private String amount;
	private String remark;
	private String feeitemname;
	private String worktaskname;
	private String count;
	private TextView job_task;
	private TextView job_money;
	private TextView job_count;
	private TextView job_remark;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.cost_job_detail_more);
    	
    	initData();
    	initView();
    	
    	
    	
    }

	private void initView() {
		job_name  =(TextView)this.findViewById(R.id.costjob_de_more_tv_value);
		job_name.setText(feeitemname);
		job_task =(TextView)this.findViewById(R.id.costjob_de_more_tv_task);
		job_task.setText(worktaskname);
		job_money =(TextView)this.findViewById(R.id.costjob_de_more_et_money);
		job_money.setText(amount);
		job_count =(TextView)this.findViewById(R.id.costjob_de_more_et_count);
		job_count.setText(count);
		job_remark =(TextView)this.findViewById(R.id.costjob_de_more_et_detail);
		job_remark.setText(remark);
	    iv_back  =(ImageView)this.findViewById(R.id.ll_top_title);
	    iv_back.setOnClickListener(listener);
	    tv_top_title = (TextView) this.findViewById(R.id.tv_top_title);
	    tv_top_title.setText("人力开支详情单");
	}
	private void initData() {
	      Bundle bundle = this.getIntent().getExtras();
	      amount =   bundle.get("PRICE")+"";
	      remark =   bundle.get("REMARK")+"";
	      feeitemname =   bundle.get("FEEITEMNAME")+"";
	      worktaskname =   bundle.get("WORKTASKNAME")+"";
	      count  =   bundle.get("COUNT")+"";
		}
   
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_top_title:
				onBackPressed();
				break;

			default:
				break;
			}
			
		}
	};

       
}
