package pl.edu.agh.kis;

public class Board implements BoardInterface{
	private int size;
	private int[][] boardArrayOfBricks;
	private boolean[][] boardArrayOfWalls;
	/**
	 * Konstruktor ustawiaj¹cy parametry planszy
	 * @param size parametr okreœlaj¹cy rozmiar planszy
	 */
	public Board(int size){
		if(size > 0) {
			this.size = size;
			boardArrayOfBricks = new int[size][size];
			boardArrayOfWalls = new boolean[size][size];
		}
		else {
			size = 10;
			this.size = size;
			boardArrayOfBricks = new int[size][size];
			boardArrayOfWalls = new boolean[size][size];
		}
		
	}
	public void setBoardSize(int size){
		if(size > 0) {
			this.size = size;
			boardArrayOfBricks = new int[size][size];
			boardArrayOfWalls = new boolean[size][size];
		}
		else {
			size = 10;
			this.size = size;
			boardArrayOfBricks = new int[size][size];
			boardArrayOfWalls = new boolean[size][size];
		}
	}
	public int getBoardSize(){
		return size;
	}
	public int getNumberOfBricks(int row, int column){
			return boardArrayOfBricks[row][column];
		
	}
	
	public void changeFieldState(int row, int column, boolean flag){
		if(flag) boardArrayOfBricks[row][column]++;
		else boardArrayOfBricks[row][column]--;
	}
	public void setFieldState(int row, int column, int state){
		if(row >= 0 && column >= 0){
			if(state == -1){
				boardArrayOfWalls[row][column] = true;
			}
			else if(state > 0){
				boardArrayOfBricks[row][column] = state;
			}
			else{
				boardArrayOfBricks[row][column] = 0;
				boardArrayOfWalls[row][column] = false;
			}
		}
	}
	
	public boolean isBrick(int row, int column){
		if(boardArrayOfBricks[row][column] > 0) return true;
		else return false;
	}
	public boolean isWall(int row, int column){
		 return boardArrayOfWalls[row][column];
	}
	
	
	
}
