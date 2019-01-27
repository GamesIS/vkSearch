package com.ilku1297.vksearch;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainController {
    @FXML
    private ImageView testImage;

    private Main mainApp;

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public MainController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов с двумя столбцами.

    }

    public void setMain(Main main) {
        mainApp = main;
        System.out.println(testImage);

        InputStream in = null;
        try {
            URLConnection conn = new URL("https://sun1-11.userapi.com/c844216/v844216569/1739b7/a9h_Mk-B98I.jpg?ava=1").openConnection();
            in = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (in != null) {
            testImage.setImage(new Image(in, 100, 200, false, true));

        }
    }
}
