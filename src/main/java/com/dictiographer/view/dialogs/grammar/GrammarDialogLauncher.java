package com.dictiographer.view.dialogs.grammar;

import com.dictiographer.view.Bindable;
import com.dictiographer.view.MyThreadLocal;
import entry.Grammar;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 01/07/14
 */
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
