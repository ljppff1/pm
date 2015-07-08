package com.superdata.pm.activity.project.manager.detail.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.BitmapDrawable;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.project.manager.detail.comment.ProjectCommentActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.PProjectLog;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 项目-->项目管理-->项目详细-->项目日志
 * @author kw
 *
 */
public class ProjectLogActivity extends BaseActivity implements IXListViewListener,
				OnClickListener{
	
	
	private ImageView btnBack;
	private TextView top_title;
	private EditText et_projectlog_search;
	private ImageButton btnSearch;
	
	private TabProjectLogAdapter tplAdapter;
	private List<PProjectLog> pList = new ArrayList<PProjectLog>();
	private List<PProjectLog> pList1 = new ArrayList<PProjectLog>();

	private String empId;
	private int pageNum = 1;
	private int pageSize = 10;
	private String projectId; 
	private boolean flag = true;
	private boolean flag1 = false; //输入关键字标识
	private String keyword = "";
	private boolean isLastPage =false;
	
	private XListView listView;
	
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	String url = InterfaceConfig.PROJECT_LOG_URL;
	PProjectLog pProjectLog; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectlog);
		
		empId  =getIntent().getExtras().getString("empId");
		projectId = (String) getIntent().getExtras().get("projectId");
		
		initView();//初始化视图
		initData();//初始化数据
		
	}
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				listView.setAdapter(tplAdapter);
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				tplAdapter.notifyDataSetChanged();
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				break;
			case 2:
				flag = true;
				listView.setAdapter(tplAdapter);
				listView.smoothScrollToPosition(pList.size());
				tplAdapter.notifyDataSetChanged();
				tplAdapter.notifyDataSetChanged();
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
		};
	};

	public void onLoad(){
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	
	//初始化数据
	public void initData(){
		top_title.setText("项目日志");
		
		initData(empId,pageNum+"",pageSize+"",projectId);
	}
	
	public void initData(String empId,String pageNum,String pageSize,String projectId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("projectId", projectId));
		
		new MyTask().execute(params);
	}
	
	
	public void initSearch(){
		initSearch(empId,pageNum+"",pageSize+"",keyword);
	}
	
	/**
	 * 项目的搜索列表
	 * @param empId	用户id
	 * @param page	页码
	 * @param pagesize	每页大小
	 * @param keyWord	搜索关键字
	 */
	public void initSearch(String empId,String pageNum,String pageSize,String keyword){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum",pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("keyword", keyword));
		
		new MyTask().execute(params);
	}
	
	
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
					for(int i=0;i<resultList.length();i++){
						//创建人
						String createName = (String) resultList.getJSONObject(i).get("createName");
						//创建时间
						String createDate = (String) resultList.getJSONObject(i).get("createDate");
						//操作类型
						String opType = (String) resultList.getJSONObject(i).get("opType");
						//操作说明
						String opRemark = (String) resultList.getJSONObject(i).get("opRemark");
						
						PProjectLog pProjectLog1 = new PProjectLog(opType, opRemark, createName, createDate);
						
						pList1.add(pProjectLog1);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Message msg = new Message();
			//加载更多时
			if(!flag){
				pList.addAll(pList1);
				pList1.clear();
				tplAdapter = new TabProjectLogAdapter();
				msg.what =2;
			}else{
				//重新加载全部
				pList.clear();
				pList.addAll(pList1);
				tplAdapter = new TabProjectLogAdapter();
				msg.what = 1;
			}
			
			if(sdDialog.isShow()){
				sdDialog.cancel();
			}
			handler.sendMessage(msg);
			
			super.onPostExecute(result);
		}
		
	}
	
	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_projectlog_search = (EditText) findViewById(R.id.et_projectlog_search);
		
		sdDialog = new SDProgressDialog(this);
		pProjectLog = new PProjectLog();
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch = (ImageButton) findViewById(R.id.btn_projectlog_search);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		
		listView = (XListView) findViewById(R.id.lv_projectlog_list);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		
	}
	
	@Override
	public void onRefresh() {
		if(!flag1){
			pageNum = 1;
			initData(empId,pageNum+"",pageSize+"",projectId);
			onLoad();
		}else{
			//搜索刷新
			pageNum = 1;
			initSearch(empId,pageNum+"",pageSize+"",keyword);
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
					initData(empId,pageNum+"",pageSize+"",projectId);
				}
			}else{
				handler.sendEmptyMessage(3);
			}
		}else{
			if(!isLastPage){
				if(flag){
					flag = false;
					++pageNum;
					initSearch(empId,pageNum+"",pageSize+"",keyword);
				}
				
			}else{
				handler.sendEmptyMessage(3);
			}
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
			keyword = et_projectlog_search.getText().toString();
			flag1 =true;
			pageNum = 1;
			initSearch();
			
		
		}
		
		
	}
	
	
	
	
	//自定义适配器
	class TabProjectLogAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return pList.get(position);
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
						.inflate(R.layout.projectlog_listitem, null);
				
				holder.tv_tab_projectlog_operater = (TextView) convertView.findViewById(R.id.tv_tab_projectlog_operater);
				holder.tv_tab_projectlog_operatetime = (TextView) convertView.findViewById(R.id.tv_tab_projectlog_operatetime);
				holder.tv_tab_projectlog_operatecontent = (TextView) convertView.findViewById(R.id.tv_tab_projectlog_operatecontent);
				holder.tv_tab_projectlog_opType = (TextView) convertView.findViewById(R.id.tv_tab_projectlog_opType);
				
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_tab_projectlog_operater.setText(pList.get(position).getCreateName().toString());
			holder.tv_tab_projectlog_operatetime.setText(pList.get(position).getCreateDate().toString());
			holder.tv_tab_projectlog_operatecontent.setText(pList.get(position).getOpRemark().toString());
			holder.tv_tab_projectlog_opType.setText(pList.get(position).getOpType().toString());
			
			return convertView;
		}
		
		class Holder{
			//创建人
			TextView tv_tab_projectlog_operater;
			//创建时间
			TextView tv_tab_projectlog_operatetime;
			//操作说明
			TextView tv_tab_projectlog_operatecontent;
			//操作类型
			TextView tv_tab_projectlog_opType;
			
		}
		
	}


}
