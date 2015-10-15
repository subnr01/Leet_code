/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

/**
    The basic Bubble Sort algorithm.

    @param <T> element type
*/
public final class BubbleSortR<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

    /**
        Sort an array.

        @param array array to sort
    */
    public void sort(Array<T> array) {
        T temp;

        for (int end = array.length() - 1; end > 0; end--) {
            for (int i = 0; i < end; i++) {
                if (array.get(i).compareTo(array.get(i + 1)) < 0)  {  // swap
                    temp = array.get(i);
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);
                }
            }
        }
    }

    /**
        Name of algorithm.

        @return name of algorithm
    */
    public String name() {
        return "Bubble Sort";
    }
}