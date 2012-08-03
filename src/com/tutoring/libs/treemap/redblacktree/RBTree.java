package com.tutoring.libs.treemap.redblacktree;

import java.awt.font.GlyphJustificationInfo;

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

    public void insert(K key, V value) {
        //Make a new node so we don't need to later
        RBTreeNode<K, V> insertedNode = new RBTreeNode<K, V>(key, value, Color.RED, null, null);
        //If root is null, set the new node to root (inserting it as the only pair in the tree)
        if (root == null) {
            root = insertedNode;
        } else {
            //Start with current node being root
            RBTreeNode<K, V> node = root;
            //Loop down the tree until we've found the proper place
            while (true) {
                int cmp = key.compareTo(node.key);
                if (cmp == 0) {
                    //If the key was already in the tree, then replace it
                    //(We won't even need to rebalance!)
                    node.value = value;
                    return;
                } else if (cmp < 0) {
                    //If we're inserting to the left of a node with no left child...
                    if (node.left == null) {
                        //Then we'll insert it there, and re-balance in a bit.
                        node.left = insertedNode;
                        break;
                    } else {
                        //Otherwise, "recurse" on left child.
                        node = node.left;
                    }
                } else { //if (cmp > 0)
                    assert cmp > 0;
                    //If we're inserting to the right of a node with no right child...
                    if (node.right == null) {
                        //Insert it there, and rebalance later.
                        node.right = insertedNode;
                        break;
                    } else {
                        //Otherwise, recurse on right
                        node = node.right;
                    }
                }
            }
            //If we've exited the while loop here, then insertedNode has just been made the child of node
            //So we'll fix the parent.
            insertedNode.parent = node;
        }
        //Re-balance.
        insertCase1(insertedNode);
        //Complain if we've messed up.
        verifyProperties();
    }

    //Case 1: We've inserted at the root.
    private void insertCase1(RBTreeNode<K, V> node) {
        if (node.parent == null) {
            //If we've inserted at the root, then color the node black.
            node.color = Color.BLACK;
        } else {
            //Otherwise, check case 2.
            insertCase2(node);
        }
    }

    private void insertCase2(RBTreeNode<K, V> node) {
        //If the new node (that we inserted as red) has a black parent
        if (nodeColor(node.parent) == Color.BLACK) {
            //Then we haven't messed anything up by inserting it.
            //(Unless it was the root, which we covered in case 1)
            return;
        } else {
            insertCase3(node);
        }
    }

    private void insertCase3(RBTreeNode<K, V> node) {
        //If the new node (inserted as red) has a RED parent and parent sibling
        if (nodeColor(node.uncle()) == Color.RED) {
            //Then we'll want to repaint the parent, uncle, and grandparent opposite colors to fix this.
            node.parent.color = Color.BLACK;
            node.uncle().color = Color.BLACK;
            node.grandparent().color = Color.RED;
            //This may cause its own problems, though
            //(the grandparent could have been root, which needs to be black,
            //or the #black paths property could fail)
            //So we'll go back and (recursively) fix the grandparent)
            insertCase1(node.grandparent());
        } else {
            insertCase4(node);
        }
    }

    private void insertCase4(RBTreeNode<K, V> node) {
        //If we're the right child of our parent
        //and our parent is the left child of our grandparent
        if (node == node.parent.right && node.parent == node.grandparent().left) {
            //Then we want to perform a left rotation about the parent/
            rotateLeft(node.parent);
            node = node.left;
            //And the mirror image case:
        } else if (node == node.parent.left && node.parent == node.grandparent().right) {
            rotateRight(node.parent);
            node = node.right;
        }
        //Note: The above doesn't fix all properties
        insertCase5(node);
    }

    private void insertCase5(RBTreeNode<K, V> node) {
        //Fix ancestor colors.
        node.parent.color = Color.BLACK;
        node.grandparent().color = Color.RED;
        //If we're our parent's left child
        //And our parent is our grandparent's left child
        if (node == node.parent.left && node.parent == node.grandparent().left) {
            //Then rotate right about the grandparent, which will
            rotateRight(node.grandparent());
        } else {
            assert node == node.parent.right && node.parent == node.grandparent().right;
            //Otherwise, rotate grandparent left.
            rotateLeft(node.grandparent());
        }
    }

    public void delete(K key) {
        //Lookup the node we're trying to delete (Woo parent references!~)
        RBTreeNode<K, V> node = lookupNode(key);
        if (node == null) {
            //If the node we're trying to delete isn't actually in the tree
            //Then return, because we don't have anything to do.
            return;
        }
        //If the node we're removing has both children...
        if (node.left != null && node.right != null) {
            //Grab rightmost left child (predecessor)
            RBTreeNode<K, V> next = maximumNode(node.left);
            //Replace the node we're trying to delete with the rightmost child (in terms of values/keys, children still there.)
            node.key = next.key;
            node.value = next.value;
            //Then swap the node reference we're trying to delete with the rightmost child reference (which is fine now)
            node = next;
        }

        //Make sure we have at least one null child
        assert node.left == null || node.right == null;
        RBTreeNode<K, V> child;
        //Let child be our only child node if right is null,
        //Else the right child
        if (node.right == null) {
            child = node.left;
        } else {
            child = node.right;
        }
        //If we're a black node
        if (nodeColor(node) == Color.BLACK) {
            //Replace our color with child's color
            node.color = nodeColor(child);
            //Then go on and fix the tree.
            deleteCase1(node);
        }
        //Fix the children/parent references (actual deletion occurs here, for the most part)
        replaceNode(node, child);

        //If root ended up red after all this
        if (nodeColor(root) == Color.RED) {
            //Fix it.
            root.color = Color.BLACK;
        }

        verifyProperties();
    }

    private static <K extends Comparable<? super K>,V> RBTreeNode<K,V> maximumNode(RBTreeNode<K,V> node) {
        assert node != null;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private void deleteCase1(RBTreeNode<K,V> node) {
        //If node is now root
        if (node.parent == null) {
            //Then we're done (root should be fine)
            return;
        } else {
            deleteCase2(node);
        }
    }


    private void deleteCase2(RBTreeNode<K,V> node) {
        //If our sibling is red
        if (nodeColor(node.sibling()) == Color.RED) {
            //Recolor the parent and sibling so the colors are
            //How they were before rotation (about)
            node.parent.color = Color.RED;
            node.sibling().color = Color.BLACK;
            //Then rotate towards us.
            if (node == node.parent.left) {
                rotateLeft(node.parent);
            } else {
                rotateRight(node.parent);
            }
        }
        deleteCase3(node);
    }

    private void deleteCase3(RBTreeNode<K,V> node) {
        //If parent, sibling, and both sibling's children are black
        if (nodeColor(node.parent) == Color.BLACK &&
                nodeColor(node.sibling()) == Color.BLACK &&
                nodeColor(node.sibling().left) == Color.BLACK &&
                nodeColor(node.sibling().right) == Color.BLACK) {
            //Paint sibling red, and fix properties from sibling's parent.
            node.sibling().color = Color.RED;
            deleteCase1(node.parent);
        } else {
            deleteCase4(node);
        }
    }

    private void deleteCase4(RBTreeNode<K,V> node) {
        //If sibling's tree has longer black paths
        if(nodeColor(node.parent) == Color.RED &&
                nodeColor(node.sibling()) == Color.BLACK &&
                nodeColor(node.sibling().left) == Color.BLACK &&
                nodeColor(node.sibling().right) == Color.BLACK) {
            //Color sibling red
            node.sibling().color = Color.RED;
            //And parent black
            node.parent.color = Color.BLACK;
            //This should make the black paths property true again
        } else {
            deleteCase5(node);
        }
    }

    private void deleteCase5(RBTreeNode<K,V> node) {
        if (node == node.parent.left &&
                nodeColor(node.sibling()) == Color.BLACK &&
                nodeColor(node.sibling().left) == Color.RED &&
                nodeColor(node.sibling().right) == Color.BLACK) {
            node.sibling().color = Color.RED;
            node.sibling().left.color = Color.BLACK;
            rotateRight(node.sibling());
        } else if (node == node.parent.right &&
                nodeColor(node.sibling()) == Color.BLACK &&
                nodeColor(node.sibling().right) == Color.RED &&
                nodeColor(node.sibling().left) == Color.BLACK) {
            node.sibling().color = Color.RED;
            node.sibling().right.color = Color.BLACK;
            rotateLeft(node.sibling());
        }
        deleteCase6(node);
    }

    private void deleteCase6(RBTreeNode<K,V> node) {
        node.sibling().color = nodeColor(node.parent);
        node.parent.color = Color.BLACK;
        if (node == node.parent.left) {
            assert nodeColor(node.sibling().right) == Color.RED;
            node.sibling().right.color = Color.BLACK;
            rotateLeft(node.parent);
        } else {
            assert nodeColor(node.sibling().left) == Color.RED;
            node.sibling().left.color = Color.BLACK;
            rotateRight(node.parent);
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
