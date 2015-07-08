package com.superdata.pm.activity.project.manager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.PProject;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 项目-->项目管理-->项目列表
 * @author kw
 *
 * 2014-07-04
 */
public class ProjectManagerCheckActivity extends BaseActivity implements IXListViewListener,
				OnClickListener{

	private ImageView btnBack;
	private TextView top_title;
	private EditText et_projectcheck_search;
	private ImageButton btnSearch;
	
	private MyAdapter adapter;
	private List<PProject> pProjectList = new ArrayList<PProject>();
	private List<PProject> pProjectList1 = new ArrayList<PProject>();
	private XListView listView;
	
	private String empId;//用户id
	private int page = 1;//页码
	private int pagesize = 10;//每页大小
	private boolean flag = true;//标识
	private boolean flag1 = false;//是否输入关键字
	private boolean isLastPage =false;//是否末页
	private String keyWord = "";//搜索的关键字
	
	
	PProject pProject;
	Context context;
	String url = InterfaceConfig.PROJECT_LIST_URL;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectmanager_check);
		
		initView();//初始化视图
		intitData();//初始化数据
	}
	
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			
			case 1:
				
				listView.setAdapter(adapter);
				
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				
				adapter.notifyDataSetChanged();//通知更新
				
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				
				break;
				
			case 2:
				
				flag = true;
				
				listView.setAdapter(adapter);
				listView.smoothScrollToPosition(pProjectList.size());
				
				adapter.notifyDataSetChanged();
				
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				
				onLoad();
				
				break;
				
			case 3:
				
				onLoad();
				
				Toast.makeText(getApplicationContext(), "已全部加载完成", Toast.LENGTH_SHORT).show();
				
				break;
				
			default:
				break;
			}
			super.handleMessage(msg);
		};
	};

	
	public void onLoad(){
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	
	
	public void intitData(){
		top_title.setText("项目列表");
		
		intitData(empId,page+"",pagesize+"");
	}
	
	
	/**
	 * 项目列表
	 * @param empId	用户id
	 * @param page	页码
	 * @param pagesize	每页大小
	 */
	public void intitData(String empId, String page, String pagesize){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("id", empId));
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum",page));
		params.add(new BasicNameValuePair("pageSize", pagesize));
		Log.d("id--------", empId);
		
		new MyTask().execute(params);
	}
	
	
	public void initDataSearch(){
		initDataSearch(empId,page+"",pagesize+"",keyWord);
	}
	
	/**
	 * 项目的搜索列表
	 * @param empId	用户id
	 * @param page	页码
	 * @param pagesize	每页大小
	 * @param keyWord	搜索关键字
	 */
	public void initDataSearch(String empId, String page, String pagesize, String keyWord){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", empId));
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum",page));
		params.add(new BasicNameValuePair("pageSize", pagesize));
		params.add(new BasicNameValuePair("keyword", keyWord));
		
		
		new MyTask().execute(params);
	}
	
	
	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String>{

		@Override
		protected void onPreExecute() {
			
			if(page == 1){
				sdDialog.show();
			}
			
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			sdClient = new SDHttpClient();
			String json = null;
			try {
				json = sdClient.post_session(url, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		@Override
		protected void onPostExecute(String result) {
			if(result != null){
				try {
					JSONObject jRoot = new JSONObject(result);
//					list = new ArrayList<HashMap<String,Object>>();
					JSONArray resultList = jRoot.getJSONArray("resultList");
					isLastPage = jRoot.getBoolean("isLastPage");
					
					pProjectList1.clear();
					for(int i=0; i<resultList.length(); i++){
						String code = (String) (resultList.getJSONObject(i).get("code"));//项目编号
						String name =(String)(resultList.getJSONObject(i).get("name"));//项目名称
						String startDate = (String)(resultList.getJSONObject(i).get("startDate"));//开始日期
						String endDate = (String)(resultList.getJSONObject(i).get("endDate"));//结束日期
						String empName = (String)(resultList.getJSONObject(i).get("empName"));//项目经理
						String amount = df.format(resultList.getJSONObject(i).get("amount"));//预算总额
						Integer rate = (Integer)(resultList.getJSONObject(i).get("rate"));//完成度
						String statusName = (String) (resultList.getJSONObject(i).get("statusName").equals(null)?
								"":resultList.getJSONObject(i).get("statusName"));//项目状态
						String createDate = (String)(resultList.getJSONObject(i).get("createDate"));//立项日期
						Integer id = (Integer)(resultList.getJSONObject(i).get("id"));//项目id
						
						String startDate1 = startDate.substring(0, 10);
						String endDate1 = endDate.substring(0, 10);
						String createDate1 = createDate.substring(0, 10);
						
						PProject pProject = new PProject(code, name, amount, startDate1,
								endDate1, rate, empName, createDate1, statusName,id);
						
						pProjectList1.add(pProject);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			Message msg = new Message();
			//加载更多 时
			if(!flag){
				pProjectList.addAll(pProjectList1);
				pProjectList1.clear();
				adapter = new MyAdapter();
				msg.what = 2;
			}else{
				/*//第一次
				if(flag1){
				pProjectList.addAll(pProjectList1);
				pProjectList1.clear();
				adapter = new MyAdapter();
				msg.what = 1;
				}else{
					//重新加载全部
					pProjectList.clear();
					pProjectList.addAll(pProjectList1);
					adapter = new MyAdapter();
					msg.what = 1;
				}*/
				//重新加载全部
				pProjectList.clear();
				pProjectList.addAll(pProjectList1);
				adapter = new MyAdapter();
				msg.what = 1;
			}
			
			if(sdDialog.isShow()){
				sdDialog.cancel();
			}
			
			handler.sendMessage(msg);
			
			super.onPostExecute(result);
		}
		
	}
	
	
	@Override
	public void onClick(View v) {
		// 点击返回时
		if(v == btnBack){
			onBackPressed();
		}
		
		//点击搜索时
		if(v == btnSearch){
			keyWord = et_projectcheck_search.getText().toString();
			flag1 =true;
			page = 1;
			initDataSearch();
		}
		
	}


	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		
		et_projectcheck_search = (EditText) findViewById(R.id.et_projectcheck_search);
		
		sdDialog = new SDProgressDialog(ProjectManagerCheckActivity.this);
		pProject = new PProject();
		
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(ProjectManagerCheckActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);//得到用户id
		
		//点击返回
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch = (ImageButton) findViewById(R.id.btn_projectcheck_search);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		listView = (XListView) findViewById(R.id.lv_projectcheck_list);
		listView.setOnItemClickListener(new ProjectManagerCheckListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		
	}
	
	
	/**
	 * 条目点击监听事件
	 * @author Administrator
	 *
	 */
	private class ProjectManagerCheckListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//点击项目列表条目进入查看项目项目详情
			Intent intent = new Intent(ProjectManagerCheckActivity.this,ProjectManagerCheckDetailActivity.class);
			intent.putExtra("ID", pProjectList.get(position-1).getId().toString());
			intent.putExtra("empId", empId);
			startActivity(intent);
		}
		
	}
	
	

	@Override
	public void onRefresh() {
		/*flag1 = false;
		page = 1;
		intitData(empId,1+"",pagesize+"");
		onLoad();*/
		if(!flag1){
			page = 1;
			intitData(empId,page+"",pagesize+"");
			onLoad();
		}else{
			//搜索刷新
			page = 1;
			initDataSearch(empId,page+"",pagesize+"",keyWord);
			onLoad();
		}
	}

	@Override
	public void onLoadMore() {
		/*if(!isLastPage){
			if(flag){
				flag = false;
				++page;
				intitData(empId,page+"",pagesize+"");
			}
		}else{
			handler.sendEmptyMessage(3);
		}*/
		
		if(!flag1){
			if(!isLastPage){
				if(flag){
					flag = false;
					++page;
					intitData(empId,page+"",pagesize+"");
				}
			}else{
				handler.sendEmptyMessage(3);
			}
		}else{
			if(!isLastPage){
				if(flag){
					flag = false;
					++page;
					initDataSearch(empId,1+"",pagesize+"",keyWord);
				}
				
			}else{
				handler.sendEmptyMessage(3);
			}
		}
		
	}
	
	/**
	 * 自定义适配器
	 * @author Administrator
	 *
	 */
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return pProjectList.size();
		}

		@Override
		public Object getItem(int position) {
			return pProjectList.get(position);
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
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.projectmanager_check_listitem, null);
				
				holder.tv_projectmanager_check_listitem_number = (TextView) convertView.findViewById(R.id.tv_projectmanager_check_listitem_number);
				holder.tv_projectmanager_check_listitem_name = (TextView) convertView.findViewById(R.id.tv_projectmanager_check_listitem_name);
				holder.tv_projectmanager_check_listitem_startdata = (TextView) convertView.findViewById(R.id.tv_projectmanager_check_listitem_startdata);
				holder.tv_projectmanager_check_listitem_enddata = (TextView) convertView.findViewById(R.id.tv_projectmanager_check_listitem_enddata);
				holder.tv_projectmanager_check_listitem_manager = (TextView) convertView.findViewById(R.id.tv_projectmanager_check_listitem_manager);
				holder.tv_projectmanager_check_listitem_budget = (TextView) convertView.findViewById(R.id.tv_projectmanager_check_listitem_budget);
				holder.tv_projectmanager_check_listitem_state = (TextView) convertView.findViewById(R.id.tv_projectmanager_check_listitem_state);
				holder.tv_projectmanager_check_listitem_completeness = (TextView) convertView.findViewById(R.id.tv_projectmanager_check_listitem_completeness);
				holder.tv_projectmanager_check_listitem_createtime = (TextView) convertView.findViewById(R.id.tv_projectmanager_check_listitem_createtime);
				
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			
			holder.tv_projectmanager_check_listitem_number.setText(pProjectList.get(position).getCode().toString());
			holder.tv_projectmanager_check_listitem_name.setText(pProjectList.get(position).getName().toString());
			holder.tv_projectmanager_check_listitem_startdata.setText(pProjectList.get(position).getStartDate().toString());
			holder.tv_projectmanager_check_listitem_enddata.setText(pProjectList.get(position).getEndDate().toString());
			holder.tv_projectmanager_check_listitem_manager.setText(pProjectList.get(position).getEmpName().toString());
			holder.tv_projectmanager_check_listitem_budget.setText(pProjectList.get(position).getAmount().toString());
			holder.tv_projectmanager_check_listitem_state.setText(pProjectList.get(position).getStatusName().toString());
			holder.tv_projectmanager_check_listitem_completeness.setText(pProjectList.get(position).getRate().toString()+"%");
			holder.tv_projectmanager_check_listitem_createtime.setText(pProjectList.get(position).getCreateDate().toString());
			
			return convertView;
		}
		
		
		class Holder{
			//项目编号
			TextView tv_projectmanager_check_listitem_number;
			//项目名称
			TextView tv_projectmanager_check_listitem_name;
			//开始时间
			TextView tv_projectmanager_check_listitem_startdata;
			//结束时间
			TextView tv_projectmanager_check_listitem_enddata;
			//项目经理
			TextView tv_projectmanager_check_listitem_manager;
			//预算资金
			TextView tv_projectmanager_check_listitem_budget;
			//项目状态
			TextView tv_projectmanager_check_listitem_state;
			//完成度
			TextView tv_projectmanager_check_listitem_completeness;
			//立项时间
			TextView tv_projectmanager_check_listitem_createtime;
			
		}
	}
	
	
}
