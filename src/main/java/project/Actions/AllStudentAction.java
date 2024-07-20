package project.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import project.Models.Student;

/** Klasa obsługująca akcję wyświetlenia wszystkich studentów w tabeli. */

public class AllStudentAction implements ActionListener {
	/** Model danych tabeli dla studentów. */
	private DefaultTableModel tableStudentModel;
	/** Referencja do tabeli, której dane są wyświetlane. */
	private JTable tableData;
	/** Kolumny tabeli. */
	private String[] columns = { "ID", "Name", "Surname" };

	/**
	 * Konstruktor klasy AllStudentAction.
	 * 
	 * @param tableData Referencja do tabeli, której dane są wyświetlane.
	 */
	public AllStudentAction(JTable tableData) {
		this.tableStudentModel = new DefaultTableModel(columns, 0) {
			private static final long serialVersionUID = -7054313241574389823L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.tableData = tableData;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tableStudentModel.setRowCount(0);
		for (Student student : Student.allStudents.values()) {
			Object[] rowData = { student.getId(), student.getName(), student.getSurname() };
			tableStudentModel.addRow(rowData);
		}
		tableData.setModel(tableStudentModel);
		if (tableData.getRowCount() != 0)
			tableData.setRowSelectionInterval(0, 0);
	}
}
