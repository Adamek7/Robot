package pl.edu.agh.kis;

public interface BoardInterface {
	/**
	 * Ustawia rozmiar planszy
	 * @param size rozmiar kt�ry zostanie ustawiony
	 */
	void setBoardSize(int size);
	/**
	 * Zwraca rozmiar planszy
	 * @return rozmiar planszy
	 */
	int getBoardSize();
	/**
	 * Zwi�ksza lub zmniejsza ilo�c klock�w na danym polu
	 * @param row wiersz planszy
	 * @param column kolumna planszy
	 * @param flag flaga ustalaj�ca czy podno�cimy czy k�adziemy klocek
	 */
	void changeFieldState(int row, int column, boolean flag);
	/**
	 * Okre�la status pola, czy b�dzie na nim �ciana czy klocek lub nic
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
	 * Zwraca informacje czy przed nami jest �ciana
	 * @param row wiersz w kt�rym znajduje sie robot
	 * @param column kolumna w kt�rej znajduje sie robot
	 * @return true je�li przed robotem jest sciana
	 */
	boolean isWall(int row, int column);
	/**
	 * Zwraca informacje o ilo�ci klock�w na danym polu
	 * @param row wiersz planszy
	 * @param column kolumna planszy
	 * @return ilo�� klock�w na polu
	 */
	int getNumberOfBricks(int row, int column);

}
