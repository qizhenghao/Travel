package com.bruce.travel.desktop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;
import com.bruce.travel.db.MyDbHelper;
import com.bruce.travel.finds.ui.FindFragment;
import com.bruce.travel.message.ui.DestinationFragment;
import com.bruce.travel.mine.ui.LoginActivity;
import com.bruce.travel.mine.ui.MineFragment;
import com.bruce.travel.mine.ui.SettingActivity;
import com.bruce.travel.travels.TravelsFragment;
import com.bruce.travel.universal.utils.ImageUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class DesktopActivity extends BaseActivity {
    public static boolean isLogin = false;
    public static String username = null;
    private TravelsFragment travelsFragment;
    private FindFragment findFragment;
    private DestinationFragment destinationFragment;
    private MineFragment mineFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private Button[] mTabs;

    private boolean mBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop_layout);
        init();

        travelsFragment = new TravelsFragment();
        findFragment = new FindFragment();
        destinationFragment = new DestinationFragment();
        mineFragment = new MineFragment();
        fragments = new Fragment[] {travelsFragment, findFragment, destinationFragment, mineFragment};
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, travelsFragment).add(R.id.fragment_container, findFragment)
                    .hide(findFragment).show(travelsFragment).commit();
        }

        Intent intent = getIntent();
        isLogin = intent.getBooleanExtra("loginState", false);
        username = intent.getStringExtra("username");
    }

    @Override
    protected void initViews() {
        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.travels_btn);
        mTabs[1] = (Button) findViewById(R.id.find_btn);
        mTabs[2] = (Button) findViewById(R.id.destination_btn);
        mTabs[3] = (Button) findViewById(R.id.mine_btn);
        // select first tab
        mTabs[0].setSelected(true);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }

    public void onTabClicked(View view) {
        switch(view.getId()) {
            case R.id.travels_btn:
                index = 0;
                break;
            case R.id.find_btn:
                index = 1;
                break;
            case R.id.destination_btn:
                index = 2;
                break;
            case R.id.mine_btn:
                index = 3;
                break;
        }

        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fm = getSupportFragmentManager();
        handlerFragmentOnActivityResult(fm, requestCode, resultCode, data);
    }

    public void handlerFragmentOnActivityResult(FragmentManager fragmentManager, int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
                FragmentManager childFragmentManager = fragment.getChildFragmentManager();
                List<Fragment> childFragments = childFragmentManager.getFragments();
//                if (childFragmentManager != null) {
//
//                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!mBackPressed) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackPressed = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackPressed = false;
                }
            }, 2000);
        } else {
            this.finish();
            System.exit(0);
        }
    }
}
