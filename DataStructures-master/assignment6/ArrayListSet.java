import java.util.Iterator;
import java.util.ArrayList;

/**
    Simple Set<T> based on Java's ArrayList<T>.

    We're using ArrayList<T> methods to implement Set<T> which requires
    adjusting for some "interface mismatch" as it were. Aside from null
    values and handling them, however, there's not too much going on.

    All set operations are O(n) because we allow neither duplicates nor
    "gaps" in the underlying representation.

    @param <T> element type
*/
public class ArrayListSet<T> implements Set<T> {
    private ArrayList<T> data;

    /**
        New instance.
    */
    public ArrayListSet() {
        this.data = new ArrayList<>();
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
        return new ArrayList<T>(this.data).iterator();
    }
}
