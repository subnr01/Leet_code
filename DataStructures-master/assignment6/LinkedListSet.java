import java.util.Iterator;
import java.util.LinkedList;

/**
    Simple Set<T> based on Java's LinkedList<T>.

    We're using LinkedList<T> methods to implement Set<T> which requires
    adjusting for some "interface mismatch" as it were. Aside from null
    values and handling them, however, there's not too much going on.

    All set operations are O(n) because we allow no duplicates.

    @param <T> element type
*/
public class LinkedListSet<T> implements Set<T> {
    private LinkedList<T> data;

    /**
        New instance.
    */
    public LinkedListSet() {
        this.data = new LinkedList<>();
    }

    // refactoring helper
    private void ensureNotNull(T t) {
        if (t == null) {
            throw new IllegalArgumentException("null value not allowed");
        }
    }

    /**
        Insert value into set.
        @param t value to insert
    */
    public void insert(T t) {
        this.ensureNotNull(t);
        if (!this.data.contains(t)) {
            this.data.add(t);
        }
    }

    /**
        Remove value from set.
        @param t value to remove
    */
    public void remove(T t) {
        this.ensureNotNull(t);
        this.data.remove(t);
    }

    /**
        Is value in set?
        @param t value to check
        @return True if value in set, false otherwise.
    */
    public boolean has(T t) {
        this.ensureNotNull(t);
        return this.data.contains(t);
    }

    /**
        Iterator for set. Note that we make a copy of the underlying
        ArrayList<T> to prevent the Iterator<T>.remove() method from
        messing anything up.
        @return iterator for set
    */
    public Iterator<T> iterator() {
        return new LinkedList<T>(this.data).iterator();
    }
}
