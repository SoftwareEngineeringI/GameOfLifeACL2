import static org.junit.Assert.*;

import org.junit.Test;


public class GridTest {

	@Test
	public void testAbove() {
		Grid grid = new Grid (10,10);
		assertEquals("normal case",5, grid.above(15));
		assertEquals("topEdgeCase",100,grid.above(10));
		try{
			grid.above(-1);
			fail("above did not throw exception!");
		}catch (Exception e){
			
		}
	}

	@Test
	public void testBelow() {
		Grid grid = new Grid (10,10);
		assertEquals("normal case",15, grid.below(5));
		assertEquals("bottomEdgeCase",10,grid.below(100));
		try{
			grid.below(101);
			fail("below did not throw exception");
		}catch (Exception e){
			
		}
	}

	@Test
	public void testLeft() {
		Grid grid = new Grid (10,10);
		assertEquals("normal case",5, grid.left(6));
		assertEquals("leftEdgeCase",10,grid.left(1));
		try{
			grid.left(250);
			fail("left did not throw exception");
		}catch (Exception e){
			
		}
	}

	@Test
	public void testRight() {
		Grid grid = new Grid (10,10);
		assertEquals("normal case",5, grid.right(4));
		assertEquals("rightEdgeCase",1,grid.right(10));
		try{
			grid.right(230);
		}catch (Exception e){
			
		}
	}


}
