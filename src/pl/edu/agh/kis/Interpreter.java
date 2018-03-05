package pl.edu.agh.kis;


import java.io.*;
import java.util.*;


public class Interpreter implements InterpreterInterface{
	
	private boolean breakFlag = false; // flaga kt�ra przerywa wykonywanie makra
	private boolean stepFlag = false;
	public void resetBreakFlag(){	// fukncja resetuj�ca flag� przerywaj�c� p�tl�
		this.breakFlag = false;
	}
	public void setStepFlag(){
		stepFlag = true;
	}
	private final RobotInterface robot;
	private final FileServiceInterface fileService;
	private final DrawerInterface drawer;
	
	private Stack<String> performedMacros = new Stack<>();	// stos pami�taj�cy nazwy wykonyj�cych sie makr
	private Stack<Integer> performedMacrosLine = new Stack<>();	// stos pami�taj�cy aktualne linie wykonuj�acych sie makr
	
	private InputStreamReader is = new InputStreamReader(System.in); // nas�uchuje na wci�ci�cie enter, wtedy makro jest wstrzymywane

	private final ArrayList<String> currentMacro = new ArrayList<>();	// zapisane kolejne linie aktualnie wykonywanego makra
	private final HashMap<String, ArrayList<String> > savedMacros = new HashMap<>(); // mapa pami�taj�ca wykonane ju� makra

 	private static final List<String> keyWords = // lista s��w kluczowych j�zyka
			Collections.unmodifiableList(Arrays.asList("procedure", "begin", "end", "if", "not", "while"));
	private static final List<String> builtInFunctions = 	//lista podstawowych operacji kt�re mo�e wykona� robot
			Collections.unmodifiableList(Arrays.asList("move", "turnLeft", "put", "take", "isWall", "isBrick"));
	
