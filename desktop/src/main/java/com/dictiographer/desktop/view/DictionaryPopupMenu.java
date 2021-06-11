package com.dictiographer.desktop.view;

import com.dictiographer.desktop.presenter.Presenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DictionaryPopupMenu extends JPopupMenu {
    JMenuItem deleteMenuItem;
    Presenter presenter;
    DictionaryToggleButton dictionaryToggleButton;

    public DictionaryPopupMenu(Presenter p, DictionaryToggleButton b) {
        this.presenter = p;
        this.dictionaryToggleButton = b;

        deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.onDictionaryDelete(dictionaryToggleButton.dictionary);
            }
        });
        add(deleteMenuItem);
    }
}
