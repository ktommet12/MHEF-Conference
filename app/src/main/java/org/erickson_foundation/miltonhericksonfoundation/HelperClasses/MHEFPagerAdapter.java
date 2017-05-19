package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.erickson_foundation.miltonhericksonfoundation.Fragments.ScheduleDayFragment;

/**
 * Created by ktomm on 3/14/2017.
 */

public class MhefPagerAdapter extends FragmentStatePagerAdapter {
    private int mTabCount;
    private String[] mPageTitles;

    public MhefPagerAdapter(FragmentManager fm, int tabCount, String[] titles){
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
