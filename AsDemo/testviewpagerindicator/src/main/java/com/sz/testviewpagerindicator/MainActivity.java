package com.sz.testviewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity {
    /**
     * Tab标题
     */
    private static final String[] TITLE = new String[] { "头条", "房产", "另一面", "女人",
            "财经", "数码", "情感", "科技" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例化ViewPager， 然后给ViewPager设置Adapter
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        //实例化TabPageIndicator，然后与ViewPager绑在一起（核心步骤）
        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        //如果要设置监听ViewPager中包含的Fragment的改变(滑动切换页面)，使用OnPageChangeListener为它指定一个监听器，那么不能像之前那样直接设置在ViewPager上了，而要设置在Indicator上，
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                Toast.makeText(getApplicationContext(), TITLE[arg0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    /**
     * 定义ViewPager的适配器
     */
    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = new ItemFragment();
            Bundle args = new Bundle();
            args.putString("arg", TITLE[position]);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }
    }
}