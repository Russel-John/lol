import javax.swing.*;
import java.awt.*;

// Add this import if AppointmentForm is in a different package
// Replace `your.package.name` with the actual package name of AppointmentForm
// import your.package.name.AppointmentForm;

public class HomePage extends JFrame {
    private PatientInfo patient;
    private PatientBSTree bst;

    public HomePage(PatientInfo patient, PatientBSTree bst) {
        this.patient = patient;
        this.bst = bst;

        setTitle("Homepage of " + patient.name);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Welcome to the Hospital System Homepage"));

        JButton showInfoButton = new JButton("Show Information");
        showInfoButton.addActionListener(e -> {
            String info = "Name: " + patient.name + "\n"
                    + "Age: " + patient.age + "\n"
                    + "Email: " + patient.email + "\n"
                    + "Blood Type: " + patient.bloodType;
            JOptionPane.showMessageDialog(this, info, "Patient Information", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton editInfoButton = new JButton("Edit Info");
        editInfoButton.addActionListener(e -> {
            new EditInfoForm(patient, bst).setVisible(true);
            dispose();
        });

        // Button to open the AppointmentForm
        JButton manageAppointmentsButton = new JButton("Manage Appointments");
        manageAppointmentsButton.addActionListener(e -> new AppointmentForm().setVisible(true));

        JButton signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(e -> {
            dispose();
            new LoginPage(bst).setVisible(true);
        });

        panel.add(showInfoButton);
        panel.add(editInfoButton);
        panel.add(manageAppointmentsButton);
        panel.add(signOutButton);

        add(panel, BorderLayout.CENTER);
    }
}