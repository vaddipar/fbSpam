package com.vaddipar.fbspam.utilities;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.ws.http.HTTPException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FacebookHelper {

    private String fbFeedGraphAPIURL = "https://graph.facebook.com/me/feed";

    private MultivaluedMap dateStringToDate(String dateString){
        /*
        Given a string of the format YYYY/MM/DD. Convert it to a date object and return it.
         */

        DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/DD HH:mm:ss", Locale.ENGLISH);
        // dateFormat.setTimeZone(TimeZone.getDefault());

        String startTime = dateString+ " 00:00:00";
        String endTime = dateString+ " 23:59:59";

        MultivaluedMap dayEpoch = new MultivaluedMapImpl();
        ((MultivaluedMapImpl) dayEpoch).add("since", "1469039400");
        ((MultivaluedMapImpl) dayEpoch).add("access_token", System.getenv("FB_ACCESS_TOKEN"));
        ((MultivaluedMapImpl) dayEpoch).add("until", "1469125799");
        System.out.println(dayEpoch);
        return dayEpoch;
        //return null;
    }

    public JSONObject getFeedOnDate(String dateString) throws HTTPException{
        /*
        Given a FB user, get posts from his feed on a certain day.
         */
        MultivaluedMap epochTimes = dateStringToDate(dateString);

        Client webClient = new Client();
        WebResource fbFeedClient = webClient.resource(fbFeedGraphAPIURL);
        ClientResponse fbFeedResponse =
                fbFeedClient.queryParams(epochTimes).accept("application/json").get(ClientResponse.class);


        if (fbFeedResponse.getStatus() != 200){
            throw new HTTPException(fbFeedResponse.getStatus());
        }

        try{
            JSONObject responseData = new JSONObject(fbFeedResponse.getEntity(String.class));
            return responseData;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
