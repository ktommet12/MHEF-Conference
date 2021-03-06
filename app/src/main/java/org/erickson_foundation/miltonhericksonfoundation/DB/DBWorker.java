package org.erickson_foundation.miltonhericksonfoundation.DB;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceType;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.MhefProgressDialog;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DBWorker extends AsyncTask<Void, Void, JSONObject>{
    private final String TAG = "DBWorker";
    private DBWorkerDelegate delegate;
    private ConferenceType confType;

    //String representing a problem with the schedule download
    private final String ERROR_STRING = "There was a problem getting the schedule from the server";

    public void setOnFinishedListener(DBWorkerDelegate delegate){
        this.delegate = delegate;
    }
    public void setConferenceType(ConferenceType conferenceType){
        this.confType = conferenceType;
    }
    public DBWorker(ConferenceType confType){
        this.confType = confType;
    }
    public DBWorker(){
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        try {
            URL url = null;
            switch(confType){
                case COUPLES:
                    break;
                case EVOLUTION:
                    url = new URL(AppConfig.EVOLUTION_SCHEDULE_URL);
                    break;
                default:
                    JSONObject json = new JSONObject(this.ERROR_STRING);
                    json.put("wasASuccess", false);
                    return new JSONObject(this.ERROR_STRING);
            }

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            JSONObject result = new JSONObject(readURLReturnData(connection));
            connection.disconnect();
            result.put("wasASuccess", true);
            return result;


        }catch(MalformedURLException ex){
            return failure(ex.getMessage());
        }catch(IOException ex){
            return failure(ex.getMessage());
        }catch(Exception ex){
            return failure(ex.getMessage());
        }
    }
    private JSONObject failure(String message){
        JSONObject json = new JSONObject();
        try {
            json.putOpt("Error:", this.ERROR_STRING);
            if(AppConfig.DEBUG){
                json.putOpt("Exception Message", message);
            }
            json.putOpt("wasASuccess", false);
        }
        catch(Exception ex){}
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
        this.delegate.didFinishTask(s);
    }
    private String readURLReturnData(HttpURLConnection connection){
        String result = null;
        StringBuilder sb = new StringBuilder();
        InputStream is = null;

        try{
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while((inputLine = br.readLine()) != null){
                sb.append(inputLine);
            }
            result = sb.toString();
        }
        catch(Exception e){
            Log.i(TAG, "Error Reading Input Stream");
            result = null;
        }
        finally{
            if(is != null){
                try{
                    is.close();
                }
                catch(IOException e){}
            }
        }
        return result;
    }
}
