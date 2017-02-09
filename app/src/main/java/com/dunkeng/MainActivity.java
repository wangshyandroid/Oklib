package com.dunkeng;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.dunkeng.news.FragmentNewsMain;
import com.dunkeng.zhihu.FragmentZhihu;
import com.oklib.AppManager;
import com.oklib.base.CoreBaseActivity;

import butterknife.BindView;

public class MainActivity extends CoreBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentOpenDrawerListener {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onResume() {
        super.onResume();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, (Toolbar) findViewById(R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.main_container, new FragmentZhihu());
        }
//        navigationView.setCheckedItem(R.id.nav_zhihu);
    }

    @Override
    public void onBackPressedSupport() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressedSupport();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_zhihu) {
            loadRootFragment(R.id.main_container, new FragmentZhihu());
            // Handle the camera action
        } else if (id == R.id.nav_news) {
            FragmentNewsMain fragmentNewsMain = FragmentNewsMain.newInstance(0);
            loadRootFragment(R.id.main_container, fragmentNewsMain);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onOpenDrawer() {
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再按一次结束蹲坑", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    finish();
                    AppManager.getAppManager().AppExit(mContext);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
