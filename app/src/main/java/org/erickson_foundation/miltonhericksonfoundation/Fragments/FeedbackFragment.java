package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private MainActivity mainActivity;
    private Conference currentConference;
    private Button submitFeedback, clearFeedback;
    private CheckBox sendDeviceInfo;
    private EditText feedbackMessage;
    private final String TAG = "FeedbackFragment";

    public FeedbackFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        sendDeviceInfo = (CheckBox) view.findViewById(R.id.send_Info);
        feedbackMessage = (EditText) view.findViewById(R.id.feedback_message);

        submitFeedback = (Button) view.findViewById(R.id.btn_submit_feedback);
        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempMessage = feedbackMessage.getText().toString();
                String debugInfo = null;

                if(sendDeviceInfo.isChecked()) {
                    debugInfo = "Device Information: ";
                    debugInfo += "\n OS Version: "   + System.getProperty("os.version");
                    debugInfo += "\n SDK Version: "  + Build.VERSION.SDK_INT;
                    debugInfo += "\n Device: "       + Build.DEVICE;
                    debugInfo += "\n Model: "        + Build.MODEL;
                    debugInfo += "\n Product: "      + Build.PRODUCT;
                    debugInfo += "\n Manufacturer: " + Build.MANUFACTURER;
                }

                String finalMessage = tempMessage + "\n" +  debugInfo;
                Log.i(TAG, finalMessage);

            }
        });

        clearFeedback = (Button) view.findViewById(R.id.btn_clear_feedback);
        clearFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackMessage.setText("");
                sendDeviceInfo.setChecked(true);
            }
        });



        return view;
    }
}
