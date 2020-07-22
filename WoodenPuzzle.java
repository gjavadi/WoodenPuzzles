
import java.util.*;

public class WoodenPuzzle {

	/**
	 * The WoodenPuzzle class contains the main method that reads initial and goal configurations
	 * and then calls the solve method. This class uses a queue to store every board after a new
	 * move is made. It also implements a hashset to store board with new board. Before storing 
	 * a board in the queue and the hashset, the hashset is checked and if the board already exists, it
	 * will not be stored. 
	 */
	
	private Board winBoard; 
	private Board currentBoard;
	private Queue<Board> auxQ = new LinkedList<Board>();
	private HashSet<Board> alreadySeen; //every time we make a move we store it in the queue & hashset,if its not already been there.
	                                    //the way we check that the board is already save or not is by using the hashset.
	boolean stop = false;

	public WoodenPuzzle(Board root, Board goal){
		
		winBoard = goal;
		currentBoard = root;
		alreadySeen = new HashSet<Board>();
		alreadySeen.add(root);
		
		auxQ.add(root);
	}
	public static void main(String[] args) {
		
		try{
			
			if (args.length < 1) {
				System.err.println ("There should be exactly two arguments");
				System.exit (1);
			}
			String inputfile = args[0];
			String outputfile = args[1];
			
			Readinput in = new Readinput(inputfile, 0, 0);
			Readinput goal = new Readinput(outputfile, in.board().getHeight(), in.board().getWidth());
			WoodenPuzzle s = new WoodenPuzzle(in.board(), goal.board());
			System.out.println(in.board());
			s.solve();
		}catch (OutOfMemoryError e){
			System.out.println("Ran out of memory");
		}
	}
	
	public void solve(){
		
		while (!stop ){
			
			this.PossibleMoves();
			currentBoard = auxQ.remove();
			if (this.isSolved()){
				stop = true;
			}
			
		}
		System.out.println("Total number of moves to win: "+currentBoard.myMoveSize());
		System.out.println();
		System.out.print(currentBoard.displayMoves()); 
		System.out.println();
		System.out.println("The final board configuration is:\n"+ currentBoard);	
		
	}
	public boolean isSolved(){
		
		Block J1 = winBoard.getBlocksPosition().get(new Point(3,1));
		Block J2 = winBoard.getBlocksPosition().get(new Point(4,2));
		
		HashMap<Point, Block> currTray = currentBoard.getBlocksPosition();
		
		if (currTray.containsKey(new Point(3,1)) && currTray.containsKey(new Point(4,2))){
			Block currentB1 = currentBoard.getBlocksPosition().get(new Point(3,1));
			Block currentB2 = currentBoard.getBlocksPosition().get(new Point(4,2));
			if (J1.equals(currentB1) && J2.equals(currentB2)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
		
		
	}
	/**
	 * This method first finds the open space and then tries all the possible moves
	 */
	
	private void PossibleMoves(){
		HashMap<Point, Block> currTray = currentBoard.getBlocksPosition(); //get the current board hashmap from the board class
		for (int i=0; i < currentBoard.getWidth(); i++){
			for(int j=0; j < currentBoard.getHeight(); j++){
				if (!currTray.containsKey(new Point(i,j))){ 
					
					if (i-1 >= 0){ 
						this.tryMove(new Point(i-1, j), "right");}
					if(i+1 < currentBoard.getWidth()){
						this.tryMove(new Point(i+1, j), "left");}
					if(j-1 >= 0){
						this.tryMove(new Point(i, j-1), "down");}
					if(j+1 < currentBoard.getHeight()){
						this.tryMove(new Point(i, j+1), "up");}
				}
			}
		}	
	}
	private void tryMove(Point p, String direction){
		
		HashMap<Point, Block> currTray = currentBoard.getBlocksPosition();
		if (currTray.containsKey(p)){ 
			Point upperl = currTray.get(p).UpperLeft();
			Point moveto = new Point(-1,-1);
			if (direction.equals("right")){
				moveto = new Point(upperl.x+1, upperl.y);
			}else if (direction.equals("left")){
				moveto = new Point(upperl.x-1, upperl.y);
			}else if (direction.equals("up")){
				moveto = new Point(upperl.x, upperl.y-1);
			}else if (direction.equals("down")){
				moveto = new Point(upperl.x, upperl.y+1);
			}

			try{
				Board possibleBoard = currentBoard.makeMove(currTray.get(p), moveto); 
				
				if (!alreadySeen.contains(possibleBoard)){
					
					auxQ.add(possibleBoard);
					alreadySeen.add(possibleBoard);  	
				}
					
			}catch(IllegalStateException e){
				return;
			}
		}
	}
	public HashSet<Board> getBoards(){
		return alreadySeen;
	}

}