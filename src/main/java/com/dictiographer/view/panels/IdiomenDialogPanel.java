package com.dictiographer.view.panels;

import com.dictiographer.view.AbstractContainerRenderer;
import com.dictiographer.view.DnDTabbedPane;
import entry.Idioom;

import java.awt.*;
import java.util.ArrayList;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 10/07/14
 */
public class IdiomenDialogPanel extends AbstractContainerRenderer {
    public DnDTabbedPane idDndTabbedPane;

    public IdiomenDialogPanel() {
        init("descriptors/IdiomenDialog.xml");
    }

    @Override
    public void setData(Object d) {
        Idioom[] data = (Idioom[]) d;
        for (int i = 0; i < data.length; i++) {
            Component c = idDndTabbedPane.getComponentAt(i);
            if (c != null && c instanceof FormContentPanel) {
                FormContentPanel form = (FormContentPanel) c;
                IdioomPanel idioomPanel = (IdioomPanel) form.getForm();
                idioomPanel.setData(data[i]);
            } else {
                idDndTabbedPane.addNewTab(data[i]);
            }
        }
        idDndTabbedPane.setSelectedIndex(0);
    }

    @Override
    public Object getData(Object data) {
        ArrayList<Idioom> poses = new ArrayList();
        for (int i = 0; i < idDndTabbedPane.getTabCount() - 1; i++) {
            Component c = idDndTabbedPane.getComponentAt(i);
            if (c != null && c instanceof FormContentPanel) {
                FormContentPanel form = (FormContentPanel) c;
                IdioomPanel idioomPanel = (IdioomPanel) form.getForm();
                if (idioomPanel.isEmpty()) continue;
                Idioom pos = new Idioom();
                idioomPanel.getData(pos);
                poses.add(pos);
            }
        }
        Idioom[] ids = poses.toArray(new Idioom[poses.size()]);
        return ids;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < idDndTabbedPane.getTabCount() - 1; i++) {
            Component c = idDndTabbedPane.getComponentAt(i);
            if (c != null && c instanceof FormContentPanel) {
                FormContentPanel form = (FormContentPanel) c;
                IdioomPanel idioomPanel = (IdioomPanel) form.getForm();
                if (!idioomPanel.isEmpty()) return false;
            }
        }
        return true;
    }
}
