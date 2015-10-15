/**Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 2 Problem 3
*/

/**
This interfase enables developers to create varied implementations of the Array<T> data structure
*/
public interface Array<T> {
    // no "new" because Java
    
    /**
    Returns the value for a certain index in the SimpleArray
    @param int index, the index of interest
    @return T, the value of the object in the index of interest
    */
    T get(int index) throws IndexException;
    
        /**
    Sets a new value for a certain index in the SimpleArray
    @param int index, the index of interest
    @param T t, the new value to be set
    */
    void set(int index, T t) throws IndexException;
    
    /**
    Returns the length of the SimpleArray
    @return int, the length of the SimpleArray
    */
    int length();
}
