/**Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 2 Problem 3
*/

/**
This Class implements the Array<T> Interface. It implements a SimpleArray, which is basically a Java Array adaptation. 
*/
public class SimpleArray<T> implements Array<T> {
   
    private T[] data;  //Using a java array to implement SimpleArray<T>

    /**
    Constructor, checks that length is positive, initializes the array, and populate it with t
    @param int length, length of the SimpleArray to initialize
    @param T t, the value t type T to populate the SimpleArray with
    */
    public SimpleArray(int length, T t) throws LengthException {
        
        //Check if length is positive
        if (length <= 0) {
            throw new LengthException();
        }
        
        //Java makes us to do it this way, ugly
        this.data = (T[]) new Object[length];
        
        //Populate the SimpleArray with the desired value
        for (int i = 0; i < length; i++) {
            this.data[i] = t;
        }
    }

    /**
    This private function checks if the index is in the bounds of the array, from 0 to data.length
    @param int index, the index to check
    */
    private void indexCheck(int index) throws IndexException {

        //Throw an exception if the index is less than 0 or greater than data.length
        if (!((0 <= index) && (index < this.data.length))) {
            throw new IndexException();
        }
    }


    public T get(int index) throws IndexException {
        indexCheck(index);
        return this.data[index];
    }


    public void set(int index, T t) throws IndexException {
        indexCheck(index);
        this.data[index] = t;
    }


    public int length() {
        return this.data.length;
    }
}
