package org.erickson_foundation.miltonhericksonfoundation.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.HelperFunctions;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.TouchImageView;

public class MapFragment extends Fragment implements View.OnClickListener {
    private TouchImageView floorPlan;
    private String mMapItem;
    private MainActivity mainActivity;
    private Button btnBackToMapList;

    private final String TAG = "MapFragment";


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mainActivity = (MainActivity) getActivity();

        Bundle bundle = getArguments();
        if(bundle.containsKey(AppConfig.MAP_ITEM_BUNDLE_KEY)){
            this.mMapItem = bundle.getString(AppConfig.MAP_ITEM_BUNDLE_KEY);
        }
        mMapItem = HelperFunctions.toPicString(mMapItem);

        int resID = getResources().getIdentifier(mMapItem , "drawable", getActivity().getPackageName());

        floorPlan        = (TouchImageView) v.findViewById(R.id.conference_floor_plan);
        //btnBackToMapList = (Button)         v.findViewById(R.id.back_to_mapList);

        floorPlan.setImageResource(resID);
        //btnBackToMapList.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, ((Integer)view.getId()).toString());
//        if (view.getId() == R.id.back_to_mapList){
//            mainActivity.loadMapList();
//        }
    }
}
