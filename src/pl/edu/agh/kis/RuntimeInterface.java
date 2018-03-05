package pl.edu.agh.kis;

import java.io.IOException;

public interface RuntimeInterface {
	/**
	 * Funkcja rozpoczynaj�ca prac� �rodowiska wykonawczego
	 * @throws IOException rzuca wyj�tkiem je�li nie uda si� otworzy� jakiego� pliku
	 */
	void startRun() throws IOException;
}
