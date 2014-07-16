package com.dictiographer.view;

import org.swixml.Localizer;
import org.swixml.SwingEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * User: Vitaly Sazanovich
 * Date: 1/13/13
 * Time: 10:13 PM
 */


public abstract class MySwingEngine extends SwingEngine implements Bindable {
    public JPanel mainPanel;
    public Container container;

    public void init(String desc) {
        try {
            getTaglib().registerTag("dndtabbedpane", DnDTabbedPane.class);
            getTaglib().registerTag("layeredpane", JLayeredPane.class);
            container = render(desc);

            mainPanel.registerKeyboardAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onCancel();
                }
            }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCancel() {
        getClosestWindow(getMainPanel()).dispose();
    }

    @Override
    public abstract void setData(Object d);

    @Override
    public abstract Object getData(Object d);

    @Override
    public abstract boolean isEmpty();

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
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