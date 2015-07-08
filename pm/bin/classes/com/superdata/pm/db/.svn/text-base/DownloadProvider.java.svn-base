package com.superdata.pm.db;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DownloadProvider extends ContentProvider {
	
	private static final int DOWNLOAD_CODE = 0;
	private static final int ALL_CODE = 1;
	private static String authority = "com.superdata.pm.DownloadProvider";
	private SQLiteDatabase db;
	private static UriMatcher matcher = null;
	
	static{
		matcher = new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(authority , "download", DOWNLOAD_CODE);
		/**
		 * 传all时，表示查询某path路径对应的所有下载进度
		 */
		matcher.addURI(authority , "all", ALL_CODE);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int code = matcher.match(uri);
		switch (code) {
		case DOWNLOAD_CODE:
			int index = db.delete(MySqliteOpenHelper.DOWNLOAD_TABLE, selection, selectionArgs);
			return index;
		default:
			break;
		}
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		int code = matcher.match(uri);
		switch (code) {
		case DOWNLOAD_CODE:
			long l = db.insert(MySqliteOpenHelper.DOWNLOAD_TABLE, "_id", values);
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		//初始化数据库
		MySqliteOpenHelper helper = new MySqliteOpenHelper(getContext());
		db = helper.getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int code = matcher.match(uri);
		Cursor c = null;
		switch (code) {
		case DOWNLOAD_CODE:
			c = db.query(MySqliteOpenHelper.DOWNLOAD_TABLE, projection, selection, selectionArgs, null, null, null);
			break;
			
		case ALL_CODE:
			c = db.query(MySqliteOpenHelper.DOWNLOAD_TABLE, projection, selection, selectionArgs, null, null, null);
			break;

		default:
			break;
		}
		
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int code = matcher.match(uri);
		switch (code) {
		case DOWNLOAD_CODE:
			//ctrl+d
			int index = db.update(MySqliteOpenHelper.DOWNLOAD_TABLE, values, selection, selectionArgs);
			return index;
		default:
			break;
		}
		return 0;
	}

}
