package project.Models;

import java.io.Serializable;

/**
 * Enum Specialization reprezentuje specjalizacje dostępne dla studentów.
 */
public enum Specialization implements Serializable {

	/** Specjalizacja Informatyka. */
	ComputerScience("Computer Science", "CS"),
	
	/** Specjalizacja Fizyka. */
	Physics("Physics", "PH"),
	
	/** Specjalizacja Analiza Złożoności. */
	ComplexAnalys("Complex Analys", "CA");
	
	private static final long serialVersionUID = 1L;

	/** Pełna nazwa specjalizacji. */
	private String fullName;
	
	/** Kod grupy specjalizacji. */
	private String groupCode;
	
	/**
	 * Zwraca pełną nazwę specjalizacji.
	 *
	 * @return pełna nazwa specjalizacji.
	 */
	public String getSpec() {
		return this.fullName;
	}
	
	/**
	 * Zwraca kod grupy specjalizacji.
	 *
	 * @return kod grupy specjalizacji.
	 */
	public String getCode() {
		return this.groupCode;
	}
	
	/**
	 * Prywatny konstruktor dla wyliczenia Specialization.
	 *
	 * @param spec pełna nazwa specjalizacji.
	 * @param code kod grupy specjalizacji.
	 */
	private Specialization(String spec, String code) {
		this.fullName = spec;
		this.groupCode = code;
	}
}
