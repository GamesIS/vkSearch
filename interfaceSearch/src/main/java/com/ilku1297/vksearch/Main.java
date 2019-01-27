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
        showListImages(stage);
    }

    public void showListImages(Stage stage){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(new File(INTERFACE_RESOURCE_PATH + "/main.fxml").toURL());
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.setTitle(NAME);

            stage.show();

            MainController controller = fxmlLoader.getController();
            controller.setMain(this);

            /*System.out.println(INTERFACE_RESOURCE_PATH);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(INTERFACE_RESOURCE_PATH + "/main.fxml").toURL());
            AnchorPane listImage = (AnchorPane) fxmlLoader.load();
            ;*/
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
