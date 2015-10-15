/*Jose Nino - jnino1
Data Structures Spring 2014
Assigment 2 Problem 3
*/

/**
This class is a polymorphic test on a variety of Implementations of the interface Array<T>
*/
public class BetterTestArray {

    /**
    test accepts an Array<Integer> and performs a bunch of checks to make sure the implementation is appropriate
    @param Array<Integer> a, the Array object to test
    */  
    private static void test(Array<Integer> a) throws IndexException, LengthException {
        
        //After creation, the Array should have the length with which it was created
        assert a.length() == 10;

        //After creation, the Array should have the correct value of t in all indeces
        for (int i = 0; i < 10; i++) {
            assert a.get(i) == -1;
        }
        
        a.set(4, 4);
        //After setting an index to a new value, it should have that value
        assert a.get(4) == 4;
        //Setting an index to a new value should not affect the length of the Array
        assert a.length() == 10;
        //Setting an index to a new value should not change any other index
        for (int i = 0; i < 10; i++) {
            if (i != 4) {
                assert a.get(i) == -1;
            }
        }

        //Change all the indeces
        for (int i= 0; i < a.length(); i++) {
            a.set(i, i);
        }

        //Check all indeces were successfully changed
        for (int i= 0; i < a.length(); i++) {
            assert a.get(i) == i;
        }

        //Check that no matter what index is changed, it changes, and that the rest of them dont change
        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < a.length(); j++) {
                a.set(j, 0);
                assert a.get(j) == 0;
            }
            a.set(i , 1);
            assert a.get(i) == 1;

            for (int k = 0; k < a.length(); k++) {      
                if (k != i) {
                assert a.get(k) == 0;
                }
            }
        }

        //Check that no matter what index is changed, it changes appropriately to the default value (especially for the SparseArray), and that the rest of them dont change
        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < a.length(); j++) {
                a.set(j, -1);
                assert a.get(j) == -1;
            }
            a.set(i , 1);
            assert a.get(i) == 1;

            for (int k = 0; k < a.length(); k++) {      
                if (k != i) {
                assert a.get(k) == -1;
                }
            }
        }

        //Test the lower and upper bounds of the array
        a.set(0, -1);
        assert a.get(0)  == -1;
        a.set(a.length()-1, -1);
        assert a.get(a.length()-1) == -1;

        //Check that the length of the array must be a positive integer
        try {
            Array<Integer> b = new SparseArray<Integer>(-2, 3);
            assert false;
        } catch (LengthException e) {
            // happy! :-)
        }

        //Check that the get function needs an index inside the bounds of the array (lower bound)
        try {
            a.get(-2);
            assert false;
        } catch (IndexException e) {
            // happy! :-)
        }

        //Check that the get function needs an index inside the bounds of the array (upper bound)
        try {
            a.get(12);
            assert false;
        } catch (IndexException e) {
            //happy! :)
        }

        //Check that the get function needs an index inside the bounds of the array (lower bound)
        try {
            a.set(-4, 0);
            assert false;
        } catch (IndexException e) {
            // happy! :-)
        }

        //Check that the get function needs an index inside the bounds of the array (lower bound)
        try {
            a.set(12, 2);
            assert false;
        } catch (IndexException e) {
            //happy! :)
        }



    } 

    /**
    main creates three Array<T> objects, and test them on the private test function.
    @param String[] args, commandline arguments
    */
    public static void main(String[] args) throws IndexException, LengthException {

        test(new SimpleArray<Integer>(10,-1));
        test(new ListArray<Integer>(10,-1));
        test(new SparseArray<Integer>(10,-1));

    }
}
