package com.dictiographer.desktop.view.dialogs;

import com.dictiographer.desktop.model.entry.Example;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.MySwingEngine;
import com.dictiographer.desktop.view.MyThreadLocal;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CategoryDialog extends AbstractDialog {

    private CategoryDialogPanel categoryDialogPanel;

    public CategoryDialog(Window view, Bindable p, Example[] eom) {
        super(view, ModalityType.APPLICATION_MODAL, p);
        this.setSize(900, 700);
        setTitle(MyThreadLocal.get().getMessageSource().getString("title.category"));
        categoryDialogPanel = new CategoryDialogPanel();
        setContentPane(categoryDialogPanel.mainPanel);
        setLocationRelativeTo(view);
        if (eom != null) {
            categoryDialogPanel.setData(eom);
        }
    }


    public class CategoryDialogPanel extends MySwingEngine {
        public JList anchorsList;
        public JTree catsTree;

        public CategoryDialogPanel() {
            init("descriptors/CategoryDialog.xml");
        }

        @Override
        public void setData(Object d) {
//        if (d == null) return;
//        SemanticCategory data = (SemanticCategory) d;
            DefaultListModel model = new DefaultListModel();
            model.addElement("test");
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
            DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child1");
            DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child2");

            child1.add(child2);
            rootNode.add(child1);

            TreeModel treeModel = new DefaultTreeModel(rootNode);
            catsTree.setModel(treeModel);
            anchorsList.setModel(model);
        }


        @Override
        public Object getData(Object data) {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                parent.setData(getData(null));
                ((JDialog) container).dispose();
            }
        };
    }
}

