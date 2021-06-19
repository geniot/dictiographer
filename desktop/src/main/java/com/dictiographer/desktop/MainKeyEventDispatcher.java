package com.dictiographer.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainKeyEventDispatcher implements KeyEventDispatcher {
    private MainFrame mainFrame;

    public MainKeyEventDispatcher(MainFrame f) {
        this.mainFrame = f;
    }

    private boolean isModalDialogShowing() {
        Window[] windows = Window.getWindows();
        if (windows != null) { // don't rely on current implementation, which at least returns [0].
            for (Window w : windows) {
//                if (w.isShowing() && w instanceof JDialog && ((JDialog) w).isModal())
                if (w.isShowing() && w instanceof JDialog)
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        boolean isPressed = e.getID() == KeyEvent.KEY_PRESSED;
        //filtering out key released event
        if (!isPressed) {
            return false;
        }
        //new dictionary on CTRL+D
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D && !isModalDialogShowing()) {
            new DictionaryDialog(mainFrame).setVisible(true);
            return false;
        }
        //new entry on CTRL+E
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E && !isModalDialogShowing()) {
            if (mainFrame.getCurrentShelf(false).size() > 0) {
                new EntryDialog(mainFrame).setVisible(true);
            }
            return false;
        }
        return false;
    }
}
