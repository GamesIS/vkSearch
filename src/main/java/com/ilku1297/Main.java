package com.ilku1297;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.ilku1297.db.DBHandler;
import com.ilku1297.db.DBObj;
import com.ilku1297.json.desirializers.UserDesirializer;
import com.ilku1297.json.desirializers.UserSearchDeserializer;
import com.ilku1297.objects.User;
import com.ilku1297.objects.UserSearchResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Main {
    public static final Integer APP_ID = 6820852;
    public static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    public static final String VK_AUTH_URL = "https://oauth.vk.com/authorize?client_id=" + APP_ID + "&display=page&response_type=token&scope=wall,photos,offline&redirect_url=" + REDIRECT_URL + "&v=5.92";
    public static String access_token = "473920a142d2a7117b4ed3dac2131ea3848b6c3d0b98893e98ae8d3a623e2e7ef21b3bf969df7e8872f58";
    public static final String SERVICE_TOKEN = "c4b25907c4b25907c4b2590797c4da4af3cc4b2c4b25907988c0f6176812f5f1f04789e";
    public static final String USER_AGENT = "Mozilla/5.0";

    public static final GsonBuilder builder = new GsonBuilder();

    static {
        builder.registerTypeAdapter(UserSearchResponse.class, new UserSearchDeserializer());
        builder.registerTypeAdapter(User.class, new UserDesirializer());
    }

    public static void main(String[] args) {
        try {
            sendGet();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void sendGet() throws Exception {

        String url = "https://api.vk.com/method/users.search?sort=0&count=3" + User.fields + "&city=125&country=1&sex=1&age_from=17&age_to=17&has_photo=1&v=5.92&access_token=" + access_token;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("com.ilku1297.objects.User.objects.User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(response.toString(), JsonNode.class);

        String items = jsonNode.findValue("items").toString();
        if (items != null) {
            DBHandler.saveJson(new DBObj((List) mapper.readValue(items, new TypeReference<List<User>>() {
            })));
        }
        System.out.println(DBHandler.loadJson());
    }

}
