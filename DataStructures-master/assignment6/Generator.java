import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/** Jose Nino Rivera jnino1@jhu.edu.
    Tiffany Chung tchung12@jhu.edu
    Generator Class to generate the
    heavily biased data sets.
*/
public final class Generator {

    private Generator() {}

    /** Main Method of Generator.
    @param args command line input
    @throws IOException invalid input
    */
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> data = new ArrayList<Integer>();
        final int a = 100000;
        final int b = 1000;
        final int c = 5;
        for (int i = 1; i <= a; i++) {
            data.add(i);
        }

        Collections.shuffle(data);

        PrintWriter writer = new PrintWriter("heavybiased10k.txt", "UTF-8");

        for (int j = 1; j <= c; j++) {
            for (int i = 0; i < b; i++) {
                writer.println(0);
            }

            for (int k = j * b; k < (j + 1) * b; k++) {
                writer.println(data.get(k));
            }
        }
        writer.close();
    }
}