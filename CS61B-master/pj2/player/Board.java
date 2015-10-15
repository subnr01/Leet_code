//BOARD MODULE
package player;

import java.util.Arrays;
import list.*;

/**
 * A class that is extended by all Network players (human and machine). 
 * This is the player's internal representation of the board. 
 * This representation of the board is intended for the Game Tree Search Module to get and set contents on the board.  
 */

//Constructor for Board class - constructs an 8 by 8 2D array
 
public class Board {
    public final static int EMPTY = -1;
    public final static int BLACK = 0;
    public final static int WHITE = 1;

    private int[][] gameBoard = new int[8][8];
    private int whitePiecesLeft = 10;
    private int blackPiecesLeft = 10;

//Board Corners
    private final static int[] BOTTOM_RIGHT = {7, 7};
    private final static int[] TOP_RIGHT = {7, 0};
    private final static int[] BOTTOM_LEFT = {0, 7};
    private final static int[] TOP_LEFT = {0, 0};
//Directions
    private static final int UP_DIRECTION = 0;
    private static final int UP_RIGHT_DIRECTION = 1;
    private static final int RIGHT_DIRECTION = 2;
    private static final int DOWN_RIGHT_DIRECTION = 3;
    private static final int DOWN_DIRECTION = 4;
    private static final int DOWN_LEFT_DIRECTION = 5;
    private static final int LEFT_DIRECTION = 6;
    private static final int UP_LEFT_DIRECTION = 7;

/**
 * Constructs a Board object, setting each spot on the board to EMPTY initially
 */
     public Board() {
		for (int x = 0; x<8; x++) {
			for (int y =0; y<8; y++) {
				gameBoard[x][y] = Board.EMPTY;
			}
		}
	}
     
/**This constructor duplicates a board.
     It takes in a Board and duplicates its contents. 
        * @param b is the current Board we are duplicating.
        */
     public Board (Board b){
                 for (int x = 0; x<8; x++) {
                        for (int y =0; y<8; y++) {
                                this.gameBoard[x][y] = b.gameBoard[x][y];
                        }      
                }
         this.whitePiecesLeft = b.whitePiecesLeft;
         this.blackPiecesLeft = b.blackPiecesLeft;
     }

/**This method's purpose is to return if a coordinate is a corner on the board. 
 * It uses the static final constants declared in this class. 
     @param x - The x coordinate of the position. 
     @param y - The y coordinate of the position.
     @return true is the position is on a corner, false otherwise
      */
     
 private boolean isCorner(int x, int y){
     if (x == 0 && y == 7){
         return true;
     }
     if (x == 0 && y == 0){
         return true;
     }
     if (x == 7 && y == 7){
         return true;
     }
     if (x == 7 && y ==0){
         return true;
     }
    return false;
 }
 /**This method finds the neighbors of a coordinate. It returns the coordinate positions. 
  * @param x - The x coordinate of the position. 
  * @param y - The y coordinate of the posistion. 
  * @return an int array containing the positions of each neighbor
  */
 private int[][] neighbors(int x, int y){
	 int[][] n = {{x,y+1}, {x,y-1}, {x+1,y+1}, {x+1,y-1}, {x+1,y},{x-1,y},{x-1,y-1},{x-1,y+1}};
	 return n;
 }
 
 /**
  * Determines whether the move with create a cluster if a chip is moved there
  * @param m the Move being considered 
  * @param color the color of the chip being placed
  * @return true if a cluster is created, false otherwise
  */
 private boolean isClustered(Move m, int color){
	 int count = 0;
	 int[][] n = neighbors(m.x1,m.y1);
	 if (m.moveKind == Move.ADD){
		 for (int i = 0; i< 8; i++) {
			if (getSquare(n[i][0], n[i][1]) == color) {
                            count++;
                        int[][] othersNeighbors = (neighbors(n[i][0], n[i][1]));
                        for (int k = 0; k< 8; k++){
                                if (getSquare(othersNeighbors[k][0], othersNeighbors[k][1]) == color){
                                    count++;
                                }
			}
                        }   
                 }
                 if (count > 1){
	             return true;
	         }
	 }else if (m.moveKind == Move.STEP){
		 Board temp = new Board(this);
		 temp.gameBoard[m.x2][m.y2] = EMPTY;
		 Move addMove = new Move(m.x1, m.y1);
		 return temp.isClustered(addMove, color);
	 }
	 return false;		
    }
 
 
 
