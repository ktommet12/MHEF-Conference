package org.erickson_foundation.miltonhericksonfoundation.Twitter;

/**
 * Created by User on 5/1/2017.
 */

public class Tweet {
    private String posterName, postBody;
    public Tweet(String name, String body){
        this.postBody = body;
        this.posterName = name;
    }

    public String getPosterName(){
        return this.posterName;
    }
    public String getPostBody(){
        return this.postBody;
    }
}
