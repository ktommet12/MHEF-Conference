package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceTalk;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefResources;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayTalkInfoFragment extends Fragment implements View.OnClickListener {
    private ConferenceTalk currentTalk;
    private final String TAG = "DayTalkInfo";
    private TextView txtTitle, txtTimeAndDate, txtDescription, txtSpeakerName;
    private Button btnAddToFavorites,btnRemoveFromFavorites, btnViewOnMap, btnBackToSchedule;
    private MainActivity mainActivity;
    private ImageView speakerImage;
    private int tabPos;

    public DayTalkInfoFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_day_talk_info, container, false);
        mainActivity = (MainActivity) getActivity();
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(AppConfig.TALK_ID_BUNDLE_KEY)){
            currentTalk = mainActivity.currentConference.locateTalkById(bundle.getInt(AppConfig.TALK_ID_BUNDLE_KEY));
            //currentTalk.toggleFavorite();
        }
        tabPos = mainActivity.currentConference.getSpeakerTabPosition(currentTalk.getTalkDay());

        txtDescription         = (TextView) v.findViewById(R.id.txt_event_description);
        txtTimeAndDate         = (TextView) v.findViewById(R.id.txt_event_time_date);
        txtTitle               = (TextView) v.findViewById(R.id.txt_event_title);
        txtSpeakerName         = (TextView) v.findViewById(R.id.txt_speaker_name);
        btnBackToSchedule      = (Button)v.findViewById(R.id.btn_back_to_schedule);
        btnViewOnMap           = (Button) v.findViewById(R.id.btn_go_to_map);
        btnAddToFavorites      = (Button) v.findViewById(R.id.btn_add_to_favorites);
        btnRemoveFromFavorites = (Button) v.findViewById(R.id.btn_remove_from_favorites);
        speakerImage           = (ImageView) v.findViewById(R.id.img_speaker_view);

        int resID = MhefResources.getImageResource(getContext(), currentTalk.getSpeaker().getShortName());
        if(resID != -1)
            speakerImage.setImageResource(resID);


        btnRemoveFromFavorites.setOnClickListener(this);

        if(currentTalk.isTalkFavorited()){
            btnRemoveFromFavorites.setVisibility(View.VISIBLE);
            btnAddToFavorites.setVisibility(View.GONE);
        }

        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.addToFavorites(currentTalk.getTitle());
            }
        });


        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(R.id.nav_map, null);
            }
        });

        btnBackToSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(R.id.nav_schedule, null);
            }
        });

        txtDescription.setText(currentTalk.getDescription());
        txtTitle.setText(currentTalk.getTitle());
        txtTimeAndDate.setText(currentTalk.getTimeSlot());
        txtSpeakerName.setText(currentTalk.getFullSpeakerName());

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_remove_from_favorites:
                Log.i(TAG, "BLah");
                break;
            case R.id.btn_back_to_schedule:
                mainActivity.loadSchedule(tabPos);
        }
    }
}