    /** hasNetwork returns true if this board has a network with 6 or greater length. It otherwise 
    *returns false. It takes in a player because it is called on the board. It calls a recursive, private function travel
     that repeatedly 
    *@param player the player whose network is being determined
    *@return true if this board has a network with 6 or greater length or false otherwise
    * 
   */

    protected boolean hasNetwork (int playerColor) {
        if (playerColor == WHITE) {
                if (this.whitePiecesLeft > 4 || !inGoalArea(playerColor)) {
                        return false;
                }
        }
        if (playerColor == BLACK) {
                 if (this.blackPiecesLeft > 4 || !inGoalArea(playerColor)) {
                 return false;
                 }
        }
        DList startGoals = this.goalPieces(playerColor, 0);
        DList endGoals = this.goalPieces(playerColor, 7);
        DListNode current = (DListNode) startGoals.front();
            try{
                while (current.isValidNode()){
                    int[] startCoord = (int[]) current.getItem();
                    DList connections = currentConnections(startCoord[0],startCoord[1]);
                    DList visited = new DList();
                    int piecesUsed = 1;
                    if (travel(startCoord, piecesUsed, endGoals, connections, visited, -1)){
                        return true;
                    }else{
                        current = (DListNode) current.next();
                    }
            }
            }catch (InvalidNodeException e){
                System.err.println(e);
            }
        return false;
}

    private boolean travel(int[] startCoord, int piecesUsed, DList endGoals,DList connections, DList visited, int notTurn){
            if (piecesUsed > 9){
                    return false;
            }
        if (endGoals.within(startCoord)){
                if (piecesUsed > 5){
                     return true;
                }else{
                    return false;
                }
            }
        if (visited.within(startCoord)){
            return false;
            }
        else{
            DListNode otherCurrent = (DListNode) connections.front();
        try{
            while (otherCurrent.isValidNode()){
                int cannotTravel = (direction(startCoord, (int[]) otherCurrent.getItem()));
                if (cannotTravel == notTurn){
                    otherCurrent = (DListNode) otherCurrent.next();
                    continue;
                }
                visited.insertBack(startCoord);
                if (travel(((int[])otherCurrent.getItem()), piecesUsed + 1, endGoals,currentConnections(((int[])otherCurrent.getItem())[0], ((int[])otherCurrent.getItem())[1]), visited, cannotTravel)) {
                     return true;
                } else{
                    otherCurrent = (DListNode) otherCurrent.next();
                    visited.back().remove();         
                }
            }
        } catch (InvalidNodeException e){
            System.err.println(e);
            }
            }
        return false;
        }

    private boolean inGoalArea(int playerColor) {
            boolean goalarea1 = false;
            boolean goalarea2 = false;
            if (playerColor == WHITE){
                    for (int y = 1; y < 7; y++){
                            if (this.getSquare(0,y) == WHITE){
                                    goalarea1 = true;
                            }
                    }
                    for (int y = 1; y < 7; y++){
                            if (this.getSquare(7,y) == playerColor){
                                    goalarea2 = true;
                            }
                    }
            }
            if (playerColor == BLACK){
                    for (int x = 1; x < 7; x++){
                            if(this.getSquare(x,0) == BLACK){
                                    goalarea1 = true;
                            }
                    }
                    for (int x = 1; x < 7; x++){
                            if (this.getSquare(x,7) == BLACK){
                                    goalarea2 = true;
                            }
                    }
            }
            return goalarea1 && goalarea2;
        }

    private boolean isGoalPosition(Move m, int color){
            if (color == WHITE){
                    if ((m.x1 == 0) || (m.x1 == 7)){
                            return true;
                    }
            }else if (color == BLACK){
                    if (m.y1 == 0 || m.y1 == 7){
                            return true;
                    }
            }
            return false;
    }
        
        
        
     /**
      * Finds the pieces in the player's goal areas
      * @param playerColor the color of the player who's goals are being searched
      * @param x the row or column being searched (depeding on color)
      * @return a DList of the coordinates containing goal pieces
      */
     private DList goalPieces (int playerColor, int x) {
            DList piecesInGoalArea = new DList();
            for (int y = 1; y < 7; y++) {
                if (playerColor == WHITE) {
                    if (this.getSquare(x, y) == WHITE) {
                        int[] coordinate = {x, y};
                        piecesInGoalArea.insertBack(coordinate);
                    }
                } else{   
                    if (this.getSquare(y, x) == BLACK) {
                        int[] coordinate = {y, x};
                        piecesInGoalArea.insertBack(coordinate);
                     }
                }
            }
            return piecesInGoalArea;
         }
         
      

