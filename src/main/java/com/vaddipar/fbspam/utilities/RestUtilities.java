package com.vaddipar.fbspam.utilities;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestUtilities {
    public void makeGetReq(){
        Client webClient = new Client();
        WebResource webResource = webClient.resource("https://www.google.com");
        ClientResponse respObject = webResource.accept("application/json").get(ClientResponse.class);

        System.out.println(respObject.getEntity(String.class));

    }
}
