package project.Views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.Gui;
import project.Models.Group;
import project.Models.Student;

/** Okno dodawania nowego studenta. */
public class AddNewStudentWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = -4075435062570821261L;

	/** Etykieta dla pola imienia */
	JLabel labelName;
	/** Etykieta dla pola nazwiska */
	JLabel labelSurname;

	/** Pole tekstowe dla imienia */
	JTextField inputName;
	/** Pole tekstowe dla nazwiska */
	JTextField inputSurname;

	/** Panel dla pola imienia */
	JPanel panelName;
	/** Panel dla pola nazwiska */
	JPanel panelSurname;
	/** Panel dla wyboru grupy */
	JPanel panelGroup;
	/** Panel dla przycisku "Add student" */
	JPanel panelSubmit;

	/** Przycisk "Add student" */
	JButton addStudent;

	/** ComBox dla grup */
	JComboBox<Group> groups;

	/** Wybrana grupa dla studenta */
	Group selectedGroup;
	/** Wybrany student */
	Student selectedStudent;

	/** Tworzy nowe okno dodawania nowego studenta. */
	public AddNewStudentWindow() {
		setTitle("Create student");
		setPreferredSize(new Dimension(250, 230));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		labelName = new JLabel("Name :");
		labelSurname = new JLabel("Surname :");

		inputName = new JTextField(20);
		inputSurname = new JTextField(20);

		panelName = new JPanel();
		panelSurname = new JPanel();
		panelGroup = new JPanel();
		panelSubmit = new JPanel();

		groups = new JComboBox<Group>();

		addStudent = new JButton("Add student");

		panelName.setLayout(new FlowLayout());
		labelName.setAlignmentX(Component.LEFT_ALIGNMENT);
		inputName.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelName.setAlignmentY(Component.CENTER_ALIGNMENT);
		inputName.setAlignmentY(Component.CENTER_ALIGNMENT);
		labelName.setBounds(50, 50, 50, 20);
		panelName.add(labelName);
		panelName.add(inputName);

		panelSurname.setLayout(new FlowLayout());
		labelSurname.setAlignmentX(Component.LEFT_ALIGNMENT);
		inputSurname.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelSurname.setAlignmentY(Component.CENTER_ALIGNMENT);
		inputSurname.setAlignmentY(Component.CENTER_ALIGNMENT);
		panelSurname.add(labelSurname);
		panelSurname.add(inputSurname);

		panelSubmit.add(addStudent);
		add(panelName);
		add(panelSurname);
		add(panelGroup);
		add(panelSubmit);
		panelGroup.add(groups);

		for (Group g : Group.codeToGroup.values()) {
			groups.addItem(g);
		}

		if (groups.getItemCount() != 0) {
			groups.setSelectedIndex(0);
		}

		addStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!inputName.getText().isEmpty() && !inputSurname.getText().isEmpty()) {
					selectedGroup = (Group) groups.getSelectedItem();
					if (selectedGroup != null) {
						selectedStudent = Student.createStudent(inputName.getText().trim(),
								inputSurname.getText().trim());
						selectedGroup.addStudent(selectedStudent);
						Gui.menuItemAllStudents.doClick();
						dispose();
					} 
					else {
						Student.createStudent(inputName.getText().trim(), inputSurname.getText().trim());
						Gui.menuItemAllStudents.doClick();
						dispose();
					}
					return;
				}
				JOptionPane.showMessageDialog(null, "Please enter both name and surname.", "Missing Input",
						JOptionPane.ERROR_MESSAGE);
			}

		});

		pack();
		setLocationByPlatform(true);
		setVisible(true);
		setResizable(false);

	}

	@Override
	public void run() {
	}
}