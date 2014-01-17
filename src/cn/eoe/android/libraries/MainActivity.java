package cn.eoe.android.libraries;

import java.io.File;
import java.util.List;

import org.apkplug.Bundle.BundleControl;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.SynchronousBundleListener;
import cn.eoe.FileUtil.FilebrowerDialog;
import cn.eoe.FileUtil.FilebrowerDialog.ClickFile;
import cn.eoe.FileUtil.filter.apkFilter;
import cn.eoe.FileUtil.filter.isFilesFilter;
import cn.eoe.app.adapter.ListBundleAdapter;
import cn.eoe.util.preferencesFactory.SharedPreferencesFactory;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 0.0.1 版本新增安装本地插件接口
 * MainActivity.install(String path,installCallback callback)
 * @author 梁前武 QQ 1587790525
 * www.apkplug.com
 */
public class MainActivity extends Activity {
	private FrameworkInstance frame=null;
	private List<org.osgi.framework.Bundle> bundles=null;
	private ListView bundlelist =null;
	private ListBundleAdapter adapter=null;
	private TextView info=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try
        {
        	//启动框架
			//文档见 http://www.apkplug.com/javadoc/Maindoc1.4.6/
			//org.apkplug.app 
			//     接口 FrameworkInstance
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
		info =(TextView)this.findViewById(R.id.info);
		
		initInstallBundle();
		
		initBundleList();
		//监听插件安装状态已动态更新列表
		ListenerBundleEvent();
	}
	/**
	 * 安装插件回调函数
	 */
	class myinstallCallback implements installCallback{

		@Override
		public void callback(int arg0, org.osgi.framework.Bundle arg1) {
			if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
				info.setText("插件安装成功 ：\n"+showBundle(arg1));
				return;
			}
			else{
				String info1="安装状态:"+arg0;
				info.setText("插件安装失败 ："+this.stutasToStr(arg0));
			}
			
		}
		/**
		 * 信息由 http://www.apkplug.com/javadoc/bundledoc1.5.3/
		 * org.apkplug.Bundle 
		 *      接口 installCallback 提供
		 * @param stutas
		 * @return
		 */
		private String stutasToStr(int stutas){
			if(stutas==installCallback.stutas){
				return "缺少SymbolicName";
			}else if(stutas==installCallback.stutas1){
				return "已是最新版本";
			}else if(stutas==installCallback.stutas2){
				return "版本号不正确";
			}else if(stutas==installCallback.stutas3){
				return " 版本相等";
			}else if(stutas==installCallback.stutas4){
				return "无法获取正确的证书";
			}else if(stutas==installCallback.stutas5){
				return "更新成功";
			}else if(stutas==installCallback.stutas6){
				return "证书不一致";
			}else if(stutas==installCallback.stutas7){
				return "安装成功";
			}
			return "状态信息不正确";
		}
	}
	/**
	 * 用户长按apk文件以后回调事件
	 */
	class clickfile implements ClickFile{

		@Override
		public void ClickFile(FilebrowerDialog fd,int ClickType, String[] filepath) {
			//用户长按文件后的回掉函数
			fd.close();
			//记录当前文件路径 下次直接显示这个文件夹
			SharedPreferencesFactory.
			getInstance(MainActivity.this).putString("sd",new File(filepath[0]).getParent());
			try {
				//调用osgi插件安装服务安装插件
				install("file:"+filepath[0],new myinstallCallback());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	/**
	 * 初始化安装本地插件apk的相关UI和事件
	 */
	public void initInstallBundle(){
		
        Button install=(Button)this.findViewById(R.id.installlocal);
        install.setOnClickListener(
        		new OnClickListener(){
					@Override
					public void onClick(View v) {
						//FilebrowerDialog 是一个选择本地文件的对话框 
						//初始化文件显示对话框
						final FilebrowerDialog fildia=new FilebrowerDialog(
								MainActivity.this,
								SharedPreferencesFactory.
								getInstance(MainActivity.this).getString("sd",
										Environment.getExternalStorageDirectory().getAbsolutePath())  //上次读取的文件夹路径
								,new apkFilter(new isFilesFilter(null)));  //过滤除.apk外的文件
						//设置用户选择文件监听事件（长按）
						fildia.setClickfile(new clickfile());
						//显示对话框
						fildia.show();
					}
        	
        });
	}
	/**
	 * 初始化显示已安装插件的UI
	 */
	public void initBundleList(){
		 //已安装插件列表
        bundlelist=(ListView)findViewById(R.id.bundlelist);
		bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
		BundleContext context =frame.getSystemBundleContext();
		for(int i=0;i<context.getBundles().length;i++)
		{
			//获取已安装插件
				bundles.add(context.getBundles()[i]);        	        
		}
		adapter=new ListBundleAdapter(MainActivity.this,bundles);
		bundlelist.setAdapter(adapter);
	}
	
	/**
	  * 安装本地插件服务调用
	  * 详细接口参见 http://www.apkplug.com/javadoc/bundledoc1.5.3/
	  * org.apkplug.Bundle 
      *      接口 BundleControl
	  * @param path
	  * @param callback   安装插件的回掉函数
	  * @throws Exception
	  */
	 public void install(String path,installCallback callback) throws Exception{
		 System.out.println("安装 :"+path);
			BundleContext mcontext=BundleContextFactory.getInstance().getBundleContext();
			ServiceReference reference=mcontext.getServiceReference(BundleControl.class.getName());
	    	if(null!=reference){
	    		BundleControl service=(BundleControl) mcontext.getService(reference);
	    		if(service!=null){
	    			service.install(mcontext, path,callback);
	    		}
	    	mcontext.ungetService(reference);
	    	}
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
 	
 	public String showBundle(org.osgi.framework.Bundle b){
		StringBuffer sb=new StringBuffer();
		sb.append("\n插件名称:"+b.getName());
		sb.append("\n插件应用名称:"+b.getSymbolicName());
		sb.append("\n插件版本:"+b.getVersion());
		sb.append("\n插件ID:"+b.getBundleId());
		sb.append("\n插件当前状态:"+b.getState());
		sb.append("\n插件启动Activity:"+b.getBundleActivity());
		return sb.toString();
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
