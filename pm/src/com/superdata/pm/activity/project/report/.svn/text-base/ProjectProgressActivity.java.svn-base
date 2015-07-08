package com.superdata.pm.activity.project.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.adapter.ProjectProgressExpandableListAdapter;
import com.superdata.pm.common.BaseActivity;



/**
 * 项目-->项目进度
 * 
 * @author kw
 * 
 */
public class ProjectProgressActivity extends BaseActivity {

	private ImageView back;
	private TextView top_title;
	private ProjectProgressExpandableListAdapter adapter = null;
	
//	List<HashMap<String, Object>> groups;
//	List<List<HashMap<String, Object>>> childs;
//	ExpandableListView expandableListView;
	
	private List<HashMap<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectprogress);
		
		init();
		
		//点击返回
		back = (ImageView) findViewById(R.id.ll_top_title);
		back.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	
	
	public void init(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		top_title.setText("项目进度");
		
		/*//创建一级条目
		groups = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String, Object> group1 = new HashMap<String, Object>();
		group1.put("pName", "工作包1");
		group1.put("pStartData", "2014-01-01");
		group1.put("pEndData", "2014-02-01");
		group1.put("pDuration", "30天");
		group1.put("pPerson", "王五");
		group1.put("pEstimateprogress", "80%");
		group1.put("pExamineprogress", "50%");
		group1.put("pFillprogress", "70%");
		group1.put("pFilltime", "2014-01-15");
		
		HashMap<String, Object> group2 = new HashMap<String, Object>();
		group2.put("pName", "工作包2");
		group2.put("pStartData", "2014-01-01");
		group2.put("pEndData", "2014-02-01");
		group2.put("pDuration", "32天");
		group2.put("pPerson", "李四");
		group2.put("pEstimateprogress", "80%");
		group2.put("pExamineprogress", "56%");
		group2.put("pFillprogress", "75%");
		group2.put("pFilltime", "2014-01-12");
		
		HashMap<String, Object> group3 = new HashMap<String, Object>();
		group3.put("pName", "工作包3");
		group3.put("pStartData", "2014-01-01");
		group3.put("pEndData", "2014-03-01");
		group3.put("pDuration", "55天");
		group3.put("pPerson", "李四");
		group3.put("pEstimateprogress", "80%");
		group3.put("pExamineprogress", "60%");
		group3.put("pFillprogress", "75%");
		group3.put("pFilltime", "2014-01-20");
		
		HashMap<String, Object> group4 = new HashMap<String, Object>();
		group4.put("pName", "工作包4");
		group4.put("pStartData", "2014-01-01");
		group4.put("pEndData", "2014-02-01");
		group4.put("pDuration", "32天");
		group4.put("pPerson", "张三");
		group4.put("pEstimateprogress", "70%");
		group4.put("pExamineprogress", "40%");
		group4.put("pFillprogress", "40%");
		group4.put("pFilltime", "2014-01-12");
		
		groups.add(group1);
		groups.add(group2);
		groups.add(group3);
		groups.add(group4);
		
		// 创建一级条目下的的二级条目
        List<HashMap<String, Object>> child1 = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> childdata1 = new HashMap<String, Object>();
        childdata1.put("tName", "任务1-1");
        childdata1.put("tStartData", "2014-01-01");
        childdata1.put("tEndData", "2014-01-15");
        childdata1.put("tDuration", "15天");
        childdata1.put("tPerson", "张三");
        childdata1.put("pEstimateprogress", "60%");
        childdata1.put("pExamineprogress", "45%");
        childdata1.put("pFillprogress", "30%");
        childdata1.put("pFilltime", "2014-01-10");
        
        HashMap<String, Object> childdata2 = new HashMap<String, Object>();
        childdata2.put("tName", "任务1-2");
        childdata2.put("tStartData", "2014-01-01");
        childdata2.put("tEndData", "2014-01-16");
        childdata2.put("tDuration", "10天");
        childdata2.put("tPerson", "赵六");
        childdata2.put("pEstimateprogress", "80%");
        childdata2.put("pExamineprogress", "60%");
        childdata2.put("pFillprogress", "32%");
        childdata2.put("pFilltime", "2014-01-18");
        
        child1.add(childdata1);
        child1.add(childdata2);
        
        List<HashMap<String, Object>> child2 = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> childdata3 = new HashMap<String, Object>();
        childdata3.put("tName", "任务2-1");
        childdata3.put("tStartData", "2014-01-01");
        childdata3.put("tEndData", "2014-02-15");
        childdata3.put("tDuration", "25天");
        childdata3.put("tPerson", "李四");
        childdata3.put("pEstimateprogress", "50%");
        childdata3.put("pExamineprogress", "45%");
        childdata3.put("pFillprogress", "30%");
        childdata3.put("pFilltime", "2014-01-10");
		
        child2.add(childdata3);
        
        List<HashMap<String, Object>> child3 = new ArrayList<HashMap<String, Object>>();
        child3.add(childdata2);
        
        List<HashMap<String, Object>> child4 = new ArrayList<HashMap<String, Object>>();
        child4.add(childdata2);
        child4.add(childdata3);
        
        childs = new ArrayList<List<HashMap<String,Object>>>();
        childs.add(child1);
        childs.add(child2);
        childs.add(child3);
        childs.add(child4);
        
        // 实例化ExpandableListView对象
        expandableListView = (ExpandableListView) findViewById(R.id.elv_projectprogress);
        // 实例化ExpandableListView的适配器  
        adapter = new ProjectProgressExpandableListAdapter(getApplicationContext(), groups, childs);
        expandableListView.setAdapter(adapter);
        //子条目点击
        expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				return false;
			}
		});*/
	}
	
	class MyAdapter extends BaseAdapter{

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
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if(convertView == null){
				holder = new Holder();
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.projectprogress_package_item, null);
				
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
				holder  = (Holder) convertView.getTag();
				
			}
			HashMap<String, Object> map = list.get(position);
			
			holder.package_name.setText(map.get("name").toString());
			holder.package_startdata.setText(map.get("name").toString());
			holder.package_enddata.setText(map.get("name").toString());
			holder.package_duration.setText(map.get("name").toString());
			holder.package_person.setText(map.get("name").toString());
			holder.package_estimateprogress.setText(map.get("name").toString());
			holder.package_examineprogress.setText(map.get("name").toString());
			holder.package_fillprogress.setText(map.get("name").toString());
			holder.package_filltime.setText(map.get("name").toString());
			
			return null;
		}
		
		
		class Holder{
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
		
	}

}
