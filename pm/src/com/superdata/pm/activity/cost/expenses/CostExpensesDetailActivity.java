package com.superdata.pm.activity.cost.expenses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suda.pm.ui.R;
import com.superdata.pm.activity.project.plan.WriteRateActivity;
import com.superdata.pm.common.BaseActivity;
import com.superdata.pm.config.InterfaceConfig;
import com.superdata.pm.entity.entitycostexpensesdetail;
import com.superdata.pm.service.SDHttpClient;
import com.superdata.pm.view.SDProgressDialog;

/**
 * 费用管理详情类
 * @author lj
 *
 * 2014年6月28日
 */

public class CostExpensesDetailActivity extends BaseActivity {
	private SDProgressDialog sdDialog;

	//审核状态
	private RelativeLayout rl_isverify;
	private ImageView back;
	private TextView tv_top_title;
	//总金额
	private ListView listview;
	private String id;
	List<entitycostexpensesdetail> listplanpack =new ArrayList<entitycostexpensesdetail>();
	private MyAdapter adapter;
	private int Position;

	private String STATUS;

	private String userId;
	//费用单号
	private String code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_expenses_detail);
		Map<String, String> mapconfig = com.superdata.pm.util.SharedPreferencesConfig
				.config(CostExpensesDetailActivity.this);
		userId = mapconfig.get(InterfaceConfig.ID);

		code = getIntent().getExtras().getString("CODE");
		id =getIntent().getExtras().getString("ID");
		Position =getIntent().getExtras().getInt("POSITION");
		STATUS =getIntent().getExtras().getString("STATUS");
		initData();
	    //初始化布局文件
		initView();
		//设置监听
		setListener();
		
	}


	private void initData() {
		initdata(id);
	}
	
	private void initdata(String id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("billId", id));
		new MyTask().execute(params);
	}
	private Handler handler =new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
			    
				listview.setAdapter(adapter);
	
				if (sdDialog.isShow()) {
					sdDialog.cancel();
				}
				adapter.notifyDataSetChanged();
				
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

	private TextView costex_de_item_tv_isverify;
	//String url ="http://192.168.0.117:8080/pm/costOrderInterface/queryFeeOrderDetail.do";
	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{
		String url =InterfaceConfig.COST_expenses_Detail_URL;
		@Override
        protected void onPreExecute() {
        	sdDialog = new com.superdata.pm.view.SDProgressDialog(CostExpensesDetailActivity.this);
    		sdDialog.show();
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
			 if(result!=null){
					try {
						JSONObject jo =new JSONObject(result);
						JSONArray array =jo.getJSONArray("resultData");
						for(int i=0;i<array.length();i++){
							String amount =(array.getJSONObject(i).get("amount")).equals(null)?"1":String.valueOf(array.getJSONObject(i).get("amount"));
							String remark =(array.getJSONObject(i).get("remark")).equals(null)?"暂未描述":String.valueOf(array.getJSONObject(i).get("remark"));
							String feeItemName =(array.getJSONObject(i).get("feeItemName")).equals(null)?"1":String.valueOf(array.getJSONObject(i).get("feeItemName"));
							String workTaskName =(array.getJSONObject(i).get("workTaskName")).equals(null)?"1":String.valueOf(array.getJSONObject(i).get("workTaskName"));
                            entitycostexpensesdetail e =new entitycostexpensesdetail(amount, remark, feeItemName, workTaskName);
							listplanpack.add(e);
					}
					}catch (JSONException e) {
						e.printStackTrace();
					}
				}
		     Message msg =new Message();
		    	adapter = new MyAdapter();
		       msg.what =1;
			     if(sdDialog.isShow())
			     sdDialog.cancel();
			     handler.sendMessage(msg);
			     super.onPostExecute(result);
		}
	}
	
	private void setListener() {
		rl_isverify.setOnClickListener(listener);	
		back.setOnClickListener(listener);
		
	}

	private void initView() {
		rl_isverify=(RelativeLayout)this.findViewById(R.id.costex_de_rl_isverify);
		back=(ImageView)this.findViewById(R.id.ll_top_title);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_title.setText(code);
		
		
		listview =(ListView)this.findViewById(R.id.costex_de_lv);
		costex_de_item_tv_isverify =(TextView)this.findViewById(R.id.costex_de_item_tv_isverify);
		if(STATUS.equals("1")){
			costex_de_item_tv_isverify.setText("通过");
		}else{
			costex_de_item_tv_isverify.setText("未审核");
		}
		adapter =new MyAdapter();
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listener2);
	}
       

	OnItemClickListener listener2=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent =new Intent(CostExpensesDetailActivity.this, CostExpensesDetailMoreActivity.class);
			Bundle bundle =new Bundle();
            bundle.putString("AMOUNT", listplanpack.get(position).getAmount());            
            bundle.putString("REMARK", listplanpack.get(position).getRemark());            
            bundle.putString("FEEITEMNAME", listplanpack.get(position).getFeeItemName());            
            bundle.putString("WORKTASKNAME", listplanpack.get(position).getWorkTaskName());            
			intent.putExtras(bundle);
		    startActivity(intent);
		}
	};
	
	OnClickListener listener =new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.costex_de_rl_isverify:
				verify();
				break;
			case R.id.ll_top_title:
				onBackPressed();
				break;
			default:
				break;
			}
		}
		/**
         * 审核处理
         */
		private void verify() {
			showDialog();
		}
	};

	public void showDialog(){
		String str ="";
		if(STATUS.equals("0")){
			str="您是否提交审核";
		}else{
			str="您是否提交反审核";
		}
		new AlertDialog.Builder(CostExpensesDetailActivity.this)
		.setTitle(str)
		.setIcon(android.R.drawable.ic_dialog_info)

		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 点击确定发送选择的结果到handler
						AuditPass();
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).show();
	}
	/**
	 * 审核通过 ，在这里提交一个请求
	 */
	private void AuditPass() {
		if(STATUS.equals("0")){
		initdata(id,userId);
		}else{
			initdata2(id);
		}
	}
	private void initdata(String billId , String auditUserId ) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("billId", billId));
		params.add(new BasicNameValuePair("auditUserId", auditUserId));
		new MyTask1().execute(params);
	}
	class MyTask1 extends AsyncTask<List<NameValuePair>, Integer, String> {
		String url = InterfaceConfig.CostExpensesDetailAudit;
		@Override
		protected void onPreExecute() {
			sdDialog = new com.superdata.pm.view.SDProgressDialog(
					CostExpensesDetailActivity.this);
//			sdDialog.show();
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
			String code;
			Message msg = new Message();
			if (result != null) {
				try {
					// {"code":1000,"msg":"进度填报成功"}
					JSONObject jo = new JSONObject(result);
					code = jo.getInt("resultCode") + "";
					String str = jo.getString("resultMsg");
					if (code.equals("200")) {
						msg.what = 1;
						 msg.obj =str;	
					} else{
						msg.what = 2;
                        msg.obj =str;						
					} 
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what =6;
				}
			}
			handler1.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	private void initdata2(String billId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("billId",billId));
		new MyTask2().execute(params);
	}
	class MyTask2 extends AsyncTask<List<NameValuePair>, Integer, String> {
		String url = InterfaceConfig.CostExpensesDetailAuditRETURN;
		@Override
		protected void onPreExecute() {
			sdDialog = new com.superdata.pm.view.SDProgressDialog(
					CostExpensesDetailActivity.this);
//			sdDialog.show();
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
			String code;
			Message msg = new Message();
			if (result != null) {
				try {
					// {"code":1000,"msg":"进度填报成功"}
					JSONObject jo = new JSONObject(result);
					code = jo.getInt("resultCode") + "";
					String str = jo.getString("resultMsg");
					if (code.equals("200")) {
						msg.what = 4;
						 msg.obj =str;	
					} else{
						msg.what = 5;
                        msg.obj =str;						
					} 
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what =6;
				}
			}
			handler1.sendMessage(msg);
			super.onPostExecute(result);
		}
	}
	private Handler handler1 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
			//	Toast.makeText(CostExpensesDetailActivity.this, msg.obj.toString(),Toast.LENGTH_SHORT).show();
				Intent intent =new Intent();
				intent.putExtra("POSITION", Position);
				setResult(1, intent);
				finish();
				break;
			case 2:
				Toast.makeText(CostExpensesDetailActivity.this,  msg.obj.toString(),Toast.LENGTH_SHORT).show();
				if(sdDialog.isShow())
					sdDialog.cancel();
				break;
			case 3:
				Toast.makeText(CostExpensesDetailActivity.this, "审核失败",Toast.LENGTH_SHORT).show();
				if(sdDialog.isShow())
					sdDialog.cancel();

				break;
			case 4:
			//	Toast.makeText(CostExpensesDetailActivity.this, msg.obj.toString(),Toast.LENGTH_SHORT).show();
				Intent intent2 =new Intent();
				intent2.putExtra("POSITION", Position);
				setResult(2, intent2);
				finish();
				break;
			case 5:
				Toast.makeText(CostExpensesDetailActivity.this, msg.obj.toString(),Toast.LENGTH_SHORT).show();
				if(sdDialog.isShow())
					sdDialog.cancel();

				break;
			case 6:
				Toast.makeText(CostExpensesDetailActivity.this, "出现异常",Toast.LENGTH_SHORT).show();
				if(sdDialog.isShow())
					sdDialog.cancel();

				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	
	
	
	
	
	
	
	
	class MyAdapter extends BaseAdapter{
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
			HolderView holderView;
			if(convertView==null){
				holderView=new HolderView();
				convertView=LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.cost_expenses_details_addmore_item, null);
                holderView.exp1=(TextView) convertView.findViewById(R.id.costex_item_tv_expname);
                holderView.mon1=(TextView)convertView.findViewById(R.id.costex_item_tv_money);
				convertView.setTag(holderView);
			}else{
				holderView=(HolderView)convertView.getTag();
			}
           holderView.exp1.setText(listplanpack.get(position).getFeeItemName());
           holderView.mon1.setText(listplanpack.get(position).getAmount());
			return convertView;
		}
		
	}
	
	class HolderView{
		TextView exp1,mon1;
	}
	
}
