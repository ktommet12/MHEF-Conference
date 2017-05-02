package org.erickson_foundation.miltonhericksonfoundation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.erickson_foundation.miltonhericksonfoundation.Twitter.SearchTwitter;
import org.erickson_foundation.miltonhericksonfoundation.Twitter.TwitterConfig;
import org.json.JSONObject;

import java.util.List;

import io.fabric.sdk.android.Fabric;

public class TestActivity extends AppCompatActivity implements DBWorkerDelegate {
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private ListView tweetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tweetList = (ListView) findViewById(R.id.tweet_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TwitterConfig.TWITTER_CONSUMER_KEY, TwitterConfig.TWITTER_CONSUMER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("EricksonFound")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();

        tweetList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        //searchView.setOnQueryTextListener(listener);
        return true;
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_SHORT).show();
            return false;
        }
    };

    @Override
    public void didFinishTask(JSONObject jsonObject) {

    }
}
