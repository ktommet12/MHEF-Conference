package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakerViewFragment extends Fragment {
    private WebView webView;
    private final String SPEAKER_PAGE_BASE_URL = "http://www.evolutionofpsychotherapy.com/speakers/";
    private String speakerUrl = "";


    public SpeakerViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_speaker_view, container, false);
        webView = (WebView) v.findViewById(R.id.webview);
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra(AppConfig.SPEAKER_NAME_KEY)){
            speakerUrl = SPEAKER_PAGE_BASE_URL + intent.getExtras().getString(AppConfig.SPEAKER_NAME_KEY)

        }
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        return v;
    }

}
