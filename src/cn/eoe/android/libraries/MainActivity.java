package cn.eoe.android.libraries;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Class mActivityClass;
	private Object instance;
	private Object mActivityInstance;
	Class localClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Bundle paramBundle = new Bundle();
			paramBundle.putBoolean("KEY_START_FROM_OTHER_ACTIVITY", true);
			paramBundle.putString("str", "PlugActivity");

			String path = Environment.getExternalStorageDirectory()
					+ File.separator;
			String filename = "HeyIce.apk";

			String optimizedDirectory = path + File.separator + "dex_temp";
			// 核心是这里， 通过getDir来获取一个File对象，然后在获取到getAbsolutePath,
			// 传递给DexClassLoader 即可
			File file = getDir("dex", 0);
//			DexClassLoader classLoader = new DexClassLoader(path + filename,
//					file.getAbsolutePath(), null, getClassLoader());
			String dexpath =  path + filename ; //"/sdcard/HeyIce.apk";
			String dexoutputpath =  file.getAbsolutePath();// "/mnt/sdcard/";
			LoadAPK(paramBundle, dexpath, dexoutputpath);

		}
	};

	public void LoadAPK(Bundle paramBundle, String dexpath, String dexoutputpath) {
		ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
		DexClassLoader localDexClassLoader = new DexClassLoader(dexpath,
				dexoutputpath, null, localClassLoader);
		try {
			PackageInfo plocalObject = getPackageManager()
					.getPackageArchiveInfo(dexpath, 1);

			if ((plocalObject.activities != null)
					&& (plocalObject.activities.length > 0)) {
				String activityname = plocalObject.activities[0].name;
				Log.d("sys", "activityname = " + activityname);

				localClass = localDexClassLoader.loadClass(activityname);// 结果："com.example.fragmentproject.FristActivity"
				mActivityClass = localClass;
				Constructor localConstructor = localClass
						.getConstructor(new Class[] {});
				instance = localConstructor.newInstance(new Object[] {});
				Log.d("sys", "instance = " + instance);
				mActivityInstance = instance;

//				Method des = localClass.getMethod("test");
//				des.invoke(instance);

//				Method localMethodSetActivity = localClass.getDeclaredMethod(
//						"setActivity", new Class[] { Activity.class });
//				localMethodSetActivity.setAccessible(true);
//				localMethodSetActivity.invoke(instance, new Object[] { this });

				Method methodonCreate = localClass.getDeclaredMethod(
						"onCreate", new Class[] { Bundle.class });
				methodonCreate.setAccessible(true);
				methodonCreate.invoke(instance, paramBundle);
			}
			return;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
