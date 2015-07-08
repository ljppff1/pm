package com.superdata.pm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.impl.io.ChunkedInputStream;

import android.os.Environment;
import android.util.Log;

/**
 * 多线程下载
 * @author Administrator
 *
 */
public class MutilDownloader {
	// 开启的线程的个数
//	public static final int THREAD_COUNT = 3;
	
	private static final String TAG = "FileDownloader......";

	public void download(String path, int fileSize, String savePath) {
//		path = "http://down.360safe.com/yunpan/360wangpan_setup.exe";
		// 连接服务器，获取一个文件，获取文件的长度，在本地创建一个大小跟服务器文件大小一样的临时文件
		URL url;
		try {
			url = new URL(path);
			Log.v("--path---", path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置网络请求超时时间
			conn.setConnectTimeout(5*1000);
			// 设置请求方式
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg,application/x-shockwave-flash, application/xaml+xml,application/vnd.ms-xpsdocument, application/x-ms-xbap,application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword");
			//conn.setRequestProperty("Accept", "application/pdf");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Referer", path);
			conn.setRequestProperty("Charset", "UTF-8");
			// 设置用户代理
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; "
					+ "MSIE 8.0; Windows NT 5.2;"
					+ " Trident/4.0; .NET CLR 1.1.4322;"
					+ ".NET CLR 2.0.50727; " + ".NET CLR 3.0.04506.30;"
					+ " .NET CLR 3.0.4506.2152; " + ".NET CLR 3.5.30729)");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			printResponseHeader(conn);
			int code = conn.getResponseCode();
			if (code == 200) {
				// 服务器返回的数据的长度，实际就是文件的长度
				int length = fileSize;
				System.out.println("----文件总长度----" + length);
				// 在客户端本地创建出来一个大小跟服务器端文件一样大小的临时文件
				
				RandomAccessFile raf = new RandomAccessFile(savePath, "rwd");
				raf.writeBoolean(true);
				// 指定创建的这个文件的长度
//				raf.setLength(length);
				// 关闭raf
				raf.close();
				
				new DownloadThread(path, savePath).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			// 假设是3个线程去下载资源
			// 平均每一个线程下载的文件的大小
			/*int blockSize = length / THREAD_COUNT;
			for (int threadId = 1; threadId <= THREAD_COUNT; threadId++) {
				// 计算每个线程下载的开始位置和结束位置
				int startIndex = (threadId - 1) * blockSize;
				int endIndex = threadId * blockSize - 1;
				if (threadId == THREAD_COUNT) {
					endIndex = length;
				}
				System.out.println("----threadId---" + threadId
						+ "--startIndex--" + startIndex + "--endIndex--"
						+ endIndex);
				// 开启每一个线程
				new DownloadThread(path, threadId, startIndex, endIndex)
						.start();
			}*/

	}

	/**
	 * 下载文件的子线程，每一个线程下载对应位置的文件
	 * 
	 * @author loonggg
	 * 
	 */
	public static class DownloadThread extends Thread {
//		private int threadId;
//		private int startIndex;
//		private int endIndex;
		private String path;
//		private File saveDir;
		private String savePath;

		/**
		 * @param path
		 *            下载文件在服务器上的路径
		 * @param threadId
		 *            线程id
		 * @param startIndex
		 *            线程下载的开始位置
		 * @param endIndex
		 *            线程下载的结束位置
		 */
		public DownloadThread(String path, String savePath) {
			this.path = path;
			this.savePath = savePath;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				// 重要：请求服务器下载部分的文件 指定文件的位置
				/*conn.setRequestProperty("Range", "bytes=" + startIndex + "-"
						+ endIndex);*/
				conn.setConnectTimeout(5000);
				
//				conn.connect();
				// 从服务器请求全部资源的状态码200 ok 如果从服务器请求部分资源的状态码206 ok
				int code = conn.getResponseCode();
				System.out.println("---code---" + code);
				InputStream is = conn.getInputStream();// 已经设置了请求的位置，返回的是当前位置对应的文件的输入流
//				BufferedInputStream bis = new BufferedInputStream(is); 
				RandomAccessFile raf = new RandomAccessFile(savePath, "rwd");
				// 随机写文件的时候从哪个位置开始写
//				raf.seek(startIndex);// 定位文件
				
				int len = 0;
				byte[] buffer = new byte[1024];
				int i = 0;
				while ((len = is.read(buffer)) != -1) {
					raf.write(buffer, 0, len);
					i+=len;
				}
				Log.v("-----iiii------", i+"");
				is.close();
				raf.close();
//				System.out.println("线程" + threadId + ":下载完毕了！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 获取Http响应头字段
	 * 
	 * @param http
	 *            HttpURLConnection对象
	 * @return 返回头字段的LinkedHashMap
	 */
	public static Map<String, String> getHttpResponseHeader(
			HttpURLConnection http) {
		Map<String, String> header = new LinkedHashMap<String, String>(); // 使用LinkedHashMap保证写入和遍历的时候的顺序相同，而且允许空值存在
		for (int i = 0;; i++) { // 此处为无限循环，因为不知道头字段的数量
			String fieldValue = http.getHeaderField(i); // getHeaderField(int
														// n)用于返回 第n个头字段的值。

			if (fieldValue == null)
				break; // 如果第i个字段没有值了，则表明头字段部分已经循环完毕，此处使用Break退出循环
			header.put(http.getHeaderFieldKey(i), fieldValue); // getHeaderFieldKey(int
														// n)用于返回
																// 第n个头字段的键。
		}
		return header;
	}
	
	
	/**
	 * 打印Http头字段
	 * 
	 * @param http
	 *            HttpURLConnection对象
	 */
	public static void printResponseHeader(HttpURLConnection http) {
		Map<String, String> header = getHttpResponseHeader(http);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey() != null ? entry.getKey() + ":" : "";
			print(key + entry.getValue()); // 答应键和值的组合
		}
	}

	
	/**
	 * 打印信息
	 * 
	 * @param msg
	 *            信息字符串
	 */
	private static void print(String msg) {
		Log.i(TAG, msg); // 使用LogCat的Information方式打印信息
		
	}

}
