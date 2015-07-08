package com.superdata.pm.activity.document.projectdocument;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.DownloadListener;

/**
 * 附件下载详细
 * @author kw
 * 
 */
public class ProjectDownloadActivity extends BaseActivity implements
		OnClickListener {
	
	
	private ImageView btnBack;//返回
	private TextView top_title;//标题
	private TextView tv_projectdocument_detail_docname;//附件名称
	private TextView tv_projectdocument_detail_doctype;//附件类型
	private TextView tv_projectdocument_detail_docsize;//附件大小
	private TextView tv_projectdocument_detail_docproject;//所属项目
	private TextView tv_projectdocument_detail_docman;//上传者
	private TextView tv_projectdocument_detail_doctime;//上传时间
	private TextView resultView;
	private Button btnDownload;//下载按钮
	private Button btnPause;//暂停下载
	private Button btnOpen;//打卡文件所在位置
	private ProgressBar pb;//进度条
//	private DownloadManager manager;
//	private FileService fileService;
	private String savePath;
	
	private Button btnPause_Continue;
	private TextView tv_result;
	private ProgressBar pb_download;
	private Button btnOpenFile;
	private Button btnCancelFile;
	
	protected static final int SET_MAX = 0;
	protected static final int UPDATE_PROGRESS = 1;
	protected static final int ERROR = 400;
	protected static final int CONTINUE = 2;
	protected static final int PAUSE = 3;
	
	//附件id
	private int id;
	//附件名称
	private String name;
	//附件类型
	private String fileType;
	//附件大小（显示）
	private String fileSize;
	//上传时间
	private String createDate;
	//所属项目	
	private String projectName;
	//上传者
	private String createName;
	//附件大小（不显示）
	private String fileByteSize;
	
	private int aa = 0;
	
	String path = InterfaceConfig.PROJECTDOCUMENT_DOWNLOAD_URL;
	String downloadPath;

	
	HttpURLConnection httpURLConnection = null;
	URL url = null;
	BufferedInputStream bis = null;
	byte[] buf = new byte[1024];
	int size = 0;
	boolean isPaused = false;
//	Context context;
	File file;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectdocument_detail);
		
		initView();//初始化
		initData();
	}
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SET_MAX:
				//pb.setMax(msg.arg1);
//				pb.setMax(100);
				pb_download.setMax(100);
				break;
				
			case UPDATE_PROGRESS:
				int progress = msg.arg1;
//				btnPause.setVisibility(View.VISIBLE);
//				pb.setVisibility(View.VISIBLE);
//				btnPause.setEnabled(true);
//				btnDownload.setEnabled(false);
				
				//pb.setProgress(progress);
				//resultView.setText("下载："+(progress*100)/pb.getMax()+"%");*/
				//int progress  =aa;
//				pb.setProgress(progress);
//				resultView.setText(progress+"%");
				
				
				pb_download.setProgress(progress);
				tv_result.setText(progress+"%");
				
				if(progress == 100){
					Toast.makeText(ProjectDownloadActivity.this,
							"下载完成！", Toast.LENGTH_SHORT).show();
					btnOpenFile.setVisibility(View.VISIBLE);
					btnCancelFile.setVisibility(View.VISIBLE);
					
//					btnPause.setEnabled(false);
//					btnDownload.setEnabled(false);
//					btnOpen.setVisibility(View.VISIBLE);
				}
				break;
				
			case ERROR:
				Toast.makeText(ProjectDownloadActivity.this, "下载出错！", Toast.LENGTH_SHORT).show();
				break;
				
			case CONTINUE:
				int progress2 = msg.arg1;
				/*System.out.println("max:"+pb.getMax()+"  progress:"+progress2);
				pb.setProgress(progress2);
				resultView.setText("下载："+(progress2*100)/pb.getMax()+"%");*/
				//int progress2  =msg.arg2;
