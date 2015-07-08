package com.superdata.pm.activity.homepage.bbs;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 首页-->企业论坛-->修改主题
 * @author kw
 *
 */
public class BBSModifyTitleActivity extends BaseActivity {
	
	private TextView top_title;
	private ImageView back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bbs_modify);
		
		init();
		
		back = (ImageView) findViewById(R.id.ll_top_title);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	
	//初始化方法
	public void init(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		top_title.setText("修改主题");
	}

}
