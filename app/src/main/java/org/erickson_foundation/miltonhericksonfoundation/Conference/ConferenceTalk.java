package org.erickson_foundation.miltonhericksonfoundation.Conference;

import android.content.Context;
import android.util.Log;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;

/**
 * Created by User on 3/21/2017.
 */

public class ConferenceTalk {
    private static final String TAG = "ConferenceTalk";
    private String mTitle, mTimeSlot, mDescription, mSpeakerName, mDay, mSpeakerNameOnly;
    private Speaker mSpeaker;
    private boolean mIsFavorited;
    private int mID;

    public ConferenceTalk(String title, String time, String description, Speaker speaker, String day){
        mTimeSlot = time;
        mTitle = title;
        mDescription = description;
        mDay = day;
        mSpeaker = speaker;
        mID = (int) (Math.random() * 999 + 1);
        mIsFavorited = false;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getTimeSlot() {
        return mTimeSlot;
    }
    public String getTalkDay(){
        return mDay;
    }
    public String getDescription() {
        return mDescription;
    }

    public String getFullSpeakerName(){
        return mSpeaker.getFullName();
    }
    public int getTalkID(){
        return this.mID;
    }
    public Speaker getSpeaker(){
        return mSpeaker;
    }
    public boolean isTalkFavorited(){
        return mIsFavorited;
    }
    public void toggleFavorite(){
        mIsFavorited = !mIsFavorited;
    }
    public String getSpeakerNameOnly(){
        return mSpeakerNameOnly;
    }
}
