package com.dictiographer.view.dialogs.grammar;

import entry.Grammar;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.dialogs.grammar.by.GrammarDialogDZE;
import com.dictiographer.view.dialogs.grammar.by.GrammarDialogNAZ;
import com.dictiographer.view.dialogs.grammar.by.GrammarDialogPRY;
import com.dictiographer.view.dialogs.grammar.nl.GrammarDialogBNW;
import com.dictiographer.view.dialogs.grammar.nl.GrammarDialogVNW;
import com.dictiographer.view.dialogs.grammar.nl.GrammarDialogWEW;
import com.dictiographer.view.dialogs.grammar.nl.GrammarDialogZNW;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 01/07/14
 */
public class GrammarDialogLauncher {

    public GrammarDialogLauncher(Bindable p, Grammar grammar, String cn) {
        if (cn.toLowerCase().equals("naz")) {
            new GrammarDialogNAZ(p, grammar, cn);
        } else if (cn.toLowerCase().equals("dze")) {
            new GrammarDialogDZE(p, grammar, cn);
        } else if (cn.toLowerCase().equals("pry")) {
            new GrammarDialogPRY(p, grammar, cn);
        }

        else if (cn.toLowerCase().equals("bnw")) {
            new GrammarDialogBNW(p, grammar, cn);
        } else if (cn.toLowerCase().equals("vnw")) {
            new GrammarDialogVNW(p, grammar, cn);
        } else if (cn.toLowerCase().equals("wew")) {
            new GrammarDialogWEW(p, grammar, cn);
        } else if (cn.toLowerCase().equals("znw")) {
            new GrammarDialogZNW(p, grammar, cn);
        }
    }
}
