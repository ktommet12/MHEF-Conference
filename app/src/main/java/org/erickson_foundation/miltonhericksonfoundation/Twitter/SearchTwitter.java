package org.erickson_foundation.miltonhericksonfoundation.Twitter;

import android.os.AsyncTask;
import android.util.Log;

import org.erickson_foundation.miltonhericksonfoundation.DB.DBWorkerDelegate;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by User on 5/1/2017.
 */

public class SearchTwitter extends AsyncTask<String, Void, Void> {
    private DBWorkerDelegate delegate;
    private final String TAG = "SearchTwitter";
    private static Twitter twitter;
    public void setOnFinishedListener(DBWorkerDelegate delegate){
        this.delegate = delegate;
    }
    @Override
    protected Void doInBackground(String... params) {
        try{
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setOAuthAuthenticationURL("https://api.twitter.com/oauth/request_token");
            cb.setOAuthAccessTokenURL("https://api.twitter.com/oauth/access_token");
            cb.setOAuthAuthorizationURL("https://api.twitter.com/oauth/authorize");
            cb.setOAuthRequestTokenURL("https://api.twitter.com/oauth/request_token");
            cb.setRestBaseURL("https://api.twitter.com/1.1/");
            cb.setApplicationOnlyAuthEnabled(true);
            cb.setOAuthConsumerKey(TwitterConfig.TWITTER_CONSUMER_KEY);
            cb.setOAuthConsumerSecret(TwitterConfig.TWITTER_CONSUMER_SECRET);

            Log.i(TAG, cb.toString());
            ///*

            OAuth2Token token = new TwitterFactory(cb.build()).getInstance().getOAuth2Token();

            cb = new ConfigurationBuilder();
            cb.setOAuthAuthenticationURL("https://api.twitter.com/oauth/request_token");
            cb.setOAuthAccessTokenURL("https://api.twitter.com/oauth/access_token");
            cb.setOAuthAuthorizationURL("https://api.twitter.com/oauth/authorize");
            cb.setOAuthRequestTokenURL("https://api.twitter.com/oauth/request_token");
            cb.setRestBaseURL("https://api.twitter.com/1.1/");
            cb.setApplicationOnlyAuthEnabled(true);
            cb.setOAuthConsumerKey(TwitterConfig.TWITTER_CONSUMER_KEY);
            cb.setOAuthConsumerSecret(TwitterConfig.TWITTER_CONSUMER_SECRET);

            cb.setOAuth2TokenType(token.getTokenType());
            cb.setOAuth2AccessToken(token.getAccessToken());

            twitter = new TwitterFactory(cb.build()).getInstance();

            Query query = new Query(params[0]);

            query.setCount(25);
            QueryResult results;

            results = twitter.search(query);

            List<twitter4j.Status> tweets = results.getTweets();
            Log.i(TAG, tweets.toString());//*/
        }catch(TwitterException ex){
            Log.e(TAG, ex.getMessage());
        }catch(Exception ex){
            Log.e(TAG, ex.getMessage());
        }
        return null;
    }

}
