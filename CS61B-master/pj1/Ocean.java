/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
  private int[][][] ocean;
  private int width;
  private int height;
  private int starveTime;


  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
    // Your solution here.
      width = i;
      height = j; 
      this.starveTime = starveTime;
      ocean = new int[i][j][2];
  }


  public Ocean(Ocean orig){
      this(orig.width(), orig.height(), orig.starveTime());
      for (int x = 0; x < orig.width(); x++){
        for (int y = 0; y < orig.height(); y++){
            ocean[x][y][0] = orig.ocean[x][y][0]; 
            ocean[x][y][1] = orig.ocean[x][y][1];
        }
      }    
  }
  

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
    // Replace the following line with your solution.
    return this.width;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
    // Replace the following line with your solution.
    return this.height;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
    // Replace the following line with your solution.
    return this.starveTime;
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here.
      if (ocean[x][y][0] == EMPTY){
          ocean[x][y][0] = FISH;
      }
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here.
      if (ocean[x][y][0] == EMPTY){
          ocean[x][y][0] = SHARK;
          ocean[x][y][1] = 0;
      }
      
  }

  /*
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {
    // Replace the following line with your solution.
    return ocean[wrapMethod(x,this.width())][wrapMethod(y, this.height())][0];
  }

  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */


  private int wrapMethod(int x, int length){
      if (x < 0){
          return (x % length) + length;
      }
      if (x >= length){
          return x % length;
    }
    return x;
  }
  

  
private int[] getNeighbors(int x, int y){
    int[] a = {wrapMethod(x+1, width),y};
    int[] b = {x, wrapMethod(y+1, height)};
    int[] c = {wrapMethod(x+1, width),wrapMethod(y+1, height)};
    int[] d = {wrapMethod(x-1, width),y};
    int[] e = {x,wrapMethod(y-1, height)};
    int[] f = {wrapMethod(x-1, width),wrapMethod(y+1, height)};
    int[] g = {wrapMethod(x-1, width),wrapMethod(y-1, height)};
    int[] h = {wrapMethod(x+1, width),wrapMethod(y-1, height)};
    int[][] neighbors = {a,b,c,d,e,f,g,h};
    
    int[] ContentsofCell = new int[3];
    for (int l = 0; l < neighbors.length; l++){
        if (cellContents(neighbors[l][0],neighbors[l][1]) == SHARK){
            ContentsofCell[0] = ContentsofCell[0] + 1;
        }
        if (cellContents(neighbors[l][0],neighbors[l][1]) == FISH){
            ContentsofCell[1] = ContentsofCell[1] + 1;
        }
        if (cellContents(neighbors[l][0],neighbors[l][1]) == EMPTY){
            ContentsofCell[2] = ContentsofCell[2] + 1;
        }

    }
    return ContentsofCell;
  }

  public Ocean timeStep() {
    Ocean newOcean = new Ocean(this);
    for(int x = 0; x < newOcean.width(); x++){ 
        for(int y = 0; y < newOcean.height(); y++){
            if (cellContents(x,y) == SHARK){
                if (getNeighbors(x,y)[1] > 0){ // shark eats fish
                    newOcean.ocean[x][y][1] = 0; //hunger goes to 0
                } 
                if (getNeighbors(x,y)[1] == 0){ // shark gets hungrier
                    if (newOcean.ocean[x][y][1] == newOcean.starveTime()){ //
                        newOcean.ocean[x][y][0] = EMPTY;
                        newOcean.ocean[x][y][1] = 0;
                    }else{
                        newOcean.ocean[x][y][1]++;
                        
                    }
                }
            }
            if (cellContents(x,y)== FISH){
                if (getNeighbors(x,y)[0] > 0){
                    newOcean.ocean[x][y][0] = EMPTY;
                }
                if (getNeighbors(x,y)[0] >= 2){
                    newOcean.ocean[x][y][0] = EMPTY;
                    newOcean.addShark(x,y);
                    }
            }
            if (cellContents(x,y) == EMPTY){
                if (getNeighbors(x,y)[1] >= 2 && getNeighbors(x,y)[0] <= 1){
                    newOcean.addFish(x,y);
                }
                if (getNeighbors(x,y)[1] >= 2 && getNeighbors(x,y)[0] >= 2){
                    newOcean.addShark(x,y);
                }
            }
    
        }
    }
    return newOcean;
  }

  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) {
    // Your solution here.
      if (ocean[x][y][0] == EMPTY){
          ocean[x][y][0] = SHARK;
          ocean[x][y][1] = feeding;
      }
      
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
    // Replace the following line with your solution.
      if (cellContents(x,y) == SHARK){
          return ocean[x][y][1];
      } else{
          return 0;
  }

}
}