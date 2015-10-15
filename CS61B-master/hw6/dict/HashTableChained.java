/* HashTableChained.java */

package dict;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {
    int bucketNumber;
    DList[] anArray;
  /**
   *  Place any data fields here.
   **/



  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    bucketNumber = getNearestPrime(sizeEstimate);  
    anArray = new DList[bucketNumber]; 
    for (int i = 0; i < bucketNumber; i++){
        anArray[i] = new DList();
    }
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
      bucketNumber = getNearestPrime(100);
      anArray = new DList[bucketNumber];
      for (int i = 0; i < bucketNumber; i++){
          anArray[i] = new DList();
      }
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
    int newcode = code;
    if (newcode != 0){
        newcode = newcode % (anArray.length);
    }
    if (newcode < 0){
        while (newcode < 0){
            newcode = newcode + anArray.length;
        }
    }
    int a = 39;
    int b = 53;
    int N = this.bucketNumber;
    int p = 100000*N;
    return (((a*newcode + b)% p)% N);
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    int totalEntries = 0;
    for (int x = 0; x < anArray.length; x++){
        if (anArray[x] != null){
            totalEntries = totalEntries + anArray[x].length();
        }
    }
    return totalEntries;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
      if (this.size() == 0){
          return true;
      }else {
          return false;
      }
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
      int index = compFunction(key.hashCode());
      Entry current = new Entry();
      current.key = key;
      current.value = value;
    anArray[index].insertFront(current);
    return current;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
    int index = compFunction(key.hashCode());
    if (anArray[index].isEmpty()){
      return null;
    }else{
       DListNode current = (anArray[index]).front();
        while (current != null){
            if (((Entry)(current.item)).key().equals(key)){
                return (Entry)current.item;
            }else{
                current = anArray[index].next(current);
            }
        }
        return null;
    }
    }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
    int index = compFunction(key.hashCode());
    if (this.anArray[index].isEmpty()){
      return null;
    }else{
      DListNode current =   this.anArray[index].front();
      while (current != null){
            if (((Entry)(current.item)).key().equals(key)){
                (this.anArray[index]).remove(current);
                return ((Entry)(current.item));
            }else {
                current = anArray[index].next(current);
            }
        }  
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
      for (int i = 0; i < bucketNumber; i++){
          anArray[i] = new DList();
      
  }
  }

  public boolean isPrime(int value){
      if (value % 2 == 0){
          return false;
      }else {
          int counter = 3;
          while (counter < value){
              if (value % counter == 0){
                  return false;
              }else{
                  counter = counter + 2;
              }
          }
          return true;
      }
  }
  public int getNearestPrime(int number){
      if (isPrime(number)){
          return number;
      }else{
          int currentNumber = number + 1;
          while (!(isPrime(currentNumber))){
              currentNumber++;
          }
          return currentNumber;
      }
  }

  public int numCollisions(){
      int num = 0;
      for (int x = 0; x < anArray.length; x++){
          System.out.println(anArray[x].length());
          if((anArray[x]).length() > 1){
              num++;
          }
      }
      return num;
  }

}