    /** currentConnections returns a DList with all the pieces containing a connection to given coordinate. 
     * This is used to build a network. 
     *@param x the x coordinate of the board
     *@param y the y coordinate of the board
     *@return a DList with all the spots on the board (Move items) containing a connection to the coordinate
     */

     protected DList currentConnections(int x, int y){
         int piece = getSquare(x,y);
            int opp=-1;
            if (piece == 0) {
                    opp = 1;
            }else if (piece ==1){
                    opp = 0;
            }
            
            DList connections = new DList();
         //TOP-LEFT
         int i = x-1;
         int j = y-1;
         while (i>= 0 && j >= 0){
             if (getSquare(i,j) == piece) {
                     int[] d = {i,j};
                 connections.insertFront(d);
                 break;
             }
             if (getSquare(i,j) == opp) {
                     break;
             }
             i--;
             j--;
         }
         //TOP
         i =x;
         j = y-1;
         while (j >= 0){
             if (getSquare(i,j) == piece) {
             int[] d = {i,j};
             connections.insertFront(d);
             break;
         }
             if (getSquare(i,j) == opp) {
                     break;
             }
             j--;
     }
     //TOP-RIGHT
     i =x+1;
     j = y-1;
     while (i<= 7 && j >= 0){
         if (getSquare(i,j) == piece) {
             int[] d = {i,j};
             connections.insertFront(d);
         break;
     }
         if (getSquare(i,j) == opp) {
             break;
         }
         i++;
         j--;
     }
     //RIGHT
     i =x+1;
     j = y;
     while (i <= 7){
         if (getSquare(i,j) == piece) {
             int[] d = {i,j};
             connections.insertFront(d);
         break;
     }
         if (getSquare(i,j) == opp) {
             break;
         }
         i++;
     }
     //BOTTOM-RIGHT
     i =x+1;
     j = y+1;
     while (i<= 7 && j <=7){
         if (getSquare(i,j) == piece) {
             int[] d = {i,j};
             connections.insertFront(d);
         break;
     }
         if (getSquare(i,j) == opp) {
             break;
         }
         i++;
         j++;
     }
     //BOTTOM
     i =x;
     j = y+1;
     while (j <= 7){
         if (getSquare(i,j) == piece) {
             int[] d = {i,j};
             connections.insertFront(d);
         break;
     }
         if (getSquare(i,j) == opp) {
             break;
         }
         j++;
     }
     //BOTTOM-LEFT
     i =x-1;
     j = y+1;
     while (i >=0 && j <=7){
         if (getSquare(i,j) == piece) {
             int[] d = {i,j};
             connections.insertFront(d);
         break;
     }
         if (getSquare(i,j) == opp) {
             break;
         }
         i--;
         j++;
     }
     //Left
     i =x-1;
     j = y;
     while (i>= 0){
         if (getSquare(i,j) == piece) {
             int[] d = {i,j};
             connections.insertFront(d);
         break;
     }
         if (getSquare(i,j) == opp) {
             break;
         }
         i--;
     }
            return connections;
    }
     /**
      * Finds which direction a connection is traveling in, based on positions of first and second chips in a connection
      * @param first the position of the first chip
      * @param next the position of the second coordinate
      * @return an int represeting the direction of the connection (UP, DOWN, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT)
      */
     private int direction(int[] first, int[] next){
                int x1 = first[0];
                int y1 = first[1];
                int x2 = next[0];
                int y2 = next[1];
                if (x2 < x1) {
                    if (y2 == y1) {
                        return LEFT_DIRECTION;
                    }
                    if (y2 > y1) {
                        return DOWN_LEFT_DIRECTION;
                    }
                    if (y2 < y1) {
                        return UP_LEFT_DIRECTION;
                    }
                }
                if (x2 > x1) {
                    if (y2 == y1) {
                        return RIGHT_DIRECTION;
                    }
                    if (y2 > y1) {
                        return DOWN_RIGHT_DIRECTION;
                    }
                    if (y2 < y1) {
                        return UP_RIGHT_DIRECTION;
                    }
                }
                else {
                    if (y2 > y1) {
                        return DOWN_DIRECTION;
                    }
                    if (y2 < y1) {
                        return UP_DIRECTION;
                    }
                }
                return -1;
            }

    /** getSquare determines the contents of the board at a given coordinate
    *@param x the x coordinate of the spot
    *@param y the y coordinate of the spot
    *@return the contents of the board in that spot (-1, 0, 1 for empty, black, or white)
    */

