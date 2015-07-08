package com.superdata.pm.adapter;

import java.util.List;

import com.suda.pm.ui.R;
import com.superdata.pm.entity.AppInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 应用数据适配器
 * @author chenheng
 * @title AppAdapter.java   
 * @package com.yubang.myphone.adapter 
 * @date 2014-4-15 上午10:53:17  
 * @version 1.0.0.1  
 * Copyright Copyright (c)  2014
 */
public class AppAdapter extends BaseAdapter {

	private List<AppInfo> lists = null;
	
	private LayoutInflater infater = null;
    
	public AppAdapter(Context context,  List<AppInfo> apps) {
		this.infater = LayoutInflater.from(context);
		this.lists = apps ;
	}

	@Override
	public int getCount() {
		if(lists == null){
			return 0; 
		}
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View view = null;
		ViewHolder holder = null;
		if (convertview == null) {
			view = infater.inflate(R.layout.app_gv_item, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertview ;
			holder = (ViewHolder) convertview.getTag() ;
		}
		AppInfo appInfo = (AppInfo) getItem(position);
		holder.appIcon.setImageDrawable(appInfo.getAppIcon());
		holder.tvAppLabel.setText(appInfo.getAppLabel());
		return view;
	}

	class ViewHolder {
		
		ImageView appIcon;
		TextView tvAppLabel;

		public ViewHolder(View view) {
			this.appIcon = (ImageView) view.findViewById(R.id.imgApp);
			this.tvAppLabel = (TextView) view.findViewById(R.id.tvAppLabel);
		}
		
	}

}
