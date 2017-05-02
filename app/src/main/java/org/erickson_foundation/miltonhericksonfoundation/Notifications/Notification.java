package org.erickson_foundation.miltonhericksonfoundation.Notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import org.erickson_foundation.miltonhericksonfoundation.R;

/**
 * Created by User on 5/1/2017.
 */

public class Notification {
    private static NotificationCompat.Builder mBuilder;

    public static void createNotification(String message, Context ctx){
        mBuilder = new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.ic_stat_error_outline)
                        .setContentTitle("Notification Title")
                        .setContentText("Notification Text");

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(715, mBuilder.build());


    }
}
