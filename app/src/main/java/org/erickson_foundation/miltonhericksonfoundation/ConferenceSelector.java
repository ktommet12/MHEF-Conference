package org.erickson_foundation.miltonhericksonfoundation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;

import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceType;
import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorker;
import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefProgressDialog;
import org.json.JSONObject;

public class ConferenceSelector extends AppCompatActivity implements View.OnClickListener, DBWorkerDelegate {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    private TableRow couples, evolution;
    private DBWorker dbWorker;
    private final String TAG = "ConfSelector";
    private ConferenceType confType = ConferenceType.DEFAULT;

    private MhefProgressDialog progressDialog;

    private boolean isTaskInProgress = false;
    private String confContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conference_selector);

        couples = (TableRow) findViewById(R.id.couples_conferece);
        evolution = (TableRow) findViewById(R.id.evolution_conferece);

        couples.setOnClickListener(this);
        evolution.setOnClickListener(this);

        dbWorker = new DBWorker();
        dbWorker.setOnFinishedListener(this);
    }
    private void startEvoConference(){
       // startProgressDialog();
        Intent intent = new Intent(this, MainActivity.class);

        this.confType = ConferenceType.EVOLUTION;
        dbWorker.setConferenceType(this.confType);
        dbWorker.execute();
    }
    @Override
    public void onClick(View v) {
        //startProgressDialog();
        Intent intent = new Intent(this, MainActivity.class);
        switch (v.getId()){
            case R.id.couples_conferece:
                this.confType = ConferenceType.COUPLES;
                break;
            case R.id.evolution_conferece:
                this.confType = ConferenceType.EVOLUTION;
                break;
        }
        if (!isTaskInProgress && dbWorker != null){
            dbWorker.setConferenceType(this.confType);
            dbWorker.execute(); //initiate information download from server
            isTaskInProgress = true;
        }else{
            if(dbWorker == null){
                dbWorker = new DBWorker(this.confType);
                dbWorker.setOnFinishedListener(this);
                dbWorker.execute();
            }
        }

    }
    private void startProgressDialog(){
        progressDialog = new MhefProgressDialog.Builder()
                .message("Grabbing Conference Information, Please Wait...")
                .indeterminate(false)
                .cancelable(false)
                .context(this)
                .build();

        progressDialog.show();
    }
    private void loadConference(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppConfig.CONFERENCE_CONTENTS_JSON, confContents);
        intent.putExtra(AppConfig.CONFERENCE_TYPE_STRING, confType);
        finish();
        startActivity(intent);
    }
    //once the app contacts the DB and its returns with either information or an error it will call didFinishTask
    @Override
    public void didFinishTask(JSONObject jsonObject) {
        dbWorker = null;
        isTaskInProgress = false;
        try{
            if(jsonObject.optBoolean("wasASuccess", false) == false){
                Log.i(TAG, "Error Contacting Server");
            }else{
                this.confContents = jsonObject.toString();
                loadConference();
            }
        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
       // progressDialog.dismiss();
        Log.i(TAG, "OnDestroy() called");
        super.onDestroy();
    }
}
