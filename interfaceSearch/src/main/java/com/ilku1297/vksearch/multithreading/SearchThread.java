package com.ilku1297.vksearch.multithreading;

import com.ilku1297.VKRestSender;
import com.ilku1297.db.DBHandler;
import com.ilku1297.objects.User;
import com.ilku1297.proxy.ProxyHandler;
import com.ilku1297.vksearch.MainController;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ilku1297.db.DBHandler.*;

public class SearchThread implements Runnable {
    private static Logger logger = Logger.getLogger("search");
    private MainController mc;

    public SearchThread(MainController mc) {
        this.mc = mc;
    }

    public static Integer ageMin = 17;
    public static Integer ageMax = 21;

    public static int sleepTime;
    public final static int maxSleepTime = 1000;
    public final static int minSleepTime = 500;
    public final static int middleSleepTime = (maxSleepTime + minSleepTime) / 2;

    public static final int countRequestThread = 10;

    public static final List<RequestThread> requestThreads = Collections.synchronizedList(new ArrayList<RequestThread>());


    @Override
    public void run() {
        logger.info("Search started");
        mc.searchButton.setDisable(true);
        try {
            Platform.runLater(() -> {
                mc.statusLabel.setText("Загрузка: " + 0 + "%");
                mc.countProxiesLabel.setText("Количество proxy: " + ProxyHandler.checkedProxyList.size());
            });
            int size = names.size();
            int index = 0;
            for(String name: names){
                int oldSize;
                synchronized (userMap){
                    oldSize = userMap.size();
                }
                int tmp = index++;
                final AtomicInteger count = new AtomicInteger(0);
                //int ageIndex = 0;
                for (int age = ageMin; age <= ageMax; age++){
                    //ageIndex++;
                    while (requestThreads.size() >= countRequestThread){
                    }

                    final int tmpAge = age;

                    Thread thread = new Thread (() -> {
                        List<User> users = null;
                        try {
                            users = VKRestSender.getUsersByName(null, name, tmpAge, tmpAge, true);
                        } catch (Exception e) {
                            logger.error("Error in SearchThread", e);
                            saveJson();
                            logger.info("Search ended with error");
                            System.exit(0);
                        }
                        synchronized (count){
                            count.addAndGet(users.size());
                        }
                        addUserToMap(users);
                        logger.info("Name '" + name + "', ages = "+ tmpAge +" successfully loaded in the amount of = " + users.size());
                        if(isLastThreadName(name)){
                            logger.info("Finally for name '" + name + "' successfully loaded in the amount of = " + count); //TODO починить неверно показывает
                        }
                        synchronized (requestThreads){
                            requestThreads.remove(new RequestThread(name, Thread.currentThread()));
                        }
                    });
                    //TODO сделать примерное время ожидания по сумме слипов
                    synchronized (requestThreads){
                        requestThreads.add(new RequestThread(name, thread));
                    }
                    thread.start();
                    /*sleepTime = ThreadLocalRandom.current().nextInt(minSleepTime, maxSleepTime + 1);
                    Thread.sleep(sleepTime);


                    long remainedTimems = (middleSleepTime * size * (ageMax-ageMin) - middleSleepTime * index * (ageMax-ageMin) - ageIndex * middleSleepTime) / countRequestThread;
                    long second = (remainedTimems / 1000) % 60;
                    long minute = (remainedTimems / (1000 * 60)) % 60;
                    long hour = (remainedTimems / (1000 * 60 * 60)) % 24;

                    String time = String.format("%02d:%02d:%02d", hour, minute, second);*/
                    String percent = String.format("%.2f", 100.0 / (double)size * (double)tmp);

                    Platform.runLater(() -> {
                        mc.statusLabel.setText("Загрузка: " + percent + "%"/*+ "%" + " осталось примерно: " + time*/);
                        mc.countProxiesLabel.setText("Количество proxy: " + ProxyHandler.checkedProxyList.size());
                    });
                }
                synchronized (userMap){
                    if(oldSize < userMap.size()){
                        saveJson();
                    }
                }
            }
            Platform.runLater(() -> mc.statusLabel.setText("Загрузка: 100%"));
        }
        catch (Exception ex){
            logger.error("Error in SearchThread", ex);
            saveJson();
            mc.searchButton.setDisable(false);
            synchronized (userMap){
                logger.info("Finally count girls = " + userMap.size());
            }
            logger.info("Search ended with error");
            return;
        }
        mc.searchButton.setDisable(false);
        synchronized (userMap){
            logger.info("Finally count girls = " + userMap.size());
        }
        logger.info("Search ended");
        saveJson();
        synchronized (ProxyHandler.checkedProxyList){
            DBHandler.saveProxyList(CHECKED_PROXY_PATH, ProxyHandler.checkedProxyList);
        }
    }

    public static boolean isLastThreadName(String name){
        int count = 0;
        synchronized (requestThreads){
            for(RequestThread requestThread : requestThreads){
                if(requestThread.getName().equals(name)){
                    count++;
                }
            }
        }
        if(count == 1){
            return true;
        }
        return false;
    }
}
