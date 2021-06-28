package com.dictiographer.desktop;

import com.dictiographer.desktop.event.DictionaryDeletedEvent;
import com.dictiographer.desktop.event.EventService;
import io.github.geniot.dictiographer.model.IDictionary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DictionaryPopupMenu extends JPopupMenu {
    private JMenuItem deleteMenuItem;
    private JMenuItem editMenuItem;
    private DictionaryToggleButton dictionaryToggleButton;
    private MainFrame mainFrame;

    public DictionaryPopupMenu(DictionaryToggleButton b, MainFrame mf) {
        this.dictionaryToggleButton = b;
        this.mainFrame = mf;

        deleteMenuItem = new JMenuItem("Delete");
        editMenuItem = new JMenuItem("Edit");

        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    IDictionary dictionary = dictionaryToggleButton.getDictionary();
                    String indexLanguage = dictionary.getIndexLanguage();
                    String contentsLanguage = dictionary.getContentsLanguage();
                    Files.delete(Paths.get(Constants.DICT_DIR_NAME + File.separator + dictionary.getId()));
                    EventService.getInstance().publish(new DictionaryDeletedEvent(indexLanguage, contentsLanguage, dictionaryToggleButton));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        editMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                IDictionary dictionary = dictionaryToggleButton.getDictionary();
                new DictionaryDialog(mainFrame, dictionary).setVisible(true);
            }
        });

        add(editMenuItem);
        add(deleteMenuItem);
    }
}
