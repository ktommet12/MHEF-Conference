package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kyle Tommet on 5/22/2017.
 */

public class MhefSharedPrefs {
    private static String SHARED_PREFS_FILE_NAME = AppConfig.SHARED_PREFS_FILE_NAME;


    private static SharedPreferences getPrefs (Context context)
    {
        return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }
    public static void saveString(Context ctx, String key, String value){
        getPrefs(ctx).edit().putString(key, value).commit();
    }
    public static String getString(Context context, String key, String defaultString)
    {
        return getPrefs(context).getString(key,defaultString);
    }

    public static void deleteEverything(Context context, String key)
    {
        getPrefs(context).edit().remove(key).commit();
    }
    public static void saveStringArray(Context ctx, String key, ArrayList<String> val){
        StringBuilder sb = new StringBuilder();
        val.remove("");
        for(int i = 0; i < val.size(); i++){
            sb.append(val.get(i));
            if(i != val.size() - 1)
                sb.append(AppConfig.FAVORITES_DELIMITER);
        }
        String compressedString = sb.toString();
        getPrefs(ctx).edit().putString(key, compressedString).commit();
    }
    public static ArrayList<String> getStringArray(Context ctx, String key){
        String temp = getPrefs(ctx).getString(AppConfig.FAVORITES_ARRAY_SHARED_PREFS_KEY,"");
        if(temp.equals("")){
            return new ArrayList<>();
        }
        else return new ArrayList<>( Arrays.asList(temp.split(AppConfig.FAVORITES_DELIMITER)));
    }
}
