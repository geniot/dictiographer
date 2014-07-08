package com.dictiographer.view.panels;

import entry.IdiomExplanation;
import entry.Idioom;
import com.dictiographer.view.AbstractContainerRenderer;
import com.dictiographer.view.DnDTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 11:09 PM
 */
public class IdioomPanel extends AbstractContainerRenderer {

    public JTextField idioom;
    public DnDTabbedPane idDndTabbedPane;

    public IdioomPanel() {
        init("com/dictiography/client/ui/descriptors/IdioomPanel.xml");
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
