package com.cn.activityresult.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RequestActivity extends Activity {
	public void b(){
		BundleContextFactory.getInstance();
	}
	private Button btn01;
	private EditText et01;
	private EditText et02;
	public static final String KEY_USER_ID="KEY_USER_ID";
	public static final String KEY_USER_PASSWORD="KEY_USER_PASSWORD";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity);
        btn01=(Button)findViewById(R.id.btn01);
        et01=(EditText)findViewById(R.id.et01);
        et02=(EditText)findViewById(R.id.et02);
        
        
        btn01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra(KEY_USER_ID, et01.getText().toString());
				intent.putExtra(KEY_USER_PASSWORD, et02.getText().toString());
		        setResult(RESULT_OK, intent);
		        finish();
			}
		});         
    }
}