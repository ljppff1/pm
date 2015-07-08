package com.superdata.pm.view;


/**
 * OnPageLoadListener接口
 * @author kw
 *
 */
public interface OnPageLoadListener {

	/**
	 * 触发分页事件
	 * @param pageSize
	 * @param pageIndex
	 */
	public void onPageChanging(int pageSize, int pageIndex);
	
	/**
	 * 是否能够加载数据，返回true时触发onPageChanging事件，否则不做任何处理
	 * @return
	 */
	public boolean canLoadData();
	
}
