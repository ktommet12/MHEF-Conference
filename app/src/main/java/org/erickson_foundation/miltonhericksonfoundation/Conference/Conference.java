package org.erickson_foundation.miltonhericksonfoundation.Conference;

import android.content.Context;
import android.util.Log;

import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AssetLoader;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.HelperFunctions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kyle Tommet(erickson-foundation.org) on 3/21/2017.
 */

public class Conference {
    private String mTitle;                              //title for the Conference
    private String mShortTitle;                         //short title for Conference, i.e. "Evolution" for Evolution of Psychotherapy
    private String[] mDates;                            //String array for the dates of the conference
    private ConferenceType mConfType;                   //Type of Conference, i.e. EVOLUTION, COUPLES, etc
    private HashMap<String, ArrayList<ConferenceTalk>> days;     //maps a day to an array of talks
    private HashMap<String, ArrayList<String>> room_data_set;
    private ArrayList<Speaker> allSpeakers;
    private ArrayList<String> roomKeys;
    private int numFavoritedTalks;
    private final String TAG = "Conference";
    private String syllabusUrl = "";


    public Conference(JSONObject confContents, ArrayList<String> favTalks, Context ctx) throws JSONException{
        days = new HashMap<>();
        room_data_set = new HashMap<>();
        allSpeakers = new ArrayList<Speaker>();
        roomKeys = new ArrayList<>();

        JSONObject conferenceIds = new JSONObject(); //this is only used to make sure the the ids that are generated
        // for each talk and speaker are unique, will only live in the context of this constructor

        conferenceIds.put("talk_ids", new ArrayList<Integer>());
        conferenceIds.put("speaker_ids", new ArrayList<Integer>());


        numFavoritedTalks = favTalks.size();

        String biosString = AssetLoader.loadSpeakerBios(ctx);
        JSONObject biosJSON = new JSONObject(biosString);

        mTitle = confContents.getString("conference_full_name");
        mShortTitle = confContents.getString("conference_short_name");

        JSONObject tempRoomDataSet = confContents.getJSONObject("map_data_set");
        JSONArray picStrings = confContents.getJSONArray("pic_strings");
        JSONObject things = new JSONObject();
        for(int i = 0; i < picStrings.length(); i++){
            String picString = (String)picStrings.get(i);
            JSONArray tempRoomsJsonArr = (JSONArray) tempRoomDataSet.get(picString);
            ArrayList<String> rooms = new ArrayList<>();
            for(int j = 0; j < tempRoomsJsonArr.length(); j++){
                rooms.add(tempRoomsJsonArr.get(j).toString());
            }
            this.room_data_set.put(picString, rooms);
            Log.i(TAG, tempRoomsJsonArr.toString());
        }
        this.room_data_set.put("acc_north_100", new ArrayList<String>());
        this.room_data_set.put("campus_map", new ArrayList<String>());

        switch(mShortTitle){
            case "Evolution":
                mConfType = ConferenceType.EVOLUTION;
                break;
            case "Couples":
                mConfType = ConferenceType.COUPLES;
                break;
            case "Brief":
                mConfType = ConferenceType.BRIEF;
                break;
            default:
                mConfType = ConferenceType.DEFAULT;
        }
        JSONArray temp = confContents.getJSONArray("dates");
        mDates = new String[temp.length()];
        for(int i = 0; i < temp.length(); i++){
            mDates[i] = (String)temp.get(i);
        }
        JSONObject daysJSON = confContents.getJSONObject("days");

        this.syllabusUrl = confContents.getString("syllabus-url");

        for(int i = 0; i < daysJSON.length(); i++){
            ArrayList<ConferenceTalk> talks = new ArrayList<>();
            JSONObject dayObj = daysJSON.getJSONObject(mDates[i]);
            JSONArray tempArr = dayObj.getJSONArray("talks");
            for(int j = 0; j < tempArr.length(); j++){
                JSONObject talk = tempArr.getJSONObject(j);
                String title = talk.getString("title");
                String time = talk.getString("time_slot");
                String description = talk.getString("description");
                String name = talk.getString("speaker_name");
                String nameOnly = talk.getString("speaker_name_only");
                String tempRoom = talk.getString("room");

                //Converting the Conference Room into a ConferenceRoom object by breaking the  string into its pieces and creating the object from that
                String[] roomArr = tempRoom.split(" - ");
                ConferenceRoom room;
                Log.i(TAG, tempRoom);
                if (roomArr.length != 1){
                    this.addRoomKey(roomArr[0]);
                    room = new ConferenceRoom(roomArr[0], roomArr[1]);
                }
                else{
                    room = new ConferenceRoom("TBD", "TBD");
                }

                String[] tempFullName = name.split(";");
                String[] tempShortName = nameOnly.split(";");
                String shortTitle = talk.getString("short_title");
                TalkCategory category = this.convertToCategory(talk.getString("talk_category"));

                ArrayList<Speaker> speakers = new ArrayList<>();
                for(int k = 0; k < tempFullName.length; k++){
                    String shortName = tempShortName[k].trim(), fullName = tempFullName[k].trim();
                    Speaker existingSpeaker = checkIfSpeakerInArray(shortName);

                    if(existingSpeaker != null){
                        speakers.add(existingSpeaker);
                    }else {
                        String tempBioString = HelperFunctions.toBioString(shortName);
                        String speakerBio = biosJSON.optString(tempBioString, "");
                        ArrayList currentSpeakerIds = (ArrayList) conferenceIds.get("speaker_ids");
                        Speaker newSpeaker = new Speaker(fullName, shortName, speakerBio, currentSpeakerIds);
                        currentSpeakerIds.add(newSpeaker.getSpeakerID());
                        conferenceIds.put("speaker_ids", currentSpeakerIds);
                        speakers.add(newSpeaker);
                        this.allSpeakers.add(newSpeaker);
                    }
                }
                speakers.trimToSize();
                Speaker[] tempSpeakers = new Speaker[speakers.size()];
                int index = 0;
                for(Speaker z : speakers){
                    tempSpeakers[index] = z;
                    index++;
                }
                ConferenceTalk tempTalk = new ConferenceTalk(title, shortTitle, time, room, description, tempSpeakers, mDates[i], category);
                tempTalk.setFullNameString(name);
                tempTalk.setShortNameString(nameOnly);
                if(favTalks.contains(tempTalk.getTitle()))
                    tempTalk.addToFavorites();
                talks.add(tempTalk);
            }
            days.put(mDates[i], talks);
        }
        this.roomKeys.add("ACC North 100");
        this.roomKeys.add("Campus Map");
    }
    //increments a counter that is keeping track of the total number of talks that they currently have favorited
    public void incrementFavoriteTalkCount(){
        this.numFavoritedTalks++;
    }

