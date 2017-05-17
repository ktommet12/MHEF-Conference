package org.erickson_foundation.miltonhericksonfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorker;
import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity implements DBWorkerDelegate {
    private final String TAG = "SplashScreen";
    private String confContents;
    private ConferenceType confType;

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

        DBWorker dbWorker = new DBWorker(this, confType);
        dbWorker.setOnFinishedListener(this);
        dbWorker.execute();

    }

    @Override
    public void didFinishTask(JSONObject jsonObject) {
        if(jsonObject != null) {
            this.confContents = jsonObject.toString();
            loadConference();
        }
        else
            Toast.makeText(this, "Problem Downloading Conference Information", Toast.LENGTH_SHORT).show();
    }
    private void loadConference(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppConfig.CONFERENCE_CONTENTS_JSON, confContents);
        intent.putExtra(AppConfig.CONFERENCE_TYPE_STRING, confType);
        finish();
        startActivity(intent);
    }
}
