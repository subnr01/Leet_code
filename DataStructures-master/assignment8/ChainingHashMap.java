/*Emily Wagner ewagne14@jhu.edu
Jose Nino Rivera jnino1@jhu.edu
600.226 Data Structures
Assignment 8: Hashing Out Spells
4/11/14*/

import java.util.ArrayList;
import java.util.Iterator;

/**
    Maps from arbitrary keys to arbitrary values.

    This implementation uses a hash-table with separate chaining
    on top of Java's ArrayList class. Using ArrayList is okay as
    the get() operation guarantees O(1) performance.

    As long as type K has a decent hashCode() method, all basic
    operations take O(1) expected time (unless we need to resize
    of course).

    Note that it's a bad idea to call insert/remove on the map
    while an iterator is active!

    @param <K> Type for keys.
    @param <V> Type for values.
*/
public class ChainingHashMap<K, V> implements Map<K, V> {

    // An entry is a key/value pair; we add the equals() and
    // hashCode() methods so we can take advantage of certain
    // ArrayList operations like indexOf(); we only use the
    // key, not the value, for comparisons.
    private static class Entry<K, V> {
        K key; V value;

        Entry(K k, V v) {
            this.key = k;
            this.value = v;
        }
        public boolean equals(Object that) {
            return (that instanceof Entry)
                && (this.key.equals(((Entry) that).key));
        }
        public int hashCode() {
            return this.key.hashCode();
        }
    }

    // The iterator class; we avoid having to make a copy of
    // our data at the expense of having to write a slightly
    // more complicated iterator; the "problem" is that we
    // need to traverse a "list of lists" so we need "inner"
    // iterators for each bucket and an "outer" iterator for
    // the bucket array itself.
    private class HashMapIterator implements Iterator<K> {
        private int returned = 0;
        private Iterator<ArrayList<Entry<K, V>>> outer;
        private Iterator<Entry<K, V>> inner;

        HashMapIterator() {
            this.outer = ChainingHashMap.this.data.iterator();
            this.inner = this.outer.next().iterator();
        }
        public boolean hasNext() {
            return this.returned < ChainingHashMap.this.size;
        }
        public K next() {
            if (this.inner.hasNext()) {
                this.returned += 1;
                return this.inner.next().key;
            } else {
                while (!this.inner.hasNext() && this.outer.hasNext()) {
                    this.inner = this.outer.next().iterator();
                }
                if (this.inner.hasNext()) {
                    this.returned += 1;
                    return this.inner.next().key;
                } else {
                    return null;
                }
            }
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final int INITIAL_SIZE = 8;
    private static final double ALPHA_CUT_OFF = .9;
    private ArrayList<ArrayList<Entry<K, V>>> data;
    private Entry<K, V> fake;
    private int size;


    /**
        Create an empty map.
    */
    public ChainingHashMap() {
        this.data = new ArrayList<>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            this.data.add(new ArrayList<Entry<K, V>>());
        }
        this.fake = new Entry<K, V>(null, null);
    }

    @Override
    public boolean has(K k) {
        return this.find(k) != null;
    }

    private Entry<K, V> find(K k) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        int slot = this.hash(k);
        this.fake.key = k;
        int index = this.data.get(slot).indexOf(this.fake);
        if (index == -1) {
            return null;
        } else {
            return this.data.get(slot).get(index);
        }
    }

    private int hash(Object o) {
        return this.abs(o.hashCode()) % this.data.size();
    }

    private int abs(int i) {
        if (i < 0) {
            return -i; // won't work for Integer.MIN_VALUE
        } else {
            return i;
        }
    }

    @Override
    public void put(K k, V v) {
        Entry<K, V> e = this.findForSure(k);
        e.value = v;
    }

    @Override
    public V get(K k) {
        Entry<K, V> e = this.findForSure(k);
        return e.value;
    }

    private Entry<K, V> findForSure(K k) {
        Entry<K, V> e = this.find(k);
        if (e == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }
        return e;
    }

    private double alpha() {
        return ((double) this.size) / this.data.size();
    }

    private void grow() {
        ArrayList<ArrayList<Entry<K, V>>> oldData = this.data;
        this.data = new ArrayList<>();
        int oldSize = this.size;
        for (int i = 0; i < 2 * oldData.size(); i++) {
            this.data.add(new ArrayList<Entry<K, V>>());
        }
        for (int i = 0; i < oldData.size(); i++) {
            for (int j = 0; j < oldData.get(i).size(); j++) {
                this.insert(oldData.get(i).get(j).key,
                    oldData.get(i).get(j).value);
            }
            this.size = oldSize;
        }
    }

    @Override
    public void insert(K k, V v) {
        if (this.has(k)) {
            throw new IllegalArgumentException("duplicate key " + k);
        }
        if (this.alpha() > ALPHA_CUT_OFF) {
            this.grow();
        }
        Entry<K, V> e = new Entry<K, V>(k, v);
        int slot = this.hash(k);
        this.data.get(slot).add(e);
        this.size += 1;
    }

    @Override
    public void remove(K k) {
        Entry<K, V> e = this.findForSure(k);
        int slot = this.hash(k);
        this.data.get(slot).remove(e);
        this.size -= 1;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    @Override
    public int size() {
        return this.size;
    }
}
