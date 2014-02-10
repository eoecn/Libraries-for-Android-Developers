package cn.eoe.android.libraries.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * @brief 接收list为参数的Adapter的抽象类
 * @author haoxiqiang
 * 
 */
public abstract class ListAdapter extends BaseAdapter {

	protected ArrayList<?> list;
	protected Context mContext;

	public ListAdapter(Context _context, ArrayList<?> _list) {
		super();
		this.list = _list;
		this.mContext = _context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return initView(position, convertView);
	}

	public abstract View initView(int position, View convertView);
}
