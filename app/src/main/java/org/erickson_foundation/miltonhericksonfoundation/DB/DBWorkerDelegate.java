package org.erickson_foundation.miltonhericksonfoundation.DB;

import org.json.JSONObject;

/**
 * Created by User on 3/9/2017.
 */

public interface DBWorkerDelegate {
    public void didFinishTask(boolean wasASuccess, JSONObject jsonObject);
}
