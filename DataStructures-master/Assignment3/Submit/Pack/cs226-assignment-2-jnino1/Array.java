/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

/**
    A generic array indexed with integer positions.

    One thing we cannot do in Java is specify the "new" operation
    from the ADT: The corresponding Java concept is a constructor,
    and constructors cannot be declared in interfaces.

    @param <T> element type
*/
public interface Array<T> {
    /**
        Store new element at index.

        @param index index between 0 and length-1 where to put the element
        @param element element to put at the given index
        @throws InvalidIndexException if index is outside array bounds
    */
    void set(int index, T element) throws InvalidIndexException;

    /**
        Return element stored at index.

        @param index index between 0 and length-1 where to get from
        @return the element at the given index
        @throws InvalidIndexException if index is outside array bounds
    */
    T get(int index) throws InvalidIndexException;

    /**
        How many slots in this array?

        @return the length of the array, always greater than 0
    */
    int length();
}
