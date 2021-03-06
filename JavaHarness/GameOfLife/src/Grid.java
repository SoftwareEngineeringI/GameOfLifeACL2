
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
	
	private boolean inRange(int place)
	{
		if(0< place && width * height >= place)
			return true;
		return false;
	}
	public int above ( int place) {	
		if(!inRange(place)){
			throw new IllegalArgumentException("that location is outside of the grid!");
		}
		if((0< place) && (place <= width)){
			return (width * (height-1) + place);
		}else
			return place - width;
	}
	
	public int below ( int place) {
		if(!inRange(place)){
			throw new IllegalArgumentException("that location is outside of the grid!");
		}
		if((width * (height -1)< place) && (place <= width*height)){
			return ( place - width * (height -1) );
		}else
			return place + width;
	}
	
	public int left (int place) {
		if(!inRange(place)){
			throw new IllegalArgumentException("that location is outside of the grid!");
		}
		if(place % width == 1){
			return (place + (width - 1));
		}
		return place - 1;
	}
	
	public int right (int place) {
		if(!inRange(place)){
			throw new IllegalArgumentException("that location is outside of the grid!");
		}
		if(place % width == 0){
			return (place - (width - 1));
		}
		return place + 1;
	}
	
	public int upLeft (int place){
		return this.left(this.above(place));
	}
	
	public int upRight (int place){
		return this.right(this.above(place));
		
	}

	public int downLeft (int place){
		return this.left(this.below(place));
	
	}

	public int downRight (int place){
		return this.right(this.below(place));
	
	}

	public void single(int _height, int _width){
		int place = this.width * _height + _width;
		aliveCells.add(place);
	}
	

	public void block(int _height, int _width){
		int place = this.width * _height + _width;
		aliveCells.add(place);
		aliveCells.add(below(place));
		aliveCells.add(right(place));
		aliveCells.add(downRight(place));
	}
	
	public void beehive( int _height, int _width) {
		int place = this.width * _height + _width;
		aliveCells.add(above(place));
		aliveCells.add(below(place));
		aliveCells.add(left(place));
		aliveCells.add(upRight(place));
		aliveCells.add(downRight(place));
		aliveCells.add(right(right(place)));
	}
	
	public void blinker ( int _height, int _width){
		int place = this.width * _height + _width;
		aliveCells.add(place);
		aliveCells.add(above(place));
		aliveCells.add(below(place));
	}
	
	public void glider(int _height, int _width){
		int place = this.width * _height + _width;
		aliveCells.add(above(place));
		aliveCells.add(right(place));
		aliveCells.add(below(place));
		aliveCells.add(downLeft(place));
		aliveCells.add(downRight(place));
	}
	
	public void setStartRPentomino( int _height, int _width ){
		int place = this.width * _height + _width;
		aliveCells.clear();
		aliveCells.add(place);
		aliveCells.add(above(place));
		aliveCells.add(upRight(place));
		aliveCells.add(left(place));
		aliveCells.add(below(place));
	}
	
	public void gliderGun(int _height, int _width){
		single( _width +24, 8+ _height);
		single( _width +22, 7+ _height);
		single( _width +24, 7+ _height);
		single( _width +12, 6+ _height);
		single( _width +13, 6+ _height);
		single( _width +20, 6+ _height);
		single( _width +21, 6+ _height);
		single( _width +34, 6+ _height);
		single( _width +35, 6+ _height);
		single( _width +11, 5+ _height);
		single( _width +15, 5+ _height);
		single( _width +20, 5+ _height);
		single( _width +21, 5+ _height);
		single( _width +34, 5+ _height);
		single( _width +35, 5+ _height);
		single( _width +0, 4+ _height);
		single( _width +1, 4+ _height);
		single( _width +10, 4+ _height);
		single( _width +16, 4+ _height);
		single( _width +20, 4+ _height);
		single( _width +21, 4+ _height);
		single( _width +0, 3+ _height);
		single( _width +1, 3+ _height);
		single( _width +10, 3+ _height);
		single( _width +14, 3+ _height);
		single( _width +16, 3+ _height);
		single( _width +17, 3+ _height);
		single( _width +22, 3+ _height);
		single( _width +24, 3+ _height);
		single( _width +10, 2+ _height);
		single( _width +16, 2+ _height);
		single( _width +24, 2+ _height);
		single( _width +11, 1+ _height);
		single( _width +15, 1+ _height);
		single( _width +12, 0+ _height); 
		single( _width +13, 0+ _height);
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
