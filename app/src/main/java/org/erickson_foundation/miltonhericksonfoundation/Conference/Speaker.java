package org.erickson_foundation.miltonhericksonfoundation.Conference;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by User on 5/18/2017.
 */

public class Speaker implements Serializable{
    private String mFullName, mBio, mShortName;
    private int speakerID;

    public Speaker(String name, String fullName, String bio, ArrayList<Integer> currentIds){
        this.mShortName = fullName;
        this.mFullName = name;
        this.mBio = bio;
        do {
            speakerID = (int) (Math.random() * 999 + 1);
            Log.i("Speaker", "Picked an ID for " + this.mShortName);
        }while (currentIds.contains((Integer)speakerID));
    }
    public String getFullName(){
        return this.mFullName;
    }
    public String getShortName(){
        return this.mShortName;
    }
    public String getBio(){
        return this.mBio;
    }
    public int getSpeakerID(){
        return this.speakerID;
    }

    public static Comparator<Speaker> SpeakerNameComparator = new Comparator<Speaker>(){
        public int compare(Speaker one, Speaker two){
            String[] name1NameArr = one.getShortName().split(" ");
            String[] name2NameArr = two.getShortName().split(" ");

            String name1LastName = name1NameArr[name1NameArr.length-1];
            String name2LastName = name2NameArr[name2NameArr.length-1];

            return name1LastName.compareTo(name2LastName);
        };
    };
}
