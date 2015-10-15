/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

/**
    The basic Bubble Sort algorithm.
    Stops if in any iteration of the outer loop, there are no swaps
    This means the Array is sorted

    @param <T> element type
*/
public final class BubbleSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

    /**
        Sort an array through the use of Bubble Sort algorithm.

        @param array array to sort
    */
    public void sort(Array<T> array) {
        T temp;         //Used to temporaly store the item to swap
        int swapCount;  //Used to keep track if anything has been swapped

        //Outer loop goes from the end down
        //The idea is that the correct number will 'bubble' to the end
        //So then the end of the array will be sorted
        //No need to check the last element, because it should be in order
        for (int end = array.length() - 1; end > 0; end--) {

            //Keep track of ow many swaps have happened
            swapCount = 0;

            //This will iterate over the unordered part of the array
            //and 'bubble up' the value
            for (int i = 0; i < end; i++) {
                //If the next value is less than the current value swap them
                if (array.get(i).compareTo(array.get(i + 1)) > 0)  {
                    temp = array.get(i);
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);
                    swapCount++; //keep track of swappings
                }
            }

            //If at any time the inner loop does not swap value,
            //this must mean the array is sorted, and we can break out
            if (swapCount == 0) {
                break;
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