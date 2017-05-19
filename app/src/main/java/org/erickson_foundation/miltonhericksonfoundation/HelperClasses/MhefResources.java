package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by User on 5/18/2017.
 */

public final class MhefResources {
    public static String TAG = "MhefResources";
    public static int getImageResource(Context ctx, String name){
        if(!name.equals(null)){
            ArrayList<String> nameArr = new ArrayList<>( Arrays.asList(name.split(" ")));
            if(nameArr.size() > 1) {
                nameArr.remove("");
                String firstName = nameArr.get(0).toLowerCase();
                String lastName = nameArr.get(1).toLowerCase();
                String nameSrc = firstName + "_" + lastName;
                int resID = MhefResources.getResource(nameSrc, "drawable", ctx);
                if (AppConfig.DEBUG) {
                    Log.i(TAG, "NameSrc: " + nameSrc);
                    Log.i(TAG, "Res ID: " + resID);
                }
                return resID;
            }
        }
        return -1;
    }
    public static int getStringResource(Context ctx, String name){
        if(!name.equals(null)){
            ArrayList<String> nameArr = new ArrayList<String>( Arrays.asList(name.split(" ")));
            if(nameArr.size() > 1){
                nameArr.remove("");
                String firstName = nameArr.get(0).toLowerCase();
                String lastName = nameArr.get(1).toLowerCase();
                String nameStr = firstName + "_" + lastName + "_bio";
                int bioID = MhefResources.getResource(nameStr, "string", ctx);
                if (AppConfig.DEBUG) {
                    Log.i(TAG, "NameSrc: " + nameStr);
                    Log.i(TAG, "Res ID: " + bioID);
                }
                return bioID;
            }
        }
        return -1;
    }

    private static int getResource(String name, String folder, Context ctx){
        return ctx.getResources().getIdentifier(name, folder, ctx.getPackageName());
    }
}
