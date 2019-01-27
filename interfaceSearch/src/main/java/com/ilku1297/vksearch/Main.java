package com.ilku1297.vksearch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Main extends Application {
    public static final String NAME = "VK Search 3000";
    public static String INTERFACE_RESOURCE_PATH = new File(System.getProperty("user.dir") + "/interfaceSearch/src/main/resources").getPath();//TODO костыль

    static Stage window;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        showListImages();
    }

    public void showListImages(){
        try {
            System.out.println(INTERFACE_RESOURCE_PATH);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(INTERFACE_RESOURCE_PATH + "/main.fxml").toURL());
            AnchorPane listImage = (AnchorPane) fxmlLoader.load();
            MainController controller = fxmlLoader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }


        InputStream in = null;
        try {
            URLConnection conn = new URL("https://sun1-11.userapi.com/c844216/v844216569/1739b7/a9h_Mk-B98I.jpg?ava=1").openConnection();
            in = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (in != null) {
            Image image2 = new Image(in, 100, 200, false, true);
            ImageView imageView2 = new ImageView(image2);

        }
    }
}
