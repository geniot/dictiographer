package com.dictiographer.shared.compiler.lsd2dsl;

public class CharInfo {
    private final boolean sorted;
    private final boolean escaped;
    private final String chr;

    public CharInfo() {
        this.sorted = false;
        this.escaped = false;
        this.chr = "";
    }
}
