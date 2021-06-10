package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.view.View;
import com.dictiographer.desktop.model.Constants;
import com.dictiographer.desktop.model.Model;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExitHandler extends BaseHandler {
    protected WindowCloseHandler windowCloseHandler;


    public ExitHandler(Model m, View v, Presenter p) {
        super(m, v, p);
        windowCloseHandler = new WindowCloseHandler();
    }

    class WindowCloseHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            model.properties.setProperty(Constants.PropKeys.PROP_WIDTH.name(), String.valueOf(view.getWidth()));
            model.properties.setProperty(Constants.PropKeys.PROP_HEIGHT.name(), String.valueOf(view.getHeight()));
            model.properties.setProperty(Constants.PropKeys.PROP_POS_X.name(), String.valueOf((int) view.getLocation().getX()));
            model.properties.setProperty(Constants.PropKeys.PROP_POS_Y.name(), String.valueOf((int) view.getLocation().getY()));

            try {
                Files.deleteIfExists(Paths.get(getUserPropertiesFileName()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                FileOutputStream fos = new FileOutputStream(getUserPropertiesFileName());
                model.properties.store(fos, "");
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            e.getWindow().dispose();
            System.exit(0);
        }
    }
}
