package pl.edu.agh.kis;

import java.io.IOException;

public interface FileServiceInterface {
	/**
	 * £aduje plik konfiguracyjny o podanej nazwie wraz z ewentualnym stanem pêtli
	 * @param name nazwa pliku konfiguracyjnego
	 */
	void loadFile(String name);
	/**
	 * Zapisuje plik konfiguaryjny
	 * @param name nazwa pliku
	 * @throws IOException rzuca wyj¹tkiem jeœli nie uda siê stworzyæ pliku
	 */
	void saveFile(String name) throws IOException;
	/**
	 * Zapisuje plik konfiguracyjny wraz ze stanem pêtli
	 * @param name nazwa pliku
	 * @param state stan pêtli
	 * @throws IOException rzuca wyj¹tkiem jeœli nie uda siê stworzyæ pliku
	 */
	void saveFile(String name, String state) throws IOException;
	/**
	 * Zwraca informacje czy kolejka linii startowych jest pusta
	 * @return true jeœli pusta
	 */
	boolean startLinesIsEmpty();
	/**
	 * Pobiera linie startow¹ makra
	 * @return numer linii od której zacznie wykonywac makro
	 */
	int pollStartLine();
	/**
	 * Zwraca nazwe makra do wykonywania
	 * @return nazwa makra w postaci String
	 */
	String pollFunctionName();
}
