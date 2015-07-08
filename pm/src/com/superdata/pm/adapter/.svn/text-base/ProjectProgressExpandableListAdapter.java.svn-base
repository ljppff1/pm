package com.superdata.pm.adapter;

import java.util.HashMap;
import java.util.List;

import com.suda.pm.ui.R;
import com.superdata.pm.adapter.ProjectPlanExpandableListAdapter.ExpandableChildenHolder;
import com.superdata.pm.adapter.ProjectPlanExpandableListAdapter.ExpandableGroupHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ProjectProgressExpandableListAdapter extends
		BaseExpandableListAdapter {
	
	private Context context;
	private List<HashMap<String, Object>> groupData;//组显示  
	private List<List<HashMap<String, Object>>> childData;//子列表  
	private LayoutInflater mGroupInflater; //用于加载group的布局xml  
	private LayoutInflater mChildInflater; //用于加载listitem的布局xml
	
	
	class ExpandableGroupHolder{
		TextView package_name; //工作包名称
		TextView package_startdata; //工作包开始时间
		TextView package_enddata; //工作包结束时间
		TextView package_duration; //工作包持续时间
		TextView package_person; //工作包负责人
		TextView package_estimateprogress;//工作包预计进度
		TextView package_examineprogress;//工作包已审进度
		TextView package_fillprogress;//工作包填报进度
		TextView package_filltime;//工作包填报日期
	}
	
	class ExpandableChildenHolder {
		TextView task_name; //任务名称
		TextView task_startdata; //任务开始时间
		TextView task_enddata; //任务结束时间
		TextView task_duration; //任务持续时间
		TextView task_person; //任务负责人
		TextView task_estimateprogress;//任务预计进度
		TextView task_examineprogress;//任务已审进度
		TextView task_fillprogress;//任务填报进度
		TextView task_filltime;//任务填报日期
	}
	
	

	public ProjectProgressExpandableListAdapter(Context context,
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
			convertView = mChildInflater.inflate(R.layout.projectprogress_task_item, null);
			holder = new ExpandableChildenHolder();
			
			holder.task_name = (TextView) convertView.findViewById(R.id.tv_projectprogress_task_name);
			holder.task_startdata = (TextView) convertView.findViewById(R.id.tv_projectprogress_task_startdata);
			holder.task_enddata = (TextView) convertView.findViewById(R.id.tv_projectprogress_task_enddata);
			holder.task_duration = (TextView) convertView.findViewById(R.id.tv_projectprogress_task_duration);
			holder.task_person = (TextView) convertView.findViewById(R.id.tv_projectprogress_task_person);
			holder.task_estimateprogress = (TextView) convertView.findViewById(R.id.tv_projectprogress_task_estimateprogress);
			holder.task_examineprogress = (TextView) convertView.findViewById(R.id.tv_projectprogress_task_examineprogress);
			holder.task_fillprogress = (TextView) convertView.findViewById(R.id.tv_projectprogress_task_fillprogress);
			holder.task_filltime = (TextView) convertView.findViewById(R.id.tv_projectprogress_task_filltime);
			
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
		holder.task_estimateprogress.setText((String)unitData.get("tEstimateprogress"));
		holder.task_examineprogress.setText((String)unitData.get("tExamineprogress"));
		holder.task_fillprogress.setText((String)unitData.get("tFillprogress"));
		holder.task_filltime.setText((String)unitData.get("tFilltime"));
		
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
			convertView = mGroupInflater.inflate(R.layout.projectprogress_package_item, null);
			
			holder.package_name = (TextView) convertView.findViewById(R.id.tv_projectprogress_package_name);
			holder.package_startdata = (TextView) convertView.findViewById(R.id.tv_projectprogress_package_startdata);
			holder.package_enddata = (TextView) convertView.findViewById(R.id.tv_projectprogress_package_enddata);
			holder.package_duration = (TextView) convertView.findViewById(R.id.tv_projectprogress_package_duration);
			holder.package_person = (TextView) convertView.findViewById(R.id.tv_projectprogress_package_person);
			holder.package_estimateprogress = (TextView) convertView.findViewById(R.id.tv_projectprogress_package_estimateprogress);
			holder.package_examineprogress = (TextView) convertView.findViewById(R.id.tv_projectprogress_package_examineprogress);
			holder.package_fillprogress = (TextView) convertView.findViewById(R.id.tv_projectprogress_package_fillprogress);
			holder.package_filltime = (TextView) convertView.findViewById(R.id.tv_projectprogress_package_filltime);
			
			convertView.setTag(holder);
		}else{
			holder = (ExpandableGroupHolder) convertView.getTag();
		}
		
		String gName = (String) this.groupData.get(groupPosition).get("pName");
		String gStratData = (String) this.groupData.get(groupPosition).get("pStratData");
		String gEndData = (String) this.groupData.get(groupPosition).get("pEndData");
		String gDuration = (String) this.groupData.get(groupPosition).get("pDuration");
		String gPerson = (String) this.groupData.get(groupPosition).get("pPerson");
		String gEstimateprogress = (String) this.groupData.get(groupPosition).get("pEstimateprogress");
		String gExamineprogress = (String) this.groupData.get(groupPosition).get("pExamineprogress");
		String gFillprogress = (String) this.groupData.get(groupPosition).get("pFillprogress");
		String gFilltime = (String) this.groupData.get(groupPosition).get("pFilltime");
		
		holder.package_name.setText(gName);
		holder.package_startdata.setText(gStratData);
		holder.package_enddata.setText(gEndData);
		holder.package_duration.setText(gDuration);
		holder.package_person.setText(gPerson);
		holder.package_estimateprogress.setText(gEstimateprogress);
		holder.package_examineprogress.setText(gExamineprogress);
		holder.package_fillprogress.setText(gFillprogress);
		holder.package_filltime.setText(gFilltime);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
