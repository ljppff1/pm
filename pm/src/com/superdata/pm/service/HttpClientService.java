package com.superdata.pm.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpClientService {

	private HttpParams httpParams;//http参数设置对象
    private HttpClient httpClient;//http请求对象
    private static HttpClientService httpClientService;
    
    /**
     * @author 刘定富
     * @param 发送get请求
     * @param parans Map key value
     * @return
     */
	public  String doGet(String url, Map params) {  
	        /* 建立HTTPGet对象 */  	  
	        String paramStr = "";  
	        Iterator iter = params.entrySet().iterator();  
	        while (iter.hasNext()) {  
	            Map.Entry entry = (Map.Entry) iter.next();  
	            Object key = entry.getKey();  
	            Object val = entry.getValue();  
	            paramStr += paramStr = "&" + key + "=" + val;  
	        }  
	        if (!paramStr.equals("")) {  
	            paramStr = paramStr.replaceFirst("&", "?");  
	            url += paramStr;  
	        }  
	        HttpGet httpRequest = new HttpGet(url);  
	        String strResult = "doGetError";  
	        getHttpClient();//创建httpclient对象
	        try {  
	            /* 发送请求并等待响应 */  
	            HttpResponse httpResponse = httpClient.execute(httpRequest);  
	            /* 若状态码为200 ok */  
	            if (httpResponse.getStatusLine().getStatusCode() == 200) {  
	                /* 读返回数据 */  
	                strResult = EntityUtils.toString(httpResponse.getEntity());  
	            } else {  
	                strResult = "Error Response: "  
	                        + httpResponse.getStatusLine().toString();  
	            }  
	        } catch (ClientProtocolException e) {  
	            strResult = e.getMessage().toString();  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            strResult = e.getMessage().toString();  
	            e.printStackTrace();  
	        } catch (Exception e) {  
	            strResult = e.getMessage().toString();  
	            e.printStackTrace();  
	        }  
	        Log.v("strResult", strResult);  
	        return strResult;  
	    }  
	/**
	 * @author 刘定富
	 * @param 发送post请求
	 * @param params List<NameValuePair> params
	 * @return String
	 */
	 public String doPost(String url, List<NameValuePair> params) {  
	        /* 建立HTTPPost对象 */  
	        HttpPost httpRequest = new HttpPost(url);  
	        String strResult = "doPostError";  
	        getHttpClient();//创建httpclient对象
	        try {  
	            /* 添加请求参数到请求对象 */  
	            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
	            /* 发送请求并等待响应 */  
	            HttpResponse httpResponse = httpClient.execute(httpRequest);  
	            /* 若状态码为200 ok */  
	            if (httpResponse.getStatusLine().getStatusCode() == 200) {  
	                /* 读返回数据 */  
	                strResult = EntityUtils.toString(httpResponse.getEntity());  
	            } else {  
	                strResult = "Error Response: "  
	                        + httpResponse.getStatusLine().toString();  
	            }  
	        } catch (ClientProtocolException e) {  
	            strResult = e.getMessage().toString();  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            strResult = e.getMessage().toString();  
	            e.printStackTrace();  
	        } catch (Exception e) {  
	            strResult = e.getMessage().toString();  
	            e.printStackTrace();  
	        }  
	        Log.v("strResult", strResult);  
	        return strResult;  
	    } 
	/**
	 * 获得httpclient对象
	 * @return
	 */
	 public HttpClient getHttpClient() {  
	        // 创建 HTTPParams以用来设置HTTP 参数（这一部分不是必需的）
	        this.httpParams = new BasicHttpParams();  
	        // 设置连接超时和socket超时， 以及socket缓存大小  
	        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);  
	        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);  
	        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);  
	        // 设置重定向，缺省为true 
	       // HttpClientParams.setRedirecting(httpParams, true);  
	        // 设置 user agent
	      // String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";  
	       // HttpProtocolParams.setUserAgent(httpParams, userAgent);  
	        // 创建一个HttpClient实例
	        // 注意HttpClient httpClient = new HttpClient();  是Commons HttpClient
	        // 中的用法， 在Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient
	        httpClient = new DefaultHttpClient(httpParams);  
	        return httpClient;  
	    }
	 
	 public static HttpClientService getHttpClientService(){
		 if(httpClientService == null){
			 httpClientService = new HttpClientService();
		 }
		 return httpClientService;
	 }
	
}
