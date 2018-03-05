package pl.edu.agh.kis;

import java.io.IOException;

public interface InterpreterInterface {
	/**
	 * Interpretuje makro o podanej nazwie
	 * @param fun nazwa makra do interpretacji
	 * @throws IOException rzuca wyj�tek je�li makro nie istnieje
	 */
	void interpret(String fun) throws IOException;
	/**
	 * Interpretuje makro o podanej nazwie zaczynaj�c od konkretnej linii
	 * @param fun nazwa makra
	 * @param startLine linia od kt�rej zaczyna
	 * @throws IOException rzuca wyj�tek je�li makro nie istnieje
	 */
	void interpret(String fun, int startLine) throws IOException;
	/**
	 * Resetuje flag� przerywaj�c� wykonywanie makra
	 */
	void resetBreakFlag();
	/***
	 * Metoda kt�ra sprawia, �e wykonywany jest tylko jeden krok makra
	 */
	void setStepFlag();
}
