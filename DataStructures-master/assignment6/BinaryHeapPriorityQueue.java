/**Jose Nino Rivera jnino1@jhu.edu.
 * Tiffany Chung tchung12@jhu.edu
 * 600.226 Data Structures 3/26/14
 * Assignment 6.4: Queueing Priorities
 */

import java.util.Comparator;

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
public class BinaryHeapPriorityQueue<T extends Comparable<? super T>>
    implements PriorityQueue<T> {

    //default comparator class that
    //uses the compareTo method, Peter's suggestion
    private static class DefaultComparator<T extends Comparable<? super T>>
        implements Comparator<T> {
        public DefaultComparator() {}

        public int compare(T first, T second) {
            return first.compareTo(second);
        }
    }

    private T[] data;
    private int length;
    private Comparator<T> comparator;

    // first constructor uses "natural" ordering of java.util.Comparable<T>
    // takes no arguments, create instances of Comparable<T>
    // to use compareTo(Object o) method
    /**
    BinaryHeapPriorityQueue constructor with no parameter.
    Uses the Object's natural ordering using the private DefaultComparator
    class.
    */
    public BinaryHeapPriorityQueue() {
        this.data = (T[]) new Comparable[2];
        this.comparator = new DefaultComparator<T>();
        this.length = 0;
    }

    // second constructor accepts explicit java.util.Comparable<T> argument
    // uses Comparator<T> object to compare
    //two given objects, in compare(Object 01, Oject o2) method
    /**
    BinaryHeapPriorityQueue constructor with parameter.
    Uses the Users Comparator object to create a sense of
    ordering in the heap.

    @param c the Comparator object passed by the user
    the Data Structure will use the compare method of the object to
    order the PriorityQueue.
    */
    public BinaryHeapPriorityQueue(Comparator<T> c) {
        this.data = (T[]) new Comparable[2];
        this.comparator = c;
        this.length = 0;

    }

    //private less method
    private boolean less(int x, int y) {
        return this.comparator.compare(this.data[x], this.data[y]) == -1;
    }

    private void swap(int x, int y) {
        T temp = this.data[y];
        this.data[y] = this.data[x];
        this.data[x] = temp;
    }

    /**
    Swim method to swap values up
    their ordered position (used in insert)
    based on the comparator instance.

    @param int k, the position to swim up from
    */
    private void swim(int k) {
        while (k > 1 && this.less(k / 2, k)) {
            this.swap(k, k / 2);
            k = k / 2;
        }
    }
    /**
    Sink method to swap values down
    to their ordered positon (used in remove)
    based on the comparator instance.

    @param int parent the position to sink down
    */
    private void sink(int parent) {
        int child;
        while (2 * parent <= this.length) {
            child = 2 * parent;
            if (child < this.length && this.less(child, child + 1)) {
                child++;
            }
            if (!this.less(parent, child)) {
                break;
            }
            this.swap(parent, child);
            parent = child;
        }
    }

    // private method to relength array
    private void grow() {
        T[] bigger = (T[]) new Comparable[2 * this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            bigger[i] = this.data[i];
        }
        this.data = bigger;
    }
    /**
    Insert a value. Duplicate values <b>do</b> end up in the
    queue, so inserting X three times means it has to
    be removed three times before it's gone again.

    @param t Value to insert.
     */
    public void insert(T t) {
        if (this.length + 1 == this.data.length) {
            this.grow();
        }
        // add new element to heap
        this.data[this.length + 1] = t;
        // increase length by 1
        this.length++;
        // check if it needs to be swapped up
        this.swim(this.length);
    }

    /**
    Remove top value. The top value is the "largest"
    value in the queue as determined by the comparator
    for the queue.

    @throws EmptyQueueException If queue is empty.
     */
    public void remove() throws EmptyQueueException {
        // check if queue is empty
        if (this.empty()) {
            throw new EmptyQueueException();
        }
        //Need to remove all multiple inserts
        T top = this.top();

        while (!this.empty() && top == this.top()) {
            // replace root with last element
            this.data[1] = this.data[this.length];
            //We don't need to remove it, just decrease size
            this.length--;
            //sink the new top
            this.sink(1);
        }
    }

    /**
    Return top value. The top value is the largest
    value in the queue as determined by the comparator
    for the queue.

    @return Top value in the queue.
    @throws EmptyQueueException If queue is empty.
     */
    public T top() throws EmptyQueueException {
        // check if queue is empty
        if (!this.empty()) {
            return this.data[1];
        } else {
            throw new EmptyQueueException();
        }
        // return the root element
    }

    /**
    No elements?
    @return True if queue is empty, false otherwise.
     */
    public boolean empty() {
        return this.length == 0;
    }

}
