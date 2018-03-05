package pl.edu.agh.kis;

public interface DrawerInterface {
	/**
	 * Zapisuje aktualny wygl�d planszy do pliku podanego przez u�ytkownika
	 */
	void drawToFile();
	/**
	 * Rysuje na konsoli aktualny wygl�d planszy
	 */
	void draw();
	/**
	 * Ustawia lub wy�acza zapis krokow�w do pliku
	 * @param fileName nazwa pliku do kt�rego b�dziemy zapisywa�
	 */
	void setStepSaving(String fileName);
	/**
	 * Zwraca informacje czy ustawiony jest zapis krokowy do pliku 
	 * @return true je�li zapis krokowy do pliku w��czony
	 */
	boolean isStepSaving();
}
