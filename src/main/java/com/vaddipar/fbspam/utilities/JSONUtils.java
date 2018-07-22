package com.vaddipar.fbspam.utilities;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

public class JSONUtils {

    public JSONObject readJSONFile(String filePath) throws IOException, JSONException{
        try{
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            return new JSONObject(jsonString);
        }catch (IOException e){
            throw e;
        }catch (JSONException e){
            throw e;
        }
    }
}
