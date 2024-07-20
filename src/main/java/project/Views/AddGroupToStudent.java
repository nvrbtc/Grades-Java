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
import javax.swing.JOptionPane;

import project.Models.Group;
import project.Models.Student;

/** Okno JFrame używane do dodawania grupy do studenta. */

public class AddGroupToStudent extends JFrame implements Runnable {
	private static final long serialVersionUID = -5995299095470556171L;

	/** Student, do którego dodajemy grupę */
	private Student student;

	/** ComboBox zawierający wszystkie dostępne grupy */
	private JComboBox<Group> comboAllGroups;

	/** Przycisk do potwierdzenia dodania grupy */
	private JButton button;

	/**
	 * Konstruktor nowego okna AddGroupToStudent.
	 * 
	 * @param st któremu zostanie dodana grupa.
	 */
	public AddGroupToStudent(Student st) {
		setPreferredSize(new Dimension(220, 120));
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.student = st;
		button = new JButton("Submit");
		setTitle("Add group");
		comboAllGroups = new JComboBox<>();
		for (Group a : availableGroups()) {
			comboAllGroups.addItem(a);
		}
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Group group = (Group) comboAllGroups.getSelectedItem();
				if (!student.getGroup().contains(group) && group != null)
					group.addStudent(student);
				dispose();
			}

		});
		if (comboAllGroups.getItemCount() != 0) {
			comboAllGroups.setSelectedIndex(0);
		}

		add(comboAllGroups);
		add(button);
		setVisible(comboAllGroups.getItemCount() != 0);
		setResizable(false);

		pack();
		if (comboAllGroups.getItemCount() != 0) {
			comboAllGroups.setSelectedIndex(0);
		} else {
			JOptionPane.showMessageDialog(null, "No available groups", "Sorry", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * Pobiera dostępne grupy, które mogą być dodane do studenta.
	 * 
	 * @return Zbiór dostępnych grup.
	 */
	public Set<Group> availableGroups() {
		Set<Group> all = new HashSet<Group>(Group.codeToGroup.values());
		all.removeAll(student.getGroup());
		return all;
	}

}
