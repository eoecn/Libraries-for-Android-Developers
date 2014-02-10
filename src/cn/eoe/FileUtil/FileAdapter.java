package cn.eoe.FileUtil;
import java.util.Vector;
import cn.eoe.FileUtil.FileItem;
import cn.eoe.android.libraries.R;
import cn.eoe.app.adapter.base.VListAdapter;
import cn.eoe.util.Observer.Observer;
import cn.eoe.util.Observer.Subject;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FileAdapter extends VListAdapter {
	public FileAdapter(Context c, Vector data) {
		super(c, data);
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView=mInflater.inflate(R.layout.fileview,null);
		}
		FileItem item=(FileItem)list.elementAt(position);
		holder=new ViewHolder();
		holder.fileName=(TextView)convertView.findViewById(R.id.fileName);
		holder.fileAddr=(TextView)convertView.findViewById(R.id.fileaddr);
		holder.fileName.setText(item.getName());
		holder.fileAddr.setText(item.getImg());
		convertView.setTag(holder);
		
		return convertView;
	}
	static class ViewHolder{
		TextView fileName;
		TextView fileAddr;
	}
}
