package com.xcf.util.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiTreeNode<KEY, NDATA> {

    private static final Integer DEFAULT_CHILD_SIZE = 32;
    private Map<KEY, MultiTreeNode<KEY, NDATA>> childs;
    private NDATA data;

    public MultiTreeNode() {
        childs = new HashMap<>(DEFAULT_CHILD_SIZE);
    }

    public MultiTreeNode(NDATA data) {
        childs = new HashMap<>(DEFAULT_CHILD_SIZE);
        this.data = data;
    }

    public MultiTreeNode(NDATA data, Integer childSize) {
        childs = new HashMap<>(childSize);
        this.data = data;
    }

    public List<MultiTreeNode> getChild() {
        return new ArrayList<>(childs.values());
    }

    public MultiTreeNode<KEY, NDATA> setData(NDATA data) {
        this.data = data;
        return this;
    }

    public boolean contain(KEY key) {
        return this.childs.containsKey(key);
    }

    public MultiTreeNode<KEY, NDATA> addChild(KEY key, MultiTreeNode<KEY, NDATA> child) {
        this.childs.put(key, child);
        return this;
    }

    public NDATA getData() {
        return this.data;
    }
}
