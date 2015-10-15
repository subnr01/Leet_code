/* ListSorts.java */

import criplist.*;

public class ListSorts {

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  private static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    LinkedQueue result = new LinkedQueue();
    while(! q.isEmpty()){
      try{
        LinkedQueue temp= new LinkedQueue();
        temp.enqueue(q.dequeue());
        result.enqueue(temp);
      }catch(QueueEmptyException e){
        System.out.println("the queue is empty...");
      }
    }
    return result;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  private static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    LinkedQueue result = new LinkedQueue();
    while(! q1.isEmpty() && ! q2.isEmpty()){
      try{
        if(((Comparable) q1.front()).compareTo(q2.front())<= 0){
          result.enqueue(q1.dequeue());
        }else{ //q1.front() is larger
          result.enqueue(q2.dequeue());
        }
      }catch(QueueEmptyException e){
        System.out.println("um...empty...");
      }
    }
    if(! q1.isEmpty()){
      result.append(q1);
    }
    if(! q2.isEmpty()){
      result.append(q2);
    }
    return result;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  private static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    while(! qIn.isEmpty()){
      try{
        int compare = pivot.compareTo(qIn.front()); 
        if(compare < 0){
          qSmall.enqueue(qIn.dequeue());
        }else if(compare == 0){
          qEquals.enqueue(qIn.dequeue());
        }else{
          qLarge.enqueue(qIn.dequeue());
        }
      }catch(QueueEmptyException e){
        System.out.println("IT'S GONE");
      }
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    LinkedQueue result = makeQueueOfQueues(q);
    try{
      while(result.size() > 1){
        LinkedQueue merged = mergeSortedQueues((LinkedQueue)result.dequeue(), (LinkedQueue)result.dequeue());
        result.enqueue(merged);
      }
      if(result.size() == 1){
        q.append((LinkedQueue)result.dequeue());
      }
    }catch(QueueEmptyException e){
      System.out.println("EMPTY");
    }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    if(q.size() > 1){
      int n = (int)(Math.random()*(q.size()-1));
      n+=1;
      Comparable pivot = (Comparable) q.nth(n);
      LinkedQueue larger = new LinkedQueue(), smaller = new LinkedQueue(), equal = new LinkedQueue();
      partition(q, pivot, smaller, equal, larger);
      if(! larger.isEmpty()){
        quickSort(larger);
      }
      if(! smaller.isEmpty()){
        quickSort(smaller);
      }
      q.append(larger);
      q.append(equal);
      q.append(smaller);   
    }
  }

 
}
