package com.superdata.pm.activity.project.member;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.PEmployee;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 项目-->成员项目列表-->项目成员列表
 * @author kw
 *
 */
public class ProjectMemberActivity extends BaseActivity implements IXListViewListener,
				OnClickListener{

	private ImageView btnBack;
	private TextView top_title;
	private EditText et_projectmember_search;
	private ImageButton btnSearch;
	
	private MyAdapter adapter;
	private List<PEmployee> pList = new ArrayList<PEmployee>();
	private List<PEmployee> pList1 = new ArrayList<PEmployee>();
	
	private String empId;//用户id
	private String projectId;//项目ID
	private int pageNum = 1;//项目页
	private int pageSize = 10;//每页大小
	private boolean flag = true;//加载标识
	private boolean flag1 = false;//搜索标识
	private boolean isLastPage =false;//是否最后一页
	private String keyWord = "";//搜索的关键字
	
	private XListView listView;
	
	PEmployee pEmployee;
	String url = InterfaceConfig.PROJECT_MEMBERLIST_URL;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectmember);
		
		projectId = (String) getIntent().getExtras().getString("ID");
		initView();//初始化视图
		initData();//初始化数据
		
	}
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
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
				listView.smoothScrollToPosition(pList.size());
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
		};
	};

	public void onLoad(){
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	
	
	
	//初始化数据
	public void initData(){
		top_title.setText("项目成员");
		initData(empId,projectId,pageNum+"",pageSize+"");
	}
	
	/**
	 * 项目成员
	 * @param projectId	项目id
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 */
	public void initData(String empId, String projectId, String pageNum, String pageSize){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		
		new MyTask().execute(params);
	}
	
	
	public void initDataSearch(){
		initDataSearch(empId,projectId,pageNum+"",pageSize+"",keyWord);
	}
	
	/**
	 * 项目成员搜索列表
	 * @param projectId	项目id
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 * @param keyWord	搜索关键字
	 */
	public void initDataSearch(String empId, String projectId, String pageNum,
			String pageSize, String keyWord){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("keyword", keyWord));
		
		new MyTask().execute(params);
	}
	
	//异步处理后台连接和json解析
	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String>{
		
		@Override
		protected void onPreExecute() {
			if(pageNum == 1){
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
					pList1.clear();
					
					for(int i=0; i<resultList.length(); i++){
						String empCode = (String) resultList.getJSONObject(i).get("empCode");//成员编号
						String empName = (String) resultList.getJSONObject(i).get("empName");//成员姓名
						String empDept = (String) resultList.getJSONObject(i).get("empDept");//所属部门
						String positionName = (String) resultList.getJSONObject(i).get("positionName");//成员职位
						String isCreatorName = (String) resultList.getJSONObject(i).get("isCreatorName");//是否立项人
						String closedName = (String) resultList.getJSONObject(i).get("closedName");//是否参与
						String remark = (String) (resultList.getJSONObject(i).get("remark").equals(null)?
								"":resultList.getJSONObject(i).get("remark"));//备注
						Integer empId = (Integer) resultList.getJSONObject(i).get("empId");//员工ID
						Integer projectId = (Integer) resultList.getJSONObject(i).get("projectId");//项目ID
						PEmployee pEmployee1 = new PEmployee(empName, empCode, empDept, positionName,
								remark, isCreatorName, closedName, empId, projectId);
						pList1.add(pEmployee1);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Message message = new Message();
			//加载更多时
			if(!flag){
				pList.addAll(pList1);
				pList1.clear();
				adapter = new MyAdapter();
				message.what = 2;
			}else{
				//重新加载全部
				pList.clear();
				pList.addAll(pList1);
				adapter = new MyAdapter();
				message.what = 1;
			}
			if(sdDialog.isShow()){
				sdDialog.cancel();
			}
			
			handler.sendMessage(message);
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
			keyWord = et_projectmember_search.getText().toString();
			flag1 = true;
			pageNum = 1;
			initDataSearch();
		}
		
	}


	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_projectmember_search = (EditText) findViewById(R.id.et_projectmember_search);
		
		sdDialog = new SDProgressDialog(ProjectMemberActivity.this);
		pEmployee = new PEmployee();
		
		Map<String,String> mapconfig = SharedPreferencesConfig
				.config(ProjectMemberActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);
		
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch  = (ImageButton) findViewById(R.id.btn_projectmember_search);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		listView = (XListView) findViewById(R.id.lv_projectmember_list);
		listView.setOnItemClickListener(new ProjectMemberListener());
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
	private class ProjectMemberListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 点击跳转到成员详细
			Intent intent = new Intent(ProjectMemberActivity.this,
					ProjectMemberDetailActivity.class);
			intent.putExtra("projectId", pList.get(position-1).getProjectId().toString());
			intent.putExtra("projectEmpId", pList.get(position-1).getEmpId().toString());
			intent.putExtra("empId", empId);
			startActivity(intent);
		}
		
	}

	
	@Override
	public void onRefresh() {
		if(!flag1){
			pageNum = 1;
			initData(empId,projectId,pageNum+"",pageSize+"");
			onLoad();
		}else{
			//搜索刷新
			pageNum = 1;
			initDataSearch(empId,projectId,pageNum+"",pageSize+"",keyWord);
			onLoad();
		}
	}

	@Override
	public void onLoadMore() {
		if(!flag1){
			if(!isLastPage){
				if(flag){
					flag = false;
					++pageNum;
					initData(empId,projectId,pageNum+"",pageSize+"");
				}
			}else{
				handler.sendEmptyMessage(3);
			}
		}else{
			if(!isLastPage){
				if(flag){
					flag = false;
					++pageNum;
					initDataSearch(empId,projectId,pageNum+"",pageSize+"",keyWord);
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
			return pList.size();
		}

		@Override
		public Object getItem(int position) {
			return pList.get(position);
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
						.inflate(R.layout.projectmember_listitem, null);
				holder.tv_projectmember_listitem_membernumber = (TextView) convertView.findViewById(R.id.tv_projectmember_listitem_membernumber);
				holder.tv_projectmember_listitem_membername = (TextView) convertView.findViewById(R.id.tv_projectmember_listitem_membername);
				holder.tv_projectmember_listitem_department = (TextView) convertView.findViewById(R.id.tv_projectmember_listitem_department);
				holder.tv_projectmember_listitem_memberposition = (TextView) convertView.findViewById(R.id.tv_projectmember_listitem_memberposition);
				holder.tv_projectmember_listitem_projectperson = (TextView) convertView.findViewById(R.id.tv_projectmember_listitem_projectperson);
				holder.tv_projectmember_listitem_state = (TextView) convertView.findViewById(R.id.tv_projectmember_listitem_state);
				holder.tv_projectmember_listitem_remark = (TextView) convertView.findViewById(R.id.tv_projectmember_listitem_remark);
			
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_projectmember_listitem_membernumber.setText(pList.get(position).getEmpCode().toString());
			holder.tv_projectmember_listitem_membername.setText(pList.get(position).getEmpName().toString());
			holder.tv_projectmember_listitem_department.setText(pList.get(position).getEmpDept().toString());
			holder.tv_projectmember_listitem_memberposition.setText(pList.get(position).getPositionName().toString());
			holder.tv_projectmember_listitem_projectperson.setText(pList.get(position).getIsCreatorName().toString());
			holder.tv_projectmember_listitem_state.setText(pList.get(position).getClosedName().toString());
			holder.tv_projectmember_listitem_remark.setText(pList.get(position).getRemark().toString());
			
			return convertView;
		}
		
		
		class Holder{
			TextView tv_projectmember_listitem_membernumber;//成员编号
			TextView tv_projectmember_listitem_membername;//成员姓名
			TextView tv_projectmember_listitem_department;//所属部门
			TextView tv_projectmember_listitem_memberposition;//成员职位
			TextView tv_projectmember_listitem_projectperson;//是否立项人
			TextView tv_projectmember_listitem_state;//是否参与
			TextView tv_projectmember_listitem_remark;//备注
		}
		
	}
}
