package com.dictiographer.desktop.event;

public interface Filter<T extends Event> {
    boolean apply(T event);

    boolean equals(Filter filter);
}

