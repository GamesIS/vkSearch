package com.ilku1297;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilku1297.db.DBHandler;
import com.ilku1297.db.DBObj;
import com.ilku1297.objects.User;
import com.ilku1297.objects.photos.Photo;
import com.ilku1297.objects.photos.PhotoList;
import com.ilku1297.proxy.JProxy;
import com.ilku1297.proxy.ProxyHandler;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.rmi.RemoteException;
import java.util.List;

import static com.ilku1297.InternetAvailabilityChecker.isInternetAvailable;
import static com.ilku1297.db.DBHandler.addUserToMap;

public class VKRestSender {

    public static final Integer APP_ID = 6820852;//TODO сделать много токенов
    public static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    public static final String VK_AUTH_URL = "https://oauth.vk.com/authorize?client_id=" + APP_ID + "&display=page&response_type=token&scope=wall,photos,offline&redirect_url=" + REDIRECT_URL + "&v=5.92";
    public static final String ACCESS_TOKEN = "473920a142d2a7117b4ed3dac2131ea3848b6c3d0b98893e98ae8d3a623e2e7ef21b3bf969df7e8872f58";
    public static final String SERVICE_TOKEN = "c4b25907c4b25907c4b2590797c4da4af3cc4b2c4b25907988c0f6176812f5f1f04789e";
    public static final String USER_AGENT = "Mozilla/5.0";
    public static final String VERSION_API = "5.92";
    private static final Logger logger = Logger.getLogger(VKRestSender.class);
    public static final String VER_ACC_TOK = "&v=" + VERSION_API + "&access_token=" + ACCESS_TOKEN;
    public static final String ADDRESS = "https://api.vk.com/method/";

    /*public static final GsonBuilder builder = new GsonBuilder();

    static {
        builder.registerTypeAdapter(UserSearchResponse.class, new UserSearchDeserializer());
        builder.registerTypeAdapter(User.class, new UserDesirializer());
    }*/

    public static List<Photo> getAllPhoto(User user) {
        String url = ADDRESS + "photos.getAll?owner_id=" + user.getID() + "&count=20&extended=1&photo_sizes=1" + VER_ACC_TOK;
        String response = null;
        String items = null;
        try{
            response = sendGet(url, false);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readValue(response, JsonNode.class);
            JsonNode itemsNode = jsonNode.findValue("items");
            if (itemsNode != null) {
                items = itemsNode.toString();
                System.out.println(items);
            }

            List<Photo> photoList = null;
            if (items != null) {
                photoList = mapper.readValue(items, new TypeReference<List<Photo>>() {});
            }
            else {
                if(response.equals("Access denied: user deactivated")){
                    throw new IllegalArgumentException("Access denied: user deactivated");
                }
                itemsNode = jsonNode.findValue("error_msg");
                if (itemsNode != null) {
                    items = itemsNode.asText();
                    if(items.equals("This profile is private")){
                        throw new IllegalArgumentException(items);
                    }
                }
            }
            return photoList;

        }
        catch (IllegalArgumentException ex){
            throw new IllegalArgumentException(ex.getMessage());
        }
        catch (Exception ex){
            logger.error("Error loading allPhoto" , ex);
        }
        return null;
    }

    public static List<User> getUsersByName(int groupID, String name, int ageFrom, int ageTo, boolean isNeedProxy, int status, String univer, String city, String country, String school) throws Exception {
        boolean univerIsEmpty = univer == null;
        boolean nameIsEmpty = name == null;
        boolean countryIsEmpty = country == null;
        boolean cityIsEmpty = city == null;
        boolean groupIsEmpty = groupID == -1;
        boolean schoolIsEmpty = school == null;

        StringBuilder url = new StringBuilder("");
        url
                .append(ADDRESS)
                .append("users.search?sort=0&count=1000")
                .append(User.fields);
        if(!countryIsEmpty){
            url.append("&country=").append(country);
        }
        if(!cityIsEmpty){
            url.append("&city=").append(city);
        }
        /*if(groupID == -1 || !univerIsEmpty){
            url.append("&city=125&country=1");
        }
        else {
            url.append("&group_id=").append(groupID);
        }*/
        if(!groupIsEmpty){
            url.append("&group_id=").append(groupID);
        }
        if(!schoolIsEmpty){
            url.append("&school=").append(school);
        }
        if(status != 0){
            url.append("&status=").append(status);
        }
        if(!univerIsEmpty){
            url.append("&university=").append(univer);
        }
        url
                .append("&sex=1&age_from=")
                .append(ageFrom)
                .append("&age_to=")
                .append(ageTo)
                .append("&has_photo=1");
        if(!nameIsEmpty){
            url.append("&q=").append(name);
        }
        url.append(VER_ACC_TOK);
        return getUsers(url.toString(), isNeedProxy);
    }

    public static List<User> getUsers(String url , boolean isNeedProxy) throws InterruptedException {
        System.out.println("URL = " + url);
        DBObj dbObj = null;
        String response = null;
        String items = null;
        boolean isNoData = false;
        while (true) {
            try {
                if (isNoData) {
                    //Thread.sleep(100000);
                    //Thread.sleep(150);
                    isNoData = false;
                }
                response = sendGet(url, isNeedProxy);

                System.out.println(response);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readValue(response, JsonNode.class);
                JsonNode itemsNode = jsonNode.findValue("items");
                if (itemsNode != null) {
                    items = itemsNode.toString();
                }

                dbObj = null;
                if (items != null) {
                    dbObj = new DBObj(mapper.readValue(items, new TypeReference<List<User>>() {
                    }));
                    addUserToMap(dbObj);
                    DBHandler.saveJson();
                }
                if ((dbObj != null && dbObj.getItems().size() > 0) || itemsNode != null) {
                    break;
                } else {
                    logger.info("No data: " + response);
                    isNoData = true;
                }
            }
            catch (Exception ex) {
                logger.error("Error send request " + response, ex);
                isNoData = true;
            }
        }

        if (dbObj != null) {
            return dbObj.getItems();
        }
        return null;
    }

    public static String sendGet(String url, boolean isNeedProxy) throws Exception {
        JProxy jProxy = null;
        try {
            HttpURLConnection con;
            if (isNeedProxy) {
                jProxy = ProxyHandler.getProxy();
                if (jProxy != null) {
                    jProxy.setBusy(true);
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(jProxy.getHost(), Integer.parseInt(jProxy.getPort())));
                    URL obj = new URL(url);
                    con = (HttpURLConnection) obj.openConnection(proxy);
                    con.setConnectTimeout(2000);
                } else {
                    throw new Exception("No working proxies");
                }
            } else {
                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
            }

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("com.ilku1297.objects.User.objects.User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            //System.out.println("\nSending 'GET' request to URL : " + url);
            //System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if(isNeedProxy){
                final JProxy tmp = jProxy;
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        tmp.setBusy(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }


            return response.toString();
        }
        catch (SSLHandshakeException | SocketTimeoutException ex){
            logger.error("Badly proxy ", ex);
            synchronized (ProxyHandler.checkedProxyList){
                ProxyHandler.checkedProxyList.remove(jProxy);
            }
            throw new RemoteException(ex.getMessage());
        }
        catch (Exception ex) {
            if(!isInternetAvailable()){
                String message = "No Internet connection";
                logger.error(message);
                Thread.sleep(10000);
                throw new RemoteException(message);
            }
            logger.error("Error send request VK", ex);
            throw new RemoteException(ex.getMessage());
        }
    }
}
