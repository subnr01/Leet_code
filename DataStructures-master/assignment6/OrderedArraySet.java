/**Jose Nino Rivera jnino1@jhu.edu.
 * Tiffany Chung tchung12@jhu.edu
 * 600.226 Data Structures 3/26/14
 * Assignment 6.3: Ordering Sets Around
 */

import java.util.Iterator;

/**
    Sets of ordered values.
    Implemented with simple Java Array.
    @param <T> Type of element values.
*/
public class OrderedArraySet<T extends Comparable<? super T>>
    implements OrderedSet<T> {

    private int length;
    private T[] data;

    private static class OrderedSetIterator<T> implements Iterator<T> {
        private int current;
        private int arrayLength;
        private T[] arrayData;

        public OrderedSetIterator(int length, T[] data) {
            this.arrayLength = length;
            this.arrayData = data;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public boolean hasNext() {
            return this.current < this.arrayLength;
        }
        public T next() {
            T t = this.arrayData[this.current];
            this.current += 1;
            return t;
        }
    }

    /**
        New set instance.
    */
    public OrderedArraySet() {
        //this.data = (T[]) new Object[1]; //so it allows things like this
        // this should be
        this.data = (T[]) new Comparable[1];
    }

    private void grow() {
        T[] bigger = (T[]) new Comparable[2 * this.length];
        for (int i = 0; i < this.length; i++) {
            bigger[i] = this.data[i];
        }
        this.data = bigger;
    }

    private int find(T t) {
        int start = 0, end = this.length - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (this.data[middle].compareTo(t) > 0) {
                end = middle - 1;
            } else if (this.data[middle].compareTo(t) == 0) {
                return middle;
            } else {
                start = middle + 1;
            }
        }
        return start;
    }

    private boolean found(int p, T t) {
        return p < this.length && this.data[p].equals(t);
    }

    /**
        Insert value into set.
        @param t value to insert
    */
    public void insert(T t) {
        //find
        int position = this.find(t);
        //found
        if (this.found(position, t)) {
            return;
        }
        //check to grow
        if (this.length == this.data.length) {
            this.grow();
        }
        //actual insertion
            //First need to move everything over
                // have position of where we want to insert to
                // for loop moving everything over
        for (int i = this.length - 1; i >= position; i--) {
            this.data[i + 1] = this.data[i];
        }
            //then insert
        this.data[position] = t;
            //then increase length
        this.length++;

    }

    /**
        Remove value from set.
        @param t value to remove
    */
    public void remove(T t) {
        int position = this.find(t); // look for it
        if (this.found(position, t)) { // if we find it
            // if yes we found it,
            //remove it by moving everything to its right left
            for (int i = position; i < this.length - 1; i++) {
                this.data[i] = this.data[i + 1];
            }
            // decrease length by 1
            this.length -= 1;
        }
        //if not found
        return;
    }

    /**
        Is value in set?
        @param t value to check
        @return True if value in set, false otherwise.
    */
    public boolean has(T t) {
        //First use find to return an int
        int position = this.find(t);
        //then use found to return a boolean
        return this.found(position, t);
    }

    /**
        Iterator for set. Does not support Iterator<T>.remove().
        @return iterator for set
    */
    public Iterator<T> iterator() {
        return new OrderedSetIterator<T>(this.length, this.data);
    }
}