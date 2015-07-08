package com.superdata.pm.activity.project.report;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.project.report.CheckReportListActivity.MyAdapter;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.PReportDetail;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 项目-->项目管理-->项目详细-->验收报告-->查看验收项
 * @author kw
 *
 */
public class CheckItemActivity extends BaseActivity implements IXListViewListener {

	private ImageView back;
	private TextView top_title;
	
	private List<HashMap<String, Object>> list;
	private CheckItemAdapter cIAdapter;
	private List<PReportDetail> pList = new ArrayList<PReportDetail>();
	private List<PReportDetail> pList1 = new ArrayList<PReportDetail>();
	private XListView listView;
	
	private int reportId;
	private int pageNum = 1;
	private int pageSize = 10;
	private boolean isLastPage = false;
	private boolean flag = true;
	private boolean flag1 = false;
	
	PReportDetail pReportDetail;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	String url = InterfaceConfig.CHECKITEM_URL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_checkreport_list);
		
		reportId = getIntent().getExtras().getInt("reportId");
		
		initView();//初始化视图
		initData();//初始化数据
		
	}
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				listView.setAdapter(cIAdapter);
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				cIAdapter.notifyDataSetChanged();
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				break;
			case 2:
				flag = true;
				listView.setAdapter(cIAdapter);
				listView.smoothScrollToPosition(pList.size());
				cIAdapter.notifyDataSetChanged();
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
	
	
	public void initData(){
		top_title.setText("验收项");
		
		initData(reportId+"",pageNum+"",pageSize+"");
	}
	
	public void initData(String reportId, String pageNum, String pageSize){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("reportId", reportId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		
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
		
		@Override
		protected void onPostExecute(String result) {
			if(null != result){
				try {
					JSONObject jRoot = new JSONObject(result);
					JSONObject jsonObject = jRoot.getJSONObject("resultData");
					
					String resultCode = (String) jRoot.get("resultCode");
					
					//判断resultCode是否为200，为200则查询成功，否则失败
					if("200".equals(resultCode.trim())){
						JSONArray resultList = jsonObject.getJSONArray("resultList");
						isLastPage = jsonObject.getBoolean("isLastPage");
						pList1.clear();
						
						for(int i=0; i<resultList.length(); i++){
							String name = (String) resultList.getJSONObject(i).get("name");//验收项
							String resultName = (String) resultList.getJSONObject(i).get("resultName");//验收结果
							String remark = (String) resultList.getJSONObject(i).get("remark");//备注
							
							PReportDetail pReportDetail1 = new PReportDetail(name, resultName, remark);
							pList1.add(pReportDetail1);
						}
					}else{
						Toast.makeText(CheckItemActivity.this, "查询失败",
								Toast.LENGTH_SHORT).show();
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
				cIAdapter = new CheckItemAdapter();
				msg.what = 2;
			}else{
				/*//第一次
				if(flag){
					pList.addAll(pList1);
					pList1.clear();
					cIAdapter = new CheckItemAdapter();
					msg.what = 1;
				}else{
					//重新加载全部
					pList.clear();
					pList.addAll(pList1);
					pList1.clear();
					cIAdapter = new CheckItemAdapter();
					msg.what = 1;
				}*/
				pList.clear();
				pList.addAll(pList1);
				cIAdapter = new CheckItemAdapter();
				msg.what = 1;
			}
			if(sdDialog.isShow()){
				sdDialog.cancel();
			}
			
			handler.sendMessage(msg);
			
			super.onPostExecute(result);
		}
	}
	
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		
		back = (ImageView) findViewById(R.id.ll_top_title);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 回退
				onBackPressed();
			}
		});
		
		sdDialog = new SDProgressDialog(this);
		pReportDetail = new PReportDetail();
		
		listView = (XListView) findViewById(R.id.lv_tab_checkreport_list);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	
	}
	
	@Override
	public void onRefresh() {
		/*flag = false;
		pageNum = 1;
		initData(reportId+"",pageNum+"",pageSize+"");
		onLoad();*/
		if(!flag1){
			pageNum = 1;
			initData(reportId+"",pageNum+"",pageSize+"");
			onLoad();
		}
	}

	@Override
	public void onLoadMore() {
		if(!isLastPage){
			if(flag){
				flag = false;
				++pageNum;
				initData(reportId+"",pageNum+"",pageSize+"");
			}
		}else{
			handler.sendEmptyMessage(3);
		}
	}
	
	
	/**
	 * 自定义适配器
	 * @author Administrator
	 *
	 */
	public class CheckItemAdapter extends BaseAdapter{
		
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
						.inflate(R.layout.tab_checkitem, null);
				
				holder.tv_checkitem_item = (TextView) convertView.findViewById(R.id.tv_checkitem_item);
				holder.tv_checkitem_result = (TextView) convertView.findViewById(R.id.tv_checkitem_result);
				holder.tv_checkitem_explain = (TextView) convertView.findViewById(R.id.tv_checkitem_explain);
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_checkitem_item.setText(pList.get(position).getName().toString());
			holder.tv_checkitem_result.setText(pList.get(position).getResultName().toString());
			holder.tv_checkitem_explain.setText(pList.get(position).getRemark().toString());
			
			return convertView;
		}
		
		class Holder{
			//验收项
			TextView tv_checkitem_item;
			//验收结果
			TextView tv_checkitem_result;
			//备注
			TextView tv_checkitem_explain;
		}
		
	}

	
	
}
