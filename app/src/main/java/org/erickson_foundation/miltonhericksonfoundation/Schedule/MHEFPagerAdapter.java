package org.erickson_foundation.miltonhericksonfoundation.Schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ktomm on 3/14/2017.
 */

public class MHEFPagerAdapter extends FragmentStatePagerAdapter {
    private int mTabCount;
    private String[] mPageTitles;

    public MHEFPagerAdapter(FragmentManager fm, int tabCount, String[] titles){
        super(fm);
        this.mTabCount = tabCount;
        this.mPageTitles = titles;
    }
    @Override
    public Fragment getItem(int position) {
        String title = this.mPageTitles[position];
        Bundle bundle = new Bundle();
        bundle.putString("Date", title);
        Fragment fragment = new ScheduleDayFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getCount() {
        return this.mTabCount;
    }
}