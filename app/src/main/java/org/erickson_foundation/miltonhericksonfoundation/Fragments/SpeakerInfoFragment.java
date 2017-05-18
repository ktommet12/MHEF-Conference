package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.erickson_foundation.miltonhericksonfoundation.Conference.Speaker;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefResources;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakerInfoFragment extends Fragment {
    private Speaker currentSpeaker;

    public SpeakerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_speaker_info, container, false);


        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey(AppConfig.SPEAKER_BUNDLE_KEY)){
                currentSpeaker = (Speaker)bundle.get(AppConfig.SPEAKER_BUNDLE_KEY);
            }
        }
        int x= 0;
        //int picId = MhefResources.getImageResource(getContext(), speakersName);
        return v;
    }

}
