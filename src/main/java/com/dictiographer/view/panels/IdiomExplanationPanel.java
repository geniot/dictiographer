package com.dictiographer.view.panels;

import com.dictiographer.view.Bindable;
import com.dictiographer.view.MySwingEngine;
import entry.IdiomExplanation;
import entry.Translation;

import javax.swing.*;
import java.util.ArrayList;

/**
 * User: Vitaly Sazanovich
 * Date: 23/01/13
 * Time: 19:44
 * Email: Vitaly.Sazanovich@gmail.com
 */
public class IdiomExplanationPanel extends MySwingEngine implements Bindable {
    public JTextField meta;
    public JTextField explanation;
    public Box translations;

    public Box contentPanel;

    public IdiomExplanationPanel() {
        super("descriptors/IdiomExplanationPanel.xml");
    }

    protected void addSingleTranslation(Translation tr) {
        SingleTranslationPanel stp = new SingleTranslationPanel(contentPanel);
        if (tr != null) {
            stp.setData(tr);
        }
        FormContentPanel formContentPanel = new FormContentPanel(stp.getMainPanel(), stp);
        contentPanel.add(formContentPanel, contentPanel.getComponentCount());
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    @Override
    public void setData(Object d) {
        if (d == null) return;
        IdiomExplanation data = (IdiomExplanation) d;
        if (data.getExplanation() != null) explanation.setText(data.getExplanation());
        if (data.getMeta() != null) meta.setText(data.getMeta());
        if (data.getTranslations() != null) {
            for (Translation ex : data.getTranslations()) {
                addSingleTranslation(ex);
            }
        }
    }

    @Override
    public Object getData(Object d) {
        IdiomExplanation data = (IdiomExplanation) d;
        if (!explanation.getText().trim().equals("")) data.setExplanation(explanation.getText().trim());
        if (!meta.getText().trim().equals("")) data.setMeta(meta.getText().trim());
        ArrayList<Translation> translations = new ArrayList();
        for (int i = 0; i < contentPanel.getComponentCount(); i++) {
            FormContentPanel fcp = (FormContentPanel) contentPanel.getComponent(i);
            SingleTranslationPanel singleTranslationPanel = (SingleTranslationPanel) fcp.getForm();
            if (singleTranslationPanel.isEmpty()) continue;
            Translation t = new Translation();
            singleTranslationPanel.getData(t);
            translations.add(t);
        }
        data.setTranslations(translations.toArray(new Translation[translations.size()]));
        return data;
    }

    @Override
    public boolean isEmpty() {
        if (!explanation.getText().trim().equals("")) return false;
        if (!meta.getText().trim().equals("")) return false;
        for (int i = 0; i < contentPanel.getComponentCount(); i++) {
            FormContentPanel fcp = (FormContentPanel) contentPanel.getComponent(i);
            SingleTranslationPanel stf = (SingleTranslationPanel) fcp.getForm();
            if (!stf.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public JPanel getMainPanel() {
        return null;
    }
}
