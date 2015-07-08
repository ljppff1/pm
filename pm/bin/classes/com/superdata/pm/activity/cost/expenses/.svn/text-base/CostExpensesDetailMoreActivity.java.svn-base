package com.superdata.pm.activity.cost.expenses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;

public class CostExpensesDetailMoreActivity extends BaseActivity{
	private TextView exp_name;
	private TextView exp_money;
	private ImageView iv_back;
	private TextView tv_top_title;
	private String amount;
	private String remark;
	private String feeitemname;
	private String worktaskname;
	private TextView tv_task;
	private TextView detail;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.cost_expenses_detail_more);
    	initData();
    	initView();
    	
    	
    	
    }
     
	
	private void initView() {
		exp_name  =(TextView)this.findViewById(R.id.costex_de_more_tv_money);
		exp_name.setText(feeitemname);
		exp_money =(TextView)this.findViewById(R.id.costex_de_more_et_money);
		exp_money.setText(amount);
		tv_task =(TextView)this.findViewById(R.id.costex_de_more_tv_task);
		tv_task.setText(worktaskname);
		detail =(TextView)this.findViewById(R.id.costex_de_more_et_detail);
		detail.setText(remark);
	    iv_back  =(ImageView)this.findViewById(R.id.ll_top_title);
	    iv_back.setOnClickListener(listener);
	    tv_top_title = (TextView) this.findViewById(R.id.tv_top_title);
	    tv_top_title.setText("费用明细单项开支");
	
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
	private void initData() {
	      Bundle bundle = getIntent().getExtras();
	      amount =  bundle.getString("AMOUNT");            
	      remark =  bundle.getString("REMARK");            
	       feeitemname = bundle.getString("FEEITEMNAME");            
	       worktaskname = bundle.getString("WORKTASKNAME");            
	}
       
}
