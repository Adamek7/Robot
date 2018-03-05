package pl.edu.agh.kis;

import java.io.IOException;

public interface InterpreterInterface {
	/**
	 * Interpretuje makro o podanej nazwie
	 * @param fun nazwa makra do interpretacji
	 * @throws IOException rzuca wyj¹tek jeœli makro nie istnieje
	 */
	void interpret(String fun) throws IOException;
	/**
	 * Interpretuje makro o podanej nazwie zaczynaj¹c od konkretnej linii
	 * @param fun nazwa makra
	 * @param startLine linia od której zaczyna
	 * @throws IOException rzuca wyj¹tek jeœli makro nie istnieje
	 */
	void interpret(String fun, int startLine) throws IOException;
	/**
	 * Resetuje flagê przerywaj¹c¹ wykonywanie makra
	 */
	void resetBreakFlag();
	/***
	 * Metoda która sprawia, ¿e wykonywany jest tylko jeden krok makra
	 */
	void setStepFlag();
}
