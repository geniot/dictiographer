package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.view.View;
import com.dictiographer.shared.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyHandler extends BaseHandler implements KeyEventDispatcher {
    public KeyHandler(Model m, View v, Presenter p) {
        super(m, v, p);
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
            System.out.println("new dictionary");
            System.out.println(isPressed);
            return false;
        }
        return false;
    }
}
