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
                String nameSrc = HelperFunctions.toPicString(name);
                int resID = MhefResources.getResource(nameSrc, "drawable", ctx);
                return resID;
            }
        }
        return 0;
    }

    private static int getResource(String name, String folder, Context ctx){
        return ctx.getResources().getIdentifier(name, folder, ctx.getPackageName());
    }
}
