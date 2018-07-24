package com.vaddipar.fbspam.utilities;

import org.json.JSONException;

import javax.xml.ws.http.HTTPException;
import java.text.ParseException;
import java.util.List;

public class FBSpam {
    public static void main(String args[]) throws HTTPException, JSONException, ParseException {
        FacebookHelper fbHelper = new FacebookHelper();
        List<String> postIds = fbHelper.getBirthdayWishes();
        for (String postId : postIds) System.out.println(postId);
        System.out.println("I fetched "+postIds.size()+" posts");
    }
}
