package org.erickson_foundation.miltonhericksonfoundation.HelperClasses;
import org.erickson_foundation.miltonhericksonfoundation.Conference.ConferenceType;

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
    public static final String SPEAKER_BUNDLE_KEY = "speaker";
    public static final String SHARED_PREFS_FILE_NAME = "mhef_shared_prefs";
    public static final String FAVORITES_ARRAY_SHARED_PREFS_KEY = "favoritesArray";
    public static final String FAVORITES_DELIMITER = ";";
    public static final String IS_FAVORITES_TAB_SELECTED = "favoritesTabSelected";
    public static final String IS_FILTER_TAB_SELECTED = "filtersTab";
    public static final int NO_FAVORITES_TABLE_ROW_TAG = 10001;
    public static final String MAP_ITEM_BUNDLE_KEY = "mapItem";
    public static final String EVOLUTION_SCHEDULE_URL = "http://www.evolutionofpsychotherapy.com/wp-content/uploads/evolutionConference1.json";

    public static final String MAIN_EVO_URL = "https://www.evolutionofpsychotherapy.com";
    public static final String WP_CONTENT = "/wp-content/";


    //TODO: change this to false in final release
    public static final boolean DEBUG = true;           //true only during development


}
