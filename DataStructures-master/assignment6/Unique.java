/**Jose Nino Rivera jnino1@jhu.edu.
 * Tiffany Chung tchung12@jhu.edu
 * 600.226 Data Structures 3/26/14
 * Assignment 6: Setting Priorities
 */

import java.util.Scanner;

/**
    Filter unique integers.
*/
public final class Unique {
    private static Set<Integer> data;
    private static final double NANOS = 1e9;

    private Unique() { /* hide constructor */ }

    /**
        Main method.
        @param args command line arguments
    */
    public static void main(String[] args) {
        data = new MoveToFrontSet<Integer>();
        Scanner scanner = new Scanner(System.in);

        long before = System.nanoTime();

        while (scanner.hasNextInt()) {
            int i = scanner.nextInt();
            data.insert(i);
        }

        for (Integer i: data) {
            System.out.println(i);
        }

        long duration = System.nanoTime() - before;
        System.err.println(duration / NANOS);
    }
}
