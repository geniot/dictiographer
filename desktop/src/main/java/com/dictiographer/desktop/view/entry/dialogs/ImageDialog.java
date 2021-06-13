package com.dictiographer.desktop.view.entry.dialogs;

import com.dictiographer.desktop.model.entry.EntryImage;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.entry.DnDTabbedPane;
import com.dictiographer.desktop.view.entry.MySwingEngine;
import com.dictiographer.desktop.view.entry.MyThreadLocal;
import com.dictiographer.desktop.view.entry.panels.BeeldPanel;
import com.dictiographer.desktop.view.entry.panels.FormContentPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ImageDialog extends AbstractDialog {
    private ImagesDialogPanel imagesDialogPanel;

    public ImageDialog(Window view, Bindable p, EntryImage[] eom) {
        super(view, ModalityType.APPLICATION_MODAL, p);
        this.setSize(900, 700);
        setTitle(MyThreadLocal.get().getMessageSource().getString("title.images"));
        imagesDialogPanel = new ImagesDialogPanel();
        setContentPane(imagesDialogPanel.mainPanel);
        setLocationRelativeTo(view);
        if (eom != null) {
            imagesDialogPanel.setData(eom);
        }
    }

    public class ImagesDialogPanel extends MySwingEngine {
        public DnDTabbedPane beeldDndTabbedPane;

        public ImagesDialogPanel() {
            init("descriptors/ImageDialog.xml");
            beeldDndTabbedPane.addNewTab(null);
        }

        @Override
        public void setData(Object d) {
            EntryImage[] data = (EntryImage[]) d;

            for (int i = 0; i < data.length; i++) {
                Component c = beeldDndTabbedPane.getComponentAt(i);
                if (c != null && c instanceof FormContentPanel) {
                    FormContentPanel form = (FormContentPanel) c;
                    BeeldPanel beeldPanel = (BeeldPanel) form.getForm();
                    beeldPanel.setData(data[i]);
                } else {
                    beeldDndTabbedPane.addNewTab(data[i]);
                }
            }

            beeldDndTabbedPane.setSelectedIndex(0);
        }

        @Override
        public Object getData(Object data) {
            ArrayList<EntryImage> entryImages = new ArrayList();
            for (int i = 0; i < beeldDndTabbedPane.getTabCount() - 1; i++) {
                Component c = beeldDndTabbedPane.getComponentAt(i);
                if (c != null && c instanceof FormContentPanel) {
                    FormContentPanel form = (FormContentPanel) c;
                    BeeldPanel beeldPanel = (BeeldPanel) form.getForm();
                    if (beeldPanel.isEmpty()) continue;
                    EntryImage entryImage = new EntryImage();
                    beeldPanel.getData(entryImage);
                    entryImages.add(entryImage);
                }
            }
            EntryImage[] eis = entryImages.toArray(new EntryImage[entryImages.size()]);
            return eis;
        }

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                EntryImage[] eis = (EntryImage[]) getData(null);
                parent.setData(eis);
                ImageDialog.this.dispose();
            }
        };


        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
