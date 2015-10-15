import java.util.Iterator;

/**
    Set implemented using plain Java list.
    @param <T> element type
*/
public class ListSet<T> implements Set<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;
    }

    private Node<T> head;

    private static class SetIterator<T> implements Iterator<T> {
        private Node<T> current;

        public SetIterator(Node<T> head) {
            this.current = head;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public boolean hasNext() {
            return this.current != null;
        }
        public T next() {
            T t = this.current.data;
            this.current = this.current.next;
            return t;
        }
    }

    /**
        Insert value into set.
        @param t value to insert
    */
    public void insert(T t) {
        if (this.has(t)) { return; }
        Node<T> n = new Node<T>();
        n.data = t;
        n.next = this.head;
        n.prev = null;
        if (this.head != null) {
            this.head.prev = n;
        }
        this.head = n;
    }

    private Node<T> find(T t) {
        for (Node<T> n = this.head; n != null; n = n.next) {
            if (n.data.equals(t)) {
                return n;
            }
        }
        return null;
    }

    /**
        Remove value from set.
        @param t value to remove
    */
    public void remove(T t) {
        Node<T> position = this.find(t);
        if (position == null) { return; }
        if (position.next != null) {
            position.next.prev = position.prev;
        }
        if (position.prev != null) {
            position.prev.next = position.next;
        } else {
            this.head = position.next;
        }
    }

    /**
        Is value in set?
        @param t value to check
        @return True if value in set, false otherwise.
    */
    public boolean has(T t) {
        return this.find(t) != null;
    }

    /**
        Iterator for set. Does not support Iterator<T>.remove().
        @return iterator for set
    */
    public Iterator<T> iterator() {
        return new SetIterator<T>(this.head);
    }
}
