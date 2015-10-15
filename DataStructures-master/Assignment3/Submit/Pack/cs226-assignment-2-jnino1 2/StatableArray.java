/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

/**
    StatableArray<T> extends SimpleArray<T>
    and implements the Statable interface.

    @param <T> element type
*/
public class StatableArray<T> extends SimpleArray<T> implements Statable {

    //New Data Members
    private int numGet;
    private int numSet;
    private int numLen;

    //Methods
    /**
        Create a new StatableArray<T> instance.

        @param length number of slots.
        @param elements initial value for all slots.
        @throws InvalidLengthException if length is less than 0.
    */
    public StatableArray(int length, T elements) throws InvalidLengthException {
        super(length, elements);
        this.numGet = 0;
        this.numSet = 0;
        this.numLen = 0;
    }

    /**
        Return element stored at index.

        @param index index between 0 and length-1 where to get from
        @return the element at the given index
        @throws InvalidIndexException if index is outside array bounds
    */
    public T get(int index) throws InvalidIndexException {
        this.numGet++;
        return super.get(index);
    }

    /**
        Store new element at index.

        @param index index between 0 and length-1 where to put the element
        @param element element to put at the given index
        @throws InvalidIndexException if index is outside array bounds
    */
    public void set(int index, T element) throws InvalidIndexException {
        this.numSet++;
        super.set(index, element);
    }

    /**
        How many slots in this array?

        @return the length of the array, always greater than 0
    */
    public int length() {
        this.numLen++;
        return super.length();
    }

    /**
    Returns a string providing information about the StatableArray,
    the number of times each method, get, set, length were called
    and the total number of method calls performed.

    @return the string containing the Statistics
    */
    public String getStatistics() {
        String stats;
        int total = this.numGet + this.numLen + this.numSet;

        stats = "StatableArray Statistics\n"
                + "========================\n"
                + "Total: " + total + "\n"
                + "get():" + this.numGet + "\n"
                + "set():" + this.numSet + "\n"
                + "length():" + this.numLen;
        return stats;
    }

    /**
    Reset the private data members of the StatableArray object
    from the statistic information.
    */
    public void resetStatistics() {
        this.numGet = 0;
        this.numSet = 0;
        this.numLen = 0;
    }
}