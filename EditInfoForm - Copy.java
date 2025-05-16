import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class EditInfoForm extends JFrame {
    private JTextField nameField, ageField, emailField;
    private JComboBox<String> bloodTypeComboBox;
    private JComboBox<String> rhFactorComboBox;
    private LinkedList<String> editHistory = new LinkedList<>();
    private PatientBSTree bst;
    private PatientInfo patient;

    // Constructor with PatientInfo and PatientBSTree as parameters
    public EditInfoForm(PatientInfo patient, PatientBSTree bst) {
        this.patient = patient;
        this.bst = bst;

        setTitle("Edit Patient Info");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel bloodTypeLabel = new JLabel("Blood Type:");
        JLabel rhFactorLabel = new JLabel("Rh Factor:");

        nameField = new JTextField(patient.name);
        ageField = new JTextField(patient.age);
        emailField = new JTextField(patient.email);

        bloodTypeComboBox = new JComboBox<>(new String[]{"A", "B", "AB", "O"});
        rhFactorComboBox = new JComboBox<>(new String[]{"+", "-"});

        if (patient.bloodType.length() > 1) {
            bloodTypeComboBox.setSelectedItem(patient.bloodType.substring(0, patient.bloodType.length() - 1));
            rhFactorComboBox.setSelectedItem(patient.bloodType.substring(patient.bloodType.length() - 1));
        }

        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        JButton historyButton = new JButton("View Edit History");

        saveButton.addActionListener(e -> {
            String newName = nameField.getText();
            String newAge = ageField.getText();
            String newEmail = emailField.getText();
            String newBloodType = (String) bloodTypeComboBox.getSelectedItem();
            String newRhFactor = (String) rhFactorComboBox.getSelectedItem();
            String newBlood = newBloodType + newRhFactor;

            StringBuilder changeLog = new StringBuilder("Changes for " + patient.name + ": ");
            if (!newName.equals(patient.name)) changeLog.append("Name changed from ").append(patient.name).append(" to ").append(newName).append(". ");
            if (!newAge.equals(patient.age)) changeLog.append("Age changed from ").append(patient.age).append(" to ").append(newAge).append(". ");
            if (!newEmail.equals(patient.email)) changeLog.append("Email changed from ").append(patient.email).append(" to ").append(newEmail).append(". ");
            if (!newBlood.equals(patient.bloodType)) changeLog.append("Blood Type changed from ").append(patient.bloodType).append(" to ").append(newBlood).append(". ");
            editHistory.add(changeLog.toString());

            bst.deletePatient(patient.name);
            bst.insertPatient(newName, newAge, newEmail, newBlood);
            JOptionPane.showMessageDialog(this, "Patient information updated successfully.");
            dispose();
        });

        deleteButton.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                bst.deletePatient(patient.name);
                JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
                dispose();
            }
        });

        historyButton.addActionListener(e -> {
            if (editHistory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No edit history available.", "History", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder history = new StringBuilder();
                for (String entry : editHistory) {
                    history.append(entry).append("\n");
                }
                JOptionPane.showMessageDialog(this, history.toString(), "Edit History", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(bloodTypeLabel);
        panel.add(bloodTypeComboBox);
        panel.add(rhFactorLabel);
        panel.add(rhFactorComboBox);
        panel.add(saveButton);
        panel.add(deleteButton);
        panel.add(historyButton);

        add(panel);
        setVisible(true);
    }
}
