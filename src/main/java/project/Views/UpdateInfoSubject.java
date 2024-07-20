package project.Views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import project.Models.Subject;

/**
 * Klasa UpdateInfoSubject umożliwia aktualizację informacji o wybranym przedmiocie.
 */
public class UpdateInfoSubject extends JDialog {
	
	private static final long serialVersionUID = -9022840630709463558L;
	
	/** Przedmiot do aktualizacji. */
	private Subject subject;

	/** Etykieta dla nazwy przedmiotu. */
	private JLabel lbName = new JLabel("Name:");
	
	/** Etykieta dla opisu przedmiotu. */
	private JLabel lbDescription = new JLabel("Description");

	/** Pole tekstowe dla nazwy przedmiotu. */
	private JTextField tfName = new JTextField(20);
	
	/** Pole tekstowe dla opisu przedmiotu. */
	private JTextArea tfDescription = new JTextArea(4, 1);

	/** Panel dla etykiety i pola tekstowego nazwy przedmiotu. */
	private JPanel pnName = new JPanel();
	
	/** Panel dla etykiety i pola tekstowego opisu przedmiotu. */
	private JPanel pnDescription = new JPanel();

	/** Panel dla przycisku zatwierdzającego. */
	private JPanel pnSubmit = new JPanel();

	/** Przycisk zatwierdzający zmiany. */
	private JButton btSubmit = new JButton("Submit");

	/**
	 * Konstruktor klasy UpdateInfoSubject.
	 *
	 * @param selectedSubj wybrany przedmiot do edycji.
	 * @param frame główne okno aplikacji.
	 */
	public UpdateInfoSubject(Subject selectedSubj, JFrame frame) {
		super(frame, "Update subject", true);
		this.subject = selectedSubj;
		tfName.setText(subject.getSubjectName());
		tfDescription.setText(subject.getDescription());
		tfDescription.setLineWrap(true);
		tfDescription.setWrapStyleWord(true);
		setPreferredSize(new Dimension(380, 220));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		pnName.setLayout(new GridLayout(1, 2, 5, 5));
		pnName.add(lbName);
		pnName.add(tfName);

		pnDescription.setLayout(new GridLayout(2, 1, 5, 5));
		pnDescription.add(lbDescription);
		pnDescription.add(tfDescription);

		pnSubmit.add(btSubmit);
		
		add(pnName);
		add(pnDescription);
		add(pnSubmit);

		btSubmit.addActionListener(new ActionListener() {

			/**
			 * Metoda wywoływana po naciśnięciu przycisku. Aktualizuje dane wybranego przedmiotu.
			 *
			 * @param e zdarzenie ActionEvent
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String oldName = subject.getSubjectName();
					String updatedName = tfName.getText();
					String updatedDesc = tfDescription.getText();
					subject.setSubjectName(updatedName);
					subject.setDescription(updatedDesc);
					Subject.allSubjects.remove(oldName);
					Subject.allSubjects.put(updatedName, subject);
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid input", "Sorry", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pack();
		setVisible(true);
	}

}