    //decrements a counter that is keeping track of the total number of talks that they currently have favorited
    public void decrementFavoriteTalkCount(){
        this.numFavoritedTalks--;
    }

    //returns the number of favorited talks
    public int getNumFavoritedTalks(){
        return this.numFavoritedTalks;
    }

    //returns the Conference title i.e. "Evolution of Psychotherapy"
    public String getTitle() {
        return mTitle;
    }

    //this returns the Conference dates as a [String]
    public String[] getDates() {
        return mDates;
    }

    //returns the url associated with the syllabus for the Conference
    public String getSyllabusUrl(){return this.syllabusUrl;}

    public ConferenceType getConfType() {
        return mConfType;
    }
    //returns the data set of the conference talk rooms
    public HashMap<String, ArrayList<String>> getRoomDataSet(){
        return this.room_data_set;
    }
    //gets the pic string associated with a talk room
    public String getRoomPicString(String room){
        String roomPicString = "";
        for(String key: room_data_set.keySet()){
            ArrayList<String> tempRooms = this.room_data_set.get(key);
            for(String currentRoom : tempRooms){
                if (currentRoom.equals(room)){
                    return key;
                }
            }
        }
        return roomPicString;
    }
    private void addRoomKey(String key){
        boolean alreadyInList = false;
        for(String tempKey : roomKeys){
            if(tempKey.equals(key)){
                alreadyInList = true;
            }
        }
        if(!alreadyInList){
            this.roomKeys.add(key);
        }
    }
    public ArrayList<String> getRoomKeys(){
        return this.roomKeys;
    }
    public int getNumFavoritedTalksForDay(String date){
        ArrayList<ConferenceTalk> talks = this.days.get(date);
        int numFavorites = 0;
        for(ConferenceTalk talk : talks){
            if(talk.isTalkFavorited()) {numFavorites++;}
        }
        return numFavorites;
    }
    public void addToFavorites(ConferenceTalk talk){
        talk.addToFavorites();
    }
    public void removeFromFavorites(ConferenceTalk talk){
        talk.removeFromFavorites();
    }
    public ArrayList<ConferenceTalk> getConferenceDayTalks(String dayName, boolean isFavorited){
        ArrayList<ConferenceTalk> tempTalks = days.get(dayName);
        return (tempTalks == null)? new ArrayList<ConferenceTalk>() : tempTalks;
    }
    public ArrayList<ConferenceTalk> getAllFavoritedTalksForDay(String date){
        ArrayList<ConferenceTalk> tempTalks = days.get(date), returnTalks;
        returnTalks = new ArrayList<>();
        for(ConferenceTalk talk : tempTalks){
            if(talk.isTalkFavorited()){
                returnTalks.add(talk);
            }
        }
        return returnTalks;
    }
    public ArrayList<Speaker> getAllSpeakers(){
        return this.allSpeakers;
    }
    public Speaker getSpeakerById(int id){
        for(Speaker speaker : this.allSpeakers){
            if(speaker.getSpeakerID() == id) return speaker;
        }
        return null;
    }
    public ConferenceTalk getTalkById(int id){
        for(int i = 0; i < days.size(); i++){
            ArrayList<ConferenceTalk> currentDay = days.get(mDates[i]);
            for(int j = 0; j < currentDay.size(); j++){
                if(currentDay.get(j).getTalkID() == id){
                    return currentDay.get(j);
                }
            }
        }
        return null;
    }
    public ConferenceTalk getTalkByTitle(String title){
        ConferenceTalk talk = null;
        boolean talkFound = false;
        for (String date : this.mDates) {
            ArrayList<ConferenceTalk> talks = this.days.get(date);
            for (ConferenceTalk currentTalk : talks) {
                if (currentTalk.getTitle().equals(title)) {
                    talk = currentTalk;
                    talkFound = true;
                    break;
                }
            }
            if(talkFound) break;
        }
        return talk;
    }
    public int getSpeakerTabPosition(String day){
        for(int i = 0; i < mDates.length; i++){
            if(mDates[i].equals(day)){
                return i;
            }
        }
        return 0;
    }
    public ArrayList<ConferenceTalk> getAllTalksBySpeaker(Speaker speaker){
        ArrayList<ConferenceTalk> talks = new ArrayList<>();
        for(int i = 0; i < days.size(); i++){
            ArrayList<ConferenceTalk> currentDay = this.getConferenceDayTalks(mDates[i], false);
            for(int j = 0; j < currentDay.size(); j++){
                Speaker[] tempSpeakers = currentDay.get(j).getAllSpeakers();
                for(Speaker currentSpeaker : tempSpeakers){
                    if(currentSpeaker.getFullName().equals(speaker.getFullName())){
                        talks.add(currentDay.get(j));
                    }
                }
            }
        }
        return talks;
    }
    private TalkCategory convertToCategory(String cat) {
        TalkCategory category;
        switch(cat.toLowerCase()){
            case "pc":
                category = TalkCategory.PRE_CONFERENCE;
                break;
            case "le":
                category = TalkCategory.LAW_AND_ETHICS;
                break;
            case "k":
                category = TalkCategory.KEYNOTE;
                break;
            case "ws":
                category = TalkCategory.WORKSHOP;
                break;
            case "gd":
                category = TalkCategory.GREAT_DEBATES;
                break;
            case "cd":
                category = TalkCategory.CLINICAL_DEMONSTRATION;
                break;
            case "cdd":
                category = TalkCategory.CLINICAL_DISCUSSANT;
                break;
            case "tp":
                category = TalkCategory.TOPICAL;
                break;
            case "ch":
                category = TalkCategory.CONVERSATION_HOUR;
                break;
            case "sp":
                category = TalkCategory.SPEECH;
                break;
            case "spd":
                category = TalkCategory.SPEECH_DISCUSSANT;
                break;
            case "mc":
                category = TalkCategory.MASTER_CLASS;
                break;
            default:
                category = TalkCategory.DEFAULT;
        }
        return category;
    }
    private Speaker checkIfSpeakerInArray(String name){
        boolean alreadyInList = false;
        for(Speaker speaker : this.allSpeakers) {
            if (speaker.getShortName().equals(name)) {
                return speaker;
            }
        }
        return null;
    }
}
