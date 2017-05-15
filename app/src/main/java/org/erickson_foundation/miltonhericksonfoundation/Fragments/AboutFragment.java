package org.erickson_foundation.miltonhericksonfoundation.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.BuildConfig;
import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.GetHtml;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MHEFProgressDialog;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;
import org.json.JSONObject;

public class AboutFragment extends Fragment implements DBWorkerDelegate {
    private final String TAG = "WebViewFrag";
    private String aboutContents;
    private MHEFProgressDialog dialogProgress;
    private MainActivity mainActivity;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        mainActivity = (MainActivity) getActivity();
        TextView aboutText = (TextView) v.findViewById(R.id.txt_about_text);
        aboutText.setText(mainActivity.aboutErickson);

        TextView txtVersionNum = (TextView) v.findViewById(R.id.txt_version_num);
        String versionNum = BuildConfig.VERSION_NAME;
        txtVersionNum.setText("App Version: " + versionNum);




        return v;
    }

    @Override
    public void didFinishTask(JSONObject jsonObject) {
        //aboutContents = jsonObject.toString();
        //Log.i(TAG, aboutContents);
        int x = 0;
        //Log.i(TAG, jsonObject.);
        dialogProgress.hide();
    }
}
