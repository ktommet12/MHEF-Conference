package org.erickson_foundation.miltonhericksonfoundation.Fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.MainActivity;
import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment {
    private TextView navMap, navSchedule, navSocialMedia, navAbout, navBuyTickets,
                     navSpeakers, navWebsite, navSyllabus, navParking, navFavorites;
    private MainActivity mainActivity;

    public LandingFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landing, container, false);

        mainActivity      = (MainActivity) getActivity();

        navSchedule       = (TextView) v.findViewById(R.id.btn_nav_schedule);
        navBuyTickets     = (TextView) v.findViewById(R.id.btn_nav_register);
        navAbout          = (TextView) v.findViewById(R.id.btn_nav_about);
        navMap            = (TextView) v.findViewById(R.id.btn_nav_map);
        navSocialMedia    = (TextView) v.findViewById(R.id.btn_nav_twitter);
        navSpeakers       = (TextView) v.findViewById(R.id.btn_nav_speakers);
        //navWebsite        = (TextView) v.findViewById(R.id.btn_nav_website);
        navSyllabus       = (TextView) v.findViewById(R.id.btn_nav_syllabus);
        navParking        = (TextView) v.findViewById(R.id.btn_nav_parking_info);
        navFavorites      = (TextView) v.findViewById(R.id.btn_nav_favorites);



        navSchedule.setOnClickListener(navigationClick);
        navBuyTickets.setOnClickListener(navigationClick);
        navAbout.setOnClickListener(navigationClick);
        navMap.setOnClickListener(navigationClick);
        navSocialMedia.setOnClickListener(navigationClick);
        navSpeakers.setOnClickListener(navigationClick);
        //navWebsite.setOnClickListener(navigationClick);
        navSyllabus.setOnClickListener(navigationClick);
        navParking.setOnClickListener(navigationClick);
        navFavorites.setOnClickListener(navigationClick);


        return v;
    }
    View.OnClickListener navigationClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.changeFragment(v.getId(), null);
        }
    };
}
