/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 4
*/

/**
    Dequeue implemented using a growing array.

    All operations except insertFront() and InsertBack()
    take O(1) time in the worst case;
    insertFront() and insertBack() take O(1) amortized time
    because the array may need to be resized;

    @param <T> Element type.
*/
public class ArrayDequeue<T> implements Dequeue<T> {

    /*DATA MEMBERS*/

    //Keeps track of the front of the Dequeue
    private int front;
    //Keeps track of the back of the Dequeue
    private int back;
    //Keeps track of the number of elements in the Dequeue
    private int length;
    //The growing Array
    private T[] data;

    /*CONSTRUCTOR*/

    /**
    The constructor instantiates an Array of length 1
    that will serve as the growing dequeue.
    */
    public ArrayDequeue() {
        this.data = (T[]) new Object[1];
        this.front = 0;
        this.back = 0;
        this.length = 0;
    }

    /*PRIVATE METHODS*/

    /**
    Checks if the data Array is full.

    @return True if it is full, False otherwise
    */
    private boolean isFull() {
        return this.data.length == this.length;
    }

    /**
    Resizes the data array to double the size it was.
    */
    private void grow() {
        T[] temp = (T[]) new Object[2 * this.data.length];

        for (int i = 0; i < this.data.length; i++) {
            temp[i] = this.data[this.front];
            this.front = (this.front + 1) % this.data.length;
        }
        this.front = 0;
        this.back = this.data.length;
        this.data = temp;
    }

    /*PUBLIC METHODS*/

    /**
    Check if the Dequeue does not have any elements.

    @return True if there are no elements, False otherwise
    */
    public boolean empty() {
        return this.length == 0;
    }

    /**
    Returns the number of elements in the Dequeue.

    @return int, the number of elements in the Dequeue
    */
    public int length() {
        return this.length;
    }

    /**
    Returns the front element of the Dequeue.

    @return T, the front element
    @throws EmptyQueueException when the Dequeue is empty
    */
    public T front() throws EmptyQueueException {
        if (this.empty()) {
            throw new EmptyQueueException();
        } else {
            return this.data[this.front];
        }
    }

    /**
    Returns the back element of the Dequeue.

    @return T, the back element
    @throws EmptyQueueException when the Dequeue is empty
    */
    public T back() throws EmptyQueueException {
        if (this.empty()) {
            throw new EmptyQueueException();
        } else {
            return this.data
                [((this.back - 1) + this.data.length) % this.data.length];
        }
    }

    /**
    Insert a new front element.

    @param t Element to insert.
    */
    public void insertFront(T t) {
        if (this.isFull()) {
            this.grow();
        }
        this.front = ((this.front - 1) + this.data.length) % this.data.length;
        this.length++;
        this.data[this.front] = t;
    }

    /**
    Insert a new back element.

    @param t Element to insert.
    */
    public void insertBack(T t) {
        if (this.isFull()) {
            this.grow();
        }
        this.data[this.back] = t;
        this.length++;
        this.back = (this.back + 1) % this.data.length;

    }

    /**
    Remove front element.

    @throws EmptyQueueException If queue is empty.
    */
    public void removeFront() throws EmptyQueueException {

        if (this.empty()) {
            throw new EmptyQueueException();
        }
        this.front = (this.front + 1) % this.data.length;
        this.length--;

    }

    /**
    Remove back element.

    @throws EmptyQueueException If queue is empty.
    */
    public void removeBack() throws EmptyQueueException {
        if (this.empty()) {
            throw new EmptyQueueException();
        }
        this.back = ((this.back - 1) + this.data.length) % this.data.length;
        this.length--;
    }

    /**
    Prints the Dequeue from front to back.

    @return String, the printed dequeue
    */
    public String toString() {
        String s = "[";
        int current = this.front;
        for (int i = 0; i < this.length; i++) {
            s += this.data[current].toString();
            if (i < this.length - 1) {
                s += ", ";
            }
            current = (current + 1) % this.data.length;
        }
        s += "]";
        return s;
    }
}