package cn.eoe.android.libraries;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apkplug.app.PropertyInstance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

public class MyApplication implements PropertyInstance{
	public static String name="apkplug";
	public static boolean DEBUG = true;
	private   Context context;
	public MyApplication(Context context){
		this.context=context;
	}
	public String getProperty(String key) {
		// TODO Auto-generated method stub
		SharedPreferences sharedata = PreferenceManager.getDefaultSharedPreferences(this.context);
		String data = sharedata.getString(key, null);
		return data;
	}
	public void setProperty(String key, String v) {
		// TODO Auto-generated method stub
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.context); 
		Editor edit = settings.edit();
		edit.putString(key, v);
		edit.commit();
	}
	public String[] AutoInstall() {
		
		return null;
	}
	public String[] AutoStart() {
		//把BundleDemo1.apk从assets文件夹中移至应用安装目录中
		File f0=null;
		//插件托管服务应该提前启动
		try {
			InputStream in=context.getAssets().open("ActivityForResultDemo.apk");
			f0=new File(context.getFilesDir(),"ActivityForResultDemo.apk");
			if(!f0.exists())
			copy(in, f0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	       
		return new String[]{"file:"+f0.getAbsolutePath()};
	}
	private void copy(InputStream is, File outputFile)
	        throws IOException
	    {
	        OutputStream os = null;

	        try
	        {
	            os = new BufferedOutputStream(
	                new FileOutputStream(outputFile),4096);
	            byte[] b = new byte[4096];
	            int len = 0;
	            while ((len = is.read(b)) != -1)
	                os.write(b, 0, len);
	        }
	        finally
	        {
	            if (is != null) is.close();
	            if (os != null) os.close();
	        }
	    }
	@Override
	public boolean Debug() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
