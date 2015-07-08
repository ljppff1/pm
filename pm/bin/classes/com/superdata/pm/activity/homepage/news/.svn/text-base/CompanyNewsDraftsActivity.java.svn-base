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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.homepage.news.CompanyNewsOutBoxActivity.DeleteTask;
import com.superdata.pm.activity.homepage.news.CompanyNewsOutBoxActivity.MyAdapter;
import com.superdata.pm.activity.homepage.news.CompanyNewsOutBoxActivity.MyTask;
import com.superdata.pm.activity.homepage.news.CompanyNewsOutBoxActivity.MyAdapter.Holder;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.IMsg;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 首页-->站内消息-->草稿箱
 * @author kw
 *
 */
public class CompanyNewsDraftsActivity extends BaseActivity implements IXListViewListener,
				OnClickListener{
	
	//返回按钮
	private ImageView btnBack;
	//标题
	private TextView top_title;
	//搜索按钮
	private ImageButton btnSearch;
	//新消息按钮
	private Button btnNewMsg;
	//全选按钮
	private Button btnSelectAll;
	//删除按钮
	private Button btnDelete;
	
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
	//删除返回结果
	private String resultCode;
	//发送状态   1为发送消息  0为保存消息
	private int status = 0;
	//选中条目的个数
	private int chooseNum = 0;
	
	CheckBox[] checkBoxItems;
	Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
	
	
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	Message msg;
	IMsg iMsg;
	IMsg iMsg1;
	String url = InterfaceConfig.NEWSOUTBOX_LIST_URL;
	String deleteUrl = InterfaceConfig.NEWSOUTBOX_LIST_DELETE_URL;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companynews_drafts);
		
		initView();
		initData();
	}
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
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
				Toast.makeText(CompanyNewsDraftsActivity.this,
						"已全部加载完成", Toast.LENGTH_SHORT).show();;
				break;
				
			case 4:
				Toast.makeText(CompanyNewsDraftsActivity.this,
						"请选择要删除的信息！", Toast.LENGTH_SHORT).show();;
				break;

			default:
				break;
			}
			
			
			super.handleMessage(msg);
		}
		
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
	
	
	
	public void initDataDelete(){
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<isCheckMap.size(); i++){
			id = list1.get(i).getId()+"";
			if(isCheckMap.get(i)){
				sb.append(id+",");
			}
		}
		
		String id1 = sb.toString();
		String id2 = id1.substring(0, id1.lastIndexOf(","));
		
		initDataDelete(empId, id2);
	}
	
	
	
	public void initDataDelete(String empId, String id){
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
					resultCode = jRoot.getString("resultCode");
					
					if("200".equals(resultCode)){
//						initData(empId, pageNum+"", pageSize+"", status+"");
						Toast.makeText(CompanyNewsDraftsActivity.this, "删除成功！",
								Toast.LENGTH_SHORT).show();
						IntentUtils.gotoActivity(CompanyNewsDraftsActivity.this,
								CompanyNewsDraftsListActivity.class);
						finish();
					}else{
						
						Toast.makeText(CompanyNewsDraftsActivity.this,
								"删除失败！", Toast.LENGTH_SHORT).show();
						
					}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
		
	}
	
	
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_companynews_drafts_search = (EditText) findViewById(R.id.et_companynews_drafts_search);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch = (ImageButton) findViewById(R.id.btn_companynews_drafts_search);
//		btnNewMsg = (Button) findViewById(R.id.btn_companynews_drafts_add);
		btnSelectAll = (Button) findViewById(R.id.btn_companynews_drafts_selectall);
		btnDelete = (Button) findViewById(R.id.btn_companynews_drafts_delete);
	
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
//		btnNewMsg.setOnClickListener(this);
		btnSelectAll.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		
		//得到用户id
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(CompanyNewsDraftsActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);
		
		sdDialog = new SDProgressDialog(this);
		iMsg = new IMsg();
		
		listView = (XListView) findViewById(R.id.lv_companynews_drafts);
//		listView.setOnItemClickListener(new DraftsListItemClickListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}
	
	
	/*private class DraftsListItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(CompanyNewsDraftsActivity.this,
					CompanyNewsDraftsDetailActivity.class);
			intent.putExtra("id", list.get(position-1).getId());
			intent.putExtra("empId", empId);
			intent.putExtra("receiveId", list.get(position-1).getReceiveId());
			startActivity(intent);
			finish();
		}
		
	}*/

	
	//询问是否删除
	public void askYesOrNo(){
		chooseNum = 0;
		for (int i = 0; i < isCheckMap.size(); i++) {
			if(isCheckMap.get(i) == false){
				/*Toast.makeText(CompanyNewsDraftsActivity.this,
						"请选择要删除的信息！", Toast.LENGTH_SHORT).show();*/
			}else{
				/*new AlertDialog.Builder(CompanyNewsDraftsActivity.this)
				.setTitle("删除消息")
				.setMessage("确定删除选中的消息？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
				
						initDataDelete();
				
					}
				}).setNegativeButton("取消", null).show();*/
				
				chooseNum++;
			}
		}
		
		if(chooseNum != 0){
			new AlertDialog.Builder(CompanyNewsDraftsActivity.this)
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
		// 点击后退时
		if(v == btnBack){
			onBackPressed();
		}
				 
		// 点击新消息时
		if(v == btnNewMsg){
			IntentUtils.gotoActivity(this, CompanyNewMessageActivity.class);
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
		
		
		// 点击删除的时候
		if(v == btnDelete){
			askYesOrNo();
		}
		
		
		// 点击搜索时
		if(v == btnSearch){
			keyword = et_companynews_drafts_search.getText().toString();
			flag1 = true;
			pageNum = 1;
			initDataSearch();
		}
		
		
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
			
			//设置单选按钮的选中
			checkBoxItems[position].setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// 将选择项加载到map里
					isCheckMap.put(position, isChecked);
					
				}
			});
			
			boolean checked = isCheckMap.get(position);
			checkBoxItems[position].setChecked(checked);
			
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
		
		
		/**
		 * 得到选中的checkbox存入集合中
		 * @return	存放选中要删除消息checkbox的list
		 */
		public List<IMsg> getList(){
			if(list.size() > 0){
				list1.clear();
				for(int i=0; i<isCheckMap.size(); i++){
					if(isCheckMap.get(i) == true){
						list1.add(list.get(i));
					}
				}
			}
			
			return list1;
		}
		
		
		
	}
	

}
