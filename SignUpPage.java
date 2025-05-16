import javax.swing.*;
import java.awt.*;

public class SignUpPage extends JFrame {
    private PatientBSTree patientTree;

    public SignUpPage(PatientBSTree tree) {
        this.patientTree = tree;

        setTitle("Hospital System - Sign Up");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField emailField = new JTextField();

        panel.add(new JLabel("Enter Patient Information", JLabel.CENTER));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        String[] bloodTypeOptions = {"", "A", "B", "AB", "O"};
        JComboBox<String> comboBoxBloodType = new JComboBox<>(bloodTypeOptions);
        panel.add(new JLabel("Select Blood Type:"));
        panel.add(comboBoxBloodType);

        String[] rhFactorOptions = {"", "+", "-"};
        JComboBox<String> comboBoxRhFactor = new JComboBox<>(rhFactorOptions);
        panel.add(new JLabel("Select Rh Factor:"));
        panel.add(comboBoxRhFactor);

        JButton addButton = new JButton("Add Patient");
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String age = ageField.getText().trim();
            String email = emailField.getText().trim();
            String bloodType = (String) comboBoxBloodType.getSelectedItem();
            String rhFactor = (String) comboBoxRhFactor.getSelectedItem();

            if (name.isEmpty() || age.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!age.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Email must be a valid email address.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (bloodType == null || bloodType.isEmpty() || rhFactor == null || rhFactor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select both Blood Type and Rh Factor.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Use searchPatient instead of searchPatientByName
            PatientInfo existingPatient = patientTree.searchPatient(name);
            if (existingPatient != null) {
                JOptionPane.showMessageDialog(this, "A patient with this name already exists.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String fullBloodType = bloodType + rhFactor;

            try {
                // Use insertPatient instead of addPatient
                patientTree.insertPatient(name, age, email, fullBloodType);
                JOptionPane.showMessageDialog(this, "Patient Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                PatientInfo patient = new PatientInfo(name, age, email, fullBloodType);

                dispose();
                new HomePage(patient, patientTree).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while adding the patient. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        panel.add(addButton);
        add(panel, BorderLayout.CENTER);
    }
}
