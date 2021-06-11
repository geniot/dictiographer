package com.dictiographer.desktop.view;

import com.dictiographer.desktop.presenter.Presenter;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    public Presenter presenter;
    public JPanel cards;

    public QuickHelpPanel quickHelpPanel;
    public MainPanel mainPanel;
    public JPanel emptyPanel;

    public CardLayout cardLayout;

    public enum MainViews {
        MAIN, QUICK_HELP, EMPTY
    }

    public View(Presenter p) {
        this.presenter = p;
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        quickHelpPanel = new QuickHelpPanel();
        mainPanel = new MainPanel();
        emptyPanel = new JPanel();

        cards.add(mainPanel.$$$getRootComponent$$$(), MainViews.MAIN.name());
        cards.add(quickHelpPanel.$$$getRootComponent$$$(), MainViews.QUICK_HELP.name());
        cards.add(emptyPanel, MainViews.EMPTY.name());

        cardLayout.show(cards, MainViews.EMPTY.name());

        getContentPane().add(cards, BorderLayout.CENTER);
    }
}
