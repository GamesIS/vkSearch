package com.ilku1297.vksearch;

import com.ilku1297.VKRestSender;
import com.ilku1297.db.DBHandler;
import com.ilku1297.objects.User;
import com.ilku1297.objects.photos.Photo;
import com.ilku1297.proxy.ProxyHandler;
import com.ilku1297.vksearch.multithreading.SearchThread;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.ilku1297.VKRestSender.getUsersByName;
import static com.ilku1297.db.DBHandler.*;
import static com.ilku1297.vksearch.ConstHelper.RUSSIA;
import static com.ilku1297.vksearch.ConstHelper.SARATOV;
import static com.ilku1297.vksearch.multithreading.SearchThread.maxSleepTime;
import static com.ilku1297.vksearch.multithreading.SearchThread.minSleepTime;

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
    public AnchorPane mainBox;
    public ImageView mainImage;
    public TextFlow nameTextFlow;
    public AnchorPane maxImageAnchor;
    public Button testButton;

    public int scrollIndex = 0;
    public int currentUserIndex = 0;

    public List<ImageView> imageViewList = new ArrayList<>();

    private Hyperlink hyperlink = new Hyperlink();

    private Main mainApp;

    private static Logger logger = Logger.getLogger(MainController.class);

    public List<User> fullUserList;
    public List<User> readyToUIUserList;
    public List<User> userUIList;
    public List<User> notCheckedUser;

    private VBox centerIndicatorVerticBox = new VBox();
    private ProgressIndicator progressIndicator = new ProgressIndicator();
    private HBox centerIndicatorHorizBox = new HBox();

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
        System.out.println(" " + 3 + 3);


        VBox.setVgrow(galleryScroll, Priority.ALWAYS);
        //galleryScroll.setVmax(440);
        //galleryScroll.setPrefSize(115, 150);
        //galleryScroll.setContent(galleryBox);

        // Инициализация таблицы адресатов с двумя столбцами.

        galleryScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        galleryScroll.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT)
                event.consume();
        });
        new KeyListener(this);

        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> startSearch());
        checkProxyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            checkProxyButton.setDisable(true);
            searchButton.setDisable(true);
            ProxyHandler.checkAllProxies(false);
            checkProxyButton.setDisable(false);
            searchButton.setDisable(false);
        });

        {
            centerIndicatorHorizBox.setAlignment(Pos.CENTER);
            centerIndicatorHorizBox.setPrefWidth(mainBox.getPrefWidth());
            centerIndicatorHorizBox.setPrefHeight(mainBox.getPrefHeight());
            centerIndicatorHorizBox.getChildren().addAll(progressIndicator);

            centerIndicatorVerticBox.setAlignment(Pos.CENTER);
            centerIndicatorVerticBox.setPrefWidth(mainBox.getPrefWidth());
            centerIndicatorVerticBox.setPrefHeight(mainBox.getPrefHeight());
            centerIndicatorVerticBox.getChildren().add(centerIndicatorHorizBox);

            centerIndicatorVerticBox.setVisible(false);
            progressIndicator.setVisible(false);
            centerIndicatorHorizBox.setVisible(false);

            mainBox.getChildren().add(centerIndicatorVerticBox);
        }
    }

    public void hideAllElements(Boolean bool) {
        searchButton.setVisible(bool);
        statusLabel.setVisible(bool);
        checkProxyButton.setVisible(bool);
        countProxiesLabel.setVisible(bool);
        galleryBox.setVisible(bool);
        galleryScroll.setVisible(bool);
        mainImage.setVisible(bool);
        nameTextFlow.setVisible(bool);
        maxImageAnchor.setVisible(bool);
        testButton.setVisible(bool);
    }

    public void progressIndicatorHandle(Boolean activate) {
        centerIndicatorVerticBox.setVisible(activate);
        progressIndicator.setVisible(activate);
        centerIndicatorHorizBox.setVisible(activate);
        hideAllElements(!activate);
    }

    public void setMain(Main main) {


        mainApp = main;

        hyperlink.setFont(Font.font(FAMILY, SIZE));
        hyperlink.setBorder(Border.EMPTY);
        hyperlink.setStyle("-fx-color: #000000;");
        nameTextFlow.getChildren().addAll(hyperlink);

        DBHandler.loadJson();
        fullUserList = new ArrayList<>(userMap.values());
        notCheckedUser = new ArrayList<>(fullUserList);
        readyToUIUserList = new ArrayList<>();
        userUIList = new ArrayList<>();
        Collections.shuffle(fullUserList);
        //waitAssyncTasks(backgroundLoadImages(fullUserList, 0, 2));
        //preLoadingImages();
        //setContent(0);


        Set<User> users = new HashSet<>(); //TODO Это сортировка по last seen
        /*try {
            int rangeAges = 20;
            String name = "Юля";
            for(int age = 17; age <= 20; age++){
                users.addAll(getUsersByName(-1, name, age, age, false, 0, ""));
            }
        } catch (Exception e) {
            logger.error("Error loading Users", e);
        }*/
        try {
            int rangeAges = 20;
            String name = "Вероника";
            String country;
            String city;
            String school = null;
            String un = null;
            int groupID = -1;
            //for(String name: names){
                for (int i = 0; i <= 8; i++) {
                    for (int age = 16; age <= 21; age++) {
                        if (i == 1) {
                            for (String univer : univers) {
                                country = null;
                                city = null;
                                un = univer;
                                school = null;
                                Thread.sleep(ThreadLocalRandom.current().nextInt(minSleepTime, maxSleepTime + 1));
                                users.addAll(getUsersByName(groupID, name, age, age, false, i, un, city, country, school));
                            }
                            /*for (String sch : schools) {
                                country = null;
                                city = null;
                                un = null;
                                school = sch;
                                Thread.sleep(ThreadLocalRandom.current().nextInt(minSleepTime, maxSleepTime + 1));
                                users.addAll(getUsersByName(groupID, name, age, age, false, i, un, city, country, school));
                            }*/
                        } else {
                            country = RUSSIA;
                            city = SARATOV;
                            un = null;
                            school = null;
                            Thread.sleep(ThreadLocalRandom.current().nextInt(minSleepTime, maxSleepTime + 1));
                            users.addAll(getUsersByName(groupID, name, age, age, false, i, un, city, country, school));
                        }
                    }
                }
            //}
        } catch (Exception e) {
            logger.error("Error loading Users", e);
        }
        //waitAssyncTasks(backgroundLoadImages(users, 0, 2)); //TODO перенес метод
        //setContent(users);
        System.out.println("Users Size = " + users.size());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        for (User user : users) {
            java.util.Date time = new java.util.Date((long) user.getLastSeen().getTime() * 1000);
            if (time.after(calendar.getTime())) {
                // In between
                //System.out.println(user.getFirstName() + " " + user.getLastName() + "          \t" + time + "          \thttp://vk.com/id" + user.getID());
                System.out.println("http://vk.com/id" + user.getID());
            }
        } //TODO END

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

    public void preLoadingImages() {
        new Thread(() -> {
            for (User user : fullUserList) {
                while (fullUserList.indexOf(user) - currentUserIndex > 15) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                backgroundLoadImages(user);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public static Boolean usersEnded = false;

    public void setContent(Integer userID) {
        boolean userNotLoaded = false;
        while (true) {
            if (readyToUIUserList.isEmpty() && !notCheckedUser.isEmpty()) {
                logger.info("Users is not loaded");
                try {
                    if (!progressIndicator.isVisible()) {
                        Platform.runLater(() -> progressIndicatorHandle(true));
                        userNotLoaded = true;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if (userNotLoaded) {
                Platform.runLater(() -> progressIndicatorHandle(false));
            }
            if (readyToUIUserList.isEmpty() && notCheckedUser.isEmpty()) {
                usersEnded = true;
                logger.info("Users Finished");
                setContent(userID);
                return;
            }
            User user = readyToUIUserList.get(0);
            Platform.runLater(() -> {
                if (user.getPhotoList() != null) {
                    setScrollGallery(user.getPhotoList());
                } else {
                    clearGallery();
                }
            });

            Platform.runLater(() -> {
                setMainImage(user.getMainPhoto());
                hyperlink.setText(user.getFirstName() + " " + user.getLastName());
                hyperlink.setOnAction(e -> mainApp.getHostServices().showDocument("http://vk.com/id" + user.getID()));
            });
            break;
        }
    }

    private void clearGallery() {
        for (ImageView imageView : imageViewList) {
            galleryBox.getChildren().remove(imageView);
        }
        imageViewList.clear();
    }

    private static void downloadAllPhoto(User user) {
        if (user.getPhotoList() != null) {
            for (Photo photo : user.getPhotoList()) {
                DBHandler.checkPhoto(photo);
                if (photo.getDownloadedMaxImage() == null) {
                    photo.setDownloadedMaxImage(downloadPhoto(photo));
                }
            }
        }
    }

    private void setScrollGallery(List<Photo> photoList) {
        clearGallery();
        //downloadAllPhoto(photoList);
        for (Photo photo : photoList) {
            ImageView view = new ImageView();
            view.setImage(photo.getDownloadedMaxImage());
            imageViewList.add(view);
            view.setFitWidth(galleryBox.getWidth() - 10);
            view.setPreserveRatio(true);
            view.setOnMouseClicked(event -> setMainImage(photo));
            galleryBox.getChildren().add(view);
        }
    }

    public static void ensureVisible(ScrollPane pane, List<ImageView> imageViewList, Integer index) {
        Platform.runLater(() -> {
            if (imageViewList.isEmpty()) {
                pane.setVvalue(0);
                pane.setHvalue(0);
            } else {
                if (index != 0) {
                    double width = pane.getContent().getBoundsInLocal().getWidth();
                    double height = pane.getContent().getBoundsInLocal().getHeight();

                    double x = imageViewList.get(index).getBoundsInParent().getMaxX();
                    double y = imageViewList.get(index).getBoundsInParent().getMaxY();

                    // scrolling values range from 0 to 1
                    pane.setVvalue(y / height);
                    pane.setHvalue(x / width);

                    imageViewList.get(index).requestFocus();
                } else {
                    pane.setVvalue(0);
                    pane.setHvalue(0);
                    imageViewList.get(0).requestFocus();
                }

                // just for usability
            }
        });
    }


    public void backgroundLoadImages(User user) {
        if (user.getPhotoList() != null && user.getMainPhoto() != null) {
            user.setIsLoaded(true);
            return;
        }
        Thread thread = new Thread(() -> {
            if (user.getMainPhoto() == null) {
                user.setMainPhoto(new Photo(downloadPhoto(user.getCustomPhotoMaxOrig())));
            }
            if (user.getPhotoList() == null) {
                try {
                    user.setPhotoList(VKRestSender.getAllPhoto(user));
                } catch (IllegalArgumentException ex) {
                    logger.info(ex.getMessage());
                }
            }
            new Thread(() -> {
                if (user.getMainPhoto().getDownloadedMaxImage() == null) {
                    user.getMainPhoto().setDownloadedMaxImage(downloadPhoto(user.getPhoto()));
                }
                downloadAllPhoto(user);//Todo можно каждую в отдельном потоке, пот
                user.setIsLoaded(true);
                readyToUIUserList.add(user);
                notCheckedUser.remove(user);
            }).start();
        });
        thread.start();
    }

    public static BufferedImage downloadPhoto(String strUrl) {
        //Todo Сделать Try catch для отсутсвия подключения уже реализовано, вынести в метод
        BufferedImage image = null;
        try {
            URL url = new URL(strUrl);
            image = ImageIO.read(url);
        } catch (Exception e) {
            logger.error("Error loading images from URL", e);
            return downloadPhoto(User.NO_PHOTO);
        }
        return image;
    }


    public static BufferedImage downloadPhoto(Photo photo) {
        BufferedImage bufferedImage = downloadPhoto(photo.getMaxPhotoURL());
        DBHandler.saveImage(photo, bufferedImage);
        return bufferedImage;
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

    public void setMainImage(Photo photo) {
        if (photo != null && photo.getDownloadedMaxImage() != null) {

            mainImage.setImage(photo.getDownloadedMaxImage());
            centerImage();

            Main.MAINSTAGE.show();
            //maxImageAnchor.backgroundProperty().setValue(new Background(Color.rgb(1,2,3)));
            //downloadedMaxImage.setFitHeight(originalImage.getHeight());
            //downloadedMaxImage.setFitWidth(originalImage.getWidth());
            System.out.println(maxImageAnchor.getBackground().getFills().get(0).getFill());
        }
    }

    public void centerImage() {
        Image img = mainImage.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = mainImage.getFitWidth() / img.getWidth();
            double ratioY = mainImage.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            mainImage.setX((mainImage.getFitWidth() - w) / 2);
            mainImage.setY((mainImage.getFitHeight() - h) / 2);

        }
    }
}
