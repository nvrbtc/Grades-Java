package project.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa Student reprezentuje studenta, który może należeć do różnych grup.
 */
public class Student implements Serializable {
	
	private static final long serialVersionUID = 3984255979616339209L;
	
	/** Aktualne ID do przypisywania nowym studentom. */
	private static int CurrentId = 1;
	
	/** Mapa wszystkich studentów. */
	public static HashMap<Integer, Student> allStudents = new HashMap<>();

	/** Imię studenta. */
	private String name;
	
	/** Nazwisko studenta. */
	private String surname;
	
	/** Unikalne ID studenta. */
	private final int id;
	
	/** Lista grup, do których należy student. */
	private List<Group> groups;
	
	/**
	 * Prywatny konstruktor klasy Student.
	 *
	 * @param name imię studenta.
	 * @param surname nazwisko studenta.
	 */
	private Student(String name, String surname) {
		this.name = name;
		this.surname = surname;
		this.id = CurrentId++;
		this.groups = new LinkedList<>();
	}

	/**
	 * Prywatny konstruktor klasy Student.
	 *
	 * @param name imię studenta.
	 * @param surname nazwisko studenta.
	 * @param group grupa, do której należy student.
	 */
	private Student(String name, String surname, Group group) {
		this(name, surname);
		this.groups.add(group);
	}
	
	/**
	 * Tworzy nowego studenta bez przypisanej grupy.
	 *
	 * @param name imię studenta.
	 * @param surname nazwisko studenta.
	 * @return nowo utworzony student.
	 */
	public static Student createStudent(String name, String surname) {
		Student a = new Student(name, surname);
		allStudents.put(a.id, a);
		return a;
	}
	
	/**
	 * Tworzy nowego studenta z przypisaną grupą.
	 *
	 * @param name imię studenta.
	 * @param surname nazwisko studenta.
	 * @param group grupa, do której należy student.
	 * @return nowo utworzony student.
	 */
	public static Student createStudent(String name, String surname, Group group) {
		Student a = new Student(name, surname, group);
		allStudents.put(a.id, a);
		return a;
	}
	
	/**
	 * Dodaje grupę do listy grup studenta.
	 *
	 * @param group grupa do dodania.
	 */
	public void addGroup(Group group) {
		this.groups.add(group);
	}
	
	/**
	 * Usuwa grupę z listy grup studenta.
	 *
	 * @param group grupa do usunięcia.
	 */
	public void removeGroup(Group group) {
		groups.remove(group);
	}
	
	/**
	 * Aktualizuje imię studenta.
	 *
	 * @param name nowe imię studenta.
	 */
	public void updateName(String name) {
		setName(name);
	}
	
	/**
	 * Aktualizuje nazwisko studenta.
	 *
	 * @param surname nowe nazwisko studenta.
	 */
	public void updateSurname(String surname) {
		setSurname(surname);
	}
	
	/**
	 * Zwraca reprezentację tekstową studenta.
	 *
	 * @return imię i nazwisko studenta.
	 */
	@Override
	public String toString() {
		return name + " " + surname;
	}
	
	/**
	 * Zwraca listę grup, do których należy student.
	 *
	 * @return lista grup studenta.
	 */
	public List<Group> getGroup() {
		return this.groups;
	}
	
	/**
	 * Zwraca imię studenta.
	 *
	 * @return imię studenta.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Zwraca nazwisko studenta.
	 *
	 * @return nazwisko studenta.
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Zwraca ID studenta.
	 *
	 * @return ID studenta.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Zwraca aktualne ID używane do tworzenia nowych studentów.
	 *
	 * @return aktualne ID.
	 */
	public static int getCurrentId() {
		return CurrentId;
	}
	
	/**
	 * Ustawia aktualne ID używane do tworzenia nowych studentów.
	 *
	 * @param id nowe aktualne ID.
	 */
	public static void setCurrentId(int id) {
		CurrentId = id;
	}
	
	/**
	 * Ustawia imię studenta.
	 *
	 * @param name nowe imię studenta.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Ustawia nazwisko studenta.
	 *
	 * @param surname nowe nazwisko studenta.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
}
