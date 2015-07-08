package com.superdata.pm.service;


import com.superdata.pm.entity.DownloadInfo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/*
 * 方法：保存单个线程的进度，查询(查询单个线程的进度；查询指定路径的文件对应的所有进度)、更新、插入、删除
 */
public class DownloadService {

	private ContentResolver cr;
	private Uri url;
	
	public DownloadService(Context context){
		cr = context.getContentResolver();
		url = Uri.parse("content://com.superdata.pm.DownloadProvider/download");
	}
	
	/**
	 * 保存单个线程的进度
	 * @param info
	 */
	public void insert(DownloadInfo info){
		ContentValues values = new ContentValues();
		values.put("path", info.getPath());
		values.put("threadId", info.getThreadId());
		values.put("downloadLength", info.getDownloadLength());
		cr.insert(url, values);
	}
	
	/**
	 * 
	 * @param path	下载路径
	 * @param threadId	线程ID
	 * @return info 
	 */
	public DownloadInfo query(String path, int threadId){
		String selection = " path=? and threadId=?";
		String[] args = new String[]{path, threadId+""};
		Cursor c = cr.query(url, null, selection , args , null);
		DownloadInfo info = new DownloadInfo();
		if(c!= null){
			while(c.moveToNext()){
				info.setPath(c.getString(c.getColumnIndex("path")));
				info.setThreadId(c.getInt(c.getColumnIndex("threadId")));
				info.setDownloadLength(c.getInt(c.getColumnIndex("downloadLength")));
			}
			c.close();
		}
		return info;
	}
	
	/**
	 * 查询指定路径的文件对应的所有进度
	 */
	public int queryAllByPath(String path){
		String selection = " path = ?";
		String[] selectionArgs = new String[]{path};
		Cursor c = cr.query(url, new String[]{"downloadLength"}, selection , selectionArgs, null);
		int length = 0;
		if(c!=null){
			while (c.moveToNext()) {
				length += c.getInt(c.getColumnIndex("downloadLength"));
			}
			c.close();
		}
		return length;
	}
	
	public void update(DownloadInfo info){
		ContentValues values = new ContentValues();
		values.put("path", info.getPath());
		values.put("threadId", info.getThreadId());
		values.put("downloadLength", info.getDownloadLength());
		cr.update(url, values , "path=? and threadId=?", new String[]{info.getPath(), info.getThreadId()+""});
	}
	
	public void delete(String path){
		cr.delete(url, "path=?", new String[]{path});
	}
	
	/**
	 * 判断某文件是否下载过---有下载的记录
	 * @param path
	 * @return
	 */
	public boolean isExist(String path){
		String selection = " path = ?";
		String[] selectionArgs = new String[]{path};
		Cursor c = cr.query(url, new String[]{"downloadLength"}, selection , selectionArgs, null);
		if(c!=null){
			if(c.moveToNext()){
				c.close();
				return true;
			}
			c.close();
		}
		return false;
	}
		
}
