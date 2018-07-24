package com.vaddipar.fbspam.utilities;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.ws.http.HTTPException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class FacebookHelper {

    private String fbFeedGraphAPIURL;

    FacebookHelper() {
        fbFeedGraphAPIURL = "https://graph.facebook.com/me/feed";
    }

    private void dateStringToDate(String dateString, MultivaluedMap queryParams){
        /*
        Given a string of the format YYYY/MM/DD. Convert it to a date object and return it.
         */

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getDefault());

        try{
            Long startEpochTime = dateFormat.parse(dateString).getTime() / 1000;
            Long endEpochTime = startEpochTime + 24*60*60;

            ((MultivaluedMapImpl) queryParams).add("since", Long.toString(startEpochTime));
            ((MultivaluedMapImpl) queryParams).add("until", Long.toString(endEpochTime));

        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    private JSONObject makeGetRequest(String url, MultivaluedMap queryParams) throws JSONException, NullPointerException{
        Client webClient = new Client();
        WebResource apiClient = webClient.resource(url);

        ClientResponse webResponse;

        if (queryParams != null){
            webResponse =
                    apiClient.queryParams(queryParams).accept("application/json").get(ClientResponse.class);

        }else{
            webResponse = apiClient.accept("application/json").get(ClientResponse.class);
        }

        if (webResponse == null || webResponse.getStatus() != 200){
            throw new HTTPException(webResponse.getStatus());
        }

        return new JSONObject(webResponse.getEntity(String.class));
    }

    List<String> getFeedOnDate(String dateString) throws HTTPException, JSONException{
        /*
        Given a FB user, get posts from his feed on a certain day.
         */
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        ((MultivaluedMapImpl) queryParams).add("access_token", System.getenv("FB_ACCESS_TOKEN"));
        dateStringToDate(dateString, queryParams);

        JSONObject respData = makeGetRequest(fbFeedGraphAPIURL, queryParams);
        JSONArray posts = respData.getJSONArray("data");
        List<String> postIds = new ArrayList<String>();


        while (posts!=null){
            for (int postIndex = 0; postIndex < posts.length(); postIndex++) {
                postIds.add(posts.getJSONObject(postIndex).get("id").toString());
            }

            try{
                JSONObject pagingInfo = respData.getJSONObject("paging");
                String nextPageURL = pagingInfo.get("next").toString();
                respData = makeGetRequest(nextPageURL, null);
                posts = respData.getJSONArray("data");
            }catch (JSONException e){
                posts = null;
            }
        }

        return postIds;
    }
}
