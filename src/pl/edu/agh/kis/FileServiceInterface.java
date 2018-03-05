package pl.edu.agh.kis;

import java.io.IOException;

public interface FileServiceInterface {
	/**
	 * �aduje plik konfiguracyjny o podanej nazwie wraz z ewentualnym stanem p�tli
	 * @param name nazwa pliku konfiguracyjnego
	 */
	void loadFile(String name);
	/**
	 * Zapisuje plik konfiguaryjny
	 * @param name nazwa pliku
	 * @throws IOException rzuca wyj�tkiem je�li nie uda si� stworzy� pliku
	 */
	void saveFile(String name) throws IOException;
	/**
	 * Zapisuje plik konfiguracyjny wraz ze stanem p�tli
	 * @param name nazwa pliku
	 * @param state stan p�tli
	 * @throws IOException rzuca wyj�tkiem je�li nie uda si� stworzy� pliku
	 */
	void saveFile(String name, String state) throws IOException;
	/**
	 * Zwraca informacje czy kolejka linii startowych jest pusta
	 * @return true je�li pusta
	 */
	boolean startLinesIsEmpty();
	/**
	 * Pobiera linie startow� makra
	 * @return numer linii od kt�rej zacznie wykonywac makro
	 */
	int pollStartLine();
	/**
	 * Zwraca nazwe makra do wykonywania
	 * @return nazwa makra w postaci String
	 */
	String pollFunctionName();
}