    protected int getSquare(int x, int y) {
                     if (x<0 || x>7 || y<0 || y>7) {
                             return Board.EMPTY;
                     }
                     else {
                             return gameBoard[x][y];
                    }
             }
    
    
//VALID MOVES MODULE


/**Uses the rules of the game and positions of the current chips
*to generate a list of valid moves
*@param playerColor the color of the player whose valid moves are being determiend
*@return an array of Move objects which represent valid moves for the current game situation
*/
protected DList validMoves(int playerColor){
	DList moves = new DList();
	int chips=0;
	if (playerColor == WHITE){
		chips = whitePiecesLeft;
	}else if (playerColor == BLACK){
		chips = blackPiecesLeft;
	}
	if (chips > 0){ //if there are chips left, generate valid add moves
		for (int i = 0; i<8; i++){
			for (int j =0;j<8;j++){
				Move m = new Move(i,j);
				if(isValidMove(m,playerColor)){
					moves.insertBack(m);
				}
			}
		}
	}else{// generate valid step moves
		DList positions = new DList();
		int[] pos = new int[2];
		for (int j=0;j<8;j++){ //generate a list of positions where there are chips of the desired color
			for (int i=0; i<8; i++){
				if (getSquare(i,j) == playerColor){ 
					pos[0] =i;
					pos[1] = j;
					positions.insertBack(pos);
				}
			}
		}
		for (int j=0; j<8;j++){
			for (int i=0;i<8;i++){
				DListNode posNode = (DListNode) positions.front();
				try{
					while (posNode.isValidNode()){
						int[] coords = (int[]) posNode.item();
						Move m = new Move(i,j,coords[0], coords[1]); //try to move each chip in the list from its current position to 
						if (isValidMove(m, playerColor)){            //every other spot on the board, if possible add it to the moves list
							moves.insertBack(m);
						}
                                            posNode = (DListNode) posNode.next();
					}
				}catch (InvalidNodeException e){
					System.err.println(e);
				}
			}
		}
	}
	
	
	
    return moves;
}

/**
 * Determines whether a move is on the board
 * @param m the move being evaulated
 * @return true if it is on the board, or false if not
 */
private boolean onBoard(Move m){
	if (m.x1 < 0 || m.x1 > 7){
		return false;
	}
	if (m.y1 < 0 || m.y1 > 7){
		return false;
	}
	return true;
}

/**Determines whether a move is valid or not
 * @param m the move being evaluated
 * @param playerColor the player being evauluated
*@return true if the move is valid, false otherwise
*/
    protected boolean isValidMove(Move m, int playerColor) {
	if (m.moveKind == Move.STEP){
	                if (m.x1 == m.x2 && m.y1 == m.y2){
	                        return false;
	                }
	        }

	        if (onBoard(m)){
                        if (getSquare(m.x1,m.y1) != EMPTY){
	                        return false;
	                }
                        if (isCorner(m.x1,m.y1)){
                    return false;
                        }
	                if (playerColor == BLACK){
	                        if (m.moveKind == Move.ADD && blackPiecesLeft == 0){
	                                return false;
	                        }
	                        if (isGoalPosition(m,WHITE) || isClustered(m, playerColor)){
	                                return false;
	                        }
	                        return true;
	                        
	                }else if (playerColor == WHITE){
	                        if (m.moveKind == Move.ADD && whitePiecesLeft ==0){
	                                return false;
	                        }
                                if (isGoalPosition(m,BLACK)){
                                return false;
                            }
                                    
                            if (isClustered(m,playerColor)){
                                return false;
	                        }
                        return true;
	                }
	        }
	        return false;

	}


/** makeMove takes a legal move and an int player and changes the board. It can make
    an add move or a step move and it labels the move as which it is. It adds the piece and needs to 
    decrement the amount of pieces left for each player.
    @param m - Takes in a move to make
    @ param playerColor - Determines which player the move is made for for chip color
    @return NOTHING HA!
*/

protected void makeMove(Move m, int playerColor){
        if (m.moveKind == Move.ADD) {
            gameBoard[m.x1][m.y1] = playerColor;
            if (playerColor == Board.WHITE) {
                whitePiecesLeft--;
        		}
            if (playerColor == Board.BLACK) {
                blackPiecesLeft--;
        		}
	}
	else if (m.moveKind == Move.STEP) {
		gameBoard[m.x2][m.y2] = Board.EMPTY; 
		gameBoard[m.x1][m.y1] = playerColor; 
	}
}




}

