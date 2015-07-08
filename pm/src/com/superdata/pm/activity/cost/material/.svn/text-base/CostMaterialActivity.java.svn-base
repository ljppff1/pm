package com.superdata.pm.activity.cost.material;

import java.text.DecimalFormat;
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
 * 材料管理类
 * @author lj
 *
 * 2014年6月28日
 */
public class CostMaterialActivity extends  BaseActivity implements
		IXListViewListener, OnClickListener {
    
	// 返回按钮
	private ImageView btnBack;
	// 搜索按钮
	private ImageButton btnSearch;
	private TextView top_title;
	private EditText costex_etsearch;

	// 费用管理条目
	private XListView listView;
	// 获取的是第几页的数据
	private int page = 1;
	// 获取的这页的数量
	private int pagesize = 10;
	// 搜索的关键字
	private String search_name = "";
	// 标识是否加载更多
	private boolean flag = true;
	// 用来判断是否输入了关键字
	private boolean flag2 = false;
	// 用于判断是否修改了值
	private boolean flag3 = true;
	private boolean isLastPage = false;
	// 返回的位置 ，用于再次刷新这个条目
	private int position_return;
	private int showToast;
	// Adapter里面放的List，
	private List<entitycostexpenses> listplanpack = new ArrayList<entitycostexpenses>();
	// 临时的List，用于加载更多 的逻辑处理
	private List<entitycostexpenses> listplanpack1 = new ArrayList<entitycostexpenses>();

	private MyAdapter adapter;
	// 项目id
	private String id;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	entitycostexpenses ecp;
	String url =InterfaceConfig.COST_material_URL;
	
	private Handler handler = new Handler() {

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
				flag = true;
				listView.setAdapter(adapter);
				listView.setSelection(listplanpack.size() - pagesize);
				adapter.notifyDataSetChanged();// 数据变化刷新
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}

				onLoad();
				break;
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(), "已全部加载完成",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:

				listView.setAdapter(adapter);
				listView.setSelection(position_return);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				switch (showToast) {
				// input
				case 1:
					Toast.makeText(CostMaterialActivity.this, "审核成功",
							Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(CostMaterialActivity.this, "反审核成功",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}

				break;
			case 5:
				Toast.makeText(getApplicationContext(), "暂无网络", Toast.LENGTH_SHORT).show();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			case 6:
				Toast.makeText(getApplicationContext(), "暂时无法成功获取", Toast.LENGTH_SHORT).show();
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_expenses);
		
		//初始化布局
		initView();
		//初始化数据
		initData();
	}
	
	
	
	public void initData() {
		top_title.setText("职位管理费用单");
		initData(id, page + "", pagesize + "");
	}
	
	
	// 搜索列表
	private void initData1() {
		initData1(id, page + "", pagesize + "", search_name);
	}
	
	
	
	/**
	 * 获取费用单列表
	 * 
	 * @param projectId
	 *            项目ID
	 * @param page
	 *            页数
	 * @param size
	 *            数量
	 */
	private void initData(String projectId, String page, String size) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", page));
		params.add(new BasicNameValuePair("pageSize", size));

		new MyTask().execute(params);
	}
	
	
	
	/**
	 * 获取搜索费用单列表
	 * 
	 * @param projectId
	 *            项目ID
	 * @param page
	 *            页数
	 * @param size
	 *            数量
	 * @param keyword
	 *            关键字
	 */
	private void initData1(String projectId, String page, String size,
			String keyword) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", page));
		params.add(new BasicNameValuePair("pageSize", size));
		params.add(new BasicNameValuePair("keyword", keyword));

		new MyTask().execute(params);
	}
	

