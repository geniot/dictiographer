package com.dictiographer.desktop.presenter;

import javax.swing.*;

public interface Bindable {
    public void setData(Object data);

    public Object getData(Object data);

    public boolean isEmpty();

    public JPanel getMainPanel();

}
