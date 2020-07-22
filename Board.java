
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.HashSet; 

/**
 * 
 *The Board class creates a board and fills it with blocks. This class uses a Hashmap to store 
 *each point on the board as a key and the block in that point as its value.
 *The class uses an Arraylist to store all moves made on the current board and its parents.
 *The board also uses a Hashset to store all the blocks in the current board
 *
 */

public class Board {  

	private HashMap<Point, Block> blocksPosition;  //in each board all blocks are stored in the hashmap.  
	private int boardWidth;  
	private int boardHeight;  
	private ArrayList<String> myMoves; 
	//private HashSet<Block> Blocks; 
	int count =0;
	

	public Board(int height, int width, Board parent) {  
  
		boardWidth = width;  
		boardHeight = height;  
		blocksPosition = new HashMap<Point, Block>(); 
		myMoves = new ArrayList<String>(); 
		//Blocks = new HashSet<Block>(); 

		//Blocks.addAll(parent.Blocks); 
		myMoves.addAll(parent.myMoves); 

		if (parent.blocksPosition != null) { 
			blocksPosition.putAll(parent.blocksPosition);    //copy parent blocks to blockPosition hashmap
		} 
	}  

	public Board(int height, int width) { 
		boardWidth = width;  
		boardHeight = height;  
		blocksPosition = new HashMap<Point, Block>(); 
		myMoves = new ArrayList<String>(); 
		//Blocks = new HashSet<Block>(); 
	}  

	private void clearBlock(Block block) {  

		for (int j = block.UpperLeft().x; j <= block.LowerRight().x; j++) {  
			for (int k = block.UpperLeft().y; k <= block.LowerRight().y; k++) {  
				blocksPosition.remove(new Point(j,k)); 
				//Blocks.remove(block); 
			}  
		}  
	}  

	public void fillBlock(Block block) {  //adding blocks to the blockPosition hashmap in the form of point:block

		for (int j = block.UpperLeft().x; j <= block.LowerRight().x; j++) {  
			for (int k = block.UpperLeft().y; k <= block.LowerRight().y; k++) {  
				blocksPosition.put(new Point(j,k), block);  
				//Blocks.add(block); 
			}  
		}  
	}  

	private boolean isValid(Block block, Point point) {  

		if (point.x < 0 || point.y < 0) {  
			return false;  
		} 
		if (point.x> boardWidth || point.y > boardHeight) {  
			return false;  
		}  
		clearBlock(block);  
		
		for (int j = point.x; j < point.x + block.getWidth(); j++) {  // need to make sure block doesn't hit another block
			for (int k = point.y; k < point.y + block.getHeight(); k++) {   // during the move
				if (blocksPosition.containsKey(new Point(j,k))) {  
					fillBlock(block);  
					return false;  
				}  
			}  
		}  
		return true;  
	}  

	public Board makeMove(Block block, Point point) //move a block from its current position to point
	{  

		boolean notValid = true;
		Block tempBlock = new Block(block.UpperLeft(), block.LowerRight()); //copy of the block being moved  

		if (!isValid(block, point)) {  
			notValid = true;
		}  
		else{  
			fillBlock(block);  
			tempBlock.moveBlock(point, new Point(point.x + block.getWidth()-1, point.y + block.getHeight()-1));  
			notValid = false;	
		}
		
		Board board = new Board(boardHeight, boardWidth, this);  
		board.clearBlock(block);  
		board.fillBlock(tempBlock); 
		
		if(!notValid){
			String moveMade = Integer.toString(block.UpperLeft().y) + " " +  Integer.toString(block.UpperLeft().x) + " "
		+ Integer.toString(point.y) + " " + Integer.toString(point.x);
			 board.myMoves.add(moveMade);
		}
	 
		return board;  
	}
	
		public int hashCode(){ 

		int rtn = 0; 
		for (Block block: blocksPosition.values()) { 
			rtn += Math.pow(block.UpperLeft().x, 2) + Math.pow(block.UpperLeft().y, 3) + Math.pow(block.LowerRight().x, 4) + Math.pow(block.LowerRight().y, 5); 
		} 
		return rtn; 
	}  

	public boolean equals(Object o) {  
		Board board = (Board) o;  
		if (board.boardHeight != boardHeight || board.boardWidth != boardWidth)  
			return false;  
		else if (!this.blocksPosition.equals(board.blocksPosition)) {  
			return false;  
		}  
		else {  
			return true;  
		}  
	}  

	public HashMap<Point, Block> getBlocksPosition() { 
		return blocksPosition; 
	} 

	public String toString() {  
		String board = "";  

		for (int j = 0; j < boardHeight; j++) {  
			for (int k = 0; k < boardWidth; k++) {  
				if (blocksPosition.containsKey(new Point(k,j))) {  
					Block block = blocksPosition.get(new Point(k,j)); 
					int size = block.getHeight() * block.getWidth(); 
					board += Integer.toString(size);  
				}  
				else{  
					board += "O";  
				}  
			}  
			board += "\n";  
		}  
		return board;  
	}  

	public String displayMoves() { 
		
		String direction = "";
		String allMoves = ""; 
		
		for (int i = 0; i < myMoves.size(); i++) { 
			String[] move = myMoves.get(i).split(" ");
			int[] move_info = new int[move.length];
			for (int j = 0; j < move.length ; j++ ){
				move_info[j] = Integer.parseInt(move[j]);
			}
			
			if (move_info[0] == move_info[2]){
				if ((move_info[3] - move_info[1]) > 0){
					direction = "right";
				}
				else direction = "left";
			}
			if (move_info[1] == move_info[3]){
				if ((move_info[2] - move_info[0]) > 0){
					direction = "down";
				}
				else direction = "up";
			}
			allMoves += "Move piece at coordinates (" + move[1] + "," + move[0] + ")" + "one unit " + direction;
			allMoves += "\n";
			
			
		}
		return allMoves; 
	}
	public int myMoveSize(){
		return myMoves.size();
		
	}

	public int getHeight() { 
		return boardHeight; 
	} 

	public int getWidth() { 
		return boardWidth; 
	} 

	
}