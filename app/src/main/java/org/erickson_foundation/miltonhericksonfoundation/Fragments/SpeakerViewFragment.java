package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefWebViewClient;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakerViewFragment extends Fragment {
    private WebView webView;
    private final String SPEAKER_PAGE_BASE_URL = "http://www.evolutionofpsychotherapy.com/speakers/";
    private Button goBack, goForward, refresh;
    private String speakerUrl = "";
    private final String TAG = "SpeakerView";


    public SpeakerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v    = inflater.inflate(R.layout.fragment_speaker_view, container, false);
        webView   = (WebView) v.findViewById(R.id.webview);
        goBack    = (Button) v.findViewById(R.id.btn_go_back);
        goForward = (Button) v.findViewById(R.id.btn_go_forward);
        refresh   = (Button) v.findViewById(R.id.btn_refresh);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack())
                    webView.goBack();
            }
        });
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoForward())
                    webView.goForward();
            }
        });

        Bundle bundle = getArguments(); 

        if(!(bundle == null) && bundle.containsKey(AppConfig.SPEAKER_NAME_KEY)){
            String name = bundle.getString(AppConfig.SPEAKER_NAME_KEY);
            String[] namePieces = name.split(" ");
            String newName = namePieces[0].toLowerCase() + "-" + namePieces[1].toLowerCase();
            speakerUrl = SPEAKER_PAGE_BASE_URL + newName;
            Log.i(TAG, speakerUrl);
        }else{
            speakerUrl = SPEAKER_PAGE_BASE_URL;
        }
        webView.loadUrl(speakerUrl);
        webView.setWebViewClient(new MhefWebViewClient());

        return v;
    }
}
