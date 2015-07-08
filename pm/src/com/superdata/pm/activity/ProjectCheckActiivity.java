package com.superdata.pm.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.suda.pm.ui.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 项目验收类
 * 
 * @author kw
 * 
 */
public class ProjectCheckActiivity extends Activity {

	private GridView gridView;
	private MyAdapter myAdapter;
	List<HashMap<String, Object>> gridViewList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		gridView = (GridView) findViewById(R.id.home_gridView);
		// 初始化数据
		init();
		myAdapter = new MyAdapter();
		gridView.setAdapter(myAdapter);
		gridView.setOnItemClickListener(new MyOnItemClickListener());
	}

	// 初始化数据的方法
	public void init() {
		gridViewList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "项目验收报告");
		map.put("image", R.drawable.all_menu);
		gridViewList.add(map);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//通知刷新GirdView
		myAdapter.notifyDataSetChanged();
	}
	
	//gridView的监听事件
	private final class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	/**
	 * 自定义适配器
	 * @author kw
	 *
	 */
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return gridViewList.size();
		}

		@Override
		public Object getItem(int position) {
			return gridViewList.get(position);
		}

		@Override
		public long getItemId(int position) {
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
