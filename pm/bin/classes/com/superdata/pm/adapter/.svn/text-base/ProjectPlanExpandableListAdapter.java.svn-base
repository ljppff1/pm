package com.superdata.pm.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suda.pm.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ProjectPlanExpandableListAdapter extends BaseExpandableListAdapter {

	class ExpandableGroupHolder {
		TextView package_name; //工作包名称
		TextView package_startdata; //工作包开始时间
		TextView package_enddata; //工作包结束时间
		TextView package_duration; //工作包持续时间
		TextView package_person; //工作包负责人
	}
	
	class ExpandableChildenHolder {
		TextView task_name; //任务名称
		TextView task_startdata; //任务开始时间
		TextView task_enddata; //任务结束时间
		TextView task_duration; //任务持续时间
		TextView task_person; //任务负责人
	}
	
	private Context context;
	private List<HashMap<String, Object>> groupData;//组显示  
	private List<List<HashMap<String, Object>>> childData;//子列表  
	private LayoutInflater mGroupInflater; //用于加载group的布局xml  
	private LayoutInflater mChildInflater; //用于加载listitem的布局xml  

	
	public ProjectPlanExpandableListAdapter(Context context,
			List<HashMap<String, Object>> groupData,
			List<List<HashMap<String, Object>>> childData) {
		this.groupData = groupData;
		this.childData = childData;
		mChildInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGroupInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ExpandableChildenHolder holder = null;
		if(convertView == null){
			convertView = mChildInflater.inflate(R.layout.projectplan_task_item, null);
			holder = new ExpandableChildenHolder();
			
			holder.task_name = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_name);
			holder.task_startdata = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_startdata);
			holder.task_enddata = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_enddata);
			holder.task_duration = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_duration);
			holder.task_person = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_person);
			
			convertView.setTag(holder);
			
		}else{
			holder = (ExpandableChildenHolder) convertView.getTag();
		}
		
		HashMap<String,Object> unitData=this.childData.get(groupPosition).get(childPosition);  
		holder.task_name.setText((String)unitData.get("tName"));
		holder.task_startdata.setText((String)unitData.get("tStartData"));
		holder.task_enddata.setText((String)unitData.get("tEndData"));
		holder.task_duration.setText((String)unitData.get("tDuration"));
		holder.task_person.setText((String)unitData.get("tPerson"));
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childData.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ExpandableGroupHolder holder = null;
		if(convertView == null){
			holder = new ExpandableGroupHolder(); 
			convertView = mGroupInflater.inflate(R.layout.projectplan_package_item, null);
			
			holder.package_name = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_package_name);
			holder.package_startdata = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_package_startdata);
			holder.package_enddata = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_package_enddata);
			holder.package_duration = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_package_duration);
			holder.package_person = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_package_person);
			
			convertView.setTag(holder);
		}else{
			holder = (ExpandableGroupHolder) convertView.getTag();  
		}
		String gName = (String) this.groupData.get(groupPosition).get("pName");
		String gStratData = (String) this.groupData.get(groupPosition).get("pStartData");
		String gEndData = (String) this.groupData.get(groupPosition).get("pEndData");
		String gDuration = (String) this.groupData.get(groupPosition).get("pDuration");
		String gPerson = (String) this.groupData.get(groupPosition).get("pPerson");
		
		holder.package_name.setText(gName);
		holder.package_startdata.setText(gStratData);
		holder.package_enddata.setText(gEndData);
		holder.package_duration.setText(gDuration);
		holder.package_person.setText(gPerson);
		
//		notifyDataSetChanged();
		return convertView;
	}

	@Override
	//行是否具有唯一id 
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	//行是否可选
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
