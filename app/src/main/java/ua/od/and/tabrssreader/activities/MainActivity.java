package ua.od.and.tabrssreader.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ua.od.and.tabrssreader.ViewPagerFragments.Fragment1;
import ua.od.and.tabrssreader.ViewPagerFragments.Fragment2;
import ua.od.and.tabrssreader.R;

public class MainActivity extends FragmentActivity {

    private MyAdapter adapter;
    private ViewPager viewPager;
    private String strLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(), "Page 1");
        adapter.addFragment(new Fragment2(), "Page 2");
        viewPager.setAdapter(adapter);
        //viewPager.setCurrentItem(0);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Fragment1 fragment1 = (Fragment1) adapter.getItem(position);
                    if (fragment1 != null) {
                        fragment1.setLabel(strLabel);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setLabel(String strLabel) {
        this.strLabel = strLabel;
    }

    public void showProgressbar() {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" +
                viewPager.getCurrentItem());
        if (viewPager.getCurrentItem() == 1
                && page != null) {
            ((Fragment2) page).showProgressBar();
            Log.i("TAG", "showProgressBar");
        }
    }

    public void hideProgressbar() {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" +
                viewPager.getCurrentItem());
        if (viewPager.getCurrentItem() == 1
                && page != null) {
            ((Fragment2) page).hideProgressBar();
            Log.i("TAG", "hideProgressBar");
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public MyAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}