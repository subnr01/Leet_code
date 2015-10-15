/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

/**
    The basic Insertion Sort algorithm.
    Does not swap values, instead displaces the sorted portion
    and THEN it inserts the current element to the appropriate slot

    @param <T> element type
*/
public final class InsertionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

    /**
        Sort an array.

        @param array array to sort
    */
    public void sort(Array<T> array) {

        //Assume that the first element is sorted
        for (int i = 1; i < array.length(); i++) {
            T value = array.get(i); //Current value to insert in the right place
            int j = i - 1;          //Starting point of 'sorted portion'

            //Displace the sorted elements up until the correct slot
            //for the current value is found
            while (j >= 0 && array.get(j).compareTo(value) > 0) {
                array.set(j + 1, array.get(j));
                j--;
            }

            //if not used, explained why in README
            //if (j + 1 != i) {
            array.set(j + 1, value);
            //}

        }
    }

    /**
        Name of algorithm.

        @return name of algorithm
    */
    public String name() {
        return "Insertion Sort";
    }
}