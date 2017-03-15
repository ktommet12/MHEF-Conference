package org.erickson_foundation.miltonhericksonfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TextView;

import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorker;
import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.erickson_foundation.miltonhericksonfoundation.Schedule.ScheduleFragment;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DBWorkerDelegate {
    private TableRow evolution;
    private ImageView evolutionPic, logoPic;

    private TextView conferenceTitle;
    private String currentlySelectedConference;
    private ConferenceType confType = ConferenceType.DEFAULT;

    private DBWorker dbWorker;

    private TabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        currentlySelectedConference = "Placeholder";
        if(intent.hasExtra(AppConfig.CONFERENCE_NAME_INTENT)){
            currentlySelectedConference = (String)intent.getCharSequenceExtra(AppConfig.CONFERENCE_NAME_INTENT);
        }
        if(currentlySelectedConference == "Evolution"){
            confType = ConferenceType.EVOLUTION;
        }else if (currentlySelectedConference == "Couples"){
            confType = ConferenceType.COUPLES;
        }else if(currentlySelectedConference == "Brief"){
            confType = ConferenceType.BRIEF;
        }else{
            confType = ConferenceType.DEFAULT;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setting the TextView in the NavDrawer to the selected Confernce Title
        View header = navigationView.getHeaderView(0);
        conferenceTitle = (TextView) header.findViewById(R.id.lblConferenceName);
        conferenceTitle.setText(currentlySelectedConference);




        changeFragment(R.id.nav_schedule);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule) {
            changeFragment(R.id.nav_schedule);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void changeFragment(int fragmentID){
        Fragment fragment = null;

        switch(fragmentID){
            case R.id.nav_schedule:
                fragment = new ScheduleFragment();
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void didFinishTask(JSONObject jsonObject) {
        try {
            Log.i("DidFinishTask", jsonObject.toString());
        }catch (Exception ex){}
    }
}
