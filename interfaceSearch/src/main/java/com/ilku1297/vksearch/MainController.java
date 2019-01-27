package com.ilku1297.vksearch;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

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
    }
}
