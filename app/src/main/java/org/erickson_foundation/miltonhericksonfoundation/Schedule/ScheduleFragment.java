package org.erickson_foundation.miltonhericksonfoundation.Schedule;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceTalk;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {
    // PLACEHOLDER WILL CHANGE WHEN ONCREATEVIEW() RUNS!!
    private String[] confTabDates = {"Pre", "Day 1", "Day 2", "Day 3"};     //stores the Dates for the Conference, They will be used for the Tab Titles
    private MainActivity mainActivity;
    private TableLayout table;
    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.schedule_fragment, container, false);

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

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

}
