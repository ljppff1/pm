package com.superdata.pm.activity.cost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.cost.expenses.CostExpenseProjectActivity;
import com.superdata.pm.activity.cost.expenses.CostExpensesActivity;
import com.superdata.pm.activity.cost.job.CostJobActivity;
import com.superdata.pm.activity.cost.job.CostJobProjectActivity;
import com.superdata.pm.activity.cost.material.CostMaterialActivity;
import com.superdata.pm.activity.cost.material.CostMaterialProjectActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
 * 成本类
 * 
 * @author kw
 * 
 */
public class CostsActivity extends Activity {

	private GridView gridView;
	private MyAdapter myAdapter;
	List<HashMap<String, Object>> gridViewList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		gridView = (GridView) findViewById(R.id.home_gridView);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消点击效果
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
		map.put("title", "费用管理");
		map.put("image", R.drawable.ic_fee);
		gridViewList.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "材料管理");
		map.put("image", R.drawable.ic_material);
		gridViewList.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "职位管理");
		map.put("image", R.drawable.ic_positionmanager);
		gridViewList.add(map);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 通知刷新GirdView
		myAdapter.notifyDataSetChanged();
	}

	// gridView的监听事件
	private final class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
			startActivity(new Intent(CostsActivity.this, CostExpenseProjectActivity.class));
				break;
			case 1:
				startActivity(new Intent(CostsActivity.this, CostMaterialProjectActivity.class));
	
				break;
			case 2:
				startActivity(new Intent(CostsActivity.this, CostJobProjectActivity.class));

				break;
			}

		}
	}
	
	
	/**
	 * 自定义适配器
	 * @author kw 
	 *
	 */
	private class MyAdapter extends BaseAdapter {

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
