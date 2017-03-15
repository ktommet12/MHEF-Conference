package org.erickson_foundation.miltonhericksonfoundation.Schedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ktomm on 3/14/2017.
 */

public class MHEFPagerAdapter extends FragmentStatePagerAdapter {
    private int mTabCount;

    public MHEFPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.mTabCount = tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        return new ScheduleDayFragment();
    }
    @Override
    public int getCount() {
        return this.mTabCount;
    }
}
