package cn.eoe.android.libraries.adapter;

import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class VListAdapter extends BaseAdapter{
	protected Context mContext=null;
	protected LayoutInflater mInflater=null;
	protected Vector list=null;
	public VListAdapter(Context c,Vector data){
    	 this.mContext=c;
    	 mInflater=LayoutInflater.from(c);
    	 list=data;
    	 
     }
	public Vector getList() {
		return list;
	}
	public void setList(Vector list) {
		this.list = list;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if(list!=null)
		return list.elementAt(arg0);
		return arg0;
	}

	
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}


}
