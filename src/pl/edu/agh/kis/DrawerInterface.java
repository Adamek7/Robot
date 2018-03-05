package pl.edu.agh.kis;

public interface DrawerInterface {
	/**
	 * Zapisuje aktualny wygl¹d planszy do pliku podanego przez u¿ytkownika
	 */
	void drawToFile();
	/**
	 * Rysuje na konsoli aktualny wygl¹d planszy
	 */
	void draw();
	/**
	 * Ustawia lub wy³acza zapis krokowów do pliku
	 * @param fileName nazwa pliku do którego bêdziemy zapisywaæ
	 */
	void setStepSaving(String fileName);
	/**
	 * Zwraca informacje czy ustawiony jest zapis krokowy do pliku 
	 * @return true jeœli zapis krokowy do pliku w³¹czony
	 */
	boolean isStepSaving();
}
