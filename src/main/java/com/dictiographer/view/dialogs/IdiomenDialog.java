package com.dictiographer.view.dialogs;

import entry.Idioom;
import com.dictiographer.view.AbstractContainerRenderer;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.DnDTabbedPane;
import com.dictiographer.view.panels.FormContentPanel;
import com.dictiographer.view.panels.IdioomPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 6:39 PM
 */
public class IdiomenDialog extends AbstractContainerRenderer {
    public DnDTabbedPane idDndTabbedPane;

    public IdiomenDialog(Bindable p, Idioom[] eom) {
        parent = p;
        init("descriptors/IdiomenDialog.xml");

        if (eom != null) {
            setData(eom);
        } else {
            idDndTabbedPane.addNewTab(null);
        }
        container.setVisible(true);
    }


    public static void main(String[] args) {
        new IdiomenDialog(null, null);
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
