package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.Constants;
import com.dictiographer.desktop.model.DictionariesMap;
import com.dictiographer.desktop.model.Model;
import com.dictiographer.desktop.view.*;
import com.dictiographer.shared.model.IDictionary;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.SortedMap;
import java.util.SortedSet;

public class BaseHandler {
    protected Model model;
    protected View view;

    protected Presenter presenter;

    public BaseHandler(Model m, View v, Presenter p) {
        this.model = m;
        this.view = v;
        this.presenter = p;
    }

    protected String getUserPropertiesFileName() {
        return System.getProperty("user.home") + File.separator + Constants.PROPS_FILE_NAME;
    }

    public void onDictionariesUpdated(DictionariesMap dictionariesMap) {
        if (dictionariesMap.isEmpty()) {
            view.cardLayout.show(view.cards, View.MainViews.QUICK_HELP.name());
        } else {
            //removing panels
            view.mainPanel.indexTabbedPane.removeAll();
            view.mainPanel.cardsPanel.removeAll();
            //adding panels
            SortedMap<String, SortedSet<String>> languages = dictionariesMap.getLanguages();
            for (String indexLanguage : languages.keySet()) {
                view.mainPanel.indexTabbedPane.addTab(indexLanguage, new IndexPanel(presenter).contentPanel);

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
                    SortedSet<IDictionary> shelfDictionaries = dictionariesMap.getShelfDictionaries(indexLanguage, contentLanguage);
                    for (IDictionary dictionary : shelfDictionaries) {
                        contentsPanel.dictionariesPanel.add(new DictionaryToggleButton(presenter, dictionary));
                    }
                    contentsTabbedPane.add(contentsPanel.mainPanel, contentLanguage);
                }
                view.mainPanel.cardsPanel.add(contentsTabbedPane, indexLanguage);
            }

            view.cardLayout.show(view.cards, View.MainViews.MAIN.name());
        }
    }
}
