package pl.edu.agh.kis.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.edu.agh.kis.Board;

public class BoardTest {
	
	private Board board = new Board(10);

	@Test
	public void testSetBoardSize() {
		board.setBoardSize(10);
		assertEquals(10, board.getBoardSize());
		board.setBoardSize(-1);
		assertFalse(-1 == board.getBoardSize());
	}

	@Test
	public void testGetBoardSize() {
		int size = -1;
		board.setBoardSize(size);
		assertFalse(board.getBoardSize() == size);
		size = 10;
		board.setBoardSize(size);
		assertEquals(board.getBoardSize(), size);
	}

	@Test
	public void testGetNumberOfBricks() {
		board.setFieldState(0, 0, 5);
		assertEquals(5, board.getNumberOfBricks(0, 0));
	}

	@Test
	public void testChangeFieldState() {
		board.setFieldState(0, 0, 5);
		board.changeFieldState(0, 0, true);
		assertFalse(5 == board.getNumberOfBricks(0, 0));
		board.changeFieldState(0, 0, false);
		assertTrue(5 == board.getNumberOfBricks(0, 0));
	}

	@Test
	public void testSetFieldState() {
		board.setFieldState(0, 0, -1);
		assertEquals(true, board.isWall(0, 0));
		board.setFieldState(0, 0, -2);
		assertFalse(true == board.isWall(0, 0));
		board.setFieldState(0, 0, 5);
		assertEquals(true, board.isBrick(0, 0));
		assertEquals(5, board.getNumberOfBricks(0, 0));
	}

	@Test
	public void testIsBrick() {
		board.setFieldState(0, 0, -1);
		assertEquals(false, board.isBrick(0, 0));
		board.setFieldState(0, 0, -2);
		assertEquals(false, board.isBrick(0, 0));
		board.setFieldState(0, 0, 2);
		assertEquals(true, board.isBrick(0, 0));
	}

	@Test
	public void testIsWall() {
		board.setFieldState(0, 0, -1);
		assertEquals(true, board.isWall(0, 0));
		board.setFieldState(0, 0, -2);
		assertEquals(false, board.isWall(0, 0));
		board.setFieldState(0, 0, 2);
		assertEquals(false, board.isWall(0, 0));
	}

}
