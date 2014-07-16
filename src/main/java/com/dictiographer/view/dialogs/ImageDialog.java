package com.dictiographer.view.dialogs;

import com.dictiographer.view.Bindable;
import com.dictiographer.view.DnDTabbedPane;
import com.dictiographer.view.panels.BeeldPanel;
import com.dictiographer.view.panels.FormContentPanel;
import entry.EntryImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * User: Vitaly Sazanovich
 * Date: 12/27/12
 * Time: 12:47 AM
 */
public class ImageDialog extends AbstractDialog implements Bindable {
    public Container container;
    public DnDTabbedPane beeldDndTabbedPane;
    Bindable parent;

//    public ImageDialog(Bindable p, EntryImage[] eom) {
//        parent = p;
////        init("descriptors/ImageDialog.xml");
//
//        if (eom != null) {
//            setData(eom);
//        } else {
//            beeldDndTabbedPane.addNewTab(null);
//        }
//        container.setVisible(true);
//    }

    public ImageDialog(Window view, ModalityType applicationModal) {
        super(view, applicationModal, null);
    }


    public static void main(String[] args) {
        new ImageDialog(null, null);
    }

    public Action saveAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            EntryImage[] eis = (EntryImage[]) getData(null);
            parent.setData(eis);
            ((JDialog) container).dispose();
        }
    };


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

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public JPanel getMainPanel() {
        return null;
    }
}
