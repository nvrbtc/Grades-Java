package project.Views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import project.Models.Group;
import project.Models.Specialization;
import project.Actions.*;
/** Okno dialogowe służące do dodawania nowej grupy. */
public class AddGroupWindow extends JDialog implements Runnable {
	private static final long serialVersionUID = -4539917670276509233L;

	/** ComboBox dla specjalizacji */
	private JComboBox<Specialization> comboSpecializations;
	/** Pole tekstowe dla kodu grupy */
	private JTextField groupCodeField;
	/** Pole tekstowe dla opisu grupy */
	private JTextField descriptionField;
	/** Główny panel okna */
	private JPanel panelMain;
	/** Panel dla przycisku "Submit" */
	private JPanel panelSubmit;
	/** Przycisk "Submit" */
	private JButton btSubmit;
	/** Zmienna przechowująca ostateczny kod grupy */
	private String finalGroupCode = null;
	/**
	 * Zmienna przechowująca referencję do obiektu grupy, początkowo ustawiona na
	 * null
	 */
	private Group group = null;
	/** Tabela danych */
	private JTable dataTable;

	/**
	 * Tworzy nowe okno dialogowe AddGroupWindow.
	 * 
	 * @param parent Frame ramka nadrzędna, z którą okno dialogowe jest powiązane.
	 * @param dataTableIn tabela z danymi
	 */
	public AddGroupWindow(Frame parent, JTable dataTableIn) {

		super(parent, "Add new Group", true);
		setTitle("Add new Group");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		dataTable = dataTableIn;

		comboSpecializations = new JComboBox<>(Specialization.values());
		groupCodeField = new JTextField(20);
		descriptionField = new JTextField(20);

		panelSubmit = new JPanel();
		panelMain = new JPanel();

		btSubmit = new JButton("Enter");
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

		panelMain.add(new JLabel("Group Code:"));
		panelMain.add(groupCodeField);
		panelMain.add(new JLabel("Specialization:"));
		panelMain.add(comboSpecializations);
		panelMain.add(new JLabel("Description:"));
		panelMain.add(descriptionField);

		panelMain.add(panelSubmit);

		panelSubmit.setLayout(new FlowLayout());
		panelSubmit.add(btSubmit);

		btSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String groupCode = groupCodeField.getText().replaceAll("\\s", "");
				Specialization specialization = (Specialization) comboSpecializations.getSelectedItem();
				String description = descriptionField.getText();

				if (groupCode.isEmpty() && specialization.getCode() == null) {
					System.out.println("Group code cannot be empty.");
					return;
				}

				finalGroupCode = specialization.getCode() + "-" + groupCode;
				try {
				group = Group.createGroup(finalGroupCode, specialization.getSpec(), description);
				group.menuItem.addActionListener(new GroupMenuItemListener(group, dataTable));
				}
				catch(Exception ex) {
					group = null;
					JOptionPane.showMessageDialog(null, "Already have same group");
				}
				
				dispose();
			}
		});

		getContentPane().add(BorderLayout.CENTER, panelMain);
		pack();
		setLocationByPlatform(true);
		setVisible(true);
		setResizable(false);
	}

	@Override
	public void run() {
	}

	/**
	 * Zwraca referencję do grupy.
	 * 
	 * @return Referencja do grupy.
	 */
	public Group returnValue() {
		return group;
	}

}
