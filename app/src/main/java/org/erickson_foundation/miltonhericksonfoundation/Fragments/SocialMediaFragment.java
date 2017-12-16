package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
public class SocialMediaFragment extends Fragment {


    public SocialMediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_social_media, container, false);
        ListView tweetList = (ListView) v.findViewById(R.id.tweet_list);




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
