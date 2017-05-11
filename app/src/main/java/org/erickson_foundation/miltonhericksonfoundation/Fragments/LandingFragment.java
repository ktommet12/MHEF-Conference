package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    private static String VIDEO_ID = "WbZtDlb3RNE";
    private Button navMap, navSchedule, navSocialMedia, navAbout;
    private MainActivity mainActivity;
    TextView txtEventTitle;

    public LandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_landing, container, false);
        String eventTitle = getArguments().getString("eventTitle", "Erickson Foundation");
        mainActivity      = (MainActivity) getActivity();
        txtEventTitle     = (TextView) v.findViewById(R.id.event_title);
        navAbout          = (Button) v.findViewById(R.id.btn_nav_about);
        navSchedule       = (Button) v.findViewById(R.id.btn_nav_schedule);
        navSocialMedia    = (Button) v.findViewById(R.id.btn_nav_social_media);
        navMap            = (Button) v.findViewById(R.id.btn_nav_map);

        navAbout.setOnClickListener(navigationClick);
        navSchedule.setOnClickListener(navigationClick);
        navMap.setOnClickListener(navigationClick);
        navSocialMedia.setOnClickListener(navigationClick);


        txtEventTitle.setText(eventTitle);

        YouTubePlayerSupportFragment youtubePlayer = YouTubePlayerSupportFragment.newInstance();
        youtubePlayer.initialize(AppConfig.YOUTUBE_API_KEY, this);


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_player, youtubePlayer);
        transaction.commit();


        return v;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if(!wasRestored){
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
        String errorMessage = error.toString();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        Log.d("errorMessage: ", errorMessage);
    }
    View.OnClickListener navigationClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.changeFragment(v.getId());
        }
    };
}
