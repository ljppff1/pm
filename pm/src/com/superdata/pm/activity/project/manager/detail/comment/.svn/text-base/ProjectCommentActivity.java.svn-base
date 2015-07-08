package com.superdata.pm.activity.project.manager.detail.comment;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.project.contract.ProjectContractActivity;
import com.superdata.pm.activity.project.manager.detail.log.ProjectLogActivity;
import com.superdata.pm.activity.project.member.ProjectMemberActivity;
import com.superdata.pm.activity.project.plan.ProjectPlanPackageActivity;
import com.superdata.pm.activity.project.report.CheckReportActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.IPost;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;
/**
 * 项目-->项目管理-->项目详细-->项目评论
 * @author kw
 *
 */
public class ProjectCommentActivity extends BaseActivity implements IXListViewListener,
				OnClickListener{

	private ImageView btnBack;
	private TextView top_title;
	private EditText et_projectcomment_search;
	private Button btnSearch;
	
	private MyAdapter adapter;
	private List<IPost> iList = new ArrayList<IPost>();
	private List<IPost> iList1 = new ArrayList<IPost>();
	private XListView listView;
	
	
	//用户id
	private String empId;
	//项目名称
	private String projectName;
	//项目id
	private String projectId;
	private int pageNum = 1;
	private int pageSize = 10;
	private boolean isLastPage = false;
	private boolean flag = true;
	private boolean flag1 = false; //输入关键字标识
	private String keyword = "";
	
	String url = InterfaceConfig.BBS_LIST_URL;
	IPost iPost;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectcomment);
		
		empId = getIntent().getExtras().getString("empId");
		projectName = getIntent().getExtras().getString("projectName");
		projectId = getIntent().getExtras().getString("projectId");
		
		initView();//初始化视图
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
	
	//初始化数据方法
	public void initData(){
		top_title.setText("项目评论");
		//通过项目名称获得在论坛中的相关项目评论
		initData(empId,projectName,pageNum+"",pageSize+"",projectId);
	}
	
	public void initData(String empId,String projectName,
			String pageNum,String pageSize,String projectId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("keyword", projectName));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("projectId", projectId));
		
		new MyTask().execute(params);
	}
	
	
	public void initSearch(){
		initSearch(empId,pageNum+"",pageSize+"",keyword);
	}
	
	/**
	 * 项目的搜索列表
	 * @param empId	用户id
	 * @param page	页码
	 * @param pagesize	每页大小
	 * @param keyWord	搜索关键字
	 */
	public void initSearch(String empId,String pageNum,String pageSize,String keyword){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum",pageNum));
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
						String projectName = (String) (resultList.getJSONObject(i).get("projectName").equals(null)?
								"":resultList.getJSONObject(i).get("projectName"));
						//创建人姓名
						String createName = (String) resultList.getJSONObject(i).get("createName");
						//创建时间
						String createDate = (String) resultList.getJSONObject(i).get("createDate");
						//回帖数
						Integer replyCount = (Integer) resultList.getJSONObject(i).get("replyCount");
						//帖子id
						Integer postId = (Integer) resultList.getJSONObject(i).get("id");
						
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
	
	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_projectcomment_search = (EditText) findViewById(R.id.et_projectcomment_search);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch = (Button) findViewById(R.id.btn_projectcomment_search);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		
		sdDialog = new SDProgressDialog(this);
		iPost = new IPost();
		
		
		listView = (XListView) findViewById(R.id.lv_projectcomment_list);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}
	
	
	@Override
	public void onRefresh() {
		if(!flag1){
			pageNum = 1;
			initData(empId,projectName,pageNum+"",pageSize+"",projectId);
			onLoad();
		}else{
			//搜索刷新
			pageNum = 1;
			initSearch(empId,pageNum+"",pageSize+"",keyword);
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
					initData(empId,projectName,pageNum+"",pageSize+"",projectId);
				}
			}else{
				handler.sendEmptyMessage(3);
			}
		}else{
			if(!isLastPage){
				if(flag){
					flag = false;
					++pageNum;
					initSearch(empId,pageNum+"",pageSize+"",keyword);
				}
				
			}else{
				handler.sendEmptyMessage(3);
			}
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
			
			keyword = et_projectcomment_search.getText().toString();
			flag1 =true;
			pageNum = 1;
			initSearch();
			
		}
		
		
		
	}
	
	
	
	
	
	
	//自定义适配器
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
				holder.tv_bbs_listitem_title = (TextView) convertView.findViewById(R.id.tv_bbs_listitem_title);
				holder.tv_bbs_listitem_content = (TextView) convertView.findViewById(R.id.tv_bbs_listitem_content);
				holder.tv_bbs_listitem_author = (TextView) convertView.findViewById(R.id.tv_bbs_listitem_author);
				holder.tv_bbs_listitem_createtime = (TextView) convertView.findViewById(R.id.tv_bbs_listitem_createtime);
				holder.tv_bbs_listitem_discussnumber = (TextView) convertView.findViewById(R.id.tv_bbs_listitem_discussnumber);
//				holder.tv_bbs_listitem_relatedproject = (TextView) convertView.findViewById(R.id.tv_bbs_listitem_relatedproject);
				
				convertView.setTag(holder);
				
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_bbs_listitem_title.setText(iList.get(position).getName().toString());
			holder.tv_bbs_listitem_author.setText(iList.get(position).getCreateName().toString());
			holder.tv_bbs_listitem_createtime.setText(iList.get(position).getCreateDate().toString());
			holder.tv_bbs_listitem_content.setText(iList.get(position).getContent().toString());
			holder.tv_bbs_listitem_discussnumber.setText(iList.get(position).getReplyCount().toString());
			holder.tv_bbs_listitem_relatedproject.setText(iList.get(position).getProjectName().toString());
			
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
