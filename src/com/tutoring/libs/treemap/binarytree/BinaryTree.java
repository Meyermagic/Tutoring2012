package com.tutoring.libs.treemap.binarytree;

public class BinaryTree <K extends Comparable<? super K>, V> {
    private BinaryTreeNode<K, V> root;
    public BinaryTree() {
        root = null;
    }

    //Return true if insert increased the size of the tree
    public V insert(K key, V value) {
        if (root == null) {
            root = new BinaryTreeNode<K, V>(key, value);
            return null;
        }
        return root.insert(key, value);
    }

    public V remove(K key) {
        if (root == null) {
            return null;
        }
        if (root.key.compareTo(key) == 0) {
            V temp = root.value;
            if (root.right == null) {
                root = root.left;
            } else if (root.left == null) {
                root = root.right;
            } else if (root.left.right == null) {
                root.left.right = root.right;
                root = root.left;
            } else {
                BinaryTreeNode<K, V> rightChild = root.left.popRightmostChild();
                rightChild.left = root.left;
                rightChild.right = root.right;
                root = rightChild;
            }
            return temp;
        }
        return root.remove(key);
    }

    public void print() {
        root.print(0);
    }
}
