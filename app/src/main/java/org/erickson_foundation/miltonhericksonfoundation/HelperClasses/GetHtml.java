package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;

import android.os.AsyncTask;
import android.util.Log;

import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by User on 5/10/2017.
 */

public class GetHtml extends AsyncTask<String, Void, JSONObject> {
    private static final String TAG = "GetHtml";
    private DBWorkerDelegate delegate;
    public void setOnFinishedListener(DBWorkerDelegate delegate){
        this.delegate = delegate;
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String output = "", currentLine;
            while((currentLine = in.readLine()) != null){
                output += currentLine;
            }
            int x = 9;
            Log.i(TAG, output);
            return new JSONObject(output);


        }catch (Exception ex){}
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
        this.delegate.didFinishTask(s);
    }
}
