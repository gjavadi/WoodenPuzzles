
import java.io.*;

public class Readinput {

	/**
	 * This class reads the initial board configuration and the goal configuration from the file
	 * It print an error message if the file in not accessible and also checks the board's dimension
	 */
	
	private Board board;
	public Readinput(String inputfile, int row, int col){
		int numrows = 0;
		int numcols = 0;
		BufferedReader in = null;
		try{
			in = new BufferedReader(new InputStreamReader (new FileInputStream (inputfile)));
		}catch(Exception e){
			System.err.println ("Cannot access the input files!"); 
			System.exit (1);
		}
		if (row == 0 && col == 0){     //just to read dimension of the board
			try{
				String inputdimension = in.readLine();
				
				String[] dimension = inputdimension.split(" ");
				
				if (dimension.length != 2){
					System.err.println("Board dimension is missing"); 
					System.exit (1);
				}
				numrows = Integer.parseInt(dimension[0]);
				numcols = Integer.parseInt(dimension[1]);

			}catch (IOException e){
				System.err.println ("Empty file");
				System.exit (1);
			}
		}else{
			numrows = row;
			numcols = col;
		}
		board = new Board(numrows, numcols);
		while (true){ 
			try{
				String nextline = in.readLine();
				if (nextline == null){
					break;
				}else{
					String[] point = nextline.split(" ");
					if (point.length != 4){
						System.err.println("Wrong number of input in " + nextline);
					}
					Point p1 = new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1]));
					Point p2 = new Point(Integer.parseInt(point[2]), Integer.parseInt(point[3]));
					Block a = new Block(p1, p2);
					
					board.fillBlock(a);		
				}
			}catch(IOException e){
				System.err.println(e);
				System.exit(1);
			 }
		}	
	}
	public Board board(){
		return board;
	}

}