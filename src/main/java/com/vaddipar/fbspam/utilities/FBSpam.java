package com.vaddipar.fbspam.utilities;

import org.json.JSONException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import javax.xml.ws.http.HTTPException;
import java.text.ParseException;
import java.util.List;

public class FBSpam {

    public static void main(String args[]) throws HTTPException, JSONException, ParseException {
        FacebookHelper fbHelper = new FacebookHelper();
        Logger logger = Logger.getLogger(FBSpam.class);
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        List<String> postIds = fbHelper.getBirthdayWishes();
        for (String postId : postIds) System.out.println(postId);
        logger.info("I fetched "+postIds.size()+" posts");
        System.out.println("I fetched "+postIds.size()+" posts");
    }
}
