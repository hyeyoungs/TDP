package com.cdp.tdp.domain;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Trans {

    public static String default_msg_param = ""
            + "template_id=66495";



    public static String token(String rtn, JsonParser parser) {
        JsonElement element = parser.parse(rtn);
        return element.getAsJsonObject().get("access_token").getAsString();

    }


}
