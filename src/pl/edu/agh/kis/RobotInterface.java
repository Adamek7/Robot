package pl.edu.agh.kis;

public interface RobotInterface {
	/**
	 * Ustawia parametry robota
	 * @param verticalPosition wiersz w którym znajduje siê robot
	 * @param horizontalPosition klumna w której znajduje siê robot
	 * @param direction kierunek w którym porusza siê robot
	 * @param socketCapacity pojemnoœæ kieszeni robota
	 * @param socketContent zawartoœæ kieszeni robota
	 */
	void setRobotParametres(int verticalPosition, int horizontalPosition, int direction, int socketCapacity, int socketContent);
	/**
	 * Zwraca informacje i pojemnoœci kieszeni robota
	 * @return pojemnoœc kieszeni robota
	 */
	int getSocketCapacity();
	/**
	 * Zwraca informacje o zawartoœci kieszeni robota
	 * @return iloœæ klocków które ma w kieszeni robot
	 */
	int getSocketContent();
	/**
	 * Zwraca informacje o wierszu w którym jest robot
	 * @return numer wiersza w którym jest robot
	 */
	int getVPosition();
	/**
	 * Zwraca informacje o kolumnie w której jest robot
	 * @return numer kolumny w której jest robot
	 */
	int getHPosition();
	/**
	 * Zwraca kierunek, w którym porusza siê robot
	 * @return kierunek robota
	 */
	int getDirection();
	/**
	 * Przesuwa robota do przodu o jedno pole w kierunku w którym jest zwrócony jest robot
	 * @return true jeœli uda³o siê przesun¹æ robota
	 */
	boolean move();
	/**
	 * Zmienia kierunek robota o jeden w lewo
	 * @return true jeœli uda³o zmieniæ sie kierunek
	 */
	boolean turnLeft();
	/**
	 * Podnosi klocek z pola i wk³ada do kieszni jeœli mo¿liwe
	 * @return true jeœli uda³o siê podnieœæ
	 */
	boolean take();
	/**
	 * Wyjmuje klocek z kieszeni i k³adzie na polu jeœli mo¿liwe
	 * @return true jeœli siê uda³o
	 */
	boolean put();
	/**
	 * Sprawdza czy przed robotem jest sciana
	 * @return true jeœli jest sciana
	 */
	boolean isWall();
	/**
	 * Sprawdza czy na danym polu s¹ klocki
	 * @return true jesli s¹ klocki na polu na którym znajduje sie robot
	 */
	boolean isBrick();
}
