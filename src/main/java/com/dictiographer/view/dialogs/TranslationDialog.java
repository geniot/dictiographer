package com.dictiographer.view.dialogs;

import entry.Translation;
import com.dictiographer.view.AbstractContainerRenderer;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.panels.FormContentPanel;
import com.dictiographer.view.panels.SingleTranslationPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 6:39 PM
 */
public class TranslationDialog extends AbstractContainerRenderer {
    Bindable parent;

    public TranslationDialog(Bindable p, Translation[] eom) {
        parent = p;
        init("descriptors/TranslationDialog.xml");
        if (eom != null) {
            setData(eom);
        } else {
            addSingleTranslation(null);
        }
        container.setVisible(true);
    }

    public Action saveAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            parent.setData(getData(null));
            ((JDialog) container).dispose();
        }
    };


    public static void main(String[] args) {
        new TranslationDialog(null, null);
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
}
