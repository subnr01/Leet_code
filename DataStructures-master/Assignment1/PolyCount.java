/*
** $Id: PolyCount.java 1027 2012-07-02 21:40:38Z phf $
*/

/**
 * Simple polymorphic test framework for counters. See comment
 * in main() below for how to use it.
 */
public class PolyCount {

  private static void println(String description, Counter c) {
    // Note that "c" is polymorphic!
    System.out.println( description + ": " + c.value() );
  }

  private static void test(Counter c) {
    // Note that "c" is polymorphic!
    println("Initial value", c);
    int first = c.value();
    c.up();
    println("After up", c);
    c.up();
    println("After up", c);
    c.up();
    println("After up", c);
    c.down();
    println("After down", c);
    c.down();
    println("After down", c);
    c.down();
    println("After down", c);
    int last = c.value();
    // Sanity check!
    assert last == first;
  }

  /**
   * Runs the polymorphic test on various counters.
   * @param args Command line arguments (ignored).
   */
  public static void main(String[] args) {
    // Initially you will only have one counter, probably
    // BasicCounter. Just comment out the tests that you
    // don't have code for yet and make sure BasicCounter
    // works as expected. Then develop the next counter,
    // remove the comments you put in there, and try that
    // one. Once again for the last counter, and you're
    // good to go. :-)

    System.out.println("Testing BasicCounter");
    test(new BasicCounter());
    System.out.println("Testing SquareCounter");
    test(new SquareCounter());
    System.out.println("Testing FlexibleCounter");
    test(new FlexibleCounter(5, 7));

    // DON'T CHANGE ANYTHING ELSE IN HERE! We will test
    // your counters against *our* version of this file!
  }
}
