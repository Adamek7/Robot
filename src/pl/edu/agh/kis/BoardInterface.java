package pl.edu.agh.kis;

public interface BoardInterface {
	/**
	 * Ustawia rozmiar planszy
	 * @param size rozmiar który zostanie ustawiony
	 */
	void setBoardSize(int size);
	/**
	 * Zwraca rozmiar planszy
	 * @return rozmiar planszy
	 */
	int getBoardSize();
	/**
	 * Zwiêksza lub zmniejsza iloœc klocków na danym polu
	 * @param row wiersz planszy
	 * @param column kolumna planszy
	 * @param flag flaga ustalaj¹ca czy podnoœcimy czy k³adziemy klocek
	 */
	void changeFieldState(int row, int column, boolean flag);
	/**
	 * Okreœla status pola, czy bêdzie na nim œciana czy klocek lub nic
	 * @param row wiersz planszy
	 * @param cloumn kolumna planszy
	 * @param state status jaki ustawiamy
	 */
	void setFieldState(int row, int cloumn, int state);
	/**
	 * Zwraca informacje czy na danym polu jest klocek
	 * @param row wiersz planszy
	 * @param column kolumna planszy
	 * @return true jesli jest klocek
	 */
	boolean isBrick(int row, int column);
	/**
	 * Zwraca informacje czy przed nami jest œciana
	 * @param row wiersz w którym znajduje sie robot
	 * @param column kolumna w której znajduje sie robot
	 * @return true jeœli przed robotem jest sciana
	 */
	boolean isWall(int row, int column);
	/**
	 * Zwraca informacje o iloœci klocków na danym polu
	 * @param row wiersz planszy
	 * @param column kolumna planszy
	 * @return iloœæ klocków na polu
	 */
	int getNumberOfBricks(int row, int column);

}
