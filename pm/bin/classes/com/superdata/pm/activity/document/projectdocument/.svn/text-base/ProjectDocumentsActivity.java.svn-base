package com.superdata.pm.activity.document.projectdocument;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
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

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.DAttachment;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 文档-->项目文档
 * @author kw
 *
 */
public class ProjectDocumentsActivity extends BaseActivity implements IXListViewListener,
				OnClickListener{
	
	private ImageView btnBack;
//	private Button btnDownload;
	private TextView top_title;
	private EditText et_projectdocuments_search;
	private ImageButton btnSearch;
	
	private List<DAttachment> list = new ArrayList<DAttachment>();
	private List<DAttachment> list1 = new ArrayList<DAttachment>();
	private MyAdapter adapter;
	private XListView listView;
	Thread mThread;
	
	//页码
	private int pageNum = 1;
	//每页大小
	private int pageSize = 10;
	//用户id
	private String empId;
	//附件id
	private int id;
	//加载标识
	private boolean flag = true;
	//搜索标识
	private boolean flag1 = false;
	//是否最后一页
	private boolean isLastPage = false;
	//关键字
	private String keyWord = "";
//	//选中条目的个数
//	private int chooseNum = 0;
	private static final int UPDATE_PROGRESS = 4;
	private static final int SET_MAX = 0;
	protected static final int ERROR = 400;
	
	
	protected static final int DOWNLOAD_ERROR = 5;
    protected static final int GET_MAX = 6;
    protected static final int GET_DOWNLOADLENGTH = 7;
	
	// 下载进度提示
	private TextView tv_progress;
	// 下载进度条
	private ProgressBar pb;
	// 下载最大进度
	private int max;
	
	private int arg1;
	
	private int length;
	
	
	DAttachment dAttachment;
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	String url = InterfaceConfig.PROJECTDOCUMENT_URL;
//	DownloadManager manager;
	String path = "http://192.168.0.113:8080/pm/documentInterface/downloadAttachment.do";
	AlertDialog.Builder builder;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.projectdocuments);
		
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
				Toast.makeText(ProjectDocumentsActivity.this,
						"已全部加载完成", Toast.LENGTH_SHORT).show();
				break;
				
			default:
				break;
			}
		};
	};
	
	//初始化数据
	public void initData(){
		initData(empId,pageNum+"",pageSize+"");
	}
	
	
	/**
	 * 
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
		initDataSearch(empId,pageNum+"",pageSize+"",keyWord);
	}
	
	/**
	 * @param empId		用户id
	 * @param pageNum	页码
	 * @param pageSize	每页大小
	 * @param keyWord	关键字
	 */
	public void initDataSearch(String empId, String pageNum, String pageSize, String keyWord){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("searchValue", keyWord));
		
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
						list1.clear();
						
						for(int i=0; i<resultList.length(); i++){
							//文件名称
							String name = (String) resultList.getJSONObject(i).get("name");
							//文件类型
							String fileType = (String) resultList.getJSONObject(i)
									.get("fileType");
							//文件大小
							String fileSize = (String) resultList.getJSONObject(i)
									.get("fileSize");
							//所属项目
							String projectName = (String) (resultList.getJSONObject(i)
									.get("projectName").equals(null)?"":
										resultList.getJSONObject(i)
										.get("projectName"));
							//上传者
							String createName = (String) resultList.getJSONObject(i)
									.get("createName");
							//上传时间
							String createDate = (String) resultList.getJSONObject(i)
									.get("createDate");
							
							id = resultList.getJSONObject(i).getInt("id");
							
							String fileByteSize = (String) resultList.getJSONObject(i)
									.get("fileByteSize");
							
							DAttachment dAttachment1 = new DAttachment(name, fileType, fileSize,
									createDate, projectName, createName, id, fileByteSize);
							list1.add(dAttachment1);
						}
						
					}else{
						Toast.makeText(ProjectDocumentsActivity.this,
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
			
			handler.sendMessage(msg);
			super.onPostExecute(result);
		}
		
	}
		
	
	//弹出下载对话框
	/*public void newprogress(){
		builder = new AlertDialog.Builder(this);
		builder.setTitle("下载");
		View view = LayoutInflater.from(this).inflate(R.layout.progressdialog, null);
		builder.setView(view);
		pb = (ProgressBar) view.findViewById(R.id.pb);
		tv_progress = (TextView) view.findViewById(R.id.tv_progress);
		pb.setProgress(progress);
		tv_progress.setText((progress*100)/pb.getMax()+"%");
		pb.setMax(max);
		if(arg1 == 0){
			//下载更新
			int progress = pb.getProgress();
			int now = length + progress;
			pb.setProgress(now);
			//计算百分比
			float percent = (float)now/(float)pb.getMax();
			int result = (int) (percent*100);
			
			tv_progress.setText("下载 :" + result+"%");
		}else if(arg1 == 1){
			//已经存在的下载记录的更新
			pb.setProgress(length);
			//计算百分比
			float percent = (float)length/(float)pb.getMax();
			int result = (int) (percent*100);
			tv_progress.setText("下载 :" + result+"%");
		}
		
		
		builder.setPositiveButton("隐藏", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(ProjectDocumentsActivity.this,
						"正在后台进行下载中……", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				mThread.interrupt();
				Toast.makeText(ProjectDocumentsActivity.this,
						"下载已终止！", Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
		pb.setMax(max);
		
	}*/
	
	
	
	
	@Override
	public void onClick(View v) {
		// 点击返回时
		if(v == btnBack){
			onBackPressed();
		}
		
		
		//点击搜索时
		if(v == btnSearch){
			
			keyWord = et_projectdocuments_search.getText().toString();
			flag1 = true;
			pageNum = 1;
			initDataSearch();
			
		}
	}
		
	
	//初始化视图
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		top_title.setText("项目附件");
//		manager=new DownloadManager(path,this);
		et_projectdocuments_search = (EditText) findViewById(R.id.et_projectdocuments_search);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch = (ImageButton) findViewById(R.id.btn_projectdocuments_search);
