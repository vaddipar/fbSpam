package com.vaddipar.fbspam.utilities;

import javax.xml.ws.http.HTTPException;

public class FBSpam {
    public static void main(String args[]){
        FacebookHelper fbHelper = new FacebookHelper();
        try{
            System.out.println(fbHelper.getFeedOnDate("2016/07/21"));
        }catch (HTTPException e){
            System.out.println(e.getStatusCode());
        }
    }
}
