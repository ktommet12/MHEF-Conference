package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.Conference;
import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceTalk;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {
    MainActivity mainActivity;
    Conference currentConference;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        mainActivity = (MainActivity) getActivity();
        currentConference = mainActivity.currentConference;
        ArrayList<ConferenceTalk> dayTalks = currentConference.getConferenceDayTalks("Dec 12");

        ScrollView scroll = (ScrollView) view.findViewById(R.id.scrollContainer);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.temp);

        int index = 0;
        for(int j = 0; j < 4; j++) {
            for (int i = 0; i < dayTalks.size(); i++) {
                View temp = inflater.inflate(R.layout.no_info_to_display, null);
                TextView t1 = (TextView) temp.findViewById(R.id.time_slot),
                         t2 = (TextView) temp.findViewById(R.id.talk_title),
                         t3 = (TextView) temp.findViewById(R.id.speaker_name);
                ConferenceTalk currentTalk = dayTalks.get(i);

                t3.setText(currentTalk.getTimeSlot());
                t2.setText(currentTalk.getTitle());
                t3.setText(currentTalk.getmSpeakerName());

                layout.addView(temp);

                if(dayTalks.size() != 1){
                    View line = new View(getContext());
                    line.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                    line.setBackgroundColor(Color.BLACK);
                    layout.addView(line);
                }
            }
        }




        return view;
    }

}
