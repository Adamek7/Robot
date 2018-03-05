package pl.edu.agh.kis;

import java.io.IOException;

public interface RuntimeInterface {
	/**
	 * Funkcja rozpoczynaj¹ca pracê œrodowiska wykonawczego
	 * @throws IOException rzuca wyj¹tkiem jeœli nie uda siê otworzyæ jakiegoœ pliku
	 */
	void startRun() throws IOException;
}
