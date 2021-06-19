package com.dictiographer.desktop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryPopupMenu extends JPopupMenu {
    private JMenuItem addMenuItem;
    private MainFrame mainFrame;

    public EntryPopupMenu(MainFrame mf) {
        this.mainFrame = mf;

        addMenuItem = new JMenuItem("Add");
        addMenuItem.addActionListener(e -> new EntryDialog(mainFrame).setVisible(true));
        add(addMenuItem);
    }
}
