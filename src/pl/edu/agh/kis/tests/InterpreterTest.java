package pl.edu.agh.kis.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import pl.edu.agh.kis.Board;
import pl.edu.agh.kis.Drawer;
import pl.edu.agh.kis.FileService;
import pl.edu.agh.kis.Interpreter;
import pl.edu.agh.kis.InterpreterInterface;
import pl.edu.agh.kis.Robot;

public class InterpreterTest {
	Board board = new Board(10);
	Robot robot = new Robot(board, 1, 0, 0, 0, 0);
	Drawer drawer = new Drawer(robot, board);
	FileService fileService = new FileService(robot, board);
	Interpreter interpreter = new Interpreter(robot, fileService, drawer);


	@Test
	public void testInterpretString() {
		try{
			interpreter.interpret("NIEMA");
			interpreter.interpret("ZLY");
		}catch(IOException e){
			fail("Exception");
		}
		
	}

	@Test
	public void testInterpretStringInt() {
		try{
			interpreter.interpret("NIEMA", 5);
			interpreter.interpret("ZLY", 5);
		}catch(IOException e){
			fail("Exception");
		}
		
	}

}
