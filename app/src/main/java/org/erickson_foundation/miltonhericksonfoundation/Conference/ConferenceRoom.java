package org.erickson_foundation.miltonhericksonfoundation.Conference;

/**
 * Created by Kyle Tommet on 10/4/2017.
 */

public class ConferenceRoom {
    private String mRoom, mSubRoom;
    public ConferenceRoom(String room, String subRoom){
        this.mRoom = room;
        this.mSubRoom = subRoom;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String mRoom) {
        this.mRoom = mRoom;
    }

    public String getSubRoom() {
        return mSubRoom;
    }

    public void setSubRoom(String mSubRoom) {
        this.mSubRoom = mSubRoom;
    }
}
