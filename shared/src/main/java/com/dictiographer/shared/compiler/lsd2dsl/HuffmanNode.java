package com.dictiographer.shared.compiler.lsd2dsl;

public class HuffmanNode {
    int left;
    int right;
    int parent;
    int weight;

    public HuffmanNode(int left, int right, int parent, int weight) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.weight = weight;
    }
}
