package org.erickson_foundation.miltonhericksonfoundation.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.erickson_foundation.miltonhericksonfoundation.R;
import org.erickson_foundation.miltonhericksonfoundation.TouchImageView;

public class MapFragment extends Fragment {
    private TouchImageView floorPlan;

    private final String TAG = "MapFragment";


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        floorPlan = (TouchImageView) v.findViewById(R.id.conference_floor_plan);
        return v;
    }
}
