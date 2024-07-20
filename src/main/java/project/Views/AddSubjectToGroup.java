package project.Views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import project.Models.Group;
import project.Models.Subject;

/** Klasa reprezentująca okno dodawania przedmiotu do grupy. */
public class AddSubjectToGroup extends JFrame implements Runnable {
	private static final long serialVersionUID = 8590624414517031723L;
	/** Wybrany przedmiot */
	private Subject selectedSubject;

	/** Etykieta dla wybranego przedmiotu */
	private JLabel lbSelected;
	/** Etykieta dla nazwy przedmiotu */
	private JLabel lbSubject;

	/** ComboBox dla grup */
	private JComboBox<Group> comboGroups;

	/** Przycisk Submit */
	private JButton btSumbit;

	/** Panel zawierający etykiety */
	private JPanel panelLb;
	/** Panel zawierający ComboBox dla grup */
	private JPanel panelCombo;
	/** Panel zawierający przycisk Submit */
	private JPanel panelBt;
	/** Referencja do obiektu reprezentującego grupę. */
	private Group group;

	/**
	 * Konstruktor klasy AddSubjectToGroup.
	 * 
	 * @param subject Subject Wybrany przedmiot.
	 */
	public AddSubjectToGroup(Subject subject) {
		this.selectedSubject = subject;

		lbSelected = new JLabel("Selected Subject:");
		lbSubject = new JLabel(selectedSubject.getSubjectName());

		panelLb = new JPanel();
		panelCombo = new JPanel();
		panelBt = new JPanel();

		comboGroups = new JComboBox<Group>();

		btSumbit = new JButton("Submit");
		panelLb.setAlignmentX(CENTER_ALIGNMENT);
		panelLb.add(lbSelected);
		panelLb.add(lbSubject);

		panelCombo.add(comboGroups);

		panelBt.add(btSumbit);

		setPreferredSize(new Dimension(300, 200));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		for (Group g : Group.codeToGroup.values()) {
			if (!g.subjects.contains(selectedSubject))
				comboGroups.addItem(g);
		}

		add(panelLb);
		add(panelCombo);
		add(panelBt);

		pack();
		setVisible(comboGroups.getItemCount() != 0);
		setResizable(false);
		btSumbit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				group = (Group) comboGroups.getSelectedItem();
				if (group == null)
					return;
				group.addSubject(selectedSubject);
				dispose();
			}
		});
		if (comboGroups.getItemCount() != 0) {
			comboGroups.setSelectedIndex(0);
		} else {
			JOptionPane.showMessageDialog(null, "No available groups", "No groups", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
