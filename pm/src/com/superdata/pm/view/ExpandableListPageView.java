package com.superdata.pm.view;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExpandableListPageView extends ExpandableListView
			implements OnScrollListener{
	
	
	public boolean canLoad = false;	//是否能够加载数据
	public int currentPageIndex = 0;	//记录页索引
	public int pageSize = 0;	//每页显示项目数
	public String loadMessage = "正在加载……";	//进度条提示消息
	public LinearLayout footerLayout;	//页脚
	public OnPageLoadListener listener;	//分布侦听事件
	public Context context;
	

	
	public ExpandableListPageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public ExpandableListPageView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	
	
	/**
	 * 设置第几页显示项目数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	/**
	 * 进度条显示的消息文本
	 * @param loadMessage
	 */
	public void setLoadMessage(String loadMessage) {
		this.loadMessage = loadMessage;
	}
	
	
	
	/**
	 * 设置数据源
	 */
	@Override
	public void setAdapter(ExpandableListAdapter adapter) {
		this.setOnScrollListener(this);
		// 必须在setAdapter()方法之前构建进度条并添加到页脚
		this.BuildProgressBar();
		super.setAdapter(adapter);
	}
	
	

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.canLoad = false;
		
		if(this.listener == null){
			return;
		}
		
		if((firstVisibleItem+visibleItemCount) == totalItemCount){
			this.canLoad = this.listener.canLoadData();
		}
		
	}

	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(this.canLoad && (scrollState == OnScrollListener.SCROLL_STATE_IDLE)){
			// 加载数据
			if(listener != null){
				this.currentPageIndex++;
				listener.onPageChanging(this.pageSize, currentPageIndex);
			}
		}
		
	}
	
	
	
	/**
	  * 是否显示进度条
	  * 
	  * @param isVisible
	  *            true:显示,false:不显示
	  */
	public void setProgressBarVisible(Boolean isVisible){
		if(this.footerLayout == null){
			return;
		}
		int visibility = View.VISIBLE;
		if(!isVisible){
			visibility = View.GONE;
		}
		
		// 定位到最后一行,必须设置，要不然进度条看不到
		this.setSelection(this.getAdapter().getCount());
		this.footerLayout.setVisibility(visibility);
		
		// 设置页脚中组件的显示状态
		for(int i=0; i<this.footerLayout.getChildCount(); i++){
			View v = this.footerLayout.getChildAt(i);
			v.setVisibility(visibility);
		}
	}
	
	
	
	/**
	  * 创建页脚显示进度条,必须在setAdapter()方法之前调用.
	  * 
	  * 
	  */
	private void BuildProgressBar(){
		if(this.getFooterViewsCount() != 0){
			return;
		}
		
		footerLayout = new LinearLayout(this.getContext());
		footerLayout.setGravity(Gravity.CENTER);
		footerLayout.setPadding(0, 0, 0, 0);
		footerLayout.setOrientation(LinearLayout.HORIZONTAL);
		ProgressBar bar = new ProgressBar(this.getContext());
		footerLayout.addView(bar);
		footerLayout.setBackgroundResource(R.color.white);
		TextView txt = new TextView(this.getContext());
		txt.setText(this.loadMessage);
		footerLayout.addView(txt);
		footerLayout.setVisibility(View.GONE);
		this.addFooterView(footerLayout);
		
	}
	
	
	/**
	  * 设置页加载侦听事件
	  * 
	  * @param listener
	  */
	public void setOnPageLoadListener(OnPageLoadListener listener){
		this.listener = listener;
	}
	

}
