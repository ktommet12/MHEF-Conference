package org.erickson_foundation.miltonhericksonfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.TabHost;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.Conference.Conference;
import org.erickson_foundation.miltonhericksonfoundation.DB.*;
import org.erickson_foundation.miltonhericksonfoundation.Fragments.*;
import org.erickson_foundation.miltonhericksonfoundation.Fragments.ScheduleFragment;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView conferenceTitle;
    private ConferenceType confType = ConferenceType.DEFAULT;
    private JSONObject conferenceContents = null;
    public Conference currentConference;
    private final int LANDING_FRAGMENT_ID = 1;

    private final String TAG = "MainActivity";
    public String aboutErickson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            currentConference = new Conference(conferenceContents);
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
        } else {}
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
            case R.id.nav_feedback:
                fragment = new FeedbackFragment();
                break;
            case R.id.btn_nav_speakers:
            case R.id.nav_speakers:
                fragment = new SpeakerViewFragment();
                break;
            case R.id.btn_nav_social_media:
            case R.id.nav_social:
                fragment = new SocialMediaFragment();
                break;
            case R.id.btn_nav_about:
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            case R.id.btn_nav_website:
                fragment = new WebFragment();
                bundle = new Bundle();
                bundle.putBoolean(AppConfig.WEB_JAVASCRIPT_ENABLED_KEY, true);
                bundle.putString(AppConfig.WEB_URL_KEY, "https://www.evolutionofpsychotherapy.com/");
                fragment.setArguments(bundle);
                break;
            case R.id.btn_nav_buy_tickets:
                bundle = new Bundle();
                bundle.putString(AppConfig.WEB_URL_KEY, "https://www.evolutionofpsychotherapy.com/register/");
                bundle.putBoolean(AppConfig.WEB_JAVASCRIPT_ENABLED_KEY, false);
                fragment = new WebFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_parking:
                startActivity(new Intent(this, TestActivity.class));
                break;
            case AppConfig.MORE_INFO_TALK_FRAGMENT:
                fragment = new DayTalkInfoFragment();
                bundle = new Bundle();
                bundle.putInt(AppConfig.TALK_ID_BUNDLE_KEY, (Integer) v.getTag());
                fragment.setArguments(bundle);
                break;
            case R.id.btn_nav_map:
            case R.id.nav_map:
                fragment = new MapFragment();
                break;
        }
        this.loadFragment(fragment);
    }
    private void loadFragment(Fragment f){
        if(f != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, f);
            ft.commit();
        }
    }
    public void goHome(){
        this.loadFragment(new LandingFragment());
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
    public void addToFavorites(String title){

    }
    public void removeFromFavorites(String name){

    }
    public void loadSchedule(int tabPos){
        Fragment fragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConfig.SCHEDULE_TAB_POS, tabPos);
        fragment.setArguments(bundle);
        loadFragment(fragment);
    }
    public void viewSpecificTalk(View v){
        Fragment fragment = new DayTalkInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConfig.TALK_ID_BUNDLE_KEY, (Integer) v.getTag());
        fragment.setArguments(bundle);
        loadFragment(fragment);
    }
    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy() called");
        super.onDestroy();
    }
}
