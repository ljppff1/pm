package com.superdata.pm.view;

import com.suda.pm.ui.R;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


public class CustomDialog {
	/**
	 * @param context
	 * @param resId:自定义布局
	 * @param content:提示语
	 * @param duration:返回时间
	 */
		public static void myToastShow(Context context, String content,String content2, int duration){
			
			Toast toast = new Toast(context);
			toast.setDuration(duration);
			toast.setGravity(Gravity.CENTER, 0 , 0);
			
			LinearLayout toastLayout = new LinearLayout(context);
			
			toastLayout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			toastLayout.setOrientation(LinearLayout.VERTICAL);
			toastLayout.setGravity(Gravity.CENTER);
			
			
			TextView textView = new TextView(context);
			textView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			textView.setBackgroundResource(R.drawable.dialog_head);
			textView.setText(content);
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(16);
			textView.setGravity(Gravity.CENTER);
			//textView.setMaxWidth(200);
			//textView.setTextScaleX(1.1f);
			//textView.setMaxLines(1);
			//textView.setEllipsize(TruncateAt.END);
			
			toastLayout.addView(textView);
			//----------------------------------------
			TextView textView2 = new TextView(context);
			textView2.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			textView2.setText(content2);
			textView2.setTextColor(Color.BLACK);
			textView2.setTextSize(14);
			textView2.setGravity(Gravity.CENTER);
			//textView2.setMaxWidth(200);
			//textView2.setTextScaleX(1.1f);
			
			//textView.setEllipsize(TruncateAt.END);
			textView2.setBackgroundResource(R.drawable.dialog_test_bj);
			toastLayout.addView(textView2);
			toast.setView(toastLayout);
			toast.show();
			
		}
		
		/**
		 * 
		 * @param context
		 * @param resId:自定义布局
		 * @param contentId:提示语（strings.xml）
		 * @param duration返回时间
		 */
		public static void myToastShow(Context context, int resId, int contentId, int duration){
			Toast toast = new Toast(context);
			toast.setDuration(duration);
			toast.setGravity(Gravity.CENTER, 0 , 0);
			
			LinearLayout toastLayout = new LinearLayout(context);
			
			toastLayout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			toastLayout.setOrientation(LinearLayout.HORIZONTAL);
			toastLayout.setGravity(Gravity.CENTER);
			toastLayout.setBackgroundResource(resId);
			
			TextView textView = new TextView(context);
			textView.setText(contentId);
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(14);
			textView.setGravity(Gravity.CENTER);
			textView.setMaxWidth(100);
			textView.setTextScaleX(1.1f);
			textView.setMaxLines(2);
			textView.setEllipsize(TruncateAt.END);
			toastLayout.addView(textView);
			
			toast.setView(toastLayout);
			toast.show();
		}
}
