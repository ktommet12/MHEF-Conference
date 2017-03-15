package org.erickson_foundation.miltonhericksonfoundation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;

import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorker;
import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.json.JSONException;
import org.json.JSONObject;

public class ConferenceSelector extends AppCompatActivity implements View.OnClickListener, DBWorkerDelegate {
    private TableRow couples, evolution;
    private DBWorker dbWorker;
    private final String TAG = "ConfSelector";
    private ConferenceType confType = ConferenceType.DEFAULT;
    private ProgressDialog mProgressDialog;
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

    @Override
    public void onClick(View v) {
        startProgressDialog();
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
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.mProgressDialog.setMessage("Grabbing Conference Information, Please Wait...");
        this.mProgressDialog.setIndeterminate(false);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.show();
    }
    private void loadConference(){
        Intent intent = new Intent(this, MainActivity.class);
        switch(this.confType){
            case EVOLUTION:
                intent.putExtra(AppConfig.CONFERENCE_NAME_INTENT, "Evolution");
                break;
            case COUPLES:
                intent.putExtra(AppConfig.CONFERENCE_NAME_INTENT, "Couples");
                break;
            case BRIEF:
                intent.putExtra(AppConfig.CONFERENCE_NAME_INTENT, "Brief");
                break;
            default:
                intent.putExtra(AppConfig.CONFERENCE_NAME_INTENT, "Default");
        }
        intent.putExtra(AppConfig.CONFERENCE_CONTENTS_JSON, confContents);
        startActivity(intent);
    }
    //once the app contacts the DB and its returns with either information or an error it will call didFinishTask
    @Override
    public void didFinishTask(JSONObject jsonObject) {
        if(this.mProgressDialog != null && mProgressDialog.isShowing()){
            this.mProgressDialog.hide();
        }
        dbWorker = null;
        isTaskInProgress = false;
        try{
            //the last DBWorkerTask was a failure for some reason
            if(jsonObject.optBoolean("wasASuccess", false) == false){
                Log.i(TAG, "Error Contacting Server");
            }else{
                Log.i(TAG+" Response", jsonObject.toString());
                this.confContents = jsonObject.toString();
                loadConference();
            }
        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }
}
