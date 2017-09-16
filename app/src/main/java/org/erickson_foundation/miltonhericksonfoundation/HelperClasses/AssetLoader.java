package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;

        import android.content.Context;
        import android.util.Log;

        import java.io.BufferedInputStream;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;

/**
 * Created by Kyle Tommet on 9/6/2017.
 */

public class AssetLoader {
    private static final String TAG = "AssetLoader";

    public static String loadSpeakerBios(Context ctx){
        String json  = null;
        StringBuilder sb = new StringBuilder();
        try{
            InputStream is = ctx.getAssets().open("test.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while((inputLine = br.readLine()) != null){
                sb.append(inputLine);
            }
            json = sb.toString();
            is.close();
        }
        catch(IOException ex){
            Log.i(TAG, "Failed: " + ex.getMessage());
        }
        return json;
    }
}
