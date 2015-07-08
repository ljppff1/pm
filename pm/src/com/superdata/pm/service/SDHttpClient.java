package com.superdata.pm.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.superdata.pm.common.exception.ServerException;

/**
 * 
* Title: SDHttpClient
* Description:
* Company: SD 
* @author luolei
* @date 2014年6月6日
 */
public class SDHttpClient {

	private DefaultHttpClient httpClient = new DefaultHttpClient();
	public static String JSESSIONID = null;

	/**
	 * Post
	 * 
	 * @param url
	 * @param params
	 * @return body
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String post(String url, List<NameValuePair> params)
			throws UnsupportedEncodingException, ClientProtocolException,
			ParseException, HttpHostConnectException, ConnectException,
			ConnectTimeoutException, IOException, ServerException {
	   System.out.println("url---------------:"+url);
		String body = null;
		HttpPost httppost = new HttpPost(url); 
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 15 * 2000);
		HttpConnectionParams.setSoTimeout(httpParameters, 15 * 1000);
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse httpresponse = httpClient.execute(httppost); 
	
		if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			if (entity != null) {
				entity.consumeContent();
			}
			
		} else {
			throw new ServerException("ServerException");
		}
		return body;
	}

	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ClientProtocolException
	 * @throws ParseException
	 * @throws HttpHostConnectException
	 * @throws ConnectException
	 * @throws ConnectTimeoutException
	 * @throws IOException
	 * @throws ServerException
	 */
	public String post_session(String url, List<NameValuePair> params)
			throws UnsupportedEncodingException, ClientProtocolException,
			ParseException, HttpHostConnectException, ConnectException,
			ConnectTimeoutException, IOException, ServerException {
		String body = "none";
		HttpPost httppost = new HttpPost(url); 
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 80 * 1000);
		HttpConnectionParams.setSoTimeout(httpParameters, 80 * 1000);
		httpClient = new DefaultHttpClient(httpParameters);
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8")); 
		if (null != JSESSIONID) {
			httppost.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
		}
		HttpResponse httpresponse = httpClient.execute(httppost); 
		if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			CookieStore mCookie = httpClient.getCookieStore();
			List<Cookie> cookies = mCookie.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				if ("JSESSIONID".equals(cookies.get(i).getName())) {
					JSESSIONID = cookies.get(i).getValue();
					break;
				}
			}
	
			if (entity != null) {
				entity.consumeContent();
			}

		} else {
			throw new ServerException("ServerException");
		}
		return body;
	}
}
