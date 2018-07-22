package com.vaddipar.fbspam.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestUtilitiesTest {
    @Test
    public void makeGetReqTest(){
        RestUtilities rest = new RestUtilities();
        rest.makeGetReq();
    }

}