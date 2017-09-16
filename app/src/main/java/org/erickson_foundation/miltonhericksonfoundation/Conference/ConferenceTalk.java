package org.erickson_foundation.miltonhericksonfoundation.Conference;

import android.util.Log;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;

/**
 * Created by User on 3/21/2017.
 */

public class ConferenceTalk {
    private static final String TAG = "ConferenceTalk";
    private String mTitle, mShortTitle, mTimeSlot, mDescription, mDay, mFullNameString, mShortNameString;
    private Speaker[] mSpeaker;
    private TalkCategory mCategory;
    private boolean mIsFavourite;
    private int mNumSpeakers;
    private int mID;


    public ConferenceTalk(String title, String shortTitle, String time, String description, Speaker[] speakers, String day, TalkCategory category){
        mTimeSlot = time;
        mTitle = title;
        mDescription = description;
        mDay = day;
        mSpeaker = speakers;
        mShortTitle = shortTitle;
        mID = (int) (Math.random() * 999 + 1);
        mIsFavourite = false;
        mNumSpeakers = speakers.length;
        mCategory = category;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getShortTitle(){
        return mShortTitle;
    }
    public String getTimeSlot() {
        return mTimeSlot;
    }
    public String getTalkDay(){
        return mDay;
    }
    public TalkCategory getTalkCategory(){
        return mCategory;
    }
    public String getDescription() {
        return mDescription;
    }
    public void setFullNameString(String str){
        this.mFullNameString = str;
    }
    public String getFullNameString(){
        return this.mFullNameString;
    }
    public void setShortNameString(String str){
        this.mShortNameString = str;
    }
    public String getShortNameString(){
        return this.mShortNameString;
    }
    public int getTalkID(){
        return this.mID;
    }
    public Speaker getSpeakerByName(String name){
        for(int i = 0; i < mSpeaker.length; i++){
            if(mSpeaker[i].getFullName().equals(name)){
                return mSpeaker[i];
            }
        }
        return null;
    }
    public Speaker getSpeakerById(int id){
        for(int i = 0; i < mSpeaker.length; i++){
            if(mSpeaker[i].getSpeakerID() == id){
                return mSpeaker[i];
            }
        }
        return null;
    }
    public Speaker[] getAllSpeakers(){
        return mSpeaker;
    }
    public boolean isTalkFavorited(){
        return mIsFavourite;
    }
    public int getNumSpeakers() {
        return mNumSpeakers;
    }
    public void toggleFavorite(){
        mIsFavourite = !mIsFavourite;
    }
}
