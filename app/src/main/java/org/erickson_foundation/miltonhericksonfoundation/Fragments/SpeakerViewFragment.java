package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakerViewFragment extends Fragment {
    private WebView webView;
    private final String SPEAKER_PAGE_BASE_URL = "http://www.evolutionofpsychotherapy.com/speakers/";
    private Button goBack;
    private String speakerUrl = "";
    private final String TAG = "SpeakerView";


    public SpeakerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_speaker_view, container, false);
        webView = (WebView) v.findViewById(R.id.webview);
        goBack = (Button) v.findViewById(R.id.btn_go_back);
        goBack.setEnabled(false);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();
            }
        });

        Bundle bundle = getArguments(); 

        if(bundle.containsKey(AppConfig.SPEAKER_NAME_KEY) && bundle != null){
            String name = bundle.getString(AppConfig.SPEAKER_NAME_KEY);
            String[] namePieces = name.split(" ");
            String newName = namePieces[0].toLowerCase() + "-" + namePieces[1].toLowerCase();
            speakerUrl = SPEAKER_PAGE_BASE_URL + newName;
            Log.i(TAG, speakerUrl);
        }else{
            speakerUrl = SPEAKER_PAGE_BASE_URL;
        }
        webView.loadUrl(speakerUrl);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                goBack.setEnabled(true  );
                return super.shouldOverrideUrlLoading(view, url);

            }
        });

        return v;
    }

}
