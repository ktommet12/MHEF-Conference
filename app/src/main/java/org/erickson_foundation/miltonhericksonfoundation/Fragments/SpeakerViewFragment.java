package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.Speaker;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefResources;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefWebViewClient;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakerViewFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "SpeakerView";
    private ArrayList<Speaker> speakerNames;
    private MainActivity mainActivity;
    private LinearLayout speakerList;


    public SpeakerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v    = inflater.inflate(R.layout.fragment_speaker_view, container, false);

        speakerList = (LinearLayout) v.findViewById(R.id.speaker_list);

        mainActivity = (MainActivity) getActivity();
        speakerNames = mainActivity.currentConference.getAllSpeakers();

        for(Speaker speaker : speakerNames){
            View speakerListItem = inflater.inflate(R.layout.speaker_list_item, null);
            ImageView speakerPic = (ImageView) speakerListItem.findViewById(R.id.img_speaker_image);
            TextView speakerName = (TextView) speakerListItem.findViewById(R.id.speaker_name);
            speakerListItem.setClickable(true);
            speakerListItem.setOnClickListener(this);

            speakerListItem.setTag(speaker.getSpeakerID());

            speakerName.setText(speaker.getFullName());
            int resID = MhefResources.getImageResource(getContext(), speaker.getShortName());
            if(resID != -1){
                speakerPic.setImageResource(resID);
            }
            speakerList.addView(speakerListItem);
        }

        //Log.i(TAG, speakerNames.toString());

        return v;
    }

    @Override
    public void onClick(View v) {
       mainActivity.loadSpeakerInfo(v);
    }
}
