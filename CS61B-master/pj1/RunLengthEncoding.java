/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {
            
    

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
private int width;
private int height;
private int starveTime;
private DListNode first;
private DListNode last;
private DListNode curr;


  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
        width = i;
        height = j;
        this.starveTime = starveTime;
  }



  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
        width = i;
        height = j;
        this.starveTime = starveTime;
      DListNode prev = new DListNode();
      DListNode next = new DListNode();
      this.starveTime = starveTime;
      
      for (int k = 0; k < runTypes.length; k++) {
          DListNode newNode = new DListNode();
          if (k ==0){
              first = newNode;
              curr = first;
          }
          if (k == runTypes.length - 1){
              last = newNode;
          }
          newNode.runTypes = runTypes[k];
          newNode.runLengths = runLengths[k];
          newNode.prev = prev;
          newNode.next = null;
          prev.next = newNode;
          prev = newNode;      
          
      }
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as an
   *  array of two ints), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
      curr = first;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  an array of two ints (constructed here), representing the type and the
   *  size of the run, in that order.
   *  @return the next run in the enumeration, represented by an array of
   *          two ints.  The int at index zero indicates the run type
   *          (Ocean.EMPTY, Ocean.SHARK, or Ocean.FISH).  The int at index one
   *          indicates the run length (which must be at least 1).
   */

  public int[] nextRun() {
      if (curr == null){
          return null;
      }
      else{
          int[] ret = new int[2];
          ret[0] = curr.runTypes;
          ret[1] = curr.runLengths;
          curr = curr.next;
          return ret;
      }
  }
  

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

    public Ocean toOcean() {
        DListNode current2 = first;
        Ocean newOcean = new Ocean(width, height, starveTime);
        int counter = 0;
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if (current2 == null){
                    return newOcean;
                }
                if (current2.runTypes == Ocean.SHARK){
                    newOcean.addShark(x,y, current2.hunger);
                }
                if (current2.runTypes == Ocean.FISH){
                    newOcean.addFish(x,y);
                }
                counter++;
                if (counter >= current2.runLengths){
                    current2 = current2.next;
                    counter = 0;
                }
            }
                
        }
        return newOcean;
    }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
      
          width = sea.width();
          height = sea.height();
          starveTime = sea.starveTime();
          int x = 0;
          int y = 0;
          
          DList ocean = new DList(sea.cellContents(x,y));
            
          ocean.head.hunger = sea.sharkFeeding(x,y);
          curr = ocean.head;
          first = curr;
          
          for (y = 0; y < sea.height(); y++){
              for(x = 0; x < sea.width(); x++){
                  if (!(x== 0 && y ==0)){
                    if (sea.cellContents(x,y) == curr.runTypes && sea.sharkFeeding(x,y) == curr.hunger){
                      curr.runLengths++;
                    } else {
                        ocean.addTail(sea.cellContents(x,y), sea.sharkFeeding(x,y), 1);
                        curr = curr.next;
                    }
                  }
            }
          }
      curr = first;
          
    check();
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // addFish and addShark have been commented out because they do not compile. However, I have presented my logic here. 
      
    /* int index = ((y  * width) + x + 1);
    DListNode current = this.head;
    int i = 0;
    while (i < index){
        i = i + current.runLengths;
        current = current.next;
    }
    current = current.prev;
    i = i - current.runLengths;
    if (i == runindex){
        if (current.runTypes == Ocean.EMPTY){
            if ((current.prev.runTypes == Ocean.EMPTY )|| (current.prev.runTypes == Ocean.SHARK)){
                if (current.runLengths == 1){
                    current.runTypes = Ocean.FISH;
                } else {                                                                                                                                       
                    DListNode fish = new DListNode();
                    fish.runTypes = FISH;
                    fish.runLength = 1;
                    fish.hunger = 0;
                    current.prev.next = fish;
                    fish.prev = current.prev;
                    fish.next = current;
                    current.prev = fish; 
                    current.runLengths = current.runLengths - 1; 
                    } 
        } else {
              current.prev.runLengths++;
              current.next.runLengths--;
                
    }
    } else {
            if (current.runTypes == EMPTY){
                DListNode Empty1 = new DListNode();
                DListNodeEmpty2 = new DListNode();
                Empty1.runTypes = EMPTY;
                Empty1.runLengths = ((index -i) - 1);
                Empty2.runTypes = EMPTY;
                Empty2.runLengths = (i + current.runLenghts) - (index) - 1;
                current.runTypes = FISH;
                current.runLength = 1;
                current.prev.prev = Empty1prev;
                Empty1prev.next = Empty1;
                Empty1.prev = Empty1prev;
                current.prev = Empty1;
                Empty1.next = current;
                current.next.next = Empty2next;
                Empty2next.prev = Empty2;
                Empty2.next = Empty2next;
                Empty2.prev = current;
                current.next = Empty2;
                
            }
    }
    
        }*/
      
    check();
  
  }
  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    /*DListNode current = this.head;
    int i = 0;
    while (i < index){
        i = i + current.runLengths;
        current = current.next;
    }
    current = current.prev;
    i = i - current.runLengths;
    if (i == runindex){
        if (current.runTypes == Ocean.EMPTY){
            if ((current.prev.runTypes == Ocean.EMPTY )|| (current.prev.runTypes == Ocean.FISH)){
                if (current.runLengths == 1){
                    current.runTypes = Ocean.SHARK;
                } else {                                                                                                                                       
                    DListNode shark = new DListNode();
                    shark.runTypes = SHARK;
                    shark.runLength = 1;
                    shark.hunger = 0;
                    current.prev.next = shark;
                    shark.prev = current.prev;
                    shark.next = current;
                    current.prev = shark; 
                    current.runLengths = current.runLengths - 1; 
                    } 
        } else {
              current.prev.runLengths++;
              current.next.runLengths--;
                
    }
    } else {
            if (current.runTypes == EMPTY){
                DListNode Empty1 = new DListNode();
                DListNodeEmpty2 = new DListNode();
                Empty1.runTypes = EMPTY;
                Empty1.runLengths = ((index -i) - 1);
                Empty2.runTypes = EMPTY;
                Empty2.runLengths = (i + current.runLenghts) - (index) - 1;
                current.runTypes = SHARK;
                current.runLength = 1;
                current.prev.prev = Empty1prev;
                Empty1prev.next = Empty1;
                Empty1.prev = Empty1prev;
                current.prev = Empty1;
                Empty1.next = current;
                current.next.next = Empty2next;
                Empty2next.prev = Empty2;
                Empty2.next = Empty2next;
                Empty2.prev = current;
                current.next = Empty2;
                
            }
    }
    
        }
      */
    check();
  }

  /**
   * 
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  private void check() {
      DListNode curr = first;
      while (curr != null && curr.next != null){
        if (curr.runTypes == curr.next.runTypes){
          if (curr.hunger == curr.next.hunger){
              System.out.println("Did Not Consolidate");
          }
        }
        curr = curr.next;
      }
      int total = 0;
      curr = first;
      while (curr != null){
          if (curr.runLengths < 1){
              System.out.println("RunLengths less than 1");
          }
          total = total + curr.runLengths;
          curr = curr.next;
      }
      if (total != width * height){
          System.out.println("Wrong RunLengths");
      } 
          }
 }


