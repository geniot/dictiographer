package com.dictiographer.desktop.presenter;

import javax.swing.*;

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

