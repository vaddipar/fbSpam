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

    private String fbMyGraphAPIURL;
    DateFormat fbDateFormat;

    FacebookHelper() {
        fbMyGraphAPIURL = "https://graph.facebook.com/v3.0/me";
        fbDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        fbDateFormat.setTimeZone(TimeZone.getDefault());
    }

    private void dateStringToDate(String dateString, MultivaluedMap queryParams){
        /*
        Given a string of the format YYYY/MM/DD. Convert it to a date object and return it.
         */

        try{
            Long startEpochTime = fbDateFormat.parse(dateString).getTime() / 1000;
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
            ((MultivaluedMapImpl) queryParams).add("access_token", System.getenv("FB_ACCESS_TOKEN"));
            webResponse =
                    apiClient.queryParams(queryParams).accept("application/json").get(ClientResponse.class);

        }else{

            webResponse = apiClient.accept("application/json").get(ClientResponse.class);
        }

        if (webResponse == null || webResponse.getStatus() != 200){
            System.out.println(webResponse);
            throw new HTTPException(webResponse.getStatus());
        }

        return new JSONObject(webResponse.getEntity(String.class));
    }

    private List<String> getFeedOnDate(String dateString) throws HTTPException, JSONException{
        /*
        Given a FB user, get posts from his feed on a certain day.
         */
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        ((MultivaluedMapImpl) queryParams).add("access_token", System.getenv("FB_ACCESS_TOKEN"));
        dateStringToDate(dateString, queryParams);

        JSONObject respData = makeGetRequest(fbMyGraphAPIURL+"/feed", queryParams);
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

    private Calendar getUserDOB() throws JSONException, NullPointerException, ParseException{
        /*
        Gets a user birthday from his profile
         */

        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        queryParams.add("fields", "birthday");
        String birthdayString = makeGetRequest(fbMyGraphAPIURL, queryParams).getString("birthday");
        Calendar bdayDateCal = Calendar.getInstance();
        bdayDateCal.setTime(fbDateFormat.parse(birthdayString));
        return bdayDateCal;
    }

    List<String> getBirthdayWishes() throws JSONException, NullPointerException, ParseException{
        /*
        This method identifies the user's birthday and gets posts from this year's birthday if it has already elapsed
        (or from last years if it has not elapsed) and replies to their wish by tagging them in the post with a custom
        message.
         */

        Calendar userBday = getUserDOB();
        Calendar today = Calendar.getInstance();

        // setting birthday to this year from date of birth
        userBday.add(Calendar.YEAR, today.get(Calendar.YEAR) - userBday.get(Calendar.YEAR));

        // getting last year's wishes

        if (today.before(userBday))
            userBday.add(Calendar.YEAR, -1);

        return getFeedOnDate(fbDateFormat.format(userBday.getTime()));

    }
}
