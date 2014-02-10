package cn.eoe.util.preferencesFactory;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * 一个简单的SharedPreferences 保存封装
 * @author 梁前武
 *
 * QQ:1587790525
 */
public class SharedPreferencesFactory {
	private static SharedPreferencesFactory _instance=null;
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	
	public SharedPreferences.Editor getEditor() {
		return editor;
	}
	
	private SharedPreferencesFactory(Context c){
		sp = c.getSharedPreferences("MiLaucherconfig.ini",0);
		editor=sp.edit();
	}
		 //单例静态工厂方法,同步防止多线程环境同时执行 
		   synchronized public static SharedPreferencesFactory getInstance(Context c){
		   if(_instance==null){
		   _instance=new SharedPreferencesFactory(c);
		   }
		   return _instance;
		   }
		public SharedPreferences getSp() {
	
			return sp;
		}	  
	public boolean getBoolean(String key, boolean value){
		return sp.getBoolean(key,value);
	}
	public int getInt(String key, int value){
		return sp.getInt(key,value);
	}
	public long getLong(String key, long value){
		return sp.getLong(key,value);
	}
	public String getString(String key, String value){
		return sp.getString(key,value);
	}
	public float getFloat(String key, float value){
		return sp.getFloat(key,value);
	}
	public void putBoolean(String key, boolean value){
		editor.putBoolean(key, value);
		editor.commit();
	}
	public void putLong(String key, long value){
		editor.putLong(key, value);
		editor.commit();
	}
	public void putInt(String key, int value){
		editor.putInt(key, value);
		editor.commit();
	}
	public void putFloat(String key, float value){
		editor.putFloat(key, value);
		editor.commit();
	}
	public void putString(String key, String value){
		editor.putString(key, value);
		editor.commit();
	}
}
