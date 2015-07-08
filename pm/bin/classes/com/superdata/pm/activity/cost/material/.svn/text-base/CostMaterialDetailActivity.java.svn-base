package com.superdata.pm.activity.cost.material;

import java.text.DecimalFormat;
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

public class CostMaterialDetailActivity extends BaseActivity {
    
	//审核状态
	private RelativeLayout rl_isverify;
	private ImageView back;
	private TextView tv_material_detail_title;
	//总金额
	private RelativeLayout rl_money;
	List<entitycostexpensesdetail> listplanpack =new ArrayList<entitycostexpensesdetail>();
	private MyAdapter adapter;
	private String id;
	private String projectId;
	private int Position;
	private String STATUS;
	private String userId;
	private String code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cost_material_detail);
		Map<String, String> mapconfig = com.superdata.pm.util.SharedPreferencesConfig
				.config(CostMaterialDetailActivity.this);
		userId = mapconfig.get(InterfaceConfig.ID);

		id =getIntent().getExtras().getString("ID");
		Position =getIntent().getExtras().getInt("POSITION");
		STATUS =getIntent().getExtras().getString("STATUS");
		code = getIntent().getExtras().getString("CODE");

	//	projectId =getIntent().getExtras().getString("PROJECTID");
		 //初始化布局文件
		initView();
		
		initData();
		
	   
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
			    
				listView.setAdapter(adapter);
	
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
//	String url ="http://192.168.0.118:8080/pm/costOrderInterface/queryProductOrderDetail.do";
	String url =InterfaceConfig.COST_material_Detail_URL;
	private ListView listView;
	private SDProgressDialog sdDialog;

	class MyTask extends AsyncTask<	List<NameValuePair> , Integer, String>{




		@Override
        protected void onPreExecute() {
     		
        	 sdDialog = new com.superdata.pm.view.SDProgressDialog(CostMaterialDetailActivity.this);
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
		DecimalFormat df = new DecimalFormat("#.##");
		@Override
		protected void onPostExecute(String result) {
			 if(result!=null){
					try {
						JSONObject jo =new JSONObject(result);
						JSONArray array =jo.getJSONArray("resultData");
						for(int i=0;i<array.length();i++){
							String amount =(array.getJSONObject(i).get("amount")).equals(null)?"空":String.valueOf(df.format(array.getJSONObject(i).get("amount")));
							String remark =(array.getJSONObject(i).get("remark")).equals(null)?"暂未描述":String.valueOf(array.getJSONObject(i).get("remark"));
							String feeItemName =(array.getJSONObject(i).get("productName")).equals(null)?"空":String.valueOf(array.getJSONObject(i).get("productName"));
							String workTaskName =(array.getJSONObject(i).get("workTaskName")).equals(null)?"无":String.valueOf(array.getJSONObject(i).get("workTaskName"));
							String price =(array.getJSONObject(i).get("price")).equals(null)?"no":String.valueOf(df.format(array.getJSONObject(i).get("price")));
							String count_2 =(array.getJSONObject(i).get("unitName")).equals(null)?"":String.valueOf(array.getJSONObject(i).get("unitName"));
							String quantity =(array.getJSONObject(i).get("quantity")).equals(null)?"0":String.valueOf(df.format(array.getJSONObject(i).get("quantity")));
							 
							 String aaa=    df.format(9.999999999E9);
							
							String count =quantity+count_2;
	                        
                            entitycostexpensesdetail e =new entitycostexpensesdetail(amount, remark, feeItemName, workTaskName, count,price);
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
		rl_isverify=(RelativeLayout)this.findViewById(R.id.costmat_de_rl_isverify);
		tv_material_detail_title = (TextView) findViewById(R.id.tv_top_title);
		back=(ImageView)this.findViewById(R.id.ll_top_title);
		tv_material_detail_title.setText(code);
		listView =(ListView)this.findViewById(R.id.costmat_de_lv);
		adapter =new MyAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listener2);
	}
       
	OnItemClickListener listener2=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent =new Intent(CostMaterialDetailActivity.this, CostMaterialDetailMoreActivity.class);
			Bundle bundle =new Bundle();
            bundle.putString("PRICE", listplanpack.get(position).getPrice());            
            bundle.putString("REMARK", listplanpack.get(position).getRemark());            
            bundle.putString("FEEITEMNAME", listplanpack.get(position).getFeeItemName());            
            bundle.putString("WORKTASKNAME", listplanpack.get(position).getWorkTaskName());     
            bundle.putString("COUNT", listplanpack.get(position).getCount());
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.costmat_de_rl_isverify:
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
		new AlertDialog.Builder(CostMaterialDetailActivity.this)
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
		String url = InterfaceConfig.CostMaterialDetailAudit;
		@Override
		protected void onPreExecute() {
			sdDialog = new com.superdata.pm.view.SDProgressDialog(
					CostMaterialDetailActivity.this);
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
		String url = InterfaceConfig.CostMaterialDetailAuditRETURN;
		@Override
		protected void onPreExecute() {
			sdDialog = new com.superdata.pm.view.SDProgressDialog(
					CostMaterialDetailActivity.this);
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
			//	Toast.makeText(CostMaterialDetailActivity.this, msg.obj.toString(),Toast.LENGTH_SHORT).show();
				Intent intent =new Intent();
				intent.putExtra("POSITION", Position);
				setResult(1, intent);
				finish();
				break;
			case 2:
				Toast.makeText(CostMaterialDetailActivity.this,  msg.obj.toString(),Toast.LENGTH_SHORT).show();
				if(sdDialog.isShow())
					sdDialog.cancel();
				break;
			case 3:
				Toast.makeText(CostMaterialDetailActivity.this, "审核失败",Toast.LENGTH_SHORT).show();
				if(sdDialog.isShow())
					sdDialog.cancel();

				break;
			case 4:
			//	Toast.makeText(CostMaterialDetailActivity.this, msg.obj.toString(),Toast.LENGTH_SHORT).show();
				Intent intent2 =new Intent();
				intent2.putExtra("POSITION", Position);
				setResult(2, intent2);
				finish();
				break;
			case 5:
				Toast.makeText(CostMaterialDetailActivity.this, msg.obj.toString(),Toast.LENGTH_SHORT).show();
				if(sdDialog.isShow())
					sdDialog.cancel();

				break;
			case 6:
				Toast.makeText(CostMaterialDetailActivity.this, "出现异常",Toast.LENGTH_SHORT).show();
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
						.inflate(R.layout.cost_material_details_addmore_item, null);
                holderView.exp1=(TextView) convertView.findViewById(R.id.costmat_item_tv_expname);
                holderView.mon1=(TextView)convertView.findViewById(R.id.costmat_item_tv_money);
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
