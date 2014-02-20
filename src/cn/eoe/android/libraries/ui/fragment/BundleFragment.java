package cn.eoe.android.libraries.ui.fragment;

import java.util.ArrayList;

import org.apkplug.app.FrameworkFactory;
import org.osgi.framework.BundleContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.eoe.android.libraries.BundleContextFactory;
import cn.eoe.android.libraries.MyApplication;
import cn.eoe.android.libraries.R;
import cn.eoe.android.libraries.adapter.ListBundleAdapter;
import cn.eoe.android.libraries.adapter.MainFragmentAdapter;
import cn.eoe.android.libraries.entity.LibProduct;
import cn.eoe.android.libraries.widget.xlistview.XListView;

import com.actionbarsherlock.app.SherlockFragment;

public class BundleFragment extends SherlockFragment implements XListView.IXListViewListener {
	private ArrayList<LibProduct> productList = new ArrayList<LibProduct>();
    private MainFragmentAdapter mainFragmentAdapter;
    private View view;
    private XListView mListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Todo    待联网API
        productList = new ArrayList<LibProduct>();    //仅为测试
        for (int i = 0; i < 5; i++) {
            LibProduct product = new LibProduct();
            product.setTitle("插件名称");
            product.setLede("插件简介");
            product.setLid("插件ID");
            productList.add(product);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_main, null);
        mListView = (XListView) view.findViewById(R.id.fragment_main_list_view);
        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(this);
        view.setBackgroundColor(0);
        mainFragmentAdapter=new MainFragmentAdapter(getActivity(),productList);
        mListView.setAdapter(mainFragmentAdapter);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
    }

    @Override
    public void onLoadMore() {
        mListView.stopRefresh();
        mListView.stopLoadMore();

    }
}
