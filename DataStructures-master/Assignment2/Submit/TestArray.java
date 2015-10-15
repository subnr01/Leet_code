/*Jose Nino - jnino1
Data Structures Spring 2014
Assigment 2 Problem 3
*/

/**
This class was written in class and Tests and Array<T> interface implementation. Here it was only modified to test SparseArray.
*/
public class TestArray {
 
    /**
    The main method runs a couple of test cases on the SparseArray<T>
    */
    public static void main(String args[]) throws LengthException, IndexException {
        Array<String> a = new SparseArray<String>(10, "Peter");
 
        assert a.length() == 10;
 
        for (int i = 0; i < 10; i++) {
            assert a.get(i).equals("Peter");
        }
 
        a.set(4, "Paul");
        assert a.get(4).equals("Paul");
        assert a.length() == 10;
 
        for (int i = 0; i < 10; i++) {
            if (i != 4) {
                assert a.get(i).equals("Peter");
            }
        }
 
        try {
            Array<String> b = new SparseArray<String>(-2, "Woops");
            assert false;
        } catch (LengthException e) {
            // happy! :-)
        }
 
        try {
            a.get(-2);
            assert false;
        } catch (IndexException e) {
            // happy! :-)
        }
 
        try {
            a.set(-4, "Joe");
            assert false;
        } catch (IndexException e) {
            // happy! :-)
        }
 
    }
 
}