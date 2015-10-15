/* MachinePlayer.java */

package player;
import java.util.Arrays;
import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
    public final static int COMPUTER_WIN = 1;
    public final static int HUMAN_WIN = -1;
    protected int color;
    protected int searchDepth;
    protected Board board;
    public static final boolean COMPUTER = true;

    

  /**
   * 
   * Creates a machine player with the given color.  Color is either 0 (black)
   *or 1 (white).  (White has the first move.)
   *@param color the color of the player
   */
  public MachinePlayer(int color) {
      this.color = color;
	 this.searchDepth = 3;
	  board = new Board();
  }

  /**Creates a machine player with the given color and search depth.  Color is
   * either 0 (black) or 1 (white).  (White has the first move.)
   * @param color the color of the plater
   * @param searchDepth the depth you want to go to
   */
  public MachinePlayer(int color, int searchDepth) {
	  this.color = color;
	  this.searchDepth = searchDepth;
	  board = new Board();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
	  Move best = findBest(color);
	  board.makeMove(best,color);
	  return best;
	  
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    if (board.isValidMove(m, opponentColor())){
    	board.makeMove(m,opponentColor());
    	return true;
    }
    return false;
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
    if (board.isValidMove(m, color)){
    	board.makeMove(m,color);
    	return true;
    }
    return false;
  }

/// GAME-TREE SEARCH MODULE

/**  Assigns a score to a board on the game tree. It is part of the Game Tree Search Module. It
    contains two important functions. It primarily checks if there is a network on the current board (by calling the and 
   then assigns a score based on recursively evaluating the next boards to a certain depth in order to 
   choose the next best possible move. It also needs to check if it is more advantageous to move forward for itself 
   or to ruin the opponent's network in progress. 
        * @param side is the color of the player who's moves we are currently looking for.
        * @param depth is how many more turns the algorithm can look ahead for a network or to evaluate a score.
        * @param alpha is the score the computer knows with certainty it can achieve.
        * @param beta is the score the opponent knows with certainty it can achieve.
        * @return a Move which holds the highest scoring move. This is known as the BestMove.
        */
  private BestMove minimax(boolean side, int depth, double alpha, double beta, Board b){
	  BestMove best = new BestMove();
	  BestMove reply;
	  
	  if (b.hasNetwork(color) || b.hasNetwork(opponentColor()) ||depth ==0){
		  best.score = evaluateBoard(b);
		  return best;
	  }
	  if (side == COMPUTER){
		  best.score = alpha;
	  }else{
		  best.score = beta;
	  }
	 
	  try {
		  DList moves = (DList) b.validMoves(color);
		  DListNode moveNode = (DListNode) moves.front();
		  while (moveNode.isValidNode()){
                          //System.out.println(b.isValidMove((Move) moveNode.item(),color));
			  Board clone = new Board(b);
			  clone.makeMove((Move)moveNode.item(),color); 
			  reply = minimax(!side, depth-1, alpha, beta, clone);
			  if ((side == COMPUTER) && (reply.score > best.score)){
				  best.move = (Move) moveNode.item();
				  best.score = reply.score;
				  alpha = reply.score;
			  }else if ((side != COMPUTER) && (reply.score < best.score)){
				  best.move = (Move) moveNode.item();
				  best.score = reply.score;
				  beta = reply.score;
			  }
			  if (alpha >= beta){
				  return best;
			  }
			  moveNode = (DListNode) moveNode.next();
		  }
	  }catch (InvalidNodeException e){
		  System.err.println(e);
	  }
	  return best;
	  
  }
 
  /** This method uses minimax to find the best move possible. It uses alpha beta pruning to a certain depth, and evauluates each board, scoring it based on chance of winning
        * @param color is the color of the player who's moves we are currently looking for.
        * @returns a Move which holds the highest scoring move, and hence gives best chance of winning.
        */
  protected Move findBest(int color){
	  int alpha = Integer.MIN_VALUE;
	  int beta = Integer.MAX_VALUE;
	  BestMove bestMove;
	  boolean side;
	  if (this.color == color){
		  side = true;
	  }else
		  side = false;
	  bestMove = minimax(side, searchDepth, alpha, beta, this.board);
	  return bestMove.move;
  }
  
    /** This method allows us to access what the opponentColor is throughout this class.
          * @returns an int which gives us the opponent color.
          */
  
  private int opponentColor(){
	  if (color == Board.WHITE){
		  return Board.BLACK;
	  }else 
		  return Board.WHITE;
  }
  
  
  
/**
 * evaluateBoard gives the current Board a score. This score reflects how likely it is to win if it is positive 
 and if it is negative, how likely the opponent is to win. 
 * This method is underneath the Game Tree Search Module and specifically underneath the minimax interface. 
 Minimax calls evaluateBoard after determining that the Board does not have a Network. It scores each board to represent the outcome. 
 Scores closer to 1 mean the MachinePlayer is more likely to win and
 scores closer to -1 mean the opponent is more likely to win.
 * @param b the Board object to be evaluated.
 * @returns a double that determines the likelihood of winning. 
 */
private double evaluateBoard(Board b){
    double myScore;
    double opponentScore;
    double blackScore = 0.0;
    double whiteScore = 0.0;
    double blackGoal1 = 0.0;
    double blackGoal2 = 0.0;
    double whiteGoal1 = 0.0;
    double whiteGoal2 = 0.0;
    if (b.hasNetwork(this.color)){
        return 100.0;
    }
    if (b.hasNetwork(this.opponentColor())){
    	return -100.0;
    }
    for (int i = 1; i < 7; i ++){
    	 if(b.getSquare(0,i) == Board.WHITE){
                  whiteGoal1 = whiteGoal1 + 1.0;
              }
              if(b.getSquare(7,i) == Board.WHITE){
                  whiteGoal2 = whiteGoal2 + 1.0;
              }
              if(b.getSquare(i,0) == Board.BLACK){
                  blackGoal1 = blackGoal1 + 1.0;
              }
              if(b.getSquare(i,7) == Board.BLACK){
                  blackGoal2 = blackGoal2 + 1.0;
             }
    }
    if ((blackGoal2 + blackGoal1) > 0){
        blackScore = 5.0;
    }
    if ((whiteGoal1 + whiteGoal2) > 0){
        whiteScore = 5.0;
    }
    
    double whiteConnections = 0.0;
    double blackConnections = 0.0;
    double connectionPoint = 10.0;
    
     for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                if(b.getSquare(x,y) == Board.WHITE){
                   whiteConnections = whiteConnections + (double)((b.currentConnections(x,y)).length());
                }
                else if(b.getSquare(x,y) == Board.BLACK){
                    blackConnections = blackConnections + (double)((b.currentConnections(x,y)).length());
                }
            }
        }
    
    if (this.color == Board.WHITE) {
    	myScore =  (whiteConnections * connectionPoint) + whiteScore;
    	opponentScore = (blackConnections * connectionPoint) + blackScore;
    } else {
    	opponentScore =  (whiteConnections * connectionPoint) + whiteScore;
    	myScore = (blackConnections * connectionPoint) + blackScore;
    }
    return ((myScore - opponentScore)); //All of the pieces possible
     
}

}

