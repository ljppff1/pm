package com.superdata.pm.activity.homepage.news;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.common.exception.ServerException;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.LEmployee;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.util.SharedPreferencesConfig;
import com.superdata.pm.view.OnPageLoadListener;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 选择接收人
 * @author kw
 *
 */
public class ChooseReceiverActivity extends BaseActivity
				implements OnClickListener{
	
	
	private ImageView btnBack;
	private ImageButton btnSearch;
	private Button btnSelectAll;
	private Button btnConfirm;
	private Button btnCancel;
	private TextView top_title;
	private EditText et_choosereceiver_search;
	
	private ListView listView;
	private List<LEmployee> list = new ArrayList<LEmployee>();
	private List<LEmployee> list1 = new ArrayList<LEmployee>();
	private MyAdapter adapter;
	
	private String empId;
	private String name;
	private int id;
	
	//是否输入关键字
	private boolean flag = false;
	//搜索的关键字
	private String keyword = "";
	
	
	CheckBox[] checkIteams;
	Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
	
	SDProgressDialog sdDialog;
	SDHttpClient sdClient;
	Message msg;
	LEmployee lEmployee;
	LEmployee lEmployee1;
	String url = InterfaceConfig.CHOOSERECEIVER_URL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosereceiver);
//		Intent intent = new Intent();
//		intent.getFlags();
		initView();
		initData();
	}
	
	
	
	

	public void initData(){
		top_title.setText("请选择收件人！");
		initData(empId);
	}
	
	
	public void initData(String empId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		
		new MyTask().execute(params);
	}
	
	
	
	public void initDataSearch(){
		initDataSearch(empId, keyword);
	}
	
	
	public void initDataSearch(String empId, String keyword){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("empId", empId));
		params.add(new BasicNameValuePair("keyword", keyword));
		
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
//					JSONObject jRoot = new JSONObject(result);
					JSONArray resultList = new JSONArray(result);
					list1.clear();
					
					for(int i=0; i<resultList.length(); i++){
						name = (String) resultList.getJSONObject(i).get("name");
						String deptName = (String) (resultList.getJSONObject(i).get("deptName").equals(null)?
								"":resultList.getJSONObject(i).get("deptName"));
						id = (Integer) resultList.getJSONObject(i).get("id");
						
						lEmployee1 = new LEmployee(name,deptName,id);
						list1.add(lEmployee1);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*msg = new Message();
			
			list.clear();
			list.addAll(list1);
			adapter = new MyAdapter();
			msg.what  = 1;
			
			if(sdDialog.isShow()){
				sdDialog.cancel();
			}
			
			*/
			list.clear();
			list.addAll(list1);
			adapter = new MyAdapter();
			listView.setAdapter(adapter);
			
			checkIteams = new CheckBox[list.size()];
//			handler.sendMessage(msg);
			
			super.onPostExecute(result);
		}
		
	}
	
	
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		et_choosereceiver_search = (EditText) findViewById(R.id.et_choosereceiver_search);
		
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		btnSearch = (ImageButton) findViewById(R.id.btn_choosereceiver_search);
		btnSelectAll = (Button) findViewById(R.id.btn_choosereceiver_selectall);
		btnConfirm = (Button) findViewById(R.id.btn_receiver_confirm);
		btnCancel = (Button) findViewById(R.id.btn_receiver_cancel);
		
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		btnSelectAll.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
//		btnConfirm.setBackgroundResource(R.drawable.btn_default_bg);
		
		Map<String, String> mapconfig = SharedPreferencesConfig
				.config(ChooseReceiverActivity.this);
		empId = mapconfig.get(InterfaceConfig.ID);
		
		sdDialog = new SDProgressDialog(this);
		lEmployee = new LEmployee();
		
		listView = (ListView) findViewById(R.id.lv_choosereceiver_list);
		listView.setCacheColorHint(0);
//		listView.setPullLoadEnable(true);
//		listView.setHeaderDividersEnabled(false);
//		listView.setFooterDividersEnabled(false);
	}
	
	
	
	
	@Override
	public void onClick(View v) {
		// 点击返回时
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
//					btnConfirm.setClickable(true);
				}
				
			}else{
				//所有项目全部不选中
				adapter.configCheckMap(false);
				adapter.notifyDataSetChanged();
				btnSelectAll.setText("全选");
				for (int i = 0; i < isCheckMap.size(); i++) {
					isCheckMap.put(i, false);
//					btnConfirm.setClickable(false);
				}
				
			}
		}
		
		
		// 点击搜索时
		if(v == btnSearch){
			keyword = et_choosereceiver_search.getText().toString();
			flag = true;
			initDataSearch();
		}
		
		
		// 点击完成时
		if(v == btnConfirm){
			Intent intent = new Intent(ChooseReceiverActivity.this,
					CompanyNewMessageActivity.class);
			StringBuffer sb1 = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();
					
			for(int i=0; i<isCheckMap.size(); i++){
				id = list1.get(i).getId();
				name = list1.get(i).getName();
				if(isCheckMap.get(i) == true){
					sb1.append(name+";");
					sb2.append(id+",");
				}
			}
			String name2 = sb1.toString();
			String id2 = sb2.toString();

				
			if(("").equals(name2)|| "" == name2){
				Toast.makeText(ChooseReceiverActivity.this,
						"请选择收件人！", Toast.LENGTH_SHORT).show();
			}else{
				String name1 = name2.substring(0, name2.lastIndexOf(";"));
				String id1  = id2.substring(0, id2.lastIndexOf(","));
				Log.d("--------name----------", name1);
				Log.d("--------id--------------", id1);
						
				intent.putExtra("name", name1);
				intent.putExtra("id", id1);
				setResult(200, intent);
				finish();
					
			}
					
		}
			
		
		// 点击取消时
		if(v == btnCancel){
			onBackPressed();
		}
		
		
		
	}
	
	
	
	
	
	
	
	/**=====================================================================================*/
	
	
	/**
	 * 自定义适配器
	 * @author Administrator
	 *
	 */
	class MyAdapter extends BaseAdapter{
		
		public MyAdapter(){
			for(int i=0; i<list.size(); i++){
				isCheckMap.put(i, false);
//				btnConfirm.setClickable(false);
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
			
			if(convertView ==null){
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.choosereceiver_item, null);
				holder = new Holder();
				
				holder.tv_receiver_name = (TextView) convertView
						.findViewById(R.id.tv_receiver_name);
				holder.tv_receiver_deptName = (TextView) convertView
						.findViewById(R.id.tv_receiver_deptName);
				checkIteams[position] = (CheckBox) convertView
						.findViewById(R.id.cb_receiver_listitem);
				
				convertView.setTag(holder);
			}else{
				holder = (Holder) convertView.getTag();
				checkIteams[position] = (CheckBox) convertView
						.findViewById(R.id.cb_receiver_listitem);
			}
			
			LEmployee bean = list.get(position);
			
			holder.tv_receiver_name.setText(bean.getName());
			holder.tv_receiver_deptName.setText(bean.getDeptName());
			
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
		
		
		
		class Holder {
			CheckBox cbCheck;
			TextView tv_receiver_name;
			TextView tv_receiver_deptName;
		}
		
		/**
		 * 将选中的checkbox放入list集合中
		 * @return
		 */
		public List<LEmployee> getList(){
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
