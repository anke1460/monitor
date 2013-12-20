package com.brochina.lvtong;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private final static int VERSION = 1;
	private final static String DATABASE_NAME = "lvtongdb";

	public DatabaseHelper(Context context) {
		this(context, DATABASE_NAME, null, VERSION);
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE photos ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "created_at DATETIME DEFAULT CURRENT_TIMESTAMP,"
				+ "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP, image BLOB)"; // 保存为binary格式

		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVerstion) {
		// TODO Auto-generated method stub
		if (newVerstion > oldVersion) {
			db.execSQL("DROP TABLE IF EXISTS photos");
		} else {
			return;
		}
		onCreate(db);
	}

}
