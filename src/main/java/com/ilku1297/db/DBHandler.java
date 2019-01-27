package com.ilku1297.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilku1297.objects.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DBHandler {
    private static final String RESOURCES_PATH = new File("src").getAbsolutePath();
    private static final String FILE_PATH = RESOURCES_PATH + "\\main\\resources\\myDB";
    private static final String FILE_NAME = "girls.json";
    private static final String GIRLS_PATH = FILE_PATH + File.separator + FILE_NAME;
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isSaveProperty() {
        File dir = new File(FILE_PATH);
        if (dir.isDirectory()) {
            // получаем все вложенные объекты в каталоге
            for (File item : dir.listFiles()) {
                if (item.getName().equals(FILE_NAME)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void saveJson(DBObj dbObj) {
        try {
            File file = new File(GIRLS_PATH);
            System.out.println(file.getAbsolutePath());
            objectMapper.writeValue(file, dbObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DBObj loadJson() {
        DBObj dbObj = null;
        try {
            File file = new File(GIRLS_PATH);
            JsonNode jsonNode = objectMapper.readValue(file, JsonNode.class);
            String items = jsonNode.findValue("items").toString();//TODO костыль
            dbObj = new DBObj((List) objectMapper.readValue(items, new TypeReference<List<User>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dbObj;
    }
}
