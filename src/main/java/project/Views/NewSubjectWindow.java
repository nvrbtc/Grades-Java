package project.Views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.Models.Subject;

/**
 * Klasa NewSubjectWindow reprezentuje okno interfejsu użytkownika do definiowania nowego przedmiotu.
 */
public class NewSubjectWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = 9188598477402941864L;

	/** Panel do wprowadzania nazwy przedmiotu. */
	private JPanel panelInputName = new JPanel();

	/** Panel do wprowadzania maksymalnej oceny. */
	private JPanel panelInputPoints = new JPanel();

	/** Panel do wprowadzania opisu przedmiotu. */
	private JPanel panelInputDescription = new JPanel();

	/** Panel do przycisku zgłaszania. */
	private JPanel panelInputSubmit = new JPanel();

	/** Etykieta dla nazwy przedmiotu. */
	private JLabel labelName = new JLabel("Subject name : ");

	/** Etykieta dla maksymalnej oceny. */
	private JLabel labelMaxPoints = new JLabel("Maximum grade : ");

	/** Etykieta dla opisu przedmiotu. */
	private JLabel labelDescription = new JLabel("Description : ");

	/** Pole tekstowe dla nazwy przedmiotu. */
	private JTextField inpName = new JTextField(26);

	/** Pole tekstowe dla maksymalnej oceny. */
	private JTextField inpMaxPoints = new JTextField(26);

	/** Pole tekstowe dla opisu przedmiotu. */
	private JTextField inpDescription = new JTextField(26);

	/** Przycisk zgłaszania. */
	private JButton submit = new JButton("Submit");

	/**
	 * Konstruktor klasy NewSubjectWindow.
	 *
	 * @param menuItemAllSubjects JMenuItem, który zostanie kliknięty po zatwierdzeniu nowego przedmiotu.
	 */
	public NewSubjectWindow(JMenuItem menuItemAllSubjects) {
		setTitle("Define new subject");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setPreferredSize(new Dimension(300, 250));
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		panelInputName.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelInputName.add(labelName);
		panelInputName.add(inpName);

		panelInputPoints.add(labelMaxPoints);
		panelInputPoints.add(inpMaxPoints);
		panelInputPoints.setLayout(new FlowLayout(FlowLayout.LEFT));

		panelInputDescription.add(labelDescription);
		panelInputDescription.add(inpDescription);
		panelInputDescription.setLayout(new FlowLayout(FlowLayout.LEFT));

		panelInputSubmit.add(submit);
		add(panelInputName);
		add(panelInputPoints);
		add(panelInputDescription);
		add(panelInputSubmit);
		
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int max = Integer.parseInt(inpMaxPoints.getText());
					if (inpName.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "No subject name given", "Invalid input",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					if ( Subject.allSubjects.containsKey(inpName.getText()))
					{
						JOptionPane.showMessageDialog(null, "Same name", "Invalid input",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					Subject.createSubject(inpName.getText(), max, inpDescription.getText());
					menuItemAllSubjects.doClick();
					dispose();

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid integer", "Invalid input",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pack();
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
