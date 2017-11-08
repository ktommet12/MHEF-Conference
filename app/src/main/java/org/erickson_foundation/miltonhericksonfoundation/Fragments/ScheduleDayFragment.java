package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.Conference;
import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceTalk;
import org.erickson_foundation.miltonhericksonfoundation.Conference.Speaker;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

import java.util.ArrayList;

public class ScheduleDayFragment extends Fragment implements View.OnClickListener {
    private TableLayout table;
    private MainActivity mainActivity;
    private Conference currentConference;
    private ArrayList<ConferenceTalk> dayTalks;
    private final String TAG = "ScheduleDayFragment";
    private boolean isFavoriteTabSelected = false, isFilterTabSelected = false;
    private LinearLayout layout;
    private View noFavoriteTalksRow;
    private String date;

    public ScheduleDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_day, container, false);

        mainActivity = (MainActivity)getActivity();
        currentConference = mainActivity.currentConference;



        Bundle bundle = getArguments();
        date = null;

        if(!(bundle == null)){
            if(bundle.containsKey("Date"))
                date = bundle.getString("Date");
            if(bundle.containsKey(AppConfig.IS_FAVORITES_TAB_SELECTED))
                isFavoriteTabSelected = bundle.getBoolean(AppConfig.IS_FAVORITES_TAB_SELECTED);
            if(bundle.containsKey(AppConfig.IS_FILTER_TAB_SELECTED))
                isFilterTabSelected = bundle.getBoolean(AppConfig.IS_FILTER_TAB_SELECTED);
        }
        layout = (LinearLayout) view.findViewById(R.id.talk_day);



        if(isFavoriteTabSelected){
            dayTalks = currentConference.getAllFavoritedTalksForDay(date);
            noFavoriteTalksRow = inflater.inflate(R.layout.no_favorites_to_display, null);
            noFavoriteTalksRow.setVisibility(View.GONE);
            noFavoriteTalksRow.setTag(AppConfig.NO_FAVORITES_TABLE_ROW_TAG);
            layout.addView(noFavoriteTalksRow);
        }
        else{
            dayTalks = currentConference.getConferenceDayTalks(date, false);
        }

        if(date != null && dayTalks.size() != 0){
            for (int i = 0; i < dayTalks.size(); i++) {
                ConferenceTalk currentTalk = dayTalks.get(i);

                View newTalkRow = inflater.inflate(R.layout.table_row, null);
                TextView time = (TextView) newTalkRow.findViewById(R.id.time_slot),
                        title = (TextView) newTalkRow.findViewById(R.id.talk_title),
                        room  = (TextView) newTalkRow.findViewById(R.id.room),
                        name  = (TextView) newTalkRow.findViewById(R.id.speaker_name);
                LinearLayout talk_table_row = (LinearLayout) newTalkRow.findViewById(R.id.talk_table_row);

                Button addToFavorites = (Button) newTalkRow.findViewById(R.id.btn_schedule_day_add_to_favorites),
                        goToMap        = (Button) newTalkRow.findViewById(R.id.btn_schedule_day_go_to_map);

                time.setText(currentTalk.getTimeSlot());
                title.setText(currentTalk.getTitle());
                room.setText(currentTalk.getRoom().getFullRoom());
                name.setText(currentTalk.getFullNameString().replace(";", ","));
                layout.addView(newTalkRow);

                addToFavorites.setOnClickListener(this);
                goToMap.setOnClickListener(this);

                addToFavorites.setTag(currentTalk.getTalkID());
                talk_table_row.setTag(currentTalk.getTalkID());
                if(currentTalk.isTalkFavorited()){
                    addToFavorites.setText(R.string.remove_from_favorites);
                }

                LinearLayout talkDetails = (LinearLayout) newTalkRow.findViewById(R.id.layout_details);
                talkDetails.setOnClickListener(talkClickListener);
                talkDetails.setTag(currentTalk.getTalkID());
            }
        }else{
            if(isFavoriteTabSelected){
                noFavoriteTalksRow.setVisibility(View.VISIBLE);
            }
            else layout.addView(inflater.inflate(R.layout.no_info_to_display, null));
        }
        return view;
    }
    View.OnClickListener talkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Speaker test = currentConference.getSpeakerById((Integer)v.getTag());
            Log.i(TAG, v.getTag().toString());
            mainActivity.viewSpecificTalk(v);
        }
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_go_to_map:
                Log.i(TAG, "Go To Map Was Pressed");
                break;
            case R.id.btn_schedule_day_add_to_favorites:
                LinearLayout unfavoritedRow = (LinearLayout)layout.findViewWithTag(v.getTag());

                ConferenceTalk talk = mainActivity.currentConference.getTalkById((Integer)v.getTag());
                Button temp = (Button)v;
                String addText = getResources().getString(R.string.add_to_favorites);
                if(temp.getText().toString().equals(addText)){
                    temp.setText(R.string.remove_from_favorites);
                    mainActivity.addToFavorites(talk.getTitle(), talk.getTalkDay());
                }else{
                    temp.setText(R.string.add_to_favorites);
                    mainActivity.removeFromFavorites(talk.getTitle(), talk.getTalkDay());
                    if(this.isFavoriteTabSelected) {
                        unfavoritedRow.setVisibility(View.GONE);
                        if(this.date != null && mainActivity.currentConference.getNumFavoritedTalksForDay(this.date) == 0){
                            noFavoriteTalksRow.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
        }
    }
}