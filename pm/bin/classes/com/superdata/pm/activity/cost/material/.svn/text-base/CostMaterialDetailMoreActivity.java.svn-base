package com.superdata.pm.activity.cost.material;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;

public class CostMaterialDetailMoreActivity extends BaseActivity{
	private String amount;
	private String remark;
	private String feeitemname;
	private String worktaskname;
	private String count;

	//材料名称
	private TextView mat_name;
	//单价
	private TextView mat_money;
	//数量
	private TextView mat_count;
	private ImageView iv_back;
	private TextView tv_top_title;
	private TextView mat_task;
	private TextView mat_remark;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.cost_material_detail_more);
    	
    	initData();
    	initView();
    	
    	
    	
    }

	private void initView() {
		mat_name  =(TextView)this.findViewById(R.id.costmat_de_more_tv_value);
		mat_name.setText(feeitemname);
		mat_task =(TextView)this.findViewById(R.id.costmat_de_more_tv_task);
		mat_task.setText(worktaskname);
		mat_money =(TextView)this.findViewById(R.id.costmat_de_more_et_money);
		mat_money.setText(amount);
		mat_count =(TextView)this.findViewById(R.id.costmat_de_more_et_count);
		mat_count.setText(count);
		mat_remark =(TextView)this.findViewById(R.id.costmat_de_more_et_detail);
		mat_remark.setText(remark);
	    iv_back  =(ImageView)this.findViewById(R.id.ll_top_title);
	    iv_back.setOnClickListener(listener);
	    tv_top_title = (TextView) this.findViewById(R.id.tv_top_title);
	    tv_top_title.setText("材料开支详情单");
	    
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
      Bundle bundle = this.getIntent().getExtras();
      amount =   bundle.get("PRICE")+"";
      remark =   bundle.get("REMARK")+"";
      feeitemname =   bundle.get("FEEITEMNAME")+"";
      worktaskname =   bundle.get("WORKTASKNAME")+"";
      count  =   bundle.get("COUNT")+"";
    

	
	
	}
       
}
