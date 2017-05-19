package org.erickson_foundation.miltonhericksonfoundation.Conference;

import android.content.Context;
import android.util.Log;

import org.erickson_foundation.miltonhericksonfoundation.ConferenceType;
import org.erickson_foundation.miltonhericksonfoundation.HelperClasses.AppConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle Tommet(erickson-foundation.org) on 3/21/2017.
 */

public class Conference {
    private String mTitle;                              //title for the Conference
    private String mShortTitle;                         //short title for Conference, i.e. "Evolution" for Evolution of Psychotherapy
    private String[] mDates;                            //String array for the dates of the conference
    private ConferenceType mConfType;                   //Type of Conference, i.e. EVOLUTION, COUPLES, etc
    private HashMap<String, ArrayList<ConferenceTalk>> days;     //maps a day to an array of talks
    private final String TAG = "Conference";

    public Conference(JSONObject confContents) throws JSONException{
        days = new HashMap<String, ArrayList<ConferenceTalk>>();


        mTitle = confContents.getString("conference_full_name");
        mShortTitle = confContents.getString("conference_short_name");

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
                String shortTitle = talk.getString("short_title");

                Speaker tempSpeaker = new Speaker(nameOnly, name, "");

                talks.add(new ConferenceTalk(title, shortTitle, time, description, tempSpeaker, mDates[i]));
            }
            days.put(mDates[i], talks);
        }
    }
    public String getTitle() {
        return mTitle;
    }
    public String[] getDates() {
        return mDates;
    }
    public ConferenceType getConfType() {
        return mConfType;
    }
    public ArrayList<ConferenceTalk> getConferenceDayTalks(String dayName){
        //verifies there is something stored for the date requested, if not returns empty ArrayList
        ArrayList<ConferenceTalk> tempTalks = days.get(dayName);
        return (tempTalks == null)? new ArrayList<ConferenceTalk>() : tempTalks;
    }
    public ArrayList<Speaker> getAllSpeakers(){
        ArrayList<Speaker> names = new ArrayList<>();
        for(int i = 0; i < days.size(); i++){
            ArrayList<ConferenceTalk> currentDay = days.get(mDates[i]);
            for(int j = 0; j < currentDay.size(); j++){
                if(names.contains(currentDay.get(j).getSpeaker())){
                    Log.i(TAG, "name already in list");
                }
                names.add(currentDay.get(j).getSpeaker());
            }
        }
        names.trimToSize();
        return names;
    }
    public Speaker getSpeakerById(int id){
        ArrayList<Speaker> speakers = this.getAllSpeakers();
        for(Speaker speaker : speakers){
            if(speaker.getSpeakerID() == id) return speaker;
        }
        return null;
    }
    public ConferenceTalk locateTalkById(int id){
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
            ArrayList<ConferenceTalk> currentDay = this.getConferenceDayTalks(mDates[i]);
            for(int j = 0; j < currentDay.size(); j++){
                if(currentDay.get(j).getSpeaker().getFullName().equals(speaker.getFullName())){
                    talks.add(currentDay.get(j));
                }
            }
        }
        return talks;
    }
}
