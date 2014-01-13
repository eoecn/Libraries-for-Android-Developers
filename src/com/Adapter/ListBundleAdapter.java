package com.Adapter;
import java.util.List;

import org.apkplug.Bundle.StartActivity;
import org.myutil2.ListAdapter.LListAdapter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import cn.eoe.android.libraries.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 显示已安装插件Adapter
 * @author 梁前武 
 * www.apkplug.com
 */
public class ListBundleAdapter extends LListAdapter<Bundle>{
	public ListBundleAdapter(Context c, List<Bundle> data) {
		super(c, data);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ListViewHolder viewHolder;
		final Bundle ab=list.get(position);
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.index_bundle_listview, null);
			viewHolder = new ListViewHolder();
			viewHolder.imageViewIcon = (ImageView)convertView.findViewById(R.id.image_item_1);
			viewHolder.appName = (TextView)convertView.findViewById(R.id.text_item_1);
			viewHolder.appinfo = (TextView)convertView.findViewById(R.id.text_item_2);
			viewHolder.download = (TextView)convertView.findViewById(R.id.text_item_4);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ListViewHolder)convertView.getTag();
		}
		viewHolder.appName.setText(ab.getName());
		viewHolder.appinfo.setText(ab.getVersion());
		viewHolder.download.setText("运行") ;
		LinearLayout linearlayout_out_2=(LinearLayout)convertView.findViewById(R.id.linearlayout_out_2);
		linearlayout_out_2.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
								if(ab.getState()!=ab.ACTIVE){
									//判断插件是否已启动
									try {
										ab.start();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								if(ab.getBundleActivity()!=null){
									//插件设置了启动 Activity
									//在宿主应用中我们需要通过 activity启动服务来启动插件的activit
									Intent i=new Intent();
									i.setClassName(mContext, ab.getBundleActivity());
									i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									mContext.startActivity(i);
								}else{
									Toast.makeText(mContext, "该插件没有配置BundleActivity",
										     Toast.LENGTH_SHORT).show();
								}
						
						}
				
			});
		
		convertView.setOnLongClickListener(
				new OnLongClickListener(){

					@Override
					public boolean onLongClick(View v) {
						AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(mContext);
						alertbBuilder
								.setMessage("")
								.setNegativeButton("卸载",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,
													int which) {
														//直接使用 Bundle.uninstall()卸载
														try {
															ab.uninstall();
														} catch (Exception e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
											
												dialog.cancel();
											}
										}).create();
						alertbBuilder.show();
						return false;
					}
			
		});
		
		convertView.setOnClickListener(
				new OnClickListener(){

					@Override
					public void onClick(View v) {
						//这里调用PullToRefreshWebView2Activity从网络 查询插件demo说明
						
						
					}
			
		});
		return convertView;
		}
	
	private final class ListViewHolder {
    	public ImageView imageViewIcon;
    	public TextView appName;
    	public TextView appinfo;
    	public TextView download;
    }
	
}
