package cn.eoe.android.libraries.ui.fragment;

import java.util.ArrayList;
import java.util.List;

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
import cn.eoe.android.libraries.entity.LibProducts;
import cn.eoe.android.libraries.entity.ProductFactory;
import cn.eoe.android.libraries.entity.ProductFactory.ProductsRevHandler;
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
        //更新插件数据  每次都更新不使用缓存
        ProductFactory.getInstance(this.getActivity()).getProducts(false,"pop" ,1,
        		new ProductsRevHandler(){
					@Override
					public void onSuccess(int statusCode,LibProducts products,String slideitme ,int page) {
						System.err.println(statusCode+" "+products.getExpires());
						List<LibProduct> items=products.getItems();
						//清理
						productList.clear();
						//将最新数据拷贝到显示列表中
						for(int i=0;i<items.size();i++){
							productList.add(items.get(i));
						}
						//更新数据
						mainFragmentAdapter.notifyDataSetChanged();
					}

					@Override
					public void onFailure(int statusCode,Throwable throwable) {
						throwable.printStackTrace();
					}
        	
        });
        
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
