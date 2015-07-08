package com.superdata.pm.common;

import java.io.File;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.superdata.pm.config.SDAppConfig;

/**
 * Title: BaseActivity
 * Description: 继承本类，对程序进行管理
 * Company: SD 
 * @author luolei
 * @date 2014年6月6日
 */
public class BaseActivity extends Activity{
		// 是否允许全屏
		private boolean allowFullScreen = true;
		// 是否允许销毁
		private boolean allowDestroy = true;

		private View view;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
							| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			allowFullScreen = true;
			SDAppManager.getAppManager().addActivity(this); // 添加Activity到堆栈
		}

		@Override
		protected void onDestroy() {
			super.onDestroy();
			SDAppManager.getAppManager().finishActivity(this); // 结束Activity&从堆栈中移除
		}

		public boolean isAllowFullScreen() {
			return allowFullScreen;
		}

		/**
		 * 设置是否可以全屏
		 * 
		 * @param allowFullScreen
		 */
		public void setAllowFullScreen(boolean allowFullScreen) {
			this.allowFullScreen = allowFullScreen;
		}

		public void setAllowDestroy(boolean allowDestroy) {
			this.allowDestroy = allowDestroy;
		}

		public void setAllowDestroy(boolean allowDestroy, View view) {
			this.allowDestroy = allowDestroy;
			this.view = view;
		}

		/**
		 * 隐藏左侧按钮
		 */
		public void setHideButtonLeft(int leftBtId, boolean bSetHide) {
			ImageView bt = setButtonLeft(leftBtId);
			if (null != bt) {
				if (bSetHide)
					bt.setVisibility(View.INVISIBLE);
				else
					bt.setVisibility(View.VISIBLE);
			}
		}

		/**
		 * 得到模板上导航栏的左侧按钮，一般为[返回]
		 * 
		 * @return 成功则返回Button对象，失败则返回null。
		 */
		public ImageView setButtonLeft(int leftBtId) {
			return (ImageView) findViewById(leftBtId);
		}

		/**
		 * 隐藏右侧按钮
		 */
		public void setHideButtonRight(int rightBtId, boolean bSetHide) {
			Button bt = setButtonRight(rightBtId);
			if (null != bt) {
				if (bSetHide)
					bt.setVisibility(View.INVISIBLE);
				else
					bt.setVisibility(View.VISIBLE);
			}
		}

		/**
		 * 得到模板上导航栏的右侧按钮
		 * 
		 * @return 成功则返回Button对象，失败则返回null。
		 */
		public Button setButtonRight(int rightBtId) {
			return (Button) findViewById(rightBtId);
		}

		/**
		 * 设置模板上导航栏中间的标题文字
		 * 
		 * @param titleText
		 * @return 修改成功返回true，失败返回false
		 */
		public boolean setBarTitleText(int txBarTitle, int titleStringId) {
			TextView tv = (TextView) findViewById(txBarTitle);
			if (null != tv) {
				tv.setText(titleStringId);
				TextPaint tp = tv.getPaint();
				tp.setFakeBoldText(true);
				return true;
			}
			return false;
		}

		/**
		 * 设置背景
		 * 
		 * @param resId
		 */
		public void setLayoutBackGround(int resId) {
			Resources res = getResources();
			Drawable drawable = res.getDrawable(resId);
			this.getWindow().setBackgroundDrawable(drawable);
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK && view != null) {
				view.onKeyDown(keyCode, event);
				if (!allowDestroy) {
					return false;
				}
			}
			return super.onKeyDown(keyCode, event);
		}

		public File getXmlFile(String filePath) {
			File file = new File(filePath);
			if (file.exists()) {
				return file;
			} else {
				return null;
			}
		}

		/**
		 * 获得数据文件路径
		 * 
		 * @return
		 */
		public String getSaveXmlPath() {
			if (SDAppConfig.isSDExist()) {
				return SDAppConfig.DEFAULT_SAVE_XML_PATH;
			} else {
				return getFilesDir().getAbsolutePath() + File.separator;
			}
		}
}
