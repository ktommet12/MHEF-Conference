package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefPagerAdapter;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

public class ScheduleFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ScheduleFragment";
    //confTabDates will be reassigned when the app runs, after it downloads the newest schedule from the server
    private String[] confTabDates = {"Pre", "Day 1", "Day 2", "Day 3"};     //stores the Dates for the Conference, They will be used for the Tab Titles
    //parent Activity
    private MainActivity mainActivity;
    private TableLayout table;
    private Button btnViewFavoritesSchedule, btnViewWholeSchedule, btnFilterByCategory;
    private LayoutInflater mLayoutInflater;

    public ScheduleFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLayoutInflater = inflater;
        Log.i(TAG, "Schedule Fragment onCreateView() called");
        View view = mLayoutInflater.inflate(R.layout.schedule_fragment, container, false);

        btnViewFavoritesSchedule = (Button)view.findViewById(R.id.btn_view_favorites_only);
        btnViewWholeSchedule = (Button) view.findViewById(R.id.btn_view_whole_schedule);

        btnViewWholeSchedule.setOnClickListener(this);
        btnViewFavoritesSchedule.setOnClickListener(this);

        boolean isFavoritesSelected = false, isFilterSelected = false;

        Bundle bundle = getArguments();
        int tabPos = 0;

        if(!(bundle == null)){
            if(bundle.containsKey(AppConfig.SCHEDULE_TAB_POS)){
                tabPos = bundle.getInt(AppConfig.SCHEDULE_TAB_POS, 0);
            }
            if(bundle.containsKey(AppConfig.IS_FAVORITES_TAB_SELECTED)){
                isFavoritesSelected = bundle.getBoolean(AppConfig.IS_FAVORITES_TAB_SELECTED, false);
            }
            if(bundle.containsKey(AppConfig.IS_FILTER_TAB_SELECTED)){
                isFilterSelected = bundle.getBoolean(AppConfig.IS_FILTER_TAB_SELECTED, false);
            }
        }
        //setting the active button on the rocker based on the selected button, or the default which is the whole schedule
        if(isFavoritesSelected){
            setActiveButton(btnViewFavoritesSchedule);
        }else if(isFilterSelected){
            setActiveButton(btnFilterByCategory);
        }else{
            setActiveButton(btnViewWholeSchedule);
        }

        mainActivity = (MainActivity) getActivity();
        confTabDates = mainActivity.currentConference.getDates();

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //adding the tabs and setting the dates
        for(String date : confTabDates){
            tabLayout.addTab(tabLayout.newTab().setText(date));
        }

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final MhefPagerAdapter pagerAdapter = new MhefPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), confTabDates);

        pagerAdapter.setFavoritesFilter(isFavoritesSelected);
        pagerAdapter.setFilterByCategory(isFilterSelected);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(tabPos);

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

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "ScheduleFragment onResume() called");

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_view_favorites_only:
                mainActivity.loadFavoritesSchedule();
                break;
            case R.id.btn_view_whole_schedule:
                mainActivity.loadSchedule(0);
        }
    }
    private void setActiveButton(Button btn){
        btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mhefBlue));
        btn.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
    }
    private View loadSchedule(){
        return null;
    }
}
