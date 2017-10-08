package org.erickson_foundation.miltonhericksonfoundation.Conference;

/**
 * Created by Kyle Tommet on 10/4/2017.
 */

public class ConferenceRoom {
    private String mRoom, mSubRoom, mFullRoomString;
    public ConferenceRoom(String room, String subRoom){
        this.mRoom = room;
        this.mSubRoom = subRoom;
        this.mFullRoomString = room + " - " + subRoom;
    }

    public String getRoom() {
        return mRoom;
    }
    public String getSubRoom() {
        return mSubRoom;
    }
    public String getFullRoom(){
        return this.mFullRoomString;
    }
}
