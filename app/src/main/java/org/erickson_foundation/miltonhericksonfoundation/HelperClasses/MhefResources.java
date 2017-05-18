package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;

import android.content.Context;
import android.util.Log;

/**
 * Created by User on 5/18/2017.
 */

public final class MhefResources {
    public static String TAG = "MhefResources";
    public static int getImageResource(Context ctx, String name){
        if(!name.equals(null)){
            String[] nameArr = name.split(" ");
            if(nameArr.length > 1) {
                String nameSrc = nameArr[0].toLowerCase() + "_" + nameArr[1].toLowerCase();
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
            String[] nameArr = name.split(" ");
            if(nameArr.length > 1){
                String nameStr = nameArr[0] + "_"+nameArr[1]+"_bio";
                return MhefResources.getResource(nameStr, "string", ctx);
            }
        }
        return -1;
    }

    private static int getResource(String name, String folder, Context ctx){
        return ctx.getResources().getIdentifier(name, folder, ctx.getPackageName());
    }
}
