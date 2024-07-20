package project.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import project.Models.Subject;

/**
 * Klasa UpdateSubjectAction implementuje ActionListener do aktualizacji tabeli danych przedmiotów.
 */
public class UpdateSubjectAction implements ActionListener {

	/** Model tabeli dla danych studentów. */
	private DefaultTableModel tableStudentModel;
	
	/** Tabela do wyświetlania danych przedmiotów. */
	private JTable tableData;

	/** Kolumny tabeli. */
	private String[] columns = { "Subject", "Max Grade", "Description" };

	/**
	 * Konstruktor klasy UpdateSubjectAction.
	 *
	 * @param tableData JTable, która ma zostać zaktualizowana.
	 */
	public UpdateSubjectAction(JTable tableData) {
		this.tableStudentModel = new DefaultTableModel(columns, 0) {
			private static final long serialVersionUID = 7775380656143830952L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.tableData = tableData;
	}

	/**
	 * Metoda wywoływana po naciśnięciu przycisku. Aktualizuje dane w tabeli.
	 *
	 * @param e zdarzenie ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		tableStudentModel.setRowCount(0);
		for (Subject subj : Subject.allSubjects.values()) {
			Object[] rowData = { subj.getSubjectName(), subj.getMaxGrade(), subj.getDescription() };
			tableStudentModel.addRow(rowData);
		}
		tableData.setModel(tableStudentModel);

		if (tableData.getRowCount() != 0)
			tableData.setRowSelectionInterval(0, 0);
	}
}
