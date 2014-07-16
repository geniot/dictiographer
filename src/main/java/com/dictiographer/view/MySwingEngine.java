package com.dictiographer.view;

import org.swixml.Localizer;
import org.swixml.SwingEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * User: Vitaly Sazanovich
 * Date: 1/13/13
 * Time: 10:13 PM
 */


public class MySwingEngine extends SwingEngine {
    public JPanel mainPanel;
    public Container container;

    public MySwingEngine(String desc){
        try {
            getTaglib().registerTag("dndtabbedpane", DnDTabbedPane.class);
            getTaglib().registerTag("layeredpane", JLayeredPane.class);
            container = render(desc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Localizer getLocalizer() {
        return MyThreadLocal.get().getLocalizer();
    }

    protected Window getClosestWindow(Container p) {
        while (p != null) {
            if (p instanceof Window) {
                return (Window) p;
            }
            p = p.getParent();
        }
        return null;
    }
}