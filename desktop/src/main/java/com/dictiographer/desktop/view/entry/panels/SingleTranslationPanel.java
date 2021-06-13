package com.dictiographer.desktop.view.entry.panels;

import com.dictiographer.desktop.model.entry.Translation;
import com.dictiographer.desktop.view.entry.MySwingEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SingleTranslationPanel extends MySwingEngine {
    Container parent;
    public JComboBox locale;
    public JTextField translation;

    public SingleTranslationPanel(Container p) {
        init("descriptors/SingleTranslationPanel.xml");
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

}
