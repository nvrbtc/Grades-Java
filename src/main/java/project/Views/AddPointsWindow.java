package project.Views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.Models.Group;
import project.Models.Student;
import project.Models.Subject;
/** Klasa reprezentująca okno dodawania punktów. */
public class AddPointsWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = -4075435062570821261L;


	/** ComBox dla grup */
	private JComboBox<Group> comboGroups;
	/** ComBox dla studentów */
	private JComboBox<Student> comboStudents;
	/** ComBox dla przedmiotów */
	private JComboBox<Subject> comboSubjects;

	/** Etykieta dla pola punktów */
	private JLabel labelPoints;
	/** Etykieta dla punktów studenta */
	private JLabel labelSudentPoints;

	/** Główny panel okna */
	private JPanel panelMain;
	/** Panel dla pola punktów */
	private JPanel panelPoints;
	/** Panel dla wprowadzania danych */
	private JPanel inputPanel;

	/** Pole tekstowe dla wprowadzania danych */
	private JTextField input;

	/** Przycisk do potwierdzenia dodania punktów */
	private JButton button;

	/** Wybrana grupa */
	private Group selectedGroup = null;
	/** Wybrany student */
	private Student selectedStudent = null;
	/** Wybrany przedmiot */
	private Subject selectedSubject = null;

	/** Punkty studenta **/
	private int studentPoints;

	/** Tworzy nowe okno dialogowe AddPointsWindow. */
	public AddPointsWindow() {
		setTitle("Add points");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(400, 230));
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		input = new JTextField(20);

		button = new JButton("Enter");

		panelMain = new JPanel();
		panelPoints = new JPanel();
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(1, 2, 5, 5));

		comboSubjects = new JComboBox<Subject>();
		comboStudents = new JComboBox<Student>();
		comboGroups = new JComboBox<Group>();

		labelSudentPoints = new JLabel();
		labelPoints = new JLabel("Points :");
		panelMain.setLayout(new GridLayout(3, 1, 5, 5));

		panelPoints.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
		panelPoints.setVisible(true);
		panelMain.add(comboGroups);
		panelMain.add(comboStudents);
		panelMain.add(comboSubjects);

		for (Group temp : Group.codeToGroup.values()) {
			if (!temp.students.isEmpty() && !temp.getSubjects().isEmpty() )
				comboGroups.addItem(temp);
		}

		comboGroups.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedGroup = (Group) comboGroups.getSelectedItem();
				comboStudents.removeAllItems();
				for (Student student : selectedGroup.getStudents()) {
					comboStudents.addItem(student);
				}
				comboStudents.setSelectedIndex(0);
				
			}
		});

		comboStudents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedStudent = (Student) comboStudents.getSelectedItem();
				comboSubjects.removeAllItems();
				for (Subject sub : selectedGroup.subjects) {
					comboSubjects.addItem(sub);
				}
				comboSubjects.setSelectedIndex(0);
				
			}
		});

		comboSubjects.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSubject = (Subject) comboSubjects.getSelectedItem();
				if (selectedSubject != null && selectedStudent!= null ) {
				studentPoints = selectedSubject.getPoints(selectedStudent);
				labelSudentPoints.setText(String.valueOf(studentPoints));
				}

			}
		});

		inputPanel.add(input);
		inputPanel.add(button);

		panelPoints.add(labelPoints);
		panelPoints.add(labelSudentPoints);

		add(panelMain);
		add(panelPoints);
		add(inputPanel);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer pointsToAdd = Integer.parseInt(input.getText());

					if (selectedStudent != null && selectedSubject != null) {
						selectedSubject.addPoints(selectedStudent, pointsToAdd);
						comboSubjects.setSelectedItem(selectedSubject);
						input.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Invalid data.", "Invalid input.",
								JOptionPane.ERROR_MESSAGE);
						input.setText("");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please enter a valid integer.", "Invalid input.",
							JOptionPane.ERROR_MESSAGE);
					input.setText("");
				}
			}
		});

		pack();
		setLocationByPlatform(true);
		setVisible(comboGroups.getItemCount() != 0);
		setResizable(false);
		
		if (comboGroups.getItemCount() != 0) {
			comboGroups.setSelectedIndex(0);
			selectedGroup = (Group) comboGroups.getSelectedItem();

		} else {
			JOptionPane.showMessageDialog(null, "No groups available", "Sorry", JOptionPane.ERROR_MESSAGE);
			dispose();
		}

	}

	@Override
	public void run() {
	}
}