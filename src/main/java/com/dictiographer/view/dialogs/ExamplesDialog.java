package com.dictiographer.view.dialogs;

import entry.Example;
import com.dictiographer.view.AbstractContainerRenderer;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.DnDTabbedPane;
import com.dictiographer.view.panels.ExPanel;
import com.dictiographer.view.panels.FormContentPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * User: Vitaly Sazanovich
 * Date: 16/01/13
 * Time: 15:28
 * Email: Vitaly.Sazanovich@gmail.com
 */
public class ExamplesDialog extends AbstractContainerRenderer {

    public DnDTabbedPane exDndTabbedPane;

    public ExamplesDialog(Bindable p, Example[] eom) {
        parent = p;
        init("descriptors/ExamplesDialog.xml");
        if (eom != null) {
            setData(eom);
        } else {
            exDndTabbedPane.addNewTab(null);
        }
        container.setVisible(true);
    }


    public static void main(String[] args) {
        new ExamplesDialog(null, null);
    }



    @Override
    public void setData(Object d) {
        if (d == null) return;
        Example[] ed = (Example[]) d;
        //examles
        if (ed != null) {
            for (int i = 0; i < ed.length; i++) {
                Component c = exDndTabbedPane.getComponentAt(i);
                if (c != null && c instanceof FormContentPanel) {
                    FormContentPanel form = (FormContentPanel) c;
                    ExPanel exPanel = (ExPanel) form.getForm();
                    exPanel.setData(ed[i]);
                } else {
                    exDndTabbedPane.addNewTab(ed[i]);
                }
            }
        }
        exDndTabbedPane.setSelectedIndex(0);
    }

    @Override
    public Object getData(Object data) {
        ArrayList<Example> exs = new ArrayList();
        for (int i = 0; i < exDndTabbedPane.getTabCount() - 1; i++) {
            Component c = exDndTabbedPane.getComponentAt(i);
            if (c != null && c instanceof FormContentPanel) {
                FormContentPanel form = (FormContentPanel) c;
                ExPanel exForm = (ExPanel) form.getForm();
                if (exForm.isEmpty()) continue;
                Example ex = new Example();
                exForm.getData(ex);
                exs.add(ex);
            }
        }
        return exs.toArray(new Example[exs.size()]);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

