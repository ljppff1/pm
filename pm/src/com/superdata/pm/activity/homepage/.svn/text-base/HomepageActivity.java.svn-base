package com.superdata.pm.activity.homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.homepage.bbs.BBSActivity;
import com.superdata.pm.activity.homepage.news.CompanyNewsActivity;
import com.superdata.pm.activity.login.LoginActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.NetCheckUtil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 首页类
 * @author kw
 *
 */
public class HomepageActivity extends BaseActivity {
	
	private GridView gv_homepage;
	private HomepageAdapter homepageAdapter;
	List<HashMap<String, Object>> gridViewList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		
		gv_homepage = (GridView) findViewById(R.id.home_gridView);
		gv_homepage.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消点击效果
		//初始化数据
		init();
		
		homepageAdapter = new HomepageAdapter();
		gv_homepage.setAdapter(homepageAdapter);
		gv_homepage.setOnItemClickListener(new MyOnItemClickListener());
	}
	
	//初始化数据的方法
	public void init(){
		gridViewList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "企业论坛");
		map.put("image", R.drawable.ic_bbs);
		gridViewList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "站内消息");
		map.put("image", R.drawable.ic_news);
		gridViewList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "我的手机");
		map.put("image", R.drawable.myphone_icon);
		gridViewList.add(map);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//通知刷新GirdView
		homepageAdapter.notifyDataSetChanged();
	}
	
	//gridView的监听事件
	private final class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0: //跳到论坛页面
				if(!NetCheckUtil.isNetworkAvailable(HomepageActivity.this)){
					Toast.makeText(HomepageActivity.this, "网络连接断开，请重新登录！",
							Toast.LENGTH_SHORT).show();
					IntentUtils.gotoActivity(HomepageActivity.this, LoginActivity.class);
					return;
				}
				IntentUtils.gotoActivity(HomepageActivity.this, BBSActivity.class);
				break;

			case 1: //跳到消息页面
				IntentUtils.gotoActivity(HomepageActivity.this, CompanyNewsActivity.class);
				break;
				
			case 2://跳到我的手机
				IntentUtils.gotoActivity(HomepageActivity.this, MyPhoneAppActivity.class);
				break;
			}
			
		}
		
	}
	
	/**
	 * 自定义适配器
	 * @author kw
	 *
	 */
	class HomepageAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return gridViewList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return gridViewList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if(convertView == null){
				holder = new Holder();
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.homepage_item, null);
				holder.textView = (TextView) convertView.findViewById(R.id.tv_main_item_option);
				holder.imageView = (ImageView) convertView.findViewById(R.id.iv_home_gv_item);
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			holder.textView.setText(gridViewList.get(position).get("title").toString());
			holder.imageView.setImageResource((Integer)gridViewList.get(position).get("image"));
		   
			return convertView;
		}
		
		/**
		 * 用来装组件类
		 * @author kw
		 *
		 */
		class Holder{
			TextView textView;
			ImageView imageView;
		}
		
		
	}
  
}
