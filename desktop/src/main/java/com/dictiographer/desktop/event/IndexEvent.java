package com.dictiographer.desktop.event;

import com.dictiographer.desktop.Constants;

public class IndexEvent extends BaseEvent {
    private String headword;

    public IndexEvent(String lc, Constants.EventType ea) {
        super(ea);
        this.headword = lc;
    }

    public String getHeadword() {
        return headword;
    }
}
