package com.nicholasding.search.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a R way trie implementation.
 *
 * @author Nicholas Ding (nicholasdsj@gmail.com) on 2016-06-28.
 */
public class RTrie implements Trie {

    private static final int R = 256;

    private static class Node {
        Object value;
        Character key;
        Node[] children = new Node[R];
    }

    private Node root;

    public void put(String key, Object value) {
        root = put(root, key, value, 0);
    }

    protected Node put(Node node, String key, Object value, int level) {
        if (node == null) node = new Node();
        if (level == key.length()) { node.value = value; return node; }
        Character c = key.charAt(level);
        node.children[c] = put(node.children[c], key, value, level + 1);
        node.children[c].key = c;
        return node;
    }

    public Object get(String key) {
        Node node = get(root, key, 0);
        return node.value;
    }

    protected Node get(Node node, String key, int level) {
        if (node == null) return null;
        if (level == key.length()) return node;
        Character c = key.charAt(level);
        return get(node.children[c], key, level + 1);
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Iterator<String> keys() {
        List<String> collector = new LinkedList<String>();
        for (int i = 0; i < R; i++) {
            collect(root.children[i], "", 1, collector);
        }
        return collector.iterator();
    }

    private void collect(Node node, String prefix, int level, List<String> collector) {
        if (node != null) {
            if (node.value != null) {
                collector.add(prefix + node.key);
            }
            for (int i = 0; i < R; i++) {
                collect(node.children[i], prefix + node.key, level + 1, collector);
            }
        }
    }
}
