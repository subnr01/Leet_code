/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import java.util.Scanner;

/**
Junit class to test StatableArray<T>
Tests both the Array properties and the Statable properties
*/
public class TestStatableArray {

    //Two references
    Array<Integer> sa;  //enables our use of Array<T> methods       
    Statable sa2;       //enables our use of Statable methods

    //Always create a StatableArray<Integer> at the beginning
    @Before
    public void setupArray() throws InvalidLengthException {
        StatableArray<Integer> test = new StatableArray<Integer>(10, -1);
        //Assign the new object to the references
        sa = test;
        sa2 = test;
    }

//TEST ARRAY INTERFACE PROPERTIES
    @Test
    public void lengthConsistentForNewArray() {
        assertEquals(10, sa.length());
    }

    @Test
    public void newArrayProperlyInitialized() throws InvalidIndexException {
        Integer val = new Integer(-1);
        for (int i = 0; i < sa.length(); i++) {
            assertEquals(val, sa.get(i));
        }
    }

    @Test
    public void setPreservesLength() throws InvalidIndexException {
        sa.set(4, 1);
        assertEquals(10, sa.length());
    }

    @Test
    public void setSetsNewValue() throws InvalidIndexException {
        Integer val = new Integer(1);
        sa.set(4, 1);
        assertEquals(val, sa.get(4));
    }

    @Test
    public void setDoesntChangeOtherValues() throws InvalidIndexException {
        Integer val = new Integer(-1);
        sa.set(0, 1);
        for (int i =1; i <sa.length(); i++) {
            assertEquals(val, sa.get(i));
        }
    }

    @Test
    public void setWorksRegardlessOfIndex() throws InvalidIndexException {
        Integer zero = new Integer(0);
        Integer one = new Integer(1);

        for (int i = 0; i < sa.length(); i++) {
            for (int j = 0; j < sa.length(); j++) {
                sa.set(j, 0);
                assertEquals(zero, sa.get(j));
            }
            
            sa.set(i , 1);
            assertEquals(one, sa.get(i));

            for (int k = 0; k < sa.length(); k++) {      
                if (k != i) {
                assertEquals(zero, sa.get(k));
                }
            }
        }
    }

    @Test
    public void testLowerBound() throws InvalidIndexException {
        Integer val = new Integer(-1);
        assertEquals(val, sa.get(0));
    }

    @Test
    public void testUpperBound() throws InvalidIndexException {
        Integer val = new Integer(-1);
        assertEquals(val, sa.get(sa.length()-1));
    }

    @Test(expected=InvalidIndexException.class)
    public void getTriggersIndexExceptionLower() throws InvalidIndexException {
        sa.get(-1);
    }

    @Test(expected=InvalidIndexException.class)
    public void getTriggersIndexExceptionUpper() throws InvalidIndexException {
        sa.get(sa.length());
    }

    @Test(expected=InvalidIndexException.class)
    public void setTriggersIndexExceptionLower() throws InvalidIndexException {
        sa.set(-1, 1000);
    }

    @Test(expected=InvalidIndexException.class)
    public void setTriggersIndexExceptionUpper() throws InvalidIndexException {
        sa.set(sa.length(), 1000);
    }
    @Test(expected=InvalidLengthException.class)
    public void newTriggersLengthException() throws InvalidLengthException {
        Statable sa = new StatableArray<Integer>(-2, 0);
    }

//TEST STATABLE INTERFACE PROPERTIES
    @Test
    public void resetStatisticsResetsTo0() {
        for (int i = 0; i < 10; i++) {
            sa.get(0);
            sa.set(0, 0);
            sa.length();
        }

        sa2.resetStatistics();

        Scanner in = new Scanner(sa2.getStatistics()).useDelimiter("[^0-9]+");
        int[] integer = new int[4];
        for (int i = 0; i < 4; i++) {
            integer[i] = in.nextInt();
        }

        for (int i = 0; i < 4; i++) {
            assertEquals(0, integer[i]);
        }
    }

    @Test
    public void statisticsReturnRight() {
        for (int i = 0; i < 10; i++) {
            sa.get(0);
            sa.set(0, 0);
            sa.length();
        }

        Scanner in = new Scanner(sa2.getStatistics()).useDelimiter("[^0-9]+");
        int[] integer = new int[4];
        for (int i = 0; i < 4; i++) {
            integer[i] = in.nextInt();
        }

        assertEquals(30, integer[0]);
        for (int i = 1; i < 4; i++) {
            assertEquals(10, integer[i]);
        }
    }

}