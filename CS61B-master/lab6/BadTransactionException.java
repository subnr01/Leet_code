
public class BadTransactionException extends Exception {

  public int accountNumber;  // The invalid account number.

    public BadTransactionException () {
      super("Invalid Transaction");

 }
}
