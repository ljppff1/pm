package com.superdata.pm.activity.project.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * 项目--》项目成员项目列表
 * @author kw
 *
 */
public class ProjectMemberProjectActivity extends BaseActivity implements IXListViewListener,
				OnClickListener {

	private TextView top_title;
	private ImageView btnBack;
	private EditText et_projectmember_pro_search;
	private ImageButton btnSearch;
	
	private List<PProject> pProjectList = new ArrayList<PProject>();
	private List<PProject> pProjectList1 = new ArrayList<PProject>();
	private XListView listView;
	private MyAdapter adapter;
	
	
	private String empId;//用户ID
	private int page = 1;//页码
	private int pagesize = 10;//每页大小
	private boolean flag = true;//加载标识
	private boolean flag1 = false;//搜索标识
	private boolean isLastPage =false;//是否最后一页
	private String keyWord = "";//搜索的关键字
	
	PProject pProject;
	String url = InterfaceConfig.PROJECT_LIST_URL;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectmember_project);
		
		initView();//初始化视图
		intitData();//初始化数据
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				listView.setAdapter(adapter);
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
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
				Toast.makeText(getApplicationContext(), "已全部加载完成", Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		
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
	 * 项目的列表
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
		
		
		@Override
		protected void onPostExecute(String result) {
			if(null != result){
				try {
					JSONObject jRoot = new JSONObject(result);
					JSONArray resultList = jRoot.getJSONArray("resultList");
					isLastPage = jRoot.getBoolean("isLastPage");
					pProjectList1.clear();
					
					for(int i=0; i<resultList.length();i++){
						String code = (String) (resultList.getJSONObject(i).get("code"));//项目编号
						String name =(String)(resultList.getJSONObject(i).get("name"));//项目名称
						String startDate = (String)(resultList.getJSONObject(i).get("startDate"));//开始时间
						String endDate = (String)(resultList.getJSONObject(i).get("endDate"));//结束时间
						String empName = (String)(resultList.getJSONObject(i).get("empName"));//项目经理
						Integer rate = (Integer)(resultList.getJSONObject(i).get("rate"));//完成度
						String createDate = (String)(resultList.getJSONObject(i).get("createDate"));//立项时间
						Integer id = (Integer)(resultList.getJSONObject(i).get("id"));//项目id
						
						String startDate1 = startDate.substring(0, 10);
						String endDate1 = endDate.substring(0, 10);
						String createDate1 = createDate.substring(0, 10);
						
						PProject pProject1 = new PProject(code, name, startDate1, endDate1,
								rate, empName, createDate1, id);
						pProjectList1.add(pProject1);
						
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
			keyWord = et_projectmember_pro_search.getText().toString();
			flag1 = true;
			page = 1;
			initDataSearch();
		}
		
	}


	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_projectmember_pro_search = (EditText) findViewById(R.id.et_projectmember_pro_search);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch = (ImageButton) findViewById(R.id.btn_projectmember_pro_search);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		
		sdDialog = new SDProgressDialog(ProjectMemberProjectActivity.this);
		pProject = new PProject();
		
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(ProjectMemberProjectActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);
		
		listView = (XListView) findViewById(R.id.lv_projectmember_pro_list);
		listView.setOnItemClickListener(new ProjectMemberProjectListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}

	/**
	 * 条目点击事件
	 * @author Administrator
	 *
	 */
	private class ProjectMemberProjectListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//跳转到项目成员列表
			Intent intent = new Intent(ProjectMemberProjectActivity.this,
					ProjectMemberActivity.class);
			intent.putExtra("ID", pProjectList.get(position-1).getId().toString());
			intent.putExtra("empId", empId);
			startActivity(intent);
		}
		
	}
	
	@Override
	public void onRefresh() {
		if(!flag1){
			page = 1;
			intitData(empId,1+"",pagesize+"");
			onLoad();
		}else{
			//搜索刷新
			page = 1;
			initDataSearch(empId,1+"",pagesize+"",keyWord);
			onLoad();
		}
	}

	@Override
	public void onLoadMore() {
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
			// TODO Auto-generated method stub
			return pProjectList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return pProjectList.get(position);
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
						.inflate(R.layout.projectmember_projectitem, null);
				
				holder.tv_projectmember_pro_item_number = (TextView) convertView.findViewById(R.id.tv_projectmember_pro_item_number);
				holder.tv_projectmember_pro_item_name = (TextView) convertView.findViewById(R.id.tv_projectmember_pro_item_name);
				holder.tv_projectmember_pro_item_startdata = (TextView) convertView.findViewById(R.id.tv_projectmember_pro_item_startdata);
				holder.tv_projectmember_pro_item_enddata = (TextView) convertView.findViewById(R.id.tv_projectmember_pro_item_enddata);
				holder.tv_projectmember_pro_item_manager = (TextView) convertView.findViewById(R.id.tv_projectmember_pro_item_manager);
				holder.tv_projectmember_pro_item_completeness = (TextView) convertView.findViewById(R.id.tv_projectmember_pro_item_completeness);
				holder.tv_projectmember_pro_item_createtime = (TextView) convertView.findViewById(R.id.tv_projectmember_pro_item_createtime);
				
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_projectmember_pro_item_number.setText(pProjectList.get(position).getCode().toString());
			holder.tv_projectmember_pro_item_name.setText(pProjectList.get(position).getName().toString());
			holder.tv_projectmember_pro_item_startdata.setText(pProjectList.get(position).getStartDate().toString());
			holder.tv_projectmember_pro_item_enddata.setText(pProjectList.get(position).getEndDate().toString());
			holder.tv_projectmember_pro_item_manager.setText(pProjectList.get(position).getEmpName().toString());
			holder.tv_projectmember_pro_item_completeness.setText(pProjectList.get(position).getRate().toString()+"%");
			holder.tv_projectmember_pro_item_createtime.setText(pProjectList.get(position).getCreateDate().toString());
			
			return convertView;
		}
		
		class Holder{
			//项目编号
			TextView tv_projectmember_pro_item_number;
			//项目名称
			TextView tv_projectmember_pro_item_name;
			//开始时间
			TextView tv_projectmember_pro_item_startdata;
			//结束时间
			TextView tv_projectmember_pro_item_enddata;
			//项目经理
			TextView tv_projectmember_pro_item_manager;
			//完成度
			TextView tv_projectmember_pro_item_completeness;
			//立项时间
			TextView tv_projectmember_pro_item_createtime;
		}
		
	}
	
}
