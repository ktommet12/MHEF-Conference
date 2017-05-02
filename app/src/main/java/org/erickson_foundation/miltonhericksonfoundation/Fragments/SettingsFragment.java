package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;
import org.erickson_foundation.miltonhericksonfoundation.Twitter.TwitterConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private ListView tweetList;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment, container, false);
        tweetList = (ListView) v.findViewById(R.id.tweet_list);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("@" + TwitterConfig.FOUNDATION_TIMELINE);


        //Using Fabric to grab the timeline
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(TwitterConfig.FOUNDATION_TIMELINE)
                .build();

        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext())
                .setTimeline(userTimeline)
                .build();

        tweetList.setAdapter(adapter);
        return v;
    }
}
