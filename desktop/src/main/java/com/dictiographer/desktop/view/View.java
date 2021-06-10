package com.dictiographer.desktop.view;

import com.dictiographer.desktop.presenter.Presenter;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    public Presenter presenter;
    public JPanel cards;
    private QuickHelpPanel quickHelpPanel;
    public CardLayout cardLayout;

    public enum MainViews {
        EMPTY, QUICK_HELP
    }

    public View(Presenter p) {
        this.presenter = p;
        quickHelpPanel = new QuickHelpPanel();
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.add(new JPanel(), MainViews.EMPTY.name());
        cards.add(quickHelpPanel.contentPanel, MainViews.QUICK_HELP.name());
        cardLayout.show(cards, MainViews.EMPTY.name());
        getContentPane().add(cards, BorderLayout.CENTER);
    }
}
