/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

/**
    A sorting algorithm that doesn't sort.

    This is provided for reference only, nothing should
    be faster than this...

    @param <T> element type
*/
public final class NullSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {
    /**
        Sort an array.

        @param array array to sort
    */
    public void sort(Array<T> array) {
        // nothing to do
    }

    /**
        Name of algorithm.

        @return name of algorithm
    */
    public String name() {
        return "Null Sort";
    }
}
