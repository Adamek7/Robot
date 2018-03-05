package pl.edu.agh.kis.tests;

import static org.junit.Assert.*;



import org.junit.Test;

import pl.edu.agh.kis.Board;
import pl.edu.agh.kis.Drawer;
import pl.edu.agh.kis.Robot;

public class DrawerTest {
	Board board = new Board(10);
	Drawer drawer = new Drawer(new Robot(board, 1, 0, 0, 0, 0), board);
	@Test
	public void testSetStepSaving() {
		drawer.setStepSaving("Test");
		assertEquals(true, drawer.isStepSaving());
		drawer.setStepSaving("Test");
		assertEquals(false, drawer.isStepSaving());
	}

	@Test
	public void testIsStepSaving() {
		drawer.setStepSaving("Test");
		assertEquals(true, drawer.isStepSaving());
		drawer.setStepSaving("Test");
		assertEquals(false, drawer.isStepSaving());
	}

}
