package project.Views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import project.Models.Group;
import project.Models.Student;
import project.Models.Subject;

/** Klasa reprezentująca okno zmiany grupy dla studenta. */

public class ChangeGroupWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = 9094110674577065395L;

	/** Panel aktualnej grupy studenta */
	private JPanel panelFromGroup;
	/** Panel dla nowej grupy studenta */
	private JPanel panelToGroup;
	/** Panel dla przycisku Submit */
	private JPanel panelSubmit;

	/** Etykieta dla aktualnej grupy studenta */
	private JLabel labGroup;
	/** Etykieta dla nowej grupy studenta */
	private JLabel labToGroup;

	/** Przycisk do potwierdzenia zmiany grupy */
	private JButton btSubmit;

	/** ComboBox dla wszystkich grup */
	private JComboBox<Group> comboAllGroups;

	/** ComboBox dla grup, do których należy student */
	private JComboBox<Group> comboStudentsGroups;

	/** Grupa, do której student zostanie przeniesiony */
	private Group groupTo;
	/** Aktualna grupa studenta */
	private Group groupFrom;
	/** Student, dla którego dokonywana jest zmiana grupy */
	private Student stud;

	/**
	 * Konstruktor klasy ChangeGroupWindow.
	 * 
	 * @param stud Student, którego grupa ma być zmieniona.
	 */
	public ChangeGroupWindow(Student stud) {
		this.stud = stud;
		setTitle(this.stud.toString() + " change group");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(250, 150));
		this.stud = stud;
		panelFromGroup = new JPanel();
		panelToGroup = new JPanel();
		panelSubmit = new JPanel();

		labGroup = new JLabel("Current groups :");
		labToGroup = new JLabel("Choose group :");

		btSubmit = new JButton("Submit");

		comboAllGroups = new JComboBox<Group>();
		comboStudentsGroups = new JComboBox<Group>();
		for (Group group : availableGroups()) {
			comboAllGroups.addItem(group);
		}
		for (Group group : this.stud.getGroup()) {
			comboStudentsGroups.addItem(group);
		}
		if (comboAllGroups.getItemCount() != 0)
			comboAllGroups.setSelectedIndex(0);

		if (comboStudentsGroups.getItemCount() != 0)
			comboStudentsGroups.setSelectedIndex(0);

		btSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				groupTo = (Group) comboAllGroups.getSelectedItem();
				groupFrom = (Group) comboStudentsGroups.getSelectedItem();
				if (groupFrom == null || groupTo == null) {
					JOptionPane.showMessageDialog(null, "No selected group", "Invalid iput", JOptionPane.ERROR_MESSAGE);
					return;
				}

				uniqueSubjects(stud);

				groupFrom.removeStudentChangeGroup(stud);
				groupTo.addStudent(stud);
				dispose();
			}
		});

		panelFromGroup.add(labGroup);
		panelFromGroup.add(comboStudentsGroups);
		panelToGroup.add(labToGroup);
		panelToGroup.add(comboAllGroups);
		panelSubmit.add(btSubmit);

		getContentPane().add(panelFromGroup);
		getContentPane().add(panelToGroup);
		getContentPane().add(panelSubmit);

		pack();
		setVisible(true);
		setResizable(false);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * Metoda usuwająca przedmioty, które są już przypisane do obu grup.
	 * 
	 * @param student Student, którego dotyczy zmiana grupy.
	 */
	public void uniqueSubjects(Student student) {
		Set<Subject> groupFromSubjects = new HashSet<>(groupFrom.getSubjects());
		Set<Subject> groupToSubjects = new HashSet<>(groupTo.getSubjects());
		Set<Subject> uniqueSubjectsGroup1 = new HashSet<>(groupFromSubjects);
		uniqueSubjectsGroup1.retainAll(groupToSubjects);
		groupFromSubjects.removeAll(uniqueSubjectsGroup1);
		for (Subject subject : groupFromSubjects) {
			subject.removeStudent(student);
		}
	}

	/**
	 * Metoda zwracająca dostępne grupy, do których student nie jest jeszcze
	 * przypisany.
	 * 
	 * @return Zbiór dostępnych grup.
	 */
	public Set<Group> availableGroups() {
		Set<Group> ret = new HashSet<Group>(Group.codeToGroup.values());
		ret.removeAll(stud.getGroup());
		return ret;
	}

}