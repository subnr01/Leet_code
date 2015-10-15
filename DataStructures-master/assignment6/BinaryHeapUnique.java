/**Jose Nino Rivera jnino1@jhu.edu.
 * Tiffany Chung tchung12@jhu.edu
 * 600.226 Data Structures 3/26/14
 * Assignment 6.4: Queueing Priorities
 */

import java.util.Scanner;

/**
    Filter unique integers.
*/
public final class BinaryHeapUnique {
    private static PriorityQueue<Integer> data;
    private static final double NANOS = 1e9;

    private BinaryHeapUnique() { /* hide constructor */ }

    /**
        Main method.
        @param args command line arguments
    */
    public static void main(String[] args) {
        data = new BinaryHeapPriorityQueue<Integer>();
        Scanner scanner = new Scanner(System.in);

        long before = System.nanoTime();

        while (scanner.hasNextInt()) {
            int i = scanner.nextInt();
            data.insert(i);
        }

        while (!data.empty()) {
            System.out.println(data.top());
            data.remove();
        }

        long duration = System.nanoTime() - before;
        System.err.println(duration / NANOS);
    }
}
