
/**
 * 
 * The Block class makes a block according to the upper left and lower right point of the block
 * From these two points, the height and width of the block is calculated 
 * The "moveBlock" method moves the block to the new location on the board
 *
 */

public class Block { 

	private Point UpperLeft; 
	private Point LowerRight; 
	private String size; 

	public Block(Point one, Point two) { 

		UpperLeft = one; 
		LowerRight = two; 

		size = Integer.toString(getHeight()) + "x" + Integer.toString(getWidth()) ; 
	} 

	public Point UpperLeft(){ 
		return UpperLeft; 
	} 

	public Point LowerRight(){ 
		return LowerRight; 
	} 

	public int getHeight() { 
		return ((LowerRight.y - UpperLeft.y) + 1); 
	} 

	public int getWidth() { 
		return ((LowerRight.x - UpperLeft.x) + 1); 
	} 

	public void moveBlock(Point one, Point two) 
	{ 
		
		UpperLeft = one; 
		LowerRight = two; 
	} 

	public String getType() { 
		return size; 
	} 
	

	public boolean equals(Object o){
		Block block = (Block) o;
		return block.UpperLeft.equals(UpperLeft) && block.LowerRight.equals(LowerRight);
	}
	public String toString(){
		return " (" + this.UpperLeft + this.LowerRight + ") ";
	}
}
