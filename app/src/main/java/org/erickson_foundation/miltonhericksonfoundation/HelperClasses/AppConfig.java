package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;

import org.erickson_foundation.miltonhericksonfoundation.ConferenceType;

/**
 * Created by ktomm on 3/14/2017.
 */

public final class AppConfig {
    private AppConfig(){}
    public static final String CONFERENCE_NAME_INTENT = "ConferenceName";
    public static final String CONFERENCE_CONTENTS_JSON = "ConferenceContents";
    public static final String CONFERENCE_TYPE_STRING = "confType";
    public static final String YOUTUBE_API_KEY = "AIzaSyBw7PJ_A6r3T6S4UpFzkUMDar5A2Q56g-o";
    public static final String SPEAKER_NAME_KEY = "speakerName";
    public static final int MORE_INFO_TALK_FRAGMENT = 100;
    public static final String TALK_ID_BUNDLE_KEY = "talkID";
    public static final ConferenceType DEFAULT_CONFERENCE = ConferenceType.EVOLUTION;
    public static final String WEB_URL_KEY = "webURL";
    public static final String SCHEDULE_TAB_POS = "tabPosition";
    public static final String WEB_JAVASCRIPT_ENABLED_KEY = "javascriptEnabled";
    //TODO: change this to false in final release
    public static final boolean DEBUG = true;


}
