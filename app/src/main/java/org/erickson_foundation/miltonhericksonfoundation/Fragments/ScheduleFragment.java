package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MHEFPagerAdapter;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";
    //confTabDates will be reassigned when the app runs, after it downloads the newest schedule from the server
    private String[] confTabDates = {"Pre", "Day 1", "Day 2", "Day 3"};     //stores the Dates for the Conference, They will be used for the Tab Titles
    //parent Activity
    private MainActivity mainActivity;
    private TableLayout table;

    public ScheduleFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.schedule_fragment, container, false);

        Bundle bundle = getArguments();
        int tabPos = 0;

        if(!(bundle == null) && bundle.containsKey(AppConfig.SCHEDULE_TAB_POS)){
            tabPos = bundle.getInt(AppConfig.SCHEDULE_TAB_POS, 0);
        }

        mainActivity = (MainActivity) getActivity();
        confTabDates = mainActivity.currentConference.getDates();

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //adding the Tabs from the Dates provided from the JSON File downloaded from website
        for(int i = 0; i < confTabDates.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(confTabDates[i]));
        }

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final MHEFPagerAdapter pagerAdapter = new MHEFPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), confTabDates);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(tabPos);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.i(TAG, "Tab Selected");
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        return view;
    }

}
