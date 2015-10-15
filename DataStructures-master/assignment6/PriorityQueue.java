/**
    Queues of ordered values.

    Note that the interface does not include methods to obtain
    minimum or maximum explicitly, but of course we want to be
    able to create PQs of either sort.

    Your implementations must provide two constructors, one that
    uses the "natural" ordering of java.util.Comparable<T>, and
    another that accepts an explicit java.util.Comparator<T> argument.

    It's a *very* good idea to take the time and read the Java
    documentation for all those interfaces *before* you start
    hacking!

    @param <T> Type of element values.
*/
public interface PriorityQueue<T extends Comparable<? super T>> {
    /**
        Insert a value. Duplicate values <b>do</b> end up in the
        queue, so inserting X three times means it has to
        be removed three times before it's gone again.

        @param t Value to insert.
    */
    void insert(T t);
    /**
        Remove top value. The top value is the "largest"
        value in the queue as determined by the comparator
        for the queue.

        @throws EmptyQueueException If queue is empty.
    */
    void remove() throws EmptyQueueException;
    /**
        Return top value. The top value is the largest
        value in the queue as determined by the comparator
        for the queue.

        @return Top value in the queue.
        @throws EmptyQueueException If queue is empty.
    */
    T top() throws EmptyQueueException;
    /**
        No elements?

        @return True if queue is empty, false otherwise.
    */
    boolean empty();
}
