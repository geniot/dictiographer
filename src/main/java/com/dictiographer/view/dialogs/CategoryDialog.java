package com.dictiographer.view.dialogs;

import entry.SemanticCategory;
import com.dictiographer.view.Bindable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * User: Vitaly Sazanovich
 * Date: 20/06/13
 * Time: 18:53
 * Email: Vitaly.Sazanovich@gmail.com
 */

public class CategoryDialog extends AbstractDialog implements Bindable{
    Bindable parent;
    JList anchorsList;
    JTree catsTree;

    public Container container;

//    public CategoryDialog(Bindable p, SemanticCategory category) {
//        parent = p;
////        init("descriptors/CategoryDialog.xml");
////        if (category != null) {
//            setData(category);
////        }
//        container.setVisible(true);
//    }

    public Action saveAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            parent.setData(getData(null));
            ((JDialog) container).dispose();
        }
    };

    public CategoryDialog(Window view, ModalityType applicationModal) {
        super(view, applicationModal,null);
    }


    public static void main(String[] args) {
        new CategoryDialog(null, null);
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

    @Override
    public JPanel getMainPanel() {
        return null;
    }
}

