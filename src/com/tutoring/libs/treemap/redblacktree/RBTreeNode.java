package com.tutoring.libs.treemap.redblacktree;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/30/12
 * Time: 6:26 AM
 * To change this template use File | Settings | File Templates.
 */

enum Color {RED, BLACK}
public class RBTreeNode<K extends Comparable<? super K>, V> {
    public K key;
    public V value;
    public RBTreeNode<K, V> left;
    public RBTreeNode<K, V> right;
    public RBTreeNode<K, V> parent;
    public Color color;

    public RBTreeNode(K key, V value, Color color, RBTreeNode<K, V> left, RBTreeNode<K, V> right) {
        this.key = key;
        this.value = value;
        this.color = color;
        this.left = left;
        this.right = right;
        if (left != null) {
            left.parent = this;
        }
        if (right != null) {
            right.parent = this;
        }
        this.parent = null;
    }

    public RBTreeNode<K, V> grandparent() {
        assert parent != null;
        assert parent.parent != null;
        return parent.parent;
    }

    public RBTreeNode<K, V> sibling() {
        assert parent != null;
        if (this == parent.left) {
            return parent.right;
        }
        return parent.left;
    }

    public RBTreeNode<K, V> uncle() {
        assert parent != null;
        assert parent.parent != null;
        return parent.sibling();
    }

}