//		btnDownload = (Button) findViewById(R.id.btn_projectdocuments_download);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
//		btnDownload.setOnClickListener(this);
		
		sdDialog = new SDProgressDialog(this);
		dAttachment = new DAttachment();
		
		
		listView = (XListView) findViewById(R.id.lv_projectdocuments_list);
		listView.setOnItemClickListener(new DetailListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
		
	}
	
	
	private class DetailListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
//			(name, fileType, fileSize,
//					createDate, projectName, createName, id)
			Intent intent = new Intent(ProjectDocumentsActivity.this,
					ProjectDownloadActivity.class);
			intent.putExtra("id", list.get(position-1).getId());//id
			intent.putExtra("name", list.get(position-1).getName());
			intent.putExtra("fileType", list.get(position-1).getFileType());
			intent.putExtra("fileSize", list.get(position-1).getFileSize());
			intent.putExtra("createDate", list.get(position-1).getCreateDate());
			intent.putExtra("projectName", list.get(position-1).getProjectName());
			intent.putExtra("createName", list.get(position-1).getCreateName());
			intent.putExtra("fileByteSize", list.get(position-1).getFileByteSize());
			startActivity(intent);
		}
		
	}
	
	
	public void onLoad(){
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	

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
	
	
	/*class myThread implements Runnable{
		
		int id;
		long fileSize;
		
		public myThread(int id,long fileSize){
			this.id=id;
			this.fileSize=fileSize;
		}

		
		@Override
		public void run() {
			//Log.v("---url----", path+"?id="+id);
			
			manager = new DownloadManager(path+"?id="+id,ProjectDocumentsActivity.this,fileSize);
			try {
				manager.download(new ProgressBarListener() {
					
					@Override
					public void getMax(int length) {
						// TODO Auto-generated method stub
						Message msg = new Message();
						msg.what = GET_MAX;
						msg.obj = length;
						handler.sendMessage(msg);
					}
					
					@Override
					public void getDownload(int length, int flag) {
						// TODO Auto-generated method stub
						Message msg = new Message();
						msg.what = GET_DOWNLOADLENGTH;
						msg.obj = length;
						msg.arg1 = flag;
						handler.sendMessage(msg);
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Message msg = new Message();
				msg.what = DOWNLOAD_ERROR;
				handler.sendMessage(msg);
			}
			
		}
		
	}*/
	
	
	/*class myThread implements Runnable{
		int id;
		
		public myThread(int id){
			this.id=id;
		}
		
		
		@Override
		public void run() {
			try {
				manager.download(path+"?id="+id,
						new DownloadListener() {
					
					@Override
					public void setProgress(int progress) {
						// handler发消息更新进度
						Message msg = handler.obtainMessage();
						msg.what = UPDATE_PROGRESS;
						msg.arg1 = progress;
						handler.sendMessage(msg);
					}
					
					@Override
					public void setMax(int max) {
						Message msg = handler.obtainMessage();
						msg.what = SET_MAX;
						msg.arg1 = max;
						handler.sendMessage(msg);
					}
				});
			} catch (Exception e) {
				//下载出错，发消息给界面
				handler.sendEmptyMessage(ERROR);
				e.printStackTrace();
			}
			
		}
		
	}*/
	
	
	/**
	 * 自定义适配器
	 * @author Administrator
	 *
	 */
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			Holder holder;
			if(convertView == null){
				holder = new Holder();
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.projectdocuments_listitem, null);
				
				/*holder.btn_projectdocuments_listitem_download = (Button) convertView
						.findViewById(R.id.btn_projectdocuments_listitem_download);*/
				holder.tv_projectdocuments_listitem_name = (TextView) convertView
						.findViewById(R.id.tv_projectdocuments_listitem_name);
				holder.tv_projectdocuments_listitem_type = (TextView) convertView
						.findViewById(R.id.tv_projectdocuments_listitem_type);
				holder.tv_projectdocuments_listitem_size = (TextView) convertView
						.findViewById(R.id.tv_projectdocuments_listitem_size);
				holder.tv_projectdocuments_listitem_project = (TextView) convertView
						.findViewById(R.id.tv_projectdocuments_listitem_project);
				holder.tv_projectdocuments_listitem_uploader = (TextView) convertView
						.findViewById(R.id.tv_projectdocuments_listitem_uploader);
				holder.tv_projectdocuments_listitem_uploadtime = (TextView) convertView
						.findViewById(R.id.tv_projectdocuments_listitem_uploadtime);
				
//				checkIteams[position] = (CheckBox) convertView
//						.findViewById(R.id.cb_projectdocuments_listitem);
//				
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
			}
			
			holder.tv_projectdocuments_listitem_name.setText(list.get(position)
					.getName().toString());
			holder.tv_projectdocuments_listitem_type.setText(list.get(position)
					.getFileType().toString());
			holder.tv_projectdocuments_listitem_size.setText(list.get(position)
					.getFileSize().toString());
			holder.tv_projectdocuments_listitem_project.setText(list.get(position)
					.getProjectName().toString());
			holder.tv_projectdocuments_listitem_uploader.setText(list.get(position)
					.getCreateName().toString());
			holder.tv_projectdocuments_listitem_uploadtime.setText(list.get(position)
					.getCreateDate().toString());
			
			id = list.get(position).getId();
			
			/*holder.btn_projectdocuments_listitem_download.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					myThread mthread=new myThread(id,Long.parseLong(list.get(position).getFileSize()));
					
					mThread=new Thread(mthread);
					mThread.start();
					
					String downloadPath = path+"?id="+id;
					if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
						File saveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
						getExternalFilesDir(Environment.DIRECTORY_MOVIES);
						Log.v(TAG, "开始下载...");
						Toast.makeText(getApplicationContext(), "开始下载...", Toast.LENGTH_SHORT).show();
						download(path, saveDir);
					}else{
						if(pb.getProgress() == pb.getMax()){
							Toast.makeText(getApplicationContext(), "SDcard不存在", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					
					
					
				}
			});*/
			
			return convertView;
		}
		
		
		class Holder{
			//下载按钮
			Button btn_projectdocuments_listitem_download;
			//附件名称
			TextView tv_projectdocuments_listitem_name;
			//附件类型
			TextView tv_projectdocuments_listitem_type;
			//附件大小
			TextView tv_projectdocuments_listitem_size;
			//相关项目
			TextView tv_projectdocuments_listitem_project;
			//上传时间
			TextView tv_projectdocuments_listitem_uploadtime;
			//上传者
			TextView tv_projectdocuments_listitem_uploader;
			
		}
		
	}
	

	
}
