package org.erickson_foundation.miltonhericksonfoundation.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefWebViewClient;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * Created by User on 5/17/2017.
 */

public class WebFragment extends Fragment {
    private String url;
    private WebView webView;
    private String DEFAULT_URL = "https://www.evolutionofpsychotherapy.com/";
    private boolean isJSEnabled = false;
    public WebFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy_tickets, container, false);

        webView  =(WebView) v.findViewById(R.id.buy_tickets_web_view);

        Bundle bundle = getArguments();
        if(!(bundle == null)){
            if(bundle.containsKey(AppConfig.WEB_URL_KEY))
                url = bundle.getString(AppConfig.WEB_URL_KEY, DEFAULT_URL);
            if(bundle.containsKey(AppConfig.WEB_JAVASCRIPT_ENABLED_KEY)){
                isJSEnabled = bundle.getBoolean(AppConfig.WEB_JAVASCRIPT_ENABLED_KEY, false);
            }
        }
        webView.getSettings().setJavaScriptEnabled(isJSEnabled);
        webView.setWebViewClient(new MhefWebViewClient());

        webView.loadUrl(url);

        return v;
    }
}
