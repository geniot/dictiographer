package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.DictionariesMap;
import com.dictiographer.desktop.model.Model;
import com.dictiographer.desktop.view.ContentsPanel;
import com.dictiographer.desktop.view.IndexPanel;
import com.dictiographer.desktop.view.View;
import com.dictiographer.desktop.view.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedMap;
import java.util.SortedSet;

public class Presenter extends AbstractAction implements Observer {

    private static Presenter presenter;
    private Model model;
    private View view;

    protected InitHandler initHandler;
    protected ExitHandler exitHandler;
    protected KeyHandler keyHandler;
    protected DictionaryHandler dictionaryHandler;


    public static Presenter getInstance() {
        if (presenter == null) {
            presenter = new Presenter();
        }
        return presenter;
    }

    public void init() {
        model = new Model();
        model.addObserver(this);
        view = new View(this);

        initHandler = new InitHandler(model, view, this);
        exitHandler = new ExitHandler(model, view, this);
        keyHandler = new KeyHandler(model, view, this);
        dictionaryHandler = new DictionaryHandler(model, view, this);

        initHandler.handle();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof DictionariesMap) {
            onDictionariesUpdated((DictionariesMap) arg);
        }
    }

    public void onDictionariesUpdated(DictionariesMap dictionariesMap) {
        if (dictionariesMap.isEmpty()) {
            view.cardLayout.show(view.cards, View.MainViews.QUICK_HELP.name());
        } else {
            SortedMap<String, SortedSet<String>> languages = dictionariesMap.getLanguages();
            view.mainPanel.indexTabbedPane.removeAll();
            view.mainPanel.contentsPanel.removeAll();
            for (String indexLanguage : languages.keySet()) {
                view.mainPanel.indexTabbedPane.addTab(indexLanguage, new IndexPanel().contentPanel);

                SortedSet<String> contentLanguages = languages.get(indexLanguage);
                JTabbedPane contentsTabbedPane = new JTabbedPane();
                contentsTabbedPane.setFocusable(false);
                contentsTabbedPane.setRequestFocusEnabled(false);
                contentsTabbedPane.setTabPlacement(JTabbedPane.RIGHT);

                WrapLayout wrapLayout = new WrapLayout();
                wrapLayout.setHgap(1);
                wrapLayout.setVgap(1);
                wrapLayout.setAlignment(FlowLayout.LEFT);

                for (String contentLanguage : contentLanguages) {
                    ContentsPanel contentsPanel = new ContentsPanel();
                    contentsPanel.dictionariesPanel.setLayout(wrapLayout);
                    contentsTabbedPane.add(contentsPanel.mainPanel, contentLanguage);
                }

                view.mainPanel.contentsPanel.add(contentsTabbedPane);


            }

            view.cardLayout.show(view.cards, View.MainViews.MAIN.name());
        }
    }
}
