package pl.edu.agh.kis;

public class Robot implements RobotInterface{
	private final BoardInterface board;
	private int verticalPosition; // pion
	private int horizontalPosition; // poziom
	private int socketCapacity;
	private int socketContent;
	
	private int direction;
	/**
	 * KOstruktor ustawiaj¹cy parametry klasy obiektu klasy Robot
	 * @param board pole potrzebne do sprawdzania mo¿liwych operacji robota na planszy
	 * @param socketCapacity pojemnoœæ kieszeni robota
	 * @param socketContent zawartoœæ kieszeni robota
	 * @param verticalPosition pozycja w wierszu
	 * @param horizontalPosition pozycja w kolumnie 
	 * @param direction kierunek robota
	 */
	public Robot(BoardInterface board, int socketCapacity, int socketContent, int verticalPosition, int horizontalPosition, int direction){
		this.board = board;
		this.socketCapacity = socketCapacity;
		this.socketContent = socketContent;
		this.verticalPosition = verticalPosition;
		this.horizontalPosition = horizontalPosition;
		this.direction = direction;
	}
	public void setRobotParametres(int verticalPosition, int horizontalPosition, int direction, int socketCapacity, int socketContent){
		if(verticalPosition >= 0 && verticalPosition < board.getBoardSize()) this.verticalPosition = verticalPosition;
		if(verticalPosition >= 0 && verticalPosition < board.getBoardSize()) this.horizontalPosition = horizontalPosition;
		if(direction >= 0 && direction < 4) this.direction = direction;
		if(socketCapacity > 0) this.socketCapacity = socketCapacity;
		if(socketContent >= 0) this.socketContent = socketContent;
	}
	
	public int getSocketCapacity(){
		return socketCapacity;
	}
	public int getSocketContent(){
		return socketContent;
	}
	public int getVPosition(){
		return verticalPosition;
	}
	public int getHPosition(){
		return horizontalPosition;
	}
	public int getDirection(){
		return direction;
	}
	public boolean move(){
		if(!isWall()){
			if(direction == 0) verticalPosition--;
			else if(direction == 2) verticalPosition++;
			else if(direction == 1) horizontalPosition--;
			else if(direction == 3) horizontalPosition++;
			return true;
		}
		else {
			System.out.println("can't move");
			return false;
		}
	}
	public boolean turnLeft(){
		if(direction > 3 || direction < 0) return false;
		else if(direction < 3) direction++;
		else direction = 0;
		
		return true;
	}
	public boolean take(){
		if(!isBrick()){
			System.out.println("can't take, no Brick here");
			return false;
		}
		else{
			if(this.getSocketContent() < this.getSocketCapacity()){
				board.changeFieldState(verticalPosition, horizontalPosition, false);
				socketContent++;
				return true;
			}
			else {
				System.out.println("can't take, full socket");
				return false;
			}
		}
	}
	public boolean put(){
			if(this.getSocketContent() > 0){
				board.changeFieldState(verticalPosition, horizontalPosition, true);
				socketContent--;
				return true;
			}
			else {
				System.out.println("can,t put, empty socket");
				return false;
			}
	}
	public boolean isWall(){
		if(direction == 0){ 
			if(verticalPosition == 0){
				return true;
			}
			else if(board.isWall(verticalPosition-1, horizontalPosition)){
				return true;
			}
		}
		else if(direction == 2){
			if(verticalPosition == board.getBoardSize()-1){
				return true;
			}
			else if(board.isWall(verticalPosition+1, horizontalPosition)){
				return true;
			}
		}
		else if(direction == 1){
			if(horizontalPosition == 0){
				return true;
			}
			else if(board.isWall(verticalPosition, horizontalPosition-1)){
				return true;
			}
		}
		else if(direction == 3){
			if(horizontalPosition == board.getBoardSize()-1){
				return true;
			}
			else if(board.isWall(verticalPosition, horizontalPosition+1)){
				return true;
			}
		}
		return false;
	}
	public boolean isBrick(){
		return board.isBrick(verticalPosition, horizontalPosition);
	}
}
