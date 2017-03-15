package org.erickson_foundation.miltonhericksonfoundation.Schedule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.erickson_foundation.miltonhericksonfoundation.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleDayFragment extends Fragment {


    public ScheduleDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_day, container, false);
    }

}
