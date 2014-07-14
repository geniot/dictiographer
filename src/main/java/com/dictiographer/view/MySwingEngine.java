package com.dictiographer.view;

import org.swixml.Localizer;
import org.swixml.SwingEngine;

/**
 * User: Vitaly Sazanovich
 * Date: 1/13/13
 * Time: 10:13 PM
 */


public class MySwingEngine extends SwingEngine {

    public Localizer getLocalizer() {
        return MyThreadLocal.get().getLocalizer();
    }
}