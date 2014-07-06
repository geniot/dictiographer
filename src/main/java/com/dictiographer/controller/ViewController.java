package com.dictiographer.controller;

import com.dictiographer.model.Constants;
import com.dictiographer.model.IndexModel;
import com.dictiographer.view.Dictiographer;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/6/14
 */
public class ViewController {
    private static ViewController INSTANCE;
    private Dictiographer view;

    public static ViewController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewController();
        }
        return INSTANCE;
    }

    private ViewController() {
        try {
            Constants.PROPS.load(Launcher.class.getClassLoader().getResourceAsStream("app.properties"));
            FileInputStream fis = new FileInputStream(getUserPropsFilePath());
            Constants.PROPS.loadFromXML(fis);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            File f = new File(Constants.PROPS.getProperty(Constants.DATA_FOLDER_PROP_KEY));
            if (!f.exists()) {
                f.mkdirs();
            }
            IndexModel.getInstance().domains = f.list();
            Arrays.sort(IndexModel.getInstance().domains);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                view = new Dictiographer(ViewController.this);
                ViewController.this.setView(view);
                view.setVisible(true);
            }
        });
    }

    public void setView(Dictiographer v) {
        this.view = v;
    }

    public void onClosing() {
        Constants.PROPS.setProperty(Constants.X_POS_PROP_KEY, String.valueOf(view.getLocation().getX()));
        Constants.PROPS.setProperty(Constants.Y_POS_PROP_KEY, String.valueOf(view.getLocation().getY()));
        Constants.PROPS.setProperty(Constants.WIDTH_PROP_KEY, String.valueOf(view.getSize().getWidth()));
        Constants.PROPS.setProperty(Constants.HEIGHT_PROP_KEY, String.valueOf(view.getSize().getHeight()));
        Constants.PROPS.setProperty(Constants.DIVIDER_PROP_KEY, String.valueOf(view.getDividerLocation()));
        Constants.PROPS.setProperty(Constants.SELECTED_DOMAIN_PROP_KEY, String.valueOf(view.getSelectedDomain()));
        Constants.PROPS.setProperty(Constants.SELECTED_WORD_PROP_KEY, String.valueOf(view.getSelectedWord()));

        try {
            FileOutputStream fos = new FileOutputStream(getUserPropsFilePath());
            Constants.PROPS.storeToXML(fos, "Dictiographer user properties", "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        view.dispose();
    }

    private String getUserPropsFilePath() {
        return System.getProperty("user.home") + File.separator + "dictiographer.properties";
    }

    public String[] getIndex(String domain) {
        try {
            File f = new File(Constants.PROPS.getProperty(Constants.DATA_FOLDER_PROP_KEY) + File.separator + domain);
            Set<String> s = new TreeSet<String>();
            String[] fns = f.list();
            for (String fn : fns) {
                s.add(URLDecoder.decode(fn, "UTF-8"));
            }
            return s.toArray(new String[s.size()]);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new String[]{};
        }
    }
}
