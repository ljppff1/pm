package com.superdata.pm.util;

/**
 * 验证码的相关工具类
 * @author kw
 *
 */
public class CheckGetUtils {

	public static int[] getCheckNum(){
		int[] tempCheckNum = {0,0,0,0};
		for(int i=0; i<4; i++){
			tempCheckNum[i] = (int) (Math.random()*10);
		}
		
		return tempCheckNum;
	}
	
	public static int[] getLine(int height,int width){
		int [] tempCheckNum = {0,0,0,0};
		for(int i=0; i<4;i+=2){
			tempCheckNum[i] = (int) (Math.random()*width);
			tempCheckNum[i+1] = (int) (Math.random()*height);
		}
		
		return tempCheckNum;
	}
	
	public static int[] getPoint(int width, int height){
		int [] tempCheckNum = {0,0,0,0};
		tempCheckNum[0] = (int) (Math.random() * width);
		tempCheckNum[1] = (int) (Math.random() * height);
		return tempCheckNum;
	}
	
	//验证是否正确
	public static boolean checkNum(String userCheck, int[] checkNum){
		if(userCheck.length() != 4){
			return false;
		}
		String checkString = "";
		for(int i=0; i<4; i++){
			checkString += checkNum[i];
		}
		if(userCheck.equals(checkString)){
			return true;
		}else{
			return false;
		}
		
	}
	
	//获取验证码的绘制位置
	public static int getPosition(int height){
		int tempPosition = (int) (Math.random()*height);
		if(tempPosition < 15){
			tempPosition += 15;
		}
		return tempPosition;
		
	}
	
}
