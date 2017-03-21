package org.erickson_foundation.miltonhericksonfoundation.Conference;

/**
 * Created by User on 3/21/2017.
 */

public class ConferenceTalk {
    public String mTitle, mTimeSlot, mDescription;

    public ConferenceTalk(String title, String time, String description){
        mTimeSlot = time;
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getTimeSlot() {
        return mTimeSlot;
    }

    public String getDescription() {
        return mDescription;
    }
}
