package com.superdata.pm.view;

import com.superdata.pm.util.CheckGetUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 验证码实现View
 * @author kw
 *
 */
public class CheckView extends View implements CheckAction {

	private Context mContext;
	private int[] CheckNum = {0,0,0,0};
	Paint mTempPaint = new Paint();
	
	public CheckView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mTempPaint.setAntiAlias(true);//设置使用抗锯齿功能
		mTempPaint.setTextSize(18);
		mTempPaint.setStrokeWidth(3);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.CYAN);
		
		//绘制验证码
		final int height = getHeight();
		final int width = getWidth();
		int dx = 40;
		
		for(int i=0; i<4;i++){
			canvas.drawText(CheckNum[i]+"", dx, CheckGetUtils.getPosition(height), mTempPaint);
			dx += width/5;
		}
		//绘制线
		int[] line;
		for(int i = 0; i<ConmentConfig.LINE_NUM; i++){
			line = CheckGetUtils.getLine(height, width);
			canvas.drawLine(line[0], line[1], line[2], line[3], mTempPaint);
		}
		//绘制点
		int[] point;
		for(int i=0; i<ConmentConfig.POINT_NUM; i++){
			point = CheckGetUtils.getPoint(width, height);
			canvas.drawCircle(point[0], point[1], 1, mTempPaint);
		}
		
	}

	@Override
	public void setCheckNum(int[] checkNum) {
		// TODO Auto-generated method stub
		CheckNum = checkNum;
	}

	@Override
	public int[] getCheckNum() {
		// TODO Auto-generated method stub
		return CheckNum;
	}

	@Override
	public void updateCheckNum() {
		// TODO Auto-generated method stub
		invalidate();
	}

}
