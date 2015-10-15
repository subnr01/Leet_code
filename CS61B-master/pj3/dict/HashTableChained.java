/* HashTableChained.java */

package dict;
import list.*;
import java.lang.Math.*;

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

  /**
   *  Place any data fields here.
   **/
  protected List[] buckets;
  protected int size;


  /**
   * Prime() finds a prime number close to an upper bound
   *
   * @param upperBound search for primes up to this number
   * @return a prime number close to upperBound
   **/
  protected int Prime(int upperBound) {
    boolean[] primes = new boolean[upperBound + 1];
    for (int i = 2; i < primes.length; i++) {
      primes[i] = true;
    }
    int k = 0;
    for (int i = 2; i < primes.length; i++) {
      if (primes[i]) {
        k = i;
        for (int j = i*2; j < primes.length; j = j+i) {
          primes[j] = false;
        }
      }
    }
    return k;
   
  }

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    size = 0;
    if (sizeEstimate > 5) {
      buckets = new List[Prime(sizeEstimate * 2)];
    }
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    size = 0;
    buckets = new List[101];
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  @param code, a hash code
   *  @return the hashcode after compression
   **/

  int compFunction(int code) {
    int result = code % buckets.length;
    if (result < 0) {
      result += buckets.length;
    }
    return result;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    return size == 0;
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
    int index = compFunction(key.hashCode());
    Entry result = new Entry();
    result.key = key;
    result.value = value;
    if(buckets[index] == null) {
      buckets[index] = new DList(result);
    } else {
      buckets[index].insertFront(result);      
    }
    size++;
    return result;
    
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
    int index = compFunction(key.hashCode());
    if(buckets[index] != null) {
      ListNode node = buckets[index].front();
      try{
        while(node != buckets[index].back()){
          if (key.equals(((Entry)node.item()).key())) {
            return (Entry)node.item();
          }
          node = node.next();    
        } 
        if (key.equals(((Entry)node.item()).key())) {
          return (Entry)node.item();
        }   
      } catch (InvalidNodeException e) {
      }
    }
    return null;
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
    int index = compFunction(key.hashCode());
    if (buckets[index] != null) {
      ListNode node = buckets[index].front();
      try{
        while(node != buckets[index].back()){
          if (key.equals(((Entry)node.item()).key())) {
            Entry result = (Entry)node.item();
            node.remove();
            size --;
            return result;
          }
          node = node.next();    
        } 
        if (key.equals(((Entry)node.item()).key())) {
          Entry result = (Entry)node.item();
          node.remove();
          size --;
          return result;
        }   
      } catch (InvalidNodeException e) {
      }
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
    for(int i = 0; i < buckets.length; i++){
      if(buckets[i] != null){
        buckets[i] = null;
      }
    }
    size = 0;
    
  }
  
  /**
   * returns the number of collisions expected
   * @return number of collisions expected
   **/
  public double collisionExpected(){
    int N = buckets.length;
    double result = size - N + N * Math.pow(1-(double)1/N,size);
    return result;
  }
  
  /**
   * return number of collisions in actuality after searching through the hashtable
   * @return actual number of collisions
   **/
   
  public int collisions(){
    int collision = 0, emptyBuckets = 0;
    for (int i = 0; i < buckets.length; i++) {
      if(buckets[i] != null){
        int length = buckets[i].length();
        collision += length - 1;
      } else {
       emptyBuckets ++;
      }
    }
    System.out.println(emptyBuckets);
    return collision;
  }
}
