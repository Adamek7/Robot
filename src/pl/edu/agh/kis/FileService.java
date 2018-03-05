package pl.edu.agh.kis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class FileService implements FileServiceInterface{
	private final RobotInterface robot;
	private final BoardInterface board;
	private Queue<Integer> startLines = new LinkedList<>();
	private Queue<String> functionNames = new LinkedList<>();
	/**
	 * Konstruktor ustawiaj¹cy parametry klasy odpowiedzialnej za obs³uge plików
	 * @param robot pole potrzebne do zapisu/wczytania pozycji robota
	 * @param board pole potrzebne do zapisu/wczytania stanu planszy
	 */
	public FileService(RobotInterface robot, BoardInterface board){
		this.robot = robot;
		this.board = board;
	}
	
	public boolean startLinesIsEmpty(){
		return startLines.isEmpty();
	}
	
	public int pollStartLine(){
		return startLines.poll();
	}
	
	public String pollFunctionName(){
		return functionNames.poll();
	}
	public void loadFile(String name){
		try{
			functionNames.clear();
			startLines.clear();
			FileInputStream is = new FileInputStream("configurationFiles//" + name + ".txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String ln = "";
			ln = br.readLine();
			String[] splitedLines;
			board.setBoardSize(Integer.parseInt(ln));
			for(int i=0; i<board.getBoardSize(); ++i){
				ln = br.readLine();
				splitedLines = ln.split(" ");
				for(int j=0; j<board.getBoardSize(); ++j){
					board.setFieldState(i, j, Integer.parseInt(splitedLines[j]));;
				}
			}
			ln = br.readLine();
			splitedLines = ln.split(" ");
			if(splitedLines.length != 5) throw new NullPointerException();
			robot.setRobotParametres(Integer.parseInt(splitedLines[0]), Integer.parseInt(splitedLines[1]), Integer.parseInt(splitedLines[2]), Integer.parseInt(splitedLines[3]), Integer.parseInt(splitedLines[4]));
			while((ln = br.readLine())!= null){
				splitedLines = ln.split(" ");
				functionNames.add(splitedLines[0]);
				startLines.add(Integer.parseInt(splitedLines[1]));
			}
			br.close();
			
			
		}catch(IOException e){
			System.out.println("Nie ma takiego pliku!");
		}catch(NullPointerException e2){
			System.out.println("Pusty plik");
		}catch(NumberFormatException e1){
		System.out.println("Z³y format pliku konfiguracyjnego");
	}
		
	}
	
	public void saveFile(String name) throws IOException{
		String path = "configurationFiles//";
		File file = new File(path, name + ".txt");
		//BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()),"UTF-8"));
		bw.append(board.getBoardSize() + "\n");
		for(int i=0; i<board.getBoardSize(); i++){
			for(int j=0; j<board.getBoardSize(); j++){
				if(board.isBrick(i, j) == true) bw.append(board.getNumberOfBricks(i, j) + " ");
				else if(board.isWall(i, j) == true)bw.append("-1 ");
				else bw.append("0 ");
			}
			bw.newLine();
		}
		bw.append(robot.getVPosition() + " " + robot.getHPosition() + " " + robot.getDirection() + " " + robot.getSocketCapacity() + " " + robot.getSocketContent());
		bw.close();
	}
	public void saveFile(String name, String state) throws IOException{
		String path = "configurationFiles//";
		File file = new File(path, name + ".txt");
		//BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()),"UTF-8"));
		bw.append(board.getBoardSize() + "\n");
		for(int i=0; i<board.getBoardSize(); i++){
			for(int j=0; j<board.getBoardSize(); j++){
				if(board.isBrick(i, j) == true) bw.append(board.getNumberOfBricks(i, j) + " ");
				else if(board.isWall(i, j) == true) bw.append("-1 ");
				else bw.append("0 ");
			}
			bw.newLine();
		}
		bw.append(robot.getVPosition() + " " + robot.getHPosition() + " " + robot.getDirection() + " " + robot.getSocketCapacity() + " " + robot.getSocketContent() +"\n");
		bw.append(state);
		bw.close();
	}
}
