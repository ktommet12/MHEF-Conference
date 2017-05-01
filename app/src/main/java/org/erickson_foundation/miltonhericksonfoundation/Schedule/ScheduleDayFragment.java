package org.erickson_foundation.miltonhericksonfoundation.Schedule;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.Conference;
import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceTalk;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

import java.util.ArrayList;

public class ScheduleDayFragment extends Fragment {
    private TableLayout table;
    private MainActivity mainActivity;
    private Conference currentConference;
    private ArrayList<ConferenceTalk> dayTalks;
    private final String TAG = "ScheduleDayFragment";

    public ScheduleDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_day, container, false);

        mainActivity = (MainActivity)getActivity();
        currentConference = mainActivity.currentConference;



        Bundle bundle = getArguments();
        String date = null;
        if(bundle != null && bundle.containsKey("Date")){
            date = bundle.getString("Date");
        }
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.talk_day);
        dayTalks = currentConference.getConferenceDayTalks(date);
        if(date != null && dayTalks.size() != 0){
            for (int i = 0; i < dayTalks.size(); i++) {
                View temp = inflater.inflate(R.layout.table_row, null);
                TextView time = (TextView) temp.findViewById(R.id.time_slot),
                        title = (TextView) temp.findViewById(R.id.talk_title),
                        name = (TextView) temp.findViewById(R.id.speaker_name);

                ConferenceTalk currentTalk = dayTalks.get(i);

                time.setText(currentTalk.getTimeSlot());
                title.setText(currentTalk.getTitle());
                name.setText(currentTalk.getmSpeakerName());
                layout.addView(temp);

                if (dayTalks.size() != 1) {
                    View line = new View(getContext());
                    line.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                    line.setBackgroundColor(Color.BLACK);
                    layout.addView(line);
                }
            }
        }else{
            layout.addView(inflater.inflate(R.layout.no_info_to_display, null));
        }


        return view;
    }
}
