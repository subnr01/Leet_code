/* YourSort.java */

public class YourSort {

  public static void sort(int[] A) {
    // Place your Part III code here.
      if (A.length < 15){
         Sort.insertionSort(A);
          return;
      }
      if (A.length < 40){
          Sort.selectionSort(A);
          return;
      }
      if (A.length < 60){
          Sort.mergeSort(A);
          return;
      }
      else{
          Sort.quicksort(A);
          return;
      }
  }
}
