package com.superdata.pm.activity.homepage.news;

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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.IMsgDetail;
import com.superdata.pm.entity.InBoxBean;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 首页-->站内消息-->收件箱
 * @author kw
 *
 */
public class CompanyNewsInboxActivity extends BaseActivity implements IXListViewListener,
					OnClickListener{

	private ImageView btnBack;
	private TextView top_title;
	private Button btnSelectAll;
	private Button btnDelete;
//	private Button btnNewMsg;
	private ImageButton btnSearch;
	private EditText et_companynews_inbox_search;
	
	private List<IMsgDetail> list = new ArrayList<IMsgDetail>();
	private List<IMsgDetail> list1 = new ArrayList<IMsgDetail>();
	private MyAdapter adapter;
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
	//是否输入关键字
	private boolean flag1 = false;
	//是否是最后一页
	private boolean isLastPage = false;
	//搜索的关键字
	private String keyword = "";
	//删除返回结果
	private String resultCode;
	//选中条目的个数
	private int chooseNum = 0;
	
	
	CheckBox[] checkIteams;
	Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
//	List<String> idList = new ArrayList<String>();
	
	
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	IMsgDetail iMsgDetail;
	IMsgDetail iMsgDetail1;
	Message msg;
	String url = InterfaceConfig.NEWSINBOX_LIST_URL;
	String deleteUrl = InterfaceConfig.NEWSINBOX_LIST_DELETE_URL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companynews_inbox);
		
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
				Toast.makeText(CompanyNewsInboxActivity.this,
						"已全部加载完成", Toast.LENGTH_SHORT).show();
				break;
				
			case 4:
				Toast.makeText(CompanyNewsInboxActivity.this,
						"请选择要删除的信息！", Toast.LENGTH_SHORT).show();
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
	
	
	
	//初始化方法
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
	
	
	public void initDataDelete(){
		StringBuffer sb=new StringBuffer();
		
		for(int i=0; i<isCheckMap.size(); i++){
//			list1 = adapter.getList();
			id = list1.get(i).getId()+"";
			if(isCheckMap.get(i)){
				sb.append(id+",");
			}
		}
		String id1 = sb.toString();
		String id2 = id1.substring(0, id1.lastIndexOf(","));
//		Log.d("sb------------", id2);
		initDataDelete(empId, id2);
	}
	
	
	public void initDataDelete(String empId,String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("id", id));
		
		new DeleteTask().execute(params);
	}
	
	
	class DeleteTask extends AsyncTask<List<NameValuePair>, Integer, String>{

		@Override
		protected void onPreExecute() {
			if(pageNum == 1){
				if(sdDialog.isShow()){
					sdDialog.cancel();
				}
				sdDialog.show();
			}
			super.onPreExecute();
		}
		
		
		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			sdClient = new SDHttpClient();
			String json = null;
			
			try {
				json = sdClient.post_session(deleteUrl, params[0]);
				Log.v("---json---", json);
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
					resultCode = jRoot.getString("resultCode");
					
					if("200".equals(resultCode)){
//						initData(empId, pageNum+"", pageSize+"");
						Toast.makeText(CompanyNewsInboxActivity.this, "删除成功！",
								Toast.LENGTH_SHORT).show();
						IntentUtils.gotoActivity(CompanyNewsInboxActivity.this,
								CompanyNewsInboxListActivity.class);
						finish();
						
					}else{
						Toast.makeText(CompanyNewsInboxActivity.this, "删除失败！",
								Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			super.onPostExecute(result);
		}
		
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
	
	
	//初始化视图
	public void initView(){
		
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_companynews_inbox_search = (EditText) findViewById(R.id.et_companynews_inbox_search);
		
		btnSelectAll = (Button) findViewById(R.id.btn_companynews_inbox_selectall);
		btnSelectAll.setOnClickListener(this);
		
		btnDelete = (Button) findViewById(R.id.btn_companynews_inbox_delete);
		btnDelete.setOnClickListener(this);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnBack.setOnClickListener(this);
		
//		btnNewMsg = (Button) findViewById(R.id.btn_companynews_inbox_add);
//		btnNewMsg.setOnClickListener(this);
		
		btnSearch = (ImageButton) findViewById(R.id.btn_companynews_inbox_search);
		btnSearch.setOnClickListener(this);
		
		
		//得到用户id
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(CompanyNewsInboxActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);
		
		
		sdDialog = new SDProgressDialog(this);
		iMsgDetail = new IMsgDetail();
		
		
		listView = (XListView) findViewById(R.id.lv_companynews_inbox);
//		listView.setOnItemClickListener(new InboxListItemClickListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}
	
	
	
	//listview监听事件
	/*private class InboxListItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//跳转到收件箱消息详细
			Intent intent = new Intent(CompanyNewsInboxActivity.this,
					CompanyNewsInboxDetailActivity.class);
			
			intent.putExtra("id", list.get(position-1).getId());
			intent.putExtra("empId", empId);
			startActivity(intent);
			finish();
		}
		
	}*/
	
	
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
	
	
	
	//询问是否删除
	public void askYesOrNo(){
		chooseNum = 0;
		for(int i=0; i<isCheckMap.size(); i++){
			if(isCheckMap.get(i) == false){
				/*Toast.makeText(CompanyNewsInboxActivity.this,
						"请选择要删除的信息！", Toast.LENGTH_SHORT).show();*/
			}else{
				chooseNum++;
				/*new AlertDialog.Builder(CompanyNewsInboxActivity.this)
				.setTitle("删除消息")
				.setMessage("确定删除选中的消息？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						initDataDelete();
						
					}
				}).setNegativeButton("取消", null).show();*/
				
			}
			
			
		}
		
		if(chooseNum != 0){
			new AlertDialog.Builder(CompanyNewsInboxActivity.this)
			.setTitle("删除消息")
			.setMessage("确定删除选中的消息？")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					initDataDelete();
					
				}
			}).setNegativeButton("取消", null).show();
			
		}else{
			handler.sendEmptyMessage(4);
		}
			
	}
	
	
	
	
	
	@Override
	public void onClick(View v) {
		
		//点击返回时
		if(v == btnBack){
			onBackPressed();
		}
		
		
		// 当点击全选时
		if(v == btnSelectAll){
			if(btnSelectAll.getText().toString().trim().equals("全选")){
				//所有项目全部选中
				adapter.configCheckMap(true);
				adapter.notifyDataSetChanged();
				btnSelectAll.setText("全不选");
				for (int i = 0; i < isCheckMap.size(); i++) {
					isCheckMap.put(i, true);
				}
				
			}else{
				//所有项目全部不选中
				adapter.configCheckMap(false);
				adapter.notifyDataSetChanged();
				btnSelectAll.setText("全选");
				for (int i = 0; i < isCheckMap.size(); i++) {
					isCheckMap.put(i, false);
				}
				
			}
		}
		
		
		//点击删除的时候
		if(v == btnDelete){
			askYesOrNo();
			
		}
		
		
		//当点击新信息时
		/*if(v == btnNewMsg){
			IntentUtils.gotoActivity(this, CompanyNewMessageActivity.class);
		}*/
		
		
		//点击搜索时
		if(v == btnSearch){
			keyword = et_companynews_inbox_search.getText().toString();
			flag1 = true;
			pageNum = 1;
			initDataSearch();
			
		}
		
		
	}


	/**==========================================================================================/
	
	
	
	
	
	
	/**
	 * 自定义适配器
	 * @author Administrator
	 *
	 */
	class MyAdapter extends BaseAdapter{
		
		public MyAdapter(){
			for(int i=0; i<list.size(); i++){
				isCheckMap.put(i, false);
				
			}
		}
		
		
		//默认全部没有选中
		public void configCheckMap(Boolean bool){
			
			for(int i=0; i<list.size(); i++){
				isCheckMap.put(i, bool);
			}
		}

		
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			Holder holder;
			if(convertView == null){
				convertView =  LayoutInflater.from(getApplicationContext())
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
			
			
			//设置单选按钮的选中
			checkIteams[position].setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// 将选择项加载到map里
					isCheckMap.put(position, isChecked);
					
				}
			});
			
			boolean checked = isCheckMap.get(position);
			checkIteams[position].setChecked(checked);
			
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
		
		
		
		
		
		public List<IMsgDetail> getList(){
			if(list.size() > 0){
				list1.clear();
				for(int i= 0; i<isCheckMap.size(); i++){
					if(isCheckMap.get(i) == true){
						list1.add(list.get(i));
					}
				}
			}
			
			return list1;
		}
		
	}

	
	
}
