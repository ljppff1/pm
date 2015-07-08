package com.superdata.pm.activity.homepage.bbs;

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
import android.text.Editable;
import android.text.TextWatcher;
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
import com.superdata.pm.entity.IPost;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 首页-->企业论坛
 * @author kw
 *
 */
public class BBSActivity extends BaseActivity implements IXListViewListener,OnClickListener{

	private TextView top_title;
	private ImageView btnBack;
	private EditText et_bbs_list_search;
//	private Button btnSearch;
	private ImageView btnAdd;
	private ImageButton ibSearch;;
	private ImageButton btnDel;
	private View plant;
	
	private MyAdapter adapter;
	private List<IPost> iList = new ArrayList<IPost>();
	private List<IPost> iList1 = new ArrayList<IPost>();
	
	private String empId;//用户id
	private int pageNum = 1;//页码
	private int pageSize = 10;//每页大小
	private boolean isLastPage = false;//是否末页
	private boolean flag = true;//加载更多标识
	private boolean flag1 = false; //输入关键字标识
	private String keyWord = "";//关键字
	
	private XListView listView;
	
	String url = InterfaceConfig.BBS_LIST_URL;
	IPost iPost;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bbs_list);
		
		initView();//初始化视图
		initData();//初始化控件
		
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
		};
	};
	
	
	
	public void onLoad(){
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	//初始化方法
	public void initData(){
		top_title.setText("企业论坛");
		initData(empId,pageNum+"",pageSize+"");
	}
	
	
	/**
	 * 项目列表
	 * @param empId	用户id
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 */
	public void initData(String empId, String pageNum, String pageSize){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum",pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		
		new MyTask().execute(params);
	}
	
	
	public void initDataSearch(){
		initDataSearch(empId,pageNum+"",pageSize+"",keyWord);
	}
	
	
	/**
	 * 项目的搜索列表
	 * @param empId	用户id
	 * @param page	页码
	 * @param pagesize	每页大小
	 * @param keyWord	搜索关键字
	 */
	public void initDataSearch(String empId, String pageNum, String pageSize,String keyWord){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum",pageNum));
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
		
		
		@Override
		protected void onPostExecute(String result) {
			if(null != result){
				try {
					JSONObject jRoot = new JSONObject(result);
					JSONArray resultList = jRoot.getJSONArray("resultList");
					isLastPage = jRoot.getBoolean("isLastPage");
					
					iList1.clear();
					
					for(int i=0; i<resultList.length(); i++){
						//帖子主题
						String name = (String) resultList.getJSONObject(i).get("name");
						//帖子内容
						String content = (String) resultList.getJSONObject(i).get("content");
						//项目名称
						String projectName = (String) (resultList.getJSONObject(i)
								.get("projectName").equals(null)?
								"":resultList.getJSONObject(i).get("projectName"));
						//创建人姓名
						String createName = (String) resultList.getJSONObject(i)
								.get("createName");
						//创建时间
						String createDate = (String) resultList.getJSONObject(i)
								.get("createDate");
						//回帖数
						Integer replyCount = (Integer) resultList.getJSONObject(i)
								.get("replyCount");
						//帖子id
						Integer postId = (Integer) resultList.getJSONObject(i)
								.get("id");
						
						IPost iPost1 = new IPost(projectName, name, content,
								replyCount, createName, createDate, postId);
						
						iList1.add(iPost1);
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
				msg.what =2;
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
		
		//点击返回时
		if(v == btnBack){
			onBackPressed();
		}
		
		//点击搜索时
//		if(v == btnSearch){
//			keyWord = et_bbs_list_search.getText().toString();
//			flag1 = true;
//			pageNum = 1;
//			initDataSearch();
//		}
		
		//点击发新帖时
		if(v == btnAdd){
			Intent intent = new Intent(BBSActivity.this,BBSAddTitleActivity.class);
			intent.putExtra("empId", empId);
			startActivity(intent);
			finish();
		}
		
		if(v == ibSearch){
			keyWord = et_bbs_list_search.getText().toString();
			flag1 = true;
			pageNum = 1;
			initDataSearch();
		}
		
	}
	
	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_bbs_list_search = (EditText) findViewById(R.id.et_bbs_list_search);
//		et_bbs_list_search.addTextChangedListener(watcher);
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
//		btnSearch = (Button) findViewById(R.id.btn_bbs_list_search);
		btnAdd = (ImageView) findViewById(R.id.btn_bbs_list_add);
		ibSearch = (ImageButton) findViewById(R.id.ib_search);
		
//		btnDel = (ImageButton) findViewById(R.id.ib_del);
//		plant = (View) findViewById(R.id.plant);
		
		ibSearch.setOnClickListener(this);
//		btnDel.setOnClickListener(this);
		btnBack.setOnClickListener(this);
//		btnSearch.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		
		sdDialog = new SDProgressDialog(this);
		iPost = new IPost();
		
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(this);
		empId = mapconfig.get(InterfaceConfig.ID);//得到用户id
		
		listView = (XListView) findViewById(R.id.lv_bbs_list);
		listView.setOnItemClickListener(new BBSListOnItemClickListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		
	}
	
	//listview监听事件
	private class BBSListOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//跳转到论坛帖子详细
			Intent intent = new Intent(BBSActivity.this, BBSDetailActivity.class);
			intent.putExtra("empId", empId);
			intent.putExtra("postId", iList.get(position-1).getId());
			startActivity(intent);
			finish();
		}
		
	}
	
	
	/** 文本域改变事件处理 **/
	/*private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			String content = s.toString().trim();
			if (content.length() > 0) {
				plant.setVisibility(View.VISIBLE);
				btnDel.setVisibility(View.VISIBLE);
			} else {
				plant.setVisibility(View.GONE);
				btnDel.setVisibility(View.GONE);

				// 重新加载
//				 new Thread(resetListViewRun).start();

			}
		}
	};*/

	
	

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
			Holder holder = null;
			if(convertView == null){
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.bbs_list_item, null);
				holder = new Holder();
				holder.tv_bbs_listitem_title = (TextView) convertView
						.findViewById(R.id.tv_bbs_listitem_title);
				holder.tv_bbs_listitem_content = (TextView) convertView
						.findViewById(R.id.tv_bbs_listitem_content);
				holder.tv_bbs_listitem_author = (TextView) convertView
						.findViewById(R.id.tv_bbs_listitem_author);
				holder.tv_bbs_listitem_createtime = (TextView) convertView
						.findViewById(R.id.tv_bbs_listitem_createtime);
				holder.tv_bbs_listitem_discussnumber = (TextView) convertView
						.findViewById(R.id.tv_bbs_listitem_discussnumber);
//				holder.tv_bbs_listitem_relatedproject = (TextView) convertView
//						.findViewById(R.id.tv_bbs_listitem_relatedproject);
				
				convertView.setTag(holder);
				
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			
			holder.tv_bbs_listitem_author.setText(iList.get(position)
					.getCreateName().toString());
			holder.tv_bbs_listitem_createtime.setText(iList.get(position)
					.getCreateDate().toString());
			holder.tv_bbs_listitem_content.setText("内容："+iList.get(position)
					.getContent().toString());
			holder.tv_bbs_listitem_discussnumber.setText(iList.get(position)
					.getReplyCount().toString());
			if("".equals(iList.get(position).getProjectName().toString())){
				holder.tv_bbs_listitem_title.setText("主题："+iList.get(position)
						.getName().toString());
			}else{
				/*holder.tv_bbs_listitem_relatedproject.setText("("+iList.get(position)
						.getProjectName().toString()+")");*/
				holder.tv_bbs_listitem_title.setText("主题："+iList.get(position)
						.getName().toString()+"  ("+iList.get(position).getProjectName()
						.toString()+")");
			}
			
			return convertView;
		}
		
		
		class Holder{
			TextView tv_bbs_listitem_title;
			TextView tv_bbs_listitem_content;
			TextView tv_bbs_listitem_author;
			TextView tv_bbs_listitem_createtime;
			TextView tv_bbs_listitem_discussnumber;
			TextView tv_bbs_listitem_relatedproject;
		}
		
	}


	
}
