package com.superdata.pm.activity.homepage.news;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.IMsgDetail;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

public class CompanyNewsInboxListActivity extends BaseActivity implements
			IXListViewListener, OnClickListener{
	
	private ImageView btnBack;
	private TextView top_title;
	private EditText et_companynews_list_search;
	private ImageButton ibSearch;
	private ImageButton iv_top_window;
	private PopupWindow popupwindow;
//	private LinearLayout layout01;
//	private LinearLayout layout02;
	
	private List<IMsgDetail> list = new ArrayList<IMsgDetail>();
	private List<IMsgDetail> list1 = new ArrayList<IMsgDetail>();
	private XListView listView;
	private MyAdapter adapter;
	
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
	//是否输入关键字
	private boolean flag1 = false;
	//是否是最后一页
	private boolean isLastPage = false;
	//搜索的关键字
	private String keyword = "";
	CheckBox[] checkIteams;
	
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	IMsgDetail iMsgDetail;
	IMsgDetail iMsgDetail1;
	Message msg;
	String url = InterfaceConfig.NEWSINBOX_LIST_URL;
	
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
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				listView.setAdapter(adapter);
				
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				
				//通知更新
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
				Toast.makeText(CompanyNewsInboxListActivity.this,
						"已全部加载完成", Toast.LENGTH_SHORT).show();
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
		top_title.setText("收件箱");
		initData(empId, pageNum+"", pageSize+"");
	}
	
	/**
	 * 收件箱列表
	 * @param empId		用户id
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
		initDataSearch(empId, pageNum+"", pageSize+"", keyword);
	}
	
	/**
	 * 收件箱搜索列表
	 * @param empId		用户id
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 * @param keyword	搜索的关键字
	 */
	public void initDataSearch(String empId, String pageNum, String pageSize, String keyword){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
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
			if(result != null){
				try {
					JSONObject jRoot = new JSONObject(result);
					JSONArray resultList = jRoot.getJSONArray("resultList");
					isLastPage = jRoot.getBoolean("isLastPage");
					list1.clear();
					
					for(int i=0; i<resultList.length(); i++){
						//发送人
						String senderName = (String) resultList.getJSONObject(i).get("senderName");
						//标题
						String msgName = (String) (resultList.getJSONObject(i).get("msgName").equals(null)?
								"":resultList.getJSONObject(i).get("msgName"));
						//内容
						String msgContent = (String) (resultList.getJSONObject(i).get("msgContent").equals(null)?
								"":resultList.getJSONObject(i).get("msgContent"));
						//接收时间
						String receiveDate = (String) resultList.getJSONObject(i).get("receiveDate");
						//查看状态		0-未读，1-已读
						Integer status = (Integer) resultList.getJSONObject(i).get("status");
						//消息id
						Integer id = (Integer) resultList.getJSONObject(i).get("id");
						
						
						iMsgDetail1 = new IMsgDetail(msgName, msgContent,
								senderName, receiveDate, status, id);
						
						list1.add(iMsgDetail1);
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
			
			checkIteams = new CheckBox[list.size()];
			handler.sendMessage(msg);
			super.onPostExecute(result);
		}
		
	}
	
	
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title_window);
		et_companynews_list_search = (EditText) findViewById(R.id.et_companynews_list_search);
		btnBack = (ImageView) findViewById(R.id.ll_top_title_window);
		ibSearch = (ImageButton) findViewById(R.id.ib_companynews_list_search);
		iv_top_window = (ImageButton) findViewById(R.id.iv_top_window);
//		View view = LayoutInflater.from(this).inflate(R.layout.news_dialog, null);
//		layout01 = (LinearLayout) view.findViewById(R.id.ll_dialog01);
//		layout02 = (LinearLayout) view.findViewById(R.id.ll_dialog02);
		
		btnBack.setOnClickListener(this);
		ibSearch.setOnClickListener(this);
		iv_top_window.setOnClickListener(this);
//		layout01.setOnClickListener(this);
//		layout02.setOnClickListener(this);
		
		//得到用户id
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(CompanyNewsInboxListActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);
		
		sdDialog = new SDProgressDialog(this);
		iMsgDetail = new IMsgDetail();
		
		listView = (XListView) findViewById(R.id.lv_companeynews_list);
		listView.setOnItemClickListener(new InboxListItemClickListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		
	}
	
	private class InboxListItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//跳转到收件箱消息详细
			Intent intent = new Intent(CompanyNewsInboxListActivity.this,
					CompanyNewsInboxDetailActivity.class);
			
			intent.putExtra("id", list.get(position-1).getId());
			intent.putExtra("empId", empId);
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
			keyword = et_companynews_list_search.getText().toString();
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
		
		/*if(v == layout01){
			IntentUtils.gotoActivity(this, CompanyNewMessageActivity.class);
		}
		
		if(v == layout02){
			
		}*/
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
	
	
	
	public void addMsg(View v){
		IntentUtils.gotoActivity(this, CompanyNewMessageActivity.class);
		popupwindow.dismiss();
		finish();
	} 
	
	public void deleteMsg(View v){
		IntentUtils.gotoActivity(this, CompanyNewsInboxActivity.class);
		popupwindow.dismiss();
		finish();
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
	
	
	@Override
	public void onRefresh() {
		if(!flag1){
			pageNum = 1;
			initData(empId, pageNum+"", pageSize+"");
			onLoad();
		}else{
			//搜索刷新
			pageNum = 1;
			initDataSearch(empId,pageNum+"",pageSize+"",keyword);
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
					initData(empId, pageNum+"", pageSize+"");
				}
			}else{
				handler.sendEmptyMessage(3);
			}
			
		}else{
			if(!isLastPage){
				
				if(flag){
					flag = false;
					++pageNum;
					initDataSearch(empId,pageNum+"",pageSize+"",keyword);
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
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.companynews_inbox_listitem, null);
				holder = new Holder();
				
				holder.tv_companynews_inbox_title = (TextView) convertView
						.findViewById(R.id.tv_inbox_listitem_title);
				holder.tv_companynews_inbox_sender = (TextView) convertView
						.findViewById(R.id.tv_inbox_listitem_sender);
				holder.tv_companynews_inbox_sendtime = (TextView) convertView
						.findViewById(R.id.tv_inbox_listitem_sendtime);
				holder.tv_inbox_listitem_content = (TextView) convertView
						.findViewById(R.id.tv_inbox_listitem_content);
				holder.iv_inbox_listitem_status = (ImageView) convertView
						.findViewById(R.id.iv_inbox_listitem_status);
				checkIteams[position] = (CheckBox) convertView
						.findViewById(R.id.cb_inbox_listitem);
				
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
				checkIteams[position] = (CheckBox) convertView
						.findViewById(R.id.cb_inbox_listitem);
			}
			
			IMsgDetail bean = list.get(position);
			holder.tv_companynews_inbox_title.setText(bean.getMsgName());
			holder.tv_companynews_inbox_sender.setText(bean.getSenderName());
			holder.tv_companynews_inbox_sendtime.setText(bean.getReceiveDate());
			holder.tv_inbox_listitem_content.setText(bean.getMsgContent());
			if(bean.getStatus() == 0){
				holder.iv_inbox_listitem_status.setVisibility(View.VISIBLE);
			}else{
				holder.iv_inbox_listitem_status.setVisibility(View.INVISIBLE);
			}
			checkIteams[position].setVisibility(View.INVISIBLE);
			
			return convertView;
		}
		
		class Holder{
			CheckBox cbCheck;
			TextView tv_companynews_inbox_title;
			TextView tv_companynews_inbox_sender;
			TextView tv_companynews_inbox_sendtime;
			TextView tv_inbox_listitem_content;
			ImageView iv_inbox_listitem_status;
		}
		
	}

}
