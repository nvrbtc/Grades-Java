package project.Models;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JMenuItem;

/**
 * Reprezentuje grupę studentów w systemie.
 */
public class Group implements Serializable{
	
	/** Serial version UID dla serializacji */
	private static final long serialVersionUID = -1310512707364936431L;

	/** Mapuje kod grupy na obiekt grupy. */
	public static HashMap<String, Group> codeToGroup = new HashMap<String, Group>();
	
	/** Kod grupy. */
	private String groupCode;
	
	/** Specjalizacja grupy. */
	private String specialization;
	
	/** Opis grupy. */
	private String description;

	/** MenuItem związany z grupą. */
	public JMenuItem menuItem ;
	
	/** Zbiór studentów należących do grupy. */
	public Set<Student> students;
	
	/** Zbiór przedmiotów przypisanych do grupy. */
	public Set<Subject> subjects;

	/** Konstruktor domyślny. */
	public Group () {};
	
	/**
	 * Konstruktor tworzący grupę na podstawie podanego kodu i specjalizacji.
	 *
	 * @param groupCode       Kod grupy.
	 * @param specialization  Specjalizacja grupy.
	 */
	private Group(String groupCode, String specialization) {
		this.groupCode = groupCode;
		this.specialization = specialization;
		this.description = "";
		this.students = new HashSet<Student>();
		this.subjects = new HashSet<Subject>();
		this.menuItem = new JMenuItem(this.getGroupCode());
	}

	/**
	 * Dodaje przedmiot do grupy.
	 *
	 * @param subject  Dodawany przedmiot.
	 */
	public void addSubject(Subject subject) {
		subjects.add(subject);
		for (Student stud : students)
			subject.addStudent(stud);
	}

	/**
	 * Usuwa przedmiot z grupy.
	 *
	 * @param subject  Usuwany przedmiot.
	 */
	public void deleteSubject(Subject subject) {
		this.subjects.remove(subject);
	}

	/**
	 * Konstruktor tworzący grupę na podstawie podanego kodu, specjalizacji i opisu.
	 *
	 * @param groupCode       Kod grupy.
	 * @param specialization  Specjalizacja grupy.
	 * @param description     Opis grupy.
	 */
	private Group(String groupCode, String specialization, String description) {
		this(groupCode, specialization);
		this.description = description;
	}

	/**
	 * Tworzy nową grupę na podstawie podanego kodu i specjalizacji.
	 *
	 * @param groupCode       Kod grupy.
	 * @param specialization  Specjalizacja grupy.
	 * @return                Nowo utworzona grupa.
	 */
	public static Group createGroup(String groupCode, String specialization) {
		if (Group.codeToGroup.keySet().contains(groupCode))
			return null;
		Group temp = new Group(groupCode, specialization);
		codeToGroup.put(groupCode, temp);
		return temp;
	}

	/**
	 * Tworzy nową grupę na podstawie podanego kodu, specjalizacji i opisu.
	 *
	 * @param groupCode       Kod grupy.
	 * @param specialization  Specjalizacja grupy.
	 * @param description     Opis grupy.
	 * @return                Nowo utworzona grupa.
	 */
	public static Group createGroup(String groupCode, String specialization, String description) {
		if (Group.codeToGroup.keySet().contains(groupCode))
			return null;
		Group temp = new Group(groupCode, specialization, description);
		codeToGroup.put(groupCode, temp);
		return temp;
	}

	/**
	 * Usuwa studenta z grupy.
	 *
	 * @param stud  Usuwany student.
	 */
	public void removeStudent(Student stud) {
		for (Subject subject : this.subjects) {
			subject.removeStudent(stud);
		}
		this.students.remove(stud);
		stud.removeGroup(this);
	}
	
	/**
	 * Usuwa studenta z grupy, zachowując informacje o grupie w obiekcie studenta.
	 *
	 * @param stud  Usuwany student.
	 */
	public void removeStudentChangeGroup(Student stud) {
		this.students.remove(stud);
		stud.removeGroup(this);
	}
	
	/**
	 * Dodaje studenta do grupy.
	 *
	 * @param student  Dodawany student.
	 * @return         True, jeśli dodanie powiodło się, w przeciwnym razie false.
	 */
	public boolean addStudent(Student student) {
		student.addGroup(this);
		for (Subject subject : subjects) {
			subject.addStudent(student);
		}
		return students.add(student);
	}

	/**
	 * Zwraca kod grupy.
	 *
	 * @return  Kod grupy.
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * Zwraca specjalizację grupy.
	 *
	 * @return  Specjalizacja grupy.
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * Zwraca opis grupy.
	 *
	 * @return  Opis grupy.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Zwraca zbiór studentów w grupie.
	 *
	 * @return  Zbiór studentów w grupie.
	 */
	public Set<Student> getStudents() {
		return students;
	}

	/**
	 * Zwraca zbiór przedmiotów przypisanych do grupy.
	 *
	 * @return  Zbiór przedmiotów przypisanych do grupy.
	 */
	public Set<Subject> getSubjects() {
		return subjects;
	}

	/**
	 * Przedstawienie grupy jako string (kod grupy).
	 *
	 * @return  Kod grupy w formie stringa.
	 */
	@Override
	public String toString() {
		return this.groupCode;
	}

	/**
	 * Zwraca mapę mapującą kody grup na obiekty grup.
	 *
	 * @return  Mapa mapująca kody grup na obiekty grup.
	 */
	public static HashMap<String, Group> getCodeToGroup() {
		return codeToGroup;
	}

	/**
	 * Ustawia mapę mapującą kody grup na obiekty grup.
	 *
	 * @param codeToGroup  Mapa mapująca kody grup na obiekty grup.
	 */
	public static void setCodeToGroup(HashMap<String, Group> codeToGroup) {
		Group.codeToGroup = codeToGroup;
	}

	/**
	 * Ustawia kod grupy.
	 *
	 * @param groupCode  Nowy kod grupy.
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * Ustawia specjalizację grupy.
	 *
	 * @param specialization  Nowa specjalizacja grupy.
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	/**
	 * Ustawia opis grupy.
	 *
	 * @param description  Nowy opis grupy.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
