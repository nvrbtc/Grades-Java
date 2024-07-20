package project.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JTable;

import project.Models.Group;
import project.Models.Student;
import project.Models.Subject;

/** Klasa obsługująca akcję usuwania przedmiotu. */
public class DeleteSubjectAction implements ActionListener {
	/** Referencja do tabeli, w której znajdują się dane przedmiotów. */
	private JTable tableData;
	/** Referencja do pozycji menu "List all". */
	private JMenuItem menuItemAllSubjects;

	/**
	 * Konstruktor klasy DeleteSubjectAction.
	 * 
	 * @param tableData           Referencja do tabeli, w której znajdują się dane
	 *                            przedmiotów.
	 * @param menuItemAllSubjects Referencja do pozycji menu "Wszystkie przedmioty".
	 */
	public DeleteSubjectAction(JTable tableData, JMenuItem menuItemAllSubjects) {
		this.menuItemAllSubjects = menuItemAllSubjects;
		this.tableData = tableData;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int selectedRow = tableData.getSelectedRow();
		if (selectedRow != -1) {
			try {
				String name = (String) tableData.getValueAt(selectedRow, 0);
				Subject selectedSubj = Subject.allSubjects.get(name);
				for (Student stud : selectedSubj.grades.keySet()) {
					for (Group g : stud.getGroup()) {
						g.subjects.remove(selectedSubj);
					}
				}

				Subject.allSubjects.remove(selectedSubj.getSubjectName());
				selectedSubj = null;
				menuItemAllSubjects.doClick();
			} catch (Exception ex) {}
		}
	}
}
