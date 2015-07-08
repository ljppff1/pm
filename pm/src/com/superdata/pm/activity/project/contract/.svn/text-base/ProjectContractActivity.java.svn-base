package com.superdata.pm.activity.project.contract;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.text.DecimalFormat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.PContract;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 项目-->项目合同
 * @author kw
 *
 */
public class ProjectContractActivity extends BaseActivity implements IXListViewListener,
				OnClickListener{

	private ImageView btnBack;
	private TextView top_title;
	private EditText et_projectcontract_search;
	private ImageButton btnSearch;
	
	private MyAdapter adapter;
	private List<PContract> pList = new ArrayList<PContract>();
	private List<PContract> pList1 = new ArrayList<PContract>();
	private XListView listView;
	
	private String empId;//用户ID
	private int pageNum = 1;//页码
	private int pageSize =10;//每页大小
	private boolean flag = true;//加载标识
	private boolean flag1 = false;//搜索标识
	private boolean isLastPage =false;//是否最后一页
	private String keyWord = "";//搜索关键字
	
	PContract pContract;
	String url = InterfaceConfig.PROJECT_CONTRACTLIST_URL;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectcontract);
		
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
		top_title.setText("项目合同");
		initData(empId,pageNum+"",pageSize+"");
	}
	
	/**
	 * 项目合同列表
	 * @param empId	用户id
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 */
	public void initData(String empId, String pageNum, String pageSize){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		
		new MyTask().execute(params);
	}
	
	public void initDataSearch(){
		initDataSearch(empId,pageNum+"",pageSize+"",keyWord);
	}
	
	
	/**
	 * 合同搜索
	 * @param empId	用户id
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 * @param keyWord	关键字
	 */
	public void initDataSearch(String empId, String pageNum, String pageSize,String keyWord){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("keyword", keyWord));
		
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
			} catch (HttpHostConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		@Override
		protected void onPostExecute(String result) {
			if(null != result){
				try {
					JSONObject jRoot = new JSONObject(result);
					JSONArray resultList = jRoot.getJSONArray("resultList");
					isLastPage = jRoot.getBoolean("isLastPage");
					
					pList1.clear();
					
					for(int i=0; i<resultList.length(); i++){
						String projectName = (String) resultList.getJSONObject(i).get("projectName");
						String code = (String) resultList.getJSONObject(i).get("code");
						String name = (String) resultList.getJSONObject(i).get("name");
						String otherCompany = (String) resultList.getJSONObject(i).get("otherCompany");
						String startDate = (String) resultList.getJSONObject(i).get("startDate");
						String endDate = (String) resultList.getJSONObject(i).get("endDate");
						String amount =  df.format(resultList.getJSONObject(i).get("amount"));
						Integer attachment = (Integer) resultList.getJSONObject(i).get("attachment");
						Integer contractId = (Integer) resultList.getJSONObject(i).get("id");
						
						String startDate1 = startDate.substring(0, 10);
						String endDate1 = endDate.substring(0, 10);
						
						PContract pContract1 = new PContract(projectName, code, name, amount,
								startDate1, endDate1, otherCompany, attachment, contractId);
						pList1.add(pContract1);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Message msg = new Message();
			//加载更多 时
			if(!flag){
				pList.addAll(pList1);
				pList1.clear();
				adapter = new MyAdapter();
				msg.what = 2;
			}else{
				/*//第一次
				if(flag1){
				pList.addAll(pList1);
				pList1.clear();
				adapter = new MyAdapter();
				msg.what = 1;
				}else{
					//重新加载全部
					pList.clear();
					pList.addAll(pList1);
					adapter = new MyAdapter();
					msg.what = 1;
				}*/
				
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
	public void onClick(View v) {
		
		// 点击返回时
		if(v == btnBack){
			onBackPressed();
		}
		
		
		//点击搜索时
		if(v == btnSearch){
			keyWord = et_projectcontract_search.getText().toString();
			flag1 = true;
			pageNum = 1;
			initDataSearch();
		}
		
		
	}

	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_projectcontract_search = (EditText) findViewById(R.id.et_projectcontract_search);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch = (ImageButton) findViewById(R.id.btn_projectcontract_search);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		sdDialog = new SDProgressDialog(ProjectContractActivity.this);
		pContract = new PContract();
		
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(ProjectContractActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);
		
		listView = (XListView) findViewById(R.id.lv_projectcontract_list);
		listView.setOnItemClickListener(new ProjectContractListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}
	
	private class ProjectContractListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 跳转到合同详细
			Intent intent = new Intent(ProjectContractActivity.this,
					ProjectContractDetailActivity.class);
			intent.putExtra("empId", empId);
			intent.putExtra("contractId", pList.get(position-1).getId().toString());
			startActivity(intent);
		}
		
	} 

	@Override
	public void onRefresh() {
		if(!flag1){
			pageNum = 1;
			initData(empId,pageNum+"",pageSize+"");
			onLoad();
		}else{
			//搜索刷新
			pageNum = 1;
			initDataSearch(empId,pageNum+"",pageSize+"",keyWord);
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
					initData(empId,pageNum+"",pageSize+"");
				}
			}else{
				handler.sendEmptyMessage(3);
			}
		}else{
			if(!isLastPage){
				if(flag){
					flag = false;
					++pageNum;
					initDataSearch(empId,pageNum+"",pageSize+"",keyWord);
				}
			}else{
				handler.sendEmptyMessage(3);
			}
		}
	}
	
	//自定义适配器
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
						.inflate(R.layout.projectcontract_listitem, null);
				holder.tv_projectcontract_listitem_contractnumber = (TextView) convertView.findViewById(R.id.tv_projectcontract_listitem_contractnumber);
				holder.tv_projectcontract_listitem_contractname = (TextView) convertView.findViewById(R.id.tv_projectcontract_listitem_contractname);
				holder.tv_projectcontract_listitem_othercompany = (TextView) convertView.findViewById(R.id.tv_projectcontract_listitem_othercompany);
				holder.tv_projectcontract_listitem_startdata = (TextView) convertView.findViewById(R.id.tv_projectcontract_listitem_startdata);
				holder.tv_projectcontract_listitem_enddata = (TextView) convertView.findViewById(R.id.tv_projectcontract_listitem_enddata);
				holder.tv_projectcontract_listitem_contractsum = (TextView) convertView.findViewById(R.id.tv_projectcontract_listitem_contractsum);
				holder.tv_projectcontract_listitem_annexnumer = (TextView) convertView.findViewById(R.id.tv_projectcontract_listitem_annexnumer);
				holder.tv_projectcontract_listitem_project = (TextView) convertView.findViewById(R.id.tv_projectcontract_listitem_project);
			
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_projectcontract_listitem_project.setText(pList.get(position).getProjectName().toString());
			holder.tv_projectcontract_listitem_contractnumber.setText(pList.get(position).getCode().toString());
			holder.tv_projectcontract_listitem_contractname.setText(pList.get(position).getName().toString());
			holder.tv_projectcontract_listitem_othercompany.setText(pList.get(position).getOtherCompany().toString());
			holder.tv_projectcontract_listitem_startdata.setText(pList.get(position).getStartDate().toString());
			holder.tv_projectcontract_listitem_enddata.setText(pList.get(position).getEndDate().toString());
			holder.tv_projectcontract_listitem_contractsum.setText(pList.get(position).getAmount().toString());
			holder.tv_projectcontract_listitem_annexnumer.setText(pList.get(position).getAttachment().toString());
			
			return convertView;
		}
		
		
		class Holder{
			//合同编号
			TextView tv_projectcontract_listitem_contractnumber;
			//合同名称
			TextView tv_projectcontract_listitem_contractname;
			//对方公司
			TextView tv_projectcontract_listitem_othercompany;
			//合同开始日期
			TextView tv_projectcontract_listitem_startdata;
			//合同结束日期
			TextView tv_projectcontract_listitem_enddata;
			//合同金额
			TextView tv_projectcontract_listitem_contractsum;
			//附件个数
			TextView tv_projectcontract_listitem_annexnumer;
			//所属项目
			TextView tv_projectcontract_listitem_project;
		}
		
	}
	
	
	
}
