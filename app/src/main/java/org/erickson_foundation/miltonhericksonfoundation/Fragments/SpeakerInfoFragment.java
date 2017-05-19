package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceTalk;
import org.erickson_foundation.miltonhericksonfoundation.Conference.Speaker;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefResources;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakerInfoFragment extends Fragment implements View.OnClickListener{
    private final String TAG = "SpeakerInfo";
    private Speaker currentSpeaker;
    private TextView txtSpeakerName, txtSpeakerBio;
    private ImageView speakerImg;
    private MainActivity mainActivity;
    private LinearLayout speakerPanelList;

    public SpeakerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.speaker_profile, container, false);

        mainActivity = (MainActivity) getActivity();

        Bundle bundle = getArguments();

        if(bundle != null){
            if(bundle.containsKey(AppConfig.SPEAKER_BUNDLE_KEY)){
                currentSpeaker = (Speaker)bundle.get(AppConfig.SPEAKER_BUNDLE_KEY);
            }
        }

        int picId = MhefResources.getImageResource(getContext(), currentSpeaker.getShortName());
        int bioId = MhefResources.getStringResource(getContext(), currentSpeaker.getShortName());

        ArrayList<ConferenceTalk> speakerTalks = mainActivity.currentConference.getAllTalksBySpeaker(currentSpeaker);

        String speakerBio = getResources().getString(bioId);

        txtSpeakerBio = (TextView) v.findViewById(R.id.speaker_profile_bio_text);
        txtSpeakerName = (TextView) v.findViewById(R.id.speaker_profile_name);
        speakerImg = (ImageView) v.findViewById(R.id.speaker_profile_image);
        speakerPanelList = (LinearLayout) v.findViewById(R.id.speaker_panel_list);

        txtSpeakerName.setText(currentSpeaker.getFullName());
        speakerImg.setImageResource(picId);
        txtSpeakerBio.setText(speakerBio);

        for(ConferenceTalk talk : speakerTalks){
            View listItem = inflater.inflate(R.layout.speaker_panel_item, null);
            TextView txtTalKTitle = (TextView) listItem.findViewById(R.id.txt_panel_title);

            txtTalKTitle.setText(talk.getShortTitle());
            txtTalKTitle.setOnClickListener(this);
            txtTalKTitle.setTag(talk.getTalkID());

            speakerPanelList.addView(listItem);
        }


        return v;
    }

    @Override
    public void onClick(View v) {
/*        TextView panelTitle = (TextView) v.findViewById(R.id.txt_panel_title);
        int talkID = (Integer)panelTitle.getTag();
        Log.i(TAG, panelTitle.getText().toString());*/

        mainActivity.viewSpecificTalk(v);
    }
}