//				pb.setProgress(progress2);
//				resultView.setText(progress2+"%");
				pb_download.setProgress(progress2);
				tv_result.setText(progress2+"%");
				break;
				
			case PAUSE:
				btnPause.setEnabled(false);
				btnDownload.setEnabled(true);
				break;
				

			default:
				break;
			}
		};
	};
	
	
	
	public void initView(){
		id = getIntent().getExtras().getInt("id");
		name = getIntent().getExtras().getString("name");
		fileType = getIntent().getExtras().getString("fileType");
		fileSize = getIntent().getExtras().getString("fileSize");
		createDate = getIntent().getExtras().getString("createDate");
		projectName = getIntent().getExtras().getString("projectName");
		createName = getIntent().getExtras().getString("createName");
		fileByteSize = getIntent().getExtras().getString("fileByteSize");
		
		tv_projectdocument_detail_docname = (TextView) findViewById(R.id.tv_projectdocument_detail_docname);
		tv_projectdocument_detail_doctype = (TextView) findViewById(R.id.tv_projectdocument_detail_doctype);
		tv_projectdocument_detail_docsize = (TextView) findViewById(R.id.tv_projectdocument_detail_docsize);
		tv_projectdocument_detail_docproject = (TextView) findViewById(R.id.tv_projectdocument_detail_docproject);
		tv_projectdocument_detail_docman = (TextView) findViewById(R.id.tv_projectdocument_detail_docman);
		tv_projectdocument_detail_doctime = (TextView) findViewById(R.id.tv_projectdocument_detail_doctime);
		
		
		top_title = (TextView) findViewById(R.id.tv_top_title);
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnDownload = (Button) findViewById(R.id.btn_projectdocument_download);
//		btnPause = (Button) findViewById(R.id.btn_projectdocument_pause);
//		btnOpen = (Button) findViewById(R.id.btn__projectdocument_open);
		
//		btnPause_Continue = (Button) findViewById(R.id.btn_download_pause);
	    
//		pb = (ProgressBar) findViewById(R.id.pb);
//		resultView = (TextView) findViewById(R.id.result);
		
//		btnPause.setVisibility(View.INVISIBLE);
//		btnOpen.setVisibility(View.INVISIBLE);
//		pb.setVisibility(View.INVISIBLE);
		
		btnBack.setOnClickListener(this);
//		btnDownload.setOnClickListener(this);
//		btnPause.setOnClickListener(this);
//		btnOpen.setOnClickListener(this);
		
		
//		manager = new DownloadManager(this, name, fileType, fileByteSize);
		//下载路径
		downloadPath = path+"?id="+id+"&jsessionid="+SDHttpClient.JSESSIONID;
//		Log.v("---------downloadPath----------", downloadPath);
		
		btnDownload.setOnClickListener(new BtnDownloadClick());
		
	}
	
	class BtnDownloadClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			Context context = ProjectDownloadActivity.this;
			DownloadDialog dd = new DownloadDialog(context);
			dd.show();
		}
		
	}
	
	class DownloadDialog extends Dialog{
		Context context ;

		public DownloadDialog(Context context) {
			super(context);
			this.context = context;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			View view = LayoutInflater.from(this.context).inflate(R.layout.downloadview, null);
			setContentView(view);
			tv_result = (TextView) view.findViewById(R.id.tv_result);
			pb_download = (ProgressBar) view.findViewById(R.id.pb_download);
			if(null == pb_download){
				Log.i("dddddddddd"," is null");
				return;
			}else{
				Log.d("aaaaaaaaa", "not null ");
			}
			setTitle("下载");
			
//			final Button btnPauseOrContinue = (Button) view.findViewById(R.id.btn_download_pause);
			/*btnPauseOrContinue.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String text = btnPauseOrContinue.getText().toString();
					if(text.equals("暂停")){
						btnPauseOrContinue.setText("继续");
						isPaused = true;
					}else if(text.equals("继续")){
						btnPauseOrContinue.setText("暂停");
						MyThread myThread = new MyThread();
						Thread t = new Thread(myThread);
						t.start();
					}
				}
				
			});*/
			
			btnOpenFile = (Button) view.findViewById(R.id.btn_openfile);
			btnOpenFile.setVisibility(View.INVISIBLE);
			btnOpenFile.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openFile(file);
					dismiss();
				}
				
			});
			
			btnCancelFile = (Button) view.findViewById(R.id.btn_cancel);
			btnCancelFile.setVisibility(View.INVISIBLE);
			btnCancelFile.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismiss();
				}
				
			});
			
			MyThread myThread = new MyThread();
			Thread t = new Thread(myThread);
			t.start();
			
		}
	}
	
	
	public void initData(){
		top_title.setText("附件详细");
		
		tv_projectdocument_detail_docname.setText(name);
		tv_projectdocument_detail_doctype.setText(fileType);
		tv_projectdocument_detail_docsize.setText(fileSize);
		tv_projectdocument_detail_docproject.setText(projectName);
		tv_projectdocument_detail_docman.setText(createName);
		tv_projectdocument_detail_doctime.setText(createDate);
	}
	
	
	@Override
	public void onClick(View v) {
		if(v == btnBack){
			onBackPressed();
		}
		
		/*if(v == btnDownload){
			btnPause.setVisibility(View.VISIBLE);
			pb.setVisibility(View.VISIBLE);
			btnPause.setEnabled(true);
			btnDownload.setEnabled(false);
//			manager.setPause(false);
			//开启线程
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						new DownloadListener() {
							
							@Override
							public void setProgress(int progress) {
								//handler发消息更新进度
								Message msg = handler.obtainMessage();
								msg.what = UPDATE_PROGRESS;
								msg.arg1 = aa;
								handler.sendMessage(msg);
							}
							
							@Override
							public void setMax(int max) {
								// handler发消息
								Message msg = handler.obtainMessage();
								msg.what = SET_MAX;
								msg.arg1 = max;
								handler.sendMessage(msg);
							}
							
							@Override
							public void onContinue(int progress) {
								Message msg = handler.obtainMessage();
								msg.what = CONTINUE;
								msg.arg1 = aa;
								handler.sendMessage(msg);
							}
						};
						
					} catch (Exception e) {
						e.printStackTrace();
						//下载出错，发消息给界面
						handler.sendEmptyMessage(ERROR);
					}
				}
			});
			//.start();
			
			MyThread myThread = new MyThread();
			Thread t = new Thread(myThread);
			t.start();
			
//			downloadBuilder();
			
		}*/
		
		/*if(v == btnPause){
			btnPause.setEnabled(false);
			btnDownload.setEnabled(true);
			//让3个线程暂停
			manager.setPause(true);
			try {
				bis.close();
				httpURLConnection.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			isPaused = true;
//			
//			PauseThread pThread = new PauseThread();
//			Thread p = new Thread(pThread);
//			p.start();
			
		}*/
		
		/*if(v == btnOpen){
//			fileService.readSdCardFile(name);
			openFile(file);
			Log.d("-----open------", "open");
			
		}*/
		
		/*if(v == btnPause_Continue){
			String text = btnPause_Continue.getText().toString();
			if(text.equals("暂停")){
				btnPause_Continue.setText("继续");
				isPaused = true;
			}else if(text.equals("继续")){
				btnPause_Continue.setText("暂停");
				MyThread myThread = new MyThread();
				Thread t = new Thread(myThread);
				t.start();
			}
			
//			PauseThread pThread = new PauseThread();
//			Thread p = new Thread(pThread);
//			p.start();
		}*/

	}
	
	/*public void downloadBuilder(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("下载");
		View view = LayoutInflater.from(this).inflate(R.layout.downloadview, null);
		builder.setView(view);
		builder.setPositiveButton("打开", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				openFile(file);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
		
		MyThread myThread = new MyThread();
		Thread t = new Thread(myThread);
		t.start();
	}*/
	
	
	private void openFile(File file){
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//设置intent的Action属性 
		intent.setAction(Intent.ACTION_VIEW);
		//获取文件file的MIME类型
		String type = getMIMEType(file);
		//设置intent的Data和Type属性
		intent.setDataAndType(Uri.fromFile(file), type);
		//跳转
		startActivity(intent);
	}
	
	
	private String getMIMEType(File file){
		String type = "*/*";
		String fName = file.getName();
		//获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if(dotIndex < 0){
			return type;
		}
		/*获取文件的后缀名*/
		String end = fName.substring(dotIndex, fName.length())
				.toLowerCase();
		if(end == "")
			return type;
		//在MIME和文件类型的匹配表中找到对应的MIME类型
		for(int i=0;i<MIME_MapTable.length; i++){
			if(end.equals(MIME_MapTable[i][0])){
				type = MIME_MapTable[i][1];
			}
		}
		return type;
	}
	
	
	//MIME_MapTable是所有文件的后缀名所对应的MIME类型的一个String数组
	private final String[][] MIME_MapTable = {
		//{后缀名，MIME类型}
			{".3gp","video/3gpp"},
			{".apk","application/vnd.android.package-archive"},
			{"asf","video/x-ms-asf"},
			{".avi",    "video/x-msvideo"}, 
            {".bin",    "application/octet-stream"},
            {".bmp",    "image/bmp"}, 
            {".c",  "text/plain"}, 
            {".class",  "application/octet-stream"}, 
            {".conf",   "text/plain"}, 
            {".cpp",    "text/plain"}, 
            {".doc",    "application/msword"}, 
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, 
            {".xls",    "application/vnd.ms-excel"},  
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, 
            {".exe",    "application/octet-stream"}, 
            {".gif",    "image/gif"}, 
            {".gtar",   "application/x-gtar"}, 
            {".gz", "application/x-gzip"}, 
            {".h",  "text/plain"}, 
            {".htm",    "text/html"}, 
            {".html",   "text/html"}, 
            {".jar",    "application/java-archive"}, 
            {".java",   "text/plain"}, 
            {".jpeg",   "image/jpeg"}, 
            {".jpg",    "image/jpeg"}, 
            {".js", "application/x-javascript"}, 
            {".log",    "text/plain"}, 
            {".m3u",    "audio/x-mpegurl"}, 
            {".m4a",    "audio/mp4a-latm"}, 
            {".m4b",    "audio/mp4a-latm"}, 
            {".m4p",    "audio/mp4a-latm"}, 
            {".m4u",    "video/vnd.mpegurl"}, 
            {".m4v",    "video/x-m4v"},  
            {".mov",    "video/quicktime"}, 
            {".mp2",    "audio/x-mpeg"}, 
            {".mp3",    "audio/x-mpeg"}, 
            {".mp4",    "video/mp4"}, 
            {".mpc",    "application/vnd.mpohun.certificate"},        
            {".mpe",    "video/mpeg"},   
            {".mpeg",   "video/mpeg"},   
            {".mpg",    "video/mpeg"},   
            {".mpg4",   "video/mp4"},    
            {".mpga",   "audio/mpeg"}, 
            {".msg",    "application/vnd.ms-outlook"}, 
            {".ogg",    "audio/ogg"}, 
            {".pdf",    "application/pdf"}, 
            {".png",    "image/png"}, 
            {".pps",    "application/vnd.ms-powerpoint"}, 
            {".ppt",    "application/vnd.ms-powerpoint"}, 
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"}, 
            {".prop",   "text/plain"}, 
            {".rc", "text/plain"}, 
            {".rmvb",   "audio/x-pn-realaudio"}, 
            {".rtf",    "application/rtf"}, 
            {".sh", "text/plain"}, 
            {".tar",    "application/x-tar"},    
            {".tgz",    "application/x-compressed"},  
            {".txt",    "text/plain"}, 
            {".wav",    "audio/x-wav"}, 
            {".wma",    "audio/x-ms-wma"}, 
            {".wmv",    "audio/x-ms-wmv"}, 
            {".wps",    "application/vnd.ms-works"}, 
            {".xml",    "text/plain"}, 
            {".z",  "application/x-compress"}, 
            {".zip",    "application/x-zip-compressed"},
            {"","*/*"}
            
	};
	
	
	class PauseThread implements Runnable{

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = PAUSE;
			handler.sendMessage(msg);
		}
		
	}
	
	class MyThread implements Runnable{


		@Override
		public void run() {
			// 检查本地文件
			RandomAccessFile rndFile = null;
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				savePath = Environment.getExternalStorageDirectory().getAbsolutePath()
						+"/"+name+"."+fileType;
				Log.v("----savePath----", savePath);
				file = new File(savePath);
				
				long fileSize1 = Long.parseLong(fileByteSize);
				long nPos = 0;
				Log.v("----fileSize1----", fileSize1+"");
							
							
				long localFileSzie = file.length();
							
				if (file.exists()) {
					if (localFileSzie < fileSize1) {
//						System.out.println("文件续传...");
						//listener.onContinue(localFileSzie);
//						System.out.println(aa+"%");
						isPaused = false;
						Log.v("----文件续传...----", aa+"%");
						Message msg = handler.obtainMessage();
						msg.what = CONTINUE;
						msg.arg1 = aa;
						handler.sendMessage(msg);
						nPos = localFileSzie;
					} else {
						System.out.println("文件存在，重新下载...");
						isPaused = false;
//						file.delete();
						try {
							file.createNewFile();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}

				} else {
					// 建立文件
					try {
						file.createNewFile();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
							

				// 下载文件
				try {
					url = new URL(downloadPath);
					httpURLConnection = (HttpURLConnection) url.openConnection();
					// 设置User-Agent
					httpURLConnection.setRequestProperty("User-Agent", "Net");
					// 设置续传开始
					httpURLConnection
							.setRequestProperty("Range", "bytes=" + nPos + "-");
					// 获取输入流
					bis = new BufferedInputStream(httpURLConnection.getInputStream());
					rndFile = new RandomAccessFile(savePath, "rwd");
					rndFile.seek(nPos);
					Message msg= new Message();
					while ((size = bis.read(buf)) != -1) {
						if(isPaused){
							break;
						}
						// if (i > 500) break;
						rndFile.write(buf, 0, size);
						localFileSzie=localFileSzie+size;
//						Log.v("----localFileSzie----", localFileSzie+"");
						aa = (int) (localFileSzie*100/fileSize1);
						msg = handler.obtainMessage();
						msg.what = UPDATE_PROGRESS;
						msg.arg1 = aa;
									
//						System.out.println(msg.arg1+"%");
						handler.sendMessage(msg);
						//listener.setProgress(aa);
									
						//msg.arg2 = str;
//						handler.sendMessage(msg);
					}
//				httpURLConnection.disconnect();
				} catch (Exception e) {
//					e.printStackTrace();
//					handler.sendEmptyMessage(ERROR);
				}
			}
		}
		
	}

}
