/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

import static java.lang.System.out;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
    A basic framework for comparing sorting algorithms.

    Note that this is quite a mess! I've cleaned it up a bit
    to make checkstyle happy, but this is still not really a
    very good example of decent programming style.

    There's also a warning we cannot easily get rid of, see
    below. You are not responsible for that particular one.
*/

public final class Run {
    // default capacity if none on the command line
    private static final int DEFAULT_CAPACITY = 1000;

    // how many nanoseconds in a second
    private static final double NANOS = 1e9;

    // We read the strings to be sorted into the following
    // array; only entries 0 to size-1 are actually used.
    private static int capacity = DEFAULT_CAPACITY;
    private static int size = 0;
    private static Array<String> buffer = null;

    // The following array has one entry for each sorting
    // algorithm you want to run; this should be the only
    // place where you make changes in this program.

    // ONLY CHANGE THIS! vvvvv ONLY CHANGE THIS!
    private static final SortingAlgorithm[] ALGOS = {
        new NullSort<String>(),
        new SelectionSort<String>(),
        new BubbleSort<String>(),
        new InsertionSort<String>(),
        // ADD YOUR ALGORITHMS HERE
    };
    // ONLY CHANGE THIS! ^^^^^ ONLY CHANGE THIS!

    // private constructor to make checkstyle happy
    private Run() {}

    // Check whether the array is sorted or not.
    private static boolean sorted(Array<String> a) {
        for (int i = 0; i < a.length() - 1; i++) {
            if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    // Parse arguments and set up buffer array.
    private static void arguments(String[] args) {
        if (args.length <= 0) {
            return; // nothing to do, default capacity
        }
        try {
            int value = Integer.parseInt(args[0]);
            if (value > 0) {
                capacity = value;
            } else {
                out.println("Warning: Capacity \"" + args[0]
                    + "\" is too small (must be >0).");
            }
        } catch (NumberFormatException e) {
            out.println("Warning: \"" + args[0]
                + "\" is not an integer (or is too large).");
        }
        buffer = new SimpleArray<String>(capacity, null);
    }

    // Read input into buffer array.
    private static void input() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                System.in));
            String str = in.readLine();
            while ((size < capacity) && (str != null)) {
                buffer.set(size, str);
                size += 1;
                str = in.readLine();
            }
        } catch (IOException e) {
            System.out.print("Error: Problem with I/O.");
        }
    }

    // Run the benchmark for all sorting algorithms.
    private static void benchmark() {
        StatableArray<String> temp = new StatableArray<String>(size, null);

        for (int i = 0; i < ALGOS.length; i++) {
            // copy value into statable array and reset it
            for (int j = 0; j < size; j++) {
                temp.set(j, buffer.get(j));
            }
            temp.resetStatistics();

            // run the next sorting algorithm
            long before = System.nanoTime();
            // we get an ugly warning here, but fixing that gets us
            // into even more trouble; try it?!
            ALGOS[i].sort(temp);
            long after = System.nanoTime();

            // print the statistics for this algorithm
            out.println("========================================");
            out.println(ALGOS[i].name());
            out.println("----------------------------------------");
            out.println(temp.getStatistics());
            out.println("Took " + (after - before) / NANOS + " seconds.");
            out.println("----------------------------------------");
            if (sorted(temp)) {
                out.println("Sorting successful.");
            } else {
                out.println("Sorting FAILED!!!");
            }
        }
    }

    /**
        Compare sorting algorithms.

        Sorts up to capacity lines from standard input with
        all algorithms we know about, measures performance
        for each and finally prints statistics.

        @param args command line arguments
    */
    public static void main(String[] args) {
        arguments(args);
        input();
        benchmark();
    }
}
