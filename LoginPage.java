import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    private PatientBSTree patientTree;

    public LoginPage(PatientBSTree bst) {
        this.patientTree = bst;

        setTitle("Hospital System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nameField = new JTextField();

        panel.add(new JLabel("Enter your name to login:"));
        panel.add(nameField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PatientInfo patient = patientTree.searchPatientByName(name);
            if (patient != null) {
                dispose();
                new HomePage(patient, patientTree).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found. Please sign up first.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            new SignUpPage(patientTree).setVisible(true); // Open SignUpPage
            dispose(); // Close LoginPage only after opening SignUpPage
        });

        panel.add(loginButton);
        panel.add(signUpButton);

        add(panel, BorderLayout.CENTER);
    }
}