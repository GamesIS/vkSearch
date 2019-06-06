package com.ilku1297.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilku1297.objects.User;
import com.ilku1297.objects.photos.Photo;
import com.ilku1297.proxy.JProxy;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class DBHandler {
    public static final Map<Integer, User> userMap = Collections.synchronizedMap(new HashMap<Integer, User> ());
    public static final String RESOURCES_PATH = new File("src").getAbsolutePath();
    public static final String FILE_PATH = RESOURCES_PATH + "\\main\\resources\\myDB";
    public static final String GIRLS_JSON = "girls.json";
    public static final String NAMES_TXT = "names.txt";
    public static final String UNIVERS_TXT = "universID.txt";
    public static final String SCHOOLS_TXT = "schoolsID.txt";
    public static final String UNCHECKED_PROXY_TXT = "uncheckedProxy.txt";
    public static final String CHECKED_PROXY_TXT = "checkedProxy.txt";
    public static final String GIRLS_PATH = FILE_PATH + File.separator + GIRLS_JSON;
    public static final String NAMES_PATH = FILE_PATH + File.separator + NAMES_TXT;
    public static final String UNIVERS_PATH = FILE_PATH + File.separator + UNIVERS_TXT;
    public static final String SCHOOLS_PATH = FILE_PATH + File.separator + SCHOOLS_TXT;
    public static final String UNCHECKED_PROXY_PATH = FILE_PATH + File.separator + UNCHECKED_PROXY_TXT;
    public static final String CHECKED_PROXY_PATH = FILE_PATH + File.separator + CHECKED_PROXY_TXT;

    public static final List<String> names = getNames(NAMES_PATH);
    public static final List<String> univers = getNames(UNIVERS_PATH);
    public static final List<String> schools = getNames(SCHOOLS_PATH);
    {
        univers.add("");
    }
    public static ObjectMapper objectMapper = new ObjectMapper();

    private static Logger logger = Logger.getLogger(DBHandler.class);

    public static boolean isSaveProperty() {
        File dir = new File(FILE_PATH);
        if (dir.isDirectory()) {
            // получаем все вложенные объекты в каталоге
            for (File item : dir.listFiles()) {
                if (item.getName().equals(GIRLS_JSON)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> getNames(String path){
        List<String> names = new ArrayList<>();
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String name;
            while ((name = br.readLine()) != null){
                name = name.trim();
                names.add(name);
            }

        } catch (IOException e) {
            logger.error("Error loading Names", e);
            System.exit(0);
        }
        return names;
    }

    public static void saveJson() {
        try {
            File file = new File(GIRLS_PATH);
            DBObj dbObj;
            synchronized (userMap){
                dbObj = new DBObj(new ArrayList<>(DBHandler.userMap.values()));
            }
            objectMapper.writeValue(file, dbObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadJson() {
        try {
            File file = new File(GIRLS_PATH);
            JsonNode jsonNode = objectMapper.readValue(file, JsonNode.class);
            String items = jsonNode.findValue("items").toString();//TODO костыль
            DBObj dbObj = new DBObj(objectMapper.readValue(items, new TypeReference<List<User>>() {
            }));
            addUserToMap(dbObj);
        } catch (IOException e) {
            logger.error("Error loading JSON", e);
            System.exit(0);
        }
    }

    public static List<JProxy> loadProxyList(String path) {
        List<JProxy> jProxies = new ArrayList<>();
        try{
            File f = new File(path);
            BufferedReader reader = new BufferedReader(
                    new FileReader(f));
            String proxy = null;
            while( (proxy = reader.readLine()) != null){
                System.out.println("Proxy: " + proxy);
                jProxies.add(new JProxy(proxy.split(":")[0]
                        ,proxy.split(":")[1]));
            }
            reader.close();
        }catch(Exception e){
            logger.error("Error loading ProxyList", e);
            System.exit(0);
        }
        return jProxies;
    }

    public static void saveProxyList(String path, List<JProxy> proxies) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)))){
            for(JProxy jProxy : proxies){
                writer.write(jProxy.getHost() + ":" + jProxy.getPort());
                writer.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            logger.error("Error save ProxyList", e);
        }
    }

    public static void addUserToMap(DBObj dbObj){
        addUserToMap(dbObj.getItems());
    }
    public static void addUserToMap(List<User> userList){
        for (User user: userList){
            synchronized (userMap){
                userMap.put(user.getID(), user);
            }
        }
    }


    //public static final String GIRLS_FOLDER = "H:\\GirlsPhoto";
    public static final String GIRLS_FOLDER = "H:\\girlsPhoto";
    public static void checkPhoto(Photo photo) {
        File f = new File(GIRLS_FOLDER);
        if(!f.exists()){
            f.mkdirs();
        }

        File[] children = f.listFiles();
        if (children != null) {
            for (File child : children) {//TODO можно переписать на стримы
                if(child.getName().equals(photo.getUserID().toString()) && child.isDirectory()){
                    File[] images = child.listFiles();
                    if(images != null){
                        for(File imageFile : images){
                            if(imageFile.getName().equals(photo.getId().toString() + ".jpg")){
                                try {
                                    photo.setDownloadedMaxImage(ImageIO.read(imageFile));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    public static void saveImage(Photo photo, BufferedImage bufImage){
        File f = new File(GIRLS_FOLDER);

        if(!f.exists()){
            f.mkdirs();
        }
        String userFolderPath = GIRLS_FOLDER + "\\" + photo.getUserID().toString();
        File userFolder = new File(userFolderPath);
        if(!userFolder.exists()){
            userFolder.mkdir();
        }
        File[] images = userFolder.listFiles();
        File image = new File(userFolderPath,photo.getId().toString() + ".jpg");
        if(images != null){
            for(File imageFile : images){
                if(imageFile.getName().equals(image.getName())){
                    return;
                }
            }
        }

        try {
            ImageIO.write(bufImage, "jpg", image);
        } catch (IOException ex) {
            logger.error("Error save image to file :" + photo, ex);
        }
    }
}
