package project.Actions;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;

import project.Models.Group;
import project.Models.Student;
import project.Models.Subject;

/**
 * Klasa OnOpeningGui obsługuje zdarzenia związane z otwieraniem okna GUI i ładowaniem danych z pliku.
 */
public class OnOpeningGui extends WindowAdapter {

	/** Strumień wejściowy pliku. */
	private FileInputStream fileIn = null;

	/** Strumień wejściowy obiektu. */
	private ObjectInputStream in = null;

	/** Tabela danych. */
	private JTable tableData;

	/** Menu grup. */
	private JMenu menuGroups;

	/** Obiekt studenta. */
	private Student student;

	/** Obiekt grupy. */
	private Group group;

	/** Obiekt przedmiotu. */
	private Subject subject;

	/** Element menu dla wszystkich studentów. */
	private JMenuItem allStudentsClick;

	/**
	 * Konstruktor klasy OnOpeningGui.
	 *
	 * @param tableData JTable, która ma być zaktualizowana.
	 * @param menuGroups JMenu do dodawania grup.
	 * @param clickAfter JMenuItem, który zostanie kliknięty po załadowaniu danych.
	 */
	public OnOpeningGui(JTable tableData, JMenu menuGroups, JMenuItem clickAfter) {
		super();
		this.menuGroups = menuGroups;
		this.tableData = tableData;
		this.allStudentsClick = clickAfter;
	}

	/**
	 * Metoda wywoływana po otwarciu okna. Ładuje dane z pliku "Data.ser".
	 *
	 * @param e zdarzenie WindowEvent
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		try {
			fileIn = new FileInputStream("Data.ser");
			in = new ObjectInputStream(fileIn);
			while (true) {
				try {
					Object data = in.readObject();
					switch (data.getClass().getName()) {
					case "project.Student":
						student = (Student) data;
						Student.allStudents.put(student.getId(), student);
						break;
					case "project.Group":
						group = (Group) data;
						Group.codeToGroup.put(group.getGroupCode(), group);
						menuGroups.add(group.menuItem);
						group.menuItem.addActionListener(new GroupMenuItemListener(group, tableData));
						break;
					case "project.Subject":
						subject = (Subject) data;
						Subject.allSubjects.put(subject.getSubjectName(), subject);
						break;
					default:
						Student.setCurrentId((int) data);
						break;
					}
				} catch (EOFException ex) {
					break;
				}
			}
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		allStudentsClick.doClick();
	}
}
