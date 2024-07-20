package project.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import project.Models.Group;
import project.Models.Student;

/**
 * Klasa GroupMenuItemListener implementuje ActionListener dla elementów menu grupy w interfejsie użytkownika.
 */
public class GroupMenuItemListener implements ActionListener {
	
	/** Grupa, dla której definiowany jest słuchacz menu. */
	private Group group;
	
	/** Tabela, która ma zostać zaktualizowana. */
	private JTable tableData;
	
	/** Model tabeli dla studentów w grupie. */
	private DefaultTableModel tableStudentModel;
	
	/** Nazwy kolumn w modelu tabeli. */
	private String[] columns = { "ID", "Group", "Name", "Surname" };

	/**
	 * Konstruktor klasy GroupMenuItemListener.
	 * 
	 * @param group     Obiekt grupy, dla którego definiowany jest słuchacz menu.
	 * @param tableData Tabela, która ma zostać zaktualizowana.
	 */
	public GroupMenuItemListener(Group group, JTable tableData) {
		this.group = group;
		this.tableData = tableData;
		this.tableStudentModel = new DefaultTableModel(columns, 0) {
			private static final long serialVersionUID = 3001553988693023386L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
	}

	/**
	 * Metoda obsługi zdarzenia akcji.
	 * 
	 * @param e Zdarzenie akcji.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		group = Group.codeToGroup.get(group.getGroupCode());
		tableStudentModel.setRowCount(0);
		for (Student student : group.getStudents()) {
			Object[] rowData = { student.getId(), group.getGroupCode(), student.getName(), student.getSurname() };
			tableStudentModel.addRow(rowData);
		}
		tableData.setModel(tableStudentModel);
		if (tableData.getRowCount() != 0)
			tableData.setRowSelectionInterval(0, 0);
	}
}
