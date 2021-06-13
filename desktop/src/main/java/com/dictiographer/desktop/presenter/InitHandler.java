package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.Constants;
import com.dictiographer.desktop.model.Model;
import com.dictiographer.desktop.view.View;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class InitHandler extends BaseHandler {

    public InitHandler(Model m, View v, Presenter p) {
        super(m, v, p);
    }

    public void handle() {
        Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
        insets.top = -1;
        insets.left = -1;
        insets.right = -1;
        insets.bottom = -1;
        UIManager.put("TabbedPane.contentBorderInsets", insets);

        model.resourceBundle = ResourceBundle.getBundle("DictiographerResource", Locale.ENGLISH);

        model.properties = new Properties();
        try {
            model.properties.load(getClass().getClassLoader().getResourceAsStream(Constants.PROPS_FILE_NAME));
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't load properties file from the classpath.");
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(getUserPropertiesFileName());
            model.properties.load(fis);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            Files.createDirectories(Paths.get(Constants.DICT_DIR_NAME));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Display the window.
        try {
            int width = model.properties.containsKey(Constants.PropKeys.PROP_WIDTH.name()) ? Integer.parseInt(model.properties.getProperty(Constants.PropKeys.PROP_WIDTH.name())) : 600;
            int height = model.properties.containsKey(Constants.PropKeys.PROP_HEIGHT.name()) ? Integer.parseInt(model.properties.getProperty(Constants.PropKeys.PROP_HEIGHT.name())) : 800;
            view.setPreferredSize(new Dimension(width, height));
        } catch (Exception ex) {
            view.setPreferredSize(new Dimension(600, 800));
        }

        try {
            int posX = model.properties.containsKey(Constants.PropKeys.PROP_POS_X.name()) ? Integer.parseInt(model.properties.getProperty(Constants.PropKeys.PROP_POS_X.name())) : 0;
            int posY = model.properties.containsKey(Constants.PropKeys.PROP_POS_Y.name()) ? Integer.parseInt(model.properties.getProperty(Constants.PropKeys.PROP_POS_Y.name())) : 0;
            view.setLocation(posX, posY);
        } catch (Exception ex) {
            view.setLocation(50, 50);
        }

        view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        view.addWindowListener(presenter.exitHandler.windowCloseHandler);
        view.setTitle(model.resourceBundle.getString("title"));
        view.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/favicon-16x16.png")).getImage());

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(presenter.keyHandler);
        Constants.setLAF(view, Constants.DEFAULT_LAF);

        onDictionariesUpdated(model.getDictionaries());

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.pack();
                view.setVisible(true);
            }
        });

        //for debugging
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                presenter.entryHandler.handle();
//            }
//        });
    }
}
