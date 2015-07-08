package com.superdata.pm.util;

import java.util.HashMap;
import java.util.Map;

import com.superdata.pm.service.HttpClientService;

/**
 * 管理常量和方法
 * @author 刘定富
 *
 */
public class BaseUtil {
/**
 * 基础URL	
 */
private   final String BASEHTTPURL="http://192.168.0.113:8080/common/userInterface/";//基础URL
/**
 * 登录URL
 */
private   final String LOGURL=BASEHTTPURL+"index.do";
/**
 * 构造http访问对象
 */
private   HttpClientService  httpClientService=HttpClientService.getHttpClientService();//构造http访问对象

/**
 * 验收报告    审核通过
 */
public final static String CHECKREPORT_PASS = "通过";

/**
 * 验收报告    审核不通过：重新整改
 */
public final static String CHECKREPORT_NOTPASS_RECTIFICATION = "重新整改";


/**
 * 
* @Title: loginUser
* @Description: TODO(登陆方法)
* @param @param name 用户名
* @param @param pwd  密码
* @param @return    设定文件
* @return String    返回类型
* @throws
 */
  public  String loginUser(String name,String pwd){
	  Map<String,String > map =new HashMap<String, String>();
	 // map.put("name", name);
	 // map.put("pwd", pwd);  
	  return httpClientService.doGet(LOGURL, map);
   }  
}
