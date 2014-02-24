/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.eoe.android.libraries.ui.activity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import cn.eoe.android.libraries.R;
import cn.eoe.android.libraries.entity.LibSlides;
import cn.eoe.android.libraries.entity.SlideFactory;
import cn.eoe.android.libraries.entity.SlideFactory.SlideRevHandler;
import cn.eoe.android.libraries.ui.fragment.BundleFragment;
import cn.eoe.android.libraries.ui.fragment.MainFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: JaveZh
 * Date: 14-1-22
 * Time: 上午10:32
 * To change this template use File | Settings | File Templates.
 */
@SuppressLint("NewApi") public class MainActivity extends SherlockFragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout mDrawerLeft;
    private FrameLayout mContentLayout;
    private PopupWindow selectPopupWindow;
    private int mActivePosition = 0;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] names = new String[]{"参与规则", "联系我们", "评分鼓励", "意见反馈", "关于我们"};
    private int[] imagesIds = new int[]{R.drawable.participation, R.drawable.contact, R.drawable.score, R.drawable.opinion, R.drawable.people};

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.common_title_bg));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLeft = (LinearLayout) findViewById(R.id.left_drawer);
        mContentLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_list_view);

//        List<Object> items = new ArrayList<Object>();
//        items.add(new Category(""));
//        items.add(new Item("最新的", R.drawable.star));
//        items.add(new Item("热门的", R.drawable.like_me));
//        items.add(new Item("收藏的", R.drawable.star));
        //获取侧栏目数据 每次都更新不使用缓存
        SlideFactory.getInstance(this).getSlide(false,
        		new SlideRevHandler(){

					@Override
					public void onSuccess(int statusCode, LibSlides slides) {
						//异步获取的，如果有缓存数据就直接返回
						System.err.println(statusCode+" "+slides.getData().get(0).getItems().get(0).getTitle());
					}

					@Override
					public void onFailure(int statusCode, Throwable throwable) {
						throwable.printStackTrace();
					}
        	
        });
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }


    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	System.out.println("position:"+position);
        	selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
    	System.out.println("position:"+position);
    	Fragment fragment=null;
    	switch(position){
    		case 0: 
    			fragment = new BundleFragment();
    			break;
    		case 1: 
    			fragment = new MainFragment();
    	        
    			break;
    		default:
    			fragment = new BundleFragment();
    			break;
    	}
    	getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerLeft);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 当左上角标题栏图标被单击时
                if (mDrawerLayout.isDrawerOpen(mDrawerLeft)) {
                    mDrawerLayout.closeDrawer(mDrawerLeft);
                } else {
                    mDrawerLayout.openDrawer(mDrawerLeft);
                }
                break;
            //TODO  待完善
            case R.id.abs_attend:
                break;
            case R.id.abs_contact:
                break;
            case R.id.abs_score:
                break;
            case R.id.abs_opinion:
                break;
            case R.id.abs_about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isExit = false;

    private CountDownTimer countDownTimer = new CountDownTimer(2000, 500) {

        @Override
        public void onTick(long millisUntilFinished) {
            isExit = true;
        }

        @Override
        public void onFinish() {
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isExit) {
                System.exit(0);
            }
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            countDownTimer.start();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        countDownTimer.cancel();
        super.onPause();
    }

    @Override
    protected void onResume() {
        countDownTimer.cancel();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}