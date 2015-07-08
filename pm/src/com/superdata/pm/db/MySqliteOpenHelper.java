package com.superdata.pm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {
	
	private static final String name  = "download_db.db";
	public static final String DOWNLOAD_TABLE = "download";

	public MySqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public MySqliteOpenHelper(Context context) {
		super(context, name, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//_id, path, threadId, downloadLength
		String sql = "CREATE TABLE download(" +
				"_id integer primary key autoincrement," +
				"path TEXT," +
				"threadId integer," +
				"downloadLength integer)";
		db.execSQL(sql );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
