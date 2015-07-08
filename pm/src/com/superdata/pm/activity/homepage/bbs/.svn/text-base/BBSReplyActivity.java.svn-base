package com.superdata.pm.activity.homepage.bbs;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
/**
 * 帖子回复
 * @author kw
 *
 */
public class BBSReplyActivity extends BaseActivity {

	private View back;
	private TextView top_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bbs_reply);
		
		init();//初始化
		
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
		top_title.setText("帖子回复");
	}
	
}
