package com.dictiographer.view;

import com.dictiographer.model.Constants;
import org.swixml.*;

/**
 * User: Vitaly Sazanovich
 * Date: 1/13/13
 * Time: 10:13 PM
 */


public class MySwingEngine extends SwingEngine{
    public Localizer getLocalizer() {
        return Constants.LOCALIZER;
    }
}