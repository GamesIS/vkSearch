package com.ilku1297.vksearch;

import com.ilku1297.VKRestSender;
import com.ilku1297.db.DBHandler;
import com.ilku1297.objects.User;
import com.ilku1297.objects.photos.Photo;
import com.ilku1297.proxy.ProxyHandler;
import com.ilku1297.vksearch.multithreading.SearchThread;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.ilku1297.db.DBHandler.userMap;
import static com.ilku1297.proxy.ProxyHandler.waitAssyncTasks;

public class MainController {
    public static final String FAMILY = "Helvetica";
    public static double SIZE = 30;

    public static int MAX_HEIGHT_BIG_IMAGE = 550;
    public static int MAX_WIDTH_BIG_IMAGE = 400;


    public Button searchButton;
    public Label statusLabel;
    public Button checkProxyButton;
    public Label countProxiesLabel;
    public VBox galleryBox;
    public ScrollPane galleryScroll;

    public int scrollIndex = 0;
    public int currentUserIndex = 0;

    public List<ImageView> imageViewList = new ArrayList<>();
    public AnchorPane mainBox;

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

    public List<User> userList;

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
        VBox.setVgrow(galleryScroll, Priority.ALWAYS);
        //galleryScroll.setVmax(440);
        //galleryScroll.setPrefSize(115, 150);
        //galleryScroll.setContent(galleryBox);

        // Инициализация таблицы адресатов с двумя столбцами.

        galleryScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        galleryScroll.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT)
                event.consume();
        });
        new KeyListener(this);

        testButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> maxImage.setImage(null));
        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> startSearch());
        checkProxyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            checkProxyButton.setDisable(true);
            searchButton.setDisable(true);
            ProxyHandler.checkAllProxies(false);
            checkProxyButton.setDisable(false);
            searchButton.setDisable(false);
        });
    }

    public void setMain(Main main) {
        mainApp = main;

        DBHandler.loadJson();
        userList = new ArrayList<>(userMap.values());
        waitAssyncTasks(backgroundLoadImages(userList, 0, 2));
        setContent(0);



        /*List<User> users = null; //TODO Это сортировка по last seen
        try {
            users = getUsersByName("Екатерина", 17, 17, false);
        } catch (Exception e) {
            logger.error("Error loading Users", e);
        }
        //waitAssyncTasks(backgroundLoadImages(users, 0, 2)); //TODO перенес метод
        //setContent(users);
        System.out.println("Users Size = " + users.size());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        for(User user: users){
            java.util.Date time=new java.util.Date((long)user.getLastSeen().getTime()*1000);
            if(time.after(calendar.getTime())) {
                // In between
                //System.out.println(user.getFirstName() + " " + user.getLastName() + "          \t" + time + "          \thttp://vk.com/id" + user.getID());
                System.out.println("http://vk.com/id" + user.getID());
            }
        }*/ //TODO END

    }



    public void startSearch() {
        try {
            ProxyHandler.checkAllProxies(true);
            new Thread(new SearchThread(this)).start();

        } catch (Exception ex) {
            logger.error("Error startSearch", ex);
        }
    }

    public int lastTmp = 2;
    public void setContent(Integer userID) {
        if(lastTmp != 0){
            int tmp = userList.size() - 1 - lastTmp;
            int first = 0, last = 0;
            if(tmp > 5){
                first = lastTmp + 1;
                last = lastTmp + 5;
            }
            else if(tmp < 5 && tmp > 1){
                first = lastTmp + 1;
                last = lastTmp + tmp;
            }
            else if(tmp == 1){
                first = lastTmp + 1;
                last = lastTmp + 1;
            }
            if(!(first == 0 && last == 0 )){
                lastTmp =last;
                backgroundLoadImages(userList, first, last);
            }
        }
        User user = userList.get(userID);
        try{
            List<Photo> photoList = VKRestSender.getAllPhoto(user);
            Platform.runLater(() -> {
                setScrollGallery(photoList);
            });
        }
        catch (IllegalArgumentException ex){
            Platform.runLater(() -> {
                clearGallery();
            });
            logger.info(ex.getMessage());
        }
        Platform.runLater(() -> {
            setMaxImage(user.getPhotoMaxBufferedImage());
            Hyperlink hyperlink = new Hyperlink(user.getFirstName() + " " + user.getLastName());
            hyperlink.setOnAction(e -> mainApp.getHostServices().showDocument("http://vk.com/id" + user.getID()));
            hyperlink.setFont(Font.font(FAMILY, SIZE));
            hyperlink.setBorder(Border.EMPTY);
            hyperlink.setStyle("-fx-color: #000000;");
            nameTextFlow.getChildren().addAll(hyperlink);
        });
    }

    private void clearGallery() {
        for (ImageView imageView : imageViewList) {
            galleryBox.getChildren().remove(imageView);
        }
        imageViewList.clear();
    }

    private void setScrollGallery(List<Photo> photoList) {
        clearGallery();
        for (Photo photo : photoList) {
            photo.setMaxImage(getPhoto(photo.getMaxPhoto()));
            ImageView view = new ImageView();
            WritableImage image = SwingFXUtils.toFXImage(scaleImage(photo.getMaxImage()), null);
            view.setImage(image);
            imageViewList.add(view);
            view.setFitWidth(galleryBox.getWidth() - 10);
            view.setPreserveRatio(true);
            view.setOnMouseClicked(event -> setMaxImage(photo.getMaxImage()));
            galleryBox.getChildren().add(view);
        }
    }

    public static void ensureVisible(ScrollPane pane, Node node) {
        Platform.runLater(() -> {
            double width = pane.getContent().getBoundsInLocal().getWidth();
            double height = pane.getContent().getBoundsInLocal().getHeight();

            double x = node.getBoundsInParent().getMaxX();
            double y = node.getBoundsInParent().getMaxY();

            // scrolling values range from 0 to 1
            pane.setVvalue(y / height);
            pane.setHvalue(x / width);

            // just for usability
            node.requestFocus();
        });
    }


    public List<Thread> backgroundLoadImages(List<User> users, int first, int last) {
        List<Thread> threadList = new ArrayList<>();
        if (first < users.size() && last < users.size() && last >= first) {
            for (int i = first; i <= last; i++) {
                User tmpUser = users.get(i);
                Thread thread = new Thread(() -> tmpUser.setPhotoMaxBufferedImage(getPhoto(tmpUser.getCustomPhotoMaxOrig())));
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

    private BufferedImage scaleImage(BufferedImage originalImage) {
        /*BufferedImage dst = new BufferedImage(MAX_WIDTH_BIG_IMAGE, MAX_HEIGHT_BIG_IMAGE, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = dst.createGraphics();
            // Fill background for scale to fit.
            g2.setBackground(java.awt.Color.BLACK);
            g2.clearRect(0,0,MAX_WIDTH_BIG_IMAGE,MAX_HEIGHT_BIG_IMAGE);
            double xScale = (double)MAX_WIDTH_BIG_IMAGE/originalImage.getWidth();
            double yScale = (double)MAX_HEIGHT_BIG_IMAGE/originalImage.getHeight();
            // Scaling options:
            // Scale to fit - image just fits in label.
            double scale = Math.min(xScale, yScale);
            // Scale to fill - image just fills label.
            //double scale = Math.max(xScale, yScale);
            int width  = (int)(scale*originalImage.getWidth());
            int height = (int)(scale*originalImage.getHeight());
            int x = (MAX_WIDTH_BIG_IMAGE - width)/2;
            int y = (MAX_HEIGHT_BIG_IMAGE - height)/2;
            g2.drawImage(originalImage, x, y, width, height, null);
            g2.dispose();*/

        int finalw = MAX_WIDTH_BIG_IMAGE;
        int finalh = MAX_HEIGHT_BIG_IMAGE;
        double factor = 1.0d;
        if (originalImage.getWidth() > originalImage.getHeight()) {
            factor = ((double) originalImage.getHeight() / (double) originalImage.getWidth());
            finalh = (int) (finalw * factor);
        } else {
            factor = ((double) originalImage.getWidth() / (double) originalImage.getHeight());
            finalw = (int) (finalh * factor);
        }

        BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(originalImage, 0, 0, finalw, finalh, null);
        g2.dispose();

        return originalImage;


            /*Image image = originalImage.getScaledInstance(MAX_WIDTH_BIG_IMAGE, MAX_HEIGHT_BIG_IMAGE, Image.SCALE_AREA_AVERAGING);
            BufferedImage changedImage = new BufferedImage(MAX_WIDTH_BIG_IMAGE, MAX_HEIGHT_BIG_IMAGE, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = changedImage.createGraphics();//TODO AWT
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();*/
    }

    public void setMaxImage(BufferedImage image) {
        if (image != null) {

            WritableImage toFXImage = SwingFXUtils.toFXImage(scaleImage(image), null);

            maxImage.setImage(toFXImage);
            centerImage();

            Main.MAINSTAGE.show();
            //maxImageAnchor.backgroundProperty().setValue(new Background(Color.rgb(1,2,3)));
            //maxImage.setFitHeight(originalImage.getHeight());
            //maxImage.setFitWidth(originalImage.getWidth());
            System.out.println(maxImageAnchor.getBackground().getFills().get(0).getFill());
        }
    }

    public void centerImage() {
        Image img = maxImage.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = maxImage.getFitWidth() / img.getWidth();
            double ratioY = maxImage.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            maxImage.setX((maxImage.getFitWidth() - w) / 2);
            maxImage.setY((maxImage.getFitHeight() - h) / 2);

        }
    }
}
