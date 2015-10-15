/*Emily Wagner ewagne14@jhu.edu
Jose Nino Rivera jnino1@jhu.edu
600.226 Data Structures
Assignment 8: Hashing Out Spells
4/11/14*/

import java.util.ArrayList;
import java.util.Iterator;
import java.math.BigInteger;

/**
    Maps from arbitrary keys to arbitrary values.

    This implementation uses a hash-table with open addressing
    on top of Java's ArrayList class. In specific, it uses quadratic
    probing as a collision resolution method
    Using ArrayList is okay as
    the get() operation guarantees O(1) performance.

    As long as type K has a decent hashCode() method, all basic
    operations take O(1) expected time (unless we need to resize
    of course).

    @param <K> Type for keys.
    @param <V> Type for values.
*/
public class OpenAddressingHashMap<K, V> implements Map<K, V> {

    // An entry is a key/value pair; we add the equals() and
    // hashCode() methods so we can take advantage of certain
    // ArrayList operations like indexOf(); we only use the
    // key, not the value, for comparisons.
    private static class Entry<K, V> {
        K key;
        V value;
        boolean active;

        Entry(K k, V v) {
            this.key = k;
            this.value = v;
            this.active = true;
        }
        public boolean equals(Object that) {
            return (that instanceof Entry)
                && (this.key.equals(((Entry) that).key));
                //THINK THIS TRHOUGH! do we need active????
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
        private Iterator<Entry<K, V>> list;

        HashMapIterator() {
            this.list = OpenAddressingHashMap.this.data.iterator();
        }
        public boolean hasNext() {
            return this.returned < OpenAddressingHashMap.this.size;
        }
        public K next() {
            while (this.list.hasNext()) {
                Entry<K, V> e = this.list.next();
                if (e.active) {
                    this.returned++;
                    return e.key;
                }
            }
            return null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final int INITIAL_SIZE = 13;
    private static final double ALPHA_CUT_OFF = .5;
    private ArrayList<Entry<K, V>> data;
    private Entry<K, V> fake;
    private int size;

    /**
        Create an empty map.
    */
    public OpenAddressingHashMap() {
        this.data = new ArrayList<>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            this.data.add(null);
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

        int steps = 0;
        int slot = this.hash(k);
        this.fake.key = k;

        //Search for the correct entry
        while (steps < this.data.size()
            && this.data.get(slot) != null) {

            //If the slot is active
            if (this.data.get(slot).active) {

                //Is the data in the slot the one we are looking for?
                if (this.fake.equals(this.data.get(slot))) {
                    return this.data.get(slot);
                }
            }

            //Keep stepping through
            steps++;
            slot = (slot + (steps * steps)) % this.data.size();
        }

        //The entry is not in the ArrayList
        return null;
    }

    private Entry<K, V> findForSure(K k) {
        Entry<K, V> e = this.find(k);
        if (e == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }
        return e;
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

    private double alpha() {
        return ((double) this.size) / this.data.size();
    }

    private int returnPrime(int currSize) {
        String current = String.valueOf(currSize);
        BigInteger newSize = new BigInteger(current);
        newSize = newSize.nextProbablePrime();
        int intNewSize = newSize.intValue();
        return intNewSize;
    }

    private void grow() {
        ArrayList<Entry<K, V>> oldData = this.data;
        this.data = new ArrayList<>();
        int doubleSize = oldData.size() * 2;
        int newSize = this.returnPrime(doubleSize);
        for (int i = 0; i < newSize; i++) {
            this.data.add(null);
        }

        this.size = 0;

        for (int i = 0; i < oldData.size(); i++) {
            if (oldData.get(i) != null
                && oldData.get(i).active) {

                this.insert(oldData.get(i).key,
                    oldData.get(i).value);
            }
        }
    }

    @Override
    public void insert(K k, V v) {
        Entry<K, V> e;
        int slot;
        int steps = 0;

        if (this.has(k)) {
            throw new IllegalArgumentException("duplicate key " + k);
        }

        if (this.alpha() > ALPHA_CUT_OFF) {
            this.grow();
        }

        //Need to insert, make a new entry and hash the key
        e = new Entry<K, V>(k, v);
        slot = this.hash(k);

        //Search for a spot to insert
        while (steps < this.data.size()) {
            if (this.data.get(slot) == null
                    || !this.data.get(slot).active) {
                this.data.set(slot, e);
                this.size++;
                return;
            } else {
                steps++;
                slot = (slot + (steps * steps)) % this.data.size();
            }
        }
    }

    @Override
    public void remove(K k) {
        Entry<K, V> e = this.findForSure(k);
        e.active = false;
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
