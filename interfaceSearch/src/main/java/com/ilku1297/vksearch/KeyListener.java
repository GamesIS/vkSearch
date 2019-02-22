package com.ilku1297.vksearch;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static com.ilku1297.vksearch.MainController.*;

public class KeyListener implements NativeKeyListener {
    MainController mc;

    public KeyListener(MainController mainController) {
        this.mc = mainController;
        LogManager.getLogManager().reset();

        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(this);
        // Clear previous logging configurations.
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent ev) {
        // check your specific key press here
        // example:
        if(ev.getKeyCode() == NativeKeyEvent.VC_R) {
            // the key "h" is pressed
            System.out.println("Hello!");
        }

        if(NativeKeyEvent.VC_DOWN == ev.getKeyCode()){
            if(mc.scrollIndex + 1 < mc.imageViewList.size()){
                ensureVisible(mc.galleryScroll, mc.imageViewList, ++mc.scrollIndex);
                mc.mainImage.setImage(mc.imageViewList.get(mc.scrollIndex).getImage());
            }
        }
        if(NativeKeyEvent.VC_UP == ev.getKeyCode()){
            if(mc.scrollIndex - 1 >= 0){//TODO  а если их нет
                ensureVisible(mc.galleryScroll, mc.imageViewList, --mc.scrollIndex);
                mc.mainImage.setImage(mc.imageViewList.get(mc.scrollIndex).getImage());
            }
        }
        if(NativeKeyEvent.VC_RIGHT == ev.getKeyCode()){
            if(mc.currentUserIndex + 1 < mc.fullUserList.size()){
                mc.scrollIndex = 0;
                ensureVisible(mc.galleryScroll, mc.imageViewList, mc.scrollIndex);
                mc.setContent(++mc.currentUserIndex);
            }
        }
        if(NativeKeyEvent.VC_LEFT == ev.getKeyCode()){
            if(mc.currentUserIndex - 1 >= 0){//TODO  а если их нет
                mc.scrollIndex = 0;
                ensureVisible(mc.galleryScroll, mc.imageViewList, mc.scrollIndex);
                mc.setContent(--mc.currentUserIndex);
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent arg0) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0) {}
}
