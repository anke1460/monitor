package com.brochina.lvtong;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IpSetting extends Activity {
	
	private EditText ip_editor;
	private EditText video_editor;
	private Button save_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ip_setting);
		
		this.ip_editor = (EditText) this.findViewById(R.id.ip_editor);
		this.video_editor = (EditText) this.findViewById(R.id.video_address_editor);
		this.save_btn = (Button) this.findViewById(R.id.save_ip_setting);
		
	    SharedPreferences localSharedPreferences = getSharedPreferences("ip_setting", 0);
	    String str = localSharedPreferences.getString("server_ip", null);
	    if ((str == "") || (str == null))
	    {
	      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
	      localEditor.putString("server_ip", "61.184.136.162");
	      localEditor.putString("video_ip", "rtsp://admin:admin@192.168.1.90:554");
	      localEditor.commit();
	      this.ip_editor.setText("61.184.136.162");
	      this.video_editor.setText("rtsp://admin:admin@192.168.1.90:554");
	      return;
	    }
	    
	    this.ip_editor.setText(str);
	    this.video_editor.setText(localSharedPreferences.getString("video_ip", null));
	    
		this.save_btn.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						SharedPreferences.Editor localEditor = getSharedPreferences("data", 0).edit();  
				        localEditor.putString("server_ip", IpSetting.this.ip_editor.getText().toString());
				        localEditor.putString("video_ip", IpSetting.this.video_editor.getText().toString());
				        localEditor.commit();
//				        new AlertDialog.Builder(IpSetting.this).setTitle("信息").setMessage("保存成功！").setPositiveButton("确定", null).show();
				    	new AlertDialog.Builder(IpSetting.this).setTitle("信误")
						.setMessage("设置成功！").setPositiveButton("确定", null)
						.show();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
