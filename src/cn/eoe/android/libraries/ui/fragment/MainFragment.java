package cn.eoe.android.libraries.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.eoe.android.libraries.BundleContextFactory;
import cn.eoe.android.libraries.MyApplication;
import cn.eoe.android.libraries.R;
import cn.eoe.android.libraries.adapter.ListBundleAdapter;
import cn.eoe.android.libraries.adapter.MainFragmentAdapter;
import cn.eoe.android.libraries.entity.LibProduct;
import cn.eoe.android.libraries.ui.activity.DetailsActivity;
import cn.eoe.android.libraries.widget.xlistview.XListView;
import com.actionbarsherlock.app.SherlockFragment;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JaveZh
 * Date: 14-1-23
 * Time: 上午9:42
 * To change this template use File | Settings | File Templates.
 */
public class MainFragment extends SherlockFragment implements XListView.IXListViewListener {

    private static MainFragment mInstance;
    private ArrayList<LibProduct> productList = new ArrayList<LibProduct>();
    private MainFragmentAdapter mainFragmentAdapter;
    private View view;
    private XListView mListView;


    private FrameworkInstance frame=null;
    private List<org.osgi.framework.Bundle> bundles=null;
    private ListBundleAdapter adapter=null;

    public static MainFragment newInstance() {
        if (mInstance == null) {
            mInstance = new MainFragment();
        }
        return mInstance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getActivity().getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.common_title_bg));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Todo    待联网API
//        productList = new ArrayList<LibProduct>();    //仅为测试
//        for (int i = 0; i < 5; i++) {
//            LibProduct product = new LibProduct();
//            product.setLib_name("类库名称");
//            product.setLib_submitter("Jave");
//            product.setLib_brief("类库简介，类库简介类库简介类库简介类库简介类库简介");
//            productList.add(product);
//        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_main, null);
        mListView = (XListView) view.findViewById(R.id.fragment_main_list_view);
        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(this);
        view.setBackgroundColor(0);

        try
        {
            //启动框架
            frame= FrameworkFactory.getInstance().start(null,getActivity(),new MyApplication(getActivity().getApplicationContext()));
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
        bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
        BundleContext context =BundleContextFactory.getInstance().getBundleContext();
        for(int i=0;i<context.getBundles().length;i++)
        {
            //获取已安装插件
            bundles.add(context.getBundles()[i]);
        }
        adapter=new ListBundleAdapter(getActivity(),bundles);
        mListView.setAdapter(adapter);
        ListenerBundleEvent();

//       istView.setAdapter(mainFragmentAdapter);
        return view;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.abs_about:
//                Toast.makeText(getActivity(), "Menu Item abs_about selected", Toast.LENGTH_SHORT)
//                        .show();
//                break;
//            case R.id.abs_settings:
//                Toast.makeText(getActivity(), "Menu item abs_settings selected", Toast.LENGTH_SHORT)
//                        .show();
//                break;
//            default:
//                break;
//        }
//
//        return true;
//    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // Place an action bar item for searching.
//        MenuItem item = menu.add("Search");
//        item.setIcon(R.drawable.search);
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        menu.add("设置").setIcon(R.drawable.ic_compose_inverse).setTitle("设置").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        menu.add("关于我们").setIcon(R.drawable.ic_compose).setTitle("关于我们").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        View searchView = SearchViewCompat.newSearchView(getActivity());
//        if (searchView != null) {
//            SearchViewCompat.setOnQueryTextListener(searchView,
//                    new SearchViewCompat.OnQueryTextListenerCompat() {
//                        @Override
//                        public boolean onQueryTextChange(String newText) {
//                            // Called when the action bar search text has changed.  Since this
//                            // is a simple array adapter, we can just have it do the filtering.
//                            return true;
//                        }
//                    });
//            item.setActionView(searchView);
//        }
//    }

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
