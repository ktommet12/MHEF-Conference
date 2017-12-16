package org.erickson_foundation.miltonhericksonfoundation.Conference;

/**
 * Created by Kyle Tommet on 6/5/2017.
 */

public enum TalkCategory {
    WORKSHOP("Workshop"),
    KEYNOTE("Keynote"),
    DIALOGUE("Dialogue"),
    PRE_CONFERENCE("Pre-Conference"),
    LAW_AND_ETHICS("Law & Ethics"),
    GREAT_DEBATES("Great Debates"),
    CLINICAL_DEMONSTRATION("Clinical Demonstration"),
    CLINICAL_DISCUSSANT("Clinical Discussant"),
    TOPICAL("Topical Panel"),
    CONVERSATION_HOUR("Conversation Hour"),
    SPEECH("Speech"),
    SPEECH_DISCUSSANT("Speech Discussant"),
    MASTER_CLASS("Master Class"),
    DEFAULT("Other");

    private final String name;
    TalkCategory(String name){
        this.name = name;
    }
    public String toString(){
        return this.name;
    }
}
