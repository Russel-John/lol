import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AppointmentForm extends JFrame {
    // Custom Queue and Stack for managing appointments
    private static CustomQueue appointmentQueue = new CustomQueue();
    private static CustomStack processedAppointments = new CustomStack();

    public AppointmentForm() {
        setTitle("Appointment Management");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this form
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Manage Your Appointments", JLabel.CENTER));

        // Button to set an appointment
        JButton setAppointmentButton = new JButton("Set Appointment");
        setAppointmentButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(this, "Enter the title of the appointment:");
            if (title == null || title.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Appointment title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String description = JOptionPane.showInputDialog(this, "Enter the description of the appointment:");
            if (description == null || description.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Appointment description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Appointment appointment = new Appointment(title, description);
            appointmentQueue.enqueue(appointment);
            JOptionPane.showMessageDialog(this, "Appointment set:\nTitle: " + title + "\nDescription: " + description, "Appointment Set", JOptionPane.INFORMATION_MESSAGE);
        });

        // Button to go to the next appointment
        JButton goToAppointmentButton = new JButton("Go To Appointment");
        goToAppointmentButton.addActionListener(e -> {
            if (appointmentQueue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No appointments to go to.", "No Appointments", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Appointment nextAppointment = appointmentQueue.peek(); // View the first appointment
            int response = JOptionPane.showConfirmDialog(this,
                    "Next Appointment:\nTitle: " + nextAppointment.getTitle() + "\nDescription: " + nextAppointment.getDescription() +
                            "\n\nDo you want to proceed to this appointment?",
                    "Go To Appointment", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                appointmentQueue.dequeue(); // Remove the first appointment
                processedAppointments.push(nextAppointment); // Add to stack
                JOptionPane.showMessageDialog(this, "Appointment processed:\nTitle: " + nextAppointment.getTitle(), "Appointment Processed", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Button to view appointment history
        JButton viewHistoryButton = new JButton("View Appointment History");
        viewHistoryButton.addActionListener(e -> {
            if (processedAppointments.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No appointments have been processed yet.", "Appointment History", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder history = new StringBuilder("Appointment History:\n");
            for (Appointment appointment : processedAppointments) {
                history.append("Title: ").append(appointment.getTitle()).append("\n");
                history.append("Description: ").append(appointment.getDescription()).append("\n\n");
            }

            JOptionPane.showMessageDialog(this, history.toString(), "Appointment History", JOptionPane.INFORMATION_MESSAGE);
        });

        // Back button to close this form
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());

        // Adding buttons to the panel
        panel.add(setAppointmentButton);
        panel.add(goToAppointmentButton);
        panel.add(viewHistoryButton);
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);
    }

    // Inner class to represent an appointment
    private static class Appointment {
        private String title;
        private String description;

        public Appointment(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

    // Custom Queue Implementation
    private static class CustomQueue {
        private Node front;
        private Node rear;

        private static class Node {
            Appointment data;
            Node next;

            Node(Appointment data) {
                this.data = data;
                this.next = null;
            }
        }

        public void enqueue(Appointment data) {
            Node newNode = new Node(data);
            if (rear == null) {
                front = rear = newNode;
                return;
            }
            rear.next = newNode;
            rear = newNode;
        }

        public Appointment dequeue() {
            if (front == null) {
                return null;
            }
            Appointment data = front.data;
            front = front.next;
            if (front == null) {
                rear = null;
            }
            return data;
        }

        public Appointment peek() {
            if (front == null) {
                return null;
            }
            return front.data;
        }

        public boolean isEmpty() {
            return front == null;
        }
    }



private static class CustomStack implements Iterable<Appointment> {
    private Node top;

    private static class Node {
        Appointment data;
        Node next;

        Node(Appointment data) {
            this.data = data;
            this.next = null;
        }
    }

    public void push(Appointment data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
    }

    public Appointment pop() {
        if (top == null) {
            return null;
        }
        Appointment data = top.data;
        top = top.next;
        return data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public Iterator<Appointment> iterator() {
        return new Iterator<Appointment>() {
            private Node current = top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Appointment next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Appointment data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}
}