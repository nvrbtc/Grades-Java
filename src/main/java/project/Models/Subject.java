package project.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Klasa Subject reprezentuje przedmiot, który może być przypisany do studentów wraz z ocenami.
 */
public class Subject implements Serializable {
	
	private static final long serialVersionUID = 663193035681989857L;

	/** Mapa wszystkich przedmiotów. */
	public static HashMap<String, Subject> allSubjects = new HashMap<>();

	/** Nazwa przedmiotu. */
	private String subjectName;
	
	/** Maksymalna możliwa ocena dla przedmiotu. */
	private int maxGrade;
	
	/** Opis przedmiotu. */
	private String description;
	
	/** Mapa studentów i ich ocen dla tego przedmiotu. */
	public HashMap<Student, Integer> grades;

	/**
	 * Prywatny konstruktor klasy Subject.
	 *
	 * @param subjectName nazwa przedmiotu.
	 * @param maxGrade maksymalna ocena dla przedmiotu.
	 */
	private Subject(String subjectName, int maxGrade) {
		this.subjectName = subjectName;
		this.maxGrade = maxGrade;
		this.grades = new HashMap<>();
	}

	/**
	 * Prywatny konstruktor klasy Subject.
	 *
	 * @param subjectName nazwa przedmiotu.
	 * @param maxGrade maksymalna ocena dla przedmiotu.
	 * @param description opis przedmiotu.
	 */
	private Subject(String subjectName, int maxGrade, String description) {
		this(subjectName, maxGrade);
		this.description = description;
	}

	/**
	 * Tworzy nowy przedmiot bez opisu.
	 *
	 * @param subjectName nazwa przedmiotu.
	 * @param maxGrade maksymalna ocena dla przedmiotu.
	 * @return nowo utworzony przedmiot.
	 */
	public static Subject createSubject(String subjectName, int maxGrade) {
		Subject temp = new Subject(subjectName, maxGrade);
		allSubjects.put(subjectName, temp);
		return temp;
	}

	/**
	 * Tworzy nowy przedmiot z opisem.
	 *
	 * @param subjectName nazwa przedmiotu.
	 * @param maxGrade maksymalna ocena dla przedmiotu.
	 * @param description opis przedmiotu.
	 * @return nowo utworzony przedmiot.
	 */
	public static Subject createSubject(String subjectName, int maxGrade, String description) {
		Subject temp = new Subject(subjectName, maxGrade, description);
		allSubjects.put(subjectName, temp);
		return temp;
	}

	/**
	 * Dodaje punkty studentowi.
	 *
	 * @param student student, któremu mają zostać dodane punkty.
	 * @param points liczba punktów do dodania.
	 */
	public void addPoints(Student student, Integer points) {
		if (grades.containsKey(student)) {
			grades.compute(student, (wybranyStudent, currentGrade) -> points + currentGrade);
			if (grades.get(student) > this.getMaxGrade()) {
				grades.put(student, this.maxGrade);
				return;
			}
			if (grades.get(student) < 0) {
				grades.put(student, 0);
				return;
			}
		}
	}

	/**
	 * Dodaje studenta do przedmiotu z początkową oceną 0.
	 *
	 * @param student student do dodania.
	 */
	public void addStudent(Student student) {
		if (!grades.containsKey(student))
			grades.put(student, 0);
	}

	/**
	 * Zwraca zbiór studentów zapisanych na przedmiot.
	 *
	 * @return zbiór studentów.
	 */
	public Set<Student> getStudents() {
		return grades.keySet();
	}

	/**
	 * Usuwa przedmiot z mapy wszystkich przedmiotów.
	 */
	public void deleteSubject() {
		allSubjects.remove(this.subjectName, this);
	}

	/**
	 * Usuwa studenta z przedmiotu.
	 *
	 * @param student student do usunięcia.
	 */
	public void removeStudent(Student student) {
		grades.remove(student);
	}

	/**
	 * Zwraca nazwę przedmiotu.
	 *
	 * @return nazwa przedmiotu.
	 */
	@Override
	public String toString() {
		return subjectName;
	}

	/**
	 * Sprawdza, czy dwa przedmioty są równe.
	 *
	 * @param obj obiekt do porównania.
	 * @return true, jeśli przedmioty są równe, w przeciwnym razie false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Subject subject = (Subject) obj;
		return subjectName.equals(subject.subjectName);
	}

	/**
	 * Zwraca nazwę przedmiotu.
	 *
	 * @return nazwa przedmiotu.
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Zwraca maksymalną ocenę dla przedmiotu.
	 *
	 * @return maksymalna ocena.
	 */
	public int getMaxGrade() {
		return maxGrade;
	}

	/**
	 * Zwraca opis przedmiotu.
	 *
	 * @return opis przedmiotu.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Zwraca punkty zdobyte przez studenta.
	 *
	 * @param student student, którego punkty są zwracane.
	 * @return liczba punktów.
	 */
	public Integer getPoints(Student student) {
		return grades.get(student);
	}

	/**
	 * Ustawia nazwę przedmiotu.
	 *
	 * @param subjectName nowa nazwa przedmiotu.
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Ustawia maksymalną ocenę dla przedmiotu.
	 *
	 * @param maxGrade nowa maksymalna ocena.
	 */
	public void setMaxGrade(int maxGrade) {
		this.maxGrade = maxGrade;
	}

	/**
	 * Ustawia opis przedmiotu.
	 *
	 * @param description nowy opis przedmiotu.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
