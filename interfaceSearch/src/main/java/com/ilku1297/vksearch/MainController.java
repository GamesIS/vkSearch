package com.ilku1297.vksearch;

import com.ilku1297.db.DBHandler;
import com.ilku1297.db.DBObj;
import com.ilku1297.objects.User;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainController {
    public static final String FAMILY = "Helvetica";
    public static double SIZE = 30;

    public static int MAX_HEIGHT_BIG_IMAGE = 250;
    public static int MAX_WIDTH_BIG_IMAGE = 400;

    @FXML
    private ImageView maxImage;
    @FXML
    private TextFlow nameTextFlow;
    @FXML
    private AnchorPane maxImageAnchor;
    @FXML
    private Button testButton;

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

        testButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> maxImage.setImage(null));
    }

    public void setMain(Main main) {
        mainApp = main;
        System.out.println(maxImage);

        setContent();

    }

    public void setContent() {

        DBObj dbObj = DBHandler.loadJson();
        List<User> users = dbObj.getItems();

        User user = users.get(0);
        setPhoto(getPhoto(user.getPhotoMaxOrig()));
        Text text1 = new Text(user.getFirstName() + " " + user.getLastName());
        text1.setFont(Font.font(FAMILY, SIZE));
        text1.setFill(Color.RED);
        nameTextFlow.getChildren().addAll(text1);
    }

    public BufferedImage getPhoto(String strUrl) {
        BufferedImage image = null;
        try {
            URL url = new URL(strUrl);
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setPhoto(BufferedImage originalImage) {
        if (originalImage != null) {

            Image image = originalImage.getScaledInstance(MAX_WIDTH_BIG_IMAGE, MAX_HEIGHT_BIG_IMAGE, Image.SCALE_AREA_AVERAGING);
            BufferedImage changedImage = new BufferedImage(MAX_WIDTH_BIG_IMAGE, MAX_HEIGHT_BIG_IMAGE, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = changedImage.createGraphics();//TODO AWT
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            WritableImage toFXImage = SwingFXUtils.toFXImage(changedImage, null);

            maxImage.setImage(toFXImage);
            Main.MAINSTAGE.show();
            //maxImageAnchor.backgroundProperty().setValue(new Background(Color.rgb(1,2,3)));
            //maxImage.setFitHeight(originalImage.getHeight());
            //maxImage.setFitWidth(originalImage.getWidth());
            System.out.println(maxImageAnchor.getBackground().getFills().get(0).getFill());
        }
    }
}
