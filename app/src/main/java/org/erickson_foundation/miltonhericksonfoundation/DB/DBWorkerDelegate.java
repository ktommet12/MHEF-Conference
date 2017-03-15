package org.erickson_foundation.miltonhericksonfoundation.DB;

import org.json.JSONObject;

/**
 * Created by Kyle Tommet on 3/9/2017.
 * Defines the Listener Interface that a DB Connection will use to signify that it has finished its task
 * results are returned as JSONObject
 */

public interface DBWorkerDelegate {
    public void didFinishTask(JSONObject jsonObject);
}
