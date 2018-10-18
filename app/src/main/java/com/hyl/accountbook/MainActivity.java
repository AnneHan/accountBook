package com.hyl.accountbook;

import com.hyl.accountbook.CircleMenuLayout.OnMenuItemClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @programName: MainActivity.java
 * @programFunction: the home page
 * @createDate: 2018/09/19
 * @author: AnneHan
 * @version:
 * xx.   yyyy/mm/dd   ver    author    comments
 * 01.   2018/09/19   1.00   AnneHan   New Create
 */
public class MainActivity extends AppCompatActivity {

    //第一次点击事件发生的时间
    private long mExitTime;

    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[] { "登录&注册", "关于我们", "心愿墙",
            "特色设置", "收入&支出", "统计" };
    private int[] mItemImgs = new int[] { R.mipmap.home_mbank_1_normal,
            R.mipmap.home_mbank_2_normal, R.mipmap.home_mbank_3_normal,
            R.mipmap.home_mbank_4_normal, R.mipmap.home_mbank_5_normal,
            R.mipmap.home_mbank_6_normal };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);

        mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public void itemClick(View view, int pos) {
                if (mItemTexts[pos] == "特色设置") {
                    openSettingWind(view);
                } else if (mItemTexts[pos] == "收入&支出") {
                    openSpendingWind(view);
                } else if (mItemTexts[pos] == "登录&注册") {
                    openLoginWind(view);
                } else if (mItemTexts[pos] == "统计") {
                    openCountWind(view);
                } else if (mItemTexts[pos] == "关于我们") {
                    openAboutUsAddWind(view);
                } else if (mItemTexts[pos] == "心愿墙") {
                    openWishWind(view);
                }
                ;
            }

            public void itemCenterClick(View view) {
                Toast.makeText(MainActivity.this, "you can do something just like login or register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 跳转至登录&注册界面
     * @param v
     */
    private void openLoginWind(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        this.startActivity(intent);
    }

    /**
     * 跳转至开销界面，记录收入与支出
     * @param v
     */
    private void openSpendingWind(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SpendingActivity.class);
        this.startActivity(intent);
    }

    /**
     * 跳转至统计界面
     * @param v
     */
    private void openCountWind(View v){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, CountActivity.class);
        this.startActivity(intent);
    }

    /**
     * 跳转至特色设置界面
     * @param v
     */
    private void openSettingWind(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SettingActivity.class);
        this.startActivity(intent);
    }

    /**
     * 跳转至心愿墙界面
     * @param v
     */
    private void openWishWind(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, WishActivity.class);
        this.startActivity(intent);
    }

    /**
     * 跳转至关于我们界面
     * @param v
     */
    private void openAboutUsAddWind(View v){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, AboutUsActivity.class);
        this.startActivity(intent);
    }

    /**
     * 点击两次返回退出app
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出金金记较", Toast.LENGTH_SHORT).show();
                //System.currentTimeMillis()系统当前时间
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
