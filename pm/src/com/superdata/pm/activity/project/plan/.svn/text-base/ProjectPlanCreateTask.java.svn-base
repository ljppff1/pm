package com.superdata.pm.activity.project.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;

/**
 * 项目-->项目计划-->创建任务
 * @author kw
 *
 */
public class ProjectPlanCreateTask extends BaseActivity {

	private ImageView back;
	private TextView top_title;
	
	private List<HashMap<String, Object>> list;
	private MemberAdapter adapter;
	private ListView lv_projectplan_memberlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectplan_addtask);
		
		init();//初始化
		
		//点击返回
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
		top_title.setText("创建任务");
		
		list = new ArrayList<HashMap<String,Object>>();
		for(int i=0; i<50; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("number", "000"+i);
			map.put("name", "张三"+i);
			map.put("department", "研发部"+i);
			map.put("position", "开发人员"+i);
			list.add(map);
		}
		
		lv_projectplan_memberlist = (ListView) findViewById(R.id.lv_projectplan_memberlist);
		setListViewHeightBasedOnChildren(lv_projectplan_memberlist);
		
		adapter = new MemberAdapter(this);
		lv_projectplan_memberlist.setAdapter(adapter);
	}
	
	
	//算出listview所有item的高度设置给listview
	public void setListViewHeightBasedOnChildren(ListView listView){
		ListAdapter listAdapter = listView.getAdapter();
		if(listAdapter == null){
			return;
		}
		
		int totalHeight = 0;
		for(int i = 0; i<listAdapter.getCount(); i++){
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		params.height += 5;
		listView.setLayoutParams(params);
	}
	
	
	//点击添加成员
	public void addMember(View v){
		int i = 2;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("number", "000"+i);
		map.put("name", "王五"+i);
		map.put("department", "研发部"+i);
		map.put("position", "项目经理"+i);
		list.add(map);
		
		adapter.notifyDataSetChanged();
	}
	
	//listview中点击按键弹出对话框 
	public void showInfo(final int position){
		
		new AlertDialog.Builder(this).setTitle("提示")
		.setMessage("确定删除该成员？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 确定就删除
				list.remove(position);
				lv_projectplan_memberlist.setAdapter(adapter);
			}
		}).setNegativeButton("取消", null).show();
	}
	
	
	class MemberAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		private Context context;
		
		public MemberAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if(convertView == null){
				holder = new Holder();
				convertView = mInflater.inflate(R.layout.projectplan_addtask_member, null);
				holder.tv_addtask_member_membernumber = (TextView) convertView.findViewById(R.id.tv_addtask_member_membernumber);
				holder.tv_addtask_member_membername = (TextView) convertView.findViewById(R.id.tv_addtask_member_membername);
				holder.tv_addtask_member_department = (TextView) convertView.findViewById(R.id.tv_addtask_member_department);
				holder.tv_addtask_member_memberposition = (TextView) convertView.findViewById(R.id.tv_addtask_member_memberposition);
				holder.btn_addtask_member_delete = (Button) convertView.findViewById(R.id.btn_addtask_member_delete);
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_addtask_member_membernumber.setText((String)list.get(position).get("number"));
			holder.tv_addtask_member_membername.setText((String)list.get(position).get("name"));
			holder.tv_addtask_member_department.setText((String)list.get(position).get("department"));
			holder.tv_addtask_member_memberposition.setText((String)list.get(position).get("position"));
			holder.btn_addtask_member_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showInfo(position);
				}
			});
			
			return convertView;
		}
		
		class Holder{
			TextView tv_addtask_member_membernumber;
			TextView tv_addtask_member_membername;
			TextView tv_addtask_member_department;
			TextView tv_addtask_member_memberposition;
			Button btn_addtask_member_delete;
			
		}
		
	}
}
