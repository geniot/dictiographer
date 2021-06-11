package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.DictionariesMap;
import com.dictiographer.desktop.model.Model;
import com.dictiographer.desktop.view.IndexPanel;
import com.dictiographer.desktop.view.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
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
            SortedSet<String> indexLanguages = dictionariesMap.getIndexLanguages();
            for (String indexLanguage : indexLanguages) {
                view.mainPanel.indexTabbedPane.addTab(indexLanguage, new IndexPanel().contentPanel);
            }

//            dialog.searchTextField.setBorder(new EmptyBorder(0, 4, 0, 0));
//            dialog.dictionariesPanel.add(getDic(dialog, "/images/user.png"));
//
//            WrapLayout wrapLayout = new WrapLayout();
//            wrapLayout.setHgap(1);
//            wrapLayout.setVgap(1);
//            wrapLayout.setAlignment(FlowLayout.LEFT);
//            dialog.dictionariesPanel.setLayout(wrapLayout);
            view.cardLayout.show(view.cards, View.MainViews.MAIN.name());
        }
    }

    private static Component getDic(JDialog dialog, String icon) {
        JToggleButton dic = new JToggleButton();
        dic.setFocusPainted(false);
        dic.setBorderPainted(false);
        dic.setFocusable(false);

        dic.setOpaque(true);
        dic.setContentAreaFilled(false);

        dic.setIcon(new ImageIcon(dialog.getClass().getResource(icon)));
        Dimension dm = new Dimension(23, 30);
        dic.setMaximumSize(dm);
        dic.setMinimumSize(dm);
        dic.setPreferredSize(dm);
        dic.setText("");
        dic.setHorizontalAlignment(SwingConstants.CENTER);

        dic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                dic.setBorderPainted(selected);
            }
        });
        return dic;
    }
}
