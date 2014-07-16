package com.dictiographer.view.panels;

import com.dictiographer.view.Bindable;
import com.dictiographer.view.MySwingEngine;
import entry.Translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 11:09 PM
 */
public class SingleTranslationPanel extends MySwingEngine implements Bindable {
    Container parent;
    public Container container;

    public SingleTranslationPanel(Container p) {
        super("descriptors/SingleTranslationPanel.xml");
        this.parent = p;

    }

    public Action deleteAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            FormContentPanel parent = (FormContentPanel) container.getParent();
            parent.remove(container);
            parent.revalidate();
            parent.repaint();
            parent.getParent().remove(parent);
        }
    };

    public JComboBox locale;
    public JTextField translation;


    @Override
    public void setData(Object d) {
        Translation e = (Translation) d;
        if (e.getLocale() != null) locale.setSelectedItem(e.getLocale().toUpperCase());
        if (e.getTranslation() != null) translation.setText(e.getTranslation());
    }

    @Override
    public Object getData(Object d) {
        Translation data = (Translation) d;
        data.setLocale(locale.getSelectedItem().toString().toLowerCase());
        data.setTranslation(translation.getText().trim());
        return d;
    }

    @Override
    public boolean isEmpty() {
        return translation.getText().trim().equals("");
    }

    @Override
    public JPanel getMainPanel() {
        return null;
    }
}
