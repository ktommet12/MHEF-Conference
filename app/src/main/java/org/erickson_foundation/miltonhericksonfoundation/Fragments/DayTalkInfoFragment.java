package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceTalk;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayTalkInfoFragment extends Fragment {
    private ConferenceTalk currentTalk;
    private final String TAG = "DayTalkInfo";
    private TextView txtTitle, txtTimeAndDate, txtDescription;
    private Button btnAddToFavorites, btnViewOnMap;

    public DayTalkInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_day_talk_info, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();

        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(AppConfig.TALK_ID_BUNDLE_KEY)){
            currentTalk = mainActivity.currentConference.locateTalkById(bundle.getInt(AppConfig.TALK_ID_BUNDLE_KEY));
        }
        txtDescription = (TextView) v.findViewById(R.id.txt_event_description);
        txtTimeAndDate = (TextView) v.findViewById(R.id.txt_event_time_date);
        txtTitle       = (TextView) v.findViewById(R.id.txt_event_title);

        txtDescription.setText(currentTalk.getDescription());
        txtTitle.setText(currentTalk.getTitle());
        txtTimeAndDate.setText(currentTalk.getTimeSlot());

        return v;
    }

}
