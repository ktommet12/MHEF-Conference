package org.erickson_foundation.miltonhericksonfoundation.Conference;

import java.io.Serializable;

/**
 * Created by User on 5/18/2017.
 */

public class Speaker implements Serializable{
    private String mName, mFullName, mBio;
    private final int speakerID;

    public Speaker(String name, String fullName, String bio){
        this.mName = name;
        this.mFullName = fullName;
        this.mBio = bio;
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
}
