package com.dictiographer.desktop.event;

import com.dictiographer.desktop.Constants;

public abstract class BaseEvent implements Event {
    private Constants.EventType eventType;

    public BaseEvent(Constants.EventType ea) {
        this.eventType = ea;
    }

    public Constants.EventType getEventType() {
        return eventType;
    }
}
