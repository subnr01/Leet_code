/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

/**
    SimpleArray<T> implements Array<T> using a standard Java array.

    @param <T> element type
*/
public class SimpleArray<T> implements Array<T> {
    private T[] rep;

    /**
        Create a new SimpleArray<T> instance.

        @param length number of slots
        @param element initial value for all slots
        @throws InvalidLengthException if length &le; 0.
    */
    public SimpleArray(int length, T element) throws InvalidLengthException {
        if (length <= 0) {
            throw new InvalidLengthException();
        }

        this.rep = (T[]) new Object[length];
        for (int i = 0; i < length; i++) {
            this.rep[i] = element;
        }
    }

    /**
        Return element stored at index.

        @param index index between 0 and length-1 where to get from
        @return the element at the given index
        @throws InvalidIndexException if index is outside array bounds
    */
    public T get(int index) throws InvalidIndexException {
        if (index < 0 || index >= this.rep.length) {
            throw new InvalidIndexException();
        }
        return this.rep[index];
    }

    /**
        Store new element at index.

        @param index index between 0 and length-1 where to put the element
        @param element element to put at the given index
        @throws InvalidIndexException if index is outside array bounds
    */
    public void set(int index, T element) throws InvalidIndexException {
        if (index < 0 || index >= this.rep.length) {
            throw new InvalidIndexException();
        }
        this.rep[index] = element;
    }

    /**
        How many slots in this array?

        @return the length of the array, always greater than 0
    */
    public int length() {
        return this.rep.length;
    }

    /**
        String representation.

        @return string representation of array
    */
    public String toString() {
        String result = "[";
        for (int i = 0; i < this.rep.length; i++) {
            result += this.rep[i];
            if (i < this.rep.length - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }
}
