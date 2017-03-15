package org.erickson_foundation.miltonhericksonfoundation.DB;

import android.os.AsyncTask;
import android.util.Log;

import org.erickson_foundation.miltonhericksonfoundation.ConferenceType;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 3/9/2017.
 */

public class DBWorker extends AsyncTask<Void, Void, JSONObject>{
    private final String TAG = "DBWorker";
    private DBWorkerDelegate delegate;
    private ConferenceType confType;

    //URLS for each of the conferences,
    private final String EVOLUTION_SCHEDULE_URL = "http://www.evolutionofpsychotherapy.com/wp-content/uploads/evolutionConference.json";
    //TODO: get urls for Couples and Brief Therapy for the Conference Schedule
    private final String COUPLES_SCHEDULE_URL = "";
    private final String BRIEF_THERAPY_SCHEDULE_URL = "";

    //String representing a problem with the schedule download
    private final String ERROR_STRING = "{\"error\":\"There was a problem getting the schedule from the server\"}";

    public void setOnFinishedListener(DBWorkerDelegate delegate){
        this.delegate = delegate;
    }

    public DBWorker(ConferenceType confType){
        this.confType = confType;
    }
    public DBWorker(){}//default constructor

    @Override
    protected JSONObject doInBackground(Void... params) {
        try {
            URL url = null;
            switch(confType){
                case COUPLES:
                    break;
                case EVOLUTION:
                    url = new URL(this.EVOLUTION_SCHEDULE_URL);
                    break;
                default:
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
            return result;


        }catch(MalformedURLException ex){
            Log.e(TAG, ex.getMessage());
        }catch(IOException ex){
            Log.e(TAG, ex.getMessage());
        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
        this.delegate.didFinishTask(true, s);
    }
    private String readURLReturnData(HttpURLConnection connection){
        String result = null;
        StringBuffer sb = new StringBuffer();
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
