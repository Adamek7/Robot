package pl.edu.agh.kis;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Drawer implements DrawerInterface{
	private final BoardInterface board;
	private final RobotInterface robot;
	
	private boolean stepSaveFlag = false;	// flaga, która ustala, czy ma byæ wykonywany zapis po ka¿dym kroku
	private String stepFileName;	// nazwa pliku do którego maj¹ byæ zapisywane stany po k¹zdym kroku
	
	public void setStepSaving(String filestepFileName){	// funkcje ustawiaj¹ca zapis krokowy
		if(stepSaveFlag) stepSaveFlag = false;
		else stepSaveFlag = true;
		this.stepFileName = filestepFileName;
	}
	
	public boolean isStepSaving(){
		return stepSaveFlag;
	}
	/**
	 * Konstruktor ustawiaj¹cy parametry klasy odpowiedzialnej za rysowanie planszy
	 * @param robot pole potrzebne do rysowania pozycji robota
	 * @param board pole potrzebne do rysowania stanu planszy
	 */
	public Drawer(RobotInterface robot, BoardInterface board){
		this.robot = robot;
		this.board = board;
		Date date = new Date();
		this.stepFileName = (date.getDate()) + "-" + (date.getMonth()+1) + "-" + (date.getYear()+1900) + "-" + date.getHours() + "-" + date.getMinutes() + "-" + date.getSeconds();  
	}
	public void drawToFile(){
		try {
			String path = "savedFiles//";
			String FileName = path+stepFileName+".txt";
			//BufferedWriter w = new BufferedWriter(new FileWriter(FileName, true));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileName, true),"UTF-8"));
		
			for(int i = 0; i < board.getBoardSize(); ++i){
				for(int j = 0; j < board.getBoardSize(); ++j){
					if(i == robot.getVPosition() && j == robot.getHPosition()){
						if(robot.getDirection() == 0) bw.append("^ ");
						else if(robot.getDirection() == 2) bw.append("v ");
						else if(robot.getDirection() == 1) bw.append("< ");
						else if(robot.getDirection() == 3) bw.append("> ");
					}
					else if(board.isBrick(i, j)){
						bw.append("+ ");
					}
					else if(board.isWall(i, j)){
						bw.append("x ");
					}
					else{
						bw.append("o ");
					}
				}
				bw.newLine();
			}
			bw.newLine();
			/*try {
				//Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void draw(){
		for(int i = 0; i < board.getBoardSize(); ++i){
			for(int j = 0; j < board.getBoardSize(); ++j){
				if(i == robot.getVPosition() && j == robot.getHPosition()){
					if(robot.getDirection() == 0) System.out.print("^ ");
					else if(robot.getDirection() == 2) System.out.print("v ");
					else if(robot.getDirection() == 1) System.out.print("< ");
					else if(robot.getDirection() == 3) System.out.print("> ");
				}
				else if(board.isBrick(i, j)){
					System.out.print("+ ");
				}
				else if(board.isWall(i, j)){
					System.out.print("x ");
				}
				else{
					System.out.print("o ");
				}
			}
			System.out.println();
		}
		System.out.println();
		/*try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}
