package com.superdata.pm.activity.project.report;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.PReport;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 项目--》验收报告列表
 * @author kw
 *
 */
public class CheckReportListActivity extends BaseActivity implements IXListViewListener,
				OnClickListener{

	private TextView tv_top_title;
	private ImageView btnBack;
	private ImageButton btnSearch;
	private EditText et_checkreport_search;
	
	private List<PReport> pList = new ArrayList<PReport>();
	private List<PReport> pList1 = new ArrayList<PReport>();
	private MyAdapter adapter;
	private XListView listView;
	
	private String empId;//用户id
	private int page = 1;//页码
	private int pageSize = 10;//每页大小
	private boolean flag = true;//标识
	private boolean flag1 =false;//是否输入关键字
	private boolean isLastPage = false;//是否末页
	private String keyWord = "";//搜索关键字
	
	PReport pReport;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	String url = InterfaceConfig.CHECKREPORT_LIST_URL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkreport);
		
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
				adapter.notifyDataSetChanged();//通知更新
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
			super.handleMessage(msg);
		};
	};

	public void onLoad(){
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	
	
	
	@Override
	public void onClick(View v) {
		// 点击返回时
		if(v == btnBack){
			onBackPressed();
		}
		
		
		//点击搜索时
		if(v == btnSearch){
			keyWord = et_checkreport_search.getText().toString();
			flag1 = true;
			page = 1;
			initDataSearch();
		}
		
	}




	public void initView(){
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		et_checkreport_search = (EditText) findViewById(R.id.et_checkreport_search);
		
		
		btnSearch = (ImageButton) findViewById(R.id.btn_checkreport_search);
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		
		sdDialog = new SDProgressDialog(this);
		pReport = new PReport();
		
		Map<String, String> mapConfig = SharedPreferencesConfig
				.config(CheckReportListActivity.this);
		empId = mapConfig.get(InterfaceConfig.ID);
		
		listView = (XListView) findViewById(R.id.lv_checkreport_list);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//跳转到验收报告
				Intent intent = new Intent(CheckReportListActivity.this,CheckReportActivity.class);
				intent.putExtra("Id", pList.get(position-1).getId());//报告id
				intent.putExtra("empId", empId);//用户id
				startActivity(intent);
			}
		});
	}
	
	
	public void initData(){
		tv_top_title.setText("验收报告");
		initData(empId,page+"",pageSize+"");
	}
	
	/**
	 * 验收报告列表
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 */
	public void initData(String empId,String pageNum,String pageSize){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		
		new MyTask().execute(params);
	}
	
	public void initDataSearch(){
		initDataSearch(empId,page+"",pageSize+"",keyWord);
	}
	
	/**
	 * 验收报告的搜索列表
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 * @param searchValue	关键字
	 */
	public void initDataSearch(String empId,String pageNum, String pageSize, String searchValue){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("searchValue", searchValue));
		
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
					JSONObject jsonObject = jRoot.getJSONObject("resultData");
					
					String resultCode = (String) jRoot.get("resultCode");
					
					if("200".equals(resultCode.trim())){
						JSONArray rows = jsonObject.getJSONArray("resultList");
						isLastPage = jsonObject.getBoolean("isLastPage");
						pList1.clear();
						
						for(int i=0; i<rows.length(); i++){
							String code = (String) rows.getJSONObject(i).get("code");//报告编号
							String projectName = (String) rows.getJSONObject(i).get("projectName");//验收项目
							String name = (String) rows.getJSONObject(i).get("name");//报告名称
							String billDate = (String) rows.getJSONObject(i).get("billDate");//验收时间
							String resultName = (String) rows.getJSONObject(i).get("resultName");//验收结果
						
							String billDate1 = billDate.substring(0, 10);
							
							Integer id = (Integer) rows.getJSONObject(i).get("id");//报告id
							
							PReport pReport1 = new PReport(id, projectName, code, name, billDate1, resultName);
							pList1.add(pReport1);
						}
					}else{
						Toast.makeText(CheckReportListActivity.this,
								"查询失败", Toast.LENGTH_SHORT).show();
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
				adapter = new MyAdapter();
				msg.what = 2;
			}else{
				//重新加载全部
				pList.clear();
				pList.addAll(pList1);
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
	public void onRefresh() {
		/*flag = false;
		page = 1;
		initData(page+"",pageSize+"");
		onLoad();*/
		if(!flag1){
			page = 1;
			initData(empId,page+"",pageSize+"");
			onLoad();
		}else{
			//搜索刷新
			page = 1;
			initDataSearch(empId,page+"",pageSize+"",keyWord);
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
					initData(empId,page+"",pageSize+"");
				}
			}else{
				handler.sendEmptyMessage(3);
			}
		}else{
			if(!isLastPage){
				if(flag){
					flag = false;
					++page;
					initDataSearch(empId,page+"",pageSize+"",keyWord);
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
						.inflate(R.layout.checkreport_listitem, null);
				holder.tv_checkreport_reportnumber = (TextView) convertView.findViewById(R.id.tv_checkreport_reportnumber);
				holder.tv_checkreport_project = (TextView) convertView.findViewById(R.id.tv_checkreport_project);
				holder.tv_checkreport_reportname = (TextView) convertView.findViewById(R.id.tv_checkreport_reportname);
				holder.tv_checkreport_checktime = (TextView) convertView.findViewById(R.id.tv_checkreport_checktime);
				holder.tv_checkreport_checkresult = (TextView) convertView.findViewById(R.id.tv_checkreport_checkresult);
			
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_checkreport_reportnumber.setText(pList.get(position).getCode().toString());
			holder.tv_checkreport_project.setText(pList.get(position).getProjectName().toString());
			holder.tv_checkreport_reportname.setText(pList.get(position).getName().toString());
			holder.tv_checkreport_checktime.setText(pList.get(position).getBillDate().toString());
			holder.tv_checkreport_checkresult.setText(pList.get(position).getResultName().toString());
			
			return convertView;
		}
		
		class Holder{
			TextView tv_checkreport_reportnumber;//验收报告编号
			TextView tv_checkreport_project;//验收项目
			TextView tv_checkreport_reportname;//验收报告名称
			TextView tv_checkreport_checktime;//验收时间
			TextView tv_checkreport_checkresult;//验收结果
		}
		
	}

}
