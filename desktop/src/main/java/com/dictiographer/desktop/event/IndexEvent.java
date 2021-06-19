package com.dictiographer.desktop.event;

import com.dictiographer.desktop.Constants;

public class IndexEvent extends BaseEvent {
    private String headword;

    public IndexEvent(String hw, Constants.EventType ea) {
        super(ea);
        this.headword = hw;
    }

    public String getHeadword() {
        return headword;
    }
}
