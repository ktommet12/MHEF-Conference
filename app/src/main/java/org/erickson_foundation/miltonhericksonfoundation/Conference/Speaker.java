package org.erickson_foundation.miltonhericksonfoundation.Conference;

import java.io.Serializable;

/**
 * Created by User on 5/18/2017.
 */

public class Speaker implements Serializable{
    private String mName, mFullName, mBio;
    private String[] mNamesArray, mFullNamesArray;
    private final int speakerID;
    private int mNumSpeakers;

    public Speaker(String name, String fullName, String bio){
        mNamesArray = name.split(";");
        this.mName = name;
        mFullNamesArray = fullName.split(";");
        this.mFullName = fullName;
        this.mBio = bio;
        mNumSpeakers = mNamesArray.length;
        speakerID = (int) (Math.random()* 999 + 1);
    }
    public String getFullName(){
        return this.mFullName;
    }
    public String getShortName(){
        return this.mName;
    }
    public String getBio(){
        return this.mBio;
    }
    public int getSpeakerID(){
        return this.speakerID;
    }
    //returns the array of full names including the speakers credentials
    public String[] getFullNames(){
        return mFullNamesArray;
    }
    //returns the array of names without any credentials
    public String[] getShortNames(){
        return mNamesArray;
    }
    public int getNumSpeakers(){
        return mNumSpeakers;
    }
}
