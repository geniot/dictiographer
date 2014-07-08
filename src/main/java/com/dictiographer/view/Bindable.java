package com.dictiographer.view;

import javax.swing.*;

/**
 * User: Vitaly Sazanovich
 * Date: 12/7/12
 * Time: 11:59 PM
 */
public interface Bindable {
    public void setData(Object data);

    public Object getData(Object data);

    public boolean isEmpty();

    public JPanel getMainPanel();

}
