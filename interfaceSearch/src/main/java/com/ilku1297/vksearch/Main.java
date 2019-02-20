package com.ilku1297.vksearch;

import com.ilku1297.db.DBHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static final String NAME = "VK Search 3000";
    public static Stage MAINSTAGE;
    public static String INTERFACE_RESOURCE_PATH = new File(System.getProperty("user.dir") + "/interfaceSearch/src/main/resources").getPath();//TODO костыль

    public static Logger logger = LogManager.getLogger(Main.class);

    static Stage window;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        logger.info("Start Application");

        DBHandler.loadJson();
        MAINSTAGE = stage;
        showListImages(stage);
    }

    public void showListImages(Stage stage) {
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
            logger.error(e);
            System.exit(0);
        }
    }

    @Override
    public void stop() throws Exception {
        DBHandler.saveJson();
    }
}
