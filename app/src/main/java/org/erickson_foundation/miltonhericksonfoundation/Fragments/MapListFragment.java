package org.erickson_foundation.miltonhericksonfoundation.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

import java.util.ArrayList;

public class MapListFragment extends Fragment implements View.OnClickListener {
    private MainActivity mainActivity;
    private final String TAG = "MapListFragment";
    public MapListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_list, container, false);
        mainActivity = (MainActivity) getActivity();
        ArrayList<String> roomKeys = mainActivity.currentConference.getRoomKeys();
        //LinearLayout mapList = (LinearLayout) v.findViewById(R.id.map_list);
        TableLayout mapListTable = (TableLayout) v.findViewById(R.id.map_list_table);

        for(String key : roomKeys){
            TableRow newMapListItem = (TableRow) inflater.inflate(R.layout.map_list_item, null);
            TextView mapItem = (TextView) newMapListItem.findViewById(R.id.map_list_item);

            mapItem.setText(key);

            mapListTable.addView(newMapListItem);
            newMapListItem.setClickable(true);
            newMapListItem.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        TableRow tr = (TableRow) view;
        TextView mapItem = (TextView) tr.findViewById(R.id.map_list_item);
        Log.i(TAG, mapItem.getText().toString());

        mainActivity.viewMapItem(mapItem.getText().toString());
    }
}
