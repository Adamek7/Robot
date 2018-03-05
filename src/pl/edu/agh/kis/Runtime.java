package pl.edu.agh.kis;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Runtime implements RuntimeInterface {
	private BoardInterface board = new Board(10);
	private RobotInterface robot = new Robot(board, 10, 5, 0, 9, 2);
	private FileServiceInterface fileService = new FileService(robot,board);
	private DrawerInterface drawer = new Drawer(robot,board);
	private InterpreterInterface interpreter = new Interpreter(robot, fileService, drawer);
	
	public void startRun() throws IOException{
		System.out.println("Witaj!");
		Scanner odczyt = new Scanner(System.in);
		System.out.println("Aby rozpocz¹æ wybierz 1, pozosta³e koñczy program.");
		String data = odczyt.nextLine();
		try{
			if(Integer.parseInt(data) == 1){
				System.out.println("Aby w³aczyæ zapis krokowy podaj 1.");
				data = odczyt.nextLine();
				if(Integer.parseInt(data) == 1){
					System.out.println("Wpisz nazwe pliku do zapisu stanu");
					data = odczyt.nextLine();
					drawer.setStepSaving(data);
				}
				while(true){
					try{
						System.out.println("Wybór rozkazu - 1, Wczytanie stanu z pliku - 2, Zapis stanu do pliku - 3 Wyœwietlenie stanu - 4, Wykonanie jednego kroku - 5,  zakoñcz others");
						odczyt = new Scanner(System.in);
						data = odczyt.nextLine();
						
						if(Integer.parseInt(data) == 1){
							try{
								String[] funList = new File("functions").list();
								System.out.println("Dostêpne funkcje: ");
								for(String fun : funList){
									System.out.println(fun.replace(".txt", ""));
								}
								System.out.println("oraz wszystkie podstawowe funkcje robota");
							}catch(NullPointerException e){
								e.printStackTrace();
							}
							
							System.out.println("Wpisz rozkaz");
							data = odczyt.nextLine();
							//drawer.draw();
							interpreter.interpret(data);
							//drawer.draw();
							interpreter.resetBreakFlag();
							if(!drawer.isStepSaving()) drawer.drawToFile();
						}
						else if(Integer.parseInt(data) == 2){
							try{
								String[] funList = new File("configurationFiles").list();
								System.out.println("Dostêpne pliki konfiguracyjne: ");
								for(String fun : funList){
									System.out.println(fun.replace(".txt", ""));
								}
							}catch(NullPointerException e){
								e.printStackTrace();
							}
							
							System.out.println("Wpisz nazwe pliku");
							data = odczyt.nextLine();
							fileService.loadFile(data);
							//drawer.draw();
							if(!fileService.startLinesIsEmpty()){
								interpreter.interpret(fileService.pollFunctionName(), fileService.pollStartLine());
								interpreter.resetBreakFlag();
							}
							
							
						}
						else if(Integer.parseInt(data) == 3){
							System.out.println("Wpisz nazwe pliku");
							data = odczyt.nextLine();
							fileService.saveFile(data);
						}
						else if(Integer.parseInt(data)==4){
							drawer.draw();
						}
						else if(Integer.parseInt(data) == 5){
							interpreter.setStepFlag();
						}
						else break;
					}catch(NumberFormatException e){
						System.out.println("Z³y format wprowadania!");
						continue;
					}
					
				}
			}
			odczyt.close();
			System.out.println("Koniec!");
		}catch(NumberFormatException e){
			System.out.println("Z³y format");
			startRun();
		}
		
		
	}
}
