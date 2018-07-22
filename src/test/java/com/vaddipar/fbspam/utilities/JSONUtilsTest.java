package com.vaddipar.fbspam.utilities;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class JSONUtilsTest {

    @Test
    void examples() {
        JSONUtils test_util = new JSONUtils();
        try{
            test_util.readJSONFile("/Users/vaddipar/Desktop/sandeep.json");
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
