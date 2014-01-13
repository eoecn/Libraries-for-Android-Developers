package cn.eoe.android.libraries;

import java.util.List;

import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;

import cn.eoe.app.adapter.ListBundleAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	private FrameworkInstance frame=null;
	private List<org.osgi.framework.Bundle> bundles=null;
	private ListView bundlelist =null;
	private ListBundleAdapter adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try
        {
        	//启动框架
			frame=FrameworkFactory.getInstance().start(null,MainActivity.this,new MyApplication(this.getApplicationContext()));
			//如果框架启动成功就把systembundle插件放到BundleContextFactory以备进行osgi通讯使用
			BundleContextFactory.getInstance().setBundleContext(frame.getSystemBundleContext());
        }
        catch (Exception ex)
        {
            System.err.println("Could not create : " + ex);
            //ex.printStackTrace();
            int nPid = android.os.Process.myPid();
			android.os.Process.killProcess(nPid);
        }
		
		 //已安装插件列表
        bundlelist=(ListView)findViewById(R.id.bundlelist);
		bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
		BundleContext context =BundleContextFactory.getInstance().getBundleContext();
		for(int i=0;i<context.getBundles().length;i++)
		{
			//获取已安装插件
				bundles.add(context.getBundles()[i]);        	        
		}
		adapter=new ListBundleAdapter(MainActivity.this,bundles);
		bundlelist.setAdapter(adapter);
		ListenerBundleEvent();
	}
	/**
	 * 监听插件安装事件，当有新插件安装或卸载时成功也更新一下
	 */
	public void ListenerBundleEvent(){
			BundleContextFactory.getInstance().getBundleContext()
			.addBundleListener(
					new SynchronousBundleListener(){
	
						public void bundleChanged(BundleEvent event) {
							//把插件列表清空
							bundles.clear();
							BundleContext context =BundleContextFactory.getInstance().getBundleContext();
							for(int i=0;i<context.getBundles().length;i++)
							{
									bundles.add(context.getBundles()[i]);        	        
	
							}
							adapter.notifyDataSetChanged();
						}
					
			});
	}
	
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() != KeyEvent.ACTION_UP) {
			AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
			alertbBuilder
					.setTitle("真的要退出？")
					.setMessage("你确定要退出？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									//MyProperty中调试模式设置为true调用shutdown将清理已安装插件缓存
									//以在下次启动时重新安装
									frame.shutdown();
									int nPid = android.os.Process.myPid();
									android.os.Process.killProcess(nPid);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).create();
			alertbBuilder.show();
			return true;
		} else {
			return super.dispatchKeyEvent(event);
		}
	} 
}
