package com.dictiographer.desktop.view;

import com.dictiographer.desktop.presenter.Presenter;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    public Presenter presenter;
    public JPanel cards;
    private QuickHelpPanel quickHelpPanel;
    public MainPanel mainPanel;
    public CardLayout cardLayout;

    public enum MainViews {
        MAIN, QUICK_HELP
    }

    public View(Presenter p) {
        this.presenter = p;

        quickHelpPanel = new QuickHelpPanel();
        mainPanel = new MainPanel();

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        cards.add(mainPanel.$$$getRootComponent$$$(), MainViews.MAIN.name());
        cards.add(quickHelpPanel.$$$getRootComponent$$$(), MainViews.QUICK_HELP.name());

        cardLayout.show(cards, MainViews.MAIN.name());
        getContentPane().add(cards, BorderLayout.CENTER);
    }
}
