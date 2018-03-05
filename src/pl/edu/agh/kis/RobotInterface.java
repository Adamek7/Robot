package pl.edu.agh.kis;

public interface RobotInterface {
	/**
	 * Ustawia parametry robota
	 * @param verticalPosition wiersz w kt�rym znajduje si� robot
	 * @param horizontalPosition klumna w kt�rej znajduje si� robot
	 * @param direction kierunek w kt�rym porusza si� robot
	 * @param socketCapacity pojemno�� kieszeni robota
	 * @param socketContent zawarto�� kieszeni robota
	 */
	void setRobotParametres(int verticalPosition, int horizontalPosition, int direction, int socketCapacity, int socketContent);
	/**
	 * Zwraca informacje i pojemno�ci kieszeni robota
	 * @return pojemno�c kieszeni robota
	 */
	int getSocketCapacity();
	/**
	 * Zwraca informacje o zawarto�ci kieszeni robota
	 * @return ilo�� klock�w kt�re ma w kieszeni robot
	 */
	int getSocketContent();
	/**
	 * Zwraca informacje o wierszu w kt�rym jest robot
	 * @return numer wiersza w kt�rym jest robot
	 */
	int getVPosition();
	/**
	 * Zwraca informacje o kolumnie w kt�rej jest robot
	 * @return numer kolumny w kt�rej jest robot
	 */
	int getHPosition();
	/**
	 * Zwraca kierunek, w kt�rym porusza si� robot
	 * @return kierunek robota
	 */
	int getDirection();
	/**
	 * Przesuwa robota do przodu o jedno pole w kierunku w kt�rym jest zwr�cony jest robot
	 * @return true je�li uda�o si� przesun�� robota
	 */
	boolean move();
	/**
	 * Zmienia kierunek robota o jeden w lewo
	 * @return true je�li uda�o zmieni� sie kierunek
	 */
	boolean turnLeft();
	/**
	 * Podnosi klocek z pola i wk�ada do kieszni je�li mo�liwe
	 * @return true je�li uda�o si� podnie��
	 */
	boolean take();
	/**
	 * Wyjmuje klocek z kieszeni i k�adzie na polu je�li mo�liwe
	 * @return true je�li si� uda�o
	 */
	boolean put();
	/**
	 * Sprawdza czy przed robotem jest sciana
	 * @return true je�li jest sciana
	 */
	boolean isWall();
	/**
	 * Sprawdza czy na danym polu s� klocki
	 * @return true jesli s� klocki na polu na kt�rym znajduje sie robot
	 */
	boolean isBrick();
}
