package com.superdata.pm.activity.cost.expenses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.superdata.pm.entity.entitycostexpenses;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 费用管理类
 * @author lj
 *
 * 2014年6月28日
 */
public class CostExpensesActivity extends BaseActivity implements
			IXListViewListener, OnClickListener {
    
	//返回按钮
	private ImageView btnBack;
	//搜索按钮
  	private ImageButton btnSearch;
	private TextView top_title;
	private EditText costex_etsearch;
	
	//费用管理条目
	private XListView listView;
	//获取的是第几页的数据
    private int page =1;
    //获取的这页的数量
    private int pagesize =10;
    //搜索的关键字
  	private String search_name="";
  	//标识是否加载更多 
    private boolean flag =true;
    //用来判断是否输入了关键字
    private boolean flag2 =false;
    //用于判断是否修改了值
  	private boolean flag3=true;
    private boolean isLastPage=false;
    //返回的位置 ，用于再次刷新这个条目 
  	private int position_return;
  	private int showToast;
  	
    //Adapter里面放的List，
    private List<entitycostexpenses> listplanpack =new ArrayList<entitycostexpenses>();
    //临时的List，用于加载更多 的逻辑处理
    private List<entitycostexpenses> listplanpack1 =new ArrayList<entitycostexpenses>();
    
  	private MyAdapter adapter;
    //项目id
  	private String id;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	entitycostexpenses ecp;
	String url =InterfaceConfig.COST_expenses_URL;
	
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
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}

				onLoad();
				break;
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(), "已全部加载完成", Toast.LENGTH_SHORT).show();
				break;
			case 4:
			    
				listView.setAdapter(adapter);
	            listView.setSelection(position_return);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				switch (showToast) {
				//input
				case 1:
					Toast.makeText(CostExpensesActivity.this, "审核成功",Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(CostExpensesActivity.this, "反审核成功",Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}

				break;
			case 5:
				Toast.makeText(getApplicationContext(), "暂无网络", 1).show();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			case 6:
				Toast.makeText(getApplicationContext(), "暂时无法成功获取", 1).show();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_expenses);
		
		initView();//初始化视图
		initData();
		
	}
	
	
	public void initData(){
		top_title.setText("费用管理费用单");
		initData(id, page+"", pagesize+"");
	}
	
	
	private void initData1() {
		initData1(id, page+"", pagesize+"",search_name);
	}
	
	
	/**
	 * 获取费用单列表
	 * @param projectId 项目ID
	 * @param page    页数
	 * @param size    数量
	 */
	private void initData(String projectId,String page,String size) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", page));
		params.add(new BasicNameValuePair("pageSize",  size));
		
		new MyTask().execute(params);
	}
	
	
	/**
	 * 获取搜索费用单列表
	 * @param projectId  项目ID
	 * @param page      页数
	 * @param size        数量
	 * @param keyword      关键字
	 */
	private void initData1(String projectId,String page,String size,String keyword) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", page));
		params.add(new BasicNameValuePair("pageSize",  size));
		params.add(new BasicNameValuePair("keyword",  keyword));

		new MyTask().execute(params);
	}
	
	
	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{

		@Override
        protected void onPreExecute() {
     		if(page==1){
     			
        	 sdDialog = new com.superdata.pm.view.SDProgressDialog(CostExpensesActivity.this);
        	 if(!sdDialog.isShow())
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
					    if(flag3){
					    	int totalSize =jaresultList1.getInt("totalSize");
					    	if(totalSize>page*pagesize){
					       		isLastPage =false;
						    	}else{
						    		isLastPage =true;
						    	}
					    }
						listplanpack1.clear();
						for(int i=0;i<jaresultList.length();i++){
							String id =(jaresultList.getJSONObject(i).get("id")).equals(null)?"1":String.valueOf(jaresultList.getJSONObject(i).get("id"));
							String code =(jaresultList.getJSONObject(i).get("code")).equals(null)?"code_null":(String) jaresultList.getJSONObject(i).get("code");
							String amount =(jaresultList.getJSONObject(i).get("amount")).equals(null)?"1111":String.valueOf(jaresultList.getJSONObject(i).get("amount"));
							String remark =(jaresultList.getJSONObject(i).get("remark")).equals(null)?"暂无描述":(String) jaresultList.getJSONObject(i).get("remark");
							String isverify =(jaresultList.getJSONObject(i).get("status")).equals(null)?"0":String.valueOf(jaresultList.getJSONObject(i).get("status"));
							String auditName =(jaresultList.getJSONObject(i).get("auditName")).equals(null)?"未知":(String) jaresultList.getJSONObject(i).get("auditName");
							String date =(jaresultList.getJSONObject(i).get("billDate")).equals(null)?"2014-01-01":((String) jaresultList.getJSONObject(i).get("billDate")).substring(0,10);
							String projectName =(jaresultList.getJSONObject(i).get("projectName")).equals(null)?"无":(String) jaresultList.getJSONObject(i).get("projectName");

							entitycostexpenses epp=new entitycostexpenses(id, code, amount, remark, isverify, auditName, date,projectName);
			                listplanpack1.add(epp);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
		     Message msg =new Message();
		     
		     //用于判断是否修改了值
		     if(flag3){

		     //加载更多 时
		     if(!flag){
		    	 listplanpack.addAll(listplanpack1);
		    	 listplanpack1.clear();
				adapter = new MyAdapter(CostExpensesActivity.this);
		        msg.what =2;
		     }else{
		    	 //第一次
		    	
		    		 //重新加载全部的
			    	 listplanpack.clear();
			    	 listplanpack.addAll(listplanpack1);
			    	 listplanpack1.clear();
						adapter = new MyAdapter(CostExpensesActivity.this);
		           	 msg.what =1;
		     }
		     }else{
		    	 listplanpack.get(position_return-1).setIsverify(listplanpack1.get(0).getIsverify());
		    	 listplanpack.get(position_return-1).setAuditname(listplanpack1.get(0).getAuditname());		
	      	     flag3 =true;
	    	     adapter = new MyAdapter(CostExpensesActivity.this);
             	 msg.what =4;
		     }
			     if(sdDialog.isShow())
				sdDialog.cancel();
			     handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	
	
	
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		costex_etsearch = (EditText) findViewById(R.id.costex_etsearch);
		
		btnSearch = (ImageButton) findViewById(R.id.costex_btnsearch);
		id = getIntent().getExtras().getString("projectId");
		sdDialog = new SDProgressDialog(this);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		listView = (XListView) findViewById(R.id.costex_lv);
		listView.setOnItemClickListener(new CostExpensesListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}
	
	
	private class CostExpensesListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent =new Intent(CostExpensesActivity.this,
					CostExpensesDetailActivity.class);
			intent.putExtra("ID", listplanpack.get(position-1).getId());
			intent.putExtra("POSITION", position);
			intent.putExtra("STATUS", listplanpack.get(position-1).getIsverify());
			intent.putExtra("CODE", listplanpack.get(position-1).getCode());
			startActivityForResult(intent, 1);
		}
		
	}
	
	
	@Override
	public void onRefresh() {
		if(!flag2){
			page = 1;
			initData(id, page+"", pagesize+"");
			onLoad();
		}else{
			// 进入搜索的刷新
			page = 1;
			initData1(id, page+"", pagesize+"",search_name);
			onLoad();

		}
	}

	@Override
	public void onLoadMore() {
		if(!flag2){
			if(!isLastPage){
				if(flag){
					flag =false;
					++page;
					initData(id, page+"", pagesize+"");
				}
			}else{
				handler.sendEmptyMessage(3);
			}
		}else{
			if(!isLastPage){
				if(flag){
					flag =false;
					++page;
					initData1(id, page+"", pagesize+"",search_name);
				}
			}else{
				handler.sendEmptyMessage(3);
					
			}
		}
		
		
	}

	@Override
	public void onClick(View v) {
		if(v == btnBack){
			onBackPressed();
		}
		
		if(v == btnSearch){
			search_name = costex_etsearch.getText().toString();
			flag2 = true;
			page = 1;
			initData1();
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		//审核通过
		case 1:
			showToast =1;
			position_return =data.getExtras().getInt("POSITION");
		    flag3 =false;
			initData(id, position_return+"", 1+"");
			break;
		//审核不通过
		case 2:
			showToast =2;
			position_return =data.getExtras().getInt("POSITION");
		    flag3 =false;
			initData(id, position_return+"", 1+"");
			break;
		default:
			break;
	}

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/*//返回按钮
	private ImageView back;
	//费用管理条目
	private XListView listView;
	//获取的是第几页的数据
    private int page =1;
    //获取的这页的数量
    private int pagesize =1;
    //Adapter里面放的List，
    private List<entitycostexpenses> listplanpack =new ArrayList<entitycostexpenses>();
    //临时的List，用于加载更多 的逻辑处理
    private List<entitycostexpenses> listplanpack1 =new ArrayList<entitycostexpenses>();
	private com.superdata.pm.view.SDProgressDialog sdDialog;
	//搜索的关键字
	private String search_name="";
	//标识是否加载更多 
    private boolean flag =true;
    //用来判断是否输入了关键字
    private boolean flag2 =false;
    //用于判断是否修改了值
	private boolean flag3=true;
	private List<String> nameList = new ArrayList<String>();

	private boolean isLastPage=false;

	//搜索按钮
	private Button btn_search;
	private MyAdapter adapter;
    //返回的位置 ，用于再次刷新这个条目 
	private int position_return;
	//存的是项目
    Map<Integer, List<String>> map =new HashMap<Integer, List<String>>();
	private ArrayList<String> list;
	private CustemSpinerAdapter mAdapter;
	private SpinerPopWindow mSpinerPopWindow;
	private TextView costex_projectby_items;
	private ImageView costex_projectby_iv;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_expenses);
		//初始化布局
		initView();
		initTopSelect();

	
		//设置监听
		setListener(); 
		
	}
	private void initTopSelect() {
    	mAdapter = new CustemSpinerAdapter(this);
		costex_projectby_iv =(ImageView)this.findViewById(R.id.costex_projectby_iv);
		costex_projectby_items =(TextView)this.findViewById(R.id.costex_projectby_items);
		costex_projectby_iv.setOnClickListener(listener);
    	mAdapter = new CustemSpinerAdapter(this);
		mSpinerPopWindow = new SpinerPopWindow(this);
		mSpinerPopWindow.setItemListener(this);		

		intitData();

	}
	
	public void intitData(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pageNum",1+""));
		params.add(new BasicNameValuePair("pageSize", 100+""));
		new MyTask1().execute(params);
	}
	
	class MyTask1 extends AsyncTask<List<NameValuePair>, Integer, String>{
		String url = InterfaceConfig.PROJECT_LIST_URL1;

		@Override
		protected void onPreExecute() {
       	 sdDialog = new com.superdata.pm.view.SDProgressDialog(CostExpensesActivity.this);
   	    	sdDialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			String json = null;
			try {
				json = new SDHttpClient().post_session(url, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result != null){
				try {
					JSONObject jRoot = new JSONObject(result);
//					list = new ArrayList<HashMap<String,Object>>();
					JSONArray resultList = jRoot.getJSONArray("resultList");
					for(int i=0; i<resultList.length(); i++){
						String name =(String)(resultList.getJSONObject(i).get("name"));//项目名称
						Integer id = (Integer)(resultList.getJSONObject(i).get("id"));//项目id
					     list = new ArrayList<String>();
				    	 list.add(id+"");
					     list.add(name);
					     map.put(i,list);
					}
				} catch (JSONException e) {
					Message msg =new Message();
					msg.what =6;
					handler.handleMessage(msg);
					e.printStackTrace();
				}
				Message msg =new Message();
				msg.what =7;
				handler.handleMessage(msg);


				
			}else{
				Message msg =new Message();
				msg.what =5;
				handler.handleMessage(msg);
			}
			
			
			
		}
	
	}
	
	
	
	
	
	
	

	@Override
	public void onItemClick(int pos) {
		costex_projectby_items.setText(nameList.get(pos));
		id =map.get(pos).get(0);
		isLastPage=false;
		page=1;
		initData();
	
	}
	
	private void showSpinWindow() {
		mSpinerPopWindow.setWidth(costex_projectby_items.getWidth());
		mSpinerPopWindow.showAsDropDown(costex_projectby_items);
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
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}

				onLoad();
				break;
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(), "已全部加载完成", Toast.LENGTH_SHORT).show();
				break;
			case 4:
			    
				listView.setAdapter(adapter);
	            listView.setSelection(position_return);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				switch (showToast) {
				//input
				case 1:
					Toast.makeText(CostExpensesActivity.this, "审核成功",Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(CostExpensesActivity.this, "反审核成功",Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}

				break;
			case 5:
				Toast.makeText(getApplicationContext(), "暂无网络", 1).show();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			case 6:
				Toast.makeText(getApplicationContext(), "暂时无法成功获取", 1).show();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			case 7:
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}

				for(int i=0;i<map.size();i++){
					nameList.add(map.get(i).get(1));
				}
				costex_projectby_items.setText(nameList.get(0));
				id=map.get(0).get(0);
				costex_projectby_iv.setOnClickListener(listener);
				mAdapter.refreshData(nameList, 0);
				mSpinerPopWindow.setAdatper(mAdapter);
				//获取数据
				 initData();

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

	private void initData() {
		initdata(id, page+"", pagesize+"");
	}
	private void initData1() {
		initdata1(id, page+"", pagesize+"",search_name);
	}
	*//**
	 * 获取费用单列表
	 * @param projectId 项目ID
	 * @param page    页数
	 * @param size    数量
	 *//*
	private void initdata(String projectId,String page,String size) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", page));
		params.add(new BasicNameValuePair("pageSize",  size));
		//params.add(new BasicNameValuePair("keyword",  "1"));
		
		new MyTask().execute(params);
	}
	*//**
	 * 获取搜索费用单列表
	 * @param projectId  项目ID
	 * @param page      页数
	 * @param size        数量
	 * @param keyword      关键字
	 *//*
	private void initdata1(String projectId,String page,String size,String keyword) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", page));
		params.add(new BasicNameValuePair("pageSize",  size));
		params.add(new BasicNameValuePair("keyword",  keyword));

		new MyTask().execute(params);
	}

//	String url ="http://192.168.0.117:8080/pm/costOrderInterface/queryFeeOrder.do";
	private EditText costex_etsearch;
	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =InterfaceConfig.COST_expenses_URL;

		@Override
        protected void onPreExecute() {
     		if(page==1){
     			
        	 sdDialog = new com.superdata.pm.view.SDProgressDialog(CostExpensesActivity.this);
        	 if(!sdDialog.isShow())
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
					    if(flag3){
					    	int totalSize =jaresultList1.getInt("totalSize");
					    	if(totalSize>page*pagesize){
					       		isLastPage =false;
						    	}else{
						    		isLastPage =true;
						    	}
					    }
						listplanpack1.clear();
						for(int i=0;i<jaresultList.length();i++){
							String id =(jaresultList.getJSONObject(i).get("id")).equals(null)?"1":String.valueOf(jaresultList.getJSONObject(i).get("id"));
							String code =(jaresultList.getJSONObject(i).get("code")).equals(null)?"code_null":(String) jaresultList.getJSONObject(i).get("code");
							String amount =(jaresultList.getJSONObject(i).get("amount")).equals(null)?"1111":String.valueOf(jaresultList.getJSONObject(i).get("amount"));
							String remark =(jaresultList.getJSONObject(i).get("remark")).equals(null)?"暂无描述":(String) jaresultList.getJSONObject(i).get("remark");
							String isverify =(jaresultList.getJSONObject(i).get("status")).equals(null)?"0":String.valueOf(jaresultList.getJSONObject(i).get("status"));
							String auditName =(jaresultList.getJSONObject(i).get("auditName")).equals(null)?"未知":(String) jaresultList.getJSONObject(i).get("auditName");
							String date =(jaresultList.getJSONObject(i).get("billDate")).equals(null)?"2014-01-01":((String) jaresultList.getJSONObject(i).get("billDate")).substring(0,10);
							String projectName =(jaresultList.getJSONObject(i).get("projectName")).equals(null)?"无":(String) jaresultList.getJSONObject(i).get("projectName");

							entitycostexpenses epp=new entitycostexpenses(id, code, amount, remark, isverify, auditName, date,projectName);
			                listplanpack1.add(epp);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
		     Message msg =new Message();
		     
		     //用于判断是否修改了值
		     if(flag3){

		     //加载更多 时
		     if(!flag){
		    	 listplanpack.addAll(listplanpack1);
		    	 listplanpack1.clear();
				adapter = new MyAdapter(CostExpensesActivity.this);
		        msg.what =2;
		     }else{
		    	 //第一次
		    	
		    		 //重新加载全部的
			    	 listplanpack.clear();
			    	 listplanpack.addAll(listplanpack1);
			    	 listplanpack1.clear();
						adapter = new MyAdapter(CostExpensesActivity.this);
		           	 msg.what =1;
		     }}else{
		    	 listplanpack.get(position_return-1).setIsverify(listplanpack1.get(0).getIsverify());
		    	 listplanpack.get(position_return-1).setAuditname(listplanpack1.get(0).getAuditname());		
	      	     flag3 =true;
	    	     adapter = new MyAdapter(CostExpensesActivity.this);
             	 msg.what =4;
		     }
			     if(sdDialog.isShow())
				sdDialog.cancel();
			     handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	
	private void initView() {
		costex_etsearch =(EditText)this.findViewById(R.id.costex_etsearch);
	back=(ImageView)this.findViewById(R.id.costex_iv_back);
	listView=	(XListView)this.findViewById(R.id.costex_lv);
	listView.setOnItemClickListener(listener1);
	listView.setCacheColorHint(0);
	listView.setPullLoadEnable(true);
	listView.setXListViewListener(this);
	listView.setHeaderDividersEnabled(false);
	listView.setFooterDividersEnabled(false);
	btn_search=(Button)this.findViewById(R.id.costex_btnsearch);
	}
	
	private void setListener() {
	    back.setOnClickListener(listener);
		btn_search.setOnClickListener(listener);
	}
	
	OnItemClickListener listener1 =new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent =new Intent(CostExpensesActivity.this, CostExpensesDetailActivity.class);
			intent.putExtra("ID", listplanpack.get(position-1).getId());
			intent.putExtra("POSITION", position);
			intent.putExtra("STATUS", listplanpack.get(position-1).getIsverify());
			startActivityForResult(intent, 1);

		}
	};
	private int showToast;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		//审核通过
		case 1:
			showToast =1;
			position_return =data.getExtras().getInt("POSITION");
		    flag3 =false;
			initdata(id, position_return+"", 1+"");
			break;
		//审核不通过
		case 2:
			showToast =2;
			position_return =data.getExtras().getInt("POSITION");
		    flag3 =false;
			initdata(id, position_return+"", 1+"");
			break;
		default:
			break;
	}

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	OnClickListener listener =new OnClickListener() {
		
		

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.costex_iv_back:
				onBackPressed();
				break;
			case R.id.costex_btnsearch:
				  search_name = costex_etsearch.getText().toString();
				  flag2=true;
				  page =1;
				  initData1();
				break;
			case R.id.costex_projectby_iv:
				showSpinWindow();
                break;
			default:
				break;
			}
			
		}
	};*/

	public class GroupHolder {
		//单号
		 TextView costex_item_tv_humannumber;
		//总金额
		 TextView costex_item_tv_money;
		//费用描述
		 TextView costex_item_tv_pay;
		//是否审核
		 TextView costex_item_tv_isverify;
		//费用日期
		 TextView costex_item_tv_data;
        //所属项目
		 TextView costex_item_tv_projectby;
	}

	
	public class MyAdapter extends BaseAdapter{
        private Context context;
        private LayoutInflater inflater;
		
		public MyAdapter(Context context) {
			this.context =context;
			inflater=LayoutInflater.from(context);
		}

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
		public View getView(int position, View convertView, ViewGroup parent) {
		GroupHolder groupHolder;
			if(convertView==null){
				groupHolder=new GroupHolder();
				convertView=inflater.inflate(R.layout.cost_expenses_item, null);
				groupHolder.costex_item_tv_humannumber=(TextView)convertView.findViewById(R.id.costex_item_tv_humannumber);
				groupHolder.costex_item_tv_money=(TextView)convertView.findViewById(R.id.costex_item_tv_money);
				groupHolder.costex_item_tv_pay=(TextView)convertView.findViewById(R.id.costex_item_tv_pay);
				groupHolder.costex_item_tv_isverify=(TextView)convertView.findViewById(R.id.costex_item_tv_isverify);
				groupHolder.costex_item_tv_data=(TextView)convertView.findViewById(R.id.costex_item_tv_data);
			    groupHolder.costex_item_tv_projectby=(TextView)convertView.findViewById(R.id.costex_item_tv_projectby);
				convertView.setTag(groupHolder);
			}else{
				groupHolder=(GroupHolder)convertView.getTag();
			}
			
			
			groupHolder.costex_item_tv_humannumber.setText(listplanpack.get(position).getCode());
			groupHolder.costex_item_tv_money.setText(listplanpack.get(position).getAmount());
			groupHolder.costex_item_tv_pay.setText(listplanpack.get(position).getRemark());
			if(listplanpack.get(position).getIsverify().equals("0")){
				groupHolder.costex_item_tv_isverify.setText("未审核");
			}else{
				groupHolder.costex_item_tv_isverify.setText("审核通过         审核人"+listplanpack.get(position).getAuditname());

			}
			groupHolder.costex_item_tv_data.setText(listplanpack.get(position).getDate());
			groupHolder.costex_item_tv_projectby.setText(listplanpack.get(position).getProjectName());
	

			
			return convertView;
		}
		
	}

	
}
