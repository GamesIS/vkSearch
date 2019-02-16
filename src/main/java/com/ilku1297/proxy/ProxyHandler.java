package com.ilku1297.proxy;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.ilku1297.db.DBHandler.*;

public class ProxyHandler {
    private static final Logger logger = Logger.getLogger(ProxyHandler.class);
    public static List<JProxy> uncheckedProxyList = new ArrayList<>();
    public static final List<JProxy> checkedProxyList = Collections.synchronizedList(new ArrayList<JProxy>());

    public static void checkAllProxies(boolean checkedList) {
        if(checkedList){
            uncheckedProxyList = loadProxyList(CHECKED_PROXY_PATH);
        }
        else {
            uncheckedProxyList = loadProxyList(UNCHECKED_PROXY_PATH);
        }
        List<Thread> threadList = new ArrayList<>();
        for (JProxy proxy : uncheckedProxyList) {
            Thread thread = new Thread(() -> checkProxy(proxy));
            thread.start();
            threadList.add(thread);
        }
        waitAssyncTasks(threadList);

        synchronized (checkedProxyList) {
            saveProxyList(CHECKED_PROXY_PATH, checkedProxyList);
        }
    }

    public static JProxy checkProxy(JProxy jProxy) {
        JProxy checkedProxy;
        try {
            logger.info("Checking " + jProxy.getHost() + ":" + jProxy.getPort() + " ...");
            String h = jProxy.getHost();
            int port = Integer.parseInt(jProxy.getPort());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(h, port));
            URL url = new URL("http://www.google.com");
            URLConnection http = url.openConnection(proxy);
            http.connect();
            logger.info("SUCCESS " + jProxy.getHost() + ":" + jProxy.getPort() + "!");
            checkedProxy = new JProxy(h, String.valueOf(port));
            synchronized (checkedProxyList) {
                if (!checkedProxyList.contains(checkedProxy)) {
                    checkedProxyList.add(checkedProxy);
                }
            }
        } catch (Exception e) {
            logger.info("Connection failed "  + jProxy.getHost() + ":" + jProxy.getPort() + "!");
        }
        return null;
    }

    public static JProxy getProxy() {
        synchronized (checkedProxyList) {
            Collections.shuffle(checkedProxyList);
            for (JProxy proxy : checkedProxyList) {
                if (!proxy.isBusy()) {
                    return proxy;
                }
            }
        }
        return null;
    }

    public static void waitAssyncTasks(List<Thread> threadList) {
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
}
