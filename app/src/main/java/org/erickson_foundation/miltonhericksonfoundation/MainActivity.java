package org.erickson_foundation.miltonhericksonfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.Conference;
import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceTalk;
import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceType;
import org.erickson_foundation.miltonhericksonfoundation.Conference.Speaker;
import org.erickson_foundation.miltonhericksonfoundation.Fragments.*;
import org.erickson_foundation.miltonhericksonfoundation.Fragments.ScheduleFragment;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefSharedPrefs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView conferenceTitle;
    private ConferenceType confType = ConferenceType.DEFAULT;
    private JSONObject conferenceContents = null;
    public Conference currentConference;
    private final int LANDING_FRAGMENT_ID = 1, WEBSITE_FRAGMENT = 2;
    private ArrayList<String> favoritedTalks;

    private FragmentManager fragmentManager;

    private final String TAG = "MainActivity";
    public String aboutErickson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = this.getSupportFragmentManager();
        //fragmentManager.addOnBackStackChangedListener(fragmentChangedListener());

        //MhefSharedPrefs.deleteEverything(this, AppConfig.FAVORITES_ARRAY_SHARED_PREFS_KEY);
        favoritedTalks = MhefSharedPrefs.getStringArray(this, AppConfig.FAVORITES_ARRAY_SHARED_PREFS_KEY);
        Intent intent = getIntent();

        if(intent.hasExtra(AppConfig.CONFERENCE_TYPE_STRING)) {
            confType = (ConferenceType) intent.getSerializableExtra(AppConfig.CONFERENCE_TYPE_STRING);
        }
       try{
            String tempConferenceContents = null;
            if(intent.hasExtra(AppConfig.CONFERENCE_CONTENTS_JSON)){
                tempConferenceContents = intent.getStringExtra(AppConfig.CONFERENCE_CONTENTS_JSON);
            }
            //trying to convert the conferenceContents String into a Conference Object
            conferenceContents = new JSONObject(tempConferenceContents);
            aboutErickson = conferenceContents.getString("about-erickson");
            currentConference = new Conference(conferenceContents, favoritedTalks, this);
        }catch(JSONException ex){
            Log.e(TAG, ex.getMessage());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(currentConference != null){
            //setting the TextView in the NavDrawer to the selected Conference Title
            View header = navigationView.getHeaderView(0);
            conferenceTitle = (TextView) header.findViewById(R.id.lblConferenceName);
            header.setBackgroundColor(ContextCompat.getColor(this, R.color.mhefBlue));
            conferenceTitle.setText(currentConference.getTitle());
        }

        goHome();       //loads home fragment (landing fragment)
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(fragmentManager.getBackStackEntryCount() > 1) {
                fragmentManager.popBackStackImmediate();
            }
            String fragmentName = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).getName();
            if(fragmentName.equals("Schedule")){
                fragmentManager.popBackStackImmediate();
                this.loadSchedule(0);
            }else if(fragmentName.equals("Favorites")){
                fragmentManager.popBackStackImmediate();
                this.loadFavoritesSchedule();
            }
            //fragmentName = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).getName();
           // Log.i(TAG, fragmentName);
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        changeFragment(item.getItemId(), null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void changeFragment(int fragmentID){

    }
    public void changeFragment(int fragmentID, View v){
        Fragment fragment = null;
        Bundle bundle;
        switch(fragmentID){
            case R.id.btn_nav_schedule:
            case R.id.nav_schedule:
                loadSchedule(0);
                break;
//            case R.id.nav_feedback:
//                fragment = new FeedbackFragment();
//                break;
            case R.id.btn_nav_favorites:
            case R.id.nav_favorites:
                loadFavoritesSchedule();
                break;
            case R.id.btn_nav_speakers:
            case R.id.nav_speakers:
                this.loadAllSpeakersPage();
                break;
            case R.id.btn_nav_twitter:
            case R.id.nav_social:
                fragment = new SocialMediaFragment();
                break;
            case R.id.btn_nav_about:
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            case R.id.btn_nav_syllabus:
            case R.id.nav_syllabus:
                fragment = new WebFragment();
                bundle = new Bundle();
                bundle.putString(AppConfig.WEB_URL_KEY, this.currentConference.getSyllabusUrl());
                bundle.putBoolean(AppConfig.WEB_JAVASCRIPT_ENABLED_KEY, true);
                bundle.putBoolean("isPDF", true);
                fragment.setArguments(bundle);
                break;
            case WEBSITE_FRAGMENT:
            case R.id.nav_website:
                fragment = new WebFragment();
                bundle = new Bundle();
                bundle.putBoolean(AppConfig.WEB_JAVASCRIPT_ENABLED_KEY, true);
                bundle.putString(AppConfig.WEB_URL_KEY, "https://www.evolutionofpsychotherapy.com/");
                bundle.putBoolean("isPDF", false);
                fragment.setArguments(bundle);
                break;
            case R.id.btn_nav_register:
            case R.id.nav_buy_tickets:
                bundle = new Bundle();
                bundle.putString(AppConfig.WEB_URL_KEY, "https://catalog.erickson-foundation.org");
                bundle.putBoolean(AppConfig.WEB_JAVASCRIPT_ENABLED_KEY, false);
                fragment = new WebFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.btn_nav_parking_info:
            case R.id.nav_parking:
                viewMapItem("Parking");
                break;
            case AppConfig.MORE_INFO_TALK_FRAGMENT:
                fragment = new DayTalkInfoFragment();
                bundle = new Bundle();
                bundle.putInt(AppConfig.TALK_ID_BUNDLE_KEY, (Integer) v.getTag());
                fragment.setArguments(bundle);
                break;
            case R.id.btn_nav_map:
            case R.id.nav_map:
                this.loadMapList();
                break;
        }
        this.loadFragment(fragment, null);
    }
    private void loadFragment(Fragment f, String backStackName){
        if(f != null) {
            //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragment_container, f);
            ft.addToBackStack(backStackName);
            ft.commit();
            fragmentManager.executePendingTransactions();
        }
    }
    public void goHome(){
        if(fragmentManager.getBackStackEntryCount() == 0) {
            this.loadFragment(new LandingFragment(), "Home");
        }else{
            fragmentManager.popBackStack("Home", 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    public void goBackToConferenceSelector(View view) {
        finish();
        startActivity(new Intent(this, ConferenceSelector.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        goHome();
        return true;
    }
    public void addToFavorites(String title, String date){
        Log.i(TAG, title + " Was Favorited");
        this.favoritedTalks.add(title);
        currentConference.incrementFavoriteTalkCount();
        currentConference.getTalkByTitle(title).addToFavorites();
        //currentConference.addToFavorites(talk);
        MhefSharedPrefs.saveStringArray(this, AppConfig.FAVORITES_ARRAY_SHARED_PREFS_KEY, favoritedTalks);
    }
    public void removeFromFavorites(String title, String date){
        Thread t = Thread.currentThread();
        Log.i(TAG, t.getName());
        Log.i(TAG, title + " Was unfavorited");
        favoritedTalks.remove(title);
        currentConference.decrementFavoriteTalkCount();
        currentConference.getTalkByTitle(title).removeFromFavorites();
        //currentConference.removeFromFavorites(talk);
        //currentConference.toggleFavorite(title, date);
        MhefSharedPrefs.saveStringArray(this, AppConfig.FAVORITES_ARRAY_SHARED_PREFS_KEY, favoritedTalks);
    }
    public void loadSpeakerInfo(View v){
        Fragment fragment = new SpeakerInfoFragment();
        Bundle bundle = new Bundle();
        Speaker speaker = currentConference.getSpeakerById((Integer)v.getTag());
        if(speaker != null) {
            bundle.putSerializable(AppConfig.SPEAKER_BUNDLE_KEY, speaker);
        }
        fragment.setArguments(bundle);
        this.loadFragment(fragment, "SpeakerInfo");
    }
    public void loadAllSpeakersPage(){
        Fragment fragment = new SpeakerViewFragment();
        loadFragment(fragment, "Speakers");
    }
    public void loadSchedule(int tabPos){
        Fragment fragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConfig.SCHEDULE_TAB_POS, tabPos);
        fragment.setArguments(bundle);
        loadFragment(fragment, "Schedule");
    }
    public void viewMapItem(String item){
        Fragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConfig.MAP_ITEM_BUNDLE_KEY, item);
        fragment.setArguments(bundle);
        this.loadFragment(fragment, null);
    }
    public void loadMapList(){
        Fragment fragment = new MapListFragment();
        this.loadFragment(fragment, "MapList");
    }
    public void viewSpecificTalk(View v){
        Fragment fragment = new DayTalkInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConfig.TALK_ID_BUNDLE_KEY, (Integer) v.getTag());
        fragment.setArguments(bundle);
        loadFragment(fragment, "SpecificTalk");
    }
    public void goToViewMapScreen(String map){

    }
    public void loadFavoritesSchedule(){
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppConfig.IS_FAVORITES_TAB_SELECTED, true);
        Fragment fragment = new ScheduleFragment();
        fragment.setArguments(bundle);
        this.loadFragment(fragment, "Favorites");
    }
    public void loadWebsite(){
        this.changeFragment(WEBSITE_FRAGMENT, null);
    }

    private FragmentManager.OnBackStackChangedListener fragmentChangedListener(){
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(fragmentManager != null){
                    Log.i(TAG, "FragmentOnBackStackChangedListener called");
                }
            }
        };
        return result;
    }
    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy() called");
        super.onDestroy();
    }
}