	/**
	 * Konstruktor ustawiaj�cy parametry klasy odpowiedzialnej za interpretowanie polece�
	 * @param robot pole potrzebne aby m�� sterowa� robotem
	 * @param fileService pole potrzebne aby w danym momencie interpretownia mo�na by�o zapisa� stan do pliku
	 * @param drawer pole potrzebne aby w danym momencie interpretowania mo�na by�o narysowac plansz�
	 */
	public Interpreter(RobotInterface robot, FileServiceInterface fileService, DrawerInterface drawer){
		this.robot = robot;
		this.fileService = fileService;
		this.drawer = drawer;
	}
	
	
	private boolean validFile(BufferedReader br, String fun, boolean clearFlag) throws IOException{	//funkcja sprawdzaj�ca czy podane makro jest poprawne
		if(clearFlag) System.out.println("Funkcja: " + fun);
		int begins = 0; // licznik wyst�pie� znacznik�w pocz�tku bloku
		int ends = 0;	// licznk wystapie� znacznik�w ko�ca bloku
		int currentLine = 0; // aktualna linia
		boolean correctFlag = true; // flaga poprawno�ci pliku
		if(clearFlag) currentMacro.clear(); // czyszczenie listy linii
		String ln;	//  jedna linia pliku
		String[] splitedLines;	// rozdzielona jedna linia pliku
		try{
			ln = br.readLine();
			currentLine++;
			ln = ln.replace("\t", "");
			splitedLines = ln.split(" ");
			if(!(splitedLines[0].equals("procedure")) || !(splitedLines[1].equals(fun)) || splitedLines.length != 2){
				System.out.println("B�ad w linii " + currentLine);
				correctFlag = false;
			}
			else{
				if(clearFlag) currentMacro.add(ln);
			}
			ln = br.readLine();
			ln = ln.replace("\t", "");
			currentLine++;
			if(!(ln.equals("begin"))){
				System.out.println("B�ad w linii " + currentLine + " brak begin");
				correctFlag = false;
				
			}
			else{
				if(clearFlag) currentMacro.add(ln);
				begins++;
			}
			
			while((ln = br.readLine())!=null) {
				
				currentLine++;
				ln = ln.replace("\t", "");
				if(clearFlag) currentMacro.add(ln);
				splitedLines = ln.split(" ");
					if(splitedLines[0].equals("if") || splitedLines[0].equals("while")){
						if(splitedLines.length < 2 || splitedLines.length > 3){
							System.out.println("B�ad w linii1 " + currentLine);
							correctFlag = false;
						}
						else if(splitedLines.length == 2){
							if(!builtInFunctions.contains(splitedLines[1])){
								System.out.println("B�ad w linii2 " + currentLine);
								correctFlag = false;
							}
						}
						else if(splitedLines.length == 3){
							if(!(splitedLines[1].equals("not")) || !builtInFunctions.contains(splitedLines[2])){
								System.out.println("B�ad w linii3 " + currentLine);
								correctFlag = false;
							}
						}
						ln = br.readLine();
						ln = ln.replace("\t", "");
						if(!(ln.equals("begin"))){
							currentLine++;
							System.out.println("B�ad w linii4 " + currentLine);
							correctFlag = false;
						} else {
							if(clearFlag) currentMacro.add("begin");
							begins++;
							currentLine++;
						}
						
					}
					else if(splitedLines[0].equals(fun)){
						System.out.println("B�ad w linii(brak rekurencji)" + currentLine);
						correctFlag = false;
					}
					else if(splitedLines[0].equals("end")) ends++;
					else if(splitedLines[0].equals("begin")) begins++;
					else if(splitedLines[0].equals("not")){
						System.out.println("B�ad w linii5 " + currentLine);
						correctFlag = false;
					}
					else if(splitedLines[0].equals("procedure")){
						System.out.println("B�ad w linii5 " + currentLine);
						correctFlag = false;
					}
					else if(splitedLines.length > 1){
						System.out.println("B�ad w linii5 " + currentLine);
						correctFlag = false;
					}
					else if(!builtInFunctions.contains(splitedLines[0])){
						try{
							String[] funList = new File("functions").list();
							for(String oneFun : funList){
								correctFlag = splitedLines[0].equals(oneFun.replace(".txt", ""));
								if(correctFlag){
									FileInputStream fis1 = new FileInputStream("functions//" + splitedLines[0] + ".txt");
									BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1, "UTF-8"));
									correctFlag = validFile(br1, splitedLines[0], false);
									//System.out.println(correctFlag);
									break;
								}
							}
							//if(!correctFlag) System.out.println("b��d funkcji w linii " + currentLine + " podane makro nie istnieje");
						}catch(NullPointerException e){
							correctFlag = false;
							e.printStackTrace();
						}
					}
					
					
					if(ends > begins){
						System.out.println("b��d funkcji w linii " + currentLine);
						return false;	
						
					}
				
				
				
			}
			if(begins != ends){
				System.out.println("b��d: niezgodna ilo�� znacznik�w bloku");
				correctFlag = false;
			}
		}catch(NullPointerException e){
			correctFlag = false;
			System.out.println("Z�y format pliku " + fun);
		}catch(ArrayIndexOutOfBoundsException e1){
			System.out.println("Z�a d�ugo�� linii " + currentLine);
			correctFlag = false;
		}
		
		return correctFlag;
	}
	
	private boolean doBuiltInFunctionAndDraw(String fun, int line) throws IOException{	//funkcja wykonuj�ca podan� operacj� elementarn� oraz rysuj�ca stan planszy
		boolean conditionFlag = false;
			switch (fun){
			case "move": conditionFlag = robot.move(); if(drawer.isStepSaving()) drawer.drawToFile(); drawer.draw(); if(stepFlag) pauseAndSave(line); break;
			case "turnLeft": conditionFlag = robot.turnLeft(); if(drawer.isStepSaving()) drawer.drawToFile(); drawer.draw(); if(stepFlag) pauseAndSave(line); break;
			case "take": conditionFlag = robot.take(); if(drawer.isStepSaving()) drawer.drawToFile(); drawer.draw(); if(stepFlag) pauseAndSave(line); break;
			case "put": conditionFlag = robot.put(); if(drawer.isStepSaving()) drawer.drawToFile(); drawer.draw(); if(stepFlag) pauseAndSave(line); break;
			case "isWall": conditionFlag = robot.isWall(); break;
			case "isBrick": conditionFlag = robot.isBrick(); break;
			default: System.out.println("wrong " + fun);
		}
		
		
		return conditionFlag;
		
	}
		
	public void interpret(String fun) throws IOException{ // funkcja interpretuj�ca podane makro
		
		if(builtInFunctions.contains(fun)){
			doBuiltInFunctionAndDraw(fun, 0);
		}
		else{
			boolean validatedFlag = true; // flaga reprezentuj�ca wynik sprawdzenia poprawnosci pliku
			try{
				if(!savedMacros.containsKey(fun)){
						FileInputStream fis = new FileInputStream("functions//" + fun + ".txt");
						BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
						if(validatedFlag = validFile(br, fun, true)) savedMacros.put(fun, new ArrayList<String>(currentMacro));
				}
				if(validatedFlag){
					int currentLine = 0;
					performedMacros.add(fun);
					ArrayList<String> list = new ArrayList<>(); // lista linii aktualnie wykonywanego makra
					for(String l : savedMacros.get(fun)){
						list.add(l);
					}
					for(int i = 0; i < list.size(); ++i){
						if(!stepFlag) pauseAndSave(i);
						
						if(breakFlag){
							break;	
						}
						String line = list.get(i);
						String[] splitedLines = line.split(" ");
						if(builtInFunctions.contains(splitedLines[0])){
							doBuiltInFunctionAndDraw(splitedLines[0], i);
							
						}
						else{
							if(splitedLines[0].equals("if") || splitedLines[0].equals("while")){
								int begins = 0, ends = 0;
								currentLine = i;
								do{
									currentLine++;
									splitedLines = list.get(currentLine).split(" ");
									if(splitedLines[0].equals("begin")) begins++;
									if(splitedLines[0].equals("end")) ends++;
								}while(begins != ends);
								markLoop(list, i, currentLine);
								i=currentLine;
								
							}
							else if(!keyWords.contains(splitedLines[0])){
								performedMacrosLine.add(i+1);
								if(fileService.startLinesIsEmpty()) interpret(splitedLines[0]);
								else interpret(splitedLines[0], fileService.pollStartLine());
								if(!performedMacrosLine.isEmpty()) performedMacrosLine.pop();
							}
						}
					}
					
					if(!performedMacrosLine.isEmpty()) performedMacros.pop();	
				}
			}catch(IOException e){
				System.out.println("File not found!");
			}
		}
	}
	
	public void interpret(String fun, int startLine) throws IOException{ // przeci��ona funkcja interpretuj�ca podane makro od konkretnej linii
		if(builtInFunctions.contains(fun)){
			doBuiltInFunctionAndDraw(fun, 0);
		}
		else{
			boolean validatedFlag = true;
			try{
				if(!savedMacros.containsKey(fun)){
						FileInputStream fis = new FileInputStream("functions//" + fun + ".txt");
						BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
						if(validatedFlag = validFile(br, fun, true)) savedMacros.put(fun, new ArrayList<String>(currentMacro));
					
				}
				if(validatedFlag && startLine <= savedMacros.get(fun).size()){
					
					int currentLine = 0;
					performedMacros.add(fun);
					System.out.println();
					ArrayList<String> list = new ArrayList<>();
					for(String l : savedMacros.get(fun)){
						list.add(l);
					}
					for(int i = 0; i < list.size(); ++i){
						if(!stepFlag) pauseAndSave(i);
						if(breakFlag){
							break;	
						}
						i = goToLineAndMarkLoop(i, startLine, list);
						
						String line = list.get(i);
						String[] splitedLines = line.split(" ");
						if(builtInFunctions.contains(splitedLines[0])){
							doBuiltInFunctionAndDraw(splitedLines[0], i);
						}
						else{
							
							if(splitedLines[0].equals("if") || splitedLines[0].equals("while")){
								int begins = 0, ends = 0;
								currentLine = i;
								do{
									currentLine++;
									splitedLines = list.get(currentLine).split(" ");
									if(splitedLines[0].equals("begin")) begins++;
									if(splitedLines[0].equals("end")) ends++;
								}while(begins != ends);
								markLoop(list, i, i = currentLine);
								
								//i=currentLine;
								
							}
							else if(!keyWords.contains(splitedLines[0])){
								performedMacrosLine.add(i+1);
								if(fileService.startLinesIsEmpty()) interpret(splitedLines[0]);
								else interpret(splitedLines[0], fileService.pollStartLine());
								if(!performedMacrosLine.isEmpty()) performedMacrosLine.pop();
							}
						}
						
					}
					if(!performedMacros.isEmpty()) performedMacros.pop();	
				}
					
			}catch(IOException e){
				System.out.println("File not found!");
				
			}
		}
	}

	private void interpretIF(ArrayList<String> list, int start, int end) throws IOException{ // funkcja interpretuj�ca blok IF
		String[] splitedLines = list.get(start).split(" ");
		boolean flag = false;
		if(splitedLines.length == 2){
			flag = doBuiltInFunctionAndDraw(splitedLines[1], start);
		}
		else if(splitedLines.length == 3){
			flag = !doBuiltInFunctionAndDraw(splitedLines[2], start);
		}
		
		if(flag){
			for(int i = start+1; i<end+1; ++i){
				if(!stepFlag) pauseAndSave(i);
				if(breakFlag){
					break;	
				}
				
				String line = list.get(i);
				splitedLines = line.split(" ");
				int currentLine = i;
					if(splitedLines[0].equals("if") || splitedLines[0].equals("while")){
						int begins = 0, ends = 0;
						do{
			
							currentLine++;
							splitedLines = list.get(currentLine).split(" ");
							if(splitedLines[0].equals("begin")) begins++;
							if(splitedLines[0].equals("end")) ends++;
						}while(begins != ends);
						markLoop(list, i, currentLine);
						i=currentLine;
					}
					else if(builtInFunctions.contains(splitedLines[0])){
						doBuiltInFunctionAndDraw(splitedLines[0], i);
					}
					else if(!keyWords.contains(splitedLines[0])){
						
						performedMacrosLine.add(currentLine+1);
						interpret(splitedLines[0]);
						if(!performedMacrosLine.isEmpty()) performedMacrosLine.pop();
					}
				
			}
		}
			
		
	}
	
	private void interpretIF(ArrayList<String> list, int start, int end, int startLine) throws IOException{ // przeci��ona funkcja interpretuj�ca blok IF od danej linii
		String[] splitedLines;// = list.get(start).split(" ");
			for(int i = start+1; i<end+1; ++i){
				if(!stepFlag) pauseAndSave(i);
				if(breakFlag){
					break;	
				}
				i = goToLineAndMarkLoop(i, startLine, list);
				
				String line = list.get(i);
				splitedLines = line.split(" ");
				int currentLine = i;
					if(splitedLines[0].equals("if") || splitedLines[0].equals("while")){
						int begins = 0, ends = 0;
						do{
			
							currentLine++;
							splitedLines = list.get(currentLine).split(" ");
							if(splitedLines[0].equals("begin")) begins++;
							if(splitedLines[0].equals("end")) ends++;
						}while(begins != ends);
						markLoop(list, i, currentLine);
						i=currentLine;
					}
					else if(builtInFunctions.contains(splitedLines[0])){
						doBuiltInFunctionAndDraw(splitedLines[0], i);
					}
					else if(!keyWords.contains(splitedLines[0])){
						
						performedMacrosLine.add(currentLine+1);
						if(fileService.startLinesIsEmpty()) interpret(splitedLines[0]);
						else interpret(splitedLines[0], fileService.pollStartLine());
						if(!performedMacrosLine.isEmpty()) performedMacrosLine.pop();
					}
				
			}
	}
	
	private void interpretWHILE(ArrayList<String> list, int start, int end) throws IOException{  // funkcja interpretuj�ca blok WHILE
		boolean flag = false;
		while(true){
			if(breakFlag) {
			
			break;
			}
			String[] splitedLines = list.get(start).split(" ");
			if(splitedLines.length == 2){
				flag = doBuiltInFunctionAndDraw(splitedLines[1], start);
			}
			else if(splitedLines.length == 3){
				flag = !doBuiltInFunctionAndDraw(splitedLines[2], start);

			}
			if(flag){
				for(int i = start+1; i < end+1; ++i){
					if(!stepFlag) pauseAndSave(i);
					if(breakFlag){
						break;	
					}
					String line = list.get(i);
					splitedLines = line.split(" ");
						int currentLine = i;
						if(splitedLines[0].equals("if") || splitedLines[0].equals("while")){
							int begins = 0, ends = 0;
							
							do{
				
								currentLine++;
								splitedLines = list.get(currentLine).split(" ");
								if(splitedLines[0].equals("begin")) begins++;
								if(splitedLines[0].equals("end")) ends++;
							}while(begins != ends);
							markLoop(list, i, currentLine);
							i=currentLine;
						}
						else if(builtInFunctions.contains(splitedLines[0])){
							doBuiltInFunctionAndDraw(splitedLines[0], i);
						}
						else if(!keyWords.contains(splitedLines[0])){
							performedMacrosLine.add(currentLine+1);
							interpret(splitedLines[0]);
							if(!performedMacrosLine.isEmpty()) performedMacrosLine.pop();
						}
					
				}
			}
			else {
				break;
			}
		}
	}
	
	private void interpretWHILE(ArrayList<String> list, int start, int end, int startLine) throws IOException{ // przeci��ona funkcja interpretuj�ca blok WHILE od danej linii
		String[] splitedLines;
			for(int i = start+1; i<end+1; ++i){
				if(!stepFlag) pauseAndSave(i);
				if(breakFlag){
					break;	
				}
				i = goToLineAndMarkLoop(i, startLine, list);
				
				
				
				String line = list.get(i);
				splitedLines = line.split(" ");
				int currentLine = i;
					if(splitedLines[0].equals("if") || splitedLines[0].equals("while")){
						int begins = 0, ends = 0;
						do{
			
							currentLine++;
							splitedLines = list.get(currentLine).split(" ");
							if(splitedLines[0].equals("begin")) begins++;
							if(splitedLines[0].equals("end")) ends++;
						}while(begins != ends);
						markLoop(list, i, currentLine);
						i=currentLine;
					}
					else if(builtInFunctions.contains(splitedLines[0])){
						doBuiltInFunctionAndDraw(splitedLines[0], i);
					}
					else if(!keyWords.contains(splitedLines[0])){
						
						performedMacrosLine.add(currentLine+1);
						if(fileService.startLinesIsEmpty()) interpret(splitedLines[0]);
						else interpret(splitedLines[0], fileService.pollStartLine());
						if(!performedMacrosLine.isEmpty()) performedMacrosLine.pop();
					}
				
			}
			interpretWHILE(list, start, end);
	}
	
	private void markLoop(ArrayList<String> list, int start, int end) throws IOException{	// funkcja wyznaczaj�ca kt�ry blok jest zapami�tany do wykonania
		String key = list.get(start).split(" ")[0];
		if(key.equals("if")) interpretIF(list, start, end);
		else if(key.equals("while")) interpretWHILE(list, start, end);		
	}
	
	private void markLoop(ArrayList<String> list, int start, int end, int startLine) throws IOException{ // funkcja wyznaczaj�ca kt�ry blok jest zapami�tany do wykonania od danej linii
		String key = list.get(start).split(" ")[0];
		if(key.equals("if")) interpretIF(list, start, end, startLine);
		else if(key.equals("while")) interpretWHILE(list, start, end, startLine);		
	}

	private void pauseAndSave(int i) throws IOException{ //funkcja pauzuj�ca p�tl� i zapisuj�ca plik konfiguracyjny z uwzgl�dnieniem stanu p�tli
		Scanner odczyt = new Scanner(System.in);
		String data = "";
		if(is.ready()){
			try{
				while(is.ready()) is.read();
				
				odczyt = new Scanner(System.in);
				System.out.println("Zapis i przerwanie makra - 1, Zapis i kontynuacja - pozosta�e");
				data = odczyt.nextLine();
				if(Integer.parseInt(data) == 1) breakFlag = true;
				System.out.println("Wpisz nazwe pliku do zapisu");
				//odczyt = new Scanner(System.in);
				data = odczyt.nextLine();
				StringBuilder sb = new StringBuilder();
				for(int j=0; j<performedMacrosLine.size(); j++){
					sb.append(performedMacros.get(j)+ " "+ performedMacrosLine.get(j) + "\n");
				}
				sb.append(performedMacros.peek() + " " + (i+1));
				fileService.saveFile(data, sb.toString());
			}catch(NumberFormatException e){
				System.out.println("Z�y format wprowadzonych danych - kontynuje makro bez zapisu");
			}
			
		}
		if(stepFlag){
			while(true){
				try{
					System.out.println("Wyb�r rozkazu - 1, Wczytanie stanu z pliku - 2, Zapis stanu do pliku - 3 Wy�wietlenie stanu - 4,  Wykonanie jednego kroku - 5,\n"
							+ "Zako�cz makro - 6, Wykonanie pozosta�ej czesci makra - pozosta�e");
					odczyt = new Scanner(System.in);
					data = odczyt.nextLine();
					
					if(Integer.parseInt(data) == 1){
						try{
							String[] funList = new File("functions").list();
							System.out.println("Dost�pne funkcje: ");
							for(String fun : funList){
								System.out.println(fun.replace(".txt", ""));
							}
							System.out.println("oraz wszystkie podstawowe funkcje robota");
						}catch(NullPointerException e){
							e.printStackTrace();
						}
						
						System.out.println("Wpisz rozkaz");
						data = odczyt.nextLine();
						///drawer.draw();
						stepFlag = false;//
						//new Interpreter(robot, fileService, drawer).interpret(data);
						performedMacros.clear();//
						performedMacrosLine.clear();//
						interpret(data);
						breakFlag = true;//
						///drawer.draw();
						///resetBreakFlag();
						if(!drawer.isStepSaving()) drawer.drawToFile();
						break;
					}
					else if(Integer.parseInt(data) == 2){
						try{
							String[] funList = new File("configurationFiles").list();
							System.out.println("Dost�pne pliki konfiguracyjne: ");
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
							stepFlag = false;//
							performedMacros.clear();//
							performedMacrosLine.clear();//
							interpret(fileService.pollFunctionName(), fileService.pollStartLine());
							//new Interpreter(robot, fileService, drawer).interpret(fileService.pollFunctionName(), fileService.pollStartLine());
							///resetBreakFlag();
							breakFlag = true;
							break; //
						}
						
						
					}
					else if(Integer.parseInt(data) == 3){
						/*System.out.println("Wpisz nazwe pliku");
						data = odczyt.nextLine();
						fileService.saveFile(data);*/
						System.out.println("Wpisz nazwe pliku do zapisu");
						//odczyt = new Scanner(System.in);
						data = odczyt.nextLine();
						StringBuilder sb = new StringBuilder();
						for(int j=0; j<performedMacrosLine.size(); j++){
							sb.append(performedMacros.get(j)+ " "+ performedMacrosLine.get(j) + "\n");
						}
						sb.append(performedMacros.peek() + " " + (i+2));
						fileService.saveFile(data, sb.toString());
					}
					else if(Integer.parseInt(data)==4){
						drawer.draw();
					}
					/*else if(Integer.parseInt(data) == 4){
						System.out.println("Wpisz nazwe pliku");
						data = odczyt.nextLine();
						drawer.setStepSaving(data);
					}*/
					else if(Integer.parseInt(data) == 5){
						//stepFlag = false;
						break;
					}
					else if(Integer.parseInt(data) == 6){
						stepFlag = false;
						breakFlag = true;
						break;
					}
					else {
						stepFlag = false;
						break;
					
					}
				}catch(NumberFormatException e){
					System.out.println("Z�y format wprowadania!");
					continue;
				}
				
			}
		}
	}

	private int goToLineAndMarkLoop(int i, int startLine, ArrayList<String> list) throws IOException{ //funkcja wyznaczaj�ca pocz�tek i konie� bloku IF/WHILE
		int currentLine;
		while((i+1) < startLine){
			String line = list.get(i);
			String[] splitedLines = line.split(" ");
			if(splitedLines[0].equals("if") || splitedLines[0].equals("while")){
				int begins = 0, ends = 0;
				currentLine = i;
				do{
					currentLine++;
					splitedLines = list.get(currentLine).split(" ");
					if(splitedLines[0].equals("begin")) begins++;
					if(splitedLines[0].equals("end")) ends++;
				}while(begins != ends);
				if(startLine >= (i+1) && startLine <= (currentLine+1)) markLoop(list, i, currentLine, startLine);
				
				i=currentLine;
				
			}
			else{
				i++;
			}
		}
		return i;
	}

}
