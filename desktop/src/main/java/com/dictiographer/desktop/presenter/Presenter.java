package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.Model;
import com.dictiographer.desktop.view.DictionaryToggleButton;
import com.dictiographer.desktop.view.ExtendedCardLayout;
import com.dictiographer.desktop.view.View;
import com.dictiographer.shared.model.IDictionary;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class Presenter extends AbstractAction implements Observer, ChangeListener, ListSelectionListener {

    private static Presenter presenter;
    private Model model;
    private View view;

    protected InitHandler initHandler;
    protected ExitHandler exitHandler;
    protected KeyHandler keyHandler;
    protected DictionaryHandler dictionaryHandler;
    public EntryHandler entryHandler;
    protected IndexHandler indexHandler;


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
        entryHandler = new EntryHandler(model, view, this);
        indexHandler = new IndexHandler(model, view, this);

        initHandler.handle();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof DictionaryToggleButton) {

        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof IDictionary) {
            dictionaryHandler.onDictionariesUpdated(model.getDictionaries());
        } else if (arg instanceof String) {
            indexHandler.handle();
        }
    }


    /**
     * When selected index language tab is changed.
     *
     * @param e
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(view.mainPanel.indexTabbedPane)) {
            int selectedIndex = view.mainPanel.indexTabbedPane.getSelectedIndex();
            if (selectedIndex >= 0) { //selectedIndex = -1 when a tab is removed
                String selectedTitle = view.mainPanel.indexTabbedPane.getTitleAt(selectedIndex);
                ExtendedCardLayout cardLayout = (ExtendedCardLayout) view.mainPanel.cardsPanel.getLayout();
                cardLayout.show(view.mainPanel.cardsPanel, selectedTitle);
            }
        }
    }

    public void onDictionaryDelete(IDictionary dictionary) {
        model.deleteDictionary(dictionary);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        String selectedHeadword = view.getSelectedHeadword();
        if (selectedHeadword != null && !selectedHeadword.equals("")) {
            entryHandler.showEntry(selectedHeadword);
        }
    }

    public void onDictionarySelectionChanged() {
        indexHandler.handle();
    }
}
