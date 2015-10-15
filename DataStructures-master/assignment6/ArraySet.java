import java.util.Iterator;

/**
    Set implemented using plain Java arrays.
    @param <T> element type
*/
public class ArraySet<T> implements Set<T> {
    private int length;
    private T[] data;

    private static class SetIterator<T> implements Iterator<T> {
        private int current;
        private int arrayLength;
        private T[] arrayData;

        public SetIterator(int length, T[] data) {
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
    public ArraySet() {
        this.data = (T[]) new Object[1];
    }

    private void grow() {
        T[] bigger = (T[]) new Object[2 * this.length];
        for (int i = 0; i < this.length; i++) {
            bigger[i] = this.data[i];
        }
        this.data = bigger;
    }

    /**
        Insert value into set.
        @param t value to insert
    */
    public void insert(T t) {
        if (this.has(t)) { return; }
        if (this.length == this.data.length) {
            this.grow();
        }
        this.data[this.length] = t;
        this.length += 1;
    }

    private int find(T t) {
        for (int i = 0; i < this.length; i++) {
            if (this.data[i].equals(t)) {
                return i;
            }
        }
        return -1;
    }

    /**
        Remove value from set.
        @param t value to remove
    */
    public void remove(T t) {
        int position = this.find(t);
        if (position == -1) { return; }
        for (int i = position; i < this.length - 1; i++) {
            this.data[i] = this.data[i + 1];
        }
        this.length -= 1;
    }

    /**
        Is value in set?
        @param t value to check
        @return True if value in set, false otherwise.
    */
    public boolean has(T t) {
        return this.find(t) != -1;
    }

    /**
        Iterator for set. Does not support Iterator<T>.remove().
        @return iterator for set
    */
    public Iterator<T> iterator() {
        return new SetIterator<T>(this.length, this.data);
    }
}
