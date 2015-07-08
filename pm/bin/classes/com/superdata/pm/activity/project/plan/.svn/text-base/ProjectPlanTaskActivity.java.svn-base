package com.superdata.pm.activity.project.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.project.contract.ProjectContractActivity;
import com.superdata.pm.activity.project.manager.detail.comment.ProjectCommentActivity;
import com.superdata.pm.activity.project.manager.detail.log.ProjectLogActivity;
import com.superdata.pm.activity.project.member.ProjectMemberActivity;
import com.superdata.pm.activity.project.report.CheckProgressActivity;
import com.superdata.pm.activity.project.report.CheckReportActivity;
import com.superdata.pm.activity.project.report.FillProgressActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.entityplanpackage;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.view.XListView;

/**
 * 项目--》项目管理--》项目详细--》项目计划（工作包）--》项目计划（任务）
 * @author kw
 *
 */
public class ProjectPlanTaskActivity extends BaseActivity  implements
com.superdata.pm.view.XListView.IXListViewListener{

	   //返回按钮
		private ImageView back;
		//费用管理条目
		private XListView listView;
		//获取的是第几页的数据
	    private int page =1;
	    //获取的这页的数量
	    private int pagesize =8;
	    //Adapter里面放的List，
	    private List<entityplanpackage> listplanpack =new ArrayList<entityplanpackage>();
	    //临时的List，用于加载更多 的逻辑处理
	    private List<entityplanpackage> listplanpack1 =new ArrayList<entityplanpackage>();
		private com.superdata.pm.view.SDProgressDialog sdDialog;
		//标识是否加载更多 
	    private boolean flag =true; 
		private MyAdapter adapter;
		private boolean isLastPage=false;

	    private String packageId;
	    private String projectId;
	    private String projectName;
	    //用于判断是否修改了值
	    private boolean flag2=true;
	    private int position_value;
	    private int showToast =0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectplan_task);
		packageId =String.valueOf(getIntent().getExtras().get("PACKAGEID"));
		projectId =String.valueOf(getIntent().getExtras().get("PROJECTID"));
		projectName =String.valueOf(getIntent().getExtras().get("PROJECTNAME"));
		init();//初始化
		initdata();
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
	
	
	@Override
	protected void onResume() {
		
		super.onResume();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode!=0){
		switch (resultCode) {
		// input
		case 1:
			showToast = 1;
			break;
		case 2:
			showToast = 2;
			break;
		case 3:
			showToast = 3;
			break;

		default:
			break;
		}
		position_value = Integer.valueOf(data.getExtras().getString("POSITION"));
		flag2 = false;
		initdata(projectId, (position_value + 1) + "", 1 + "", 0 + "",packageId + "");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private Handler handler =new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				listView.setAdapter(adapter);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
			
				break;
			case 2:
				flag =true;
			    listView.setAdapter(adapter);
			    listView.setSelection(listplanpack.size()-pagesize);
				adapter.notifyDataSetChanged();// 数据变化刷新
				if(sdDialog.isShow())
				sdDialog.cancel();
				onLoad();
				break;
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(), "已全部加载完成", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				listView.setAdapter(adapter);
				listView.setSelection(position_value);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				switch (showToast) {
				//input
				case 1:
					Toast.makeText(ProjectPlanTaskActivity.this, "审核成功",Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(ProjectPlanTaskActivity.this, "填报成功",
							Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(ProjectPlanTaskActivity.this, "反审核成功",
							Toast.LENGTH_SHORT).show();
	                 break;
	                
				default:
					break;
				}

				
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	private void initdata() {
		initdata(projectId, page+"", pagesize+"",0+"",packageId+"");	
	}
	private void initdata(String projectId,String page,String size,String kind,String parentId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", page));
		params.add(new BasicNameValuePair("pageSize",  size));
		params.add(new BasicNameValuePair("kind",  kind));
		params.add(new BasicNameValuePair("parentId",parentId));
		new MyTask().execute(params);
	}
    // http://localhost:8080/pm/taskInterface/queryTask.do
//	String url ="http://192.168.0.117:8080/pm/taskInterface/queryTask.do";
	String url =InterfaceConfig.PROJECT_Plan_Task_URL;
	private TextView top_title;
	
	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		@Override
        protected void onPreExecute() {
     		if(page==1){
        	 sdDialog = new com.superdata.pm.view.SDProgressDialog(ProjectPlanTaskActivity.this);
    	 	sdDialog.show();
     		}
        	super.onPreExecute();
        }
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
		 String aa = null;
		try {
			aa = new SDHttpClient().post_session(url, params[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return aa;
		}
		
		@Override
		protected void onPostExecute(String result) {
			 if(result!=null){
					try {
						JSONObject jRoot = new JSONObject(result);
						JSONObject jaresultList1 =jRoot.getJSONObject("resultData");
						JSONArray jaresultList =jaresultList1.getJSONArray("resultList");
						if(flag2){
					    	int totalSize =jaresultList1.getInt("totalSize");
					    	if(totalSize>=page*pagesize){
				       		isLastPage =false;
					    	}else{
					    		isLastPage =true;
					    	}
						}
						listplanpack1.clear();
						for(int i=0;i<jaresultList.length();i++){
				            String name =(jaresultList.getJSONObject(i).get("name")).equals(null)?"姓名没有"+i:(String) jaresultList.getJSONObject(i).get("name");
				            String startDate = (jaresultList.getJSONObject(i).get("startDate")).equals(null)?"2014-01-01":((String) jaresultList.getJSONObject(i).get("startDate")).substring(0, 10);
				            String endDate =(jaresultList.getJSONObject(i).get("endDate")).equals(null)?"2014-03-03":((String) jaresultList.getJSONObject(i).get("endDate")).substring(0,10);			             
				            String packageheader =((jaresultList.getJSONObject(i).get("empName")).equals(null))?"负责人没有":(String) jaresultList.getJSONObject(i).get("empName");
				            Integer id =(Integer) (((jaresultList.getJSONObject(i).get("id")).equals(null))?1: jaresultList.getJSONObject(i).get("id"));
				            String writeRate =  (jaresultList.getJSONObject(i).get("writeRate")).equals(null)?null:String.valueOf(jaresultList.getJSONObject(i).get("writeRate"));
				            String auditRate =  (jaresultList.getJSONObject(i).get("auditRate")).equals(null)?null:String.valueOf(jaresultList.getJSONObject(i).get("auditRate"));
				            String expectRate =  (jaresultList.getJSONObject(i).get("expectRate")).equals(null)?null:String.valueOf(jaresultList.getJSONObject(i).get("expectRate"));
				            String remark  =((jaresultList.getJSONObject(i).get("remark")).equals(null))?null:(String) jaresultList.getJSONObject(i).get("remark");
				            String scheduleId;
							String scheduleRemark;
							
						
							if(jaresultList.getJSONObject(i).get("taskSchedule").equals(null)){
				            	scheduleId ="lj";
				            	scheduleRemark="lj";
				            }else{
				             scheduleId =  (jaresultList.getJSONObject(i).getJSONObject("taskSchedule").get("id")).equals(null)?"1":String.valueOf(jaresultList.getJSONObject(i).getJSONObject("taskSchedule").get("id"));
					    	 scheduleRemark = (jaresultList.getJSONObject(i).getJSONObject("taskSchedule").get("remark")).equals(null)?"1":String.valueOf(jaresultList.getJSONObject(i).getJSONObject("taskSchedule").get("remark"));
				            }
     				    	String packageduration =  (jaresultList.getJSONObject(i).get("lastTime")).equals(null)?0+"":String.valueOf(jaresultList.getJSONObject(i).get("lastTime"))+"天";;
				            entityplanpackage epp=new entityplanpackage(name, startDate, endDate, packageheader, auditRate, expectRate, writeRate, id,scheduleId,packageduration,scheduleRemark,remark);
				            listplanpack1.add(epp);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
		     Message msg =new Message();
		     
		     //用于判断是否修改了值
		     if(flag2){
		    	 
		     //加载更多 时
		     if(!flag){
		    	 listplanpack.addAll(listplanpack1);
		    	 listplanpack1.clear();
				adapter = new MyAdapter();
		        msg.what =2;
		     }else{
		    	 //第一次
		    	 //重新加载全部的
			    	 listplanpack.clear();
			    	 listplanpack.addAll(listplanpack1);
					 adapter = new MyAdapter();
		           	 msg.what =1;
		     }
		     }else{
			    	 listplanpack.get(position_value).setAuditRate(listplanpack1.get(0).getAuditRate());
			    	 listplanpack.get(position_value).setWriteRate(listplanpack1.get(0).getWriteRate());		
			    	 listplanpack.get(position_value).setScheduleRemark(listplanpack1.get(0).getScheduleRemark());
			    	 listplanpack.get(position_value).setTaskRemark(listplanpack1.get(0).getTaskRemark());
			    	 listplanpack.get(position_value).setScheduleId(listplanpack1.get(0).getScheduleId());
		    	    flag2 =true;
		    	 adapter = new MyAdapter();
	           	 msg.what =4;
		     }
			     if(sdDialog.isShow())
				sdDialog.cancel();
			     handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	
		//初始化数据的方法
		public void init(){
			top_title = (TextView) findViewById(R.id.tv_top_title);
			top_title.setText("任务");
			listView = (com.superdata.pm.view.XListView) findViewById(R.id.lv_projectplan_task);// 初始化listview
			listView.setCacheColorHint(0);
			listView.setPullLoadEnable(true);
			listView.setXListViewListener(this);
			listView.setHeaderDividersEnabled(false);
			listView.setFooterDividersEnabled(false);
			listView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		   Intent  intent =new Intent(ProjectPlanTaskActivity.this, ProjectPlanTaskDetailsActivity.class);
           Bundle bundle =new Bundle();
           bundle.putString("EXPRATE",listplanpack.get(position-1).getExpectRate());
           bundle.putString("WRITERATE", listplanpack.get(position-1).getWriteRate());
           bundle.putString("REMARK",listplanpack.get(position-1).getScheduleRemark().equals("lj")?"":listplanpack.get(position-1).getScheduleRemark());
           bundle.putString("TASKREMARK",listplanpack.get(position-1).getTaskRemark());
           bundle.putString("AUDITRATE", listplanpack.get(position-1).getScheduleRemark().equals("lj")?"":listplanpack.get(position-1).getAuditRate());
           intent.putExtras(bundle);
           startActivity(intent);           
		}
			});
			}
	
	/**
	 * 自定义适配器
	 * @author Administrator
	 *
	 */
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listplanpack.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listplanpack.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView( final int position, View convertView, ViewGroup parent) {
			 Holder holder;
			if(convertView == null){
				holder = new Holder();
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.projectplan_task_item, null);
				holder.task_name = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_name);
				holder.task_startdata = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_startdata);
				holder.task_enddata = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_enddata);
				holder.task_duration = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_duration);
				holder.task_person = (TextView) convertView.findViewById(R.id.tv_cb_projectplan_task_person);
				holder.btn_projectplan_task_fillprogress = (Button) convertView.findViewById(R.id.btn_projectplan_task_fillprogress);
				holder.btn_projectplan_task_checkprogress = (Button) convertView.findViewById(R.id.btn_projectplan_task_checkprogress);
				holder.btn_projectplan_task_checkbackprogress =(Button)convertView.findViewById(R.id.btn_projectplan_task_checkbackprogress);
				holder.btn_projectplan_task_checkbackprogress.setTag(position);
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
	  String writerate =		listplanpack.get(position).getWriteRate();
	  String auditrate =	listplanpack.get(position).getAuditRate();
	  
	  if(listplanpack.get(position).getScheduleId().equals("lj")){
		  //只能填报
		  holder.btn_projectplan_task_fillprogress.setVisibility(View.VISIBLE);
		  holder.btn_projectplan_task_checkbackprogress.setVisibility(View.INVISIBLE);
		  holder.btn_projectplan_task_checkprogress.setVisibility(View.INVISIBLE);
	  }else{
	 //填报进度为null
	      if(writerate==null){
	    	  //审核进度也为null
	    	  if(auditrate==null){
	    		  //只能填报
	    		  holder.btn_projectplan_task_fillprogress.setVisibility(View.VISIBLE);
	    		  holder.btn_projectplan_task_checkbackprogress.setVisibility(View.INVISIBLE);
	    		  holder.btn_projectplan_task_checkprogress.setVisibility(View.INVISIBLE);

	    	  }else{
	    		  //能填报和反审核
	    		  holder.btn_projectplan_task_checkbackprogress.setVisibility(View.VISIBLE);
		    		  holder.btn_projectplan_task_fillprogress.setVisibility(View.VISIBLE);
		    		  holder.btn_projectplan_task_checkprogress.setVisibility(View.INVISIBLE);
	    	  }
	      }else{
	    	  //填报进度不为null，只有审核
    		  holder.btn_projectplan_task_checkprogress.setVisibility(View.VISIBLE);
    		  holder.btn_projectplan_task_fillprogress.setVisibility(View.INVISIBLE);
    		  holder.btn_projectplan_task_checkbackprogress.setVisibility(View.INVISIBLE);	   	  
	      }
	  }
			holder.task_name.setText(listplanpack.get(position).getPackagename());
			holder.task_startdata.setText(listplanpack.get(position).getPackagestartdata());
			holder.task_enddata.setText(listplanpack.get(position).getPackageenddata());
			holder.task_duration.setText(listplanpack.get(position).getPackageduration());
			holder.task_person.setText(listplanpack.get(position).getPackageheader());
			
			
	
			holder.btn_projectplan_task_fillprogress.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 点击跳转到填报进度的界面
					Intent intent =new Intent(ProjectPlanTaskActivity.this, WriteRateActivity.class);
                    intent.putExtra("ID", listplanpack.get(position).getId());
				    intent.putExtra("EXTRATE", listplanpack.get(position).getExpectRate());
                    intent.putExtra("POSITION", position+"");
                    startActivityForResult(intent, 1);
				}
			});
			
			holder.btn_projectplan_task_checkprogress.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 点击跳转到审核进度的界面
					Intent intent =new Intent(ProjectPlanTaskActivity.this, AuditRateActivity.class);
				    intent.putExtra("PROJECTID", projectId);
				    intent.putExtra("TASKID", listplanpack.get(position).getId());
				    intent.putExtra("SCHEDULEID", listplanpack.get(position).getScheduleId());
				    intent.putExtra("EXTRATE", listplanpack.get(position).getExpectRate());
				    intent.putExtra("INPUTRATE", listplanpack.get(position).getWriteRate());
			        intent.putExtra("REMARK",listplanpack.get(position).getScheduleRemark());
	                intent.putExtra("POSITION", position+"");
			        startActivityForResult(intent, 1);
				}
  			});
	     holder.btn_projectplan_task_checkbackprogress.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 点击跳转到反审核进度的界面
					Intent intent =new Intent(ProjectPlanTaskActivity.this, AuditBackRateActivity.class);
				    intent.putExtra("PROJECTID", projectId);
				    intent.putExtra("TASKID", listplanpack.get(position).getId());
				    intent.putExtra("SCHEDULEID", listplanpack.get(position).getScheduleId());
				    intent.putExtra("EXTRATE", listplanpack.get(position).getExpectRate());
				    intent.putExtra("AUDITRATE", listplanpack.get(position).getAuditRate());
			        intent.putExtra("REMARK",listplanpack.get(position).getScheduleRemark());
			        intent.putExtra("POSITION", position+"");
			        startActivityForResult(intent, 1);
				}
				
			});
	     return convertView;
		}
	
			
		
	}
		
		class Holder{
			TextView task_name; //任务名称
			TextView task_startdata; //任务开始时间
			TextView task_enddata; //任务结束时间
			TextView task_duration; //任务持续时间
			TextView task_person; //任务负责人
			Button btn_projectplan_task_fillprogress;//填报进度按钮
			Button btn_projectplan_task_checkprogress;//进度审核按钮
			Button btn_projectplan_task_checkbackprogress;//进度反审核
		}

	@Override
	public void onRefresh() {
		page = 1;
		initdata(projectId, 1+"", pagesize+"",0+"",packageId+"");
		onLoad();
	}
	@Override
	public void onLoadMore() {
		if(!isLastPage){
		if(flag){
			flag =false;
		++page;
		initdata(projectId, page+"", pagesize+"",0+"",packageId+"");
		}
		}else{
			handler.sendEmptyMessage(3);
		}
	}
}
