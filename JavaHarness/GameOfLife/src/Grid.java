
import java.util.HashSet;
import java.util.Set;

public class Grid {
	
	private int width;
	private int height;
	private Set<Integer> aliveCells;
	
	public Grid (int width, int height){
		this.width = width;
		this.height = height;
		this.aliveCells = new HashSet<Integer>();
	}
	
	private int above ( int place) {	
		return 0;
	}
	
	private int below ( int place) {
		return 0;
	}
	
	private int left (int place) {
		return 0;
	}
	
	private int right (int place) {
		return 0;
	}
	
	private int upLeft (int place){
		return this.left(this.above(place));
	}
	
	private int upRight (int place){
		return this.right(this.above(place));
		
	}

	private int downLeft (int place){
		return this.left(this.below(place));
	
	}

	private int downRight (int place){
		return this.right(this.below(place));
	
	}

	public void single(int place){
		aliveCells.add(place);
	}
	

	public void block(int place){
		aliveCells.add(place);
		aliveCells.add(below(place));
		aliveCells.add(right(place));
		aliveCells.add(downRight(place));
	}
	
	public void beehive( int place) {
		aliveCells.add(above(place));
		aliveCells.add(below(place));
		aliveCells.add(left(place));
		aliveCells.add(upRight(place));
		aliveCells.add(downRight(place));
		aliveCells.add(right(right(place)));
	}
	
	public void blinker ( int place){
		aliveCells.add(place);
		aliveCells.add(above(place));
		aliveCells.add(below(place));
	}
	
	public void glider(int place){
		aliveCells.add(above(place));
		aliveCells.add(right(place));
		aliveCells.add(below(place));
		aliveCells.add(downLeft(place));
		aliveCells.add(downRight(place));
	}
	
	public void setStartRPentomino( int place){
		aliveCells.clear();
		aliveCells.add(place);
		aliveCells.add(above(place));
		aliveCells.add(upRight(place));
		aliveCells.add(left(place));
		aliveCells.add(below(place));
	}
	
	public void clearGrid(){
		aliveCells.clear();
	}
	
	//Getters and Setters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Set<Integer> getAliveCells() {
		return aliveCells;
	}

	public void setAliveCells(Set<Integer> aliveCells) {
		this.aliveCells = aliveCells;
	}
	
}
