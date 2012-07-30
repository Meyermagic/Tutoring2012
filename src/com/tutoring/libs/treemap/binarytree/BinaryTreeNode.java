package com.tutoring.libs.treemap.binarytree;

public class BinaryTreeNode <K extends Comparable<? super K>, V> {
    public K key;
    public V value;

    //Children
    public BinaryTreeNode<K, V> left, right;

    public BinaryTreeNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public BinaryTreeNode(K key, V value, BinaryTreeNode<K, V> left, BinaryTreeNode<K, V> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public V insert(K key, V value) {
        int cmp = key.compareTo(this.key);
        if (cmp < 0) {
            if (left == null) {
                left = new BinaryTreeNode<K, V>(key, value);
                return null;
            }
            return left.insert(key, value);
        }
        if (cmp > 0) {
            if (right == null) {
                right = new BinaryTreeNode<K, V>(key, value);
                return null;
            }
            return right.insert(key, value);
        }
        V temp = this.value;
        this.value = value;
        return temp;
    }

    public BinaryTreeNode<K, V> popRightmostChild() {
        if (this.right.right == null) {
            BinaryTreeNode<K, V> temp = this.right;
            this.right = temp.left;
            temp.left = null;
            return temp;
        }
        return this.right.popRightmostChild();
    }

    public V remove(K key) {
        int cmp = key.compareTo(this.key);
        if (cmp < 0) {
            if (left == null) {
                return null;
            }
            if (left.key.compareTo(key) == 0) {
                V temp = left.value;
                if (left.right == null) {
                    left = left.left;
                } else if (left.left == null) {
                    left = left.right;
                } else if (left.left.right == null) {
                    left.left.right = left.right;
                    left = left.left;
                } else {
                    BinaryTreeNode<K, V> rightChild = left.left.popRightmostChild();
                    rightChild.left = left.left;
                    rightChild.right = left.right;
                    left = rightChild;
                }
                return temp;
            }
            return left.remove(key);
        }
        if (cmp > 0) {
            if (right == null) {
                return null;
            }
            if (this.right.key.compareTo(key) == 0) {
                V temp = right.value;
                if (right.right == null) {
                    right = right.left;
                } else if (right.left == null) {
                    right = right.right;
                } else if (right.left.right == null) {
                    right.left.right = right.right;
                    right = right.left;
                } else {
                    BinaryTreeNode<K, V> rightChild = right.left.popRightmostChild();
                    rightChild.left = right.left;
                    rightChild.right = right.right;
                    right = rightChild;
                }
                return temp;
            }
            return right.remove(key);
        }
        //cmp == 0
        this.value = value;
        throw new RuntimeException("We shouldn't be here.");
    }

    public void print(int depth) {
        if (left != null) {
            left.print(depth+1);
        }
        for (int i = 0; i < depth; i++) {
            System.out.print(" ");
        }
        System.out.printf("%d %s\n", (Integer)key, (String)value);
        if (right != null) {
            right.print(depth+1);
        }
    }
}
