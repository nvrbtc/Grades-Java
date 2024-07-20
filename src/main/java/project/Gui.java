package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import project.Actions.AddSubjectToGroupAction;
import project.Actions.AllStudentAction;
import project.Actions.DeleteSubjectAction;
import project.Actions.OnOpeningGui;
import project.Actions.UpdateSubjectAction;
import project.Models.Group;
import project.Models.Student;
import project.Models.Subject;
import project.Views.AddGroupToStudent;
import project.Views.AddGroupWindow;
import project.Views.AddNewStudentWindow;
import project.Views.AddPointsWindow;
import project.Views.ChangeGroupWindow;
import project.Views.GroupListWindow;
import project.Views.NewSubjectWindow;
import project.Views.SearchStudentInfo;
import project.Views.UpdateInfoSubject;

public class Gui extends JFrame implements Runnable {
	
	/**
	 * Unikalny identyfikator wersji dla serializacji.
	 */
	private static final long serialVersionUID = 5396864686589918698L;

	/**
	 * Pasek menu aplikacji.
	 */
	private JMenuBar menuBar;

	/**
	 * Menu dla działu z uczniami.
	 */
	private JMenu menuStudents;

	/**
	 * Menu dla działu z grupami.
	 */
	private JMenu menuGroups;

	/**
	 * Menu dla działu z przedmiotami.
	 */
	private JMenu menuSubjects;

	/**
	 * Model tabeli dla danych uczniów.
	 */
	private DefaultTableModel tableStudentModel;

	/**
	 * Tabela dla wyświetlania danych.
	 */
	private JTable tableData;

	/**
	 * Identyfikator wybranego ucznia.
	 */
	private int selectedStudentId;

	/**
	 * Element menu: "Wszyscy uczniowie".
	 */
	public static JMenuItem menuItemAllStudents;

	/**
	 * Element menu: "Wyszukaj ucznia".
	 */
	private JMenuItem menuItemSearchStudent;

	/**
	 * Element menu: "Dodaj ucznia".
	 */
	private JMenuItem menuItemCreateStudent;

	/**
	 * Element menu: "Utwórz grupę".
	 */
	private JMenuItem menuItemCreateGroup;

	/**
	 * Element menu: "Dodaj punkty".
	 */
	private JMenuItem menuItemAddPoints;

	/**
	 * Element menu: "Nowy przedmiot".
	 */
	private JMenuItem menuItemNewSubject;

	/**
	 * Element menu: "Wszystkie przedmioty".
	 */
	private JMenuItem menuItemAllSubjects;

	/**
	 * Element menu: "Wszystkie grupy".
	 */
	private JMenuItem menuItemAllGroups;

	/**
	 * Obszar przewijania dla tabeli.
	 */
	private JScrollPane scrollPane;

	/**
	 * Przycisk "Zmień grupę".
	 */
	private JButton btChangeGroup;

	/**
	 * Przycisk "Dodaj grupę".
	 */
	private JButton btAddGroup;

	/**
	 * Przycisk "Dodaj przedmiot do grupy".
	 */
	private JButton btAddSubjectToGroup;

	/**
	 * Przycisk "Aktualizuj przedmiot".
	 */
	private JButton btUpdateSubject;

	/**
	 * Przycisk "Usuń przedmiot".
	 */
	private JButton btDeleteSubject;

	/**
	 * Dolny panel aplikacji.
	 */
	private JPanel panelBottom;

	public Gui() {

		setTitle("Project Database");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);

		initUi();
		
