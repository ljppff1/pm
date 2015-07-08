package com.superdata.pm.activity.project.report;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;

/**
 * 项目--》项目管理--》项目详细--》项目计划（工作包）--》项目计划（任务）--》填报进度
 * @author kw
 *
 */
public class FillProgressActivity extends BaseActivity {

	private ImageView back;
	private TextView top_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectprogress_fill);
		
		init();//初始化
		
		
		back = (ImageView) findViewById(R.id.ll_top_title);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 回退
				onBackPressed();
			}
		});
		
	}
	
	//初始化方法
	public void init(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		top_title.setText("进度填报");
	}
}
