package pl.edu.agh.kis.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import pl.edu.agh.kis.Board;
import pl.edu.agh.kis.FileService;
import pl.edu.agh.kis.Robot;

public class FileServiceTest {
	Board board = new Board(10);
	FileService fileService = new FileService(new Robot(board, 1, 0, 0, 0, 0), board);
	@Test
	public void testStartLinesIsEmpty() {
		assertEquals(true, fileService.startLinesIsEmpty());
	}

	@Test
	public void testLoadFile() {
		try{
			fileService.loadFile("NieMa");
	        fileService.loadFile("testek");
	        fileService.loadFile("ZLY");
		}catch(Exception e){
			fail("Exception");
		}
		        
		   
	}

	@Test
	public void testSaveFileString() {
		try{
			fileService.saveFile("TEST1");
		}catch(IOException e){
			fail("Exception");
		}
			
	}

	@Test
	public void testSaveFileStringString() {
		try{
			fileService.saveFile("TEST2", "stan");
		}catch(IOException e){
			fail("Exception");
		}
	}

}
