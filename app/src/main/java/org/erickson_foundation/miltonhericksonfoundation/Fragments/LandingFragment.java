package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.erickson_foundation.miltonhericksonfoundation.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    public LandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_landing, container, false);
        String eventTitle = getArguments().getString("eventTitle", "Erickson Foundation");
        TextView txtEventTitle = (TextView) v.findViewById(R.id.event_title);
        txtEventTitle.setText(eventTitle);

        YouTubePlayerSupportFragment youtubePlayer = (YouTubePlayerSupportFragment) v.findViewById(R.id.youtubesupportfragment);
        return v;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
