package com.dictiographer.desktop.view;

import com.dictiographer.desktop.presenter.Presenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryPopupMenu extends JPopupMenu {
    JMenuItem addMenuItem;
    Presenter presenter;

    public EntryPopupMenu(Presenter p) {
        this.presenter = p;

        addMenuItem = new JMenuItem("Add");
        addMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.entryHandler.handle();
            }
        });
        add(addMenuItem);
    }
}