//	String url ="http://192.168.0.118:8080/pm/costOrderInterface/queryProductOrder.do";
	
	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{

		@Override
        protected void onPreExecute() {
     		if(page==1){
        	 sdDialog = new SDProgressDialog(CostMaterialActivity.this);
        	 sdDialog.show();
     		}
        	super.onPreExecute();
        }
		
		
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			String aa = null;
			sdClient = new SDHttpClient();
			try {
				aa = sdClient.post_session(url, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return aa;
		}
		
		
		
		DecimalFormat df = new DecimalFormat("#.##");

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
							String id =(jaresultList.getJSONObject(i).get("id"))
									.equals(null)?"1":
										String.valueOf(jaresultList.getJSONObject(i).get("id"));
							String code =(jaresultList.getJSONObject(i).get("code"))
									.equals(null)?"code_null":
										(String) jaresultList.getJSONObject(i).get("code");
							String amount =(jaresultList.getJSONObject(i).get("amount"))
									.equals(null)?"0":
										String.valueOf(df.format(jaresultList.getJSONObject(i).get("amount")));
							String remark =(jaresultList.getJSONObject(i).get("remark"))
									.equals(null)?"暂无描述":
										(String) jaresultList.getJSONObject(i).get("remark");
							String isverify =(jaresultList.getJSONObject(i).get("status"))
									.equals(null)?"0":
										String.valueOf(jaresultList.getJSONObject(i).get("status"));
							String auditName =(jaresultList.getJSONObject(i).get("auditName"))
									.equals(null)?"未知":
										(String) jaresultList.getJSONObject(i).get("auditName");
							String date =(jaresultList.getJSONObject(i).get("billDate"))
									.equals(null)?"":
										((String) jaresultList.getJSONObject(i).get("billDate")).substring(0,10);
							String projectby =(jaresultList.getJSONObject(i).get("projectName"))
									.equals(null)?"未知":
										(String) jaresultList.getJSONObject(i).get("projectName");
							String projectId =(jaresultList.getJSONObject(i).get("projectId"))
									.equals(null)?"1":
										String.valueOf(jaresultList.getJSONObject(i).get("projectId"));
							
							entitycostexpenses epp=new entitycostexpenses(id, code, amount,
									remark, isverify, auditName, date, projectby, projectId);
							
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
		    	 adapter = new MyAdapter(CostMaterialActivity.this);
		        msg.what =2;
		     }else{
		    	 //第一次
		      //重新加载更多
		    	 listplanpack.clear();
		    	 listplanpack.addAll(listplanpack1);
		    	 listplanpack1.clear();
		    	 adapter = new MyAdapter(CostMaterialActivity.this);
	           	 msg.what =1;
		     }
		     }else{
		    	 listplanpack.get(position_return-1).setIsverify(listplanpack1.get(0).getIsverify());
		    	 listplanpack.get(position_return-1).setAuditname(listplanpack1.get(0).getAuditname());		
	      	     flag3 =true;
	    	     adapter = new MyAdapter(CostMaterialActivity.this);
             	 msg.what =4;
		     }		     
			     if(sdDialog.isShow())
				 sdDialog.cancel();
			     handler.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	
	
	
	private void initView() {
		top_title = (TextView) findViewById(R.id.tv_top_title);
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		costex_etsearch = (EditText) findViewById(R.id.costex_etsearch);

		//获取项目id
		id = getIntent().getExtras().getString("projectId");
		sdDialog = new SDProgressDialog(this);

		btnBack.setOnClickListener(this);

		listView = (XListView) findViewById(R.id.costex_lv);
		listView.setOnItemClickListener(new CostExpensesListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}
	
	
	private class CostExpensesListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(CostMaterialActivity.this,
					CostMaterialDetailActivity.class);
			intent.putExtra("ID", listplanpack.get(position - 1).getId());
			intent.putExtra("POSITION", position);
			intent.putExtra("STATUS", listplanpack.get(position - 1)
					.getIsverify());
			intent.putExtra("CODE", listplanpack.get(position-1).getCode());
			startActivityForResult(intent, 1);
		}

	}
	
	
	@Override
	public void onClick(View v) {
		// 点击返回
		if(v == btnBack){
			onBackPressed();
		}
		
		
		// 点击搜索
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

	
	
	public class GroupHolder {
		//费用单号
		 TextView costmat_item_tv_material;
		//总金额
		 TextView costmat_item_tv_money;
		//领用描述
		 TextView costmat_item_tv_receiver;
		//是否审核
		 TextView costmat_item_tv_isverify;
		//费用日期
		 TextView costmat_item_tv_date;
	    //所属项目
		 TextView costmat_item_tv_projectby;
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
			convertView=inflater.inflate(R.layout.cost_material_item, null);
			groupHolder.costmat_item_tv_material=(TextView)convertView.findViewById(R.id.costmat_item_tv_material);
			groupHolder.costmat_item_tv_money=(TextView)convertView.findViewById(R.id.costmat_item_tv_money);
			groupHolder.costmat_item_tv_receiver=(TextView)convertView.findViewById(R.id.costmat_item_tv_receiver);
			groupHolder.costmat_item_tv_isverify=(TextView)convertView.findViewById(R.id.costmat_item_tv_isverify);
			groupHolder.costmat_item_tv_projectby=(TextView)convertView.findViewById(R.id.costmat_item_tv_projectby);
			groupHolder.costmat_item_tv_date=(TextView)convertView.findViewById(R.id.costmat_item_tv_date);
			convertView.setTag(groupHolder);
		}else{
			groupHolder=(GroupHolder)convertView.getTag();
		}
			
		groupHolder.costmat_item_tv_material.setText(listplanpack.get(position).getCode());
		groupHolder.costmat_item_tv_money.setText(listplanpack.get(position).getAmount());
		groupHolder.costmat_item_tv_receiver.setText(listplanpack.get(position).getRemark());
		groupHolder.costmat_item_tv_projectby.setText(listplanpack.get(position).getProjectby());
		groupHolder.costmat_item_tv_date.setText(listplanpack.get(position).getDate());
	
		if(listplanpack.get(position).getIsverify().equals("0")){
			groupHolder.costmat_item_tv_isverify.setText("未审核");
		}else{
			groupHolder.costmat_item_tv_isverify.setText("审核通过         审核人"+listplanpack.get(position).getAuditname());
		}
			return convertView;
		}
		
	}


	@Override
	public void onRefresh() {
		if(!flag2){
			page = 1;
			initData(id, page+"", pagesize+"");
			onLoad();
		}else{
			//进入搜索的刷新
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
}
