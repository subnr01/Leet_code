/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 4
*/
/**
    Unbounded generic double-ended queue abstraction.
    @param <T> Type for queue elements.
*/
public interface Dequeue<T> {
    /**
        No elements?
        @return True if no elements, false otherwise.
    */
    boolean empty();

    /**
        How many elements?
        @return Number of elements in the queue.
    */
    int length();

    /**
        Front element?
        @throws EmptyQueueException If queue is empty.
        @return First element in the queue.
    */
    T front() throws EmptyQueueException;

    /**
        Back element?
        @throws EmptyQueueException If queue is empty.
        @return Last element in the queue.
    */
    T back() throws EmptyQueueException;

    /**
        Insert a new front element.
        @param t Element to insert.
    */
    void insertFront(T t);

    /**
        Insert a new back element.
        @param t Element to insert.
    */
    void insertBack(T t);

    /**
        Remove front element.
        @throws EmptyQueueException If queue is empty.
    */
    void removeFront() throws EmptyQueueException;

    /**
        Remove back element.
        @throws EmptyQueueException If queue is empty.
    */
    void removeBack() throws EmptyQueueException;
}
