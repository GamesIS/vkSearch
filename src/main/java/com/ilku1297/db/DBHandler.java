package com.ilku1297.db;

import javafx.collections.FXCollections;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Comparator;

public class DBHandler {
    private static final String RESOURCES_PATH = new File("resources").getAbsolutePath();
    private static final String FILE_PATH = RESOURCES_PATH + "/myDB";
    private static final String FILE_NAME = "girls.json";

    public static boolean isSaveProperty(){
        File dir = new File(FILE_PATH);
        if(dir.isDirectory())
        {
            // получаем все вложенные объекты в каталоге
            for(File item : dir.listFiles()){
                if(item.getName().equals(FILE_NAME)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void saveJson(DBObj dbObj){
       /* try {
            File file = new File(FILE_PATH + File.separator + FILE_NAME);
            //file.
            //marshaller.marshal(dbObj, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }*/
    }

    public static DBObj loadJson(){
       /* DBObj dbObj = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(NeuroProperties.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            dbObj = (DBObj) jaxbUnmarshaller.unmarshal(
                    new File(FILE_PATH + File.separator + FILE_NAME) );

        }
        catch (JAXBException e){
            e.printStackTrace();
        }
        return dbObj;*/
       return null;
    }
}
