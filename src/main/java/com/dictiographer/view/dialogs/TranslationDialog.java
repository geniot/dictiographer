package com.dictiographer.view.dialogs;

import com.dictiographer.view.Bindable;
import com.dictiographer.view.MySwingEngine;
import com.dictiographer.view.MyThreadLocal;
import com.dictiographer.view.panels.FormContentPanel;
import com.dictiographer.view.panels.SingleTranslationPanel;
import entry.Translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 6:39 PM
 */
public class TranslationDialog extends AbstractDialog {
    private TranslationDialogPanel translationDialogPanel;

    public TranslationDialog(Window view, Bindable p, Translation[] eom) {
        super(view, ModalityType.APPLICATION_MODAL, p);

        setTitle(MyThreadLocal.get().getMessageSource().getMessage("title.translations", null, MyThreadLocal.get().getLocale()));
        translationDialogPanel = new TranslationDialogPanel();
        setContentPane(translationDialogPanel.mainPanel);

        if (eom != null) {
            translationDialogPanel.setData(eom);
        }
    }


    public class TranslationDialogPanel extends MySwingEngine {
        public Box contentPanel;

        public TranslationDialogPanel() {
            init("descriptors/TranslationDialog.xml");
        }

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
                ((JDialog) container).dispose();
            }
        };
    }

}
