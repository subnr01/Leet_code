/*Jose Nino Rivera jnino1@jhu.edu
Elliott Binder ebinder1@jhu.edu
600.226 Data Structures
Assignment 7: Whispering Trees
4/04/14 */

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
    Ordered maps from comparable keys to arbitrary values.

    These trees are AVL balanced so worst-case all operations
    are O(logn). Each iterator operates on a copy of the keys, so
    changing the tree will not change iterations in progress.

    @param <K> Type for keys.
    @param <V> Type for values.
*/
public class AvlTreeMap<K extends Comparable<? super K>, V>
    implements OrderedMap<K, V> {

    private class Node {
        Node left, right;
        K key;
        V value;
        int height;

        // Constructor to make node creation easier to read.
        Node(K k, V v) {
            // left and right default to null
            this.key = k;
            this.value = v;
            this.height = 0;
        }
        // Just for debugging purposes.
        public String toString() {
            return "Node<key: " + this.key
                + "; value: " + this.value
                + "; height: " + this.height
                + ">";
        }
    }

    private Node root;
    private int size;
    private StringBuilder stringBuilder;

    @Override
    public int size() {
        return this.size;
    }

    /**
    Returns the Node with a particular Key K.
    @param k, the key of interest
    @return Node, the Node with the key of interest. Null if not found
    */
    private Node find(K k) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        Node n = this.root;
        while (n != null) {
            int cmp = k.compareTo(n.key);
            if (cmp < 0) {
                n = n.left;
            } else if (cmp > 0) {
                n = n.right;
            } else {
                return n;
            }
        }
        return null;
    }

    @Override
    public boolean has(K k) {
        return this.find(k) != null;
    }

    /**
    Return node for given key.
    @param k, key of interest
    @return Node, with the key of interest
    @throws IllegalArgumentException, if the key is not in the tree.
    */
    private Node findForSure(K k) {
        Node n = this.find(k);
        if (n == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }
        return n;
    }

    @Override
    public void put(K k, V v) {
        Node n = this.findForSure(k);
        n.value = v;
    }

    @Override
    public V get(K k) {
        Node n = this.findForSure(k);
        return n.value;
    }

    /**
    Insert given key and value into subtree rooted
    at given node; return changed subtree with new
    node added.
    @param n, root of the subtree
    @param k, key of interest
    @param v, value
    @return Node, modified root subtree.
    */
    private Node insert(Node n, K k, V v) {
        if (n == null) {
            return new Node(k, v);
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = this.insert(n.left, k, v);
        } else if (cmp > 0) {
            n.right = this.insert(n.right, k, v);
        } else {
            throw new IllegalArgumentException("duplicate key " + k);
        }
        this.recalcHeight(n);
        n = this.balance(n);
        return n;
    }

    @Override
    public void insert(K k, V v) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        this.root = this.insert(this.root, k, v);
        this.size += 1;
    }

    /**
    Return the balance factor of a given node to keep the tree balanced.
    @param n, Node to calculate the balance
    @return int, the balance factor of a given node
    */
    private int balanceFactor(Node n) {
        return this.height(n.left) - this.height(n.right);
    }

    /**
    Rotates the tree to the left.
    @param n, the problematic node
    @return Node, balanced subtree
    */
    private Node rotateLeft(Node n) {
        Node k = n.right;

        n.right =  k.left;
        k.left = n;

        this.recalcHeight(n);
        this.recalcHeight(k);

        return k;
    }

    /**
    Rotates the tree to the right.
    @param n, the problematic node
    @return Node, balanced subtree
    */
    private Node rotateRight(Node n) {
        Node k = n.left;

        n.left = k.right;
        k.right = n;

        this.recalcHeight(n);
        this.recalcHeight(k);

        return k;
    }

    /**
    Balance according to AVL rools.
    @param n, Node to balance if necessary
    @return Node, balance node.
    */
    private Node balance(Node n) {
        if (n == null) {
            return null;
        }

        int difference = this.balanceFactor(n);

        if (difference < -1) {
            //n.right leans slightly left
            if (this.balanceFactor(n.right) == 1) {
                //this subtree is bigger on the left
                //double rotation, need to do this first
                n.right = this.rotateRight(n.right);

            }
            n = this.rotateLeft(n);
            return n;

        } else if (difference > 1) {
            //one on left is too big
            if (this.balanceFactor(n.left) == -1) {
                //this subtree is bigger on the right
                //double rotation, need to do this first
                n.left = this.rotateLeft(n.left);
            }

            n = this.rotateRight(n);
            return n;
        }
        return n;
    }

    /**
    Everytime the nodes are moved around
    it is important to recalculate their height parameter.
    @param n, recalculate the height of n
    */
    private void recalcHeight(Node n) {
        if (n == null) {
            return;
        }
        n.height = this.height(n.left) >= this.height(n.right)
            ? this.height(n.left) + 1 : this.height(n.right) + 1;
    }

    /**
    Return the hide of a tree, usefull for Null trees.
    @param n, root node of the tree of interest
    @return int, the height of the tree starting at Node n
    */
    private int height(Node n) {
        if (n == null) {
            return -1;
        }
        return n.height;
    }

    // Return node with maximum key in subtree rooted
    // at given node. (Iterative version because once
    // again recursion has no advantage here.)
    private Node max(Node n) {
        while (n.right != null) {
            n = n.right;
        }
        return n;
    }

    // Remove node with given key from subtree rooted at
    // given node; return changed subtree with given key
    // missing. (Once again doing this recursively makes
    // it easier to add fancy rebalancing code later.)
    private Node remove(Node n, K k) {
        if (n == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = this.remove(n.left, k);
        } else if (cmp > 0) {
            n.right = this.remove(n.right, k);
        } else {
            n = this.remove(n);
        }
        this.recalcHeight(n);
        n = this.balance(n);
        return n;
    }

    // Remove given node and return the remaining tree.
    // Easy if the node has 0 or 1 child; if it has two
    // children, find the predecessor, copy its data to
    // the given node (thus removing the key we need to
    // get rid off), the remove the predecessor node.
    private Node remove(Node n) {
        // 0 and 1 child
        if (n.left == null) {
            return n.right;
        }
        if (n.right == null) {
            return n.left;
        }

        // 2 children
        Node max = this.max(n.left);
        n.key = max.key;
        n.value = max.value;
        n.left = this.remove(n.left, max.key);
        return n;
    }

    @Override
    public void remove(K k) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        this.root = this.remove(this.root, k);
        this.size -= 1;
    }

    // Recursively add keys from subtree rooted at given node
    // into the given list.
    private void iteratorHelper(Node n, List<K> keys) {
        if (n == null) { return; }
        this.iteratorHelper(n.left, keys);
        keys.add(n.key);
        this.iteratorHelper(n.right, keys);
    }

    @Override
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<K>();
        this.iteratorHelper(this.root, keys);
        return keys.iterator();
    }

    // If we don't have a StringBuilder yet, make one;
    // otherwise just reset it back to a clean slate.
    private void setupStringBuilder() {
        if (this.stringBuilder == null) {
            this.stringBuilder = new StringBuilder();
        } else {
            this.stringBuilder.setLength(0);
        }
    }

    // Recursively append string representations of keys and
    // values from subtree rooted at given node.
    private void toStringHelper(Node n, StringBuilder s) {
        if (n == null) { return; }
        this.toStringHelper(n.left, s);
        s.append(n.key);
        s.append(": ");
        s.append(n.value);
        s.append(": ");
        s.append(n.height);
        s.append(", ");
        this.toStringHelper(n.right, s);
    }

    @Override
    public String toString() {
        this.setupStringBuilder();
        this.stringBuilder.append("{");

        this.toStringHelper(this.root, this.stringBuilder);

        int length = this.stringBuilder.length();
        if (length > 1) {
            // If anything was appended at all, get rid of
            // the last ", " the toStringHelper put in.
            this.stringBuilder.setLength(length - 2);
        }
        this.stringBuilder.append("}");

        return this.stringBuilder.toString();
    }
}
