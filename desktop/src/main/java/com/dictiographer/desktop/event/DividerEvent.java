package com.dictiographer.desktop.event;

import com.dictiographer.desktop.ContentsPanel;

public class DividerEvent implements Event {
    private ContentsPanel source;
    private int newValue;

    public DividerEvent(int nv, ContentsPanel s) {
        this.source = s;
        this.newValue = nv;
    }

    public ContentsPanel getSource() {
        return source;
    }

    public int getNewValue() {
        return newValue;
    }
}
