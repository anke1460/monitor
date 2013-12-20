package com.brochina.lvtong;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.VideoView;

public class CheckedCar extends Activity {
	private VideoView videoView;
	private MediaController mediaController;
	private File videoFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checked_car);
		ListView list = (ListView) findViewById(R.id.listView1);

		SimpleAdapter adapter = new SimpleAdapter(this, getImageData(),
				R.layout.checked_car_list, new String[] { "title", "time",
						"img" }, new int[] { R.id.title, R.id.info, R.id.img });
		list.setAdapter(adapter);

		// 使用SimpleAdapter的 ViewBinder方法来更新图片
		// refrences
		// http://www.cnblogs.com/thu539/archive/2012/02/01/2334455.html
		adapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Drawable) {
					ImageView iv = (ImageView) view;
					iv.setImageDrawable((Drawable) data);
					return true;
				} else
					return false;
			}
		});
		// 播放视频
		videoView = (VideoView) this.findViewById(R.id.video_checked);
		mediaController = new MediaController(this);
		videoFile = new File("/mnt/sdcard/movie.3gp");

		this.findViewById(R.id.play_btn).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						String uri = "android.resource://" + getPackageName()
								+ "/" + R.raw.test;
						videoView.setVideoURI(Uri.parse(uri));
						// videoView.setVideoPath(videoFile.getAbsolutePath());
						videoView.setMediaController(mediaController);
						videoView.requestFocus();
						videoView.start();

					}
				});

	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < 100; i++) {
			map.put("title", "京A023" + i);
			map.put("info", "2013-09-01");
			map.put("img", R.drawable.car);
			list.add(map);
		}
		map.put("title", "京A02333");
		map.put("info", "2013-09-01");
		map.put("img", R.drawable.car);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "京A22333");
		map.put("info", "2013-09-01");
		map.put("img", R.drawable.car);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "京AM2333");
		map.put("info", "2013-09-01");
		map.put("img", R.drawable.car);
		list.add(map);

		return list;
	}

	public List<Map<String, Object>> getImageData() {
		String[] col = { "_id", "image" };
		List<Map<String, Object>> list = null;
		DatabaseHelper dbHelper = new DatabaseHelper(CheckedCar.this,
				"lvtongdb", null, 1);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select image, datetime(created_at,'localtime')  AS created_at from photos", null);
		// Cursor cursor = db.query("photos", col, null, null, null, null,
		// null);// 数据的查询
		HashMap<String, Object> bindData = null;
		list = new ArrayList<Map<String, Object>>();
		for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
			bindData = new HashMap<String, Object>();
			/** 得到Bitmap字节数据 **/
			byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));

			// Bitmap bmpout = BitmapFactory.decodeByteArray(image, 0,
			// image.length);

			ByteArrayInputStream stream = new ByteArrayInputStream(
					cursor.getBlob(cursor.getColumnIndex("image")));

			Log.i("log", image.length + "____");
			bindData.put("title", "京AM2333");
			bindData.put("time",
					cursor.getString(cursor.getColumnIndex("created_at")));
			bindData.put("img", Drawable.createFromStream(stream, "image"));
			list.add(bindData);
		}
		cursor.close();
		db.close();
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.checked_car, menu);
		return true;
	}

}
