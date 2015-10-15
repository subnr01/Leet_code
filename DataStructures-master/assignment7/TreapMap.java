/*Jose Nino Rivera jnino1@jhu.edu
Elliott Binder ebinder1@jhu.edu
600.226 Data Structures
Assignment 7: Whispering Trees
4/04/14 */

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
    Maps from arbitrary keys to arbitrary values.

    @param <K> Type for keys.
    @param <V> Type for values.

*/
public class TreapMap<K extends Comparable<? super K>, V>
    implements OrderedMap<K, V> {

    static Random generate = new Random();

    private class Node {

        Node left, right;
        K key;
        V value;
        int priority;

        // Constructor to make node creation easier to read;
        Node(K k, V v) {
            //left and right are default to null
            this.key = k;
            this.value = v;
            this.priority = generate.nextInt();
        }

        public String toString() {
            return "Node<key: " + this.key
                + ";value: " + this.value
                + ";priority: " + this.priority
                + ">";
        }
    }

    private Node root;
    private int size;
    private StringBuilder stringBuilder;

    /**
        Creates a new TreapMap object.
    */
    public TreapMap() {
        this.root = null;
        this.size = 0;
    }

    /**
    Rotates the tree to the Right.
    @param n, the problematic node
    @return Node, balanced subtree
    */
    private Node rotateFromLeft(Node p) {
        Node q = p.left;
        p.left = q.right;
        q.right = p;
        return q;
    }

    /**
    Rotates the tree to the left.
    @param n, the problematic node
    @return Node, balanced subtree
    */
    private Node rotateFromRight(Node p) {
        Node q = p.right;
        p.right = q.left;
        q.left = p;
        return q;
    }

    /**
    Balance according to Treap rools.
    @param n, Node to balance if necessary
    @return Node, balance node.
    */
    private Node balance(Node n) {
        if (n == null) {
            return null;
        }
        // min heap
        if (n.right != null && n.priority > n.right.priority) {
            n = this.rotateFromRight(n);
        } else if (n.left != null && n.priority > n.left.priority) {
            n = this.rotateFromLeft(n);
        }
        return n;
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
            return new Node(k, v); // new node's height is 0 by default
        }
        int cmp = k.compareTo(n.key);
        if (cmp < 0) { // go left
            n.left = this.insert(n.left, k, v);
        } else if (cmp > 0) { // go right
            n.right = this.insert(n.right, k, v);
        } else { // found it
            throw new IllegalArgumentException("duplicate key " + k);
        }
        n = this.balance(n);
        return n;
    }

    /**
        Insert a new key/value pair.

        @param k The key.
        @param v The value to be associated with k.
        @throws IllegalArgumentException If k==null or
            if a mapping for k already exists.
    */
    public void insert(K k, V v) throws IllegalArgumentException {
        if (k == null) {
            throw new IllegalArgumentException("null values not allowed");
        }
        this.root = this.insert(this.root, k, v);
        this.size++;
    }

    private Node remove(Node n) {
        // 0 or 1 child
        if (n.right == null) { // more likely b/c maxNode function
            return n.left;
        }
        if (n.left == null) {
            return n.right;
        }

        // 2 children
        if (n.right.priority > n.left.priority) {
            n = this.rotateFromRight(n);
            n.left = this.remove(n.left);
            n = this.balance(n);
            return n;
        } else {
            n = this.rotateFromLeft(n);
            n.right = this.remove(n.right);
            n = this.balance(n);
            return n;
        }
    }

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
        // check code goes here
        n = this.balance(n); //IM NOT SURE
        return n;

    }
    /**
        Remove an existing key/value pair.

        @param k The key.
        @throws IllegalArgumentException If k==null or
            if no mapping exists for k.
    */
    public void remove(K k) throws IllegalArgumentException {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        this.root = this.remove(this.root, k);
        this.size--;
    }

    /**
    Returns the Node with a particular Key K.
    @param k, the key of interest
    @return Node, the Node with the key of interest. Null if not found.
    */
    private Node find(K k) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        Node n = this.root;
        int cmp; // so its not declared every instance of the while loop
        while (n != null) {
            cmp = k.compareTo(n.key);
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

    //copied from BSTM
    private Node findForSure(K k) {
        Node n = this.find(k);
        if (n == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }
        return n;
    }

    @Override
    public boolean has(K k) {
        return this.find(k) != null;
    }

    @Override
    public void put(K k, V v) throws IllegalArgumentException {
        Node n = this.findForSure(k);
        n.value = v;
    }

    @Override
    public V get(K k) {
        Node n = this.findForSure(k);
        return n.value;
    }

    @Override
    public int size() {
        return this.size;
    }

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
        s.append(", ");
        s.append(n.priority);
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
