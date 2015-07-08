package com.superdata.pm.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 页面跳转的工具类
 * @author kw
 *
 */
public class IntentUtils {

	public static void gotoActivity(Context context, Class<?> cls){
		Intent intent = new Intent(context,cls);
		context.startActivity(intent);
	}
	
	
}
