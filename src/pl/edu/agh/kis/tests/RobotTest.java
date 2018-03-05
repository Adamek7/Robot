package pl.edu.agh.kis.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.edu.agh.kis.Board;
import pl.edu.agh.kis.Robot;

public class RobotTest {
	Robot robot = new Robot(new Board(10), 1, 0, 0, 0, 0);
	@Test
	public void testSetRobotParametres() {
		robot.setRobotParametres(0, 0, 0, 1, 0);
		assertEquals(0, robot.getVPosition());
		assertEquals(0, robot.getHPosition());
		assertEquals(0, robot.getDirection());
		assertEquals(1, robot.getSocketCapacity());
		assertEquals(0, robot.getSocketContent());
		robot.setRobotParametres(-1, -1, 5, 0, -2);
		assertFalse(-1 == robot.getVPosition());
		assertFalse(-1 == robot.getHPosition());
		assertFalse(5 == robot.getDirection());
		assertFalse(0 == robot.getSocketCapacity());
		assertFalse(-2 == robot.getSocketContent());
	}

	@Test
	public void testGetSocketCapacity() {
		robot.setRobotParametres(0, 0, 0, 1, 0);
		assertEquals(1, robot.getSocketCapacity());
		robot.setRobotParametres(-1, -1, 5, 0, -2);
		assertFalse(0 == robot.getSocketCapacity());
	}

	@Test
	public void testGetSocketContent() {
		robot.setRobotParametres(0, 0, 0, 1, 0);
		assertEquals(0, robot.getSocketContent());
		robot.setRobotParametres(-1, -1, 5, 0, -2);
		assertFalse(-2 == robot.getSocketContent());
	}

	@Test
	public void testGetVPosition() {
		robot.setRobotParametres(0, 0, 0, 1, 0);
		assertEquals(0, robot.getVPosition());
		robot.setRobotParametres(-1, -1, 5, 0, -2);
		assertFalse(-1 == robot.getVPosition());
	}

	@Test
	public void testGetHPosition() {
		robot.setRobotParametres(0, 0, 0, 1, 0);
		assertEquals(0, robot.getHPosition());
		robot.setRobotParametres(-1, -1, 5, 0, -2);
		assertFalse(-1 == robot.getHPosition());
	}

	@Test
	public void testGetDirection() {
		robot.setRobotParametres(0, 0, 0, 1, 0);
		assertEquals(0, robot.getDirection());
		robot.setRobotParametres(-1, -1, 5, 0, -2);
		assertFalse(5 == robot.getDirection());

	}

	@Test
	public void testMove() {
		robot.setRobotParametres(0, 0, 0, 1, 0);
		assertEquals(false, robot.move());
		robot.setRobotParametres(-1, -1, 5, 0, -2);
		assertEquals(false, robot.move());
		robot.setRobotParametres(0, 0, 2, 1, 0);
		assertEquals(true, robot.move());
	}

	@Test
	public void testTurnLeft() {
		robot.setRobotParametres(0, 0, 0, 1, 0);
		assertEquals(true, robot.turnLeft());
	}

	@Test
	public void testTake() {
		robot.setRobotParametres(0, 0, 0, 1, 0);
		assertEquals(false, robot.take());
		robot.setRobotParametres(-1, -1, 5, 0, -2);
		assertEquals(false, robot.take());
		
		
	}

	@Test
	public void testPut() {
		robot.setRobotParametres(0, 0, 0, 1, 1);
		assertEquals(true, robot.put());
	}

	@Test
	public void testIsWall() {
		robot.setRobotParametres(0, 0, 0, 1, 1);
		assertEquals(true, robot.isWall());
		robot.setRobotParametres(0, 0, 2, 1, 1);
		assertEquals(false, robot.isWall());
	}

	@Test
	public void testIsBrick() {
		robot.setRobotParametres(0, 0, 0, 1, 1);
		assertEquals(false, robot.isBrick());
		robot.setRobotParametres(-1, -1, 2, 1, 1);
		assertEquals(false, robot.isBrick());
	}

}
