package project.Views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import project.Gui;
import project.Models.Group;
import project.Models.Student;

/**
 * Klasa GroupListWindow reprezentuje okno wyświetlające listę wszystkich grup w interfejsie użytkownika.
 */
public class GroupListWindow extends JFrame {
	
    private static final long serialVersionUID = -1187354340055109109L;
    
    /**
     * Reprezentuje komponent tabeli do wyświetlania informacji o grupach.
     */
    private JTable table;

    /**
     * Reprezentuje model tabeli do zarządzania danymi wyświetlanymi w tabeli.
     */
    private DefaultTableModel tableModel;

    /**
     * Reprezentuje przycisk do usuwania grupy.
     */
    private JButton deleteButton;

    /**
     * Reprezentuje przycisk do edycji grupy.
     */
    private JButton editButton;

    /**
     * Reprezentuje panel zawierający przyciski do akcji na grupach.
     */
    private JPanel buttonPanel;
    /**
     * Constructs a GroupListWindow object.
     */
    public GroupListWindow() {
        setTitle("List All Groups");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);

        String[] columns = { "Group Code", "Specialization", "Description" };
        tableModel = new DefaultTableModel(columns, 0);
        for (Group group : Group.codeToGroup.values()) {
            Object[] rowData = { group.getGroupCode(), group.getSpecialization(), group.getDescription() };
            tableModel.addRow(rowData);
        }

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String groupCode = (String) table.getValueAt(selectedRow, 0);
                    Group group = Group.codeToGroup.get(groupCode);
                    if (group != null) {

                        JFrame frame = new JFrame();
                        EditGroupWindow editGroupWindow = new EditGroupWindow(group, frame);
                        if (!editGroupWindow.groupChange())
                            return;
                        tableModel.removeRow(selectedRow);
                        Object[] rowData = { group.getGroupCode(), group.getSpecialization(), group.getDescription() };
                        tableModel.addRow(rowData);
                    } else {
                        JOptionPane.showMessageDialog(null, "Group not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a group", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String groupCode = (String) table.getValueAt(selectedRow, 0);
                    Group group = Group.codeToGroup.get(groupCode);
                    if (group != null) {
                        Group.codeToGroup.remove(groupCode);
                        ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                        for (Student s : group.students) {
                            s.removeGroup(group);
                        }
                        try {
                            Container parent = group.menuItem.getParent();
                            if (parent instanceof JPopupMenu) {
                                JPopupMenu groupMenu = (JPopupMenu) parent;
                                groupMenu.remove(group.menuItem);
                            }
                        } catch (Exception ex) {

                        }
                        Gui.menuItemAllStudents.doClick();
                        group = null;
                        JOptionPane.showMessageDialog(null, "Group deleted successfully.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Group not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a group", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
