package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceTalk;
import org.erickson_foundation.miltonhericksonfoundation.Conference.Speaker;
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
    private Button btnAddToFavorites,btnRemoveFromFavorites, btnBackToSchedule;
    private MainActivity mainActivity;
    private ImageView speakerImage;
    private int tabPos;
    private LinearLayout dayInfoSpeakerList;

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
            currentTalk = mainActivity.currentConference.getTalkById(bundle.getInt(AppConfig.TALK_ID_BUNDLE_KEY));
            //currentTalk.toggleFavorite();
        }
        tabPos = mainActivity.currentConference.getSpeakerTabPosition(currentTalk.getTalkDay());

        TextView txtDescription   = (TextView) v.findViewById(R.id.txt_event_description);
        TextView txtTimeAndDate   = (TextView) v.findViewById(R.id.txt_event_time_date);
        TextView txtTitle         = (TextView) v.findViewById(R.id.txt_event_title);
        TextView txtTalkRoom      = (TextView) v.findViewById(R.id.talk_room);
        TextView txtSpeakerName   = (TextView) v.findViewById(R.id.txt_speaker_name);
        TextView txtTalkCategory  = (TextView) v.findViewById(R.id.talk_category);
        TextView txtDate          = (TextView) v.findViewById(R.id.talk_date);
        Button btnViewOnMap       = (Button) v.findViewById(R.id.btn_go_to_map);
        btnAddToFavorites         = (Button) v.findViewById(R.id.day_talk_info_add_to_favorites);
        btnRemoveFromFavorites    = (Button) v.findViewById(R.id.btn_remove_from_favorites);
        speakerImage              = (ImageView) v.findViewById(R.id.img_speaker_view);


        if(currentTalk.isTalkFavorited()){
            btnRemoveFromFavorites.setVisibility(View.VISIBLE);
            btnAddToFavorites.setVisibility(View.GONE);
        }
        btnRemoveFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddToFavorites.setVisibility(View.VISIBLE);
                btnRemoveFromFavorites.setVisibility(View.GONE);
                mainActivity.removeFromFavorites(currentTalk.getTitle(), currentTalk.getTalkDay());
            }
        });
        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddToFavorites.setVisibility(View.GONE);
                btnRemoveFromFavorites.setVisibility(View.VISIBLE);
                mainActivity.addToFavorites(currentTalk.getTitle(), currentTalk.getTalkDay());
            }
        });


        btnViewOnMap.setOnClickListener(this);

        Speaker[] speakers = currentTalk.getAllSpeakers();
        dayInfoSpeakerList = (LinearLayout) v.findViewById(R.id.talk_info_speakers_list);

        for(Speaker currentSpeaker : speakers){
            View speakerView = inflater.inflate(R.layout.talk_speaker_layout, null);

            TextView name = (TextView)speakerView.findViewById(R.id.txt_speaker_name);
            ImageView speakerPic = (ImageView) speakerView.findViewById(R.id.img_speaker_view);

            name.setText(currentSpeaker.getFullName());
            name.setTag(currentSpeaker.getSpeakerID());
            name.setOnClickListener(this);

            int resID = MhefResources.getImageResource(getContext(), currentSpeaker.getShortName());
            if(resID != 0){
                speakerPic.setImageResource(resID);
            }
            else speakerPic.setImageResource(R.drawable.placeholder_pic);

            dayInfoSpeakerList.addView(speakerView);
        }

        txtDescription.setText(currentTalk.getDescription());
        txtTitle.setText(currentTalk.getTitle());
        txtTimeAndDate.setText("Time Slot: " + currentTalk.getTimeSlot());
        txtTalkRoom.setText("Room: " + currentTalk.getRoom().getFullRoom());
        txtDate.setText("Date: " + currentTalk.getTalkDay()+"th");
        txtTalkCategory.setText("Category: " + currentTalk.getTalkCategory().toString());

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
//            case R.id.btn_back_to_schedule:
//                mainActivity.loadSchedule(tabPos);
//                break;
            case R.id.txt_speaker_name:
                mainActivity.loadSpeakerInfo(v);
                break;
            case R.id.btn_go_to_map:
                mainActivity.viewMapItem(currentTalk.getRoom().getRoom());
        }
    }
}
