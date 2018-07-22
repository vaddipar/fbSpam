package com.vaddipar.fbspam.utilities;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.core.MultivaluedMap;

public class FBSpam {
    public static void main(String args[]){
        Client webClient = new Client();
        String url = "https://graph.facebook.com/me/picture";
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        ((MultivaluedMapImpl) queryParams).add("access_token", System.getenv("FB_ACCESS_TOKEN"));
        ((MultivaluedMapImpl) queryParams).add("type", "square");
        WebResource webResource = webClient.resource(url);
        ClientResponse respObject = webResource.queryParams(queryParams).accept("application/json").get(ClientResponse.class);

        if (respObject.getStatus() == 200){
            String pic_data = respObject.getEntity(String.class);
            Path op_file_path = Paths.get("/Users/vaddipar/Desktop/sandeep.jpg");

            try{
                Files.write(op_file_path, pic_data.getBytes());
            }catch (IOException e){
                e.printStackTrace();
            }

        }

//        try{
//            JSONObject respContent = new JSONObject(respObject.getEntity(String.class));
//            System.out.println(respContent);
//        }catch (JSONException e){
//            System.out.println(e);
//        }
    }
}
