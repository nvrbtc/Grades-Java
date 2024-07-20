package project.Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import project.Models.Group;
import project.Models.Student;
import project.Models.Subject;

/**
 * Klasa SearchStudentInfo wyświetla informacje o studencie w nowym oknie JFrame.
 */
public class SearchStudentInfo extends JFrame implements Runnable {

	private static final long serialVersionUID = -2663805917307916968L;

	/** Panel do wyświetlania ogólnych informacji o studencie. */
	private JPanel panelGeneralInfo = new JPanel();

	/** Panel do wyświetlania ocen studenta. */
	private JPanel panelGrades = new JPanel();

	/** Etykieta wyświetlająca ID studenta. */
	private JLabel labelId = new JLabel("ID:");

	/** Etykieta wyświetlająca pełne imię i nazwisko studenta. */
	private JLabel labelFullName = new JLabel("Full Name:");

	/** Etykieta wyświetlająca grupę studenta. */
	private JLabel labelGroup = new JLabel("Group:");

	/** Etykieta wyjściowa dla ID studenta. */
	private JLabel labelIdOut;

	/** Etykieta wyjściowa dla pełnego imienia i nazwiska studenta. */
	private JLabel labelFullNameOut;

	/** Etykieta wyjściowa dla grupy studenta. */
	private JLabel labelGroupOut;

	/** Model tabeli do wyświetlania ocen studenta. */
	private DefaultTableModel tableStudentModel;

	/** Tabela do wyświetlania ocen studenta. */
	private JTable tableData = new JTable();

	/** Zbiór przedmiotów studenta. */
	private Set<Subject> studentSubjects;

	/**
	 * Konstruktor klasy SearchStudentInfo.
	 *
	 * @param student obiekt studenta, dla którego mają zostać wyświetlone informacje.
	 */
	public SearchStudentInfo(Student student) {

		studentSubjects = new HashSet<>();

		setTitle("Student Information: " + student.toString());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(450, 430));

		panelGeneralInfo.setLayout(new GridLayout(4, 2, 5, 5));
		panelGeneralInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		labelIdOut = new JLabel(Integer.toString(student.getId()));
		labelFullNameOut = new JLabel(student.toString());
		labelGroupOut = new JLabel(student.getGroup().toString());

		panelGeneralInfo.add(labelId);
		panelGeneralInfo.add(labelIdOut);
		panelGeneralInfo.add(labelFullName);
		panelGeneralInfo.add(labelFullNameOut);
		panelGeneralInfo.add(labelGroup);
		panelGeneralInfo.add(labelGroupOut);

		String[] columns = { "Subject", "Grade" };
		tableStudentModel = new DefaultTableModel(columns, 0);
		tableData.setModel(tableStudentModel);
		tableData.setRowHeight(25);
		tableData.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

		JScrollPane scrollPane = new JScrollPane(tableData);
		scrollPane.setPreferredSize(new Dimension(400, 200));

		panelGrades.setLayout(new BorderLayout());
		panelGrades.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelGrades.add(new JLabel("Grades:"), BorderLayout.NORTH);
		panelGrades.add(scrollPane, BorderLayout.CENTER);

		for (Group group : student.getGroup()) {
			for (Subject subject : group.subjects) {
				studentSubjects.add(subject);
			}
		}

		for (Subject subject : studentSubjects) {
			String grade = String.valueOf(subject.getPoints(student));
			Object[] rowData = { subject.getSubjectName(), grade };
			tableStudentModel.addRow(rowData);
		}

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(panelGeneralInfo);
		getContentPane().add(panelGrades);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}

	/**
	 * Metoda uruchamiana w wątku.
	 */
	@Override
	public void run() {
	}
}
