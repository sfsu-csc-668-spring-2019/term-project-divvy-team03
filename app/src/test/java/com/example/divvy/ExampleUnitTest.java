package com.example.divvy;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void postData() throws IOException, JSONException {
        httprequest request = new httprequest();
        String json = registration();
        System.out.println(json);
        String response = request.post("http://ec2-34-226-139-149.compute-1.amazonaws.com/newListing", json);
        assertEquals("OK", response);
    }

    private String registration() throws JSONException {
        //String json = "{\"username\": \"alex3222\",\"email\": \"alex23@g.com\", \"password\": \"1234\",\"first_name\":\"anton\", \"last_name\": \"blade\", \"city\":\"LA\",\"description\":\"crazy\"}";
        String json = "{\"username\": \"antonio\",\"descr\": \"new test from android\", \"title\": \"testing from android\"}";

        System.out.println(json);
        return json;

    }


    /*@Test
    public void getData() throws IOException {
        httprequest req = new httprequest();
        Map<String,String> params = new LinkedHashMap<>();
        params.put("name", "anton");

        String response = req.get(params);
        System.out.println(response);

    }*/
}