package org.erickson_foundation.miltonhericksonfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceType;
import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorker;
import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.json.JSONObject;
import org.w3c.dom.Text;

import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity implements DBWorkerDelegate, View.OnClickListener{
    private final String TAG = "SplashScreen";
    private String confContents;
    private ConferenceType confType;
    private TextView splashScreenInfo;
    private Button btnRetry;

    private DBWorker dbWorker;

    private static final String TWITTER_KEY = "30QCzhZ13e3U7Ox29pZT7O7XB";
    private static final String TWITTER_SECRET = "hGUmN8NumH1438rkVrID8vcg3Qh0wUBziuqtjpwIsF9Z47bcT4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.content_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        confType = AppConfig.DEFAULT_CONFERENCE;

        splashScreenInfo = (TextView) findViewById(R.id.txt_splash_screen_info);
        btnRetry         = (Button)   findViewById(R.id.btn_splash_retry);

        btnRetry.setOnClickListener(this);

        loadConferenceContents();
    }

    private void loadConferenceContents(){
        btnRetry.setVisibility(View.INVISIBLE);
        splashScreenInfo.setText(R.string.loading_info);
        dbWorker = new DBWorker(confType);
        dbWorker.setOnFinishedListener(this);
        dbWorker.execute();
    }
    @Override
    public void didFinishTask(JSONObject jsonObject) {
        if(jsonObject.optBoolean("wasASuccess")) {
            this.confContents = jsonObject.toString();
            loadConference();
        }
        else{
            btnRetry.setVisibility(View.VISIBLE);
            splashScreenInfo.setText(R.string.server_error);
        }
    }
    private void loadConference(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppConfig.CONFERENCE_CONTENTS_JSON, confContents);
        intent.putExtra(AppConfig.CONFERENCE_TYPE_STRING, confType);
        finish();
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        loadConferenceContents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbWorker.setOnFinishedListener(null);
        btnRetry.setOnClickListener(null);
    }
}
