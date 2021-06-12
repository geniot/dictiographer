package com.dictiographer.desktop.view.panels;

import com.dictiographer.desktop.model.entry.IdiomExplanation;
import com.dictiographer.desktop.model.entry.Idioom;
import com.dictiographer.desktop.view.DnDTabbedPane;
import com.dictiographer.desktop.view.MySwingEngine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class IdioomPanel extends MySwingEngine {

    public JTextField idioom;
    public DnDTabbedPane idDndTabbedPane;

    public IdioomPanel() {
        init("descriptors/IdioomPanel.xml");
        idDndTabbedPane.addNewTab(null);
    }

    @Override
    public void setData(Object d) {
        if (d == null) return;
        Idioom data = (Idioom) d;
        if (data.getIdioom() != null) idioom.setText(data.getIdioom());
        //idiom explanations
        if (data.getIdiomExplanations() != null) {
            for (int i = 0; i < data.getIdiomExplanations().length; i++) {
                Component c = idDndTabbedPane.getComponentAt(i);
                if (c != null && c instanceof FormContentPanel) {
                    FormContentPanel form = (FormContentPanel) c;
                    IdiomExplanationPanel idiomExplanationPanel = (IdiomExplanationPanel) form.getForm();
                    idiomExplanationPanel.setData(data.getIdiomExplanations()[i]);
                } else {
                    idDndTabbedPane.addNewTab(data.getIdiomExplanations()[i]);
                }
            }
        }

        idDndTabbedPane.setSelectedIndex(0);
    }

    @Override
    public Object getData(Object d) {
        Idioom data = (Idioom) d;
        if (!idioom.getText().trim().equals("")) data.setIdioom(idioom.getText().trim());
        ArrayList<IdiomExplanation> eds = new ArrayList();
        for (int i = 0; i < idDndTabbedPane.getTabCount() - 1; i++) {
            Component c = idDndTabbedPane.getComponentAt(i);
            if (c != null && c instanceof FormContentPanel) {
                FormContentPanel form = (FormContentPanel) c;
                IdiomExplanationPanel idiomExplanationsPanel = (IdiomExplanationPanel) form.getForm();
                if (idiomExplanationsPanel.isEmpty()) continue;
                IdiomExplanation ie = new IdiomExplanation();
                idiomExplanationsPanel.getData(ie);
                eds.add(ie);
            }
        }
        data.setIdiomExplanations(eds.isEmpty() ? null : eds.toArray(new IdiomExplanation[eds.size()]));
        return data;
    }

    @Override
    public boolean isEmpty() {
        if (!idioom.getText().trim().equals("")) return false;
        for (int i = 0; i < idDndTabbedPane.getTabCount() - 1; i++) {
            Component c = idDndTabbedPane.getComponentAt(i);
            if (c != null && c instanceof FormContentPanel) {
                FormContentPanel form = (FormContentPanel) c;
                IdiomExplanationPanel idiomExplanationPanel = (IdiomExplanationPanel) form.getForm();
                if (!idiomExplanationPanel.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

}
