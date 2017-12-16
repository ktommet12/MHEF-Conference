package org.erickson_foundation.miltonhericksonfoundation.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.BuildConfig;
import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefProgressDialog;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;
import org.json.JSONObject;

public class AboutFragment extends Fragment implements View.OnClickListener  {
    private final String TAG = "WebViewFrag";
    private String aboutContents;
    private MhefProgressDialog dialogProgress;
    private MainActivity mainActivity;
    private Button goToWebsite, goToSpeakerEval;

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
        goToWebsite     = (Button) v.findViewById(R.id.about_btn_go_to_erickson_website);
        goToSpeakerEval = (Button) v.findViewById(R.id.about_btn_go_to_speaker_eval_preview);

        goToWebsite.setOnClickListener(this);
        goToSpeakerEval.setOnClickListener(this);

        String versionNum = BuildConfig.VERSION_NAME;
        txtVersionNum.setText("App Version: " + versionNum);




        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.about_btn_go_to_speaker_eval_preview:
                mainActivity.viewMapItem("Speaker Evaluation");
                break;
            case R.id.about_btn_go_to_erickson_website:
                mainActivity.loadWebsite();
                break;
        }
    }
}
