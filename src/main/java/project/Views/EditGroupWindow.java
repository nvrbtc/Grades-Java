package project.Views;

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
import javax.swing.JTextField;

import project.Models.Group;
import project.Models.Specialization;

/** Klasa reprezentująca okno edycji grupy. */
public class EditGroupWindow extends JDialog {
	private static final long serialVersionUID = 129905563063204093L;

	/** Grupa, która jest edytowana */
	private Group group;
	/** Pole tekstowe dla kodu grupy */
	private JTextField groupCodeField;
	/** ComboBox dla specjalizacji grupy */
	private JComboBox<Specialization> specializationComboBox;
	/** Pole tekstowe dla opisu grupy */
	private JTextField descriptionField;
	/** Flaga informująca, czy grupa została zmieniona */
	private boolean groupChanged = false;

	/**
	 * Konstruktor klasy EditGroupWindow.
	 * 
	 * @param gp które chcemy edytować.
	 * @param frame nadrzędna dla okna dialogowego.
	 */
	public EditGroupWindow(Group gp, JFrame frame) {
		super(frame, "Edit Group", true);
		this.group = gp;

		setTitle("Edit Group: " + group.getGroupCode());
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel groupCodeLabel = new JLabel("Group Code:");
		groupCodeField = new JTextField(20);
		groupCodeField.setText(group.getGroupCode());

		JLabel specializationLabel = new JLabel("Specialization:");
		specializationComboBox = new JComboBox<>(Specialization.values());
		specializationComboBox.setSelectedItem(group.getSpecialization());

		JLabel descriptionLabel = new JLabel("Description:");
		descriptionField = new JTextField(20);
		descriptionField.setText(group.getDescription());

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newGroupCode = groupCodeField.getText().replaceAll("\\s", "");
				Specialization newSpecialization = (Specialization) specializationComboBox.getSelectedItem();
				String newDescription = descriptionField.getText();

				String oldCode = group.getGroupCode();

				group.setGroupCode(newGroupCode);
				group.setSpecialization(newSpecialization.getSpec());
				group.setDescription(newDescription);
				groupChanged = true;
				Group.codeToGroup.remove(oldCode);
				Group.codeToGroup.put(groupCodeField.getText(), group);
				group.menuItem.setText(newGroupCode);
				JOptionPane.showMessageDialog(null, "Group updated successfully.", "Success",
						JOptionPane.INFORMATION_MESSAGE);

				dispose();
			}
		});

		panel.add(groupCodeLabel);
		panel.add(groupCodeField);
		panel.add(specializationLabel);
		panel.add(specializationComboBox);
		panel.add(descriptionLabel);
		panel.add(descriptionField);
		panel.add(updateButton);

		add(panel);
		setVisible(true);
	}

	/**
	 * Metoda zwracająca informacje o zmianie grupy.
	 * 
	 * @return True, jeżli grupa została zmieniona; False w przeciwnym przypadku.
	 */
	public boolean groupChange() {
		return groupChanged;
	}
}
