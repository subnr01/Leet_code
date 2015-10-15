/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

/**
    Basic sorting algorithm.

    @param <T> element type
*/
public interface SortingAlgorithm<T extends Comparable<T>> {
    /**
        Sort an array.

        @param array array to sort
    */
    void sort(Array<T> array);

    /**
        Name of algorithm.

        @return name of algorithm
    */
    String name();
}
