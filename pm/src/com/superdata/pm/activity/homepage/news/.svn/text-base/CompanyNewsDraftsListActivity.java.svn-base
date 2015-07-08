package com.superdata.pm.activity.homepage.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.homepage.news.CompanyNewsDraftsActivity.MyAdapter;
import com.superdata.pm.activity.homepage.news.CompanyNewsDraftsActivity.MyTask;
import com.superdata.pm.activity.homepage.news.CompanyNewsOutboxListActivity.MyAdapter.Holder;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.IMsg;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

public class CompanyNewsDraftsListActivity extends BaseActivity implements
		IXListViewListener, OnClickListener {
	
	private ImageView btnBack;
	private TextView top_title;
	private ImageButton ibSearch;
	private ImageButton iv_top_window;
	private PopupWindow popupwindow;
	//搜索输入框
	private EditText et_companynews_drafts_search;
	
	
	private MyAdapter adapter;
	private List<IMsg> list = new ArrayList<IMsg>();
	private List<IMsg> list1 = new ArrayList<IMsg>();
	private XListView listView;
	
	//用户id
	private String empId;
	//消息id
	private String id;
	//页码
	private int pageNum = 1;
	//每页大小
	private int pageSize = 10;
	//加载标识
	private boolean flag = true;
	//关键字标识
	private boolean flag1 =false;
	//末页标识
	private boolean isLastPage = false;
	//关键字
	private String keyword = "";
	//发送状态   1为发送消息  0为保存消息
	private int status = 0;
	
	CheckBox[] checkBoxItems;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	Message msg;
	IMsg iMsg;
	IMsg iMsg1;
	String url = InterfaceConfig.NEWSOUTBOX_LIST_URL;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companynew_list);
		
		//初始化视图
		initView();
		//初始化数据
		initData();
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
				listView.smoothScrollToPosition(list.size());
				
				adapter.notifyDataSetChanged();
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				
				onLoad();
				break;
				
			case 3:
				onLoad();
				Toast.makeText(CompanyNewsDraftsListActivity.this,
						"已全部加载完成", Toast.LENGTH_SHORT).show();;
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
	
	
	public void initData(){
		top_title.setText("草稿箱");
		initData(empId, pageNum+"", pageSize+"", status+"");
	}
	
	public void initData(String empId, String pageNum, String pageSize, String status){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("status", status));
		
		new MyTask().execute(params);
	}
	
	
	public void initDataSearch(){
		initDataSearch(empId, pageNum+"", pageSize+"", status+"", keyword);
	}
	
	
	public void initDataSearch(String empId, String pageNum, String pageSize,
			String status, String keyword){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("status", status));
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
					
					list1.clear();
					
					for(int i=0; i<resultList.length(); i++){
						//接收者
						String receiveName = (String) (resultList.getJSONObject(i).get("receiveName").equals(null)?
								"":resultList.getJSONObject(i).get("receiveName"));
						//标题
						String msgName = (String) resultList.getJSONObject(i).get("msgName");
						//内容
						String content = (String) resultList.getJSONObject(i).get("content");
						//接收时间
						String createDate = (String) resultList.getJSONObject(i).get("createDate");
						//消息id
						Integer id = (Integer) resultList.getJSONObject(i).get("id");
						//收件人id
						String receiveId = (String) resultList.getJSONObject(i).get("receiveId");
						
						iMsg1 = new IMsg(content, receiveName, createDate, msgName, id, receiveId);
						
						list1.add(iMsg1);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			msg = new Message();
			
			//加载更多时
			if(!flag){
				
				list.addAll(list1);
				list1.clear();
				adapter = new MyAdapter();
				msg.what = 2;
				
			}else{
				//重新加载全部
				list.clear();
				list.addAll(list1);
				adapter = new MyAdapter();
				msg.what = 1;
			}
			
			if(sdDialog.isShow()){
				sdDialog.cancel();
			}
			
			checkBoxItems = new CheckBox[list.size()];
			handler.sendMessage(msg);
			
			super.onPostExecute(result);
		}
		
	}
	
	
	
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title_window);
		et_companynews_drafts_search = (EditText) findViewById(R.id.et_companynews_drafts_search);
		ibSearch = (ImageButton) findViewById(R.id.ib_companynews_list_search);
		btnBack = (ImageView) findViewById(R.id.ll_top_title_window);
		iv_top_window = (ImageButton) findViewById(R.id.iv_top_window);
		
		btnBack.setOnClickListener(this);
		ibSearch.setOnClickListener(this);
		iv_top_window.setOnClickListener(this);
		
		//得到用户id
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(CompanyNewsDraftsListActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);
				
		sdDialog = new SDProgressDialog(this);
		iMsg = new IMsg();
				
		listView = (XListView) findViewById(R.id.lv_companeynews_list);
		listView.setOnItemClickListener(new DraftsListItemClickListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}
	
	
	private class DraftsListItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(CompanyNewsDraftsListActivity.this,
					CompanyNewsDraftsDetailActivity.class);
			intent.putExtra("id", list.get(position-1).getId());
			intent.putExtra("empId", empId);
			intent.putExtra("receiveId", list.get(position-1).getReceiveId());
			startActivity(intent);
			finish();
		}
		
	}
	
	
	@Override
	public void onClick(View v) {
		if(v == btnBack){
			onBackPressed();
		}
		
		
		if(v == ibSearch){
			keyword = et_companynews_drafts_search.getText().toString();
			flag1 = true;
			pageNum = 1;
			initDataSearch();
		}
		
		if(v == iv_top_window){
			if(popupwindow != null && popupwindow.isShowing()){
				popupwindow.dismiss();
				return;
			}else{
				initPopuowindow();
			}
		}
		
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
	
	
	public void initPopuowindow(){
		View view = getLayoutInflater().inflate(R.layout.news_dialog, null, false);
		popupwindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		//设置popupwindow背景
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
		//popupwindow获得焦点
		popupwindow.setFocusable(true);
		//popupwindow显示的位置
		popupwindow.showAsDropDown(iv_top_window);
		
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(popupwindow != null && popupwindow.isShowing()){
					popupwindow.dismiss();
					popupwindow = null;
				}
				return false;
			}
		});
	}
	
	
	public void addMsg(View v){
		IntentUtils.gotoActivity(this, CompanyNewMessageActivity.class);
		popupwindow.dismiss();
		finish();
	} 
	
	public void deleteMsg(View v){
		IntentUtils.gotoActivity(this, CompanyNewsDraftsActivity.class);
		popupwindow.dismiss();
		finish();
	}
	

	@Override
	public void onRefresh() {
		if(!flag1){
			pageNum = 1;
			initData(empId, pageNum+"", pageSize+"", status+"");
			onLoad();
			
		}else{
			//搜索刷新
			pageNum = 1;
			initDataSearch(empId,pageNum+"",pageSize+"",  status+"", keyword);
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
					initData(empId, pageNum+"", pageSize+"", status+"");
				}
				
			}else{
				handler.sendEmptyMessage(3);
			}
		}else{
			if(!isLastPage){
				
				if(flag){
					flag = false;
					++pageNum;
					initDataSearch(empId,pageNum+"",pageSize+"",  status+"", keyword);
				}
			}else{
				
				handler.sendEmptyMessage(3);
			}
		}

	}
	
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
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
						.inflate(R.layout.companynews_oubox_listitem, null);
				
				holder.tvReceiveName = (TextView) convertView
						.findViewById(R.id.tv_outbox_listitem_receiver);
				holder.tvMsgName = (TextView) convertView
						.findViewById(R.id.tv_outbox_listitem_title);
				holder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_outbox_listview_content);
				holder.tvCreateDate = (TextView) convertView
						.findViewById(R.id.tv_outbox_listitem_sendtime);
				checkBoxItems[position] = (CheckBox) convertView
						.findViewById(R.id.cb_outbox_listitem);
				
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
				
				checkBoxItems[position] = (CheckBox) convertView
						.findViewById(R.id.cb_outbox_listitem);
			}
			
			IMsg bean = list.get(position);
			
			holder.tvReceiveName.setText(bean.getReceiveName());
			holder.tvMsgName.setText(bean.getMsgName());
			holder.tvContent.setText(bean.getContent());
			holder.tvCreateDate.setText(bean.getCreateDate());
			checkBoxItems[position].setVisibility(View.INVISIBLE);
			
			return convertView;
		}
		
		class Holder{
			//单选按钮
			CheckBox cbCheck;
			//接收者
			TextView tvReceiveName;
			//消息标题
			TextView tvMsgName;
			//消息内容
			TextView tvContent;
			//发送时间
			TextView tvCreateDate;
		}
		
	}

}
