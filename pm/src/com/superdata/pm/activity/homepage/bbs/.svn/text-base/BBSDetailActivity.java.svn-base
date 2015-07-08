package com.superdata.pm.activity.homepage.bbs;

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

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.IPostDetail;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;
import com.superdata.pm.util.NetCheckUtil;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 首页-->企业论坛-->论坛帖子详细
 * @author Administrator
 *
 */
public class BBSDetailActivity extends BaseActivity implements IXListViewListener,
					OnClickListener{

	private TextView top_title;
	private MyAdapter adapter;
	private ImageView btnBack;
	private Button btnReply;
	private EditText et_detail_reply;
	
	private List<IPostDetail> iList = new ArrayList<IPostDetail>();
	private List<IPostDetail> iList1 = new ArrayList<IPostDetail>();
	private TextView tv_bbs_detail_title;
	private TextView tv_bbs_detail_author;
	private TextView tv_bbs_detail_createtime;
	private TextView tv_bbs_detail_relatedproject;
	private TextView tv_bbs_detail_content;
	
	
	private String empId;//用户id
	private int postId;//帖子id
	private int pageNum = 1;
	private int pageSize = 10;
	//是否最后一页
	private boolean isLastPage = false;
	private boolean flag = true;
	private boolean flag1 = false;
	private String replyContent;//回帖内容
	
	
	private XListView listView;
	
	Message msg;
	List<NameValuePair> params;
	//帖子详细url
	String url = InterfaceConfig.BBS_DETAIL_URL;
	//回帖url
	String reUrl = InterfaceConfig.BBS_REPLY_URL;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	IPostDetail iPostDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bbs_detail);
		
		empId = getIntent().getExtras().getString("empId");
		postId = getIntent().getExtras().getInt("postId");
		
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
				//通知刷新
				adapter.notifyDataSetChanged();
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
				Toast.makeText(BBSDetailActivity.this,
						"已全部加载完成！", Toast.LENGTH_SHORT).show();
				break;
				
			case 4:
				Toast.makeText(BBSDetailActivity.this,
						"回复的内容不能为空！", Toast.LENGTH_SHORT).show();
				break;
				
			case 5:
				onRefresh();
				et_detail_reply.setText("");
				break;
				
			case 500:
				Toast.makeText(BBSDetailActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
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
	
	
	
	//初始化数据
	public void initData(){
		top_title.setText("帖子详细");
		initData(empId,postId+"",pageNum+"",pageSize+"");
	}
	
	/**
	 * 请求帖子详细数据
	 * @param empId		用户id
	 * @param postId	帖子id
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 */
	public void initData(String empId,String postId,String pageNum,String pageSize){
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("postId", postId));
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
					
					JSONObject jsonObject = jRoot.getJSONObject("post");
					
					//帖子主题
					String name = (String) jsonObject.get("name");
					//项目
					String projectName = (String) (jsonObject.get("projectName").equals(null)?
							"":jsonObject.get("projectName"));
					//创建人
					String createName = (String) jsonObject.get("createName");
					//创建时间
					String createDate = (String) jsonObject.get("createDate");
					//帖子内容
					String content = (String) jsonObject.get("content");
					
					tv_bbs_detail_title.setText("主    题："+name);
					tv_bbs_detail_author.setText(createName);
					tv_bbs_detail_createtime.setText(createDate);
					if("".equals(projectName)){
						tv_bbs_detail_relatedproject.setText("所属项目：无");
					}else{
						tv_bbs_detail_relatedproject.setText("所属项目："+projectName);
					}
					tv_bbs_detail_content.setText("内    容："+content);
					
					JSONObject jRoot1 = new JSONObject(result);
					
					JSONObject jsonObject1 = jRoot1.getJSONObject("postDetail");
					JSONArray postDetail = jsonObject1.getJSONArray("resultList");
					
					isLastPage = jsonObject1.getBoolean("isLastPage");
					iList1.clear();
					
					for(int i=0; i<postDetail.length(); i++){
						//回复人
						String createName1 = (String) postDetail.getJSONObject(i)
								.get("createName");
						//回复时间
						String createDate1 = (String) postDetail.getJSONObject(i)
								.get("createDate");
						//回复内容
						String content1 = (String) postDetail.getJSONObject(i).get("content");
						
						IPostDetail iPostDetail1 = new IPostDetail(content1, createName1,
								createDate1);
						iList1.add(iPostDetail1);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			msg = new Message();
			//加载更多时
			if(!flag){
				iList.addAll(iList1);
				iList1.clear();
				adapter = new MyAdapter();
				msg.what =2;
			}else{
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			IntentUtils.gotoActivity(BBSDetailActivity.this,
					BBSActivity.class);
			finish();
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onClick(View v) {
		// 点击返回时
		if(v == btnBack){
			IntentUtils.gotoActivity(BBSDetailActivity.this,
					BBSActivity.class);
			finish();
		}
		
		
		//点击回复时
		if(v == btnReply){
			replyContent = et_detail_reply.getText().toString();
			
			if(checkIsNull(et_detail_reply)){
				msg = new Message();
				msg.what = 4;
				handler.sendMessage(msg);
			}
			
			sdDialog.show();
			UpLoadReplyThread upLoadReplyThread = new UpLoadReplyThread();
			new Thread(upLoadReplyThread).start();
		}
	}

	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnReply = (Button) findViewById(R.id.btn_detail_reply);
		
		btnBack.setOnClickListener(this);
		btnReply.setOnClickListener(this);
		
		
		sdDialog = new SDProgressDialog(this);
		iPostDetail = new IPostDetail();
		
		tv_bbs_detail_title = (TextView) findViewById(R.id.tv_bbs_detail_title);
		tv_bbs_detail_author = (TextView) findViewById(R.id.tv_bbs_detail_author);
		tv_bbs_detail_createtime = (TextView) findViewById(R.id.tv_bbs_detail_createtime);
		tv_bbs_detail_relatedproject = (TextView) findViewById(R.id.tv_bbs_detail_relatedproject);
		tv_bbs_detail_content = (TextView) findViewById(R.id.tv_bbs_detail_content);
		et_detail_reply = (EditText) findViewById(R.id.et_detail_reply);
		
		listView = (XListView) findViewById(R.id.lv_bbs_detail);
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		
	}
	
	
	public List<NameValuePair> getParamList(){
		params = new ArrayList<NameValuePair>();
		
		String rePostId = postId+"";
		String reContent = replyContent;
		String rePageNum = pageNum+"";
		String rePageSize = pageSize+"";
		String reEmpId = empId;
		
		params.add(new BasicNameValuePair("postId", rePostId));
		params.add(new BasicNameValuePair("content", reContent));
		params.add(new BasicNameValuePair("pageNum", rePageNum));
		params.add(new BasicNameValuePair("pageSize", rePageSize));
		params.add(new BasicNameValuePair("empId", reEmpId));
		
		return params;
	}
	
	
	class UpLoadReplyThread implements Runnable{

		@Override
		public void run() {
			if(!NetCheckUtil.isNetworkAvailable(BBSDetailActivity.this)){
				Toast.makeText(BBSDetailActivity.this,
						"网络不给力，请稍后重试！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			sdClient = new SDHttpClient();
			//请求数据
			getParamList();
			msg = new Message();
			try {
				String json = sdClient.post_session(reUrl, params);
				
				msg.what = 5;
				msg.obj = json;
				handler.sendMessage(msg);
				
			} catch (Exception e) {
				msg.what = 500;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	@Override
	public void onRefresh() {
		if(!flag1){
			pageNum = 1;
			initData(empId,postId+"",pageNum+"",pageSize+"");
			onLoad();
		}
	}

	@Override
	public void onLoadMore() {
		if(!isLastPage){
			if(flag){
				flag = false;
				++pageNum;
				initData(empId,postId+"",pageNum+"",pageSize+"");
			}
		}else{
			handler.sendEmptyMessage(3);
		}
		
	}
	
	//判断输入框是否为空
	public boolean checkIsNull(EditText editText){
		boolean flag = false;
		String str = editText.getText().toString().trim();
		if(null == str || ("").equals(str)){
			flag = true;
		}
		return flag;
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
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.bbs_detail_listitem, null);
				holder = new Holder();
				holder.tv_bbs_detail_listitem_replycontent = (TextView) convertView
						.findViewById(R.id.tv_bbs_detail_listitem_replycontent);
				holder.tv_bbs_detail_listitem_replyuser = (TextView) convertView
						.findViewById(R.id.tv_bbs_detail_listitem_replyuser);
				holder.tv_bbs_detail_listitem_replytime = (TextView) convertView
						.findViewById(R.id.tv_bbs_detail_listitem_replytime);
				
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_bbs_detail_listitem_replyuser.setText(iList.get(position)
					.getCreateName());
			holder.tv_bbs_detail_listitem_replytime.setText(iList.get(position)
					.getCreateDate());
			holder.tv_bbs_detail_listitem_replycontent.setText(iList.get(position)
					.getContent());
			
			
			return convertView;
		}
		
		
		class Holder{
			TextView tv_bbs_detail_listitem_replycontent;
			TextView tv_bbs_detail_listitem_replyuser;
			TextView tv_bbs_detail_listitem_replytime;
		}
		
	}
	
	
}
