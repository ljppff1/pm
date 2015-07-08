package com.superdata.pm.activity.project.plan;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.entityplanpackage;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.view.AbstractSpinerAdapter.IOnItemSelectListener;
import com.superdata.pm.view.CustemSpinerAdapter;
import com.superdata.pm.view.SDProgressDialog;
import com.superdata.pm.view.SpinerPopWindow;
import com.superdata.pm.view.XListView;
import com.superdata.pm.view.XListView.IXListViewListener;

/**
 * 项目--》项目管理--》项目详细--》项目计划（工作包）
 * 
 * @author kw
 * 
 */
public class ProjectPlanPackageActivity extends BaseActivity implements
		IXListViewListener,OnClickListener {
	
	
	private ImageView btnBack;
	private TextView top_title;
	// 费用管理条目
	private XListView listView;
	// 获取的是第几页的数据
	private int pageNum = 1;
	// 获取的这页的数量
	private int pageSize = 10;
	// Adapter里面放的List，
	private List<entityplanpackage> listplanpack = new ArrayList<entityplanpackage>();
	// 临时的List，用于加载更多 的逻辑处理
	private List<entityplanpackage> listplanpack1 = new ArrayList<entityplanpackage>();
	
	private SDProgressDialog sdDialog;
	// 标识是否加载更多
	private boolean flag = true;
	private boolean isLastPage = false;
	private MyAdapter adapter;
	private String id;
	
	entityplanpackage epp;
	SDHttpClient sdClient;
	String url = InterfaceConfig.PROJECT_Plan_Package_URL;
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				listView.setAdapter(adapter);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			case 2:
				flag = true;
				listView.setAdapter(adapter);
				listView.setSelection(listplanpack.size() - pageSize);
				adapter.notifyDataSetChanged();// 数据变化刷新

				if (sdDialog.isShow())
					sdDialog.cancel();
				onLoad();
				break;
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(), "已全部加载完成",
						Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(getApplicationContext(), "暂无网络", 1).show();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			case 6:
				Toast.makeText(getApplicationContext(), "暂时无法成功获取", 1).show();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectplan_package);
		
		initView();//初始化视图
		initData();
	}
	
	
	public void initData(){
		top_title.setText("工作包");
		initdata(id, pageNum + "", pageSize + "", 1 + "");
	}
	
	
	private void initdata(String projectId, String pageNum, String pageSize,
			String kind) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", pageNum));
		params.add(new BasicNameValuePair("pageSize", pageSize));
		params.add(new BasicNameValuePair("kind", kind));
		new MyTask().execute(params);
	}
	
	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String> {
		@Override
		protected void onPreExecute() {
			if (pageNum == 1) {
				sdDialog = new com.superdata.pm.view.SDProgressDialog(
						ProjectPlanPackageActivity.this);
				sdDialog.show();
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(List<NameValuePair>... params) {

			String aa = null;
			sdClient = new SDHttpClient();
			try {
				aa = sdClient.post_session(url, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return aa;
		}

		DecimalFormat df = new DecimalFormat("#.##");
		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					JSONObject jRoot = new JSONObject(result);
					JSONObject jaresultList1 = jRoot
							.getJSONObject("resultData");
					JSONArray jaresultList = jaresultList1
							.getJSONArray("resultList");
					int totalSize = jaresultList1.getInt("totalSize");
					if (totalSize >= pageNum * pageSize) {
						isLastPage = false;
					} else {
						isLastPage = true;
					}

					listplanpack1.clear();
					for (int i = 0; i < jaresultList.length(); i++) {
						String name = (jaresultList.getJSONObject(i)
								.get("name")).equals(null) ? "" + i
								: (String) jaresultList.getJSONObject(i).get(
										"name");
						String packagestartdata = (jaresultList
								.getJSONObject(i).get("startDate"))
								.equals(null) ? ""
								: ((String) jaresultList.getJSONObject(i).get(
										"startDate")).substring(0, 10);
						String packageenddata = (jaresultList.getJSONObject(i)
								.get("endDate")).equals(null) ? ""
								: ((String) jaresultList.getJSONObject(i).get(
										"endDate")).substring(0, 10);
						String lasttime = (jaresultList.getJSONObject(i)
								.get("lastTime")).equals(null) ? 0 + ""
								: df.format(jaresultList.getJSONObject(i)
										.get("lastTime")) + "天";
						String empName = ((jaresultList.getJSONObject(i)
								.get("empName")).equals(null)) ? ""
								: (String) jaresultList.getJSONObject(i).get(
										"empName");
						Integer id = (Integer) (((jaresultList.getJSONObject(i)
								.get("id")).equals(null)) ? 1 : jaresultList
								.getJSONObject(i).get("id"));
						Integer projectId = (Integer) (((jaresultList
								.getJSONObject(i).get("projectId"))
								.equals(null)) ? 1 : jaresultList
								.getJSONObject(i).get("projectId"));
						String projectName = ((jaresultList.getJSONObject(i)
								.get("projectName")).equals(null)) ? ""
								: (String) jaresultList.getJSONObject(i).get(
										"projectName");
						entityplanpackage epp = new entityplanpackage(name,
								packagestartdata, packageenddata, lasttime,
								empName, projectId, projectName, id);
						listplanpack1.add(epp);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			Message msg = new Message();
			// 加载更多 时
			if (!flag) {
				listplanpack.addAll(listplanpack1);
				listplanpack1.clear();
				adapter = new MyAdapter();
				msg.what = 2;
			} else {
				// 第一次
				// 重新加载全部的
				listplanpack.clear();
				listplanpack.addAll(listplanpack1);
				adapter = new MyAdapter();
				msg.what = 1;
			}
			if (sdDialog.isShow())
				sdDialog.cancel();
			handler.sendMessage(msg);

			super.onPostExecute(result);
		}
	}
	
	
	public void initView(){
		top_title = (TextView) findViewById(R.id.tv_top_title);
		btnBack = (ImageView) findViewById(R.id.ll_top_title);
		
		id = getIntent().getExtras().getString("projectId");
		sdDialog = new SDProgressDialog(this);
		
		btnBack.setOnClickListener(this);
		
		listView = (XListView) findViewById(R.id.lv_projectplan_package);
		listView.setOnItemClickListener(new ProjectPlanListener());
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}
	
	
	private class ProjectPlanListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			Intent intent = new Intent(ProjectPlanPackageActivity.this,
					ProjectPlanTaskActivity.class);
			intent.putExtra("PACKAGEID", listplanpack.get(position - 1)
					.getId());
			intent.putExtra("PROJECTID", listplanpack.get(position - 1)
					.getProjectId());
			intent.putExtra("PROJECTNAME", listplanpack.get(position - 1)
					.getProjectName());
			startActivity(intent);
			
		}
		
	}

	/*// 返回按钮
	private ImageView back;
	// 费用管理条目
	private XListView listView;
	// 获取的是第几页的数据
	private int page = 1;
	// 获取的这页的数量
	private int pagesize = 7;
	// Adapter里面放的List，
	private List<entityplanpackage> listplanpack = new ArrayList<entityplanpackage>();
	// 临时的List，用于加载更多 的逻辑处理
	private List<entityplanpackage> listplanpack1 = new ArrayList<entityplanpackage>();
	private com.superdata.pm.view.SDProgressDialog sdDialog;
	// 标识是否加载更多
	private boolean flag = true;
	private boolean isLastPage = false;
	private MyAdapter adapter;
	private TextView top_title;
	// 存的是项目
	Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
	private ArrayList<String> list;
	private CustemSpinerAdapter mAdapter;
	private SpinerPopWindow mSpinerPopWindow;
	private TextView proplanpack_projectby_items;
	private ImageView proplanpack_projectby_iv;
	private String id;
	private List<String> nameList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projectplan_package);

		init();// 初始化
		initTopSelect();

		// 点击返回
		back = (ImageView) findViewById(R.id.proplanpack_iv_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 回退
				onBackPressed();
			}
		});

	}

	private void initTopSelect() {
		mAdapter = new CustemSpinerAdapter(this);
		proplanpack_projectby_iv = (ImageView) this
				.findViewById(R.id.proplanpack_projectby_iv);
		proplanpack_projectby_items = (TextView) this
				.findViewById(R.id.proplanpack_projectby_items);
		proplanpack_projectby_iv.setOnClickListener(listener);
		mAdapter = new CustemSpinerAdapter(this);
		mSpinerPopWindow = new SpinerPopWindow(this);
		mSpinerPopWindow.setItemListener(this);

		intitData();

	}

	public void intitData() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pageNum", 1 + ""));
		params.add(new BasicNameValuePair("pageSize", 100 + ""));
		new MyTask1().execute(params);
	}

	class MyTask1 extends AsyncTask<List<NameValuePair>, Integer, String> {
		String url = InterfaceConfig.PROJECT_LIST_URL1;

		@Override
		protected void onPreExecute() {
			sdDialog = new com.superdata.pm.view.SDProgressDialog(
					ProjectPlanPackageActivity.this);
			sdDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			String json = null;
			try {
				json = new SDHttpClient().post_session(url, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					JSONObject jRoot = new JSONObject(result);
					// list = new ArrayList<HashMap<String,Object>>();
					JSONArray resultList = jRoot.getJSONArray("resultList");
					for (int i = 0; i < resultList.length(); i++) {
						String name = (String) (resultList.getJSONObject(i)
								.get("name"));// 项目名称
						Integer id = (Integer) (resultList.getJSONObject(i)
								.get("id"));// 项目id
						list = new ArrayList<String>();
						list.add(id + "");
						list.add(name);
						map.put(i, list);
					}
				} catch (JSONException e) {
					Message msg = new Message();
					msg.what = 6;
					handler.handleMessage(msg);
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = 7;
				handler.handleMessage(msg);

			} else {
				Message msg = new Message();
				msg.what = 5;
				handler.handleMessage(msg);
			}

		}

	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.proplanpack_projectby_iv:
				showSpinWindow();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onItemClick(int pos) {
		proplanpack_projectby_items.setText(nameList.get(pos));
		id = map.get(pos).get(0);
		initData();
		page = 1;
		isLastPage = false;
	}

	private void showSpinWindow() {
		mSpinerPopWindow.setWidth(proplanpack_projectby_items.getWidth());
		mSpinerPopWindow.showAsDropDown(proplanpack_projectby_items);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				listView.setAdapter(adapter);
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			case 2:
				flag = true;
				listView.setAdapter(adapter);
				listView.setSelection(listplanpack.size() - pagesize);
				adapter.notifyDataSetChanged();// 数据变化刷新

				if (sdDialog.isShow())
					sdDialog.cancel();
				onLoad();
				break;
			case 3:
				onLoad();
				Toast.makeText(getApplicationContext(), "已全部加载完成",
						Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(getApplicationContext(), "暂无网络", 1).show();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			case 6:
				Toast.makeText(getApplicationContext(), "暂时无法成功获取", 1).show();
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				break;
			case 7:
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}

				for (int i = 0; i < map.size(); i++) {
					nameList.add(map.get(i).get(1));
				}
				proplanpack_projectby_items.setText(nameList.get(0));
				id = map.get(0).get(0);
				proplanpack_projectby_iv.setOnClickListener(listener);
				mAdapter.refreshData(nameList, 0);
				mSpinerPopWindow.setAdatper(mAdapter);
				// 获取数据
				initData();

				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}

	private void initData() {
		initdata(id, page + "", pagesize + "", 1 + "");
	}

	private void initdata(String projectId, String page, String size,
			String kind) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("projectId", projectId));
		params.add(new BasicNameValuePair("pageNum", page));
		params.add(new BasicNameValuePair("pageSize", size));
		params.add(new BasicNameValuePair("kind", kind));
		new MyTask().execute(params);
	}

	// http://localhost:8080/pm/taskInterface/queryTask.do
	// String url ="http://192.168.0.117:8080/pm/taskInterface/queryTask.do";
	String url = InterfaceConfig.PROJECT_Plan_Package_URL;

	class MyTask extends AsyncTask<List<NameValuePair>, Integer, String> {
		@Override
		protected void onPreExecute() {
			if (page == 1) {
				sdDialog = new com.superdata.pm.view.SDProgressDialog(
						ProjectPlanPackageActivity.this);
				sdDialog.show();
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(List<NameValuePair>... params) {

			String aa = null;
			try {
				aa = new SDHttpClient().post_session(url, params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return aa;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					JSONObject jRoot = new JSONObject(result);
					JSONObject jaresultList1 = jRoot
							.getJSONObject("resultData");
					JSONArray jaresultList = jaresultList1
							.getJSONArray("resultList");
					int totalSize = jaresultList1.getInt("totalSize");
					if (totalSize >= page * pagesize) {
						isLastPage = false;
					} else {
						isLastPage = true;
					}

					listplanpack1.clear();
					for (int i = 0; i < jaresultList.length(); i++) {
						String name = (jaresultList.getJSONObject(i)
								.get("name")).equals(null) ? "工作包名没有" + i
								: (String) jaresultList.getJSONObject(i).get(
										"name");
						String packagestartdata = (jaresultList
								.getJSONObject(i).get("startDate"))
								.equals(null) ? "2014-01-01"
								: ((String) jaresultList.getJSONObject(i).get(
										"startDate")).substring(0, 10);
						String packageenddata = (jaresultList.getJSONObject(i)
								.get("endDate")).equals(null) ? "2014-03-03"
								: ((String) jaresultList.getJSONObject(i).get(
										"endDate")).substring(0, 10);
						String lasttime = (jaresultList.getJSONObject(i)
								.get("lastTime")).equals(null) ? 0 + ""
								: String.valueOf(jaresultList.getJSONObject(i)
										.get("lastTime")) + "天";
						String empName = ((jaresultList.getJSONObject(i)
								.get("empName")).equals(null)) ? "负责人没有"
								: (String) jaresultList.getJSONObject(i).get(
										"empName");
						Integer id = (Integer) (((jaresultList.getJSONObject(i)
								.get("id")).equals(null)) ? 1 : jaresultList
								.getJSONObject(i).get("id"));
						Integer projectId = (Integer) (((jaresultList
								.getJSONObject(i).get("projectId"))
								.equals(null)) ? 1 : jaresultList
								.getJSONObject(i).get("projectId"));
						String projectName = ((jaresultList.getJSONObject(i)
								.get("projectName")).equals(null)) ? "无所属项目"
								: (String) jaresultList.getJSONObject(i).get(
										"projectName");
						entityplanpackage epp = new entityplanpackage(name,
								packagestartdata, packageenddata, lasttime,
								empName, projectId, projectName, id);
						listplanpack1.add(epp);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			Message msg = new Message();
			// 加载更多 时
			if (!flag) {
				listplanpack.addAll(listplanpack1);
				listplanpack1.clear();
				adapter = new MyAdapter();
				msg.what = 2;
			} else {
				// 第一次
				// 重新加载全部的
				listplanpack.clear();
				listplanpack.addAll(listplanpack1);
				adapter = new MyAdapter();
				msg.what = 1;
			}
			if (sdDialog.isShow())
				sdDialog.cancel();
			handler.sendMessage(msg);

			super.onPostExecute(result);
		}
	}

	// 初始化数据的方法
	public void init() {

		listView = (com.superdata.pm.view.XListView) findViewById(R.id.lv_projectplan_package);// 初始化listview
		listView.setCacheColorHint(0);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ProjectPlanPackageActivity.this,
						ProjectPlanTaskActivity.class);
				intent.putExtra("PACKAGEID", listplanpack.get(position - 1)
						.getId());
				intent.putExtra("PROJECTID", listplanpack.get(position - 1)
						.getProjectId());
				intent.putExtra("PROJECTNAME", listplanpack.get(position - 1)
						.getProjectName());
				startActivity(intent);
			}
		});

	}*/
	
	
	@Override
	public void onRefresh() {
		pageNum = 1;
		initdata(id, 1 + "", pageSize + "", 1 + "");
		onLoad();
	}

	@Override
	public void onLoadMore() {
		if (!isLastPage) {
			if (flag) {
				flag = false;
				++pageNum;
				initdata(id, pageNum + "", pageSize + "", 1 + "");
			}
		} else {
			handler.sendEmptyMessage(3);
		}
	}

	@Override
	public void onClick(View v) {
		
		if(v == btnBack){
			onBackPressed();
		}
		
	}
	

	/**
	 * 自定义适配器
	 * 
	 * @author Administrator
	 * 
	 */
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listplanpack.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listplanpack.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.projectplan_package_item, null);

				holder.package_name = (TextView) convertView
						.findViewById(R.id.tv_cb_projectplan_package_name);
				holder.package_startdata = (TextView) convertView
						.findViewById(R.id.tv_cb_projectplan_package_startdata);
				holder.package_enddata = (TextView) convertView
						.findViewById(R.id.tv_cb_projectplan_package_enddata);
				holder.package_duration = (TextView) convertView
						.findViewById(R.id.tv_cb_projectplan_package_duration);
				holder.package_person = (TextView) convertView
						.findViewById(R.id.tv_cb_projectplan_package_person);
				holder.projectby = (TextView) convertView
						.findViewById(R.id.tv_cb_projectplan_package_projectby);
				convertView.setTag(holder);
			} else {

				holder = (Holder) convertView.getTag();
			}

			holder.package_name.setText(listplanpack.get(position)
					.getPackagename());
			holder.package_startdata.setText(listplanpack.get(position)
					.getPackagestartdata());
			holder.package_enddata.setText(listplanpack.get(position)
					.getPackageenddata());
			holder.package_duration.setText(listplanpack.get(position)
					.getPackageduration());
			holder.package_person.setText(listplanpack.get(position)
					.getPackageheader());
			holder.projectby.setText(listplanpack.get(position)
					.getProjectName());
			return convertView;
		}

		class Holder {
			TextView package_name; // 工作包名称
			TextView package_startdata; // 工作包开始时间
			TextView package_enddata; // 工作包结束时间
			TextView package_duration; // 工作包持续时间
			TextView package_person; // 工作包负责人
			TextView projectby;// 所属项目
		}

	}


}
