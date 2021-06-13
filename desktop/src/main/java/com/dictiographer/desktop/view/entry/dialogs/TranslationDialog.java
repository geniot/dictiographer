package com.dictiographer.desktop.view.entry.dialogs;

import com.dictiographer.desktop.model.entry.Translation;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.entry.MySwingEngine;
import com.dictiographer.desktop.view.entry.MyThreadLocal;
import com.dictiographer.desktop.view.entry.panels.FormContentPanel;
import com.dictiographer.desktop.view.entry.panels.SingleTranslationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TranslationDialog extends AbstractDialog {
    private TranslationDialogPanel translationDialogPanel;

    public TranslationDialog(Window view, Bindable p, Translation[] eom) {
        super(view, ModalityType.APPLICATION_MODAL, p);
        this.setSize(900, 700);
        setTitle(MyThreadLocal.get().getMessageSource().getString("title.translations"));
        translationDialogPanel = new TranslationDialogPanel();
        setContentPane(translationDialogPanel.mainPanel);
        setLocationRelativeTo(view);
        if (eom != null) {
            translationDialogPanel.setData(eom);
        }
    }


    public class TranslationDialogPanel extends MySwingEngine {
        public Box contentPanel;

        public TranslationDialogPanel() {
            init("descriptors/TranslationDialog.xml");
        }

        public Action addTranslationAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                addSingleTranslation(null);
            }
        };

        @Override
        public void setData(Object d) {
            if (d == null) return;
            Translation[] data = (Translation[]) d;
            for (Translation ex : data) {
                addSingleTranslation(ex);
            }
        }

        @Override
        public Object getData(Object data) {
            Translation[] translations = new Translation[contentPanel.getComponentCount()];
            for (int i = 0; i < translations.length; i++) {
                FormContentPanel fcp = (FormContentPanel) contentPanel.getComponent(i);
                SingleTranslationPanel stf = (SingleTranslationPanel) fcp.getForm();
                Translation t = new Translation();
                stf.getData(t);
                translations[i] = t;
            }
            return translations;
        }

        @Override
        public boolean isEmpty() {
            return false;
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

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                parent.setData(getData(null));
                TranslationDialog.this.dispose();
            }
        };
    }

}
