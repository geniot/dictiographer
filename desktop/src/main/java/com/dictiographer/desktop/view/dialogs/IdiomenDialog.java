package com.dictiographer.desktop.view.dialogs;

import com.dictiographer.desktop.model.entry.Idioom;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.DnDTabbedPane;
import com.dictiographer.desktop.view.MySwingEngine;
import com.dictiographer.desktop.view.MyThreadLocal;
import com.dictiographer.desktop.view.panels.FormContentPanel;
import com.dictiographer.desktop.view.panels.IdioomPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class IdiomenDialog extends AbstractDialog {
    private IdiomenDialogPanel idiomenDialogPanel;

    public IdiomenDialog(Window view, Bindable b, Idioom[] idiooms) {
        super(view, ModalityType.APPLICATION_MODAL, b);
        this.setSize(900, 700);
        setTitle(MyThreadLocal.get().getMessageSource().getString("title.idioms"));
        idiomenDialogPanel = new IdiomenDialogPanel();
        setContentPane(idiomenDialogPanel.mainPanel);
        setLocationRelativeTo(view);
        if (idiooms != null) {
            idiomenDialogPanel.setData(idiooms);
        }

    }

    public class IdiomenDialogPanel extends MySwingEngine implements Bindable {
        public DnDTabbedPane idDndTabbedPane;

        public IdiomenDialogPanel() {
            init("descriptors/IdiomenDialog.xml");
            idDndTabbedPane.addNewTab(null);
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
        public void onCancel() {
            getClosestWindow(mainPanel).dispose();
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
                    Idioom idiom = new Idioom();
                    idioomPanel.getData(idiom);
                    poses.add(idiom);
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

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                parent.setData(idiomenDialogPanel.getData(null));
                IdiomenDialog.this.dispose();
            }
        };

        public Action cancelAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                IdiomenDialog.this.dispose();
            }
        };


    }


}
