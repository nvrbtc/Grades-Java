package project.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import project.Models.Subject;
import project.Views.AddSubjectToGroup;

/** Klasa obsługująca akcję dodawania przedmiotu do grupy. */
public class AddSubjectToGroupAction implements ActionListener {

	/** Referencja do tabeli danych, w której wybierany jest przedmiot. */
	private JTable tableData;
	/** Referencja do wybranego przedmiotu. */
	private Subject selectedSubject;

	/**
	 * Konstruktor klasy AddSubjectToGroupAction.
	 * 
	 * @param tableData Referencja do tabeli danych, w której wybierany jest
	 *                  przedmiot.
	 */
	public AddSubjectToGroupAction(JTable tableData) {
		super();
		this.tableData = tableData;
		this.selectedSubject = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int selectedRow = tableData.getSelectedRow();
		if (selectedRow != -1) {
			try {
				String name = (String) tableData.getValueAt(selectedRow, 0);
				selectedSubject = Subject.allSubjects.get(name);
				Thread t = new Thread(new AddSubjectToGroup(selectedSubject));
				t.start();

			} catch (Exception ex) {
				JOptionPane.showInternalMessageDialog(null, "Went wrong", "Invalid input", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
