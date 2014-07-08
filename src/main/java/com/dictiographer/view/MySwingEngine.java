package com.dictiographer.view;

import org.swixml.*;

/**
 * User: Vitaly Sazanovich
 * Date: 1/13/13
 * Time: 10:13 PM
 */


public class MySwingEngine extends SwingEngine{
    private MyLocalizer myLocalizer = new MyLocalizer();
    public Localizer getLocalizer() {
        if (myLocalizer==null){
            myLocalizer = new MyLocalizer();
        }
        return myLocalizer;
    }
}