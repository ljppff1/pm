package com.superdata.pm.activity.homepage.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.util.IntentUtils;

/**
 * 首页-->站内消息
 * @author kw
 *
 */
public class CompanyNewsActivity extends BaseActivity {

	private ImageView back;
	private TextView top_title;
	private GridView gv_news;
	
	List<HashMap<String, Object>> gridViewList = null;
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companynews);
		
		gv_news = (GridView) findViewById(R.id.gv_news);
		init();
		initData();
		adapter = new MyAdapter();
		gv_news.setAdapter(adapter);
		gv_news.setOnItemClickListener(new MyOnItemClickListener());
	}
	
	public void init(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		top_title.setText("站内消息");
		
		back = (ImageView) findViewById(R.id.ll_top_title);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
	}
	
	public void initData(){
		gridViewList = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "新消息");
		map.put("image", R.drawable.ic_writemessage);
		gridViewList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "收件箱");
		map.put("image", R.drawable.ic_inbox);
		gridViewList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "发件箱");
		map.put("image", R.drawable.ic_outbox);
		gridViewList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("title", "草稿箱");
		map.put("image", R.drawable.ic_draftsbox);
		gridViewList.add(map);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	
	private final class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				IntentUtils.gotoActivity(CompanyNewsActivity.this, CompanyNewMessageActivity.class);
				break;
			case 1:
				IntentUtils.gotoActivity(CompanyNewsActivity.this, CompanyNewsInboxListActivity.class);
				break;
			case 2:
				IntentUtils.gotoActivity(CompanyNewsActivity.this, CompanyNewsOutboxListActivity.class);
				break;
			case 3:
				IntentUtils.gotoActivity(CompanyNewsActivity.this, CompanyNewsDraftsListActivity.class);
				break;

			default:
				break;
			}
			
		}
		
	} 
	
	
	class MyAdapter extends BaseAdapter{

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
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.homepage_item, null);
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
		
		
		class Holder{
			TextView textView;
			ImageView imageView;
		}
		
	}
	
	/*//点击编辑新消息的方法
	public void newMessage(View v){
		IntentUtils.gotoActivity(this, CompanyNewMessageActivity.class);
	}
	
	//点击进入收件箱的方法
	public void inbox(View v){
		IntentUtils.gotoActivity(this, CompanyNewsInboxActivity.class);
	}
	
	//点击进入发件箱的方法
	public void outbox(View v){
		IntentUtils.gotoActivity(this, CompanyNewsOutBoxActivity.class);
	}
	
	//点击进入草稿箱的方法
	public void drafts(View v){
		IntentUtils.gotoActivity(this, CompanyNewsDraftsActivity.class);
	}*/
	
	
}
