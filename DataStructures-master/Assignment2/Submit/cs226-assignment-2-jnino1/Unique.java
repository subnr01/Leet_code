/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignement 2 Problem 1
*/

import java.util.Scanner;

/**
This class enables the user to input any amount of integers, and outputs each unique integer only once
*/
public class Unique {
    //array of unique numbers
    private static Array<Integer> data; //Array of unique numbers
    private static int used;            //Index of how many numbers are used
    private static Scanner input;       //Scanner to read user input

    /**
    Position of a given value in Array<Integers> data.
    @param int value, the value to search for
    @return int, index in the Array if value is found, -1 if not found
    */
    private static int find(int value) throws IndexException {
        for (int i = 0; i < used; i++) {
            if (data.get(i) == value) {
                return i;
            }
        }
        return -1;
    }

    /**
    Inserts value into Array<Integers> data if not present already.
    @param int value, the value to be inserted
    @return void
    */
    private static void insert(int value) throws LengthException, IndexException {

        //Figure out if the value has already been processed
        int position = find(value);

        //if not, set the right index to the right value
        if (position < 0) {
            data.set(used, value);
            used += 1;
        }

        //If the used is equal to data.length() this means that the Array is full and needs to be expanded
        if (used == data.length()) {
            data = expandArray(data);
        }

    }

    /**
    Expands the Array by doubling the length and returning the new Array with all the values of the original Array.
    @param Array<Integer> data, the array that holds the values to put in the new expanded array
    @return Array<Integer>, the new Array with double the length of the param Array and all the values it contained in the right indexes
    */
    private static Array<Integer> expandArray(Array<Integer> data) throws LengthException, IndexException {

        Array<Integer> temp = new SimpleArray<Integer>(2 * data.length(), 0);

        //Copy all the values in data to temp
        for (int i = 0; i < data.length(); i++) {
            temp.set(i, data.get(i));
        }

        return temp;

    }

    /**
    This main method runs the interface, letting the user input numbers.
    @param args, command line arguments
    @return void
    */
    public static void main(String[] args) throws LengthException, IndexException {

        input = new Scanner(System.in);        //input scanner
        data = new SimpleArray<Integer>(1, 0);  // worst case: args.length distinct numbers

        //Ask for user input
        System.out.println("Please input your integers. Finish input by pressing ^D, or ^Z");


        //Keep going until the user decides to use the EOF command ^D for UNIX, ^Z for Windows
        while (input.hasNextInt()) {
            int i = input.nextInt();
            insert(i);
        }

        //Output unique numbers in array order
        for (int i = 0; i < used; i++) {
            System.out.print("\n" + data.get(i));
        }
    }
}
