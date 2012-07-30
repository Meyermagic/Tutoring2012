package com.tutoring.libs.treemap.redblacktree;

import java.awt.font.GlyphJustificationInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Music
 * Date: 7/27/12
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class RBTree<K extends Comparable<? super K>, V> {
    public static final boolean DEBUG = true;
    public RBTreeNode<K, V> root;

    public RBTree() {
        root = null;
        verifyProperties();
    }

    //Publicly exposed methods / wrappers
    public V lookup(K key) {
        RBTreeNode<K, V> node = lookupNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }


    //Internal + helper functions
    private RBTreeNode<K, V> lookupNode(K key) {
        //Start by searching the root
        RBTreeNode<K, V> node = root;
        //While we haven't run into a leaf...
        while (node != null) {
            //Compare with the current node
            int cmp = key.compareTo(node.key);
            //If we're equal...
            if (cmp == 0) {
                //Just return the node. We're done.
                return node;
            } else if (cmp < 0) {
                //If the search key is smaller than the current node's key,
                //Search the left child.
                node = node.left;
            } else {
                //Otherwise, search the right child.
                assert cmp > 0;
                node = node.right;
            }
        }
        //Same as "return node" here, which would always be null.
        return null;
    }


    //Tree rotations
    //These don't change the order of elements in the tree,
    //but change the height on either side of the node we rotate
    private void rotateLeft(RBTreeNode<K, V> node) {
        RBTreeNode<K, V> right = node.right;
        replaceNode(node, right);
        node.right = right.left;
        if (right.left != null) {
            right.left.parent = node;
        }
        right.left = node;
        node.parent = right;
    }
    private void rotateRight(RBTreeNode<K, V> node) {
        RBTreeNode<K, V> left = node.left;
        replaceNode(node, left);
        node.left = left.right;
        if (left.right != null) {
            left.right.parent = node;
        }
        left.right = node;
        node.parent = left;
    }

    private void replaceNode(RBTreeNode<K, V> old_node, RBTreeNode<K, V> new_node) {
        //If we're replacing the root (the only node without parents),
        //then be sure we actually do that.
        if (old_node.parent == null) {
            root = new_node;
        } else {
            if (old_node == old_node.parent.left) {
                //If we're our parent's left child, replace that.
                old_node.parent.left = new_node;
            } else {
                //Otherwise, replace the parent's right child.
                old_node.parent.right = new_node;
            }
        }
        //If we aren't adding a leaf...
        if (new_node != null) {
            //Then update the new node's parent to reflect its new position in the tree
            new_node.parent = old_node.parent;
        }
    }



    //Testing + Verification

    public void verifyProperties() {
        if (DEBUG) {
            verifyProperty1(root);//Nodes are either red or black
            verifyProperty2(root);//Root is black
            //verifyProperty3(root);//All null values treated as black.
            verifyProperty4(root);//Every red node has two black children
            verifyProperty5(root);//All paths from a node to leaves contain same number of black nodes
        }
    }

    //Nodes must have a color, either red or black.
    private static void verifyProperty1(RBTreeNode<?, ?> node) {
        //Check ourselves
        assert nodeColor(node) == Color.RED || nodeColor(node) == Color.BLACK;
        //If we're not null, check our (potential) children
        if (node == null) return;
        //Null children will read as black, due to how tree is defined (specifically nodeColor)
        verifyProperty1(node.left);
        verifyProperty1(node.right);
    }

    //The root is black
    private static void verifyProperty2(RBTreeNode<?, ?> root) {
        assert nodeColor(root) == Color.BLACK;
    }

    private static Color nodeColor(RBTreeNode<?, ?> node) {
        if (node == null) {
            return Color.BLACK;
        }
        return node.color;
    }

    //All red nodes have two black children
    //Also, (as a consequence of the above), the parent of a red node is black.
    private static void verifyProperty4(RBTreeNode<?, ?> node) {
        if (nodeColor(node) == Color.RED) {
            assert nodeColor(node.left) == Color.BLACK;
            assert nodeColor(node.right) == Color.BLACK;
            assert nodeColor(node.parent) == Color.BLACK;
        }
        if (node == null) return;
        verifyProperty4(node.left);
        verifyProperty4(node.right);
    }

    //All paths to leaf nodes hit the same number of black nodes along the way.
    private static void verifyProperty5(RBTreeNode<?, ?> root) {
        verifyProperty5Helper(root, 0, -1);
    }

    private static int verifyProperty5Helper(RBTreeNode<?, ?> node, int blackCount, int pathCount) {
        //If we're a black node, increment the count
        if (nodeColor(node) == Color.BLACK) {
            blackCount++;
        }
        //If we've hit a leaf node...
        if (node == null) {
            //...and there isn't a pathCount yet...
            if (pathCount == -1) {
                //...then record the count we just found.
                pathCount = blackCount;
            } else {
                //Otherwise (if we already have a pathCount), make sure our blackCount is equal.
                assert pathCount == blackCount;
            }
            //Finally, return the pathCount that we either just calculated, or had passed to us.
            return pathCount;
        }
        //For non-leaf nodes, recurse on children.
        pathCount = verifyProperty5Helper(node.left, blackCount, pathCount);
        pathCount = verifyProperty5Helper(node.right, blackCount, pathCount);
        //Again, return our calculated pathCount.
        return pathCount;
    }
}
