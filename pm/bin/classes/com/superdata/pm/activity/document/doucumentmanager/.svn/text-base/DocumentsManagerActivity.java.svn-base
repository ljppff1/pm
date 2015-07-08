package com.superdata.pm.activity.document.doucumentmanager;

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
import com.superdata.pm.entity.IFiles;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 文档-->企业文库
 * @author kw
 *
 */
public class DocumentsManagerActivity extends BaseActivity implements IXListViewListener,
				OnClickListener{
	
	private TextView top_title;
	private ImageView btnBack;
	private ImageButton btnSearch;
	private EditText et_companydocuments_search;
	
	private List<IFiles> iList = new ArrayList<IFiles>();
	private List<IFiles> iList1 = new ArrayList<IFiles>();
	private MyAdapter adapter;
	private XListView listView;
	
	private int pageNum = 1;//页码
	private int pageSize = 10;//每页大小
	private boolean flag = true;//加载标识
	private boolean flag1 = false;//搜索标识
	private boolean isLastPage = true;//是否最后一页
	private String keyWord = "";// 搜索的关键字
	private int id;//文档id
	
	
	IFiles iFiles;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	String url = InterfaceConfig.DOCUMENTSMANAGER_URL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.documentsmanager);
		
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
				listView.smoothScrollToPosition(iList.size());
				adapter.notifyDataSetChanged();
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				onLoad();
				break;
				
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(),
						"已全部加载完成", Toast.LENGTH_SHORT).show();
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
	public void initData() {
		top_title.setText("企业文库");
		
		initData(pageNum+"",pageSize+"");
	}
	
	/**
	 * 企业文库列表
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 */
	public void initData(String pageNum,String pageSize){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		
		new MyTask().execute(params);
	}
	
	
	
	public void initDataSearch(){
		initDataSearch(pageNum+"",pageSize+"",keyWord);
	}
	
	
	/**
	 * 企业文库搜索列表
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 * @param searchValue	关键字
	 */
	public void initDataSearch(String pageNum,String pageSize,
			String searchValue){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("searchValue", searchValue));
		
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
						iList1.clear();
						
						for(int i=0; i<resultList.length(); i++){
							//文档编号
							String code = (String) resultList.getJSONObject(i).get("code");
							//文档名称
							String name = (String) resultList.getJSONObject(i).get("name");
							//文档类别
							String typeName = (String) resultList.getJSONObject(i).get("typeName");
							//文档内容
							String content = (String) resultList.getJSONObject(i).get("content");
							//创建人
							String createName = (String) resultList.getJSONObject(i).get("createName");
							//创建时间
							String createDate = (String) resultList.getJSONObject(i).get("createDate");
							
							Integer id = (Integer) resultList.getJSONObject(i).get("id");
							
							IFiles iFiles1 = new IFiles(id, code, name, typeName,
									createName, createDate, content);
							iList1.add(iFiles1);
						}
					}else{
						Toast.makeText(DocumentsManagerActivity.this,
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
				iList.addAll(iList1);
				iList1.clear();
				adapter = new MyAdapter();
				msg.what = 2;
			}else{
				//重新加载全部
				iList.clear();
				iList.addAll(iList1);
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
		
		if(v == btnSearch){
			
			keyWord = et_companydocuments_search.getText().toString();
			flag1 = true;
			pageNum = 1;
			initDataSearch();
			
		}
		
		
	}

	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_companydocuments_search = (EditText) findViewById(R.id.et_companydocuments_search);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch = (ImageButton) findViewById(R.id.btn_companydocuments_search);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		sdDialog = new SDProgressDialog(this);
		iFiles =  new IFiles();
		
		listView = (XListView) findViewById(R.id.lv_companydocuments_list);
		listView.setOnItemClickListener(new CompanyDocumentsAddListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}
	
	
	//listview监听事件
	private class CompanyDocumentsAddListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//跳转到文档详细
//			IntentUtils.gotoActivity(DocumentsManagerActivity.this, DocumentsManagerDetailActivity.class);
			Intent intent = new Intent(DocumentsManagerActivity.this,
					DocumentsManagerDetailActivity.class);
			intent.putExtra("id", iList.get(position-1).getId());
			startActivity(intent);
		}
		
		
	}
	
	@Override
	public void onRefresh() {
		if(!flag1){
			pageNum = 1;
			initData(pageNum+"",pageSize+"");
			onLoad();
		}else{
			//搜索刷新
			pageNum = 1;
			initDataSearch(pageNum+"",pageSize+"",keyWord);
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
					initData(pageNum+"",pageSize+"");
				}
			}else{
				handler.sendEmptyMessage(3);
			}
		}else{
			if(!isLastPage){
				if(flag){
					flag = false;
					++pageNum;
					initDataSearch(pageNum+"",pageSize+"",keyWord);
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
			return iList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return iList.get(position);
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
						.inflate(R.layout.documentsmanager_listitem, null);
				holder.tv_companydocuments_listitem_documentsnumbers = (TextView) convertView
						.findViewById(R.id.tv_companydocuments_listitem_documentsnumbers);
				holder.tv_companydocuments_listitem_documentsnames = (TextView) convertView
						.findViewById(R.id.tv_companydocuments_listitem_documentsnames);
				holder.tv_companydocuments_listitem_states = (TextView) convertView
						.findViewById(R.id.tv_companydocuments_listitem_states);
				holder.tv_companydocuments_listitem_categories = (TextView) convertView
						.findViewById(R.id.tv_companydocuments_listitem_categories);
				holder.tv_companydocuments_listitem_createtimes = (TextView) convertView
						.findViewById(R.id.tv_companydocuments_listitem_createtimes);
				holder.tv_companydocuments_listitem_creaters = (TextView) convertView
						.findViewById(R.id.tv_companydocuments_listitem_creaters);
				
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_companydocuments_listitem_documentsnumbers.setText(iList.get(position)
					.getCode().toString());
			holder.tv_companydocuments_listitem_documentsnames.setText(iList.get(position)
					.getName().toString());
			holder.tv_companydocuments_listitem_states.setText(iList.get(position)
					.getContent().toString());
			holder.tv_companydocuments_listitem_categories.setText(iList.get(position)
					.getTypeName().toString());
			holder.tv_companydocuments_listitem_createtimes.setText(iList.get(position)
					.getCreateDate().toString());
			holder.tv_companydocuments_listitem_creaters.setText(iList.get(position)
					.getCreateName().toString());
			
			
			return convertView;
		}
		
		
		class Holder{
			//文档编号
			TextView tv_companydocuments_listitem_documentsnumbers;
			//文档名称
			TextView tv_companydocuments_listitem_documentsnames;
			//说明
			TextView tv_companydocuments_listitem_states;
			//文档类别
			TextView tv_companydocuments_listitem_categories;
			//创建时间
			TextView tv_companydocuments_listitem_createtimes;
			//创建人
			TextView tv_companydocuments_listitem_creaters;
		}
		
	}

}
