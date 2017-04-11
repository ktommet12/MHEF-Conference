package org.erickson_foundation.miltonhericksonfoundation.Schedule;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.getColor;

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

        dayTalks = new ArrayList<>();

        Bundle bundle = getArguments();
        String date = null;
        if(bundle != null && bundle.containsKey("Date")){
            date = bundle.getString("Date");
        }

        if(date != null){
            Log.i(TAG, "Schedule Day Fragment Created for Day: " + date);
            dayTalks = currentConference.getConferenceDayTalks(date);
            table = (TableLayout) view.findViewById(R.id.schedule_day_table);
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            //String title = null;
            TableRow tr;
            if(dayTalks != null) {
                for(int i = 0; i < dayTalks.size(); i++){
                    ConferenceTalk talk = dayTalks.get(i);

                    TableRow newRow = (TableRow) View.inflate(getContext(), R.layout.table_row, null);
                    TextView    title  = (TextView) newRow.findViewById(R.id.talk_title),
                                time   = (TextView) newRow.findViewById(R.id.time_slot);

                    if(i != 0) {
                        //setting a margin for the cell
                        LinearLayout layout = (LinearLayout) newRow.findViewById(R.id.talk_row);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) time.getLayoutParams();
                        params.setMargins(0, 100, 0, 0);
                        time.setLayoutParams(params);
                    }

                    title.setText(talk.getTitle());
                    title.setTextColor(getColor(getContext(), R.color.black));
                    title.setTextSize(20.0f);
                    DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
                    title.setMaxWidth(metrics.widthPixels - 60);
                    time.setText(talk.getTimeSlot());

                    table.addView(newRow, layoutParams);
                    if(dayTalks.size() != 1){
                        View line = new View(getContext());
                        line.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                        line.setBackgroundColor(Color.BLACK);
                        table.addView(line);
                    }
                }
            }
            else{
                tr = new TableRow(getContext());
                TextView text = new TextView(getContext());
                text.setText(R.string.empty_schedule_day);
                tr.addView(text);
                table.addView(tr, layoutParams);
            }
        }

        return view;
    }
}
