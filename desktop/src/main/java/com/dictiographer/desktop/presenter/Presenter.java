package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.view.View;
import com.dictiographer.desktop.model.DictionariesMap;
import com.dictiographer.desktop.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

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
            dictionaryHandler.onDictionariesUpdated((DictionariesMap) arg);
        }
    }
}
