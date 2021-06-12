package com.dictiographer.desktop.view.dialogs.grammar;

import com.dictiographer.desktop.model.entry.Grammar;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.MyThreadLocal;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;

public class GrammarDialogLauncher {

    public GrammarDialogLauncher(Window w, Bindable p, Grammar grammar, String cn) {
        try {
            String lang = MyThreadLocal.get().getLocale().getLanguage();
            Class c = Class.forName("com.dictiographer.view.dialogs.grammar." + lang + ".GrammarDialog" + cn.toUpperCase());
            Constructor co = c.getDeclaredConstructors()[0];
            JDialog dialog = (JDialog) co.newInstance(w, Dialog.ModalityType.APPLICATION_MODAL, p, grammar);
            dialog.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
