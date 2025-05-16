import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the PatientBSTree
        PatientBSTree tree = new PatientBSTree();

        // Launch the application
        SwingUtilities.invokeLater(() -> new LoginPage(tree).setVisible(true));
    }
}