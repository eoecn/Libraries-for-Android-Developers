package com.cn.activityresult.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainAcitvity extends Activity {
	public void b(){
		BundleContextFactory.getInstance();
	}
	private Button btn01,btn02;
	private TextView tv01;
	public static final int REQUSET = 1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//requestCode标示请求的标示   resultCode表示有数据
		if (requestCode == MainAcitvity.REQUSET && resultCode == RESULT_OK) {
			String str = "账号"
					+ data.getStringExtra(RequestActivity.KEY_USER_ID) + "\n"
					+ "密码"
					+ data.getStringExtra(RequestActivity.KEY_USER_PASSWORD);
			tv01.setText(str);
		}
		Toast.makeText(
				this,
				"requestCode=" + requestCode + ":" + "resultCode=" + resultCode,
				Toast.LENGTH_LONG).show();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn01 = (Button) findViewById(R.id.btn01);
		tv01 = (TextView) findViewById(R.id.tv01);
		btn01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainAcitvity.this,
						RequestActivity.class);
				//发送意图标示为REQUSET=1
				startActivityForResult(intent, REQUSET);
			}
		});
		btn02 = (Button) findViewById(R.id.btn02);
		btn02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainAcitvity.this,
						PicCutDemoActivity.class);
				//发送意图标示为REQUSET=1
				startActivity(intent);
			}
		});
	}
}