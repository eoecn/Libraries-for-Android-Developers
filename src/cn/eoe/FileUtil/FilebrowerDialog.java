package cn.eoe.FileUtil;

import java.io.File;
import java.util.Hashtable;
import java.util.Vector;
import cn.eoe.FileUtil.filter.*;
import cn.eoe.android.libraries.R;
import cn.eoe.util.Observer.AbstractSubject;
import cn.eoe.util.Observer.Observer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.*;
import android.widget.Toast;
/**
 * 文件浏览器,以Dialog形式展示文件系统中的文件目录
 * setClickfile(ClickFile clickfile) 用户点击回调接口
 * show()--显示Dialog
 * @author 梁前武 QQ 1587790525
 * www.apkplug.com
 */
public class FilebrowerDialog {
	public static String mpath=SDFileHandle.getSDPath();
	private AbFileFilter AbFileFilter=null;
	FileHandle fh=null;
	Vector flist=null;
    public AbFileFilter getAbFileFilter() {
		return AbFileFilter;
	}
	public void setAbFileFilter(AbFileFilter abFileFilter) {
		AbFileFilter = abFileFilter;
	}

	private ClickFile clickfile=null;
    public ClickFile getClickfile() {
		return clickfile;
	}
	public void setClickfile(ClickFile clickfile) {
		this.clickfile = clickfile;
	}
	/**
      * 选中文件
      */
	public static byte FileItemClick=0;
	 /**
	  * 单击 目录列表
	  */

	public static byte DirItemClick=1;

	private  String[] filePath=null;
	private  byte ClickType=0;
    private AlertDialog dlg=null;
    /**
     * 
     * @param mcontext     
     * @param path           其实文件夹路径
     * @param abFileFilter   文件过滤器          可设置只显示指定后缀的文件或目录
     */
	public FilebrowerDialog(final Context mcontext,String path,AbFileFilter abFileFilter){
    	this.setAbFileFilter(abFileFilter);
    	fh=new FileHandle();
		flist=new Vector();
    	LayoutInflater factory = LayoutInflater.from(mcontext);
    
    	
		final View textEntryView = factory.inflate(
				R.layout.fileshow, null);
		dlg = new AlertDialog.Builder(mcontext)
		.setTitle("文件浏览")
		.setView(textEntryView).setNeutralButton("退出",
				new DialogInterface.OnClickListener() {

				
					public void onClick(DialogInterface dialog,
							int which) {
						/* 右键事件 */
					}
				}).create();
		ListView FileList = (ListView) textEntryView.findViewById(R.id.filelistView);
		final FileAdapter Adapter=new FileAdapter(mcontext,flist);
		fh.start(flist, path);
		FileList.setAdapter(Adapter);
		FileList.setOnItemClickListener( new OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
                // Cancel discovery because it's costly and we're about to connect     
            	FileItem item=(FileItem)Adapter.getList().elementAt(arg2);
            	if(item.getF()==null)
            		return ;
            	if(item.getF().isDirectory()){
            		fh.loadFiles(flist, item.getF(),getAbFileFilter());
            		Adapter.notifyDataSetChanged();
            	}else{
            		Toast toast=Toast.makeText(mcontext,"请长按选项进行操作", Toast.LENGTH_SHORT); 
   		         	toast.show();
            		
            	}
            	
            }
        });	
		FileList.setLongClickable(true);
		FileList.setOnItemLongClickListener(new OnItemLongClickListener(){

			
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				FileItem item=(FileItem)Adapter.getList().elementAt(arg2);
            	if(item.getF()==null)
            		return true;
				ClickType=FileItemClick;
        		filePath=new String[]{item.getF().getAbsolutePath()};
        		if(clickfile!=null)
        			clickfile.ClickFile(FilebrowerDialog.this,0,filePath);
				return true;
			}});
    }
    public void show(){
	    			if(dlg.isShowing())
	    	    		return ;
	    	    	dlg.show();	
	    }
    public void close(){
	    
	    	if(dlg.isShowing())
	    		dlg.cancel();
	}

	public interface ClickFile{
		public void ClickFile(FilebrowerDialog fd,int ClickType,String[] filepath);
	} 

}
