package cn.eoe.android.libraries.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.eoe.android.libraries.R;
import cn.eoe.android.libraries.entity.LibProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JaveZh
 * Date: 14-1-16
 * Time: 上午1:22
 * To change this template use File | Settings | File Templates.
 */
public class MainFragmentAdapter extends BaseAdapter {
    private List<LibProduct> productList = new ArrayList<LibProduct>();
    private Activity mActivity;

    public MainFragmentAdapter() {
    }

    public MainFragmentAdapter(Activity activity, ArrayList<LibProduct> lists) {
        if (activity == null) {
            return;
        }
        mActivity = activity;
        if (lists == null) {
            Toast.makeText(mActivity, "无值", Toast.LENGTH_SHORT).show();
            return;
        }
        productList = lists;
    }


    public void appendToList(ArrayList<LibProduct> lists) {
        if (lists == null) {
            return;
        }
        productList.addAll(lists);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LibProduct product = productList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mActivity, R.layout.main_list_item, null);
            holder.productNameTxt = (TextView) convertView.findViewById(R.id.tv_lib_name);
            holder.productSubmitterTxt = (TextView) convertView.findViewById(R.id.tv_lib_submitter);
            holder.productBriefTxt = (TextView) convertView.findViewById(R.id.tv_lib_brief);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.productNameTxt.setText(product.getTitle());
        holder.productSubmitterTxt.setText(product.getL_package());
        holder.productBriefTxt.setText(product.getLede());
        return convertView;
    }

    class ViewHolder {
        public TextView productNameTxt;
        public TextView productSubmitterTxt;
        public TextView productBriefTxt;
    }

}