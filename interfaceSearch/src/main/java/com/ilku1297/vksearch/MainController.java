package com.ilku1297.vksearch;

import com.ilku1297.db.DBHandler;
import com.ilku1297.db.DBObj;
import com.ilku1297.objects.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    private static Logger logger = Logger.getLogger(MainController.class);

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

        DBObj dbObj = DBHandler.loadJson();
        waitAssyncTasks(backgroundLoadImages(dbObj.getItems(), 0, 2));
        setContent(dbObj.getItems());


    }

    public void setContent(List<User> users) {

        User user = users.get(0);
        setPhoto(user.getPhotoMaxBufferedImage());
        Text text1 = new Text(user.getFirstName() + " " + user.getLastName());
        text1.setFont(Font.font(FAMILY, SIZE));
        text1.setFill(Color.BLACK);
        nameTextFlow.getChildren().addAll(text1);
    }

    static void waitAssyncTasks(List<Thread> threadList) {
        //threadList.stream().filter(x -> !x.isAlive()).forEach(threadList::remove); //TODO Change logic to stream
        while (threadList.size() != 0) {
            Iterator iterator = threadList.iterator();
            while (iterator.hasNext()) {
                Thread thread = (Thread) iterator.next();
                if (!thread.isAlive()) {
                    iterator.remove();
                }
            }
        }
    }


    public List<Thread> backgroundLoadImages(List<User> users, int first, int last) {
        List<Thread> threadList = new ArrayList<>();
        if (first < users.size() && last < users.size() && last >= first) {
            for (int i = first; i <= last; i++) {
                User tmpUser = users.get(i);
                Thread thread = new Thread(() -> {
                    tmpUser.setPhotoMaxBufferedImage(getPhoto(tmpUser.getPhotoMaxOrig()));
                });
                threadList.add(thread);
                thread.start();
            }
        } else {
            logger.error("Invalid range images");
        }
        return threadList;

    }

    public BufferedImage getPhoto(String strUrl) {
        BufferedImage image = null;
        try {
            URL url = new URL(strUrl);
            image = ImageIO.read(url);
        } catch (Exception e) {
            logger.error("Error loading images from URL", e);
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
