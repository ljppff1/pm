package com.superdata.pm.activity;

import com.suda.pm.ui.R;
import com.superdata.pm.util.IntentUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 个人资料修改的类
 * @author kw
 *
 */
public class UserinfoSettingActivity extends Activity {

	private TextView title;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo_setting);
		
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("个人资料修改");
		layout= (LinearLayout) findViewById(R.id.title_back);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IntentUtils.gotoActivity(UserinfoSettingActivity.this, SettingsActivity.class);
				UserinfoSettingActivity.this.finish();
			}
		});
	}
	
	
}