		buttonsVisibality();

		
		menuItemCreateStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new AddNewStudentWindow());
				thread.start();
			}

		});

		tableData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selectedRow = tableData.getSelectedRow();
					if (selectedRow != -1) {
						try {
							selectedStudentId = (int) tableData.getValueAt(selectedRow, 0);
						} catch (ClassCastException ex) {
							selectedStudentId = 0;
						}
					}
					if (selectedRow == -1) {
						selectedStudentId = -1;
					}
				}
				buttonsVisibality();
			}
		});

		menuItemCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				AddGroupWindow dialog = new AddGroupWindow(frame, tableData);
				try {
					menuGroups.add(dialog.returnValue().menuItem);
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(dialog, "you didnt create group", "OK", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		menuItemAddPoints.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new AddPointsWindow());
				thread.start();
			}
		});

		menuItemAllStudents.addActionListener(new AllStudentAction(tableData));
		btChangeGroup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Student a = Student.allStudents.get(Integer.valueOf(selectedStudentId));
				if (a != null) {
					Thread window = new Thread(new ChangeGroupWindow(a));
					window.start();
				} else {
					JOptionPane.showMessageDialog(null, "No student selected", "Invalid input",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		menuItemSearchStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Student.allStudents.get(selectedStudentId) == null) {
					JOptionPane.showMessageDialog(null, "No student selected", "Forbiden", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Thread t = new Thread(new SearchStudentInfo(Student.allStudents.get(selectedStudentId)));
				t.start();

			}

		});

		btAddGroup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedStudentId <= 0) {
					JOptionPane.showMessageDialog(null, "No student selected", "Forbiden", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Thread t = new Thread(new AddGroupToStudent(Student.allStudents.get(selectedStudentId)));
				t.start();
			}

		});
		
		menuItemAllSubjects.addActionListener(new UpdateSubjectAction(tableData));

		btDeleteSubject.addActionListener(new DeleteSubjectAction(tableData, menuItemAllSubjects));

		menuItemNewSubject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t = new Thread(new NewSubjectWindow(menuItemAllSubjects));
				t.start();

			}
		});
		btAddSubjectToGroup.addActionListener(new AddSubjectToGroupAction(tableData));
		btUpdateSubject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( selectedStudentId != 0 ) return;
					String subject = (String) tableData.getValueAt(tableData.getSelectedRow(), 0);
					JFrame frame = new JFrame();
					UpdateInfoSubject upd = new UpdateInfoSubject(Subject.allSubjects.get(subject),frame);
					menuItemAllSubjects.doClick();
			}
		});
		menuItemAllGroups.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GroupListWindow groupListWindow = new GroupListWindow();
			}
		});
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Thread t = new Thread(() -> {
					saveDataToFile();
				});
				t.start();
			}
		});
		addWindowListener(new OnOpeningGui(tableData, menuGroups,menuItemAllStudents));
		
		getContentPane().add(scrollPane);
		getContentPane().add(BorderLayout.NORTH, menuBar);
		getContentPane().add(BorderLayout.SOUTH, panelBottom);
		setVisible(true);
		
		menuItemAllStudents.doClick();
	}

	@Override
	public void run() {

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					
				}
				new Gui();
				
			}
		});
	}
	
	/**
	 * Metoda odpowiedzialna za ustawianie widoczności przycisków w zależności od wybranego ucznia.
	 */
	private void buttonsVisibality() {
		btAddSubjectToGroup.setVisible(selectedStudentId == 0);
		btUpdateSubject.setVisible(selectedStudentId == 0);
		btAddGroup.setVisible(selectedStudentId > 0);
		btChangeGroup.setVisible(selectedStudentId > 0);
		btDeleteSubject.setVisible(selectedStudentId == 0);
	}


/**
 * Metoda zapisująca dane do pliku.
 */
	private void saveDataToFile() {
		synchronized (Student.allStudents.values()) {
			synchronized (Group.codeToGroup.values()) {
				synchronized (Subject.allSubjects.values()) {
					try (FileOutputStream fileOut = new FileOutputStream("Data.ser");
							ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
						for (Student s : Student.allStudents.values()) {
							out.writeObject(s);
						}
						for (Group g : Group.codeToGroup.values()) {
							out.writeObject(g);
						}
						for (Subject sub : Subject.allSubjects.values()) {
							out.writeObject(sub);
						}
						out.writeObject(Student.getCurrentId());
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		JOptionPane.showMessageDialog(null, "Serialization done", "GOOD", JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * Metoda inicjalizująca interfejs użytkownika.
	 */
	private void initUi() {
		menuBar = new JMenuBar();
		selectedStudentId = -1;
		menuStudents = new JMenu("Students");
		menuGroups = new JMenu("Groups");
		menuSubjects = new JMenu("Subjects");
		menuBar.add(menuStudents);
		menuBar.add(menuGroups);
		menuBar.add(menuSubjects);

		menuItemAllStudents = new JMenuItem("List all students");
		menuItemSearchStudent = new JMenuItem("Info by student");
		menuItemCreateStudent = new JMenuItem("Create student");
		menuItemCreateGroup = new JMenuItem("Create group");
		menuItemAddPoints = new JMenuItem("Add points");
		menuItemNewSubject = new JMenuItem("New subject");
		menuItemAllSubjects = new JMenuItem("List all");
		menuItemAllGroups = new JMenuItem("List all groups");
		menuGroups.add(menuItemCreateGroup);
		menuSubjects.add(menuItemAddPoints);
		menuSubjects.add(menuItemNewSubject);
		menuSubjects.add(menuItemAllSubjects);
		menuGroups.add(menuItemAllGroups);

		btChangeGroup = new JButton("Replace group");
		btAddGroup = new JButton("Add group to student");
		btAddSubjectToGroup = new JButton("Add subject to group");
		btUpdateSubject = new JButton("Update info");
		btDeleteSubject = new JButton("Delete subject");

		panelBottom = new JPanel();
		panelBottom.setLayout(new FlowLayout());
		panelBottom.add(btChangeGroup);
		panelBottom.add(btAddGroup);
		panelBottom.add(btDeleteSubject);

		panelBottom.add(btAddSubjectToGroup);
		panelBottom.add(btUpdateSubject);

		tableData = new JTable(tableStudentModel);

		scrollPane = new JScrollPane(tableData);
		menuStudents.add(menuItemAllStudents);
		menuStudents.add(menuItemSearchStudent);
		menuStudents.add(menuItemCreateStudent);
		
		tableData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	}
}
