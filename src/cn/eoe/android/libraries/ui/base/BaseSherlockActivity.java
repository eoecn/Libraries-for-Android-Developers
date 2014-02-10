/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.eoe.android.libraries.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;

/**
 * @brief 首页基类，公共逻辑处理
 * @author haoxq
 * 
 */
public class BaseSherlockActivity extends SherlockActivity {
	private boolean isExit=false;
	
	private CountDownTimer countDownTimer=new CountDownTimer(2000,500) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			isExit=true;
		}
		
		@Override
		public void onFinish() {
			isExit=false;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if(isExit){
				System.exit(0);
			}
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			countDownTimer.start();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onPause() {
		countDownTimer.cancel();
		super.onPause();
	}
	@Override
	protected void onResume() {
		countDownTimer.cancel();
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		countDownTimer.cancel();
	}
	
}
