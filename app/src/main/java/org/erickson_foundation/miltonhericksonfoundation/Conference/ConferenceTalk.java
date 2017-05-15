package org.erickson_foundation.miltonhericksonfoundation.Conference;

/**
 * Created by User on 3/21/2017.
 */

public class ConferenceTalk {
    private String mTitle, mTimeSlot, mDescription, mSpeakerName;
    private boolean mIsFavorited;
    private int mID;

    public ConferenceTalk(String title, String time, String description, String name){
        mTimeSlot = time;
        mTitle = title;
        mDescription = description;
        mSpeakerName = name;
        mID = (int) (Math.random() * 999 + 1);
        mIsFavorited = false;
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

    public String getSpeakerName(){
        return mSpeakerName;
    }
    public int getTalkID(){
        return this.mID;
    }
    public void toggleFavorite(){
        mIsFavorited = !mIsFavorited;
    }
}
