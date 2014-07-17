package com.dictiographer.view;

import javax.swing.*;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 17/07/14
 */
public class BindableAdapter implements Bindable {
    @Override
    public void setData(Object data) {

    }

    @Override
    public Object getData(Object data) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public JPanel getMainPanel() {
        return null;
    }
}
