package com.superdata.pm.activity.document.doucumentmanager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.IntentUtils;

/**
 * 文档-->文档管理-->文档详细
 * @author kw 
 *
 */
public class DocumentsManagerDetailActivity extends BaseActivity {
	
	private ImageView back;//返回
	private TextView top_title;//标题
	private TextView tv_companydocuments_detail_docnumber;//文档编号
	private TextView tv_companydocuments_detail_docname;//文档名称
	private TextView tv_companydocuments_detail_doccategory;//文档类别
	private TextView tv_companydocuments_detail_doccreatetime;//创建时间
	private TextView tv_companydocuments_detail_doccreater;//创建人
	private TextView tv_companydocuments_detail_docstate;//文档内容
	
	private int id;//文档id
	
	String url = InterfaceConfig.DOCUMENTSDETAIL_URL;
	SDHttpClient sdClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.documentsmanager_detail);
		
		id = getIntent().getExtras().getInt("id");
		
		initView();//初始化视图
		initData();//初始化数据
		
	}
	
	
	public void initData(){
		top_title.setText("文档详细");
		initData(id+"");
	}
	
	public void initData(String id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		
		new MyTask().execute(params);
	}
	
	
	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String>{

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
						//文档编号
						String code = (String) jsonObject.get("code");
						//文档名称
						String name = (String) jsonObject.get("name");
						//文档类别
						String typeName = (String) jsonObject.get("typeName");
						//文档内容
						String content = (String) jsonObject.get("content");
						//创建人
						String createName = (String) jsonObject.get("createName");
						//创建时间
						String createDate = (String) jsonObject.get("createDate");
						
						
						tv_companydocuments_detail_docnumber.setText(code);
						tv_companydocuments_detail_docname.setText(name);
						tv_companydocuments_detail_doccategory.setText(typeName);
						tv_companydocuments_detail_doccreatetime.setText(createDate);
						tv_companydocuments_detail_doccreater.setText(createName);
						tv_companydocuments_detail_docstate.setText(content);
					}else{
						Toast.makeText(DocumentsManagerDetailActivity.this,
								"查询失败！", Toast.LENGTH_SHORT).show();
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
		
		tv_companydocuments_detail_docnumber = (TextView)
				findViewById(R.id.tv_companydocuments_detail_docnumber);
		tv_companydocuments_detail_docname = (TextView)
				findViewById(R.id.tv_companydocuments_detail_docname);
		tv_companydocuments_detail_doccategory = (TextView)
				findViewById(R.id.tv_companydocuments_detail_doccategory);
		tv_companydocuments_detail_doccreater = (TextView)
				findViewById(R.id.tv_companydocuments_detail_doccreater);
		tv_companydocuments_detail_doccreatetime = (TextView)
				findViewById(R.id.tv_companydocuments_detail_doccreatetime);
		tv_companydocuments_detail_docstate = (TextView)
				findViewById(R.id.tv_companydocuments_detail_docstate);
		
		
		back = (ImageView) findViewById(R.id.ll_top_title);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
	}

	
}
