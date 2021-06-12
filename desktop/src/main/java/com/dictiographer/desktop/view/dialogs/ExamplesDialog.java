package com.dictiographer.desktop.view.dialogs;

import com.dictiographer.desktop.model.entry.Example;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.DnDTabbedPane;
import com.dictiographer.desktop.view.MySwingEngine;
import com.dictiographer.desktop.view.MyThreadLocal;
import com.dictiographer.desktop.view.panels.ExPanel;
import com.dictiographer.desktop.view.panels.FormContentPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ExamplesDialog extends AbstractDialog {

    private ExamplesDialogPanel examplesDialogPanel;

    public ExamplesDialog(Window view, Bindable p, Example[] eom) {
        super(view, ModalityType.APPLICATION_MODAL, p);
        this.setSize(900, 700);
        setTitle(MyThreadLocal.get().getMessageSource().getString("title.examples"));
        examplesDialogPanel = new ExamplesDialogPanel();
        setContentPane(examplesDialogPanel.mainPanel);
        setLocationRelativeTo(view);
        if (eom != null) {
            examplesDialogPanel.setData(eom);
        }
    }


    public class ExamplesDialogPanel extends MySwingEngine {
        public DnDTabbedPane exDndTabbedPane;

        public ExamplesDialogPanel() {
            init("descriptors/ExamplesDialog.xml");
            exDndTabbedPane.addNewTab(null);
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

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                parent.setData(getData(null));
                getClosestWindow(getMainPanel()).dispose();
            }
        };
    }
}

