package org.erickson_foundation.miltonhericksonfoundation;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 3/9/2017.
 */

public class DBWorker extends AsyncTask<DBWorker, Void, JSONObject>{
    private final String TAG = "DBWorker";
    private DBWorkerDelegate delegate;

    public void setOnFinishedListener(DBWorkerDelegate delegate){
        this.delegate = delegate;
    }



    @Override
    protected JSONObject doInBackground(DBWorker... params) {
        try {
            URL url = new URL("http://www.evolutionofpsychotherapy.com/wp-content/uploads/test.json");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            JSONObject result = new JSONObject(readURLReturnData(connection));

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
